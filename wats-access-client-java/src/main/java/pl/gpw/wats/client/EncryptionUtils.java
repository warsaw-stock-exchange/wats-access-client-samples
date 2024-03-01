package pl.gpw.wats.client;

import pl.gpw.wats.client.md.bendec.*;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;

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
}
