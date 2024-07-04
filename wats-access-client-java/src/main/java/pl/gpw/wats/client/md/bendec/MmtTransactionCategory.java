package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: MmtTransactionCategory
 * MMT Transaction Category
 */
public enum MmtTransactionCategory {
    /**
     * D = Dark Trade
     */
    DARKTRADE(1),
    /**
     * R = Trade that has received price improvement
     */
    TRADEPRICEIMPROVEMENT (2),
    /**
     * Z = Package Trade (excluding Exchange For Physicals)
     */
    PACKAGETRADE(3),
    /**
     * Y = Exchange For Physicals Trade
     */
    EXCHANGEFORPHYSICALSTRADE(4),
    /**
     * - = None apply (a standard trade for the Market Mechanism and Trading Mode)
     */
    NONE(5);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, MmtTransactionCategory> TYPES = new HashMap<>();
    static {
        for (MmtTransactionCategory type : MmtTransactionCategory.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    MmtTransactionCategory(int newValue) {
        value = newValue;
    }
    
    /**
     * Get MmtTransactionCategory by attribute
     * @param val
     * @return MmtTransactionCategory enum or null if variant is undefined
     */
    public static MmtTransactionCategory getMmtTransactionCategory(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get MmtTransactionCategory int value
     * @return int value
     */
    public int getMmtTransactionCategoryValue() {
        return value; 
    }
    
    /**
     * Get MmtTransactionCategory from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MmtTransactionCategory getMmtTransactionCategory(byte[] bytes, int offset) {
        return getMmtTransactionCategory(BendecUtils.uInt8FromByteArray(bytes, offset));
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