//! Running tests below require live WATS. Since all tests share the same WATS instance you have to provide
//! some isolation. It can be divided into communication level isolation and trade level isolation.
//!
//! By design, WATS allows only a single TCP connection per connection id and resends all messages when login in again.
//! To isolate the communication you need a separate connection_id for each client/test. This can be done with
//! `create_client_with_xxx` function macro.
//!
//! ```rust
//! let client = utils::create_client_with_omd(id, token);
//! ```

use crate::utils::{init_tracing, simple_order_add, simple_trade_capture_report_dual};
use chrono::{DateTime, Datelike, Local};
use utils::simple_order_modify;
use wats_access_client_rust::messages::{market_data, trading_port as tp};

mod config;
mod utils;

/// Test basic client login to [BIN] Trading Port and logout capabilities.
/// Use your credentials.
#[tokio::test]
#[ignore]
async fn basic_client_login_logout() {
    init_tracing();
    let wats_config = config::get_config().unwrap();

    let mut client = utils::create_client_with_omd(
        wats_config.connection_01_id(),
        wats_config.connection_01_token(),
        wats_config.connection_04_id(),
        wats_config.connection_04_token()
    )
    .await
    .unwrap();

    utils::fetch_messages(&mut client).await;

    client.logout().await.unwrap();
}

/// Put a limit buy order for TradableProductId=10005 with a price=150.00000000 and quantity=1000. Wait for
/// an ACK response from WATS and then put a matching sell order. Assert that a Trade response has expected value.
#[tokio::test]
#[ignore]
async fn limit_order() {
    init_tracing();
    let wats_config = config::get_config().unwrap();

    let mut client_a = utils::create_client_with_omd(
      wats_config.connection_01_id(),
      wats_config.connection_01_token(),
      wats_config.connection_04_id(),
      wats_config.connection_04_token()
  )
    .await
    .unwrap();
    utils::fetch_messages(&mut client_a).await;

    let mut client_b = utils::create_client_with_omd(
      wats_config.connection_02_id(),
      wats_config.connection_02_token(),
      wats_config.connection_04_id(),
      wats_config.connection_04_token()
  )
    .await
    .unwrap();
    utils::fetch_messages(&mut client_b).await;

    // Send Buy order to WATS
    client_a
        .submit_order(simple_order_add(
            wats_config.limit_order_instrument_id(),
            tp::OrderSide::Buy,
             // WATS uses integer value with eight decimal places for price
            100 * 100_000_000,
            1000,
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let buy_resp = client_a.get_order_add_resp().await.unwrap();
    assert_eq!(buy_resp.status, tp::OrderStatus::New);

    // Send Sell order to WATS
    client_b
        .submit_order(simple_order_add(
            wats_config.limit_order_instrument_id(),
            tp::OrderSide::Sell,
            100 * 100_000_000,
            1000,
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let sell_resp = client_b.get_order_add_resp().await.unwrap();
    assert_eq!(sell_resp.status, tp::OrderStatus::Filled);

    let trade_a = client_a.get_trade().await.unwrap();

    let trade_b = client_b.get_trade().await.unwrap();

    // Assert Trade response for client A
    assert_eq!({ trade_a.order_id }, { buy_resp.order_id });
    assert_eq!({ trade_a.price }, 100 * 100_000_000);
    assert_eq!({ trade_a.quantity }, 1000);
    assert_eq!({ trade_a.leaves_qty }, 0);

    // Assert Trade response for client B
    assert_eq!({ trade_b.order_id }, { sell_resp.order_id });
    assert_eq!({ trade_b.price }, 100 * 100_000_000);
    assert_eq!({ trade_b.quantity }, 1000);
    assert_eq!({ trade_b.leaves_qty }, 0);

    assert_eq!({ trade_a.id }, { trade_b.id });

    client_a.logout().await.unwrap();
    client_b.logout().await.unwrap();
}

/// Put a limit buy order for TradableProductId=10036 with a price=150.00000000 and quantity=1000. Then immediately send
/// OrderModify, without waiting for an ACK response from WATS. Assert that OrderModify was sent for the right OrderAdd.
///
/// The important bit here is prior knowledge of order_id that must be calculated on the client's side
/// without waiting for a WATS response. The order_id is formed from connection_id, session_id and seq_num
/// concatenated together bitwise:
///
/// <connection_id> <session_id> <seq_num>
///         <63-48>      <47-32>    <31-0>
///
/// `Client` keeps track of `connection_id`, `session_id` and `seq_num`, thus it's possible to calculate
/// order_id on the client's side. With the knowledge of `seq_num` of OrderAdd, it's possible to send OrderModify
/// for any matching message. Here, the `Client::mod_last_order` method first gets the `seq_num` of last OrderAdd, then
/// calculates order_id for it, and then finally send OrderModify with order_id matching last OrderAdd.
#[tokio::test]
#[ignore]
async fn modify_in_fly() {
    init_tracing();
    let wats_config = config::get_config().unwrap();

    let mut client = utils::create_client_with_omd(
      wats_config.connection_01_id(),
      wats_config.connection_01_token(),
      wats_config.connection_04_id(),
      wats_config.connection_04_token()
  )
    .await
    .unwrap();

    utils::fetch_messages(&mut client).await;

    // Send Buy order to WATS
    client
        .submit_order(simple_order_add(
            wats_config.modify_in_fly_instrument_id(),
            tp::OrderSide::Buy,
            100 * 100_000_000,
            1000,
        ))
        .await
        .unwrap();

    // First, get OrderAddResponse and skip any heartbeat
    let buy_resp = client.get_order_add_resp().await.unwrap();
    assert_eq!(buy_resp.status, tp::OrderStatus::New);

    // ModifyOrder #1
    let mod_buy_order = simple_order_modify(
        buy_resp.order_id,
        100 * 100_000_000,
        2000);
    client.modify_order(mod_buy_order, buy_resp.order_id).await.unwrap();

    // Then, get OrderModifyResponse and skip any heartbeat
    let mod_resp = client.get_order_mod_resp().await.unwrap();
    assert_eq!(mod_resp.status, tp::OrderStatus::New);
    // Assert that OrderModify was sent for right OrderAdd
    assert_eq!({ mod_resp.order_id }, { buy_resp.order_id });

    // ModifyOrder #2
    let mod_buy_order = simple_order_modify(
      buy_resp.order_id,
      101 * 100_000_000,
      2000);
    client.modify_order(mod_buy_order, buy_resp.order_id).await.unwrap();

    // Then, get OrderModifyResponse and skip any heartbeat
    let mod_resp = client.get_order_mod_resp().await.unwrap();
    assert_eq!(mod_resp.status, tp::OrderStatus::New);
    // Assert that OrderModify was sent for right OrderAdd
    assert_eq!({ mod_resp.order_id }, { buy_resp.order_id });

    // ModifyOrder #3
    let mod_buy_order = simple_order_modify(
      buy_resp.order_id,
      10001 * 100_000_000,
      2000000000);
    client.modify_order(mod_buy_order, buy_resp.order_id).await.unwrap();

    // Then, get OrderModifyResponse and skip any heartbeat
    let mod_resp = client.get_order_mod_resp().await.unwrap();
    assert_eq!(mod_resp.status, tp::OrderStatus::Rejected);
    // Assert that OrderModify was sent for right OrderAdd
    assert_eq!({ mod_resp.order_id }, { buy_resp.order_id });

    // ModifyOrder #4
    let mod_buy_order = simple_order_modify(
      buy_resp.order_id,
      100 * 100_000_000,
      1500);
    client.modify_order(mod_buy_order, buy_resp.order_id).await.unwrap();

    // Then, get OrderModifyResponse and skip any heartbeat
    let mod_resp = client.get_order_mod_resp().await.unwrap();
    assert_eq!(mod_resp.status, tp::OrderStatus::New);
    // Assert that OrderModify was sent for right OrderAdd
    assert_eq!({ mod_resp.order_id }, { buy_resp.order_id });

    // ModifyOrder #5
    let mut mod_buy_order = simple_order_modify(
      buy_resp.order_id,
      100 * 100_000_000,
      1500);
    mod_buy_order.expire = 9999999999000000000.into();
    client.modify_order(mod_buy_order, buy_resp.order_id).await.unwrap();

    // Then, get OrderModifyResponse and skip any heartbeat
    let mod_resp = client.get_order_mod_resp().await.unwrap();
    assert_eq!(mod_resp.status, tp::OrderStatus::Rejected);
    // Assert that OrderModify was sent for right OrderAdd
    assert_eq!({ mod_resp.order_id }, { buy_resp.order_id });

    // ModifyOrder #6
    let mod_buy_order = simple_order_modify(
      buy_resp.order_id,
      100 * 100_000_000,
      3000);
    client.modify_order(mod_buy_order, buy_resp.order_id).await.unwrap();

    // Then, get OrderModifyResponse and skip any heartbeat
    let mod_resp = client.get_order_mod_resp().await.unwrap();
    assert_eq!(mod_resp.status, tp::OrderStatus::New);
    // Assert that OrderModify was sent for right OrderAdd
    assert_eq!({ mod_resp.order_id }, { buy_resp.order_id });

    // Submit complimentary Sell order
    client
        .submit_order(simple_order_add(
            wats_config.modify_in_fly_instrument_id(),
            tp::OrderSide::Sell,
            100 * 100_000_000,
            3000,
        ))
        .await
        .unwrap();

    // Pull OrderAddResponse and skip any heartbeat
    let sell_resp = client.get_order_add_resp().await.unwrap();
    assert_eq!(sell_resp.status, tp::OrderStatus::Filled);

    // Grab resulting trades
    let _trade = client.get_trade().await.unwrap();
    let _trade = client.get_trade().await.unwrap();

    client.logout().await.unwrap();
}

/// Put a limit buy order with a bad TradableProductId (it does not exist in paramdb). Assert that WATS response
/// status is rejected and rejection reason is UnknownTradableProductId.
#[tokio::test]
#[ignore]
async fn bad_instrument_id() {
    init_tracing();
    let wats_config = config::get_config().unwrap();

    let mut client = utils::create_client_with_omd(
      wats_config.connection_01_id(),
      wats_config.connection_01_token(),
      wats_config.connection_04_id(),
      wats_config.connection_04_token()
  )
    .await
    .unwrap();

    utils::fetch_messages(&mut client).await;

    // Send Buy order to WATS
    client
        .submit_order(simple_order_add(
            wats_config.bad_instrument_id(),
            tp::OrderSide::Buy,
            100 * 100_000_000,
            1000,
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let buy_resp = client.get_order_add_resp().await.unwrap();
    assert_eq!(buy_resp.status, tp::OrderStatus::Rejected);
    assert_eq!(
        { buy_resp.reason },
        tp::OrderRejectionReason::UnknownInstrument
    );

    client.logout().await.unwrap();
}

/// Put a limit buy order for TradableProductId=10036 with a bad price=150.00000001 and quantity=1000.
/// Assert that WATS response status is rejected and rejection reason is InvalidPriceIncrement.
#[tokio::test]
#[ignore]
async fn bad_price() {
    init_tracing();
    let wats_config = config::get_config().unwrap();

    let mut client = utils::create_client_with_omd(
      wats_config.connection_01_id(),
      wats_config.connection_01_token(),
      wats_config.connection_04_id(),
      wats_config.connection_04_token()
  )
    .await
    .unwrap();

    utils::fetch_messages(&mut client).await;

    // Send Buy order to WATS
    client
        .submit_order(simple_order_add(
            wats_config.bad_price_instrument_id(),
            tp::OrderSide::Buy,
            100 * 100_000_001,
            1000,
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let buy_resp = client.get_order_add_resp().await.unwrap();
    assert_eq!(buy_resp.status, tp::OrderStatus::Rejected);
    assert_eq!(
        { buy_resp.reason },
        tp::OrderRejectionReason::InvalidPriceIncrement
    );

    client.logout().await.unwrap();
}

/// Put a limit buy order for TradableProductId=10036 with a price=150.00000000 and quantity=1000.
/// Wait for an ACK for OrderAdd and then send a CancelOrder for it. Assert that rejection reason in OrderCancelResponse
/// is NA and order_id matches OrderAddResponse.
#[tokio::test]
#[ignore]
async fn order_cancel_ok() {
    init_tracing();
    let wats_config = config::get_config().unwrap();

    let mut client = utils::create_client_with_omd(
      wats_config.connection_01_id(),
      wats_config.connection_01_token(),
      wats_config.connection_04_id(),
      wats_config.connection_04_token()
  )
    .await
    .unwrap();

    utils::fetch_messages(&mut client).await;

    // Send Buy order to WATS
    client
        .submit_order(simple_order_add(
            wats_config.order_cancel_ok_instrument_id(),
            tp::OrderSide::Buy,
            100 * 100_000_000,
            1000,
        ))
        .await
        .unwrap();

    // First, get OrderAddResponse and skip any heartbeat
    let buy_resp = client.get_order_add_resp().await.unwrap();
    assert_eq!(buy_resp.status, tp::OrderStatus::New);

    let cancel_buy_order = tp::OrderCancel {
        header: tp::Header::new(tp::MsgType::OrderCancel),
        order_id: buy_resp.order_id,
        mifid_fields: tp::MifidFields {
          flags: tp::MifidFlags::NONE,
          client: tp::MifidField {
              short_code: 1,
              qualifier: tp::PartyRoleQualifier::NA,
          },
          executing_trader: tp::MifidField {
              short_code: 4,
              qualifier: tp::PartyRoleQualifier::Algorithm,
          },
          investment_decision_maker: tp::MifidField {
              short_code: 17,
              qualifier: tp::PartyRoleQualifier::NaturalPerson,
          },
        }
    };
    client.cancel_order(cancel_buy_order).await.unwrap();

    // Then, get OrderCancelResponse and skip any heartbeat
    let cancel_resp = client.get_order_cancel_resp().await.unwrap();
    assert_eq!({ cancel_resp.reason }, tp::OrderRejectionReason::NA);
    assert_eq!({ cancel_resp.order_id }, { buy_resp.order_id });

    client.logout().await.unwrap();
}

/// Put a limit buy order for TradableProductId=10004 with a price=150.00000000 and quantity=1000.
/// Wait for an ACK for OrderAdd and then send a CancelOrder with bad order_id. Assert that
/// rejection reason in OrderCancelResponse is UnknownOrderRefId.
#[tokio::test]
#[ignore]
async fn order_cancel_bad_order_id() {
    init_tracing();
    let wats_config = config::get_config().unwrap();

    let mut client = utils::create_client_with_omd(
      wats_config.connection_01_id(),
      wats_config.connection_01_token(),
      wats_config.connection_04_id(),
      wats_config.connection_04_token()
  )
    .await
    .unwrap();

    utils::fetch_messages(&mut client).await;

    // Send Buy order to WATS
    client
        .submit_order(simple_order_add(
            wats_config.order_cancel_bad_instrument_id(),
            tp::OrderSide::Buy,
            100 * 100_000_000,
            1000,
        ))
        .await
        .unwrap();

    // First, get OrderAddResponse and skip any heartbeat
    let buy_resp = client.get_order_add_resp().await.unwrap();
    assert_eq!(buy_resp.status, tp::OrderStatus::New);

    let cancel_buy_order = tp::OrderCancel {
        header: tp::Header::new(tp::MsgType::OrderCancel),
        order_id: tp::OrderId::new(0, 0, 0, 0).unwrap(),
        mifid_fields: tp::MifidFields {
          flags: tp::MifidFlags::NONE,
          client: tp::MifidField {
              short_code: 1,
              qualifier: tp::PartyRoleQualifier::NA,
          },
          executing_trader: tp::MifidField {
              short_code: 4,
              qualifier: tp::PartyRoleQualifier::Algorithm,
          },
          investment_decision_maker: tp::MifidField {
              short_code: 17,
              qualifier: tp::PartyRoleQualifier::NaturalPerson,
          },
        }
    };
    client.cancel_order(cancel_buy_order).await.unwrap();

    // Then, get OrderCancelResponse and skip any heartbeat
    let mod_resp = client.get_order_cancel_resp().await.unwrap();
    assert_eq!({ mod_resp.reason }, tp::OrderRejectionReason::UnknownOrder);

    client.logout().await.unwrap();
}

/// Create a limit buy order for TradableProductId=10004 with a price=150.00000001 that violates tickTableEntryTickSize.
/// Use [`Client::submit_order_checked`] method to validate [`tp::OrderAdd`] prior sending it to WATS Trading Port.
/// Then create an OrderAdd with correct price and assert Ack response from WATS after sending it with
/// [`Client::submit_order_checked`].
#[tokio::test]
#[ignore]
async fn price_violates_tick_table() {
    init_tracing();
    let wats_config = config::get_config().unwrap();

    let mut client = utils::create_client_with_omd(
      wats_config.connection_01_id(),
      wats_config.connection_01_token(),
      wats_config.connection_04_id(),
      wats_config.connection_04_token()
  )
    .await
    .unwrap();

    utils::fetch_messages(&mut client).await;

    let err = client
        .submit_order_checked(simple_order_add(
            wats_config.violates_tick_table_instrument_id(),
            tp::OrderSide::Buy,
            100 * 100_000_001,
            1000,
        ))
        .await
        .unwrap_err();
    assert_eq!(
        err.to_string(),
        "PriceValidationError::TickSizeMismatch: 1000000"
    );

    // Send Buy order to WATS
    client
        .submit_order_checked(simple_order_add(
            wats_config.violates_tick_table_instrument_id(),
            tp::OrderSide::Buy,
            100 * 100_000_000,
            1000,
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let buy_resp = client.get_order_add_resp().await.unwrap();
    assert_eq!(buy_resp.status, tp::OrderStatus::New);

    client.logout().await.unwrap();
}

/// Put a limit buy order for TradableProductId=10004 with a price=150.00000000 and quantity=1000.
/// Pull a message from the Online Market Data, there shloud be no gap.
#[tokio::test]
#[ignore]
async fn no_gap_in_omd_msg() {
    init_tracing();
    let wats_config = config::get_config().unwrap();

    let mut client = utils::create_client_with_omd(
      wats_config.connection_01_id(),
      wats_config.connection_01_token(),
      wats_config.connection_04_id(),
      wats_config.connection_04_token()
  )
    .await
    .unwrap();

    utils::fetch_messages(&mut client).await;

    // Send Buy order to WATS
    client
        .submit_order_checked(simple_order_add(
            wats_config.gap_in_omd_instrument_id(),
            tp::OrderSide::Buy,
            100 * 100_000_000,
            1000,
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let buy_resp = client.get_order_add_resp().await.unwrap();
    assert_eq!(buy_resp.status, tp::OrderStatus::New);

    // There is no gap in the OMD
    let gap = client.pull_msg_from_omd().await.unwrap();
    assert!(gap.is_none());

    client.logout().await.unwrap();
}

/// Request a replay of all OMD messages
#[tokio::test]
#[ignore]
async fn request_omd_replay() {
    init_tracing();
    let wats_config = config::get_config().unwrap();

    let mut client = utils::create_client_with_omd(
      wats_config.connection_01_id(),
      wats_config.connection_01_token(),
      wats_config.connection_04_id(),
      wats_config.connection_04_token()
  )
    .await
    .unwrap();

    utils::fetch_messages(&mut client).await;

    #[allow(clippy::reversed_empty_ranges)]
    client.request_omd_replay(1..0).await.unwrap();

    client.logout().await.unwrap();
}

/// Put buy and sell orders. Assert correct PriceLevelSnapshot
#[tokio::test]
#[ignore]
async fn bbo_price_level() {
    init_tracing();
    let wats_config = config::get_config().unwrap();

    // Let's trade

    // Create trading port client
    let mut client = utils::create_client_with_bbo(
      wats_config.connection_01_id(),
      wats_config.connection_01_token(),
      wats_config.connection_03_id(),
      wats_config.connection_03_token()
    )
    .await
    .unwrap();

    utils::fetch_messages(&mut client).await;

    // Send Buy order to WATS with price=99
    client
        .submit_order(simple_order_add(
            wats_config.bbo_instrument_id(),
            tp::OrderSide::Buy,
            99 * 100_000_000,
            1000,
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let resp = client.get_order_add_resp().await.unwrap();
    assert_eq!(resp.status, tp::OrderStatus::New);

    // Send Buy order to WATS with price=98
    client
        .submit_order(simple_order_add(
            wats_config.bbo_instrument_id(),
            tp::OrderSide::Buy,
            98 * 100_000_000,
            1000,
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let resp = client.get_order_add_resp().await.unwrap();
    assert_eq!(resp.status, tp::OrderStatus::New);

    // Send Sell order to WATS with price=101
    client
        .submit_order(simple_order_add(
            wats_config.bbo_instrument_id(),
            tp::OrderSide::Sell,
            101 * 100_000_000,
            1000,
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let resp = client.get_order_add_resp().await.unwrap();
    assert_eq!(resp.status, tp::OrderStatus::New);

    // Send Sell order to WATS with price=102
    client
        .submit_order(simple_order_add(
            wats_config.bbo_instrument_id(),
            tp::OrderSide::Sell,
            102 * 100_000_000,
            1000,
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let resp = client.get_order_add_resp().await.unwrap();
    assert_eq!(resp.status, tp::OrderStatus::New);

    // Loop over BBO
    let (buy, sell) = loop {

      // Pull last OrderAdd message from Online Market Data
      if let Some(gap) = client.pull_msg_from_omd().await.unwrap() {
        client.request_omd_replay(gap).await.unwrap();
      }

      let price_level_snapshot = client
        .omd_messages()
        .filter_map(|msg| {
          if msg.msg_type() == market_data::MsgType::PriceLevelSnapshot {
              Some(unsafe { msg.price_level_snapshot })
          } else {
              None
          }
        })
        .last()
        .unwrap();

      let buy: Vec<_> = price_level_snapshot
          .buy
          .iter()
          .filter(|price_lvl| price_lvl.order_count > 0)
          .cloned()
          .collect();

      let sell: Vec<_> = price_level_snapshot
          .sell
          .iter()
          .filter(|price_lvl| price_lvl.order_count > 0)
          .cloned()
          .collect();

      if buy.len() == 2 && sell.len() == 2 {
          break (buy, sell);
      }
    };

    client.logout().await.unwrap();

    // Assert correct price level for buy
    assert_eq!(2, buy.len());
    assert_eq!(99 * 100_000_000, { buy[0].price });
    assert_eq!(98 * 100_000_000, { buy[1].price });

    // Assert correct price level for sell
    assert_eq!(2, sell.len());
    assert_eq!(101 * 100_000_000, { sell[0].price });
    assert_eq!(102 * 100_000_000, { sell[1].price });
}

/// Put a series of orders from `tests/orders.yml` file.
/// Use `ORDER_SET` environment variable to specify order set to use.
/// If not set defaults to first order set in file.
#[tokio::test]
#[ignore]
async fn orders_in_phase() {
    init_tracing();

    let orders: Vec<tp::OrderAdd> = config::get_orders()
        .unwrap()
        .into_iter()
        .map(Into::into)
        .collect();

    let wats_config = config::get_config().unwrap();

    let mut client = utils::create_client_with_omd(
      wats_config.connection_01_id(),
      wats_config.connection_01_token(),
      wats_config.connection_04_id(),
      wats_config.connection_04_token()
  )
    .await
    .unwrap();

    utils::fetch_messages(&mut client).await;

    for order in orders {
        // Send order to WATS
        client.submit_order(order).await.unwrap();

        // Get OrderAddResponse and skip any heartbeat
        let resp = client.get_order_mod_resp_ignore_other().await.unwrap();
        assert!(!matches!(
            resp.status,
            tp::OrderStatus::Cancelled | tp::OrderStatus::Rejected
        ))
    }

    client.wait_for_heartbeat().await.unwrap();

    client.logout().await.unwrap();
}

/// Submit OrderAdds that:
/// 1. Fits PTC
/// 2. Violates PTC (volume above max volume)
/// 3. Violates PTC (value above max value)
#[tokio::test]
#[ignore]
async fn ptc() {
    init_tracing();

    let wats_config = config::get_config().unwrap();

    let mut client = utils::create_client_with_omd(
      wats_config.connection_01_id(),
      wats_config.connection_01_token(),
      wats_config.connection_04_id(),
      wats_config.connection_04_token()
  )
    .await
    .unwrap();

    utils::fetch_messages(&mut client).await;

    let market_data::Instrument {
        instrument_id,
        product_type,
        pre_trade_check_min_quantity,
        pre_trade_check_max_quantity,
        pre_trade_check_max_value,
        ..
    } = client
        .omd_messages()
        .filter_map(|msg| {
            if msg.msg_type() == market_data::MsgType::Instrument {
                Some(unsafe { msg.instrument })
            } else {
                None
            }
        })
        .find(|msg| msg.instrument_id == wats_config.ptc_instrument_id())
        .unwrap();

    assert_eq!(
        product_type,
        market_data::ProductType::Equity
    );

    let price = 100 * 100_000_000;
    let max_price = (pre_trade_check_max_value
        / i64::try_from(pre_trade_check_max_quantity).unwrap())
        * 100_000_000;
    let quantity = (pre_trade_check_max_quantity - pre_trade_check_min_quantity) / 2;

    // ***************************************
    //            OrderAdd fits PTC
    // ***************************************
    client
        .submit_order(simple_order_add(
            instrument_id,
            tp::OrderSide::Buy,
            price,
            1000,
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let buy_resp = client.get_order_add_resp().await.unwrap();
    assert_eq!(buy_resp.status, tp::OrderStatus::New);

    // *****************************************************************
    //           OrderAdd violates PTC (volume out of bounds)
    // *****************************************************************
    client
        .submit_order(simple_order_add(
            instrument_id,
            tp::OrderSide::Buy,
            price,
            quantity,
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let buy_resp = client.get_order_add_resp().await.unwrap();
    assert_eq!(buy_resp.status, tp::OrderStatus::Rejected);
    assert_eq!(
        { buy_resp.reason },
        tp::OrderRejectionReason::OrderValueMustBeLowerThanMaximumValue
    );

    // *****************************************************************
    //            OrderAdd violates PTC (quantity out of bounds)
    // *****************************************************************
    client
        .submit_order(simple_order_add(
            instrument_id,
            tp::OrderSide::Buy,
            price,
            pre_trade_check_max_quantity + 1,
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let buy_resp = client.get_order_add_resp().await.unwrap();
    assert_eq!(buy_resp.status, tp::OrderStatus::Rejected);
    assert_eq!(
        { buy_resp.reason },
        tp::OrderRejectionReason::OrderQuantityMustBeLowerThanMaximumQuantity
    );

    // *****************************************************************
    //            OrderAdd violates PTC (price collar)
    // *****************************************************************
    client
        .submit_order(simple_order_add(
            instrument_id,
            tp::OrderSide::Buy,
            max_price,
            pre_trade_check_max_quantity,
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let buy_resp = client.get_order_add_resp().await.unwrap();
    assert_eq!(buy_resp.status, tp::OrderStatus::Rejected);
    assert_eq!(
        { buy_resp.reason },
        tp::OrderRejectionReason::PriceAboveHighCollar
    );

    client.logout().await.unwrap();
}

/// Build order book based on Online Market Data
#[tokio::test]
#[ignore]
async fn order_book_based_on_market_data() {
    init_tracing();

    let wats_config = config::get_config().unwrap();

    let mut client_a = utils::create_client_with_omd(
      wats_config.connection_01_id(),
      wats_config.connection_01_token(),
      wats_config.connection_04_id(),
      wats_config.connection_04_token()
  )
    .await
    .unwrap();
    utils::fetch_messages_until_heartbeat(&mut client_a).await;

    let mut client_b = utils::create_client_with_omd(
      wats_config.connection_02_id(),
      wats_config.connection_02_token(),
      wats_config.connection_04_id(),
      wats_config.connection_04_token()
  )
    .await
    .unwrap();
    utils::fetch_messages_until_heartbeat(&mut client_b).await;

    // Send Buy order to WATS with price=100
    client_a
        .submit_order(simple_order_add(
            wats_config.order_book_based_on_market_data_instrument_id(),
            tp::OrderSide::Buy,
            100 * 100_000_000,
            5000,
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let buy_resp = client_a.get_order_add_resp().await.unwrap();
    assert_eq!(buy_resp.status, tp::OrderStatus::New);

    // Pull last OrderAdd message from Online Market Data
    if let Some(gap) = client_a.pull_msg_from_omd().await.unwrap() {
        client_a.request_omd_replay(gap).await.unwrap();
    }

    let last_md_order_add = client_a
        .omd_messages()
        .filter_map(|msg| {
            if msg.msg_type() == market_data::MsgType::OrderAdd {
                Some(unsafe { msg.order_add })
            } else {
                None
            }
        })
        .last()
        .unwrap();

    // Assert that OrderAdd pulled from OMD matches the one sent to binary trading port
    assert_eq!({ buy_resp.public_order_id }, {
        last_md_order_add.public_order_id
    });
    assert_eq!(
        wats_config.order_book_based_on_market_data_instrument_id(),
        { last_md_order_add.instrument_id }
    );
    assert_eq!(tp::OrderSide::Buy as u8, last_md_order_add.side as u8);
    assert_eq!(100 * 100_000_000, { last_md_order_add.price });
    assert_eq!(5000, { last_md_order_add.quantity });

    // Submit OrderModify for previous OrderAdd
    let mod_buy_order = simple_order_modify(
      buy_resp.order_id,
      100 * 100_000_000,
      13000);
    client_a.modify_order(mod_buy_order, buy_resp.order_id).await.unwrap();

    // Get OrderModifyResponse and skip any heartbeat
    let mod_resp = client_a.get_order_mod_resp().await.unwrap();
    assert_eq!(mod_resp.status, tp::OrderStatus::New);
    // Assert that OrderModify was sent for right OrderAdd
    assert_eq!({ mod_resp.order_id }, { buy_resp.order_id });

    // Pull last OrderModify message from Online Market Data
    if let Some(gap) = client_a.pull_msg_from_omd().await.unwrap() {
        client_a.request_omd_replay(gap).await.unwrap();
    }
    let last_md_order_modify = client_a
        .omd_messages()
        .filter_map(|msg| {
            if msg.msg_type() == market_data::MsgType::OrderModify {
                Some(unsafe { msg.order_modify })
            } else {
                None
            }
        })
        .last()
        .unwrap();

    // Assert that OrderModify pulled from OMD matches OrderAdd and OrderModify sent to binary trading port
    assert_eq!({ mod_resp.order_id }, { buy_resp.order_id });
    assert_eq!(100 * 100_000_000, { last_md_order_modify.price });
    assert_eq!(13000, { last_md_order_modify.quantity });

    // Send Sell order that partially matches previous OrderAdd (Buy)
    client_b
        .submit_order(simple_order_add(
            wats_config.order_book_based_on_market_data_instrument_id(),
            tp::OrderSide::Sell,
            100 * 100_000_000,
            5000,
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let sell_resp = client_b.get_order_add_resp().await.unwrap();
    assert_eq!(sell_resp.status, tp::OrderStatus::Filled);

    // Get Trade for Client A and skip any heartbeat
    let trade_a = client_a.get_trade().await.unwrap();
    assert_eq!({ trade_a.order_id }, { buy_resp.order_id });
    assert_eq!(100 * 100_000_000, { trade_a.price });
    assert_eq!(5000, { trade_a.quantity });
    assert_eq!(8000, { trade_a.leaves_qty });

    // Get Trade for Client B and skip any heartbeat
    let trade_b = client_b.get_trade().await.unwrap();
    assert_eq!({ trade_b.order_id }, { sell_resp.order_id });
    assert_eq!(100 * 100_000_000, { trade_b.price });
    assert_eq!(5000, { trade_b.quantity });
    assert_eq!(0, { trade_b.leaves_qty });

    // Pull last OrderExecute and Trade messages from Online Market Data
    if let Some(gap) = client_a.pull_msg_from_omd().await.unwrap() {
        client_a.request_omd_replay(gap).await.unwrap();
    }
    let last_md_order_execute_buy = client_a
        .omd_messages()
        .filter_map(|msg| {
            if msg.msg_type() == market_data::MsgType::OrderExecute {
                Some(unsafe { msg.order_execute })
            } else {
                None
            }
        })
        .last()
        .unwrap();
    assert_eq!({ last_md_order_execute_buy.public_order_id }, {
        buy_resp.public_order_id
    });

    if let Some(gap) = client_a.pull_msg_from_omd().await.unwrap() {
        client_a.request_omd_replay(gap).await.unwrap();
    }
    let last_md_order_execute_sell = client_a
        .omd_messages()
        .filter_map(|msg| {
            if msg.msg_type() == market_data::MsgType::OrderExecute {
                Some(unsafe { msg.order_execute })
            } else {
                None
            }
        })
        .last()
        .unwrap();
    assert_eq!({ last_md_order_execute_sell.public_order_id }, {
        sell_resp.public_order_id
    });

    if let Some(gap) = client_a.pull_msg_from_omd().await.unwrap() {
        client_a.request_omd_replay(gap).await.unwrap();
    }
    let _last_md_trade = client_a
        .omd_messages()
        .filter_map(|msg| {
            if msg.msg_type() == market_data::MsgType::Trade {
                Some(unsafe { msg.order_execute })
            } else {
                None
            }
        })
        .last()
        .unwrap();

    // Cancel remaining OrderAdd
    client_a
        .cancel_order(tp::OrderCancel {
            header: tp::Header::new(tp::MsgType::OrderCancel),
            order_id: buy_resp.order_id,
            mifid_fields: tp::MifidFields {
              flags: tp::MifidFlags::NONE,
              client: tp::MifidField {
                  short_code: 1,
                  qualifier: tp::PartyRoleQualifier::NA,
              },
              executing_trader: tp::MifidField {
                  short_code: 4,
                  qualifier: tp::PartyRoleQualifier::Algorithm,
              },
              investment_decision_maker: tp::MifidField {
                  short_code: 17,
                  qualifier: tp::PartyRoleQualifier::NaturalPerson,
              },
            }
        })
        .await
        .unwrap();

    // Get OrderCancelResponse and skip any heartbeat
    let cancel_resp = client_a.get_order_cancel_resp().await.unwrap();
    assert_eq!({ cancel_resp.reason }, tp::OrderRejectionReason::NA);
    assert_eq!({ cancel_resp.order_id }, { buy_resp.order_id });

    // Get OrderDelete from OMD after cancelling order
    if let Some(gap) = client_a.pull_msg_from_omd().await.unwrap() {
        client_a.request_omd_replay(gap).await.unwrap();
    }
    let last_md_order_delete = client_a
        .omd_messages()
        .filter_map(|msg| {
            if msg.msg_type() == market_data::MsgType::OrderDelete {
                Some(unsafe { msg.order_delete })
            } else {
                None
            }
        })
        .last()
        .unwrap();
    assert_eq!({ last_md_order_delete.public_order_id }, {
        buy_resp.public_order_id
    });

    client_a.logout().await.unwrap();
    client_b.logout().await.unwrap();
}

fn trade_report_id_from_str(id: &str) -> tp::TradeReportId {
    let mut trade_report_id: tp::TradeReportId = tp::TradeReportId::default();
    let len = id.len();
    trade_report_id
        .split_at_mut(len)
        .0
        .copy_from_slice(id.as_bytes().split_at(len).0);
    trade_report_id
}

fn settlement_date(d: DateTime<Local>) -> u32 {
    (d.year() as u32) * 10000 + d.month() * 100 + d.day()
}

/// Trade Capture Report Dual
#[tokio::test]
#[ignore]
async fn block_dual() {
    let wats_config = config::get_config().unwrap();

    let mut client = utils::create_client_with_omd(
      wats_config.connection_01_id(),
      wats_config.connection_01_token(),
      wats_config.connection_04_id(),
      wats_config.connection_04_token()
  )
    .await
    .unwrap();

    utils::fetch_messages(&mut client).await;

    // There has to be an underlying transaction of reference instrument
    // for the request for Trade Report to work
    client
        .submit_order(simple_order_add(
            wats_config.block_dual_reference(),
            tp::OrderSide::Buy,
            100 * 100_000_000,
            1000,
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let buy_resp = client.get_order_add_resp().await.unwrap();
    assert_eq!(buy_resp.status, tp::OrderStatus::New);

    client
        .submit_order(simple_order_add(
            wats_config.block_dual_reference(),
            tp::OrderSide::Sell,
            100 * 100_000_000,
            1000,
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let sell_resp = client.get_order_add_resp().await.unwrap();
    assert_eq!(sell_resp.status, tp::OrderStatus::Filled);

    // Now request Trade Capture Report
    client
        .submit_trade_capture_report_dual(simple_trade_capture_report_dual(
            wats_config.block_dual(),
            trade_report_id_from_str("ID0000"),
            tp::ExecType::NA,
            100 * 100_000_000,
            10,
            settlement_date(Local::now() + chrono::Duration::try_days(1).unwrap()),
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let tcrd_resp = client.get_trade_capture_report_dual_resp().await.unwrap();
    assert_eq!(tcrd_resp.status, tp::TcrStatus::Accepted);

    client
        .submit_trade_capture_report_dual(simple_trade_capture_report_dual(
            wats_config.block_dual(),
            trade_report_id_from_str("ID0001"),
            tp::ExecType::Trade,
            100 * 100_000_000,
            10,
            settlement_date(Local::now() + chrono::Duration::try_days(1).unwrap()),
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let tcrd_resp = client.get_trade_capture_report_dual_resp().await.unwrap();
    assert_eq!(tcrd_resp.status, tp::TcrStatus::Rejected);

    client
        .submit_trade_capture_report_dual(simple_trade_capture_report_dual(
            wats_config.block_dual(),
            trade_report_id_from_str("ID0002"),
            tp::ExecType::NA,
            100 * 100_000_000,
            10,
            settlement_date(Local::now() + chrono::Duration::try_days(1).unwrap()),
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let tcrd_resp = client.get_trade_capture_report_dual_resp().await.unwrap();
    assert_eq!(tcrd_resp.status, tp::TcrStatus::Accepted);

    client
        .submit_trade_capture_report_dual(simple_trade_capture_report_dual(
            wats_config.block_dual(),
            trade_report_id_from_str("ID0002"),
            tp::ExecType::NA,
            100 * 100_000_000,
            10,
            settlement_date(Local::now() + chrono::Duration::try_days(1).unwrap()),
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let tcrd_resp = client.get_trade_capture_report_dual_resp().await.unwrap();
    assert_eq!(tcrd_resp.status, tp::TcrStatus::Rejected);

    // client
    //     .submit_trade_capture_report_dual(simple_trade_capture_report_dual(
    //         wats_config.block_dual(),
    //         trade_report_id_from_str("ID0003"),
    //         tp::ExecType::NA,
    //         100 * 100_000_000,
    //         1,
    //         settlement_date(Local::now() + chrono::Duration::try_days(1).unwrap()),
    //     ))
    //     .await
    //     .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    // let tcrd_resp = client.get_trade_capture_report_dual_resp().await.unwrap();
    // assert_eq!(tcrd_resp.status, tp::TcrStatus::Accepted);

    client
        .submit_trade_capture_report_dual(simple_trade_capture_report_dual(
            wats_config.block_dual(),
            trade_report_id_from_str("ID0004"),
            tp::ExecType::NA,
            100 * 100_000_000,
            1,
            settlement_date(Local::now() - chrono::Duration::try_days(1).unwrap()),
        ))
        .await
        .unwrap();

    // Get OrderAddResponse and skip any heartbeat
    let tcrd_resp = client.get_trade_capture_report_dual_resp().await.unwrap();
    assert_eq!(tcrd_resp.status, tp::TcrStatus::Rejected);

    client.logout().await.unwrap();
}
