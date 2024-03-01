use crate::messages::{
  bytes_validator::BytesValidator, generated::market_data,
};
use std::{convert::TryInto, fmt};

// TODO: Ideally it should be exported by md module, generated from BenDec files.
//       But right now version number is not stored in BenDec files.
//       For now export it directly. If version is moved to BenDec files,
//       then we'll use generated definition.
pub mod md_version {
  /// BCD encrypted version of OMD protocol. Version is coded as
  /// - high nibble of MSB - major protocol version
  /// - low nibble of MSB - minor protocol version
  /// - high nibble of LSB - release version
  /// - low nibble of LSB - patch-level version
  pub const VERSION: super::market_data::MsgVersion = 0x1000;
}

impl market_data::Message {
  #[inline(always)]
  fn get_header(&self) -> &market_data::Header {
    unsafe { &self.heartbeat.header }
  }

  #[inline(always)]
  fn get_header_mut(&mut self) -> &mut market_data::Header {
    unsafe { &mut self.heartbeat.header }
  }

  #[inline(always)]
  pub fn length(&self) -> usize {
    usize::from(self.get_header().length)
  }

  #[inline(always)]
  pub fn set_seq_num(&mut self, seq_num: market_data::SeqNum) {
    self.get_header_mut().seq_num = seq_num;
  }

  #[inline(always)]
  pub fn seq_num(&self) -> market_data::SeqNum {
    self.get_header().seq_num
  }

  #[inline(always)]
  pub fn set_timestamp(&mut self, timestamp: market_data::Timestamp) {
    self.get_header_mut().timestamp = timestamp;
  }

  #[inline(always)]
  pub fn timestamp(&self) -> market_data::Timestamp {
    self.get_header().timestamp
  }

  #[inline(always)]
  pub fn set_source_timestamp(&mut self, timestamp: market_data::Timestamp) {
    self.get_header_mut().source_timestamp = timestamp;
  }

  #[inline(always)]
  pub fn source_timestamp(&self) -> market_data::Timestamp {
    self.get_header().source_timestamp
  }

  #[inline(always)]
  pub fn is_encrypted(&self) -> bool {
    self.get_header().is_encrypted
  }

  /// Set encryption flag (the message would be marked as encrypted)
  #[inline(always)]
  pub fn set_is_encrypted(&mut self, encrypted: bool) {
    self.get_header_mut().is_encrypted = encrypted;
  }

  /// Set encryption key id
  #[inline(always)]
  pub fn set_encryption_key_id(&mut self, key: u32) {
    self.get_header_mut().encryption_key_id = key;
  }

  #[inline(always)]
  pub fn encryption_key_id(&self) -> u32 {
    self.get_header().encryption_key_id
  }

  /// Set encryption flag (the message would be marked as encrypted)
  #[inline(always)]
  pub fn set_encryption_offset(&mut self, offset: u64) {
    self.get_header_mut().encryption_offset = offset;
  }

  #[inline(always)]
  pub fn encryption_key_offset(&self) -> u64 {
    self.get_header().encryption_offset
  }

  #[inline(always)]
  pub fn set_default_version(&mut self) {
    self.get_header_mut().version = md_version::VERSION;
  }

  pub fn new_heartbeat() -> market_data::Message {
    market_data::Message {
      heartbeat: market_data::Heartbeat {
        header: market_data::Header::new(market_data::MsgType::Heartbeat),
      },
    }
  }

  #[inline(always)]
  pub fn msg_type(&self) -> market_data::MsgType {
    self.get_header().msg_type
  }
}

impl market_data::Header {
  #[inline(always)]
  pub fn new(msg_type: market_data::MsgType) -> market_data::Header {
    market_data::Header {
      length: market_data::Message::size_of(msg_type) as u16,
      version: md_version::VERSION,
      msg_type,
      seq_num: 0,
      timestamp: market_data::Timestamp::new(0),
      source_timestamp: market_data::Timestamp::new(0),
      is_encrypted: false,
      encryption_key_id: 0,
      encryption_offset: 0,
    }
  }
}

impl Default for market_data::Message {
  fn default() -> Self {
    Self {
      test: market_data::Test {
        header: market_data::Header {
          msg_type: market_data::MsgType::Test,
          length: std::mem::size_of::<market_data::Test>() as u16,
          ..Default::default()
        },
        ..Default::default()
      },
    }
  }
}

struct Invalid;

/// Get message lenght out of `market_data::Header` bytes
fn get_message_lenght(bytes: &[u8]) -> Result<market_data::MsgLength, Invalid> {
  bytes[memoffset::span_of!(market_data::Header, length)]
    .try_into()
    .map(market_data::MsgLength::from_le_bytes)
    .map_err(|_| Invalid)
}

/// Get message type out of `market_data::Header` bytes
fn get_msg_type(bytes: &[u8]) -> Result<market_data::MsgType, Invalid> {
  let value = bytes[memoffset::span_of!(market_data::Header, msg_type)]
    .try_into()
    .map(u16::from_le_bytes)
    .map_err(|_| Invalid)?;

  value.try_into().map_err(|_| Invalid)
}

impl market_data::Login {
  fn is_valid_inner(bytes: &[u8]) -> Result<(), Invalid> {
    // Is buffer the same size as market_data::Login struct
    let size_valid = bytes.len() == std::mem::size_of::<Self>();
    // Is declared size correct
    let lenght_valid = bytes.len() == get_message_lenght(bytes)? as usize;
    let type_valid = market_data::MsgType::Login == get_msg_type(bytes)?;

    if size_valid
      && lenght_valid
      && type_valid
      // SAFETY: We checked that the size is valid
      && unsafe { <Self as BytesValidator>::is_valid(bytes) }
    {
      Ok(())
    } else {
      Err(Invalid)
    }
  }

  pub fn is_valid(bytes: &[u8]) -> bool {
    Self::is_valid_inner(bytes).is_ok()
  }
}

impl market_data::Timestamp {
  pub const fn new(value: u64) -> Self {
    Self(value)
  }

  pub const fn saturating_sub(self, rhs: Self) -> Self {
    Self(self.0.saturating_sub(rhs.0))
  }
}

impl From<u64> for market_data::Timestamp {
  fn from(value: u64) -> Self {
    market_data::Timestamp::new(value)
  }
}

impl fmt::Display for market_data::Timestamp {
  fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
    self.0.fmt(f)
  }
}
