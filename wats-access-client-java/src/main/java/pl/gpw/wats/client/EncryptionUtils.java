package pl.gpw.wats.client;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import pl.gpw.wats.client.md.bendec.BendecUtils;
import pl.gpw.wats.client.md.bendec.EncryptionKey;
import pl.gpw.wats.client.md.bendec.Header;
import pl.gpw.wats.client.md.bendec.OrderAdd;
import pl.gpw.wats.client.md.bendec.OrderExecute;
import pl.gpw.wats.client.md.bendec.OrderModify;
import pl.gpw.wats.client.md.bendec.PriceLevelSnapshot;
import pl.gpw.wats.client.md.bendec.PriceLevel;
import pl.gpw.wats.client.md.bendec.ProductSummary;
import pl.gpw.wats.client.md.bendec.ChangeIndicator;
import pl.gpw.wats.client.md.bendec.TopPriceLevelUpdate;

public class EncryptionUtils {
    private static final String CIPHER = "ChaCha20";
    private final ConcurrentHashMap<Long, EncryptionKey> encryptionKeys = new ConcurrentHashMap<>();
    private final byte[] nonce;

    public EncryptionUtils(byte[] nonce) {
        this.nonce = nonce;
    }

    private static Cipher initCipher(SecretKey key, byte[] nonce, int counter) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(CIPHER);
        ChaCha20ParameterSpec param = new ChaCha20ParameterSpec(nonce, counter/64);
        cipher.init(Cipher.DECRYPT_MODE, key, param);
        cipher.update(new byte[counter%64]);
        return cipher;
    }

    private static SecretKey convertStringToSecretKeyto(String encodedKey) {
        return new SecretKeySpec(BendecUtils.stringToByteArray(encodedKey, 32), 0, 32, CIPHER);
    }

    public void add(EncryptionKey key) {
        encryptionKeys.put(key.getId(), key);
    }

    public void decrypt(Header header, byte[] bytes) throws Exception {
        switch(header.getMsgType()) {
            case ORDERADD:
                decryptOrderAdd(header, bytes);
                break;
            case ORDERMODIFY:
                decryptOrderModify(header, bytes);
                break;
            case ORDEREXECUTE:
                decryptOrderExecute(header, bytes);
                break;
            case TOPPRICELEVELUPDATE:
                decryptTopPriceLevelUpdate(header, bytes);
                break;
            case PRICELEVELSNAPSHOT:
                decryptPriceLevelSnapshot(header, bytes);
                break;
            case PRODUCTSUMMARY:
                decryptProductSummary(header, bytes);
                break;
        }
    }

    private void decryptOrderAdd(Header header, byte[] bytes) throws Exception {
        OrderAdd orderAdd = new OrderAdd(bytes);
        if (!encryptionKeys.containsKey(header.getEncryptionKeyId())) {
            throw new Exception("encryption key not found " + header.getEncryptionKeyId());
        }
        SecretKey key = EncryptionUtils.convertStringToSecretKeyto(encryptionKeys.get(header.getEncryptionKeyId()).getSecretKey());
        Cipher cipher = EncryptionUtils.initCipher(key, nonce, header.getEncryptionOffset().intValue());

        orderAdd.setInstrumentId(BendecUtils.uInt32FromByteArray(cipher.update(BendecUtils.uInt32ToByteArray(orderAdd.getInstrumentId())), 0));
        orderAdd.setPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(orderAdd.getPrice())), 0));
        orderAdd.setQuantity(BendecUtils.uInt64FromByteArray(cipher.update(BendecUtils.uInt64ToByteArray(orderAdd.getQuantity())), 0));
        System.arraycopy(orderAdd.toBytes(), 0, bytes, 0, bytes.length);
    }

    private void decryptOrderModify(Header header, byte[] bytes) throws Exception {
        OrderModify orderModify = new OrderModify(bytes);
        if (!encryptionKeys.containsKey(header.getEncryptionKeyId())) {
            throw new Exception("encryption key not found " + header.getEncryptionKeyId());
        }
        SecretKey key = EncryptionUtils.convertStringToSecretKeyto(encryptionKeys.get(header.getEncryptionKeyId()).getSecretKey());
        Cipher cipher = EncryptionUtils.initCipher(key, nonce, header.getEncryptionOffset().intValue());

        orderModify.setInstrumentId(BendecUtils.uInt32FromByteArray(cipher.update(BendecUtils.uInt32ToByteArray(orderModify.getInstrumentId())), 0));
        orderModify.setPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(orderModify.getPrice())), 0));
        orderModify.setQuantity(BendecUtils.uInt64FromByteArray(cipher.update(BendecUtils.uInt64ToByteArray(orderModify.getQuantity())), 0));
        System.arraycopy(orderModify.toBytes(), 0, bytes, 0, bytes.length);
    }

    private void decryptOrderExecute(Header header, byte[] bytes) throws Exception {
        OrderExecute orderExecute = new OrderExecute(bytes);
        if (!encryptionKeys.containsKey(header.getEncryptionKeyId())) {
            throw new Exception("encryption key not found " + header.getEncryptionKeyId());
        }
        SecretKey key = EncryptionUtils.convertStringToSecretKeyto(encryptionKeys.get(header.getEncryptionKeyId()).getSecretKey());
        Cipher cipher = EncryptionUtils.initCipher(key, nonce, header.getEncryptionOffset().intValue());

        orderExecute.setQuantity(BendecUtils.uInt64FromByteArray(cipher.update(BendecUtils.uInt64ToByteArray(orderExecute.getQuantity())), 0));
        orderExecute.setExecutionId(BendecUtils.uInt32FromByteArray(cipher.update(BendecUtils.uInt32ToByteArray(orderExecute.getExecutionId())), 0));
        orderExecute.setExecutionPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(orderExecute.getExecutionPrice())), 0));
        orderExecute.setExecutionQuantity(BendecUtils.uInt64FromByteArray(cipher.update(BendecUtils.uInt64ToByteArray(orderExecute.getExecutionQuantity())), 0));
        System.arraycopy(orderExecute.toBytes(), 0, bytes, 0, bytes.length);
    }

    private void decryptTopPriceLevelUpdate(Header header, byte[] bytes) throws Exception {
        TopPriceLevelUpdate topPriceLevelUpdate = new TopPriceLevelUpdate(bytes);
        if (!encryptionKeys.containsKey(header.getEncryptionKeyId())) {
            throw new Exception("encryption key not found " + header.getEncryptionKeyId());
        }
        SecretKey key = EncryptionUtils.convertStringToSecretKeyto(encryptionKeys.get(header.getEncryptionKeyId()).getSecretKey());
        Cipher cipher = EncryptionUtils.initCipher(key, nonce, header.getEncryptionOffset().intValue());

        topPriceLevelUpdate.setInstrumentId(BendecUtils.uInt32FromByteArray(cipher.update(BendecUtils.uInt32ToByteArray(topPriceLevelUpdate.getInstrumentId())), 0));
        topPriceLevelUpdate.setPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(topPriceLevelUpdate.getPrice())), 0));
        topPriceLevelUpdate.setQuantity(BendecUtils.uInt64FromByteArray(cipher.update(BendecUtils.uInt64ToByteArray(topPriceLevelUpdate.getQuantity())), 0));
        topPriceLevelUpdate.setOrderCount(BendecUtils.uInt16FromByteArray(cipher.update(BendecUtils.uInt16ToByteArray(topPriceLevelUpdate.getOrderCount())), 0));
        System.arraycopy(topPriceLevelUpdate.toBytes(), 0, bytes, 0, bytes.length);
    }

    private void decryptPriceLevelSnapshot(Header header, byte[] bytes) throws Exception {
        PriceLevelSnapshot priceLevelSnapshot = new PriceLevelSnapshot(bytes);
        if (!encryptionKeys.containsKey(header.getEncryptionKeyId())) {
            throw new Exception("encryption key not found " + header.getEncryptionKeyId());
        }
        for (PriceLevel priceLevel : priceLevelSnapshot.getBuy()) {
            SecretKey key = EncryptionUtils.convertStringToSecretKeyto(encryptionKeys.get(priceLevel.getEncryptionKeyId()).getSecretKey());
            Cipher cipher = EncryptionUtils.initCipher(key, nonce, header.getEncryptionOffset().intValue());

            priceLevel.setPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(priceLevel.getPrice())), 0));
            priceLevel.setQuantity(BendecUtils.uInt64FromByteArray(cipher.update(BendecUtils.uInt64ToByteArray(priceLevel.getQuantity())), 0));
            priceLevel.setOrderCount(BendecUtils.uInt16FromByteArray(cipher.update(BendecUtils.uInt16ToByteArray(priceLevel.getOrderCount())), 0));
            System.arraycopy(priceLevel.toBytes(), 0, bytes, 0, bytes.length);
        }
        for (PriceLevel priceLevel : priceLevelSnapshot.getSell()) {
            SecretKey key = EncryptionUtils.convertStringToSecretKeyto(encryptionKeys.get(priceLevel.getEncryptionKeyId()).getSecretKey());
            Cipher cipher = EncryptionUtils.initCipher(key, nonce, header.getEncryptionOffset().intValue());

            priceLevel.setPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(priceLevel.getPrice())), 0));
            priceLevel.setQuantity(BendecUtils.uInt64FromByteArray(cipher.update(BendecUtils.uInt64ToByteArray(priceLevel.getQuantity())), 0));
            priceLevel.setOrderCount(BendecUtils.uInt16FromByteArray(cipher.update(BendecUtils.uInt16ToByteArray(priceLevel.getOrderCount())), 0));
            System.arraycopy(priceLevel.toBytes(), 0, bytes, 0, bytes.length);
        }
    }

    private void decryptProductSummary(Header header, byte[] bytes) throws Exception {
        ProductSummary productSummary = new ProductSummary(bytes);
        if (!encryptionKeys.containsKey(header.getEncryptionKeyId())) {
            throw new Exception("encryption key not found " + header.getEncryptionKeyId());
        }
        SecretKey key = EncryptionUtils.convertStringToSecretKeyto(encryptionKeys.get(header.getEncryptionKeyId()).getSecretKey());
        Cipher cipher = EncryptionUtils.initCipher(key, nonce, header.getEncryptionOffset().intValue());

        productSummary.setProductIdentification(BendecUtils.stringFromByteArray(cipher.update(BendecUtils.stringToByteArray(productSummary.getProductIdentification(), 30)), 0, 30));
        productSummary.setProductId(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getProductId())), 0));

        productSummary.getClobInstrument().setInstrumentId(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getClobInstrument().getInstrumentId())), 0));
        productSummary.getClobInstrument().setLastTradedPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getClobInstrument().getLastTradedPrice())), 0));
        productSummary.getClobInstrument().setClosingPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getClobInstrument().getClosingPrice())), 0));
        productSummary.getClobInstrument().setAdjustedClosingPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getClobInstrument().getAdjustedClosingPrice())), 0));
        productSummary.getClobInstrument().setPctChange(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getClobInstrument().getPctChange())), 0));
        productSummary.getClobInstrument().setVwap(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getClobInstrument().getVwap())), 0));
        productSummary.getClobInstrument().setTotalVolume(BendecUtils.uInt64FromByteArray(cipher.update(BendecUtils.uInt64ToByteArray(productSummary.getClobInstrument().getTotalVolume())), 0));
        productSummary.getClobInstrument().setTotalValue(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getClobInstrument().getTotalValue())), 0));
        productSummary.getClobInstrument().setOpeningPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getClobInstrument().getOpeningPrice())), 0));
        productSummary.getClobInstrument().setMaxPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getClobInstrument().getMaxPrice())), 0));
        productSummary.getClobInstrument().setMinPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getClobInstrument().getMinPrice())), 0));
        productSummary.getClobInstrument().setSettlementPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getClobInstrument().getSettlementPrice())), 0));
        productSummary.getClobInstrument().setSettlementValue(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getClobInstrument().getSettlementValue())), 0));

        productSummary.getCrossInstrument().setInstrumentId(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getCrossInstrument().getInstrumentId())), 0));
        productSummary.getCrossInstrument().setLastTradedPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getCrossInstrument().getLastTradedPrice())), 0));
        productSummary.getCrossInstrument().setClosingPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getCrossInstrument().getClosingPrice())), 0));
        productSummary.getCrossInstrument().setAdjustedClosingPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getCrossInstrument().getAdjustedClosingPrice())), 0));
        productSummary.getCrossInstrument().setPctChange(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getCrossInstrument().getPctChange())), 0));
        productSummary.getCrossInstrument().setVwap(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getCrossInstrument().getVwap())), 0));
        productSummary.getCrossInstrument().setTotalVolume(BendecUtils.uInt64FromByteArray(cipher.update(BendecUtils.uInt64ToByteArray(productSummary.getCrossInstrument().getTotalVolume())), 0));
        productSummary.getCrossInstrument().setTotalValue(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getCrossInstrument().getTotalValue())), 0));
        productSummary.getCrossInstrument().setOpeningPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getCrossInstrument().getOpeningPrice())), 0));
        productSummary.getCrossInstrument().setMaxPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getCrossInstrument().getMaxPrice())), 0));
        productSummary.getCrossInstrument().setMinPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getCrossInstrument().getMinPrice())), 0));
        productSummary.getCrossInstrument().setSettlementPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getCrossInstrument().getSettlementPrice())), 0));
        productSummary.getCrossInstrument().setSettlementValue(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getCrossInstrument().getSettlementValue())), 0));

        productSummary.getBlockInstrument().setInstrumentId(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getBlockInstrument().getInstrumentId())), 0));
        productSummary.getBlockInstrument().setLastTradedPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getBlockInstrument().getLastTradedPrice())), 0));
        productSummary.getBlockInstrument().setClosingPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getBlockInstrument().getClosingPrice())), 0));
        productSummary.getBlockInstrument().setAdjustedClosingPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getBlockInstrument().getAdjustedClosingPrice())), 0));
        productSummary.getBlockInstrument().setPctChange(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getBlockInstrument().getPctChange())), 0));
        productSummary.getBlockInstrument().setVwap(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getBlockInstrument().getVwap())), 0));
        productSummary.getBlockInstrument().setTotalVolume(BendecUtils.uInt64FromByteArray(cipher.update(BendecUtils.uInt64ToByteArray(productSummary.getBlockInstrument().getTotalVolume())), 0));
        productSummary.getBlockInstrument().setTotalValue(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getBlockInstrument().getTotalValue())), 0));
        productSummary.getBlockInstrument().setOpeningPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getBlockInstrument().getOpeningPrice())), 0));
        productSummary.getBlockInstrument().setMaxPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getBlockInstrument().getMaxPrice())), 0));
        productSummary.getBlockInstrument().setMinPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getBlockInstrument().getMinPrice())), 0));
        productSummary.getBlockInstrument().setSettlementPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getBlockInstrument().getSettlementPrice())), 0));
        productSummary.getBlockInstrument().setSettlementValue(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getBlockInstrument().getSettlementValue())), 0));

        productSummary.getHybridInstrument().setInstrumentId(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getHybridInstrument().getInstrumentId())), 0));
        productSummary.getHybridInstrument().setLastTradedPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getHybridInstrument().getLastTradedPrice())), 0));
        productSummary.getHybridInstrument().setClosingPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getHybridInstrument().getClosingPrice())), 0));
        productSummary.getHybridInstrument().setAdjustedClosingPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getHybridInstrument().getAdjustedClosingPrice())), 0));
        productSummary.getHybridInstrument().setPctChange(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getHybridInstrument().getPctChange())), 0));
        productSummary.getHybridInstrument().setVwap(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getHybridInstrument().getVwap())), 0));
        productSummary.getHybridInstrument().setTotalVolume(BendecUtils.uInt64FromByteArray(cipher.update(BendecUtils.uInt64ToByteArray(productSummary.getHybridInstrument().getTotalVolume())), 0));
        productSummary.getHybridInstrument().setTotalValue(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getHybridInstrument().getTotalValue())), 0));
        productSummary.getHybridInstrument().setOpeningPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getHybridInstrument().getOpeningPrice())), 0));
        productSummary.getHybridInstrument().setMaxPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getHybridInstrument().getMaxPrice())), 0));
        productSummary.getHybridInstrument().setMinPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getHybridInstrument().getMinPrice())), 0));
        productSummary.getHybridInstrument().setSettlementPrice(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getHybridInstrument().getSettlementPrice())), 0));
        productSummary.getHybridInstrument().setSettlementValue(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getHybridInstrument().getSettlementValue())), 0));

        productSummary.setTradingValueCurrency(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getTradingValueCurrency())), 0));
        productSummary.setMarkerPriceChange(ChangeIndicator.getChangeIndicator((int )BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getMarkerPriceChange().getChangeIndicatorValue())), 0)));
        productSummary.setImpliedVolatility(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getImpliedVolatility())), 0));
        productSummary.setDelta(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getDelta())), 0));
        productSummary.setGamma(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getGamma())), 0));
        productSummary.setRho(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getRho())), 0));
        productSummary.setTheta(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getTheta())), 0));
        productSummary.setVega(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getVega())), 0));
        productSummary.setVolatility(BendecUtils.int64FromByteArray(cipher.update(BendecUtils.int64ToByteArray(productSummary.getVolatility())), 0));

        System.arraycopy(productSummary.toBytes(), 0, bytes, 0, bytes.length);
    }
}
