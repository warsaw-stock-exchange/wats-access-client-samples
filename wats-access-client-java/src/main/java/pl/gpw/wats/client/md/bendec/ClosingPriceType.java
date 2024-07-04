package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: ClosingPriceType
 * Closing Price Type
 */
public enum ClosingPriceType {
    /**
     * Initial price of the instrument
     */
    INITIALPRICE(1),
    /**
     * Last traded price
     */
    LTP(2),
    /**
     * Last adjusted closing price
     */
    LASTACP(3),
    /**
     * Fair value
     */
    FAIRVALUE(4),
    /**
     * Daily Settlement Price
     */
    DAILYSETTLEMENTPRICE(5),
    /**
     * Final Settlement Price
     */
    FINALSETTLEMENTPRICE(6);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, ClosingPriceType> TYPES = new HashMap<>();
    static {
        for (ClosingPriceType type : ClosingPriceType.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    ClosingPriceType(int newValue) {
        value = newValue;
    }
    
    /**
     * Get ClosingPriceType by attribute
     * @param val
     * @return ClosingPriceType enum or null if variant is undefined
     */
    public static ClosingPriceType getClosingPriceType(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get ClosingPriceType int value
     * @return int value
     */
    public int getClosingPriceTypeValue() {
        return value; 
    }
    
    /**
     * Get ClosingPriceType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static ClosingPriceType getClosingPriceType(byte[] bytes, int offset) {
        return getClosingPriceType(BendecUtils.uInt8FromByteArray(bytes, offset));
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