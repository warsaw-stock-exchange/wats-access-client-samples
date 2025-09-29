# WATS Access Client Java
WATS Reference Client Library for native, binary trading protocol in Java.

## About
Repository contains Java source code of a library which demonstrates interaction with Warsaw Stock Exchange - Warsaw Automated Trading System (WSE WATS) through Native Order Gateway. It can be considered a starting point for implementing a custom client which will be able to both trace the market through listening to Market Data streams and place orders and read trade results through use of native, binary trading protocol.

## Building
This implementation requires JDK 11. To build the samples you should issue the following commands:

```shell
# Build everything except tests
./gradlew assemble
```

## Tests
Tests are located in `src/test` directory. To run the test cases you should issue the following commands:

```shell
# for all test cases
./gradlew test
# for single test case (for example OnlineMarketDataReplayClientTest):
./gradlew test --tests OnlineMarketDataReplayClientTest
# for group of test cases (for example market data test cases)
./gradlew test --tests pl.gpw.wats.client.md.*
```

## Message definitions
All messages used to communicate with WATS exist primarily, as a contract, in JSON definition files. Definitions required to communicate with the system are located in `messages/source` directory. They can be translated to library's native language definitions (C++ headers) using [bendec](https://github.com/fudini/bendec/) (Binary encoder/decoder). To re-generate native definitions run:

```shell
npm ci
npm run gen-java
```

The resulting native definitions files are saved in `src/main/java/pl/gpw/wats/client/md/bendec`, `src/main/java/pl/gpw/wats/client/tp/bendec`, `src/main/java/pl/gpw/wats/client/replay/bendec`.

Normally there should be no need to generate the native definitions as the sources come with proper versions of the definitions. However with further development of the system there may be a situation when regeneration of the definitions is required.

If the generated Java code, located after running the above routine in `*/bendec/` subdirectories, misses annotations (e.g. derived methods or some other syntactical elements) first consult the annotations added in `generator/generateRust.js` file. It may be necessary to add appropriate tags in `extraDerives` or other branches of the generator parametrization code.

## Documentation
Documentation of the connectivity, market data and trading protocols supported by the EUAT WATS can be found [here](../wats-access-client-common/README.md), in the *Reference Documents* section.

## Support
If you have any doubts please feel free to contact support for WATS Access Client development environment for ISVs:

| Channel | Contact         |
| ------- | --------------- |
| Name:   | Piotr Demczuk   |
| E-mail: | ts@gpw.pl       |
| Phone:  | +48 603 855 040 |
