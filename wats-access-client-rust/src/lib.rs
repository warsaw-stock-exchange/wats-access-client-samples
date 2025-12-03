//! Native Trading Port client in Rust.
//!
//! The goal of this library is to show how to connect and interact with WATS
//! binary trading port. It can be a good starting point for writing your
//! own WATS client in Rust.

use crate::{
    messages::{market_data as md, replay, trading_port as tp},
    utils::{create_md_codec, create_tp_codec, TradingPortMsg},
};
use anyhow::{Context, Result};
use bytes::Bytes;
use chacha20::{
    cipher::{NewCipher, StreamCipher, StreamCipherSeek},
    ChaCha20,
};
use futures::stream::StreamExt;
use socket2::{Domain, Socket, Type};
use std::{
    collections::HashMap,
    net::{Ipv4Addr, SocketAddr},
    ops::Range,
    mem,
};
use tokio::{
    io::AsyncWriteExt,
    net::{
        tcp::{OwnedReadHalf, OwnedWriteHalf},
        TcpStream, ToSocketAddrs, UdpSocket,
    },
};
use tokio_util::{
    codec::{length_delimited::LengthDelimitedCodec, FramedRead},
    udp::UdpFramed,
};
use tracing::{debug, instrument, trace};

mod errors;
pub mod messages;
mod utils;

pub use utils::from_bytes;

const NONCE_SIZE: usize = 12;

#[derive(Debug)]
struct PanicOnDrop;

impl Drop for PanicOnDrop {
    fn drop(&mut self) {
        if !std::thread::panicking() {
            panic!("You have to explicitly call `Client::logout`")
        }
    }
}

/// WATS Native Trading Port client
///
/// Keeps internal state of the connection to WATS and perform all necessary
/// housekeeping.
pub struct Client {
    rx: FramedRead<OwnedReadHalf, LengthDelimitedCodec>,
    tx: OwnedWriteHalf,
    next_expected_seq_num: u32,
    last_replay_seq_num: u32,
    tp_connection_id: u16,
    md_connection_id: u16,
    session_id: u16,
    snap: Snapshot,
    omd_framed: UdpFramed<LengthDelimitedCodec>,
    replay_addr: SocketAddr,
    _remember_to_logout: PanicOnDrop,
}

impl Client {
    pub fn last_seq_num(&self) -> u32 {
        self.last_replay_seq_num
    }

    pub fn tp_connection_id(&self) -> u16 {
        self.tp_connection_id
    }

    pub fn md_connection_id(&self) -> u16 {
      self.md_connection_id
  }

  /// Logout from WATS [BIN] Trading Port. Skip `Heartbeat` message in response.
    #[instrument(skip_all, fields(connection_id = self.tp_connection_id()))]
    pub async fn logout(mut self) -> Result<()> {
        // Leak `PanicOnDrop` since we are performing graceful logout.
        std::mem::forget(self._remember_to_logout);

        let header = tp::Header {
            seq_num: 0,
            ..tp::Header::new(tp::MsgType::Logout)
        };
        let logout = tp::Logout { header };

        self.tx
            .write_all_buf(&mut logout.as_slice())
            .await
            .context("Failed sending logout message")?;

        loop {
            let response = if let Some(response) = self.rx.next().await {
                response.context("Codec error")?
            } else {
                anyhow::bail!("No logout response from WATS",)
            };

            if !tp::Message::is_valid(&response) {
                anyhow::bail!("Malformed logout response")
            }

            let response = unsafe { from_bytes::<tp::Message>(&response) };
            match response.msg_type() {
                tp::MsgType::LogoutResponse => break,
                tp::MsgType::Heartbeat => continue,
                msg_type => anyhow::bail!("Got unexpected message: {:?}", msg_type),
            }
        }

        debug!("Client logged out");

        Ok(())
    }

    pub fn omd_messages(&self) -> impl Iterator<Item = &md::Message> {
        self.snap.md_message.iter()
    }

    pub fn omd_tick_table(
        &self,
        tick_table_id: &u32,
    ) -> Option<&Vec<(md::TickSize, md::Bound)>> {
        self.snap.tick_tables.get(tick_table_id)
    }

    /// Chceck order against recent Online Market Data before submitting it to WATS.
    /// Increase internal seq_num counter.
    pub async fn submit_order_checked(&mut self, order: tp::OrderAdd) -> Result<()> {
        // Below checks reflect WATS internal validation
        if let Some(entries) = self
            .snap
            .tradable_products
            .get(&{ order.instrument_id })
            .and_then(|tick_table_id| self.snap.tick_tables.get(tick_table_id))
        {
            let idx = entries.partition_point(|entry| entry.0 <= order.price);
            if idx == 0 {
                anyhow::bail!("PriceValidationError::EntryNotFound")
            }

            let tick_size = entries[idx - 1].0;
            if (order.price % tick_size) != 0 {
                anyhow::bail!("PriceValidationError::TickSizeMismatch: {}", tick_size)
            }
        }

        // If there was no tick table for given tradeable product or no error
        // proceed with usual submit_order logic.
        self.submit_order(order).await
    }

    /// Submit order to WATS and increase internal seq_num counter.
    #[instrument(skip_all, fields(connection_id = self.tp_connection_id()))]
    pub async fn submit_order(&mut self, mut order: tp::OrderAdd) -> Result<()> {
        // Assert that order's header is correct
        debug_assert_eq!(
            usize::from(order.header.length),
            std::mem::size_of::<tp::OrderAdd>()
        );
        debug_assert_eq!({ order.header.msg_type }, tp::MsgType::OrderAdd);

        order.header.seq_num = self.next_expected_seq_num;
        self.tx
            .write_all_buf(&mut order.as_slice())
            .await
            .context("Failed sending OrderAdd message")?;

        debug!(
            "Submitted OrderAdd: {}",
            serde_json::to_string(&order).unwrap()
        );

        self.next_expected_seq_num = self.next_expected_seq_num.saturating_add(1);

        Ok(())
    }

    #[instrument(skip_all, fields(connection_id = self.tp_connection_id()))]
    pub async fn submit_trade_capture_report_dual(&mut self, 
      mut tcrd: tp::TradeCaptureReportDual) -> Result<()> {

        // Assert that order's header is correct
        debug_assert_eq!(
            usize::from(tcrd.header.length),
            std::mem::size_of::<tp::TradeCaptureReportDual>()
        );
        debug_assert_eq!({ tcrd.header.msg_type }, tp::MsgType::TradeCaptureReportDual);

        tcrd.header.seq_num = self.next_expected_seq_num;
        self.tx
            .write_all_buf(&mut tcrd.as_slice())
            .await
            .context("Failed sending TradeCaptureReportDual message")?;

        debug!(
            "Submitted TradeCaptureReportDual: {}",
            serde_json::to_string(&tcrd).unwrap()
        );

        self.next_expected_seq_num = self.next_expected_seq_num.saturating_add(1);

        Ok(())
    }

    /// Get expected `tp::TradeCaptureReportResponse` from WATS.
    #[instrument(skip_all, fields(connection_id = self.tp_connection_id()))]
    pub async fn get_trade_capture_report_dual_resp(&mut self)
      -> Result<tp::TradeCaptureReportResponse> {
        loop {
            let bytes = self.get_tp_msg_from_wats().await?;
            let msg = unsafe { from_bytes::<tp::Message>(&bytes) };
            match msg.msg_type() {
                tp::MsgType::TradeCaptureReportResponse => {
                    let msg = unsafe { msg.trade_capture_report_response };
                    debug!(
                        "TradeCaptureReportResponse: {}",
                        serde_json::to_string(&msg).unwrap()
                    );
                    return Ok(msg);
                },
                tp::MsgType::TradeCaptureReportDual => continue,
                tp::MsgType::Heartbeat => continue,
                tp::MsgType::OrderExecute => continue,
                _ => continue,
            }
        }
    }

    /// Calculate orderRefId for last order.
    ///
    /// WATS use bitwise combination of connection_id, session_id, bulk_seq_num
    /// and seq_num  +  as an client_order_id.
    fn last_order_ref_id(&self) -> tp::OrderId {
        let bulk_seq_num = 0;
        let last_seq_num = self.next_expected_seq_num.saturating_sub(1);

        tp::OrderId::new(self.tp_connection_id, self.session_id,
            bulk_seq_num, last_seq_num).unwrap()
    }

    pub async fn modify_order(&mut self, mut order: tp::OrderModify, order_id: tp::OrderId) -> Result<()> {
        // Assert that order's header is correct
        debug_assert_eq!(
            usize::from(order.header.length),
            std::mem::size_of::<tp::OrderModify>()
        );
        debug_assert_eq!({ order.header.msg_type }, tp::MsgType::OrderModify);

        order.order_id = order_id;
        order.header.seq_num = self.next_expected_seq_num;
        self.tx
            .write_all_buf(&mut order.as_slice())
            .await
            .context("Failed sending OrderModify message")?;

        debug!(
            "Submitted OrderModify: {}",
            serde_json::to_string(&order).unwrap()
        );

        self.next_expected_seq_num = self.next_expected_seq_num.saturating_add(1);

        Ok(())
    }

    /// Submit order modify for last order submitted to WATS.
    #[instrument(skip_all, fields(connection_id = self.tp_connection_id()))]
    pub async fn mod_last_order(&mut self, order: tp::OrderModify) -> Result<()> {
        self.modify_order(order, self.last_order_ref_id()).await
    }

    /// Cancel order earlier submitted to WATS.
    #[instrument(skip_all, fields(connection_id = self.tp_connection_id()))]
    pub async fn cancel_order(&mut self, mut order: tp::OrderCancel) -> Result<()> {
        // Assert that order's header is correct
        debug_assert_eq!(
            usize::from(order.header.length),
            std::mem::size_of::<tp::OrderCancel>()
        );
        debug_assert_eq!({ order.header.msg_type }, tp::MsgType::OrderCancel);

        order.header.seq_num = self.next_expected_seq_num;
        self.tx
            .write_all_buf(&mut order.as_slice())
            .await
            .context("Failed sending OrderCancel message")?;

        debug!(
            "Submitted OrderCancel: {}",
            serde_json::to_string(&order).unwrap()
        );

        self.next_expected_seq_num = self.next_expected_seq_num.saturating_add(1);

        Ok(())
    }

    /// Get raw bytes from WATS
    async fn get_tp_msg_from_wats_unchecked(&mut self) -> Result<Bytes> {
        self.rx
            .next()
            .await
            .context("No message from WATS")?
            .map(|bytes| bytes.freeze())
            .context("Codec error")
    }

    /// Get expected `tp::OrderAddResponse` from WATS. Skip any heartbeat
    /// message and return error if there is anything else than
    /// `tp::OrderAddResponse`.
    #[instrument(skip_all, fields(connection_id = self.tp_connection_id()))]
    pub async fn get_order_add_resp(&mut self) -> Result<tp::OrderAddResponse> {
        loop {
            let bytes = self.get_tp_msg_from_wats().await?;
            let msg = unsafe { from_bytes::<tp::Message>(&bytes) };
            match msg.msg_type() {
                tp::MsgType::OrderAddResponse => {
                    let msg = unsafe { msg.order_add_response };
                    debug!(
                        "Got OrderAddResponse: {}",
                        serde_json::to_string(&msg).unwrap()
                    );
                    return Ok(msg);
                }
                tp::MsgType::Heartbeat => continue,
                _ => anyhow::bail!("If not OrderAddResponse then it should be a heartbeat"),
            }
        }
    }

    /// Get expected `tp::OrderAddResponse` from WATS. Skip anything else.
    pub async fn get_order_mod_resp_ignore_other(&mut self) -> Result<tp::OrderAddResponse> {
        loop {
            let bytes = self.get_tp_msg_from_wats().await?;
            let msg = unsafe { from_bytes::<tp::Message>(&bytes) };
            match msg.msg_type() {
                tp::MsgType::OrderAddResponse => {
                    let msg = unsafe { msg.order_add_response };
                    debug!(
                        "Got OrderAddResponse: {}",
                        serde_json::to_string(&msg).unwrap()
                    );
                    return Ok(msg);
                }
                _ => continue,
            }
        }
    }

    /// Wait until next Heartbeat message from WATS.
    pub async fn wait_for_heartbeat(&mut self) -> Result<tp::Heartbeat> {
        loop {
            let bytes = self.get_tp_msg_from_wats().await?;
            let msg = unsafe { from_bytes::<tp::Message>(&bytes) };
            match msg.msg_type() {
                tp::MsgType::Heartbeat => {
                    let msg = unsafe { msg.heartbeat };
                    debug!(
                        "Got Heartbeat: {}",
                        serde_json::to_string(&msg).unwrap()
                    );
                    return Ok(msg);
                }
                _ => continue,
            }
        }
    }

    /// Pull a single message from Online Market Data. This method should be
    /// called in a loop to continuously update a local copy of the Market Data
    /// state.
    ///
    /// Returns the gap between last `md_last_seq_num` value and `seq_num` in
    /// received Market Data message. If the gap is bigger than 1 message it
    /// means that something is missing. In that case return the gap. It is
    /// caller's responsibility to request missing messages from replay.
    ///
    /// If the gap is 1 proceed with processing OMD message and update
    /// `md_last_seq_num`.
    pub async fn pull_msg_from_omd(&mut self) -> Result<Option<Range<u32>>> {
        self.snap.pull_msg_from_md(&mut self.omd_framed).await
    }

    /// Request a replay of Online Market Data messages within given range.
    #[instrument(skip_all, fields(connection_id = self.tp_connection_id()))]
    pub async fn request_omd_replay(&mut self, range: Range<u32>) -> Result<()> {
        self.snap.request_md_replay(self.replay_addr, range).await
    }

    /// Get expected `tp::OrderModifyResponse` from WATS. Skip any heartbeat
    /// message and return error if there is anything else than
    /// `tp::OrderModifyResponse`.
    #[instrument(skip_all, fields(connection_id = self.tp_connection_id()))]
    pub async fn get_order_mod_resp(&mut self) -> Result<tp::OrderModifyResponse> {
        loop {
            let bytes = self.get_tp_msg_from_wats().await?;
            let msg = unsafe { from_bytes::<tp::Message>(&bytes) };
            match msg.msg_type() {
                tp::MsgType::OrderModifyResponse => {
                    let msg = unsafe { msg.order_modify_response };
                    debug!(
                        "Got OrderModifyResponse: {}",
                        serde_json::to_string(&msg).unwrap()
                    );
                    return Ok(msg);
                }
                tp::MsgType::Heartbeat => continue,
                _ => anyhow::bail!("If not OrderModifyResponse then it should be a heartbeat"),
            }
        }
    }

    /// Get expected `tp::OrderCancelResponse` from WATS. Skip any heartbeat
    /// message and return error if there is anything else than
    /// `tp::OrderCancelResponse`.
    #[instrument(skip_all, fields(connection_id = self.tp_connection_id()))]
    pub async fn get_order_cancel_resp(&mut self) -> Result<tp::OrderCancelResponse> {
        loop {
            let bytes = self.get_tp_msg_from_wats().await?;
            let msg = unsafe { from_bytes::<tp::Message>(&bytes) };
            match msg.msg_type() {
                tp::MsgType::OrderCancelResponse => {
                    let msg = unsafe { msg.order_cancel_response };
                    debug!(
                        "Got OrderCancelResponse: {}",
                        serde_json::to_string(&msg).unwrap()
                    );
                    return Ok(msg);
                }
                tp::MsgType::Heartbeat => continue,
                _ => anyhow::bail!("If not OrderCancelResponse then it should be a heartbeat"),
            }
        }
    }

    /// Get expected `tp::Trade` from WATS. Skip any heartbeat message
    /// and return error if there is anything else than `tp::Trade`.
    #[instrument(skip_all, fields(connection_id = self.tp_connection_id()))]
    pub async fn get_trade(&mut self) -> Result<tp::OrderExecute> {
        loop {
            let bytes = self.get_tp_msg_from_wats().await?;
            let msg = unsafe { from_bytes::<tp::Message>(&bytes) };
            match msg.msg_type() {
                tp::MsgType::OrderExecute => {
                    let msg = unsafe { msg.order_execute };
                    debug!("Got Trade: {}", serde_json::to_string(&msg).unwrap());
                    return Ok(msg);
                }
                tp::MsgType::Heartbeat => continue,
                _ => anyhow::bail!("If not Trade then it should be a heartbeat"),
            }
        }
    }

    /// Get binary trading port message from WATS.
    pub async fn get_tp_msg_from_wats(&mut self) -> Result<bytes::Bytes> {
        let bytes = self.get_tp_msg_from_wats_unchecked().await?;
        if !tp::Message::is_valid(&bytes) {
            anyhow::bail!("Got garbage data. Perhaps there is a version mismatch.")
        }

        Ok(bytes)
    }

    /// Submit raw bytes to WATS without any housekeeping.
    pub async fn send_raw_bytes(&mut self, bytes: &mut bytes::BytesMut) -> Result<()> {
        self.tx
            .write_all_buf(bytes)
            .await
            .context("Failed sending raw data")?;

        Ok(())
    }
}

pub struct ClientBuilder {
    tp_rx: FramedRead<OwnedReadHalf, LengthDelimitedCodec>,
    tp_tx: OwnedWriteHalf,
    snap_rx: FramedRead<OwnedReadHalf, LengthDelimitedCodec>,
    snap_tx: OwnedWriteHalf,
    omd_framed: UdpFramed<LengthDelimitedCodec>,
    replay_addr: SocketAddr,
}

impl ClientBuilder {
    /// Connect to Online Market Data stream, Market Data snapshot and
    /// establish a TCP connection to Trading Port. Also configure the socket
    /// with the `TCP_NODELAY` option.
    pub async fn connect(
        tp_addr: impl ToSocketAddrs,
        omd_addr: impl ToSocketAddrs,
        snapshot_addr: impl ToSocketAddrs,
        replay_addr: impl ToSocketAddrs,
        multicast_interface: Ipv4Addr,
    ) -> Result<Self> {
        let replay_addr = tokio::net::lookup_host(replay_addr)
            .await?
            .next()
            .context("Could not lookup replay address")?;

        // Join the IPv4 multicast group associated with Online Market Data.
        let omd_framed = market_data_framed(omd_addr, Some(multicast_interface)).await?;

        // Establish a TCP connection to Market Data snapshot.
        let (snap_rx, snap_tx) = connect_to_omd_snapshot(snapshot_addr).await?;

        // Establish a TCP connection to Trading Port.
        let stream = TcpStream::connect(tp_addr)
            .await
            .context("Faild connecting to trading port")?;
        stream
            .set_nodelay(true)
            .context("Failed setting TCP_NODELAY socket option")?;
        let (tp_rx, tp_tx) = stream.into_split();
        let tp_rx = FramedRead::new(tp_rx, create_tp_codec());

        debug!("Connected to all services");

        Ok(ClientBuilder {
            tp_rx,
            tp_tx,
            snap_rx,
            snap_tx,
            omd_framed,
            replay_addr,
        })
    }

    /// Pull the latest Online Market Data snapshot and store essential data
    /// for further reference. The snapshot is required to fetch encryption
    /// keys and [`md::TickTableEntry`] for [`tp::OrderAdd`] validation.
    async fn get_market_data_snapshot(
        &mut self,
        connection_id: u16,
        token: [u8; 8],
    ) -> Result<Snapshot> {
        // Login to OMD Snapshot
        let login = md::Login {
            header: md::Header::new(md::MsgType::Login),
            connection_id,
            token,
            filter: md::MessageFilter::all(),
        };

        self.snap_tx
            .write_all_buf(&mut login.as_slice())
            .await
            .context("Failed sending Login message to snapshot")?;

        let bytes = self
            .snap_rx
            .next()
            .await
            .context("No snapshot login response")?
            .context("Codec error in snapshot login response")?;

        // Check if the response is [`md::LoginResponse`] message and the result is ok.
        let login_resp = unsafe { from_bytes::<md::LoginResponse>(&bytes) };

        if !(bytes.len() == std::mem::size_of::<md::LoginResponse>() && {
            login_resp.header.msg_type
        }
            == md::MsgType::LoginResponse)
        {
            anyhow::bail!("Bad snapshot login response")
        }

        if login_resp.result != md::LoginResult::Ok {
            anyhow::bail!("Snapshot login failed: {:?}", login_resp.result)
        }

        // Collect encryption keys and tradeable products -> tick table
        // mapping for further use.

        let mut snap: Snapshot = Default::default();

        // Read untill `md::EndOfSnapshot`. `last_seq_num` in `EndOfSnapshot`
        // message identifies last Market Data message that was used to
        // create a snapshot.
        snap.md_last_seq_num = loop {
            let msg = self
                .snap_rx
                .next()
                .await
                .context("End of snapshot stream")?
                .context("Bad message in snapshot stream")?;

            let msg = unsafe { &mut *(msg.as_ptr() as *mut md::Message) };

            if msg.msg_type() == md::MsgType::EndOfSnapshot {
                break unsafe { &msg.end_of_snapshot }.last_seq_num;
            }

            snap.match_message(msg).ok();
        };

        Ok(snap)
    }

    /// Login to the Online Market Data Snapshot and pull it then login to
    /// WATS Trading Port.
    pub async fn login(mut self, 
        tp_connection_id: u16, tp_token: [u8; 8],
        md_connection_id: u16, md_token: [u8; 8]) -> Result<Client> {
        let snap = self.get_market_data_snapshot(md_connection_id, md_token).await?;

        let login = tp::Login {
            header: tp::Header::new(tp::MsgType::Login),
            version: 0,
            connection_id: tp_connection_id,
            next_expected_seq_num: 0,
            last_sent_seq_num: 0,
            token: tp_token,
        };

        self.tp_tx
            .write_all_buf(&mut login.as_slice())
            .await
            .context("Failed sending login message")?;

        let response = if let Some(response) = self.tp_rx.next().await {
            response.context("Codec error")?
        } else {
            anyhow::bail!("No login response from WATS",)
        };

        if !tp::Message::is_valid(&response) {
            anyhow::bail!("Malformed login response")
        }

        let response = unsafe { from_bytes::<tp::Message>(&response) };
        let response = match response.msg_type() {
            tp::MsgType::LoginResponse => unsafe { response.login_response },
            msg_type => anyhow::bail!("Got unexpected message: {:?}", msg_type),
        };

        // Make sure the response is OK
        if response.result != tp::LoginResult::Ok {
            anyhow::bail!("Got bad login result: {:?}", response.result);
        }

        debug!("Client logged in");

        Ok(Client {
            rx: self.tp_rx,
            tx: self.tp_tx,
            next_expected_seq_num: response.next_expected_seq_num,
            last_replay_seq_num: response.last_replay_seq_num,
            tp_connection_id,
            md_connection_id: md_connection_id,
            session_id: response.session_id,
            snap,
            omd_framed: self.omd_framed,
            replay_addr: self.replay_addr,
            _remember_to_logout: PanicOnDrop {},
        })
    }
}

/// Connect to the Market Data stream.
///
/// Join the IPv4 multicast group associated with the Market Data and
/// return [`UdpFramed`] stream of [`md::Message`].
///
/// `addr` is the Ipv4 socket address of a multicast group that the Market Data
/// push messages to.
///
/// `interface` is the address of the local interface with which the system
/// should join the multicast group. If it's `None` then the default
/// [`Ipv4Addr::UNSPECIFIED`] is used and an appropriate interface is chosen
/// by the system. See [`Socket::join_multicast_v4`] for more details.
async fn market_data_framed(
    addr: impl ToSocketAddrs,
    interface: Option<Ipv4Addr>,
) -> Result<UdpFramed<LengthDelimitedCodec>> {
    let addr = tokio::net::lookup_host(addr)
        .await?
        .find_map(|addr| match addr {
            SocketAddr::V4(addr) => Some(addr),
            _ => None,
        })
        .context("Could not lookup MD multicast group address")?;

    let interface = interface.unwrap_or(Ipv4Addr::UNSPECIFIED);
    let address: SocketAddr = addr.into();

    let socket = Socket::new(Domain::IPV4, Type::DGRAM, None)?;
    socket.set_reuse_address(true)?;
    socket.set_reuse_port(true)?;
    socket.bind(&address.into())?;

    socket.set_nonblocking(true)?;
    socket.join_multicast_v4(addr.ip(), &interface)?;

    let socket = UdpSocket::from_std(socket.into())?;
    let socket = UdpFramed::new(socket, create_md_codec());

    Ok(socket)
}

/// Connect to an instance of snapshot over TCP.
async fn connect_to_omd_snapshot(
    addr: impl ToSocketAddrs,
) -> Result<(
    FramedRead<OwnedReadHalf, LengthDelimitedCodec>,
    OwnedWriteHalf,
)> {
    let stream = TcpStream::connect(addr)
        .await
        .context("Faild connecting to Online Market Data snapshot")?;
    let (rx, tx) = stream.into_split();
    let rx = FramedRead::new(rx, create_md_codec());

    Ok((rx, tx))
}

fn get_cipher_for_msg(
  encryption_keys: &[md::EncryptionKey],
  encryption_key_id: u32,
  encryption_key_offset: u64,
) -> Option<ChaCha20> {
  if encryption_key_id == 0 { return None }

  let encryption_keys: Vec<_> = encryption_keys
      .iter()
      .filter(|key| key.id == encryption_key_id)
      .collect();

  // Case when user is intentionally NOT ALLOWED to decrypt certain elements
  // should be serviced here. Now the code will stop on missing decryptiopn key.
  assert_eq!(encryption_keys.len(), 1);

  let mut cipher = ChaCha20::new(
      &encryption_keys[0].secret_key.into(),
      [0u8; NONCE_SIZE].as_slice().into(),
  );
  cipher.seek(encryption_key_offset);
  Some(cipher)
}

#[derive(Default)]
struct Snapshot {
    md_last_seq_num: u32,
    peg: Vec<md::EncryptionKey>,
    tradable_products: HashMap<tp::ElementId, md::ElementId>,
    tick_tables:
        HashMap<md::ElementId, Vec<(md::TickSize, md::Bound)>>,
    md_message: Vec<md::Message>,
}

/// Applies keystream on a field of a message
/// by copying the value
macro_rules! apply_on_field  {
  ($e:expr, $cipher:expr) => {
    {
      let mut tmp = $e;
      let size = tmp.apply_keystream($cipher);
      $e = tmp;
      size
    }
  };
}

pub unsafe trait Binarize {
  /// Returns the length in bytes of the object
  fn length(&self) -> usize;

  unsafe fn as_bytes_mut(&mut self) -> &mut [u8] {
    let ptr = self as *mut Self as *mut u8;
    let len = self.length();

    // Assert against malicious `Binarize::length` implementation
    debug_assert!(len <= std::mem::size_of_val::<Self>(self));

    std::slice::from_raw_parts_mut(ptr, len)
  }
}

macro_rules! impl_binarize {
  ($name:ty) => {
    // This implementation is very much safe - it just returns full size of an object
    unsafe impl Binarize for $name {
      fn length(&self) -> usize {
        mem::size_of::<Self>()
      }
    }
  };
}

impl_binarize!(u8);
impl_binarize!(i64);
impl_binarize!(u16);
impl_binarize!(u32);
impl_binarize!(u64);
impl_binarize!(md::ProductIdentification);
impl_binarize!(md::ChangeIndicator);
impl_binarize!(md::Timestamp);

pub trait ApplyKeystream {
  fn apply_keystream(&mut self, cipher: &mut ChaCha20) -> usize;
}

macro_rules! impl_apply_keystream {
  ($name:ty) => {
    impl ApplyKeystream for $name {
      #[inline(always)]
      fn apply_keystream(&mut self, cipher: &mut ChaCha20) -> usize {
        cipher.apply_keystream(unsafe { self.as_bytes_mut() });
        mem::size_of::<Self>()
      }
    }
  };
}

impl_apply_keystream!(u8);
impl_apply_keystream!(i64);
impl_apply_keystream!(u16);
impl_apply_keystream!(u32);
impl_apply_keystream!(u64);
impl_apply_keystream!(md::Timestamp);
impl_apply_keystream!(md::ProductIdentification);
impl_apply_keystream!(md::ChangeIndicator);

impl Snapshot {
    fn match_message(&mut self, msg: &mut md::Message) -> Result<Option<Range<u32>>> {
        let seq_num = msg.seq_num();
        let gap = seq_num.abs_diff(self.md_last_seq_num);
        if gap > 1 {
            return Ok(Some(self.md_last_seq_num..self.md_last_seq_num + gap));
        }

        self.md_last_seq_num = seq_num;

        match msg.msg_type() {
            md::MsgType::EncryptionKey => {
                let encryption_key = unsafe { msg.encryption_key };
                trace!(
                    "MD EncryptionKey: {}",
                    serde_json::to_string(&encryption_key).unwrap()
                );
                self.peg.push(encryption_key);
            }
            md::MsgType::Instrument => {
                let instrument = unsafe { msg.instrument };
                trace!(
                    "MD TradableProduct: {}",
                    serde_json::to_string(&instrument).unwrap()
                );

                let instrument_id = instrument.instrument_id;
                let tick_table_id = instrument.tick_table_id;

                self.tradable_products
                    .insert(instrument_id, tick_table_id);

                self.md_message.push(md::Message { instrument });
            }
            md::MsgType::TickTableEntry => {
                let tick_table_entry = unsafe { msg.tick_table_entry };
                trace!(
                    "MD TickTableEntry: {}",
                    serde_json::to_string(&tick_table_entry).unwrap()
                );

                let tick_table_id = tick_table_entry.tick_table_id;
                let tick_size = tick_table_entry.tick_size;
                let lower_bound = tick_table_entry.lower_bound;

                self.tick_tables
                    .entry(tick_table_id)
                    .or_default()
                    .push((tick_size, lower_bound));
            }
            md::MsgType::OrderAdd => {
                let msg = unsafe { &mut msg.order_add };
                let cipher = get_cipher_for_msg(&self.peg,
                    msg.header.encryption_key_id,
                    msg.header.encryption_offset);
                if let Some(mut cipher) = cipher {
                    apply_on_field!(msg.instrument_id, &mut cipher);
                    apply_on_field!(msg.price, &mut cipher);
                    apply_on_field!(msg.quantity, &mut cipher);
                }

                let mut order_add = Default::default();
                std::mem::swap(&mut order_add, msg);

                debug!(
                    "MD OrderAdd: {}",
                    serde_json::to_string(&order_add).unwrap()
                );

                self.md_message.push(md::Message { order_add });
            }
            md::MsgType::OrderModify => {
                let msg = unsafe { &mut msg.order_modify };
                let cipher = get_cipher_for_msg(&self.peg,
                    msg.header.encryption_key_id,
                    msg.header.encryption_offset);
                if let Some(mut cipher) = cipher {
                    apply_on_field!(msg.instrument_id, &mut cipher);
                    apply_on_field!(msg.price, &mut cipher);
                    apply_on_field!(msg.quantity, &mut cipher);
                }

                let mut order_modify = Default::default();
                std::mem::swap(&mut order_modify, msg);

                debug!(
                    "MD OrderModify: {}",
                    serde_json::to_string(&order_modify).unwrap()
                );

                self.md_message.push(md::Message { order_modify });
            }
            md::MsgType::OrderExecute => {
                let msg = unsafe { &mut msg.order_execute };
                let cipher = get_cipher_for_msg(&self.peg,
                    msg.header.encryption_key_id,
                    msg.header.encryption_offset);
                if let Some(mut cipher) = cipher {
                    apply_on_field!(msg.quantity, &mut cipher);
                    apply_on_field!(msg.instrument_id, &mut cipher);
                    apply_on_field!(msg.trade_id, &mut cipher);
                    apply_on_field!(msg.execution_price, &mut cipher);
                    apply_on_field!(msg.execution_quantity, &mut cipher);
                }

                let mut order_execute = Default::default();
                std::mem::swap(&mut order_execute, msg);

                debug!(
                    "MD OrderExecute: {}",
                    serde_json::to_string(&order_execute).unwrap()
                );

                self.md_message.push(md::Message { order_execute });
            }
            md::MsgType::TopPriceLevelUpdate => {
                let msg = unsafe { &mut msg.top_price_level_update };
                let cipher = get_cipher_for_msg(&self.peg,
                    msg.header.encryption_key_id,
                    msg.header.encryption_offset);
                if let Some(mut cipher) = cipher {
                    apply_on_field!(msg.instrument_id, &mut cipher);
                    apply_on_field!(msg.price, &mut cipher);
                    apply_on_field!(msg.quantity, &mut cipher);
                    apply_on_field!(msg.order_count, &mut cipher);
                }

                let mut top_price_level_update = Default::default();
                std::mem::swap(&mut top_price_level_update, msg);

                debug!(
                    "MD TopPriceLevelUpdate: {}",
                    serde_json::to_string(&top_price_level_update).unwrap()
                );

                self.md_message.push(md::Message {
                    top_price_level_update,
                });
            }
            md::MsgType::PriceLevelSnapshot => {
                let msg = unsafe { &mut msg.price_level_snapshot };
                for level in msg.buy.iter_mut().filter(|level| level.is_encrypted) {
                    let cipher = get_cipher_for_msg(&mut self.peg,
                      level.encryption_key_id,
                      level.encryption_offset);

                    if let Some(mut cipher) = cipher {
                      apply_on_field!(level.price, &mut cipher);
                      apply_on_field!(level.quantity, &mut cipher);
                      apply_on_field!(level.order_count, &mut cipher);
                    }
                }
                for level in msg.sell.iter_mut().filter(|level| level.is_encrypted) {
                  let cipher = get_cipher_for_msg(&mut self.peg,
                    level.encryption_key_id,
                    level.encryption_offset);

                  if let Some(mut cipher) = cipher {
                    apply_on_field!(level.price, &mut cipher);
                    apply_on_field!(level.quantity, &mut cipher);
                    apply_on_field!(level.order_count, &mut cipher);
                  }
                }

                let mut price_level_snapshot = Default::default();
                std::mem::swap(&mut price_level_snapshot, msg);

                debug!(
                    "MD PriceLevelSnapshot: {}",
                    serde_json::to_string(&price_level_snapshot).unwrap()
                );

                self.md_message.push(md::Message {
                    price_level_snapshot,
                });
            }
            md::MsgType::ProductSummary => {
                let msg = unsafe { &mut msg.product_summary };
                let cipher = get_cipher_for_msg(&self.peg,
                    msg.header.encryption_key_id,
                    msg.header.encryption_offset);
                if let Some(mut cipher) = cipher {
                    apply_on_field!(msg.product_identification, &mut cipher);
                    apply_on_field!(msg.product_id, &mut cipher);

                    apply_on_field!(msg.clob_instrument.instrument_id, &mut cipher);
                    apply_on_field!(msg.clob_instrument.last_traded_price, &mut cipher);
                    apply_on_field!(msg.clob_instrument.closing_price, &mut cipher);
                    apply_on_field!(msg.clob_instrument.adjusted_closing_price, &mut cipher);
                    apply_on_field!(msg.clob_instrument.pct_change, &mut cipher);
                    apply_on_field!(msg.clob_instrument.vwap, &mut cipher);
                    apply_on_field!(msg.clob_instrument.total_volume, &mut cipher);
                    apply_on_field!(msg.clob_instrument.total_value, &mut cipher);
                    apply_on_field!(msg.clob_instrument.opening_price, &mut cipher);
                    apply_on_field!(msg.clob_instrument.max_price, &mut cipher);
                    apply_on_field!(msg.clob_instrument.min_price, &mut cipher);
                    apply_on_field!(msg.clob_instrument.settlement_price, &mut cipher);
                    apply_on_field!(msg.clob_instrument.settlement_value, &mut cipher);

                    apply_on_field!(msg.cross_instrument.instrument_id, &mut cipher);
                    apply_on_field!(msg.cross_instrument.last_traded_price, &mut cipher);
                    apply_on_field!(msg.cross_instrument.closing_price, &mut cipher);
                    apply_on_field!(msg.cross_instrument.adjusted_closing_price, &mut cipher);
                    apply_on_field!(msg.cross_instrument.pct_change, &mut cipher);
                    apply_on_field!(msg.cross_instrument.vwap, &mut cipher);
                    apply_on_field!(msg.cross_instrument.total_volume, &mut cipher);
                    apply_on_field!(msg.cross_instrument.total_value, &mut cipher);
                    apply_on_field!(msg.cross_instrument.opening_price, &mut cipher);
                    apply_on_field!(msg.cross_instrument.max_price, &mut cipher);
                    apply_on_field!(msg.cross_instrument.min_price, &mut cipher);
                    apply_on_field!(msg.cross_instrument.settlement_price, &mut cipher);
                    apply_on_field!(msg.cross_instrument.settlement_value, &mut cipher);

                    apply_on_field!(msg.block_instrument.instrument_id, &mut cipher);
                    apply_on_field!(msg.block_instrument.last_traded_price, &mut cipher);
                    apply_on_field!(msg.block_instrument.closing_price, &mut cipher);
                    apply_on_field!(msg.block_instrument.adjusted_closing_price, &mut cipher);
                    apply_on_field!(msg.block_instrument.pct_change, &mut cipher);
                    apply_on_field!(msg.block_instrument.vwap, &mut cipher);
                    apply_on_field!(msg.block_instrument.total_volume, &mut cipher);
                    apply_on_field!(msg.block_instrument.total_value, &mut cipher);
                    apply_on_field!(msg.block_instrument.opening_price, &mut cipher);
                    apply_on_field!(msg.block_instrument.max_price, &mut cipher);
                    apply_on_field!(msg.block_instrument.min_price, &mut cipher);
                    apply_on_field!(msg.block_instrument.settlement_price, &mut cipher);
                    apply_on_field!(msg.block_instrument.settlement_value, &mut cipher);

                    apply_on_field!(msg.hybrid_instrument.instrument_id, &mut cipher);
                    apply_on_field!(msg.hybrid_instrument.last_traded_price, &mut cipher);
                    apply_on_field!(msg.hybrid_instrument.closing_price, &mut cipher);
                    apply_on_field!(msg.hybrid_instrument.adjusted_closing_price, &mut cipher);
                    apply_on_field!(msg.hybrid_instrument.pct_change, &mut cipher);
                    apply_on_field!(msg.hybrid_instrument.vwap, &mut cipher);
                    apply_on_field!(msg.hybrid_instrument.total_volume, &mut cipher);
                    apply_on_field!(msg.hybrid_instrument.total_value, &mut cipher);
                    apply_on_field!(msg.hybrid_instrument.opening_price, &mut cipher);
                    apply_on_field!(msg.hybrid_instrument.max_price, &mut cipher);
                    apply_on_field!(msg.hybrid_instrument.min_price, &mut cipher);
                    apply_on_field!(msg.hybrid_instrument.settlement_price, &mut cipher);
                    apply_on_field!(msg.hybrid_instrument.settlement_value, &mut cipher);

                    apply_on_field!(msg.trading_value_currency, &mut cipher);
                    apply_on_field!(msg.marker_price_change, &mut cipher);
                    apply_on_field!(msg.delta, &mut cipher);
                    apply_on_field!(msg.gamma, &mut cipher);
                    apply_on_field!(msg.rho, &mut cipher);
                    apply_on_field!(msg.theta, &mut cipher);
                    apply_on_field!(msg.vega, &mut cipher);
                    apply_on_field!(msg.volatility, &mut cipher);
                }

                let mut product_summary = Default::default();
                std::mem::swap(&mut product_summary, msg);

                debug!(
                    "MD ProductSummary: {}",
                    serde_json::to_string(&product_summary).unwrap()
                );

                self.md_message.push(md::Message { product_summary });
            }
            md::MsgType::OrderCancel => {
                let order_cancel = unsafe { msg.order_cancel };
                debug!(
                    "MD OrderDelete: {}",
                    serde_json::to_string(&order_cancel).unwrap()
                );

                self.md_message.push(md::Message { order_cancel });
            }
            md::MsgType::PriceUpdate => {
                let price_update = unsafe { msg.price_update };
                trace!(
                    "MD PriceUpdate: {}",
                    serde_json::to_string(&price_update).unwrap()
                );

                self.md_message.push(md::Message { price_update });
            }
            md::MsgType::Trade => {
                let trade = unsafe { msg.trade };
                debug!("MD Trade: {}", serde_json::to_string(&trade).unwrap());

                self.md_message.push(md::Message { trade });
            }
            msg_type => {
                if TryInto::<md::MsgType>::try_into(msg_type as u16).is_err() {
                    anyhow::bail!("Bad msg type: {}", msg_type as u16,);
                }
                trace!("Ignore {:?}", msg_type);
            }
        }

        self.tick_tables
            .values_mut()
            .for_each(|entries| entries.sort_unstable_by(|a, b| a.1.partial_cmp(&b.1).unwrap()));

        Ok(None)
    }

    /// Request a replay of Market Data messages within given range
    async fn request_md_replay(
        &mut self,
        replay_addr: SocketAddr,
        range: Range<u32>,
    ) -> Result<()> {
        // Establish a TCP connection to replay service.
        let mut stream = TcpStream::connect(replay_addr)
            .await
            .context("Failed connecting to market data replay service")?;

        let seq_num = if range.start == 0 { 1 } else { range.start };

        let replay_request = replay::ReplayRequest {
            header: replay::ReplayHeader::new(replay::ReplayMsgType::ReplayRequest),
            seq_num,
            end_seq_num: range.end,
            stream_id: 0
        };

        stream
            .write_all_buf(&mut replay_request.as_slice())
            .await
            .context("Failed sending replay request")?;

        let mut replay_rx = FramedRead::new(stream.split().0, create_md_codec());

        loop {
            let bytes = replay_rx.next().await;
            let bytes = if let Some(Ok(bytes)) = bytes {
                bytes
            } else {
                break;
            };

            // Replay stream is finished with two bytes zero pair
            if bytes.len() < std::mem::size_of::<md::Header>() {
                break;
            }

            let msg = unsafe { &mut *(bytes.as_ptr() as *mut md::Message) };

            if self.match_message(msg).is_err() {
                break;
            }
        }

        Ok(())
    }

    async fn pull_msg_from_md(
        &mut self,
        md_framed: &mut UdpFramed<LengthDelimitedCodec>,
    ) -> Result<Option<Range<u32>>> {
        loop {
            let msg = md_framed
                .next()
                .await
                .context("No MD message")?
                .context("MD codec error")?
                .0;
            let msg = unsafe { &mut *(msg.as_ptr() as *mut md::Message) };

            if msg.msg_type() == md::MsgType::Heartbeat {
                continue;
            }

            return self.match_message(msg);
        }
    }
}
