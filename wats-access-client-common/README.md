# WATS DEV-ISV environment
This system contains core components of Warsaw Stock Exchange/Warsaw Automated Trading System (WSE/WATS) configured as an introductory development environment for Independent Software Vendors (DEV-ISV).

## Services
Services offered by the WATS system consist of Trading Services (FIX Order Gateway, Native Order Gateway) and Market Data Services (Market Data Stream, Market Data Snapshot and Market Data Replay). Both Trading Services are unicast (TCP) ports which require a client supporting appropriate protocol to submit orders and receive trading information. Market Data services are unicast (Snapshot and Replay) and multicast (UDP) event based data distribution services. In Market Data services distributed data can be partially encrypted.

Detailed description of the services can be found in the documents referenced below.

Outline of the services and they data streams:

![services](media/services.png)

## Environment

### Access
Access to the system requires establishing a VPN connection to the network development host, which will, in turn, offer SSH based access to the development host and TCP/IP based access to the services.

Below diagram outlines access methods to cloud hosted DEV-ISV environment:

![access](media/access.png)

### User Account
User directory perspective is shown on the below diagram:

![environment](media/environment.png)

Links below lead to source code of sample access clients for native, binary trading protocol, dedicated for the following programming languages:

[WATS Reference Client in Rust](../wats-access-client-rust/README.md)

[WATS Reference Client in C++](../wats-access-client-cpp/README.md)

[WATS Reference Client in Java](../wats-access-client-java/README.md)

### Core Services Maintenance
There is a basic maintenance command set which can be invoked from the command line to Start/Stop/Restart of the core services:

```shell
    sudo systemctl start wats
    sudo systemctl stop wats
    sudo systemctl restart wats
```

To check the status of the services running you can invoke:

```shell
    sudo systemctl status wats
```

Each time the services are restarted the Reference Data database is populated with new data and state files are reset so no trace of previously submitted orders is kept.

## Connectivity Data
Below table summarizes the connectivity data for core services on the ISV development virtual machine. Other environments may require other connectivity data.

| Port                                         | Connectivity data                                                                                                                                                                           |
| -------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Market Data Snapshot (DDS::OMD::Snapshot)    | ConnectionID = 587<br />Token = ABCDEFGH <br />IP:Port (TCP) = 127.0.0.1:10033                                                                                                              |
| Market Data Stream (DDS::OMD::Stream)        | IP:Port (UDP) = 224.3.1.1:10009                                                                                                                                                             |
| Market Data Replay (DDS::OMD::Replay)        | IP:Port (TCP) = 127.0.0.1:10011                                                                                                                                                             |
| Best Bid Offer Snapshot (DDS::BBO::Snapshot) | ConnectionID = 609<br />Token = ABCDEFGH <br />IP:Port (TCP) = 127.0.0.1:10035                                                                                                              |
| Best Bid Offer Stream (DDS::BBO::Stream)     | IP:Port (UDP) = 224.3.1.1:10031                                                                                                                                                             |
| Best Bid Offer Replay (DDS::BBO::Replay)     | IP:Port (TCP) = 127.0.0.1:10032                                                                                                                                                             |
| FIX Order Gateway (OEG::FIX)                 | ConnectionID = 581<br />TargetCompId = WATS_FIX_TP<br />SenderCompId = WATSAC01_FIX01<br />Token = ABCDEFGH<br />IP:Port (TCP) = 127.0.0.1:10050                                           |
|                                              | ConnectionID = 589<br />TargetCompId = WATS_FIX_TP<br />SenderCompId = WATSAC02_FIX01<br />Token = ABCDEFGH<br />IP:Port (TCP) = 127.0.0.1:10050                                           |
| Native Order Gateway (OEG::BIN)              | ConnectionID = 581<br />Token = ABCDEFGH<br />IP:Port (TCP) = 127.0.0.1:10010                                                                                      |
|                                              | ConnectionID = 589<br />Token = ABCDEFGH<br />IP:Port (TCP) = 127.0.0.1:10010                                                                                      |
| Drop Copy Service (OEG::DCP)                 | TargetCompId = WATS_FIX_DC<br />SenderCompId = WATSAC01_FIXDC<br />Token = ABCDEFGH<br />IP:Port (TCP) = 127.0.0.1:10052<br /><br />Works with OEG::FIX and OEG::BIN connection #1 |
|                                              | TargetCompId = WATS_FIX_DC<br />SenderCompId = WATSAC02_FIXDC<br />Token = ABCDEFGH<br />IP:Port (TCP) = 127.0.0.1:10052<br /><br />Works with OEG::FIX and OEG::BIN connection #2 |

Initially encryption of Market Data streams will not be enabled. After enabling, encryption keys will be distributed through `EncryptionKey` messages of Market Data Snapshot (DDS::OMD::Snapshot) service which should be read as first of all Market Data streams.

## Reference Documents

Reference documentation and contract definitions can be found under the following link:

[GPW WATS - Dokumentacja](https://gpwwats.pl/dokumentacja-i-faq)

## Support
If you have any doubts please feel free to contact support for WATS Access Client development environment for ISVs:

| Channel | Contact         |
| ------- | --------------- |
| Name:   | Piotr Demczuk   |
| E-mail: | ts@gpw.pl       |
| Phone:  | +48 603 855 040 |
