#include <iostream>
#include <map>
#include <vector>

#include <boost/asio/io_context.hpp>
#include <boost/dll.hpp>

#include <spdlog/spdlog.h>
#include <yaml-cpp/yaml.h>

#include "wats/wats.hpp"

namespace btp = wats::trading_port;
namespace omd = wats::online_market_data;

int main() {

    boost::asio::io_context io_context;

    btp::TradingPort trading_port(io_context);
    omd::MarketData market_data(io_context);

    std::map<omd::messages::ElementId,
        std::vector<std::tuple<omd::messages::TickSize,
        omd::messages::Bound>>> tick_tables;

    btp::messages::SeqNum LastSeqNum;

    auto test_result = EXIT_SUCCESS;

    std::string module = boost::dll::program_location().stem().string();
    YAML::Node config = YAML::LoadFile("wats.yml")[module];

    spdlog::set_level(spdlog::level::from_str(
        config["log_level"].as<std::string>()));

    market_data.handle([&](omd::messages::EndOfSnapshot message, wats::EventSource source) {
        LastSeqNum = message.lastSeqNum;
    });

    trading_port.handle([&](btp::messages::LoginResponse message) {
        //
        // Here we request MD to replay a certain range of messages
        //
        try {
            market_data.replay_request((LastSeqNum - 10 > 1) ? LastSeqNum - 10 : 1, 0);
        } catch (omd::online_market_data_exception e) {
            // Exception is expected because we request a replay of messages
            // already seen. It is Because replays are normally requested
            // internally by the Online Market Data implementation whenever
            // a gap is detected within the sequences in stream of messages.
        }

        trading_port.logout();
    });

    trading_port.handle([&](btp::messages::LogoutResponse message) {
        io_context.stop();
    });

    // Connect to Market Data
    market_data.start(config);

    // Connect to Trading Port
    trading_port.start(config);

    io_context.run();

    trading_port.stop();
    market_data.stop();

    return test_result;
}