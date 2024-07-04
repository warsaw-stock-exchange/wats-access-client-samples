package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: TradeReportTransType
 * Identifies Trade Report message transaction type.
 */
public enum TradeReportTransType {
    /**
     * New.
     */
    NEW(1),
    /**
     * Cancel.
     */
    CANCEL(2),
    /**
     * Replace.
     */
    REPLACE(3);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, TradeReportTransType> TYPES = new HashMap<>();
    static {
        for (TradeReportTransType type : TradeReportTransType.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    TradeReportTransType(int newValue) {
        value = newValue;
    }
    
    /**
     * Get TradeReportTransType by attribute
     * @param val
     * @return TradeReportTransType enum or null if variant is undefined
     */
    public static TradeReportTransType getTradeReportTransType(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get TradeReportTransType int value
     * @return int value
     */
    public int getTradeReportTransTypeValue() {
        return value; 
    }
    
    /**
     * Get TradeReportTransType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static TradeReportTransType getTradeReportTransType(byte[] bytes, int offset) {
        return getTradeReportTransType(BendecUtils.uInt8FromByteArray(bytes, offset));
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