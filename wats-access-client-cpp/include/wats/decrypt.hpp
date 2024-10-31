#ifndef DECRYPT_HPP
#define DECRYPT_HPP

#include <exception>

#include <cryptopp/cryptlib.h>
#include <cryptopp/chacha.h>
#include <cryptopp/secblock.h>
#include <cryptopp/filters.h>

namespace wats::online_market_data::messages {
#include "wats/generated/market_data.hpp"
}

namespace wats::online_market_data::replay {
#include "wats/generated/replay.hpp"
}

using namespace std;

namespace omd = wats::online_market_data;

class decrypt_exception: exception {
private:
	std::string m_error{};
public:
	decrypt_exception(std::string_view error): m_error{error} {}

	const char* what() const noexcept override { return m_error.c_str(); }
};

class DecryptionProcessor: public CryptoPP::ChaChaTLS::Decryption {

private:
    uint64_t offset_;
public:
    DecryptionProcessor(): CryptoPP::ChaChaTLS::Decryption(), offset_{0} {};

    uint64_t offset() { return offset_; }

    void ProcessData(CryptoPP::byte *outString, const CryptoPP::byte *inString, size_t length) {
        CryptoPP::ChaChaTLS::Decryption::ProcessData(outString, inString, length);
        offset_ += length;
    }
};

class Decryption {

typedef std::tuple<CryptoPP::SecByteBlock, CryptoPP::SecByteBlock> KeyNonce;
typedef std::map<omd::messages::ElementId, KeyNonce> KeyNonceMap;

private:
    static inline KeyNonceMap store_;
public:
    std::map<omd::messages::ElementId, DecryptionProcessor> streams;

    void storeKeyNonce(omd::messages::ElementId id, CryptoPP::SecByteBlock key,
        CryptoPP::SecByteBlock nonce);
    Decryption::KeyNonce retrieveKeyNonce(omd::messages::ElementId id);

    void decrypt_chunk(omd::messages::ElementId encryptionKeyId,
        CryptoPP::byte* data, size_t size);

    void decrypt(omd::messages::OrderAdd* message);
    void decrypt(omd::messages::OrderModify* message);
    void decrypt(omd::messages::OrderExecute* message);
    void decrypt(omd::messages::TopPriceLevelUpdate* message);
    void decrypt(omd::messages::PriceLevelSnapshot* message);
    void decrypt(omd::messages::PriceLevel* message);
    void decrypt(omd::messages::ProductSummary* message);
};

#endif // DECRYPT_HPP
