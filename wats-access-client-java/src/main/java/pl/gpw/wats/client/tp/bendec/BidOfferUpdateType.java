package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: BidOfferUpdateType
 * Indicates a type of the BidOfferUpdate message.
 */
public enum BidOfferUpdateType {
    /**
     * Ipo.
     */
    IPO(1),
    /**
     * Tender Offer.
     */
    TENDEROFFER(2);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, BidOfferUpdateType> TYPES = new HashMap<>();
    static {
        for (BidOfferUpdateType type : BidOfferUpdateType.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    BidOfferUpdateType(int newValue) {
        value = newValue;
    }
    
    /**
     * Get BidOfferUpdateType by attribute
     * @param val
     * @return BidOfferUpdateType enum or null if variant is undefined
     */
    public static BidOfferUpdateType getBidOfferUpdateType(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get BidOfferUpdateType int value
     * @return int value
     */
    public int getBidOfferUpdateTypeValue() {
        return value; 
    }
    
    /**
     * Get BidOfferUpdateType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static BidOfferUpdateType getBidOfferUpdateType(byte[] bytes, int offset) {
        return getBidOfferUpdateType(BendecUtils.uInt8FromByteArray(bytes, offset));
    }
    
    byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        buffer.put(BendecUtils.uInt8ToByteArray(this.value));
        return buffer.array();
    }
    
    void toBytes(ByteBuffer buffer) {
        buffer.put(BendecUtils.uInt8ToByteArray(this.value));
    }
}