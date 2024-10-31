use crate::messages::{
  bytes_validator::BytesValidator, generated::market_data as md,
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
  pub const VERSION: super::md::MsgVersion = 0x1000;
}

impl md::Message {
  #[inline(always)]
  fn get_header(&self) -> &md::Header {
    unsafe { &self.heartbeat.header }
  }

  #[inline(always)]
  fn get_header_mut(&mut self) -> &mut md::Header {
    unsafe { &mut self.heartbeat.header }
  }

  #[inline(always)]
  pub fn length(&self) -> usize {
    usize::from(self.get_header().length)
  }

  #[inline(always)]
  pub fn set_seq_num(&mut self, seq_num: md::SeqNum) {
    self.get_header_mut().seq_num = seq_num;
  }

  #[inline(always)]
  pub fn seq_num(&self) -> md::SeqNum {
    self.get_header().seq_num
  }

  #[inline(always)]
  pub fn set_timestamp(&mut self, timestamp: md::Timestamp) {
    self.get_header_mut().timestamp = timestamp;
  }

  #[inline(always)]
  pub fn timestamp(&self) -> md::Timestamp {
    self.get_header().timestamp
  }

  #[inline(always)]
  pub fn set_source_timestamp(&mut self, timestamp: md::Timestamp) {
    self.get_header_mut().source_timestamp = timestamp;
  }

  #[inline(always)]
  pub fn source_timestamp(&self) -> md::Timestamp {
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

  pub fn new_heartbeat() -> md::Message {
    md::Message {
      heartbeat: md::Heartbeat {
        header: md::Header::new(md::MsgType::Heartbeat),
      },
    }
  }

  #[inline(always)]
  pub fn msg_type(&self) -> md::MsgType {
    self.get_header().msg_type
  }
}

impl md::Header {
  #[inline(always)]
  pub fn new(msg_type: md::MsgType) -> md::Header {
    md::Header {
      length: md::Message::size_of(msg_type) as u16,
      version: md_version::VERSION,
      msg_type,
      seq_num: 0,
      timestamp: md::Timestamp::new(0),
      source_timestamp: md::Timestamp::new(0),
      is_encrypted: false,
      encryption_key_id: 0,
      encryption_offset: 0,
      session_id: 0,
      stream_id: 0
    }
  }
}

impl Default for md::Message {
  fn default() -> Self {
    Self {
      heartbeat: md::Heartbeat {
        header: md::Header {
          msg_type: md::MsgType::Heartbeat,
          length: std::mem::size_of::<md::Heartbeat>() as u16,
          ..Default::default()
        }
      },
    }
  }
}

struct Invalid;

/// Get message lenght out of `md::Header` bytes
fn get_message_lenght(bytes: &[u8]) -> Result<md::MsgLength, Invalid> {
  bytes[memoffset::span_of!(md::Header, length)]
    .try_into()
    .map(md::MsgLength::from_le_bytes)
    .map_err(|_| Invalid)
}

/// Get message type out of `md::Header` bytes
fn get_msg_type(bytes: &[u8]) -> Result<md::MsgType, Invalid> {
  let value = bytes[memoffset::span_of!(md::Header, msg_type)]
    .try_into()
    .map(u16::from_le_bytes)
    .map_err(|_| Invalid)?;

  value.try_into().map_err(|_| Invalid)
}

impl md::Login {
  fn is_valid_inner(bytes: &[u8]) -> Result<(), Invalid> {
    // Is buffer the same size as md::Login struct
    let size_valid = bytes.len() == std::mem::size_of::<Self>();
    // Is declared size correct
    let lenght_valid = bytes.len() == get_message_lenght(bytes)? as usize;
    let type_valid = md::MsgType::Login == get_msg_type(bytes)?;

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

impl md::Timestamp {
  pub const fn new(value: u64) -> Self {
    Self(value)
  }

  pub const fn saturating_sub(self, rhs: Self) -> Self {
    Self(self.0.saturating_sub(rhs.0))
  }
}

impl From<u64> for md::Timestamp {
  fn from(value: u64) -> Self {
    md::Timestamp::new(value)
  }
}

impl fmt::Display for md::Timestamp {
  fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
    self.0.fmt(f)
  }
}
