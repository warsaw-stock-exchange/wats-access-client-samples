use std::convert::TryInto;

use crate::messages::replay::{
  ReplayHeader, ReplayMessage, MsgLength, ReplayMsgType, ReplayRequest, SeqNum,
};

impl ReplayHeader {
  pub fn new(replay_msg_type: ReplayMsgType) -> Self {
    Self {
      length: ReplayMessage::size_of(replay_msg_type) as u16,
      replay_msg_type,
    }
  }
}

impl ReplayMessage {
  pub fn new_replay_request(seq_num: SeqNum, end_seq_num: SeqNum) -> Self {
    Self {
      replay_request: ReplayRequest::new(seq_num, end_seq_num),
    }
  }

  pub fn msg_type(&self) -> ReplayMsgType {
    unsafe { self.replay_request.header.replay_msg_type }
  }

  pub fn is_valid(bytes: &[u8]) -> bool {
    if bytes.len() > std::mem::size_of::<Self>() {
      return false;
    }

    let length = bytes[memoffset::span_of!(ReplayHeader, length)].try_into();

    if let Ok(length) = length {
      if bytes.len() != MsgLength::from_le_bytes(length) as usize {
        return false;
      }
    } else {
      return false;
    };

    let msg_type = bytes[memoffset::span_of!(ReplayHeader, replay_msg_type)].try_into();

    if let Ok(msg_type) = msg_type {
      Self::is_valid_variant(u16::from_le_bytes(msg_type), bytes)
    } else {
      false
    }
  }
}

impl ReplayRequest {
  pub fn new(seq_num: SeqNum, end_seq_num: SeqNum) -> Self {
    Self {
      header: ReplayHeader::new(ReplayMsgType::ReplayRequest),
      seq_num,
      end_seq_num,
    }
  }
}

#[cfg(test)]
mod tests {
  use super::*;

  fn as_bytes(msg: &ReplayMessage) -> &[u8] {
    let ptr = msg as *const ReplayMessage as *const u8;
    let len = std::mem::size_of::<ReplayMessage>();
    unsafe { std::slice::from_raw_parts(ptr, len) }
  }

  #[test]
  fn valid_msg() {
    let msg = ReplayMessage::new_replay_request(0, 10);
    assert!(ReplayMessage::is_valid(as_bytes(&msg)));
  }

  #[test]
  fn invalid_msg() {
    let mut msg = ReplayMessage::new_replay_request(0, 10);

    assert!(ReplayMessage::is_valid(as_bytes(&msg)));
    assert!(!ReplayMessage::is_valid(&as_bytes(&msg)[1..]));

    unsafe {
      msg.replay_request.header.replay_msg_type =
        *(&255u16 as *const u16 as *const ReplayMsgType);
    }

    assert!(!ReplayMessage::is_valid(as_bytes(&msg)));
  }
}
