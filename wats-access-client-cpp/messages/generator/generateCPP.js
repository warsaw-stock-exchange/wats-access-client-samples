/* eslint @typescript-eslint/no-var-requires: 0 */
const { resolve, dirname } = require('path')
const { writeFileSync, existsSync, mkdirSync } = require('fs')
const { generateString, defaultOptions } = require('bendec/dist/tools/cppGenerator')
const { readDefs, nested, resolveReplayTypes } = require('./shared')

const outDir = process.argv[2]
const rootOutput = outDir !== undefined
  ? outDir
  : 'include/wats/generated'

const typeMapping = {}

const messages = [
    {
        input: ['market_data.json', nested('marketData')],
        output: 'market_data.hpp',
        options: defaultOptions
    },
    {
        input: ['trading_port.json'],
        output: 'trading_port.hpp',
        options: defaultOptions
    },
    {
        input: ['', resolveReplayTypes],
        output: 'replay.hpp',
    },
]

for (const msg of messages) {
    const inputFullPath = resolve(msg.input[0])
    console.log(`reading input file: ${ inputFullPath }`)

    const outputFullPath = resolve(rootOutput, msg.output)
    const outputDir = dirname(outputFullPath)
    if (!existsSync(outputDir))
        mkdirSync(outputDir, { recursive: true })

    const [_version, types] = readDefs(...msg.input)
    const generated = generateString(types, msg.options, outputFullPath)
    console.log(`writing output: ${ outputFullPath }`)
    writeFileSync(outputFullPath, generated)
}

console.log('done')
