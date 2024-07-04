package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: MmtSpecialDividendIndicator
 * MMT Special Dividend Indicator
 */
public enum MmtSpecialDividendIndicator {
    /**
     * E = Special Dividend Trade
     */
    SPECIALDIVIDENDTRADE(1),
    /**
     * - = No Special Dividend Trade
     */
    NOSPECIALDIVIDENDTRADE(2);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, MmtSpecialDividendIndicator> TYPES = new HashMap<>();
    static {
        for (MmtSpecialDividendIndicator type : MmtSpecialDividendIndicator.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    MmtSpecialDividendIndicator(int newValue) {
        value = newValue;
    }
    
    /**
     * Get MmtSpecialDividendIndicator by attribute
     * @param val
     * @return MmtSpecialDividendIndicator enum or null if variant is undefined
     */
    public static MmtSpecialDividendIndicator getMmtSpecialDividendIndicator(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get MmtSpecialDividendIndicator int value
     * @return int value
     */
    public int getMmtSpecialDividendIndicatorValue() {
        return value; 
    }
    
    /**
     * Get MmtSpecialDividendIndicator from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MmtSpecialDividendIndicator getMmtSpecialDividendIndicator(byte[] bytes, int offset) {
        return getMmtSpecialDividendIndicator(BendecUtils.uInt8FromByteArray(bytes, offset));
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