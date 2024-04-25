use anyhow::Result;
use std::sync::Once;
use tracing::{debug, instrument, trace};
use wats_access_client_rust::messages::trading_port::{
    self as tp, MifidField, MifidFields, MifidFlags, PartyRoleQualifier
};
use wats_access_client_rust::{from_bytes, Client, ClientBuilder};

static INIT: Once = Once::new();

pub(super) fn init_tracing() {
    INIT.call_once(|| {
        tracing_subscriber::fmt::init();
    });
}

/// Create a WATS client
pub(super) async fn create_client(tp_connection_id: u16, tp_token: [u8; 8], md_connection_id: u16, md_token: [u8; 8]) -> Result<Client> {
    debug!("Creating new client");

    let wats_config = crate::config::get_config()?;

    ClientBuilder::connect(
        wats_config.tp_addr(),
        wats_config.omd_addr(),
        wats_config.omd_snap_addr(),
        wats_config.omd_replay_addr(),
        wats_config.multicast_interface(),
    )
    .await?
    .login(tp_connection_id, tp_token, md_connection_id, md_token)
    .await
}

// Fetch messages buffer up to `last_replay_seq_num` from `LoginResponse`.
#[instrument(skip_all, fields(connection_id = client.tp_connection_id()))]
pub(super) async fn fetch_messages(client: &mut Client) {
    let last_seq_num = client.last_seq_num();

    trace!("Fetching messages untill seq_num={}", last_seq_num);

    if last_seq_num == 0 {
        return;
    }

    loop {
        let bytes = client.get_tp_msg_from_wats().await.unwrap();
        let msg = unsafe { from_bytes::<tp::Message>(&bytes) };

        if msg.seq_num() == last_seq_num {
            return;
        }
    }
}

// Fetch messages buffer up to first Heartbeat
#[instrument(skip_all, fields(connection_id = client.tp_connection_id()))]
pub(super) async fn fetch_messages_until_heartbeat(client: &mut Client) {
    loop {
        let bytes = client.get_tp_msg_from_wats().await.unwrap();
        let msg = unsafe { from_bytes::<tp::Message>(&bytes) };
        if msg.msg_type() == tp::MsgType::Heartbeat {
            return;
        }
    }
}

pub(super) fn simple_order_add(
    instrument_id: u32,
    side: tp::OrderSide,
    price: i64,
    quantity: u64,
) -> tp::OrderAdd {
    tp::OrderAdd {
        header: tp::Header::new(tp::MsgType::OrderAdd),
        instrument_id,
        order_type: tp::OrderType::Limit,
        time_in_force: tp::TimeInForce::Day,
        side,
        price,
        quantity,
        mifid_fields: MifidFields {
            flags: MifidFlags::NONE,
            client: MifidField {
                short_code: 1234567,
                qualifier: PartyRoleQualifier::FirmOrLegalEntity,
            },
            executing_trader: MifidField {
                short_code: 1234567,
                qualifier: PartyRoleQualifier::Algorithm,
            },
            investment_decision_maker: MifidField {
                short_code: 1234567,
                qualifier: PartyRoleQualifier::NaturalPerson,
            },
        },
        ..Default::default()
    }
}

pub(super) fn simple_trade_capture_report_dual(
    instrument_id: tp::ElementId,
    trade_report_id: tp::TradeReportId,
    exec_type: tp::ExecType ,
    last_px: tp::Price,
    last_qty: tp::Quantity ,
    settlement_date: tp::Date
) -> tp::TradeCaptureReportDual {
    tp::TradeCaptureReportDual {
        header: tp::Header::new(tp::MsgType::TradeCaptureReportDual),
        instrument_id,
        trade_type: tp::TradeType::BlockTrade,
        algorithmic_trade_indicator: tp::AlgorithmicTradeIndicator::NonAlgorithmicTrade,
        trade_report_id,
        exec_type,
        last_qty,
        last_px,
        settlement_date,
        tcr_party_buy: tp::TcrParty {
            mifid_fields: MifidFields {
                flags: MifidFlags::NONE,
                client: MifidField {
                    short_code: 1,
                    qualifier: PartyRoleQualifier::NA,
                },
                executing_trader: MifidField {
                    short_code: 4,
                    qualifier: PartyRoleQualifier::Algorithm,
                },
                investment_decision_maker: MifidField {
                    short_code: 17,
                    qualifier: PartyRoleQualifier::NaturalPerson,
                },
            },
            ..Default::default()
        },
        tcr_party_sell: tp::TcrParty {
            mifid_fields: MifidFields {
                flags: MifidFlags::NONE,
                client: MifidField {
                    short_code: 1,
                    qualifier: PartyRoleQualifier::NA,
                },
                executing_trader: MifidField {
                    short_code: 4,
                    qualifier: PartyRoleQualifier::Algorithm,
                },
                investment_decision_maker: MifidField {
                    short_code: 17,
                    qualifier: PartyRoleQualifier::NaturalPerson,
                },
            },
            ..Default::default()
        },
        ..Default::default()
    }
}
