# WATS Access Client Rust
WATS Reference Client Library for native, binary trading protocol in Rust.

## About
Repository contains Rust source code of a library which demonstrates interaction with Warsaw Stock Exchange/Warsaw Automated Trading System (WSE WATS) through Native Order Gateway. It can be considered a starting point for implementing a custom client which will be able to both trace the market through listening to Market Data streams and place orders and read trade results through use of native, binary trading protocol.

## Prerequisites
Using of the library requires access to running instance of WSE WATS services. To get access to them you have to collect the following information:

| Port                                         | Connectivity data                          |
| -------------------------------------------- | ------------------------------------------ |
| Market Data Snapshot (DDS::OMD::Snapshot)    | ConnectionID<br />Token<br />IP:Port (TCP) |
| Market Data Stream (DDS::OMD::Stream)        | IP:Port (UDP)                              |
| Market Data Replay (DDS::OMD::Replay)        | IP:Port (TCP)                              |
| Best Bid Offer Snapshot (DDS::BBO::Snapshot) | ConnectionID<br />Token<br />IP:Port (TCP) |
| Best Bid Offer Stream (DDS::BBO::Stream)     | IP:Port (UDP)                              |
| Best Bid Offer Replay (DDS::BBO::Replay)     | IP:Port (TCP)                              |
| Native Order Gateway (OEG::BIN)              | ConnectionID<br />Token<br />IP:Port (TCP) |

Typically, correct connectivity information should be available in the mentioned below configuration file or in the description of the development environment (e.g. EUAT, DEV-ISV etc.) If not then the above mentioned information can be obtained from WSE WATS support team which can be contacted using data from the support section below.

## Configuration
All configuration used by all of the tests attached to the library are located in `tests/wats.yml` file.

## Building
All of the code in this implementation of the library requires properly installer Rust environment to build. To build the samples you should issue the following commands:

```shell
cargo build
```

## Tests
Tests are located in `tests` directory and contain following code examples:

To run integrated tests you need a running WATS core services and proper configuration of the addresses/ports of the core services in `tests/wats.yml` configuration file. To run the tests you should issue the following commands:

```shell
cargo test -- --include-ignored --test-threads 1
```

## Message definitions
All messages used to communicate with WATS exist primarily, as a contract, in JSON definition files. Definitions required to communicate with the system are located in `messages` directory. They can be translated to library's native Rust language definitions using [bendec](https://github.com/fudini/bendec/) (Binary encoder/decoder). To re-generate native definitions run:

```shell
npm ci
npm run gen-rust
cargo fmt
```

The resulting native definitions files are saved in `src/messages/generated`.

Normally there should be no need to generate the native definitions as the sources come with proper versions of the definitions. However with further development of the system there may be a situation when regeneration of the definitions is required.

## Proxy client
This client implements an example proxy client that helps interact with WATS. It can be started with:

```shell
cargo run --example trading_port_bin_client -- examples/wats.yml
```

It will show the local address that it is listening on (by default it binds to a random TCP port), e.g.:

```shell
Listening for orders on 127.0.0.1:41557
```

You can use this socket to send messages to WATS. Only single connection is supported.
Send an example OrderAdd message using netcat:

```shell
echo -ne "\x47\x00\x04\x01\x00\x00\x00\x00\x00\x14\x27\x00\x00\x01\x01\x01"\
"\x00\xd6\x11\x7e\x03\x00\x00\x00\xe8\x03\x00\x00\x00\x00\x00\x00"\
"\x01\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00"\
"\x00\x01\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00"\
"\x00\x00\x00\x00\x00\x00\x00" | nc 127.0.0.1 41557
```

## Documentation
Documentation of the connectivity, market data and trading protocols supported by the EUAT WATS can be found [here](../wats-access-client-common/README.md), in the *Reference Documents* section.

## Support
If you have any doubts please feel free to contact support for WATS Access Client development environment for ISVs:

| Channel | Contact         |
| ------- | --------------- |
| Name:   | Piotr Demczuk   |
| E-mail: | ts@gpw.pl       |
| Phone:  | +48 603 855 040 |
