const { NewtypeKind } = require('bendec/dist/tools/rsGenerator')
const {
  attachAll,
  attachArrayNewtypeImpl,
  toSourceAbsolute,
  enumConversionError,
  readDefs,
} = require('../shared')
const { attachBytesValidator } = require('./bytesValidator')

const [_tpVersion, tradingPortDefs] = readDefs('trading_port.json')

const enumsWithConstTpMap = Object.fromEntries(
  tradingPortDefs.filter(def => def.kind === 'Enum').map(def => [def.name, { implConst: true }])
)


const tradingPortTypes = {
  input: ['trading_port.json'],
  output: 'trading_port.rs',
  options: {
    camelCase: true,
    // Added to the top of the file - import shared types
    extras: [
      'pub use crate::errors::InvalidVariant;',
      'use crate::messages::bytes_validator::BytesValidator;',
      'use serde_big_array::BigArray;',
    ],
    defaultDerives: {
      struct: ['Serialize', 'Deserialize', 'Clone', 'Copy']
    },
    extraDerives: {
      'Header': ['Default'],
      'Heartbeat': ['Default'],
      'MifidFlags': ['Default'],
      'MifidField': ['Default'],
      'MifidFields': ['Default'],
      'OrderAdd': ['Default'],
      'OrderCancel': ['Default'],
      'OrderModify': ['Default'],
      'OrderId': ['Clone', 'Copy', 'Debug', 'Default', 'Serialize', 'Deserialize', 'PartialEq', 'Eq', 'Hash'],
      'Quote': ['Default'],
      'QuoteOrder': ['Default'],
      'Timestamp': ['Clone', 'Copy', 'Debug', 'Default', 'Ord', 'PartialOrd', 'Eq', 'PartialEq', 'Serialize', 'Deserialize'],
      'ScenarioName': ['Debug', 'Serialize', 'Deserialize'],
      'TradeCaptureReportSingle': ['Default'],
      'TradeCaptureReportDual': ['Default'],
      'TcrParty': ['Default'],
    },
    meta: {
      'OrderId': {
        newtype: {
          kind: 'InCrate',
          module: 'crate::messages'
        }
      },
      'Timestamp': {
        newtype: {
          kind: NewtypeKind.InCrate,
          deref: false,
          asInner: true,
          constr: false
        }
      },
      'TestEvent': {
        fields: {
          'scenarioName': {
            annotations: ['#[serde(with = "BigArray")]']
          }
        }
      },
      'Quotes': {
        skipBytesValidator: true,
      },
      'QuoteOrderResponses': {
        skipBytesValidator: true,
      },
      ...enumsWithConstTpMap
    },
    enumConversionError,
    forEachType: attachAll([attachArrayNewtypeImpl, attachBytesValidator]),
    transparentBitflags: true,
  }
}

module.exports = { tradingPortTypes }
