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

#endif // SIMPLE_HPP
