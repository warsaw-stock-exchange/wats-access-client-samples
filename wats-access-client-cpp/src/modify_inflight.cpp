#include <iostream>
#include <chrono>

#include <boost/asio/io_context.hpp>
#include <boost/dll.hpp>

#include <spdlog/spdlog.h>
#include <yaml-cpp/yaml.h>

#include "wats/wats.hpp"
#include "wats/simple.hpp"

namespace btp = wats::trading_port;

using namespace std::chrono;

inline btp::messages::Date to_date(system_clock::time_point tp) {
    const year_month_day ymd{floor<days>(tp)};
    return (int )ymd.year() * 10000 + (unsigned )ymd.month() * 100 + (unsigned )ymd.day();
}

int main() {

    boost::asio::io_context io_context;

    btp::TradingPort trading_port(io_context);

    btp::messages::OrderId buyOrderId;
    btp::messages::OrderId sellOrderId;
    int32_t stage = 0;

    auto test_result = EXIT_SUCCESS;

    std::string module = boost::dll::program_location().stem().string();
    YAML::Node config = YAML::LoadFile("wats.yml")[module];

    spdlog::set_level(spdlog::level::from_str(
        config["log_level"].as<std::string>()));

    btp::messages::ElementId instrumentId = config["product"]
        .as<btp::messages::ElementId>();

    trading_port.handle([&](btp::messages::LoginResponse message) {

        stage = 1;

        trading_port.orderAdd(simple_order_add(
                instrumentId,
                btp::messages::OrderSide::Buy,
                100 * 100'000'000ll,
                1000
            ),
            buyOrderId
        );

    });

    trading_port.handle([&](btp::messages::OrderAddResponse message) {

        if (message.status == btp::messages::OrderStatus::New &&
            message.orderId == buyOrderId) {

            // OrderModify #1
            trading_port.orderModify(simple_order_modify(
                buyOrderId,
                100 * 100'000'000ll,
                2000
            ));

        } else if (message.status == btp::messages::OrderStatus::New &&
            message.orderId == sellOrderId) {

            trading_port.logout();
        }
    });

    trading_port.handle([&](btp::messages::OrderModifyResponse message) {

        if (message.orderId == buyOrderId) {
            switch (++stage) {
                case 1: {
                    if (message.status != btp::messages::OrderStatus::New) {
                        test_result = EXIT_FAILURE;
                    }

                    // OrderModify #2
                    trading_port.orderModify(simple_order_modify(
                        buyOrderId,
                        101 * 100'000'000ll,
                        2000
                    ));

                    break;
                };
                case 2: {
                    if (message.status != btp::messages::OrderStatus::New) {
                        test_result = EXIT_FAILURE;
                    }

                    // OrderModify #3
                    trading_port.orderModify(simple_order_modify(
                        buyOrderId,
                        10001 * 100'000'000ll,
                        2000000000
                    ));

                    break;
                };
                case 3: {
                    if (message.status != btp::messages::OrderStatus::Rejected) {
                        test_result = EXIT_FAILURE;
                    }

                    // OrderModify #4
                    trading_port.orderModify(simple_order_modify(
                        buyOrderId,
                        100 * 100'000'000ll,
                        1500
                    ));

                    break;
                };
                case 4: {
                    if (message.status != btp::messages::OrderStatus::New) {
                        test_result = EXIT_FAILURE;
                    }

                    // OrderModify #5
                    btp::messages::OrderModify orderModify = simple_order_modify(
                        buyOrderId,
                        100 * 100'000'000ll,
                        1500
                    );
                    orderModify.expire = to_date(system_clock::now() - days{1});
                    trading_port.orderModify(orderModify);

                    break;
                };
                case 5: {
                    if (message.status != btp::messages::OrderStatus::Rejected) {
                        test_result = EXIT_FAILURE;
                    }

                    // OrderModify #6
                    trading_port.orderModify(simple_order_modify(
                        buyOrderId,
                        100 * 100'000'000ll,
                        3000
                    ));

                    break;
                };
                case 6: {
                    if (message.status != btp::messages::OrderStatus::New) {
                        test_result = EXIT_FAILURE;
                    }

                    // Wrap things up - sell matching stock
                    trading_port.orderAdd(simple_order_add(
                            instrumentId,
                            btp::messages::OrderSide::Sell,
                            101 * 100'000'000ll,
                            3000
                        ),
                        sellOrderId
                    );

                    break;
                };
            }
        }

        if (test_result == EXIT_FAILURE)
            trading_port.logout();
    });

    trading_port.handle([&](btp::messages::Trade message) {

        if (message.orderId == buyOrderId) {
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