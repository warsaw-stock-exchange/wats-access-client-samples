const path = require('path')
const { writeFileSync, existsSync, mkdirSync } = require('fs')
const { generateFrom } = require('bendec/dist/tools/rust/gen/auto-from')
const { readDefs, nested } = require('../shared')

const rootNs = 'crate::messages::generated'

function getNs(input) {
  if (input[2] === undefined) {
    return path.basename(input[0]).split('.json')[0]

  } else {
    return input[2]
  }
}

function autoFrom(source,
  target,
  types,
  isCommutative,
  rootOutput) {
  if ('resolved' in target || 'resolved' in source) {
    throw new Error("autoFrom does not support replay types")
  }

  const [_sourceVersion, sourceDefs] = readDefs(...source)
  const sourceEnums = sourceDefs.filter(typeDef => typeDef.kind == 'Enum');

  const [_targetVersion, targetDefs] = readDefs(...target)
  const targetEnums = targetDefs.filter(typeDef => typeDef.kind == 'Enum');

  const sourceNs = getNs(source)
  const targetNs = getNs(target)
  const body = [`use ${rootNs}::${sourceNs};`, `use ${rootNs}::${targetNs};`];

  for (const type of types) {
    const sourceType = sourceEnums.find(typeDef => typeDef.name == type)
    if (!sourceType) {
      throw new Error(`${type} not found in ${source[0]}, possibly not an enum or defined in another file`);
    }

    const targetType = targetEnums.find(typeDef => typeDef.name == type)
    if (!targetType) {
      throw new Error(`${type} not found in ${target[0]}, possibly not an enum or defined in another file`);
    }

    body.push(generateFrom(sourceType, sourceNs, targetNs))

    if (isCommutative) {
      body.push(generateFrom(sourceType, targetNs, sourceNs,))
    }
  }

  const outputFullPath = path.resolve(rootOutput, `auto_from_${sourceNs}_${targetNs}.rs`)

  const outputDir = path.dirname(outputFullPath)
  if (!existsSync(outputDir))
    mkdirSync(outputDir)

  console.log(`writing output: ${outputFullPath}`)
  writeFileSync(outputFullPath, body.join('\n'))
}

function generateAutoFrom(rootOutput) {
  const tp2cbCommutativeTypes =
    [
      'Capacity',
      'TimeInForce',
      'RiskDefinitionStatus',
      'RiskOperation',
      'RiskLimitType',
      'RiskLimitAction',
      'RiskLimitRequestType',
      'RiskWarningLevelAction',
    ]

  const tp = ['trading_port.json']
  const md = ['market_data.json', nested('marketData')]

  autoFrom(
    tp,
    [
      ...tp2cbCommutativeTypes,
    ],
    false,
    rootOutput
  )

  autoFrom(
    tp,
    [
      'OrderRejectionReason',
      'RejectReason',
      'RiskLimitDefinitionRejectionReason',
    ],
    false,
    rootOutput + '/compile_tests'
  )

  autoFrom(
    tp,
    [
      ...tp2cbCommutativeTypes,
      'OrderSource',
      'OrderType',
      'RejectReason',
      'RiskLimitDefinitionRejectionReason',
    ],
    false,
    rootOutput
  )

  autoFrom(
    md,
    [
      'InstrumentStatus',
      'CollarType',
      'PriceType',
      'MmtMarketMechanism',
      'MmtTradingMode',
      'MmtTransationCategory',
      'MmtNegotitationIndicator',
      'MmtAgencyCrossTradeIndicator',
      'MmtModificationIndicator',
      'MmtBenchmarkReferencePriceIndicator',
      'MmtSpecialDividendIndicator',
      'MmtOffBookAutomatedIndicator',
      'MmtOrdinaryTradeIndicator',
      'MmtAlgorithmicIndicator',
      'MmtPostTradeDeferralReason',
      'MmtPostTradeDeferralType',
      'MarketModelType',
      'TradingPhaseType',
      'ClosingPriceType',
      'AdjustedClosingPriceReason',
      'ExcerciseType'
    ],
    true,
    rootOutput
  )

  autoFrom(
    md,
    [
      'AuctionType',
      'CalendarExceptionType',
      'CollarExpression',
      'CollarMode',
      'CouponType',
      'Currency',
      'NominalValueType',
      'OrderSide',
      'PriceExpressionType',
      'PriorityFlag',
      'ProductIdentificationType',
      'ProductType',
    ],
    true,
    rootOutput
  )

  const tp2shCommutativeTypes =
    [
      'AccountType',
      'AlgorithmicTradeIndicator',
      'ClearingIdentifier',
      'ElementType',
      'ExecType',
      'MatchStatus',
      'NoSides',
      'OrderSide',
      'PartyId',
      'PartyIdSource',
      'PartyRoleQualifier',
      'PriorityFlag',
      'SecurityIdSource',
      'TradeReportTransType',
      'TradeReportType',
      'TradeType',
      'TcrStatus',
    ]

  autoFrom(
    tp,
    [
      ...tp2shCommutativeTypes,
    ],
    false,
    rootOutput
  )

  autoFrom(
    tp,
    [
      'TcrRejectionReason',
    ],
    false,
    rootOutput + '/compile_tests'
  )

  autoFrom(
    tp,
    [
      ...tp2shCommutativeTypes,
      'TcrRejectionReason',
    ],
    false,
    rootOutput
  )
}

module.exports = {
  generateAutoFrom
}
