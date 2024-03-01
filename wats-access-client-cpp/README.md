# WATS Access Client C++ sample code
WATS Reference Client Library for native, binary trading protocol in C++.

## About
Repository contains C++ source code of a library which demonstrates interaction with Warsaw Stock Exchange/Warsaw Automated Trading System (WSE WATS) through Native Order Gateway. It can be considered a starting point for implementing a custom client which will be able to both trace the market through listening to Market Data streams and place orders and read trade results through use of native, binary trading protocol.

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
All configuration used by all of the tests attached to the library are located in `bin/wats.yml` file.

## Building
All of the code in this implementation of the library requires [conan](https://conan.io/) version 2.0.5+ and [cmake](https://cmake.org/) version 3.24+, to build. To build the samples you should issue the following commands:

```shell
cmake --preset={preset}
cmake --build --preset={preset}
```

Where {preset} can be either `linux-debug` or `windows-debug`.

## Tests
Tests are located in `tests` directory and contain following code examples:

| Test                          | Description                                                                                                                                                                    |
| ----------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| login_logout                  | Demonstrates simple login/logout to Trading Port procedure kinematics.                                                                                                         |
| limit_order                   | Submit buy order, wait for a response from WATS and then put a matching sell order. Assert that a Trade response has expected value.                                           |
| modify_inflight               | Submit buy order and immediately, without waiting for order response, submit order modification.                                                                               |
| bad_instrument_id             | Submit buy order with bad (nonexistent) Instrument.                                                                                                                            |
| bad_price                     | Submit buy order with an incorrect price. Assert that WATS response status is rejected and rejection reason is InvalidPriceIncrement.                                          |
| order_cancel                  | Submit buy order, wait for a response and then send a CancelOrder for it. Assert that rejection reason in OrderCancelResponse is NA and order_ref_id matches OrderAddResponse. |
| order_cancel_bad_order_ref_id | Submit buy order, wait for a response and then send a CancelOrder with incorrect order_ref_id. Assert that rejection reason in OrderCancelResponse is UnknownOrderRefId.       |
| price_violates_tick_table     | Check order price against Tick Table - Tick Size. Allows to validate order prior sending it to WATS Trading Port.                                                              |
| request_omd_replay            | Request replay of all Online Market Data messages.                                                                                                                             |
| bbo_price_level               | Verify Best Bid Order content after a set of book building orders.                                                                                                             |
| orders_in_phase               | Submit a batch of orders for easy book building.                                                                                                                               |
| ptc_verification              | Test order with Pre Trade Checks                                                                                                                                               |
| order_book_based_on_omd       | Build order book based on Online Market Data                                                                                                                                   |

To run integrated tests you need a running WATS core services and proper configuration of the addresses/ports of the core services in `bin/wats.yml` configuration file. To run the tests you should issue the following commands:

```shell
cd build
ctest [-C Debug|Release]
```

## Message definitions
All messages used to communicate with WATS exist primarily, as a contract, in JSON definition files. Definitions required to communicate with the system are located in `messages/source` directory. They can be translated to library's native language definitions (C++ headers) using [bendec](https://github.com/fudini/bendec/) (Binary encoder/decoder). To re-generate native definitions run:

```shell
npm ci
npm run gen-cpp
```

The resulting native definitions files are saved in `include/wats/generated`.

Normally there should be no need to generate the native definitions as the sources come with proper versions of the definitions. However with further development of the system there may be a situation when regeneration of the definitions is required.

## Documentation
Documentation of the connectivity, market data and trading protocols supported by the EUAT WATS can be found [here](../wats-access-client-common/README.md), in the *Reference Documents* section.

## Support
If you have any doubts please feel free to contact support for WATS Access Client development environment for ISVs:

| Channel | Contact         |
| ------- | --------------- |
| Name:   | Piotr Demczuk   |
| E-mail: | ts@gpw.pl       |
| Phone:  | +48 603 855 040 |
