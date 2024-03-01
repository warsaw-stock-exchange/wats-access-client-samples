const { 
  attachAll, 
  attachArrayNewtypeImpl,
  resolveReplayTypes
} = require('../shared')
const { attachBytesValidator } = require('./bytesValidator')


// NOTE: Replay is extracted from core_bus.json and market_data.json.
//       Because of that fact replay definition is required and checked to be identicall
//       between those definitions.
//       Despite the definition in two separate contracts replay still produces single output: replay.rs
const replayTypes = {
  input: ['', resolveReplayTypes],
  output: 'replay.rs',
  options: {
    camelCase: true,
    extras: [
      'use crate::messages::bytes_validator::BytesValidator;'
    ],
    extraDerives: {
      'ReplayHeader': ['Clone', 'Copy'],
      'ReplayRequest': ['Clone', 'Copy'],
    },
    meta: {
      "ReplayMsgType": {
        implConst: true
      }
    },
    forEachType: attachAll([attachArrayNewtypeImpl, attachBytesValidator]),
    transparentBitflags: true,
  }
}

module.exports = { replayTypes }