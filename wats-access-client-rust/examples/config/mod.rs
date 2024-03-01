//! Example client configuration

use anyhow::{Context, Result};
use serde::Deserialize;
use std::net::{Ipv4Addr};
use std::str::FromStr;
use std::fs::File;

/// Load and deserialize config file.
///
/// Path to the file is the first argument passed in command line:
/// ```
/// cargo run --bin wats_access_client_rust --features="extras" -- config/client.yml
/// ```
pub(super) fn get_config() -> Result<Config> {
    let path = std::env::args().nth(1).context("No config file path")?;
    let config = File::open(path).context("Failed opening config file")?;
    serde_yaml::from_reader(config).context("Failed deserializing client configuration")
}

#[derive(Debug, Clone, Copy, Deserialize)]
pub(super) struct Credentials {
    /// Client connection ID
    connection_id: u16,

    /// Client login token
    token: [u8; 8],
}

#[derive(Debug, Deserialize)]
#[serde(deny_unknown_fields)]
pub(super) struct Config {
    /// Either an IPv4 address or hostname to connect to
    host: String,

    /// Trading Port [BIN] TCP port number
    trading_port_port: u16,

    /// Snapshot TCP port number
    snapshot_port: u16,

    /// Either an IPv4 address or hostname to connect to
    omd_host: String,

    /// Online Market Data UDP port number
    omd_port: u16,

    /// Replay TCP port number
    replay_port: u16,

    multicast_interface: String,

    /// Client credentials to authenticate to WATS
    #[serde(flatten)]
    credentials: Credentials,
}

impl Config {
    pub(super) fn tp_addr(&self) -> impl tokio::net::ToSocketAddrs + '_ {
        (self.host.as_str(), self.trading_port_port)
    }

    pub(super) fn omd_addr(&self) -> impl tokio::net::ToSocketAddrs + '_ {
        (self.omd_host.as_str(), self.omd_port)
    }

    pub(super) fn snapshot_addr(&self) -> impl tokio::net::ToSocketAddrs + '_ {
        (self.host.as_str(), self.snapshot_port)
    }

    pub(super) fn replay_addr(&self) -> impl tokio::net::ToSocketAddrs + '_ {
        (self.host.as_str(), self.replay_port)
    }

    /// Multicast interface
    pub(super) fn multicast_interface(&self) -> Ipv4Addr {
        Ipv4Addr::from_str(self.multicast_interface.as_str()).unwrap()
    }

    pub(super) fn conn_id(&self) -> u16 {
        self.credentials.connection_id
    }

    pub(super) fn token(&self) -> [u8; 8] {
        self.credentials.token
    }
}
