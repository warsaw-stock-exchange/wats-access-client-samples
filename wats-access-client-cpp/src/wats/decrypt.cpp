#include "wats/decrypt.hpp"

using namespace omd;
using namespace omd::messages;

void Decryption::storeKeyNonce(omd::messages::ElementId id,
    CryptoPP::SecByteBlock key, CryptoPP::SecByteBlock nonce) {
    store_.emplace(id, std::make_tuple(key, nonce));
}

Decryption::KeyNonce Decryption::retrieveKeyNonce(
    omd::messages::ElementId id) {
    return store_[id];
}

void Decryption::decrypt_chunk(omd::messages::ElementId encryptionKeyId,
    CryptoPP::byte* data, size_t size) {
    streams[encryptionKeyId].ProcessData(data, data, size);
}

void Decryption::decrypt(omd::messages::OrderAdd* message) {

    // EncryptionOffset can be used either to adjust the position in cipher
    // stream (in case when not all of the messages are decrypted) or to
    // detect possible error in the position within cipher stream.

    streams[message->header.encryptionKeyId].Seek(message->header.encryptionOffset);

    // if (streams[message->header.encryptionKeyId].offset() != message->header.encryptionOffset) {
    //     throw decrypt_exception("encryption offset incorrect");
    // }

    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->instrumentId,
        sizeof(message->instrumentId));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->price,
        sizeof(message->price));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->quantity,
        sizeof(message->quantity));
}

void Decryption::decrypt(omd::messages::OrderModify* message) {

    streams[message->header.encryptionKeyId].Seek(message->header.encryptionOffset);

    // if (streams[message->header.encryptionKeyId].offset() != message->header.encryptionOffset) {
    //     throw decrypt_exception("encryption offset incorrect");
    // }

    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->instrumentId,
        sizeof(message->instrumentId));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->price,
        sizeof(message->price));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->quantity,
        sizeof(message->quantity));
}

void Decryption::decrypt(omd::messages::OrderExecute* message) {

    streams[message->header.encryptionKeyId].Seek(message->header.encryptionOffset);

    // if (streams[message->header.encryptionKeyId].offset() != message->header.encryptionOffset) {
    //     throw decrypt_exception("encryption offset incorrect");
    // }

    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->quantity,
        sizeof(message->quantity));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->instrumentId,
        sizeof(message->instrumentId));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->tradeId,
        sizeof(message->tradeId));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->executionPrice,
        sizeof(message->executionPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->executionQuantity,
        sizeof(message->executionQuantity));
}

void Decryption::decrypt(omd::messages::TopPriceLevelUpdate* message) {

    streams[message->header.encryptionKeyId].Seek(message->header.encryptionOffset);

    // if (streams[message->header.encryptionKeyId].offset() != message->header.encryptionOffset) {
    //     throw decrypt_exception("encryption offset incorrect");
    // }

    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->instrumentId,
        sizeof(message->instrumentId));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->price,
        sizeof(message->price));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->quantity,
        sizeof(message->quantity));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->orderCount,
        sizeof(message->orderCount));
}

void Decryption::decrypt(omd::messages::PriceLevelSnapshot* message) {

    for (omd::messages::PriceLevel &p: message->buy) decrypt(&p);
    for (omd::messages::PriceLevel &p: message->sell) decrypt(&p);
}

void Decryption::decrypt(omd::messages::PriceLevel* priceLevel) {

    if (priceLevel->isEncrypted) {
        streams[priceLevel->encryptionKeyId].Seek(priceLevel->encryptionOffset);

        // if (streams[message->header.encryptionKeyId].offset() != message->header.encryptionOffset) {
        //     throw decrypt_exception("encryption offset incorrect");
        // }

        decrypt_chunk(priceLevel->encryptionKeyId,
            (CryptoPP::byte* )&priceLevel->price,
            sizeof(priceLevel->price));
        decrypt_chunk(priceLevel->encryptionKeyId,
            (CryptoPP::byte* )&priceLevel->quantity,
            sizeof(priceLevel->quantity));
        decrypt_chunk(priceLevel->encryptionKeyId,
            (CryptoPP::byte* )&priceLevel->orderCount,
            sizeof(priceLevel->orderCount));
    }
}

void Decryption::decrypt(omd::messages::ProductSummary* message) {

    streams[message->header.encryptionKeyId].Seek(message->header.encryptionOffset);

    // if (streams[message->header.encryptionKeyId].offset() != message->header.encryptionOffset) {
    //     throw decrypt_exception("encryption offset incorrect");
    // }

    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->productIdentification,
        sizeof(message->productIdentification));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->productId,
        sizeof(message->productId));

    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->clobInstrument.instrumentId,
        sizeof(message->clobInstrument.instrumentId));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->clobInstrument.lastTradedPrice,
        sizeof(message->clobInstrument.lastTradedPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->clobInstrument.closingPrice,
        sizeof(message->clobInstrument.closingPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->clobInstrument.adjustedClosingPrice,
        sizeof(message->clobInstrument.adjustedClosingPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->clobInstrument.pctChange,
        sizeof(message->clobInstrument.pctChange));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->clobInstrument.vwap,
        sizeof(message->clobInstrument.vwap));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->clobInstrument.totalVolume,
        sizeof(message->clobInstrument.totalVolume));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->clobInstrument.totalValue,
        sizeof(message->clobInstrument.totalValue));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->clobInstrument.openingPrice,
        sizeof(message->clobInstrument.openingPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->clobInstrument.maxPrice,
        sizeof(message->clobInstrument.maxPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->clobInstrument.minPrice,
        sizeof(message->clobInstrument.minPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->clobInstrument.settlementPrice,
        sizeof(message->clobInstrument.settlementPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->clobInstrument.settlementValue,
        sizeof(message->clobInstrument.settlementValue));

    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->crossInstrument.instrumentId,
        sizeof(message->crossInstrument.instrumentId));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->crossInstrument.lastTradedPrice,
        sizeof(message->crossInstrument.lastTradedPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->crossInstrument.closingPrice,
        sizeof(message->crossInstrument.closingPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->crossInstrument.adjustedClosingPrice,
        sizeof(message->crossInstrument.adjustedClosingPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->crossInstrument.pctChange,
        sizeof(message->crossInstrument.pctChange));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->crossInstrument.vwap,
        sizeof(message->crossInstrument.vwap));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->crossInstrument.totalVolume,
        sizeof(message->crossInstrument.totalVolume));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->crossInstrument.totalValue,
        sizeof(message->crossInstrument.totalValue));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->crossInstrument.openingPrice,
        sizeof(message->crossInstrument.openingPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->crossInstrument.maxPrice,
        sizeof(message->crossInstrument.maxPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->crossInstrument.minPrice,
        sizeof(message->crossInstrument.minPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->crossInstrument.settlementPrice,
        sizeof(message->crossInstrument.settlementPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->crossInstrument.settlementValue,
        sizeof(message->crossInstrument.settlementValue));

    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->blockInstrument.instrumentId,
        sizeof(message->blockInstrument.instrumentId));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->blockInstrument.lastTradedPrice,
        sizeof(message->blockInstrument.lastTradedPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->blockInstrument.closingPrice,
        sizeof(message->blockInstrument.closingPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->blockInstrument.adjustedClosingPrice,
        sizeof(message->blockInstrument.adjustedClosingPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->blockInstrument.pctChange,
        sizeof(message->blockInstrument.pctChange));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->blockInstrument.vwap,
        sizeof(message->blockInstrument.vwap));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->blockInstrument.totalVolume,
        sizeof(message->blockInstrument.totalVolume));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->blockInstrument.totalValue,
        sizeof(message->blockInstrument.totalValue));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->blockInstrument.openingPrice,
        sizeof(message->blockInstrument.openingPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->blockInstrument.maxPrice,
        sizeof(message->blockInstrument.maxPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->blockInstrument.minPrice,
        sizeof(message->blockInstrument.minPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->blockInstrument.settlementPrice,
        sizeof(message->blockInstrument.settlementPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->blockInstrument.settlementValue,
        sizeof(message->blockInstrument.settlementValue));

    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->hybridInstrument.instrumentId,
        sizeof(message->hybridInstrument.instrumentId));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->hybridInstrument.lastTradedPrice,
        sizeof(message->hybridInstrument.lastTradedPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->hybridInstrument.closingPrice,
        sizeof(message->hybridInstrument.closingPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->hybridInstrument.adjustedClosingPrice,
        sizeof(message->hybridInstrument.adjustedClosingPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->hybridInstrument.pctChange,
        sizeof(message->hybridInstrument.pctChange));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->hybridInstrument.vwap,
        sizeof(message->hybridInstrument.vwap));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->hybridInstrument.totalVolume,
        sizeof(message->hybridInstrument.totalVolume));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->hybridInstrument.totalValue,
        sizeof(message->hybridInstrument.totalValue));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->hybridInstrument.openingPrice,
        sizeof(message->hybridInstrument.openingPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->hybridInstrument.maxPrice,
        sizeof(message->hybridInstrument.maxPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->hybridInstrument.minPrice,
        sizeof(message->hybridInstrument.minPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->hybridInstrument.settlementPrice,
        sizeof(message->hybridInstrument.settlementPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->hybridInstrument.settlementValue,
        sizeof(message->hybridInstrument.settlementValue));

    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->tradingValueCurrency,
        sizeof(message->tradingValueCurrency));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->markerPriceChange,
        sizeof(message->markerPriceChange));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->delta,
        sizeof(message->delta));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->gamma,
        sizeof(message->gamma));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->rho,
        sizeof(message->rho));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->theta,
        sizeof(message->theta));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->vega,
        sizeof(message->vega));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->volatility,
        sizeof(message->volatility));
}
