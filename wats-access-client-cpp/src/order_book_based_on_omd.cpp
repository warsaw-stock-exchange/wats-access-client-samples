#include <iostream>

#include <boost/asio/io_context.hpp>
#include <boost/asio/deadline_timer.hpp>
#include <boost/dll.hpp>

#include <spdlog/spdlog.h>
#include <yaml-cpp/yaml.h>

#include "wats/wats.hpp"
#include "wats/simple.hpp"

namespace btp = wats::trading_port;
namespace omd = wats::online_market_data;

int main() {

    boost::asio::io_context io_context;
    std::atomic<bool> finished {false};

    btp::TradingPort trading_port_0(io_context);
    btp::TradingPort trading_port_1(io_context);
    omd::MarketData market_data(io_context);

    btp::messages::OrderId orderId_0;
    btp::messages::OrderId orderId_1;

    btp::messages::OrderId orderRespId_0;
    btp::messages::OrderId orderRespId_1;

    int clients_active(0);

    auto test_result = EXIT_SUCCESS;

    std::string module = boost::dll::program_location().stem().string();
    YAML::Node config = YAML::LoadFile("wats.yml")[module];

    spdlog::set_level(spdlog::level::from_str(
        config["log_level"].as<std::string>()));

    btp::messages::ElementId instrumentId =
        config["product"].as<btp::messages::ElementId>();

    market_data.handle([&](omd::messages::EndOfSnapshot message, wats::EventSource source) {

    });

    market_data.handle([&](omd::messages::OrderAdd message, wats::EventSource source) {

        if (message.publicOrderId == orderId_0) {
            if (message.instrumentId != instrumentId ||
                message.side != omd::messages::OrderSide::Buy ||
                message.price != 100 * 100'000'000ll ||
                message.quantity != 5000) {
                test_result = EXIT_FAILURE;
            }
        }
    });

    market_data.handle([&](omd::messages::OrderModify message, wats::EventSource source) {

        if (message.publicOrderId == orderId_0) {
            if (message.price != 100 * 100'000'000ll ||
                message.quantity != 13000) {
                test_result = EXIT_FAILURE;
            }
        }
    });

    market_data.handle([&](omd::messages::OrderExecute message, wats::EventSource source) {

        if (message.publicOrderId == orderRespId_0) {

        } else if (message.publicOrderId == orderRespId_1) {

        }
    });

    market_data.handle([&](omd::messages::OrderCancel message, wats::EventSource source) {

        if (message.publicOrderId == orderRespId_0) {

        }
    });

    market_data.handle([&](omd::messages::Heartbeat message, wats::EventSource source) {

        if (finished) {
            trading_port_0.logout();
            trading_port_1.logout();
        }
    });

    trading_port_0.handle([&](btp::messages::LoginResponse message) {

        clients_active++;

        trading_port_0.orderAdd(simple_order_add(
            instrumentId,
            btp::messages::OrderSide::Buy,
            100 * 100'000'000ll,
            5000), orderId_0);
    });

    trading_port_0.handle([&](btp::messages::OrderAddResponse message) {

        if (message.orderId == orderId_0) {
            if (message.status != btp::messages::OrderStatus::New) {
                test_result = EXIT_FAILURE;
            }

            orderRespId_0 = message.publicOrderId;

            trading_port_0.orderModify(simple_order_modify(
                orderId_0,
                100 * 100'000'000ll,
                13000));

        }
    });

    trading_port_0.handle([&](btp::messages::OrderModifyResponse message) {

        if (message.orderId == orderId_0) {
            if (message.status != btp::messages::OrderStatus::New) {
                test_result = EXIT_FAILURE;
            }

            trading_port_1.orderAdd(simple_order_add(
                instrumentId,
                btp::messages::OrderSide::Sell,
                100 * 100'000'000ll,
                5000), orderId_1);
        }
    });

    trading_port_0.handle([&](btp::messages::OrderCancelResponse message) {

        if (message.orderId == orderId_0) {
            finished = true;
        }
    });

    trading_port_0.handle([&](btp::messages::OrderExecute message) {

        if (message.orderId == orderId_0) {
            if (message.price != 100 * 100'000'000ll ||
                message.quantity != 5000 ||
                message.leavesQty != 8000) {
                test_result = EXIT_FAILURE;
            }
        }
    });

    trading_port_0.handle([&](btp::messages::LogoutResponse message) {

        if (--clients_active <= 0)
            io_context.stop();
    });

    trading_port_1.handle([&](btp::messages::LoginResponse message) {

        clients_active++;
    });

    trading_port_1.handle([&](btp::messages::OrderAddResponse message) {

        if (message.orderId == orderId_1) {
            if (message.status != btp::messages::OrderStatus::Filled) {
                test_result = EXIT_FAILURE;
            }

            orderRespId_1 = message.publicOrderId;

            trading_port_0.orderCancel(simple_order_cancel(
                orderId_0
            ));
        }
    });

    trading_port_1.handle([&](btp::messages::OrderExecute message) {

        if (message.orderId == orderId_1) {
            if (message.price != 100 * 100'000'000ll ||
                message.quantity != 5000 ||
                message.leavesQty != 0) {
                test_result = EXIT_FAILURE;
            }
        }
    });

    trading_port_1.handle([&](btp::messages::LogoutResponse message) {

        if (--clients_active <= 0)
            io_context.stop();
    });

    market_data.start(config);
    trading_port_0.start(config["connection_0"]);
    trading_port_1.start(config["connection_1"]);

    io_context.run();

    market_data.stop();
    trading_port_0.stop();
    trading_port_1.stop();

    return test_result;
}