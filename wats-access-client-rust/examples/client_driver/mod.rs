use std::{io, net::Ipv4Addr};
use tokio::net::TcpListener;
use tracing::debug;

pub async fn incoming_orders() -> io::Result<TcpListener> {
    TcpListener::bind((Ipv4Addr::LOCALHOST, 0))
        .await
        .and_then(|listener| {
            debug!("Listening for orders on {:?}", listener.local_addr()?);
            Ok(listener)
        })
}
