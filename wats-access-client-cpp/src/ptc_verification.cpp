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
        omd::messages::Instrument> instruments;

    std::map<omd::messages::ElementId,
        std::vector<std::tuple<omd::messages::TickSize,
        omd::messages::Bound>>> tick_tables;

    btp::messages::OrderId order_ref_id_1;
    btp::messages::OrderId order_ref_id_2;
    btp::messages::OrderId order_ref_id_3;
    btp::messages::OrderId order_ref_id_4;

    auto test_result = EXIT_SUCCESS;

    std::string module = boost::dll::program_location().stem().string();
    YAML::Node config = YAML::LoadFile("wats.yml")[module];

    spdlog::set_level(spdlog::level::from_str(
        config["log_level"].as<std::string>()));

    btp::messages::ElementId instrumentId =
        config["product"].as<btp::messages::ElementId>();

    market_data.handle([&](omd::messages::Instrument message, wats::EventSource source) {

        instruments[message.instrumentId] = message;
    });

    market_data.handle([&](omd::messages::TickTableEntry message, wats::EventSource source) {

        omd::messages::TickSize tick_size = message.tickSize;
        omd::messages::Bound lowerBound = message.lowerBound;

        tick_tables[message.tickTableId].push_back(std::make_tuple(tick_size, lowerBound));
    });

    market_data.handle([&](omd::messages::EndOfSnapshot message, wats::EventSource source) {
        io_context.stop();
    });

    trading_port.handle([&](btp::messages::LoginResponse message) {

        omd::messages::Instrument instrument = instruments[instrumentId];

        auto price = 100 * 100'000'000ll;
        auto max_price = (instrument.preTradeCheckMaxValue /
            instrument.preTradeCheckMaxQuantity) * 100'000'000ll;
        auto quantity = (instrument.preTradeCheckMaxQuantity -
            instrument.preTradeCheckMinQuantity) / 2;

        trading_port.orderAdd(simple_order_add(
            instrument.instrumentId,
            btp::messages::OrderSide::Buy,
            price,
            1000), order_ref_id_1);

        trading_port.orderAdd(simple_order_add(
            instrument.instrumentId,
            btp::messages::OrderSide::Buy,
            price,
            quantity), order_ref_id_2);

        trading_port.orderAdd(simple_order_add(
            instrument.instrumentId,
            btp::messages::OrderSide::Buy,
            price,
            instrument.preTradeCheckMaxQuantity + 1), order_ref_id_3);

        trading_port.orderAdd(simple_order_add(
            instrument.instrumentId,
            btp::messages::OrderSide::Buy,
            max_price,
            instrument.preTradeCheckMaxQuantity), order_ref_id_4);
    });

    trading_port.handle([&](btp::messages::OrderAddResponse message) {

        if (message.orderId == order_ref_id_1) {
            if (message.status != btp::messages::OrderStatus::New) {
                test_result = EXIT_FAILURE;
            }
        } else if (message.orderId == order_ref_id_2) {
            if (message.status != btp::messages::OrderStatus::Rejected ||
                message.reason != btp::messages::OrderRejectionReason::OrderValueMustBeLowerThanMaximumValue) {
                test_result = EXIT_FAILURE;
            }
        } else if (message.orderId == order_ref_id_3) {
            if (message.status != btp::messages::OrderStatus::Rejected ||
                message.reason != btp::messages::OrderRejectionReason::OrderQuantityMustBeLowerThanMaximumQuantity) {
                test_result = EXIT_FAILURE;
            }
        }  else if (message.orderId == order_ref_id_4) {
            if (message.status != btp::messages::OrderStatus::Rejected ||
                message.reason != btp::messages::OrderRejectionReason::PriceAboveHighCollar) {
                test_result = EXIT_FAILURE;
            }

            trading_port.logout();
        }
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