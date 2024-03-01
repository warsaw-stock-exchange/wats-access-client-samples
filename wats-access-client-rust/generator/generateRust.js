const path = require('path')
const { writeFileSync } = require('fs')
const { generateString } = require('bendec/dist/tools/rsGenerator')
const { generateAutoFrom } = require('./rust/generateAutoFrom')
const { readDefs } = require('./shared')
const { marketDataTypes, marketDataMetadata } = require('./rust/marketData')
const { tradingPortTypes, tradingPortMetadata } = require('./rust/tradingPort')
const { replayTypes } = require('./rust/replay')

const outDir = process.argv[2]

const rootOutput = outDir !== undefined
  ? outDir
  : 'src/messages/generated/'

const messages = [
  marketDataTypes,
  tradingPortTypes,
  replayTypes
]

for (const msg of messages) {
  const inputFullPath = msg.input[0]
  const outputFullPath = path.resolve(rootOutput, msg.output)
  console.log(`reading input file: ${inputFullPath}`)

  const [_version, types] = readDefs(...msg.input)
  const generated = generateString(types, msg.options)

  console.log(`writing output: ${outputFullPath}`)
  writeFileSync(outputFullPath, generated)
}

console.log('done')
