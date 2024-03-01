#![allow(clippy::absurd_extreme_comparisons)]

use crate::{
  errors::ConversionError,
  messages::generated::{trading_port as tp},
};
use std::fmt;

macro_rules! bit_mask {
  ($name:ident) => {
    mod $name {
      pub(super) const fn bit_mask(len: u32) -> $name {
        let mut out: $name = 0b0;
        while out.trailing_ones() < len {
          out <<= 1;
          out |= 0b1;
        }

        out
      }
    }
  };
}

bit_mask!(u8);
bit_mask!(u16);
bit_mask!(u32);

/// connection_id is 14 bits long, see [`tp::OrderId::new`] for details
pub const CONNECTION_ID_MAX: u16 = u16::bit_mask(14);
/// session_id is 14 bits long, see [`tp::OrderId::new`] for details
pub const SESSION_ID_MAX: u16 = u16::bit_mask(14);
/// bulk_seq_num is 8 bits long, see [`tp::OrderId::new`] for details
pub const BULK_SEQ_NUM_MAX: u8 = u8::bit_mask(8);
/// seq_num is 28 bits long, see [`tp::OrderId::new`] for details
pub const SEQ_NUM_MAX: u32 = u32::bit_mask(28);

impl tp::OrderId {
  const CONNECTION_ID_SHIFT: u32 =
    SESSION_ID_MAX.trailing_ones() + Self::SESSION_ID_SHIFT;
  const SESSION_ID_SHIFT: u32 =
    BULK_SEQ_NUM_MAX.trailing_ones() + Self::BULK_SEQ_NUM_SHIFT;
  const BULK_SEQ_NUM_SHIFT: u32 = SEQ_NUM_MAX.trailing_ones();

  /// Assemble order_id from its components:
  /// ```text
  /// [connection_id][session_id][bulk_seq_num][seq_num]
  /// [63         50][49      36][35        28][27    0]
  /// [   14 bits   ][  14 bits ][   8 bits   ][28 bits]
  /// ```
  ///
  /// Returns `Ok(OrderId)` or `Err(ConversionError)` if components do not fit into above memory layout.
  pub fn new(
    connection_id: tp::ConnectionId,
    session_id: tp::SessionId,
    bulk_seq_num: tp::BulkSeqNum,
    seq_num: tp::SeqNum,
  ) -> Result<Self, ConversionError> {
    let mut result = String::new();

    if connection_id > CONNECTION_ID_MAX {
      result.push_str(
        format!(
          "connection_id({connection_id}) exceeds max value({CONNECTION_ID_MAX});",
        )
        .as_str(),
      );
    }

    if session_id > SESSION_ID_MAX {
      result.push_str(
        format!(
          "session_id({session_id}) exceeds max value({SESSION_ID_MAX});",
        )
        .as_str(),
      );
    }

    if bulk_seq_num > BULK_SEQ_NUM_MAX {
      result.push_str(
        format!(
          "bulk_seq_num({bulk_seq_num}) exceeds max value({BULK_SEQ_NUM_MAX});",
        )
        .as_str(),
      );
    }

    if seq_num > SEQ_NUM_MAX {
      result.push_str(
        format!("seq_num({seq_num}) exceeds max value({SEQ_NUM_MAX});")
          .as_str(),
      );
    }

    if !result.is_empty() {
      return Err(ConversionError(format!(
        "OrderId components are too big: {result}",
      )));
    }

    Ok(Self(
      u64::from(connection_id) << Self::CONNECTION_ID_SHIFT
        | u64::from(session_id) << Self::SESSION_ID_SHIFT
        | u64::from(bulk_seq_num) << Self::BULK_SEQ_NUM_SHIFT
        | u64::from(seq_num),
    ))
  }

  pub fn connection_id(&self) -> tp::ConnectionId {
    ((self.0 >> Self::CONNECTION_ID_SHIFT) as tp::ConnectionId)
      & CONNECTION_ID_MAX
  }

  pub fn session_id(&self) -> tp::SessionId {
    ((self.0 >> Self::SESSION_ID_SHIFT) as tp::SessionId) & SESSION_ID_MAX
  }

  pub fn bulk_seq_num(&self) -> tp::BulkSeqNum {
    ((self.0 >> Self::BULK_SEQ_NUM_SHIFT) as tp::BulkSeqNum) & BULK_SEQ_NUM_MAX
  }

  pub fn seq_num(&self) -> tp::SeqNum {
    (self.0 as tp::SeqNum) & SEQ_NUM_MAX
  }
}

impl fmt::Display for tp::OrderId {
  fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
    self.0.fmt(f)
  }
}

#[cfg(test)]
mod tests {

  use super::tp;

  #[test]
  fn order_id_components() {
    let connection_id = super::CONNECTION_ID_MAX;
    let session_id = super::SESSION_ID_MAX;
    let bulk_seq_num = super::BULK_SEQ_NUM_MAX;
    let seq_num = super::SEQ_NUM_MAX;

    let order_id =
      tp::OrderId::new(connection_id, session_id, bulk_seq_num, seq_num)
        .unwrap();

    assert_eq!(order_id.connection_id(), connection_id);
    assert_eq!(order_id.session_id(), session_id);
    assert_eq!(order_id.bulk_seq_num(), bulk_seq_num);
    assert_eq!(order_id.seq_num(), seq_num);
  }

  #[test]
  fn order_id_components_too_big() {
    tp::OrderId::new(u16::MAX, 0, 0, 0).unwrap_err();
    tp::OrderId::new(0, u16::MAX, 0, 0).unwrap_err();
    assert_eq!(
      tp::OrderId::new(0, 0, u8::MAX, 0).unwrap().bulk_seq_num(),
      u8::MAX
    );
    tp::OrderId::new(0, 0, 0, u32::MAX).unwrap_err();
  }

  #[test]
  fn order_id_consts() {
    assert_eq!(super::CONNECTION_ID_MAX.trailing_ones(), 14);
    assert_eq!(super::SESSION_ID_MAX.trailing_ones(), 14);
    assert_eq!(super::BULK_SEQ_NUM_MAX.trailing_ones(), 8);
    assert_eq!(super::SEQ_NUM_MAX.trailing_ones(), 28);

    assert_eq!(tp::OrderId::CONNECTION_ID_SHIFT, 50);
    assert_eq!(tp::OrderId::SESSION_ID_SHIFT, 36);
    assert_eq!(tp::OrderId::BULK_SEQ_NUM_SHIFT, 28);
  }
}
