#[doc(hidden)]
#[macro_export]
macro_rules! char_array {
  ($size:literal, $( $name:path ),*) => {
    $(
        impl $name {
            pub fn as_str(&self) -> &str {
              let i = self.0.iter().position(|v| *v == 0).unwrap_or(self.0.len());
              std::str::from_utf8(&self.0[0..i]).unwrap()
            }
          }

          impl serde::Serialize for $name {
        fn serialize<S>(&self, serializer: S) -> Result<S::Ok, S::Error>
        where
          S: serde::Serializer,
        {
          serializer.serialize_str(self.as_str())
        }
      }

      impl<'de> serde::Deserialize<'de> for $name {
        fn deserialize<D>(deserializer: D) -> Result<Self, D::Error>
        where
          D: serde::Deserializer<'de>,
        {
          let s = String::deserialize(deserializer)?;
          std::convert::TryFrom::try_from(s.as_str()).map_err(|_| {
            serde::de::Error::invalid_value(
              serde::de::Unexpected::Str(&s),
              &std::format!("string of length at most {}", $size).as_str(),
            )
          })
        }
      }

      impl ::std::convert::TryFrom<&str> for $name {
        type Error = $crate::errors::ConversionError;
        fn try_from(value: &str) -> Result<Self, Self::Error> {
          let mut result = [0_u8; $size];
          $crate::messages::macros::copy_utf_to_slice(&value, &mut result)?;
          Ok(Self(result))
        }
      }

      impl ::std::convert::TryFrom<&[u8]> for $name {
        type Error = $crate::errors::ConversionError;
        fn try_from(value: &[u8]) -> Result<Self, Self::Error> {
          let s = ::std::str::from_utf8(value)
            .map_err(|e| $crate::errors::ConversionError(e.to_string()))?;
            std::convert::TryFrom::try_from(s)
        }
      }

      impl From<&$name> for String {
        fn from(value: &$name) -> Self {
          value.as_str().to_string()
        }
      }

      // Implement Display as otherwise character arrays are serialized as
      // arrays of bytes which makes it difficult to read
      impl ::std::fmt::Display for $name {
        fn fmt(&self, f: &mut ::std::fmt::Formatter<'_>) -> ::std::fmt::Result {
          f.write_str(self.as_str())
        }
      }
    )*
  };
}

#[doc(hidden)]
#[macro_export]
macro_rules! byte_array {
  ($size:literal, $( $name:path ),*) => {
    $(
      impl serde::Serialize for $name {
        fn serialize<S>(&self, serializer: S) -> Result<S::Ok, S::Error>
        where
          S: serde::Serializer,
        {
          serializer.serialize_bytes(&self.0)
        }
      }

      impl<'de> serde::Deserialize<'de> for $name {
        fn deserialize<D>(deserializer: D) -> Result<Self, D::Error>
        where
          D: serde::Deserializer<'de>,
        {
          struct BinaryVisitor;

          impl<'de> serde::de::Visitor<'de> for BinaryVisitor {
            type Value = $name;

            fn expecting(&self, formatter: &mut std::fmt::Formatter) -> std::fmt::Result {
              std::write!(formatter, "hex string or byte array of length at most {}", $size)
            }

            fn visit_str<E>(self, string: &str) -> Result<Self::Value, E>
            where
              E: serde::de::Error,
            {
              std::convert::TryFrom::try_from(string).map_err(|_| {
                serde::de::Error::invalid_value(serde::de::Unexpected::Str(string), &self)
              })
            }

            fn visit_seq<A>(self, mut seq: A) -> Result<Self::Value, A::Error>
            where
              A: serde::de::SeqAccess<'de>,
            {
              let mut bytes = Vec::<u8>::with_capacity($size);
              while let Some(byte) = seq.next_element::<u8>()? {
                bytes.push(byte);
              }

              self.visit_bytes(bytes.as_slice())
            }

            fn visit_bytes<E>(self, bytes: &[u8]) -> Result<Self::Value, E>
            where
              E: serde::de::Error,
            {
              std::convert::TryFrom::try_from(bytes).map_err(|_| {
                serde::de::Error::invalid_value(serde::de::Unexpected::Bytes(bytes), &self)
              })
            }
          }

          deserializer.deserialize_any(BinaryVisitor)
        }
      }

      impl ::std::convert::TryFrom<&str> for $name {
        type Error = $crate::errors::ConversionError;
        fn try_from(value: &str) -> Result<Self, Self::Error> {
          let bytes = hex::decode(value)
            .map_err(|err| $crate::errors::ConversionError(err.to_string()))?;
          std::convert::TryFrom::try_from(bytes.as_slice())
        }
      }

      impl ::std::convert::TryFrom<&[u8]> for $name {
        type Error = $crate::errors::ConversionError;
        fn try_from(bytes: &[u8]) -> Result<Self, Self::Error> {
          let mut result = [0_u8; $size];
          $crate::messages::macros::copy_bytes_to_slice(bytes, &mut result)?;
          Ok(Self(result))
        }
      }

      impl From<&$name> for String {
        fn from(value: &$name) -> String {
          hex::encode(value.0.as_slice())
        }
      }
    )*
  };
}

#[cfg(test)]
pub(crate) fn to_i64(
  value: rust_decimal::Decimal,
) -> Result<i64, crate::errors::ConversionError> {
  use rust_decimal::{prelude::ToPrimitive, Decimal};
  let scaled = match value.checked_mul(Decimal::new(100_000_000, 0)) {
    Some(scaled) => scaled,
    None => {
      return Err(crate::errors::ConversionError(
        "This Decimal overflowed during scaling".to_string(),
      ))
    }
  };

  match scaled.trunc().to_i64() {
    Some(truncated) => Ok(truncated),
    None => Err(crate::errors::ConversionError(
      "This Decimal cannot be represented as i64".to_string(),
    )),
  }
}

// **precondition**: `to` must be zero-initialized
pub(crate) fn copy_utf_to_slice<'c>(
  from: &'c &str,
  to: &'c mut [u8],
) -> Result<(), crate::errors::ConversionError> {
  debug_assert!(to.iter().all(|b| *b == 0));
  let bytes = from.as_bytes();
  let utf_length = bytes.len();
  let slice_length = std::mem::size_of_val(to);
  if utf_length <= slice_length {
    to[..utf_length].copy_from_slice(bytes);
    Ok(())
  } else {
    Err(crate::errors::ConversionError(format!(
      "string '{from}' ({utf_length} bytes) does not fit into [u8;{slice_length}]"
    )))
  }
}

#[cfg(test)]
mod tests {
  use super::to_i64;
  use assert_matches::assert_matches;
  use rust_decimal::Decimal;

  #[test]
  fn decimal_to_price_conversion() {
    assert_matches!(to_i64(Decimal::new(0, 8)), Ok(0));
    assert_matches!(to_i64(Decimal::new(1, 8)), Ok(1));
    assert_matches!(to_i64(Decimal::new(i64::MAX, 8)), Ok(i64::MAX));
  }
}
