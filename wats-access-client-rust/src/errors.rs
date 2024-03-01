use std::{
    error::Error,
    fmt::{self, Display, Formatter},
};

/// Used for conversion of integer into enum variant
#[derive(Debug)]
pub struct InvalidVariant {
    value: u32,
    type_name: &'static str,
}

impl InvalidVariant {
    pub fn new(value: u32, type_name: &'static str) -> Self {
        Self { value, type_name }
    }
}

impl Display for InvalidVariant {
    fn fmt(&self, f: &mut Formatter<'_>) -> fmt::Result {
        write!(
            f,
            "value {} is out of range for {}",
            self.value, self.type_name
        )
    }
}

#[derive(Debug)]
pub struct ConversionError(pub String);

impl Display for ConversionError {
    fn fmt(&self, f: &mut Formatter<'_>) -> fmt::Result {
        f.write_str(&self.0)
    }
}

impl Error for ConversionError {}
