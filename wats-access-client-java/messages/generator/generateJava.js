const {generateFiles} = require('bendec/dist/tools/javaGenerator');
const {convertJson} = require("bendec/dist/tools/java/convert-json");
const {jsonSerializableGenerator} = require("bendec/dist/tools/java/jsonInterface");
const {binSerializableGenerator} = require("bendec/dist/tools/java/binarySerializableInterface");
const {readDefs, resolveReplayTypes} = require("./shared");

const outDir = process.argv[2]
const rootOutput = outDir !== undefined
  ? outDir
  : './src/main/java'

const [mdVersion, mdTypes] = readDefs('market_data.json');
const [tpVersion, tpTypes] = readDefs('trading_port.json');

function generateTypes() {
  let bendecPackageName = "pl.gpw.wats.client.md.bendec";
  generateFiles(convertJson([...mdTypes.marketData]),
    rootOutput,
    {
      bendecPackageName,
      interfaces: [
        binSerializableGenerator(bendecPackageName),
      ],
    }
  );

  bendecPackageName = "pl.gpw.wats.client.tp.bendec";
  generateFiles(convertJson([...tpTypes]),
    rootOutput,
    {
      bendecPackageName,
      interfaces: [
        binSerializableGenerator(bendecPackageName),
      ],
    }
  );

  bendecPackageName = "pl.gpw.wats.client.replay.bendec";
  generateFiles(convertJson([...mdTypes.replay]),
    rootOutput,
    {
      bendecPackageName,
      interfaces: [
        binSerializableGenerator(bendecPackageName),
      ],
    }
  );
}

generateTypes();
