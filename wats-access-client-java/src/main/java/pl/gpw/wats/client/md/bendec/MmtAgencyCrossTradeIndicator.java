package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: MmtAgencyCrossTradeIndicator
 * MMT Agency Cross Trade Indicator
 */
public enum MmtAgencyCrossTradeIndicator {
    /**
     * X = Agency Cross Trade
     */
    AGENCYCROSSTRADE(1),
    /**
     * - = No Agency Cross Trade
     */
    NOAGENCYCROSSTRADE(2);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, MmtAgencyCrossTradeIndicator> TYPES = new HashMap<>();
    static {
        for (MmtAgencyCrossTradeIndicator type : MmtAgencyCrossTradeIndicator.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    MmtAgencyCrossTradeIndicator(int newValue) {
        value = newValue;
    }
    
    /**
     * Get MmtAgencyCrossTradeIndicator by attribute
     * @param val
     * @return MmtAgencyCrossTradeIndicator enum or null if variant is undefined
     */
    public static MmtAgencyCrossTradeIndicator getMmtAgencyCrossTradeIndicator(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get MmtAgencyCrossTradeIndicator int value
     * @return int value
     */
    public int getMmtAgencyCrossTradeIndicatorValue() {
        return value; 
    }
    
    /**
     * Get MmtAgencyCrossTradeIndicator from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MmtAgencyCrossTradeIndicator getMmtAgencyCrossTradeIndicator(byte[] bytes, int offset) {
        return getMmtAgencyCrossTradeIndicator(BendecUtils.uInt8FromByteArray(bytes, offset));
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