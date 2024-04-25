#ifndef SIMPLE_HPP
#define SIMPLE_HPP

#include "wats/wats.hpp"

namespace btp = wats::trading_port;

inline btp::messages::OrderAdd simple_order_add(
    btp::messages::ElementId instrument_id,
    btp::messages::OrderSide order_side,
    btp::messages::Price price,
    btp::messages::Quantity quantity) {

    return btp::messages::OrderAdd {
        .onBehalfOf = 0,
        .stpId = 0,
        .instrumentId = instrument_id,
        .orderType = btp::messages::OrderType::Limit,
        .timeInForce = btp::messages::TimeInForce::Day,
        .side = order_side,
        .price = price,
        .triggerPrice = 0,
        .quantity = quantity,
        .capacity = btp::messages::Capacity::Agency,
        .accountType = btp::messages::AccountType::Missing,
        .mifidFields = {
            .flags = btp::messages::MifidFlags::None,
            .client = {
                .shortCode = 12345678,
                .qualifier = btp::messages::PartyRoleQualifier::FirmOrLegalEntity,
            },
            .executingTrader = {
                .shortCode = 12345678,
                .qualifier = btp::messages::PartyRoleQualifier::Algorithm,
            },
            .investmentDecisionMaker = {
                .shortCode = 12345678,
                .qualifier = btp::messages::PartyRoleQualifier::NaturalPerson,
            }
        },
        .memo = { 0 },
        .clearingMemberCode = { 0 },
        .clearingMemberClearingIdentifier =
            btp::messages::ClearingIdentifier::NotApplicable
    };
}

inline btp::messages::TradeCaptureReportDual simple_trade_capture_report_dual(
    btp::messages::ElementId instrument_id,
    std::string trade_report_id,
    btp::messages::ExecType exec_type,
    btp::messages::Price last_px,
    btp::messages::Quantity last_qty,
    btp::messages::Date settlement_date) {

    btp::messages::TradeCaptureReportDual m {
        .header = {
            .length = sizeof(btp::messages::TradeCaptureReportDual),
            .msgType = btp::messages::MsgType::TradeCaptureReportDual,
            .seqNum = 0
        },
        .instrumentId = instrument_id,
        .tradeId = 0,
        .tradeReportTransType = btp::messages::TradeReportTransType::New,
        .tradeReportType = btp::messages::TradeReportType::Submit,
        .tradeType = btp::messages::TradeType::BlockTrade,
        .algorithmicTradeIndicator = btp::messages::AlgorithmicTradeIndicator::NonAlgorithmicTrade,
        .execType = exec_type,
        .lastQty = last_qty,
        .lastPx = last_px,
        .settlementDate = settlement_date,
        .matchStatus = btp::messages::MatchStatus::NA,
        .tcrPartyBuy = {
            .mifidFields = {
                .flags = btp::messages::MifidFlags::None,
                .client = {
                    .shortCode = 1,
                    .qualifier = btp::messages::PartyRoleQualifier::NA,
                },
                .executingTrader = {
                    .shortCode = 4,
                    .qualifier = btp::messages::PartyRoleQualifier::Algorithm,
                },
                .investmentDecisionMaker = {
                    .shortCode = 17,
                    .qualifier = btp::messages::PartyRoleQualifier::NaturalPerson,
                }
            },
            .account = "",
            .accountType = btp::messages::AccountType::Customer,
            .orderCapacity = btp::messages::Capacity::Agency,
            .orderRestrictions = 0,
            .orderOrigination = 0,
            .memo = ""
        },
        .tcrPartySell = {
            .mifidFields = {
                .flags = btp::messages::MifidFlags::None,
                .client = {
                    .shortCode = 1,
                    .qualifier = btp::messages::PartyRoleQualifier::NA,
                },
                .executingTrader = {
                    .shortCode = 4,
                    .qualifier = btp::messages::PartyRoleQualifier::Algorithm,
                },
                .investmentDecisionMaker = {
                    .shortCode = 17,
                    .qualifier = btp::messages::PartyRoleQualifier::NaturalPerson,
                }
            },
            .account = "",
            .accountType = btp::messages::AccountType::Customer,
            .orderCapacity = btp::messages::Capacity::Agency,
            .orderRestrictions = 0,
            .orderOrigination = 0,
            .memo = ""
        }
    };

    memcpy(m.tradeReportId, trade_report_id.data(), trade_report_id.length());

    return m;
}

#endif // SIMPLE_HPP
