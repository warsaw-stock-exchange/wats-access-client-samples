const _ = require('lodash')
const { Kind } = require('bendec/dist/types')

function implIsValid(name, body) {
  return `impl BytesValidator for ${name} {
    #[inline]
    unsafe fn is_valid(bytes: &[u8]) -> bool {
      debug_assert_eq!(bytes.len(), std::mem::size_of::<Self>());
      ${body}
    }
  }`
}

function aliasBytesValidator(meta, alias) {
  if (meta?.newtype) {
    return implIsValid(alias.name, `${alias.alias}::is_valid(bytes)`)
  }

  return ""
}

function structBytesValidator(struct) {
  let validateFields
  if (struct.name === 'Empty') {
    validateFields = 'false'
  }
  else if (struct.fields.length === 0) {
    validateFields = 'true'
  }
  else {
    validateFields = struct.fields.map((field) => {
      if (field.length) {
        return `<[${field.type};${field.length}]>::is_valid(bytes.get_unchecked(memoffset::span_of!(${struct.name}, ${_.snakeCase(field.name)})))`
      }

      return `${field.type}::is_valid(bytes.get_unchecked(memoffset::span_of!(${struct.name}, ${_.snakeCase(field.name)})))`
    }).join(' && ')
  }

  return implIsValid(struct.name, validateFields)
}

function enumBytesValidatorWithData(enumType) {
  const msgType = enumType.name === 'Message'
    ? 'MsgType'
    : 'ReplayMsgType'
  const discriminator = _.snakeCase(msgType)
  const validateVariant = enumType.variants.map(([key, disc, comm, data]) => `${msgType}Int::${key} => std::mem::size_of::<${data}>() == length && unsafe { ${data}::is_valid(bytes) },`).join('\n')
  return `impl ${enumType.name} {
    pub(crate) fn is_valid_variant(${discriminator}: ${msgType}IntType, bytes: &[u8]) -> bool {
      let length = bytes.len();
      match ${discriminator} {
        ${validateVariant}
        _ => false,
      }
    }
  }`
}

function enumBytesValidator(enumType) {
  const hasData = enumType.variants.some(([key, disc, comm, data]) => data !== undefined)
  if (hasData) {
    return enumBytesValidatorWithData(enumType)
  }

  const enumIntType = `#[allow(dead_code)]\ntype ${enumType.name}IntType = ${enumType.underlying};\n`;
  const matchVariants = enumType.variants.map(([key, ,]) => `${enumType.name}Int::${key}`).join(' | ')
  const body = `let disc = std::convert::TryInto::try_into(bytes).map(${enumType.underlying}::from_le_bytes).unwrap_unchecked();
  matches!(disc, ${matchVariants})`

  return [enumIntType, implIsValid(enumType.name, body)].join('\n')
}

function bitflagsBytesValidator(enumType) {
  const body = `let disc = std::convert::TryInto::try_into(bytes).map(${enumType.underlying}::from_le_bytes).unwrap_unchecked();
  ${enumType.name}::from_bits(disc).is_some()`

  return implIsValid(enumType.name, body)
}

function unionBytesValidator(union) {
  const discriminator = _.snakeCase(union.discriminator[union.discriminator.length - 1])
  const enum_type = _.startCase(_.camelCase(discriminator)).split(' ').join('')
  const validateVariant = union.members.map((member) => `${enum_type}Int::${member} => std::mem::size_of::<${member}>() == length && unsafe { ${member}::is_valid(bytes) },`).join('\n')
  return `impl ${union.name} {
    pub(crate) fn is_valid_variant(${discriminator}: ${enum_type}IntType, bytes: &[u8]) -> bool {
      let length = bytes.len();
      match ${discriminator} {
        ${validateVariant}
        _ => false,
      }
    }
  }`
}

function arrayBytesValidator(meta, array) {
  if (meta?.newtype) {
    return implIsValid(array.name, `<[${array.type}; ${array.length}]>::is_valid(bytes)`)
  }

  return ""
}

function attachBytesValidator([generated, context, meta]) {
  if (meta?.skipBytesValidator) {
    return generated
  }

  switch (context.kind) {
    case Kind.Primitive:
      return generated
    case Kind.Alias:
      return [generated, aliasBytesValidator(meta, context)].join('\n')
    case Kind.Struct:
      return [generated, structBytesValidator(context)].join('\n')
    case Kind.Enum: {
      if (context.bitflags) {
        return [generated, bitflagsBytesValidator(context)].join('\n')
      }
      return [generated, enumBytesValidator(context)].join('\n')
    }
    case Kind.Union:
      return [generated, unionBytesValidator(context)].join('\n')
    case Kind.Array:
      return [generated, arrayBytesValidator(meta, context)].join('\n')
    default:
      throw new Error(`Kind does not match any known variant: ${context.kind}`);
  }
}

module.exports = { attachBytesValidator }

