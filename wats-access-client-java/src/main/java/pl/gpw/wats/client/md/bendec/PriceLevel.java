package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>PriceLevel</h2>
 * <p>Data type for storing snapshot price level.</p>
 * <p>Byte length: 31</p>
 * <p>bool > boolean isEncrypted - A boolean value specifying whether the level is encrypted. | size 1</p>
 * <p>u64 > BigInteger encryptionOffset - Encryption byte offset. | size 8</p>
 * <p>ElementId > long (u32) encryptionKeyId - Encryption Key ID - reference to a EncryptionKey message. | size 4</p>
 * <p>Price > long (i64) price - Price level in currency units. | size 8</p>
 * <p>Quantity > BigInteger (u64) quantity - Quantity of securities available at a given price level. | size 8</p>
 * <p>OrderCount > int (u16) orderCount - Number of open orders at a given price level. | size 2</p>
 * */

public class PriceLevel implements ByteSerializable {

    private boolean isEncrypted;
    private BigInteger encryptionOffset;
    private long encryptionKeyId;
    private long price;
    private BigInteger quantity;
    private int orderCount;
    public static final int byteLength = 31;

    public PriceLevel(boolean isEncrypted, BigInteger encryptionOffset, long encryptionKeyId, long price, BigInteger quantity, int orderCount) {
        this.isEncrypted = isEncrypted;
        this.encryptionOffset = encryptionOffset;
        this.encryptionKeyId = encryptionKeyId;
        this.price = price;
        this.quantity = quantity;
        this.orderCount = orderCount;
    }

    public PriceLevel(byte[] bytes, int offset) {
        this.isEncrypted = BendecUtils.booleanFromByteArray(bytes, offset);
        this.encryptionOffset = BendecUtils.uInt64FromByteArray(bytes, offset + 1);
        this.encryptionKeyId = BendecUtils.uInt32FromByteArray(bytes, offset + 9);
        this.price = BendecUtils.int64FromByteArray(bytes, offset + 13);
        this.quantity = BendecUtils.uInt64FromByteArray(bytes, offset + 21);
        this.orderCount = BendecUtils.uInt16FromByteArray(bytes, offset + 29);
    }

    public PriceLevel(byte[] bytes) {
        this(bytes, 0);
    }

    public PriceLevel() {
    }



    /**
     * @return A boolean value specifying whether the level is encrypted.
     */
    public boolean getIsEncrypted() {
        return this.isEncrypted;
    };
    /**
     * @return Encryption byte offset.
     */
    public BigInteger getEncryptionOffset() {
        return this.encryptionOffset;
    };
    /**
     * @return Encryption Key ID - reference to a EncryptionKey message.
     */
    public long getEncryptionKeyId() {
        return this.encryptionKeyId;
    };
    /**
     * @return Price level in currency units.
     */
    public long getPrice() {
        return this.price;
    };
    /**
     * @return Quantity of securities available at a given price level.
     */
    public BigInteger getQuantity() {
        return this.quantity;
    };
    /**
     * @return Number of open orders at a given price level.
     */
    public int getOrderCount() {
        return this.orderCount;
    };

    /**
     * @param isEncrypted A boolean value specifying whether the level is encrypted.
     */
    public void setIsEncrypted(boolean isEncrypted) {
        this.isEncrypted = isEncrypted;
    };
    /**
     * @param encryptionOffset Encryption byte offset.
     */
    public void setEncryptionOffset(BigInteger encryptionOffset) {
        this.encryptionOffset = encryptionOffset;
    };
    /**
     * @param encryptionKeyId Encryption Key ID - reference to a EncryptionKey message.
     */
    public void setEncryptionKeyId(long encryptionKeyId) {
        this.encryptionKeyId = encryptionKeyId;
    };
    /**
     * @param price Price level in currency units.
     */
    public void setPrice(long price) {
        this.price = price;
    };
    /**
     * @param quantity Quantity of securities available at a given price level.
     */
    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    };
    /**
     * @param orderCount Number of open orders at a given price level.
     */
    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        buffer.put(BendecUtils.booleanToByteArray(this.isEncrypted));
        buffer.put(BendecUtils.uInt64ToByteArray(this.encryptionOffset));
        buffer.put(BendecUtils.uInt32ToByteArray(this.encryptionKeyId));
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        buffer.put(BendecUtils.uInt16ToByteArray(this.orderCount));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        buffer.put(BendecUtils.booleanToByteArray(this.isEncrypted));
        buffer.put(BendecUtils.uInt64ToByteArray(this.encryptionOffset));
        buffer.put(BendecUtils.uInt32ToByteArray(this.encryptionKeyId));
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        buffer.put(BendecUtils.uInt16ToByteArray(this.orderCount));
    }

    @Override
    public int hashCode() {
        return Objects.hash(isEncrypted, encryptionOffset, encryptionKeyId, price, quantity, orderCount);
    }

    @Override
    public String toString() {
        return "PriceLevel{" +
            "isEncrypted=" + isEncrypted +
            ", encryptionOffset=" + encryptionOffset +
            ", encryptionKeyId=" + encryptionKeyId +
            ", price=" + price +
            ", quantity=" + quantity +
            ", orderCount=" + orderCount +
            '}';
        }
}
