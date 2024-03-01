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

void Decryption::decrypt(OrderAdd* message) {

    if (streams[message->header.encryptionKeyId].offset() != message->header.encryptionOffset) {
        throw decrypt_exception("encryption offset incorrect");
    }
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

void Decryption::decrypt(OrderModify* message) {

    if (streams[message->header.encryptionKeyId].offset() != message->header.encryptionOffset) {
        throw decrypt_exception("encryption offset incorrect");
    }
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->price,
        sizeof(message->price));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->quantity,
        sizeof(message->quantity));
}
 
void Decryption::decrypt(OrderExecute* message) {

    if (streams[message->header.encryptionKeyId].offset() != message->header.encryptionOffset) {
        throw decrypt_exception("encryption offset incorrect");
    }
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->quantity,
        sizeof(message->quantity));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->executionId,
        sizeof(message->executionId));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->executionPrice,
        sizeof(message->executionPrice));
    decrypt_chunk(message->header.encryptionKeyId,
        (CryptoPP::byte* )&message->executionQuantity,
        sizeof(message->executionQuantity));
}
