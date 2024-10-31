#include <iostream>
#include <queue>
#include <chrono>

#include <boost/asio/io_context.hpp>
#include <boost/dll.hpp>

#include <spdlog/spdlog.h>
#include <yaml-cpp/yaml.h>

#include "wats/wats.hpp"
#include "wats/simple.hpp"

namespace btp = wats::trading_port;
namespace omd = wats::online_market_data;
namespace bbo = wats::best_bid_offer;

int main() {

    boost::asio::io_context io_context;

    btp::TradingPort trading_port(io_context);
    bbo::BestBidOffer best_bid_offer(io_context);

    std::queue<btp::messages::OrderAdd> order_fifo;
    btp::messages::OrderId order_id;

    std::vector<omd::messages::PriceLevel> buy;
    std::vector<omd::messages::PriceLevel> sell;

    auto test_result = EXIT_SUCCESS;

    std::string module = boost::dll::program_location().stem().string();
    YAML::Node config = YAML::LoadFile("wats.yml")[module];

    spdlog::set_level(spdlog::level::from_str(
        config["log_level"].as<std::string>()));

    omd::messages::ElementId instrumentId =
        config["product"].as<omd::messages::ElementId>();

    trading_port.handle([&](btp::messages::LoginResponse message) {
        trading_port.orderAdd(order_fifo.front(), order_id);
        order_fifo.pop();
    });

    trading_port.handle([&](btp::messages::OrderAddResponse message) {

        if (message.orderId == order_id) {
            if (message.status != btp::messages::OrderStatus::New) {
                test_result = EXIT_FAILURE;
                trading_port.logout();
            } else {
                if (!order_fifo.empty()) {
                    // Submit order
                    trading_port.orderAdd(order_fifo.front(), order_id);
                    order_fifo.pop();
                } else {
                    trading_port.logout();
                }
            }
        }
    });

    trading_port.handle([&](btp::messages::LogoutResponse message) {

        io_context.stop();
    });

    best_bid_offer.handle([&](omd::messages::PriceLevelSnapshot message, wats::EventSource source) {

        spdlog::info("PriceLevelSnapshot received from BBO {{ instrumentId: {} }}",
            +message.instrumentId);

        // Here accumulate knowledge on bids placed for the product
        if (message.instrumentId == instrumentId) {

            buy.clear();
            sell.clear();

            for(omd::messages::PriceLevel price_level: message.buy) {
                if(price_level.price > 0) buy.push_back(price_level);
            }
            for(omd::messages::PriceLevel price_level: message.sell) {
                if(price_level.price > 0) sell.push_back(price_level);
            }
        }

        // There should be a minimum of 4 orders concerning this product
        if(buy.size() == 2 && sell.size() == 2) {
            io_context.stop();
        }
    });

    // Heartbeats can sometimes be used to push processing when no other
    // mesages flow - allows to avoid a deadlock here.
    best_bid_offer.handle([&](omd::messages::Heartbeat, wats::EventSource source) {
        if(buy.size() != 2 || sell.size() != 2) {
            io_context.stop();
            test_result = EXIT_FAILURE;
        }
    });

    // Procure a queue of orders to be send to Trading Port
    order_fifo.push(simple_order_add(
        instrumentId,
        btp::messages::OrderSide::Buy,
        99 * 100'000'000ll,
        1000));

    order_fifo.push(simple_order_add(
        instrumentId,
        btp::messages::OrderSide::Buy,
        98 * 100'000'000ll,
        1000));

    order_fifo.push(simple_order_add(
        instrumentId,
        btp::messages::OrderSide::Sell,
        101 * 100'000'000ll,
        1000));

    order_fifo.push(simple_order_add(
        instrumentId,
        btp::messages::OrderSide::Sell,
        102 * 100'000'000ll,
        1000));

    io_context.reset();

    // Let's see what Best Bid Offer service has to say
    best_bid_offer.start(config);

    // Now let's submit a series of orders
    trading_port.start(config);

    io_context.run();

    trading_port.stop();

    best_bid_offer.stop();

    if(buy.size() != 2 ||
        buy[0].price != 99 * 100'000'000ll ||
        buy[1].price != 98 * 100'000'000ll ||
        sell.size() != 2 ||
        sell[0].price != 101 * 100'000'000ll ||
        sell[1].price != 102 * 100'000'000ll) {
        test_result = EXIT_FAILURE;
    }

    return test_result;
}