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
     * Reference price placed in the value field
     */
    REFERENCEPRICE(1),
    /**
     * TBSP.Price price placed in the value field
     */
    REFERENCEINDEXPRICE(2),
    /**
     * TBSP.FixPrice price placed in the value field
     */
    REFERENCEFIXINGINDEXPRICE(3),
    /**
     * MidPrice price placed in the value field
     */
    MIDPRICE(4),
    /**
     * The fixing prices placed appropriately in the value field as the average price, valueBid as the bid price, and valueAsk as the ask price.
     */
    FIXINGPRICE(5),
    /**
     * The fixing YTM (i.e. yield to maturity) appropriately placed in the value field as the average YTM, valueBid as the bid YTM, and valueAsk as the ask YTM.
     */
    FIXINGYTM(6);
    
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