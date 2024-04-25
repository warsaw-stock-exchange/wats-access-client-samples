#include "wats/btp.hpp"

using namespace btp;
using namespace btp::messages;

namespace wats::trading_port {

TradingPort::TradingPort(boost::asio::io_context& io_context) :
    io_context_(io_context),
    socket_(io_context),
    state_(SessionState::disconnected),
    outSeqNum_{1},
    inSeqNum_{1} {

    handle([this](LoginResponse message){
        if (message.result == LoginResult::Ok) {
            this->state_ = SessionState::connected;
            this->outSeqNum_ = message.nextExpectedSeqNum;
            this->sessionId_ = message.sessionId;
        }
    });

    handle([this](LogoutResponse message){
        spdlog::info("LogoutResponse received");
        this->state_ = SessionState::disconnected;
    });

    handle([this](ConnectionClose message){
        spdlog::info("ConnectionClose received with {{ Reason: {} }}",
            (uint32_t)message.reason);
        this->state_ = SessionState::disconnected;
        stop();
    });

    handle([](Test message){
        spdlog::debug("Test message received");
    });
}

TradingPort::~TradingPort() {}

void TradingPort::start(const YAML::Node &config) {

    const YAML::Node &services = config["services"]["trading_port"];
    const YAML::Node &connections = config["connections"]["trading_port"];

    const std::string host = services["host"].as<std::string>();
    const unsigned short port = services["port"].as<int>();
    const uint16_t connectionId = connections["connection_id"].as<uint16_t>();
    const std::string token = connections["token"].as<std::string>();

    start(host, port, token, connectionId);
}

void TradingPort::start(const std::string &host, const unsigned short port,
    const std::string &token, const uint16_t connectionId) {

    boost::system::error_code ec;
    auto endpoint = resolve(io_context_, host, port, ec);

    token_ = token;
    connectionId_ = connectionId;

    if (!ec) {
        spdlog::info("connecting to trading-port at: {}:{}",
            endpoint.address().to_string(), endpoint.port());

        state_ = SessionState::connecting;

        socket_.async_connect(endpoint, [this](const boost::system::error_code& ec) {
            if (!ec) {
                spdlog::info("connected to trading-port");

                read();
                login(token_, connectionId_);
            } else {
                spdlog::info("error connecting to trading-port");
                throw boost::system::system_error(ec);
            }
        });
    } else {
        throw boost::system::system_error(ec);
    }
}

void TradingPort::stop() {

    if (state_ != SessionState::disconnected) {
        logout();
        // Wait some time for logout response
        for (auto i = 50; i > 0; i--) {
            if (state_ == SessionState::disconnected) break;
            std::this_thread::sleep_for(std::chrono::milliseconds(100));
        }
    }
}

void TradingPort::dispatch() {

    const auto header_ =
        boost::asio::buffer(&message_, sizeof(btp::messages::Message));
    const btp::messages::Header* header =
        boost::asio::buffer_cast<btp::messages::Header*>(header_);

    if (dispatch_table_.find(header->msgType) != dispatch_table_.end()) {
        std::any msg = dispatch_table_[header->msgType](message_);
        for (CallbackWrapper& callbackWrapper: callback_wrappers_) {
            if (callbackWrapper(msg.type())) {
                callbackWrapper(msg);
            }
        }
    }
}

void TradingPort::read() {

    async_read(socket_, buffer(&message_, sizeof(Header)),
        [this](const boost::system::error_code& ec, std::size_t n) {
            if (!ec) {
                const auto header_ = buffer(&message_, sizeof(Message));
                const Header* header = buffer_cast<Header*>(header_);

                auto message = buffer(header, header->length);
                auto payload = buffer(header_ + sizeof(Header), header->length - sizeof(Header));

                boost::asio::read(socket_, payload);

                spdlog::debug("{} received from trading-port {{ seqNum: {}, msgType: {}, length: {} }}",
                    MsgType2Name.find(header->msgType)->second, (uint32_t)header->seqNum,
                    (uint16_t)header->msgType, header->length);

                dispatch();

                read();
            } else {
                stop();
            }
        });
}

void TradingPort::login(std::string token, uint16_t connectionId) {

    Login login {
        .header = {
            .length = sizeof(Login),
            .msgType = MsgType::Login,
            .seqNum = 0
        },
        .version = 0,
        .connectionId = connectionId,
        .nextExpectedSeqNum = inSeqNum_,
        .lastSentSeqNum = outSeqNum_
    };

    token.copy((char *)login.token, std::min((int)token.length(), 8), 0);

    try {
        send(login);
        state_ = SessionState::connecting;
    }  catch (std::exception const& e) {
        spdlog::error("exception while sending message: {}", e.what());
    }
}

void TradingPort::logout() {

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

void TradingPort::orderAdd(OrderAdd message, OrderId& orderId) {

    // Prior to send, create order reference ID
    orderId = createOrderId(outSeqNum_, sessionId_, connectionId_);

    // Header is adjusted by the library
    message.header = {
        .length = sizeof(OrderAdd),
        .msgType = MsgType::OrderAdd,
        .seqNum = outSeqNum_++
    };

    if (state_ == SessionState::connected) {
        send(message);
    } else {
        throw trading_port_exception("not connected to trading-port");
    }
}

void TradingPort::orderModify(OrderModify message) {

    message.header = {
        .length = sizeof(OrderModify),
        .msgType = MsgType::OrderModify,
        .seqNum = outSeqNum_++
    };

    if (state_ == SessionState::connected) {
        send(message);
    } else {
        throw trading_port_exception("not connected to trading-port");
    }
}

void TradingPort::orderCancel(OrderCancel message) {

    message.header = {
        .length = sizeof(OrderCancel),
        .msgType = MsgType::OrderCancel,
        .seqNum = outSeqNum_++
    };

    if (state_ == SessionState::connected) {
        send(message);
    } else {
        throw trading_port_exception("not connected to trading-port");
    }
}

}
