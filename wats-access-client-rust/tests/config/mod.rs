//! Example client configuration for test purposes.

use anyhow::{anyhow, Context, Result};
use serde::Deserialize;
use std::collections::HashMap;
use std::net::Ipv4Addr;
use std::str::FromStr;
use std::fs::File;
use wats_access_client_rust::messages::trading_port::{self as tp};

const CONFIG_PATH: &str = concat!(env!("CARGO_MANIFEST_DIR"), "/tests/wats.yml");
const ORDERS_PATH: &str = concat!(env!("CARGO_MANIFEST_DIR"), "/tests/orders.yml");

#[derive(Deserialize)]
#[serde(deny_unknown_fields)]
pub(super) struct Order {
    instrument_id: u32,
    side: tp::OrderSide,
    quantity: u64,
    price: i64,
}

impl From<Order> for tp::OrderAdd {
    fn from(order: Order) -> Self {
        crate::utils::simple_order_add(
            order.instrument_id,
            order.side,
            order.price * 100_000_000, // Mind that WATS uses integer value with eight decimal places for price
            order.quantity,
        )
    }
}

/// Loads and deserialize orders file.
pub(super) fn get_orders() -> Result<Vec<Order>> {
    let file = File::open(ORDERS_PATH).context("Failed opening orders file")?;
    let mut order_set_file = serde_yaml::from_reader::<_, HashMap<String, _>>(file)
        .context("Failed deserializing orders")?;

    let order_set = match std::env::var("ORDER_SET") {
        Ok(order_set_key) => order_set_file
            .remove(&order_set_key)
            .ok_or_else(|| anyhow!("No `{}` key in orders file", order_set_key))?,
        Err(_) => {
            tracing::debug!(
                "`ORDER_SET` environment variable not set. Default to first key in orders map"
            );
            order_set_file
                .into_iter()
                .next()
                .ok_or_else(|| anyhow!("Orders file is empty"))?
                .1
        }
    };

    Ok(order_set)
}

/// Loads and deserialize config file.
pub(super) fn get_config() -> Result<Config> {
    let config = File::open(CONFIG_PATH).context("Failed opening config file")?;
    serde_yaml::from_reader(config).context("Failed deserializing client configuration")
}

#[derive(Debug, Deserialize)]
#[serde(deny_unknown_fields)]
pub(super) struct Config {
    /// Trading Port [BIN] endpoint
    trading_port: Endpoint,

    /// Command Port endpoint
    //command_port: Endpoint,

    /// Online Market Data Snapshot endpoint
    omd_snapshot: Endpoint,

    /// Online Market Data multicast group address
    omd_stream: Endpoint,

    /// Online Market Data replay endpoint
    omd_replay: Endpoint,

    /// Best Bid Offer replay endpoint
    bbo_snapshot: Endpoint,

    /// Best Bid Offer multicast group address
    bbo_stream: Endpoint,

    /// Best Bid Offer replay endpoint
    bbo_replay: Endpoint,

    multicast_interface: String,

    connection_01: Connection,

    connection_02: Connection,

    connection_03: Connection,

    connection_04: Connection,

    product_bucket: ProductBucket,
}

#[derive(Debug, Deserialize)]
#[serde(deny_unknown_fields)]
struct Endpoint {
    /// Either an IPv4 address or hostname to connect to
    host: String,

    /// A port number to connect to
    port: u16,
}

#[derive(Debug, Deserialize)]
#[serde(deny_unknown_fields)]
struct Connection {
    /// Connection ID
    id: u16,

    /// Login token
    token: String,
}

#[derive(Debug, Deserialize)]
#[serde(deny_unknown_fields)]
struct ProductBucket {
    /// A product for the limit order test
    limit_order_instrument_id: u32,

    /// A product for the modification in-fly test
    modify_in_fly_instrument_id: u32,

    /// A product for the bad tradable product id test
    bad_instrument_id: u32,

    /// A product for the bad_price test
    bad_price_instrument_id: u32,

    /// A product for the order_cancel_ok test
    order_cancel_ok_instrument_id: u32,

    /// A product for the order_cancel_bad test
    order_cancel_bad_instrument_id: u32,

    /// A product for the violates_tick_table test
    violates_tick_table_instrument_id: u32,

    /// A product for the bbo test
    bbo_instrument_id: u32,

    /// A product for the no_gap_in_omd test
    gap_in_omd_instrument_id: u32,

    /// A product for the PTC test
    ptc_instrument_id: u32,

    /// A product for the Order Book test
    order_book_based_on_market_data_instrument_id: u32,

    /// A product TradeCaptureReportDual
    block_dual: u32,

    /// A reference product TradeCaptureReportDual
    block_dual_reference: u32,
  }

impl Config {
    /// Trading Port peer socket address
    pub(super) fn tp_addr(&self) -> impl tokio::net::ToSocketAddrs + '_ {
        (self.trading_port.host.as_str(), self.trading_port.port)
    }

    /// Snapshot peer socket address
    pub(super) fn omd_snap_addr(&self) -> impl tokio::net::ToSocketAddrs + '_ {
        (self.omd_snapshot.host.as_str(), self.omd_snapshot.port)
    }

    /// Online Market Data multicast group address
    pub(super) fn omd_addr(&self) -> impl tokio::net::ToSocketAddrs + '_ {
        (self.omd_stream.host.as_str(), self.omd_stream.port)
    }

    /// Online Market Data replay endpoint
    pub(super) fn omd_replay_addr(&self) -> impl tokio::net::ToSocketAddrs + '_ {
        (self.omd_replay.host.as_str(), self.omd_replay.port)
    }

    /// Snapshot peer socket address
    pub(super) fn bbo_snap_addr(&self) -> impl tokio::net::ToSocketAddrs + '_ {
        (self.bbo_snapshot.host.as_str(), self.bbo_snapshot.port)
    }

    /// Online Market Data multicast group address
    pub(super) fn bbo_addr(&self) -> impl tokio::net::ToSocketAddrs + '_ {
        (self.bbo_stream.host.as_str(), self.bbo_stream.port)
    }

    /// BBO replay endpoint
    pub(super) fn bbo_replay_addr(&self) -> impl tokio::net::ToSocketAddrs + '_ {
        (self.bbo_replay.host.as_str(), self.bbo_replay.port)
    }

    /// Multicast interface
    pub(super) fn multicast_interface(&self) -> Ipv4Addr {
        Ipv4Addr::from_str(self.multicast_interface.as_str()).unwrap()
    }

    /// Connection 1 id
    pub(super) fn connection_01_id(&self) -> u16 {
        self.connection_01.id
    }

    /// Connection 1 token
    pub(super) fn connection_01_token(&self) -> [u8; 8] {
        let mut token_arr = [0u8; 8];
        token_arr[..self.connection_01.token.len()]
            .copy_from_slice(self.connection_01.token.as_bytes());
        token_arr
    }

    /// Connection 2 id
    pub(super) fn connection_02_id(&self) -> u16 {
        self.connection_02.id
    }

    /// Connection 2 token
    pub(super) fn connection_02_token(&self) -> [u8; 8] {
        let mut token_arr = [0u8; 8];
        token_arr[..self.connection_02.token.len()]
            .copy_from_slice(self.connection_02.token.as_bytes());
        token_arr
    }

    /// Connection 3 id
    pub(super) fn connection_03_id(&self) -> u16 {
      self.connection_03.id
  }

  /// Connection 3 token
  pub(super) fn connection_03_token(&self) -> [u8; 8] {
      let mut token_arr = [0u8; 8];
      token_arr[..self.connection_03.token.len()]
          .copy_from_slice(self.connection_03.token.as_bytes());
      token_arr
  }

    /// Connection 4 id
    pub(super) fn connection_04_id(&self) -> u16 {
      self.connection_04.id
  }

  /// Connection 4 token
  pub(super) fn connection_04_token(&self) -> [u8; 8] {
      let mut token_arr = [0u8; 8];
      token_arr[..self.connection_04.token.len()]
          .copy_from_slice(self.connection_04.token.as_bytes());
      token_arr
  }

  /// A product for the limit_order test
    pub(super) fn limit_order_instrument_id(&self) -> u32 {
        self.product_bucket.limit_order_instrument_id as u32
    }

    /// A product for the modify_in_fly test
    pub(super) fn modify_in_fly_instrument_id(&self) -> u32 {
        self.product_bucket.modify_in_fly_instrument_id as u32
    }

    /// A product for the bad_instrument_id test
    pub(super) fn bad_instrument_id(&self) -> u32 {
        self.product_bucket.bad_instrument_id as u32
    }

    /// A product for the bad_price test
    pub(super) fn bad_price_instrument_id(&self) -> u32 {
        self.product_bucket.bad_price_instrument_id as u32
    }

    /// A product for the order_cancel_ok test
    pub(super) fn order_cancel_ok_instrument_id(&self) -> u32 {
        self.product_bucket.order_cancel_ok_instrument_id as u32
    }

    /// A product for the order_cancel_bad test
    pub(super) fn order_cancel_bad_instrument_id(&self) -> u32 {
        self.product_bucket.order_cancel_bad_instrument_id as u32
    }

    /// A product for the violates_tick_table test
    pub(super) fn violates_tick_table_instrument_id(&self) -> u32 {
        self.product_bucket.violates_tick_table_instrument_id as u32
    }

    /// A product for the bbo test
    pub(super) fn bbo_instrument_id(&self) -> u32 {
        self.product_bucket.bbo_instrument_id as u32
    }

    /// A product for the gap_in_omd test
    pub(super) fn gap_in_omd_instrument_id(&self) -> u32 {
        self.product_bucket.gap_in_omd_instrument_id as u32
    }

    /// A product for the PTC test
    pub(super) fn ptc_instrument_id(&self) -> u32 {
        self.product_bucket.ptc_instrument_id as u32
    }

    /// A product for the Order Book test
    pub(super) fn order_book_based_on_market_data_instrument_id(&self) -> u32 {
        self.product_bucket.order_book_based_on_market_data_instrument_id as u32
    }

    /// A product for Block Dual test
    pub(super) fn block_dual(&self) -> u32 {
        self.product_bucket.block_dual as u32
    }

  /// A reference product for Block Dual test
    pub(super) fn block_dual_reference(&self) -> u32 {
        self.product_bucket.block_dual_reference as u32
    }
}
