package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: PriceUpdateType
 * Price update message variant.
 */
public enum PriceUpdateType {
    /**
     * Reference price placed in the vale field
     */
    REFERENCEPRICE(1),
    /**
     * MidPoint price placed in the vale field
     */
    MIDPOINT(2),
    /**
     * The first fixing prices fixing prices appropriately placed in the value field as the average price, valueBid as the bid price, and valueAsk as the ask price.
     */
    FIXING1PRICE(3),
    /**
     * The second fixing prices fixing prices appropriately placed in the value field as the average price, valueBid as the bid price, and valueAsk as the ask price.
     */
    FIXING2PRICE(4),
    /**
     * The first fixing YTM (e.i. yield to maturity)  appropriately placed in the value field as the average YTM, valueBid as the bid YTM, and valueAsk as the ask YTM.
     */
    FIXING1YTM(5),
    /**
     * The second fixing YTM (e.i. yield to maturity)  appropriately placed in the value field as the average YTM, valueBid as the bid YTM, and valueAsk as the ask YTM.
     */
    FIXING2YTM(6);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, PriceUpdateType> TYPES = new HashMap<>();
    static {
        for (PriceUpdateType type : PriceUpdateType.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    PriceUpdateType(int newValue) {
        value = newValue;
    }
    
    /**
     * Get PriceUpdateType by attribute
     * @param val
     * @return PriceUpdateType enum or null if variant is undefined
     */
    public static PriceUpdateType getPriceUpdateType(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get PriceUpdateType int value
     * @return int value
     */
    public int getPriceUpdateTypeValue() {
        return value; 
    }
    
    /**
     * Get PriceUpdateType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static PriceUpdateType getPriceUpdateType(byte[] bytes, int offset) {
        return getPriceUpdateType(BendecUtils.uInt8FromByteArray(bytes, offset));
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