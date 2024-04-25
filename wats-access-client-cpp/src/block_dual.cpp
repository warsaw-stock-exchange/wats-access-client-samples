#include <iostream>
#include <chrono>

#include <boost/asio/io_context.hpp>
#include <boost/asio/deadline_timer.hpp>
#include <boost/dll.hpp>

#include <spdlog/spdlog.h>
#include <yaml-cpp/yaml.h>

#include "wats/wats.hpp"
#include "wats/simple.hpp"

namespace btp = wats::trading_port;
namespace omd = wats::online_market_data;

using namespace std::chrono;

inline btp::messages::Date to_date(system_clock::time_point tp) {
    const year_month_day ymd{floor<days>(tp)};
    return (int )ymd.year() * 10000 + (unsigned )ymd.month() * 100 + (unsigned )ymd.day();
}

inline bool id_equals(btp::messages::TradeReportId trade_report_id, std::string id) {
    return id.compare(0, (std::size_t )sizeof(btp::messages::TradeReportId),
        reinterpret_cast<char *>(trade_report_id)) == 0;
}

int main() {

    boost::asio::io_context io_context;
    std::atomic<bool> finished {false};

    btp::TradingPort trading_port(io_context);
    omd::MarketData market_data(io_context);

    btp::messages::OrderId orderRespId;

    int clients_active(0);
    int tcr_count(0);

    auto test_result = EXIT_SUCCESS;

    std::string module = boost::dll::program_location().stem().string();
    YAML::Node config = YAML::LoadFile("wats.yml")[module];

    spdlog::set_level(spdlog::level::from_str(
        config["log_level"].as<std::string>()));

    btp::messages::ElementId instrumentId =
        config["product"].as<btp::messages::ElementId>();

    market_data.handle([&](omd::messages::EndOfSnapshot message, wats::EventSource source) {

    });

    market_data.handle([&](omd::messages::Trade message, wats::EventSource source) {

    });

    market_data.handle([&](omd::messages::Heartbeat message, wats::EventSource source) {

        if (finished) {
            finished = false;
            trading_port.logout();
        }
    });

    trading_port.handle([&](btp::messages::LoginResponse message) {

        clients_active++;

        // Accepted
        btp::messages::TradeCaptureReportDual tcrd1 = simple_trade_capture_report_dual(
            instrumentId,
            "ID0000",
            btp::messages::ExecType::New,
            100 * 100'000'000ll,
            10,
            to_date(system_clock::now() + days{1})
        );

        trading_port.send_sequenced(tcrd1);

        // Bad exec type
        btp::messages::TradeCaptureReportDual tcrd2 = simple_trade_capture_report_dual(
            instrumentId,
            "ID0001",
            btp::messages::ExecType::Trade,
            100 * 100'000'000ll,
            10,
            to_date(system_clock::now() + days{1})
        );

        trading_port.send_sequenced(tcrd2);

        // Ducplcated TCR ID
        btp::messages::TradeCaptureReportDual tcrd3 = simple_trade_capture_report_dual(
            instrumentId,
            "ID0002",
            btp::messages::ExecType::New,
            100 * 100'000'000ll,
            10,
            to_date(system_clock::now() + days{1})
        );

        trading_port.send_sequenced(tcrd3);

        btp::messages::TradeCaptureReportDual tcrd4 = simple_trade_capture_report_dual(
            instrumentId,
            "ID0002",
            btp::messages::ExecType::New,
            100 * 100'000'000ll,
            10,
            to_date(system_clock::now() + days{1})
        );

        trading_port.send_sequenced(tcrd4);

        // Disabled temporarily
        //
        // Capture quantity below minimum
        // btp::messages::TradeCaptureReportDual tcrd5 = simple_trade_capture_report_dual(
        //     instrumentId,
        //     "ID0003",
        //     btp::messages::ExecType::New,
        //     100 * 100'000'000ll,
        //     1,
        //     to_date(system_clock::now() + days{1})
        // );

        trading_port.send_sequenced(tcrd4);

        // Settlement date yesterday
        btp::messages::TradeCaptureReportDual tcrd6 = simple_trade_capture_report_dual(
            instrumentId,
            "ID0004",
            btp::messages::ExecType::New,
            100 * 100'000'000ll,
            10,
            to_date(system_clock::now() - days{1})
        );

        trading_port.send_sequenced(tcrd6);
    });

    trading_port.handle([&](btp::messages::TradeCaptureReportResponse message) {

        if (message.instrumentId == instrumentId && id_equals(message.tradeReportId, "ID0000")) {
            if (message.status != btp::messages::TcrStatus::Accepted) {
                test_result = EXIT_FAILURE;
            }
        }

        if (message.instrumentId == instrumentId && id_equals(message.tradeReportId, "ID0001")) {
            if (message.status != btp::messages::TcrStatus::Rejected) {
                test_result = EXIT_FAILURE;
            }
        }

        if (message.instrumentId == instrumentId && id_equals(message.tradeReportId, "ID0002")) {
            if (tcr_count == 2 && message.status != btp::messages::TcrStatus::Accepted) {
                test_result = EXIT_FAILURE;
            }
            if (tcr_count == 3 && message.status != btp::messages::TcrStatus::Rejected) {
                test_result = EXIT_FAILURE;
            }
        }

        // Disabled temporarily
        //
        // if (message.instrumentId == instrumentId && id_equals(message.tradeReportId, "ID0003")) {
        //     if (message.status != btp::messages::TcrStatus::Rejected) {
        //         test_result = EXIT_FAILURE;
        //     }
        // }

        if (message.instrumentId == instrumentId && id_equals(message.tradeReportId, "ID0004")) {
            if (message.status != btp::messages::TcrStatus::Rejected) {
                test_result = EXIT_FAILURE;
            }
        }

        tcr_count++;

        finished = true;
    });

    trading_port.handle([&](btp::messages::LogoutResponse message) {

        if (--clients_active <= 0) {
            io_context.stop();
        }
    });

    market_data.start(config);
    trading_port.start(config);

    io_context.run();

    market_data.stop();
    trading_port.stop();

    return test_result;
}