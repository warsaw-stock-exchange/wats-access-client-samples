#include "wats/dmd.hpp"

using namespace omd::messages;
using namespace CryptoPP;

namespace wats::delayed_market_data {

DelayedMarketData::DelayedMarketData(boost::asio::io_context& io_context) :
    io_context_(io_context),
    socket_stream_(io_context),
    socket_replay_(io_context),
    state_(SessionState::disconnected) {}

 DelayedMarketData::~DelayedMarketData() {}

void DelayedMarketData::start(const YAML::Node &config) {

    const YAML::Node &services = config["services"]["delayed_market_data"];
    const YAML::Node &connections = config["connections"]["delayed_market_data"];

    const std::string streamHost = services["stream"]["host"].as<std::string>();
    const unsigned short streamPort = services["stream"]["port"].as<int>();
    const std::string streamInterface = services["stream"]["interface"].as<std::string>();
    const std::string replayHost = services["replay"]["host"].as<std::string>();
    const unsigned short replayPort = services["replay"]["port"].as<int>();
    const uint16_t connectionId = connections["connection_id"].as<uint16_t>();
    const std::string token = connections["token"].as<std::string>();

    start(streamHost, streamPort, streamInterface, replayHost, replayPort);
}

void DelayedMarketData::start(const std::string streamHost, const uint16_t streamPort,
        const std::string streamInterface,
        const std::string replayHost, const uint16_t replayPort) {

    boost::system::error_code ec;

    // These have to be stored because they will be used later
    replay_host_ = replayHost;
    replay_port_ = replayPort;

    //
    // Here we are about to connect to UDP Delayed Market Data Stream
    //
    udp::endpoint stream_endpoint_(udp::v4(), streamPort);

    spdlog::info("connecting to delayed-market-data/stream at: {}:{}",
        streamHost, streamPort);

    try {
        socket_stream_.open(stream_endpoint_.protocol());
        socket_stream_.set_option(udp::socket::reuse_address(true));
        socket_stream_.set_option(boost::asio::ip::multicast::enable_loopback(true));
        socket_stream_.bind(stream_endpoint_);

        socket_stream_.set_option(multicast::join_group(
            address::from_string(streamHost).to_v4(),
            address::from_string(streamInterface).to_v4()));

        spdlog::info("connected to delayed-market-data/stream");

        read_stream();
        spdlog::info("start reading from delayed-market-data/stream");
    }  catch (std::exception const& e) {
        spdlog::info("error connecting to delayed-market-data/stream");
    }

    this->state_ = SessionState::connected;
}

void DelayedMarketData::stop() {

    if (state_ == SessionState::connected) {

    }
}

void DelayedMarketData::dispatch(Message &message, EventSource source) {

    auto header = buffer(&message, sizeof(Message));
    Header* pheader = buffer_cast<Header*>(header);
    bool heartbeat = (pheader->msgType == MsgType::Heartbeat);

    uint64_t gap = (pheader->seqNum > 0 ? (uint64_t)pheader->seqNum - expectedSeqNum_ : 0);

    // If it is a message from the future then we have missed some messages
    // and we need to replay them.
    if(gap > 0 && source == EventSource::stream) {
        spdlog::debug("requesting replay for missing {} messages", +gap);
        replay_request(expectedSeqNum_, (heartbeat ? pheader->seqNum : pheader->seqNum - 1));
    }

    // It is a message from the past. Should never happen!
    if(pheader->seqNum > 0 && pheader->seqNum < expectedSeqNum_ && !heartbeat) {
        throw delayed_market_data_exception("unexpected message sequence number");
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

void DelayedMarketData::read_stream() {

    udp::endpoint sender_;

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

void DelayedMarketData::read_replay() {

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

void DelayedMarketData::replay_request(SeqNum from, SeqNum to) {

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

void DelayedMarketData::login(std::string token, uint16_t connectionId) {

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

void DelayedMarketData::logout() {

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
