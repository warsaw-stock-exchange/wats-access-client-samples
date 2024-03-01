const _ = require('lodash')
const path = require('path')
const fs = require('fs')
const { Kind } = require('bendec/dist/types')
const { NewtypeKind } = require('bendec/dist/tools/rsGenerator')

const MESSAGES_DEFINITIONS_ROOT = path.resolve(__dirname, '..', 'messages')

const toSourceAbsolute = filename => {
  return path.resolve(MESSAGES_DEFINITIONS_ROOT, filename)
}

const readDefs = (path, process) => {
  defs = path !== ''
    ? JSON.parse(fs.readFileSync(toSourceAbsolute(path), 'utf-8'))
    : undefined

  if (process === undefined) {
    return [defs.version, defs.typeDefinitions]
  } else if (defs !== undefined) {
    return [defs.version, process(defs.typeDefinitions)]
  } else {
    return [-1, process()]
  }
}

const nested = (name) => (defs) => defs[name]

const enumConversionError = {
  type: 'InvalidVariant',
  constructor: 'InvalidVariant::new(other as u32, "{{ name }}")'
}

/// Attach all generators in given order
const attachAll = generators => {
  return ([generated, context, meta]) => {
    for (const generator of generators) {
      generated = generator([generated, context, meta])
    }
    return generated
  }
}

const arrayNewtypeImpl = (meta, array) => {
  if (meta?.newtype) {
    if (array.type == 'u8') {
      return `crate::byte_array!(${array.length}, ${array.name});\n`
    }

    if (array.type == 'AnsiChar') {
      return `crate::char_array!(${array.length}, ${array.name});\n`
    }

    throw new Error(`Unsupported array.type value: ${array.type}`)
  }

  return ""
}

/// Attach common traits and method impls depending on newtype array type
const attachArrayNewtypeImpl = ([generated, context, meta]) => {
  if (context.kind == Kind.Array) {
    return [generated, arrayNewtypeImpl(meta, context)].join('\n')
  } else if (context.kind in Kind) {
    return generated
  } else {
    throw new Error(`Kind does not match any known variant: ${context.kind}`);
  }
}

const publicNewtype = {
  newtype: {
    kind: NewtypeKind.Public,
    deref: false,
    asInner: true,
    constr: true
  }
}

const crateNewtype = {
  newtype: {
    kind: NewtypeKind.InCrate,
    deref: false,
    asInner: true,
    constr: true
  }
}

const resolveReplayTypes = () => {
  const [_mdv, mdReplay] = readDefs('market_data.json', nested('replay'))
  return mdReplay
}

module.exports = {
  attachAll,
  attachArrayNewtypeImpl,
  toSourceAbsolute,
  readDefs,
  nested,
  resolveReplayTypes,
  enumConversionError,
  crateNewtype,
  publicNewtype,
}

