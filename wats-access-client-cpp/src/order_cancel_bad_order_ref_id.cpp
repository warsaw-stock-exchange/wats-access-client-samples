#include <iostream>

#include <boost/asio/io_context.hpp>
#include <boost/dll.hpp>

#include <spdlog/spdlog.h>
#include <yaml-cpp/yaml.h>

#include "wats/wats.hpp"
#include "wats/simple.hpp"

namespace btp = wats::trading_port;

int main() {

    boost::asio::io_context io_context;
    btp::messages::SeqNum lastReplaySeqNum {0};

    btp::TradingPort trading_port(io_context);

    btp::messages::OrderId orderId;

    auto test_result = EXIT_SUCCESS;

    std::string module = boost::dll::program_location().stem().string();
    YAML::Node config = YAML::LoadFile("wats.yml")[module];

    spdlog::set_level(spdlog::level::from_str(
        config["log_level"].as<std::string>()));

    btp::messages::ElementId instrumentId = config["product"]
        .as<btp::messages::ElementId>();

    trading_port.handle([&](btp::messages::LoginResponse message) {

        lastReplaySeqNum = message.lastReplaySeqNum;

        btp::messages::OrderAdd buyOrder = simple_order_add(
            instrumentId,
            btp::messages::OrderSide::Buy,
            100 * 100'000'000ll,
            1000
        );

        trading_port.orderAdd(buyOrder, orderId);
    });

    trading_port.handle([&](btp::messages::OrderAddResponse message) {

        if (message.orderId == orderId) {
            if (message.status != btp::messages::OrderStatus::Ack) {
                test_result = EXIT_FAILURE;
                trading_port.logout();
            } else {
                btp::messages::OrderCancel cancelOrder {
                    .orderId = 0
                };

                trading_port.orderCancel(cancelOrder);
            }
        }
    });

    trading_port.handle([&](btp::messages::OrderCancelResponse message) {

        if (message.header.seqNum > lastReplaySeqNum) {
            if (message.reason != btp::messages::OrderRejectionReason::UnknownOrder) {
                test_result = EXIT_FAILURE;
            }

            trading_port.logout();
        }
    });

    trading_port.handle([&](btp::messages::LogoutResponse message) {

        io_context.stop();
    });

    // Login message is sent to Trading Port here
    trading_port.start(config);

    io_context.run();

    trading_port.stop();

    return test_result;
}