#include "wats/omd.hpp"

using namespace omd;
using namespace omd::messages;
using namespace CryptoPP;

namespace wats::online_market_data {

MarketData::MarketData(boost::asio::io_context& io_context) :
    io_context_(io_context),
    socket_snapshot_(io_context),
    socket_stream_(io_context),
    socket_replay_(io_context),
    state_(SessionState::disconnected),
    expectedSeqNum_{1} {

    handle([this](LoginResponse message, EventSource source) {
        if (message.result == LoginResult::Ok) {
            this->state_ = SessionState::connected;
        } else {
            throw online_market_data_exception("Invalid login data");
        }
    });

    handle([this](EncryptionKey message, EventSource source) {

        spdlog::info("EncryptionKey received with {{ id: {} }}",
            (uint32_t)message.id);

        DecryptionProcessor processor_;

        SecByteBlock encryptionKey(32);
        SecByteBlock encryptionNonce(12);

        const uint8_t nonce[12] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

        encryptionKey.Assign(message.secretKey, sizeof(EncryptionGroupKey));
        encryptionNonce.Assign(nonce, sizeof(nonce));

        processor_.SetKeyWithIV(encryptionKey, encryptionKey.size(),
            encryptionNonce, encryptionNonce.size());

        decryption_.streams.emplace(+message.id, processor_);
    });

    handle([this](EndOfSnapshot message, EventSource source) {
        spdlog::info("finished reading from market-data/snapshot");
        spdlog::debug("EndOfSnapshot received with {{ lastSeqNum: {} }}",
            (uint32_t)message.lastSeqNum);
        expectedSeqNum_ = message.lastSeqNum + 1;
    });
}

 MarketData::~MarketData() {}

void MarketData::start(const YAML::Node &config) {

    const YAML::Node &services = config["services"]["market_data"];
    const YAML::Node &connections = config["connections"]["market_data"];

    const std::string snapshotHost = services["snapshot"]["host"].as<std::string>();
    const unsigned short snapshotPort = services["snapshot"]["port"].as<int>();
    const std::string streamHost = services["stream"]["host"].as<std::string>();
    const unsigned short streamPort = services["stream"]["port"].as<int>();
    const std::string streamInterface = services["stream"]["interface"].as<std::string>();
    const std::string replayHost = services["replay"]["host"].as<std::string>();
    const unsigned short replayPort = services["replay"]["port"].as<int>();
    const uint16_t connectionId = connections["connection_id"].as<uint16_t>();
    const std::string token = connections["token"].as<std::string>();

    start(snapshotHost, snapshotPort, streamHost, streamPort, streamInterface,
        replayHost, replayPort, token, connectionId);
}

void MarketData::start(const std::string snapshotHost, const uint16_t snapshotPort,
        const std::string streamHost, const uint16_t streamPort,
        const std::string streamInterface,
        const std::string replayHost, const uint16_t replayPort,
        const std::string &token, const uint16_t connectionId) {

    boost::system::error_code ec;

    // These have to be stored because they will be used later
    replay_host_ = replayHost;
    replay_port_ = replayPort;

    //
    // First action is to connect to TCP Market Data Snapshot Service
    //
    auto snapshot_endpoint_ = resolve(io_context_, snapshotHost, snapshotPort, ec);

    token_ = token;
    connectionId_ = connectionId;

    if (!ec) {
        spdlog::info("connecting to market-data/snapshot at: {}:{}",
            snapshot_endpoint_.address().to_string(), snapshot_endpoint_.port());

        state_ = SessionState::connecting;

        socket_snapshot_.connect(snapshot_endpoint_, ec);

        if (!ec) {
            spdlog::info("connected to market-data/snapshot");

            spdlog::info("start reading from market-data/snapshot");
            login(token_, connectionId_);

            read_snapshot();
        } else {
            spdlog::info("error connecting to market-data/snapshot");
            throw boost::system::system_error(ec);
        }
    } else {
        throw boost::system::system_error(ec);
    }

    //
    // Here we are about to connect to UDP Market Data Stream
    //
    udp::endpoint stream_endpoint_(udp::v4(), streamPort);

    spdlog::info("connecting to market-data/stream at: {}:{}",
        streamHost, streamPort);

    try {
        socket_stream_.open(stream_endpoint_.protocol());
        socket_stream_.set_option(udp::socket::reuse_address(true));
        socket_stream_.set_option(boost::asio::ip::multicast::enable_loopback(true));
        socket_stream_.bind(stream_endpoint_);

        socket_stream_.set_option(multicast::join_group(
            address::from_string(streamHost).to_v4(),
            address::from_string(streamInterface).to_v4()));

        spdlog::info("connected to market-data/stream");

        read_stream();
        spdlog::info("start reading from market-data/stream");
    }  catch (std::exception const& e) {
        spdlog::info("error connecting to market-data/stream");
    }

    this->state_ = SessionState::connected;
}

void MarketData::stop() {

    if (state_ == SessionState::connected) {

    }
}

void MarketData::dispatch(Message &message, EventSource source) {

    auto header = buffer(&message, sizeof(Message));
    Header* pheader = buffer_cast<Header*>(header);
    bool heartbeat = (pheader->msgType == MsgType::Heartbeat);

    uint64_t gap = (pheader->seqNum > 0 ? (uint64_t)pheader->seqNum - expectedSeqNum_ : 0);

    // If it is a message from the future then we have missed some messages
    // and we need to replay them.
    if(gap > 0 && source == EventSource::stream && !heartbeat) {
        spdlog::debug("requesting replay for missing {} messages", +gap);
        replay_request(expectedSeqNum_, (heartbeat ? pheader->seqNum : pheader->seqNum - 1));
    }

    // It is a message from the past. Should never happen!
    if(pheader->seqNum > 0 && pheader->seqNum < expectedSeqNum_ && !heartbeat) {
        throw online_market_data_exception("unexpected message sequence number");
    }

    if (dispatch_table_.find(pheader->msgType) != dispatch_table_.end()) {
        std::any msg = dispatch_table_[pheader->msgType](message);
        for (CallbackWrapper& callbackWrapper: callback_wrappers_) {
            if (callbackWrapper(msg.type())) {
                callbackWrapper(msg, source);
            }
        }
    }

    expectedSeqNum_ = (pheader->seqNum > 0 ? pheader->seqNum + 1 : expectedSeqNum_);
}

void MarketData::read_snapshot() {

    boost::system::error_code ec;
    auto header = buffer(&message_snapshot_, sizeof(Message));
    Header* pheader = buffer_cast<Header*>(header);

    do {
        boost::asio::read(socket_snapshot_, buffer(&message_snapshot_, sizeof(Header)), ec);
        if (!ec) {
            auto payload = buffer(header + sizeof(Header),
                pheader->length - sizeof(Header));

            boost::asio::read(socket_snapshot_, payload);

            spdlog::debug("{} received from market-data/snapshot "
                "{{ seqNum: {}, msgType: {}, length: {} }}",
                MsgType2Name.find(pheader->msgType)->second, +pheader->seqNum,
                (unsigned int)pheader->msgType, +pheader->length);

            dispatch(message_snapshot_, EventSource::snapshot);
        } else {
            stop();
        }
    } while (!ec && pheader->msgType != MsgType::EndOfSnapshot);
}

void MarketData::read_stream() {

    socket_stream_.async_receive_from(buffer(&message_stream_, sizeof(Message)), sender_,
        [this](const boost::system::error_code& ec, std::size_t n) {
            if (!ec) {
                auto header = buffer(&message_stream_, sizeof(Message));
                Header* pheader = buffer_cast<Header*>(header);

                spdlog::debug("{} received from market-data/stream "
                    "{{ seqNum: {}, msgType: {}, length: {} }}",
                    MsgType2Name.find(pheader->msgType)->second, +pheader->seqNum,
                    (unsigned int)pheader->msgType, +pheader->length);

                if (pheader->isEncrypted) {
                    switch (pheader->msgType) {
                        case MsgType::OrderAdd: {
                            decryption_.decrypt(buffer_cast<OrderAdd*>(header));
                            spdlog::debug("OrderAdd decrypted");
                            break;
                        }
                        case MsgType::OrderModify: {
                            decryption_.decrypt(buffer_cast<OrderModify*>(header));
                            spdlog::debug("OrderModify decrypted");
                            break;
                        }
                        case MsgType::OrderExecute: {
                            decryption_.decrypt(buffer_cast<OrderExecute*>(header));
                            spdlog::debug("OrderExecute decrypted");
                            break;
                        }
                    }
                }

                dispatch(message_stream_, EventSource::stream);

                read_stream();
            } else {
                stop();
            }
        });
}

void MarketData::read_replay() {

    boost::system::error_code ec;
    auto header = buffer(&message_replay_, sizeof(Message));
    Header* pheader = buffer_cast<Header*>(header);

    do {
        boost::asio::read(socket_replay_, buffer(&message_replay_, sizeof(Header)), ec);
        if (!ec) {
            auto payload = buffer(header + sizeof(Header),
                pheader->length - sizeof(Header));

            boost::asio::read(socket_replay_, payload);

            spdlog::debug("{} received from market-data/replay "
                "{{ seqNum: {}, msgType: {}, length: {} }}",
                MsgType2Name.find(pheader->msgType)->second, +pheader->seqNum,
                (unsigned int)pheader->msgType, +pheader->length);

            if (pheader->isEncrypted) {
                switch (pheader->msgType) {
                    case MsgType::OrderAdd: {
                        decryption_.decrypt(buffer_cast<OrderAdd*>(header));
                        spdlog::debug("OrderAdd decrypted");
                        break;
                    }
                    case MsgType::OrderModify: {
                        decryption_.decrypt(buffer_cast<OrderModify*>(header));
                        spdlog::debug("OrderModify decrypted");
                        break;
                    }
                    case MsgType::OrderExecute: {
                        decryption_.decrypt(buffer_cast<OrderExecute*>(header));
                        spdlog::debug("OrderExecute decrypted");
                        break;
                    }
                }
            }

            dispatch(message_replay_, EventSource::replay);
        } else {
            stop();
        }
    } while (!ec);

    socket_replay_.close();
}

void MarketData::replay_request(SeqNum from, SeqNum to) {

    boost::system::error_code ec;

    //
    // Connect to TCP Market Data Repaly Service
    //
    auto replay_endpoint_ = resolve(io_context_, replay_host_, replay_port_, ec);

    if (!ec) {
        //
        // Send replay request for desired range of sequence numbers
        //
        omd::replay::ReplayRequest replayRequest {
            .header {
                .length = sizeof(omd::replay::ReplayRequest),
                .replayMsgType = omd::replay::ReplayMsgType::ReplayRequest
            },
            .seqNum = from,
            .endSeqNum = to
        };

        spdlog::info("connecting to market-data/replay at: {}:{}",
            replay_endpoint_.address().to_string(), replay_endpoint_.port());

        socket_replay_.connect(replay_endpoint_, ec);

        if (!ec) {
            spdlog::info("connected to market-data/replay");

            try {
                write(socket_replay_, buffer((char*) &replayRequest,
                    sizeof(omd::replay::ReplayRequest)));

                spdlog::debug("ReplayRequest sent {{ seqNum: {}, endSeqNum: {} }}",
                    +replayRequest.seqNum, +replayRequest.endSeqNum);
            }  catch (std::exception const& e) {
                spdlog::error("exception while sending message: {}", e.what());
            }

            read_replay();
        } else {
            spdlog::info("error connecting to market-data/replay");
            throw boost::system::system_error(ec);
        }
    } else {
        throw boost::system::system_error(ec);
    }
}

void MarketData::login(std::string token, uint16_t connectionId) {

    Login login {
        .header = {
            .length = sizeof(Login),
            .msgType = MsgType::Login,
            .seqNum = 0
        },
        .connectionId = connectionId
    };

    token.copy((char *)login.token, std::min((int)token.length(), 8), 0);

    try {
        send(login);
        state_ = SessionState::connecting;
    }  catch (std::exception const& e) {
        spdlog::error("exception while sending message: {}", e.what());
    }
}

void MarketData::logout() {

    Logout logout {
        .header = {
            .length = sizeof(Logout),
            .msgType = MsgType::Logout,
            .seqNum = 0
        }
    };

    try {
        send(logout);
        state_ = SessionState::disconnecting;
    }  catch (std::exception const& e) {
        spdlog::error("exception while sending message: {}", e.what());
    }
}

}
