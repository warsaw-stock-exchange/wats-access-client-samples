use crate::messages::generated::trading_port as tp;
use std::convert::TryInto;

impl tp::Message {
  #[inline(always)]
  pub fn set_seq_num(&mut self, seq_num: tp::SeqNum) {
    self.get_header_mut().seq_num = seq_num;
  }

  #[inline(always)]
  pub fn seq_num(&self) -> tp::SeqNum {
    self.get_header().seq_num
  }

  #[inline(always)]
  fn get_header(&self) -> &tp::Header {
    unsafe { &self.test.header }
  }

  #[inline(always)]
  fn get_header_mut(&mut self) -> &mut tp::Header {
    unsafe { &mut self.test.header }
  }

  #[inline(always)]
  pub fn length(&self) -> usize {
    usize::from(self.get_header().length)
  }

  #[inline(always)]
  pub fn timestamp(&self) -> tp::Timestamp {
    self.get_header().timestamp
  }

  // TODO: We should make sure the variant is of these union
  /// # Safety
  pub unsafe fn as_variant<T>(&self) -> &T {
    &*(self as *const Self as *const T)
  }

  // TODO: We should make sure the variant is of these union
  /// # Safety
  pub unsafe fn as_variant_mut<T>(&mut self) -> &mut T {
    &mut *(self as *mut Self as *mut T)
  }

  #[inline(always)]
  pub fn msg_type(&self) -> tp::MsgType {
    self.get_header().msg_type
  }
}

impl tp::Header {
  #[inline(always)]
  pub fn new(msg_type: tp::MsgType) -> tp::Header {
    tp::Header {
      length: tp::Message::size_of(msg_type) as u16,
      msg_type,
      seq_num: 0,
      timestamp: tp::Timestamp::new(0),
      source_timestamp: tp::Timestamp::new(0),
    }
  }
}

impl tp::Message {
  pub fn is_valid(bytes: &[u8]) -> bool {
    // This should already be checked in LengthDelimitedCodec
    debug_assert!(bytes.len() <= std::mem::size_of::<tp::Message>());

    let length = unsafe {
      bytes[memoffset::span_of!(tp::Header, length)]
        .try_into()
        .unwrap_unchecked()
    };
    let length = usize::from(tp::MsgLength::from_le_bytes(length));

    // This should already be checked in LengthDelimitedCodec
    debug_assert_eq!(bytes.len(), length);

    let msg_type = unsafe {
      bytes[memoffset::span_of!(tp::Header, msg_type)]
        .try_into()
        .unwrap_unchecked()
    };
    let msg_type = u16::from_le_bytes(msg_type);

    Self::is_valid_variant(msg_type, bytes)
  }
}

impl From<u64> for tp::Timestamp {
  fn from(value: u64) -> Self {
    Self::new(value)
  }
}

impl tp::Timestamp {
  pub const fn new(value: u64) -> Self {
    Self(value)
  }
}

impl Default for tp::Message {
  fn default() -> Self {
    tp::Message {
      test: tp::Test {
        header: tp::Header::new(tp::MsgType::Test),
        target_timestamp: tp::Timestamp::new(0),
      },
    }
  }
}
