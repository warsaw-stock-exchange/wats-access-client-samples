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
    std::atomic<int> connectionCount {0};

    btp::TradingPort trading_port_0(io_context);
    btp::TradingPort trading_port_1(io_context);

    btp::messages::OrderId orderId_0;
    btp::messages::OrderId orderId_1;

    auto test_result = EXIT_SUCCESS;

    std::string module = boost::dll::program_location().stem().string();
    YAML::Node config = YAML::LoadFile("wats.yml")[module];

    spdlog::set_level(spdlog::level::from_str(
        config["log_level"].as<std::string>()));

    btp::messages::ElementId instrumentId = config["product"]
        .as<btp::messages::ElementId>();

    trading_port_0.handle([&](btp::messages::LoginResponse message) {

        connectionCount++;

        btp::messages::OrderAdd buyOrder = simple_order_add(
            instrumentId,
            btp::messages::OrderSide::Sell,
            100 * 100'000'000ll,
            1000);

        trading_port_0.orderAdd(buyOrder, orderId_0);
    });

    trading_port_0.handle([&](btp::messages::OrderAddResponse message) {

        if (message.orderId == orderId_0) {
            if (message.status != btp::messages::OrderStatus::Ack) {
                test_result = EXIT_FAILURE;
            }

            trading_port_0.logout();
        }
    });

    trading_port_0.handle([&](btp::messages::LogoutResponse message) {

        if(--connectionCount == 0) io_context.stop();
    });

    trading_port_1.handle([&](btp::messages::LoginResponse message) {

        connectionCount++;

        btp::messages::OrderAdd sellOrder = simple_order_add(
            instrumentId,
            btp::messages::OrderSide::Buy,
            100 * 100'000'000ll,
            1000);

        trading_port_1.orderAdd(sellOrder, orderId_1);
    });

    trading_port_1.handle([&](btp::messages::OrderAddResponse message) {

        if (message.orderId == orderId_1) {
            if (message.status != btp::messages::OrderStatus::Filled) {
                test_result = EXIT_FAILURE;
            }

            trading_port_1.logout();
        }
    });

    trading_port_1.handle([&](btp::messages::LogoutResponse message) {

        if(--connectionCount == 0) io_context.stop();
    });

    // Login message is sent to Trading Port here
    trading_port_0.start(config["connection_0"]);
    trading_port_1.start(config["connection_1"]);

    io_context.run();

    trading_port_0.stop();
    trading_port_1.stop();

    return test_result;
}