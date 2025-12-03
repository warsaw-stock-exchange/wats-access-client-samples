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
        .memo = "",
        .clearingMemberCode = "",
        .clearingMemberClearingIdentifier =
            btp::messages::ClearingIdentifier::NotApplicable,
        .execInst = btp::messages::ExecInst::None
    };
}

inline btp::messages::OrderModify simple_order_modify(
    btp::messages::OrderId order_id,
    btp::messages::Price price,
    btp::messages::Quantity quantity) {

    return btp::messages::OrderModify {
        .orderId = order_id,
        .price = price,
        .quantity = quantity,
        .mifidFields = {
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
        }
    };
}

inline btp::messages::OrderCancel simple_order_cancel(
    btp::messages::OrderId order_id) {

    return btp::messages::OrderCancel {
        .orderId = order_id,
        .mifidFields = {
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
        }
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
        .algorithmicTradeIndicator = btp::messages::AlgorithmicTradeIndicator::NA,
        .execType = exec_type,
        .lastQty = last_qty,
        .lastPx = last_px,
        .settlementDate = settlement_date,
        .tcrPartyBuy = {
            .mifidFields = {
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
            .clearingMemberCode = "",
            .clearingMemberClearingIdentifier = btp::messages::ClearingIdentifier::NotApplicable,
            .account = "",
            .accountType = btp::messages::AccountType::Customer,
            .orderCapacity = btp::messages::Capacity::Agency,
            .memo = ""
        },
        .tcrPartySell = {
            .mifidFields = {
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
            .clearingMemberCode = "",
            .clearingMemberClearingIdentifier = btp::messages::ClearingIdentifier::NotApplicable,
            .account = "",
            .accountType = btp::messages::AccountType::Customer,
            .orderCapacity = btp::messages::Capacity::Agency,
            .memo = ""
        }
    };

    memcpy(m.tradeReportId, trade_report_id.data(), trade_report_id.length());

    return m;
}

#endif // SIMPLE_HPP
