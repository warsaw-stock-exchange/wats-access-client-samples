#include <iostream>
#include <queue>

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

    std::queue<btp::messages::OrderAdd> order_fifo;
    btp::messages::OrderId order_id;

    auto test_result = EXIT_SUCCESS;

    std::string module = boost::dll::program_location().stem().string();
    YAML::Node config = YAML::LoadFile("wats.yml")[module];

    spdlog::set_level(spdlog::level::from_str(
        config["log_level"].as<std::string>()));

    trading_port.handle([&](btp::messages::LoginResponse message) {
        if (!order_fifo.empty()) {
            trading_port.orderAdd(order_fifo.front(), order_id);
            order_fifo.pop();
        } else {
            trading_port.logout();
        }
    });

    trading_port.handle([&](btp::messages::OrderAddResponse message) {

        if (message.orderId == order_id) {
            if (message.status != btp::messages::OrderStatus::New &&
                message.status != btp::messages::OrderStatus::Filled) {
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

    YAML::Node orders = config["orders"];
    for(YAML::const_iterator it = orders.begin(); it != orders.end(); ++it) {
        order_fifo.push(simple_order_add(
            (*it)["product"].as<btp::messages::ElementId>(),
            btp::messages::Name2OrderSide.find((*it)["side"].as<std::string>())->second,
            (*it)["price"].as<btp::messages::Price>() * 100'000'000ll,
            (*it)["quantity"].as<btp::messages::Quantity>()));
    }

    // Let's submit a series of orders
    trading_port.start(config);

    io_context.run();

    trading_port.stop();

    return test_result;
}