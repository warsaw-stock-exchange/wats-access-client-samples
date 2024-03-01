#include <iostream>
#include <map>
#include <vector>

#include <boost/asio/io_context.hpp>
#include <boost/dll.hpp>

#include <spdlog/spdlog.h>
#include <yaml-cpp/yaml.h>

#include "wats/wats.hpp"
#include "wats/simple.hpp"

namespace btp = wats::trading_port;
namespace omd = wats::online_market_data;

int main() {

    boost::asio::io_context io_context;

    btp::TradingPort trading_port(io_context);
    omd::MarketData market_data(io_context);

    std::map<omd::messages::ElementId,
        omd::messages::ElementId> instruments;

    std::map<omd::messages::ElementId,
        std::vector<std::tuple<omd::messages::TickSize,
        omd::messages::Bound>>> tick_tables;

    btp::messages::OrderId orderId;

    auto test_result = EXIT_SUCCESS;

    std::string module = boost::dll::program_location().stem().string();
    YAML::Node config = YAML::LoadFile("wats.yml")[module];

    spdlog::set_level(spdlog::level::from_str(
        config["log_level"].as<std::string>()));

    btp::messages::ElementId instrumentId =
        config["product"].as<btp::messages::ElementId>();

    market_data.handle([&](omd::messages::Instrument message, wats::EventSource source) {

        omd::messages::ElementId instrumentId = message.instrumentId;
        omd::messages::ElementId tickTableId = message.tickTableId;

        instruments[instrumentId] = tickTableId;
    });

    market_data.handle([&](omd::messages::TickTableEntry message, wats::EventSource source) {

        omd::messages::TickSize tickSize = message.tickSize;
        omd::messages::Bound lowerBound = message.lowerBound;

        tick_tables[message.tickTableId].push_back(std::make_tuple(tickSize, lowerBound));
    });

    market_data.handle([&](omd::messages::EndOfSnapshot message, wats::EventSource source) {
        io_context.stop();
    });

    trading_port.handle([&](btp::messages::LoginResponse message) {

        btp::messages::OrderAdd buyOrder = simple_order_add(
            instrumentId,
            btp::messages::OrderSide::Buy,
            100 * 100'000'001ll,
            1000
        );

        auto tickTableId = instruments[buyOrder.instrumentId];
        auto tickTable = tick_tables[tickTableId];
        auto price = buyOrder.price;

        auto it = std::partition_point(tickTable.begin(), tickTable.end(),
            [price](const auto& t) { return get<0>(t) <= price; });

        if (it == tickTable.begin()) {
            spdlog::error("Tick Table entry not found.");
            test_result = EXIT_FAILURE;
        } else {
            auto tickSize = get<0>(*(it - 1));
            if (price % tickSize != 0) {
                spdlog::debug("Correct, tick mismatch: price: {}, tick size {}", price, tickSize);
            } else {
                test_result = EXIT_FAILURE;
            }
        }

        trading_port.logout();
    });

    trading_port.handle([&](btp::messages::LogoutResponse message) {
        io_context.stop();
    });

    io_context.reset();

    // Connect to Market Data
    market_data.start(config);

    // We do not run io_context here, because we need to only read snapshot!

    market_data.stop();

    io_context.reset();

    // Connect to Trading Port
    trading_port.start(config);

    io_context.run();

    trading_port.stop();

    return test_result;
}