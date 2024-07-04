const { NewtypeKind } = require('bendec/dist/tools/rsGenerator')
const {
  attachAll,
  attachArrayNewtypeImpl,
  nested,
  enumConversionError,
  crateNewtype
} = require('../shared')
const { attachBytesValidator } = require('./bytesValidator')

const marketDataTypes = {
  input:['market_data.json', nested('marketData')],
  output: 'market_data.rs',
  options: {
    camelCase: true,
    // Added to the top of the file - import shared types
    extras: [
      'pub use crate::errors::InvalidVariant;',
      'use serde_big_array::BigArray;',
      'use crate::messages::bytes_validator::BytesValidator;'
    ],
    defaultDerives: {
      struct: ['Serialize', 'Deserialize', 'Clone', 'Copy']
    },
    extraDerives: {
      'CfiCode': ['Clone', 'Copy'],
      'Code': ['Clone', 'Copy'],
      'FisnCode': ['Clone', 'Copy'],
      'Header': ['Default'],
      'InstrumentCode': ['Clone', 'Copy'],
      'Issuer': ['Clone', 'Copy'],
      'MicCode': ['Clone', 'Copy'],
      'Name': ['Clone', 'Copy'],
      'PriceLevel': ['Default'],
      'Test': ['Default'],
      'TextMessage': ['Clone', 'Copy'],
      'Timestamp': ['Clone', 'Copy', 'Debug', 'Default', 'Ord', 'PartialOrd', 'Eq', 'PartialEq', 'Serialize', 'Deserialize'],
      'ProductIdentification': ['Clone', 'Copy'],
      'InstrumentDescription': ['Clone', 'Copy'],
      'OrderAdd': ['Default'],
      'OrderDelete': ['Default'],
      'OrderExecute': ['Default'],
      'OrderModify': ['Default'],
      'ScenarioName': ['Clone', 'Copy'],
    },
    meta: {
      'MsgType': {
        implConst: true
      },
      'TestMax': {
        fields: {
          'payload': {
            annotations: ['#[serde(with = "BigArray")]']
          }
        }
      },
      'News': {
        fields: {
          'title': { annotations: ['#[serde(with = "BigArray")]'] },
          'text': { annotations: ['#[serde(with = "BigArray")]'] }
        }
      },
      'Timestamp': {
        newtype: {
          kind: NewtypeKind.InCrate,
          deref: false,
          asInner: true,
          constr: false
        },
      },
      'TestEvent': {
        fields: {
          'scenarioName': {
            annotations: ['#[serde(with = "BigArray")]']
          }
        }
      },
      // Public newtypes
      ...Object.fromEntries([
        'Name',
        'TextMessage',
        'MicCode',
        'InstrumentCode',
        'IndexPortfolioEntry',
        'ProductIdentification',
        'InstrumentDescription',
        'Issuer',
        'FisnCode',
        'CfiCode',
        'Code'
      ].map((name) => [name, crateNewtype]))
    },
    enumConversionError,
    forEachType: attachAll([attachArrayNewtypeImpl, ([generated, context, meta]) => {
      const filter = ["MsgType", "Header", "Login"];
      if (filter.includes(context.name)) {
        return attachBytesValidator([generated, context, meta]);
      } else {
        return generated;
      }
    }]),
    transparentBitflags: true,
  }
}

module.exports = { marketDataTypes }
