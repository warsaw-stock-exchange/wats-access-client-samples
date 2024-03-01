//! Internal utilities for working with raw bytes of binary trading port.

use crate::messages::{market_data, trading_port as tp, replay};
use tokio_util::codec::length_delimited::LengthDelimitedCodec;

/// Returns a `LengthDelimitedCodec` codec for WATS messages.
fn create_codec(max_frame_length: usize, length_field_length: usize) -> LengthDelimitedCodec {
    LengthDelimitedCodec::builder()
        .max_frame_length(max_frame_length) // max frame the client can send
        .little_endian() // LE
        .length_field_offset(0) // length start at index 0
        .length_field_length(length_field_length)
        .length_adjustment(0) // we specify length with the header
        .num_skip(0) // not skipping anything
        .new_codec()
}

/// Returns a `LengthDelimitedCodec` codec for WATS MD messages.
pub(crate) fn create_md_codec() -> LengthDelimitedCodec {
    create_codec(
        std::mem::size_of::<market_data::Message>(),
        std::mem::size_of::<market_data::MsgLength>(),
    )
}

/// Returns a `LengthDelimitedCodec` codec for WATS TP messages.
pub(crate) fn create_tp_codec() -> LengthDelimitedCodec {
    create_codec(
        std::mem::size_of::<tp::Message>(),
        std::mem::size_of::<tp::MsgLength>(),
    )
}

/// Cast a slice of bytes into a `T` reference.
///
/// # Safety
/// It is a caller responsibility to make sure that the slice length match
/// target type size.
///
/// Accessing invalid memory regions may lead to segmentation fault.
pub unsafe fn from_bytes<T>(bytes: &[u8]) -> &T {
    &*(bytes.as_ptr() as *const T)
}

pub trait TradinPortMsg: sealed::TradinPortMsgPriv {
    fn as_slice(&self) -> &[u8]
    where
        Self: Sized,
    {
        unsafe {
            std::slice::from_raw_parts(
                self as *const Self as *const u8,
                std::mem::size_of::<Self>(),
            )
        }
    }
}

mod sealed {
    /// Allow only specific trading port messages. Make sure that this not get
    /// implemented for `tp::Message`
    pub trait TradinPortMsgPriv {}
}

impl sealed::TradinPortMsgPriv for tp::Logout {}
impl TradinPortMsg for tp::Logout {}

impl sealed::TradinPortMsgPriv for tp::Login {}
impl TradinPortMsg for tp::Login {}

impl sealed::TradinPortMsgPriv for tp::OrderAdd {}
impl TradinPortMsg for tp::OrderAdd {}

impl sealed::TradinPortMsgPriv for tp::OrderModify {}
impl TradinPortMsg for tp::OrderModify {}

impl sealed::TradinPortMsgPriv for tp::OrderCancel {}
impl TradinPortMsg for tp::OrderCancel {}

impl sealed::TradinPortMsgPriv for market_data::Login {}
impl TradinPortMsg for market_data::Login {}

impl sealed::TradinPortMsgPriv for replay::ReplayRequest {}
impl TradinPortMsg for replay::ReplayRequest {}
