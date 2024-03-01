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

    btp::TradingPort trading_port(io_context);

    btp::messages::OrderId orderId;

    auto test_result = EXIT_SUCCESS;

    std::string module = boost::dll::program_location().stem().string();
    YAML::Node config = YAML::LoadFile("wats.yml")[module];

    spdlog::set_level(spdlog::level::from_str(
        config["log_level"].as<std::string>()));

    trading_port.handle([&](btp::messages::LoginResponse message) {

        btp::messages::OrderAdd buyOrderBadInstrumentId = simple_order_add(
            654321,
            btp::messages::OrderSide::Buy,
            100 * 100'000'000ll,
            1000
        );

        trading_port.orderAdd(buyOrderBadInstrumentId, orderId);
    });

    trading_port.handle([&](btp::messages::OrderAddResponse message) {

        if (message.orderId == orderId) {
            if (message.status != btp::messages::OrderStatus::Rejected) {
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