//! Example binary that utilize WATS binary trading port client library.

use crate::config::get_config;
use anyhow::Result;
use config::Config;
use futures::{sink::SinkExt, stream::StreamExt};
use std::net::SocketAddr;
use tokio::net::TcpStream;
use tokio_util::codec::{BytesCodec, Framed};
use tracing::debug;
use wats_access_client_rust::ClientBuilder;

mod client_driver;
mod config;

#[tokio::main(flavor = "current_thread")]
async fn main() -> Result<()> {
    // Load configuration
    let config = get_config()?;
    let orders = crate::client_driver::incoming_orders().await?;

    loop {
        let (socket, addr) = orders.accept().await?;
        handle_connector(socket, addr, &config).await?;
    }
}

async fn handle_connector(socket: TcpStream, addr: SocketAddr, config: &Config) -> Result<()> {
    debug!("Creating new WATS client for connector: {:?}", addr);

    let mut client = ClientBuilder::connect(
        config.tp_addr(),
        config.omd_addr(),
        config.snapshot_addr(),
        config.replay_addr(),
        config.multicast_interface(),
    )
    .await?
    .login(config.conn_id(), config.token(), config.conn_id(), config.token())
    .await?;

    let mut orders = Framed::new(socket, BytesCodec::new());

    loop {
        tokio::select! {
          order = orders.next() => {
            if let Some(Ok(mut order)) = order {
              client.send_raw_bytes(&mut order).await?;
            }
          },
          Ok(resp) = client.get_tp_msg_from_wats() => {
            orders.send(resp).await?;
          }
          else => break,
        }
    }

    let _ = client.logout().await;
    debug!("WATS client logout");
    Ok(())
}
