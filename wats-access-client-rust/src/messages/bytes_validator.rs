macro_rules! always_valid {
  ($type:ty) => {
    impl BytesValidator for $type {
      #[inline]
      unsafe fn is_valid(bytes: &[u8]) -> bool {
        debug_assert_eq!(bytes.len(), std::mem::size_of::<Self>());
        true
      }
    }
    impl<const N: usize> BytesValidator for [$type; N] {
      #[inline]
      unsafe fn is_valid(bytes: &[u8]) -> bool {
        debug_assert_eq!(bytes.len(), std::mem::size_of::<Self>());
        true
      }
    }
  };
}

macro_rules! slice_validation {
  ($type:ty, $array_type:ty) => {
    impl BytesValidator for $type {
      #[inline]
      unsafe fn is_valid(bytes: &[u8]) -> bool {
        debug_assert_eq!(bytes.len(), std::mem::size_of::<Self>());
        let count_bytes =
          bytes.get_unchecked(memoffset::span_of!($type, count));

        let count = std::convert::TryInto::try_into(count_bytes)
          .map(u8::from_le_bytes)
          .unwrap_unchecked();

        let items = bytes.get_unchecked(memoffset::span_of!($type, items));

        items
          .chunks(std::mem::size_of::<$array_type>())
          .take(count as usize)
          .all(|chunk_bytes| <$array_type>::is_valid(chunk_bytes))
      }
    }
  };
}

pub(crate) trait BytesValidator {
  /// # Safety
  ///
  /// It is a caller's responsibility to make sure that the bytes slice has the same length
  /// as the type size this trait is implemented for. Otherwise, it is undefined behaviour.
  unsafe fn is_valid(bytes: &[u8]) -> bool;
}

impl BytesValidator for bool {
  #[inline]
  unsafe fn is_valid(bytes: &[u8]) -> bool {
    debug_assert_eq!(bytes.len(), std::mem::size_of::<Self>());
    matches!(bytes.first().unwrap_unchecked(), 0 | 1)
  }
}

always_valid!(u8);
always_valid!(u16);
always_valid!(u32);
always_valid!(u64);
always_valid!(i64);
always_valid!(super::market_data::Timestamp);

slice_validation!(super::trading_port::Quotes, super::trading_port::Quote);
slice_validation!(super::trading_port::QuoteOrderResponses, super::trading_port::QuoteOrderResponse);

