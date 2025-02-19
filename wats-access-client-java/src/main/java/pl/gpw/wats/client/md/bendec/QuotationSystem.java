package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: QuotationSystem
 * Quotation system
 */
public enum QuotationSystem {
    /**
     * Not Applicable.
     */
    NA(1),
    /**
     * Single price quotation system with a single fixing.
     */
    SINGLEPRICESINGLEFIXING(2),
    /**
     * Continuous trading.
     */
    CONTINUOUSTRADING(3),
    /**
     * Single price quotation system with two fixings.
     */
    SINGLEPRICETWOFIXINGS(4);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, QuotationSystem> TYPES = new HashMap<>();
    static {
        for (QuotationSystem type : QuotationSystem.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    QuotationSystem(int newValue) {
        value = newValue;
    }
    
    /**
     * Get QuotationSystem by attribute
     * @param val
     * @return QuotationSystem enum or null if variant is undefined
     */
    public static QuotationSystem getQuotationSystem(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get QuotationSystem int value
     * @return int value
     */
    public int getQuotationSystemValue() {
        return value; 
    }
    
    /**
     * Get QuotationSystem from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static QuotationSystem getQuotationSystem(byte[] bytes, int offset) {
        return getQuotationSystem(BendecUtils.uInt8FromByteArray(bytes, offset));
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