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

    auto test_result = EXIT_FAILURE;

    std::string module = boost::dll::program_location().stem().string();
    YAML::Node config = YAML::LoadFile("wats.yml")[module];

    spdlog::set_level(spdlog::level::from_str(
        config["log_level"].as<std::string>()));

    YAML::Emitter emitter;
    emitter << config;

    std::cout << emitter.c_str() << "\n";

    // When login response is captured logout is sent
    trading_port.handle([&](btp::messages::LoginResponse message) {
        trading_port.logout();
    });

    trading_port.handle([&](btp::messages::LogoutResponse message) {
        // We've got logoutResponse message, tell test result is a success!
        test_result = EXIT_SUCCESS;
        io_context.stop();
    });

    // Login message is sent to Trading Port here
    trading_port.start(config);

    io_context.run();

    trading_port.stop();

    return test_result;
}