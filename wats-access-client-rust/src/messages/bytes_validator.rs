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
