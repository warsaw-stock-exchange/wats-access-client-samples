package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: AdjustedClosingPriceReason
 * Adjusted Closing Price Reason
 */
public enum AdjustedClosingPriceReason {
    /**
     * Not applicable.
     */
    NOTAPPLICABLE(1),
    /**
     * Regular
     */
    REGULAR(2),
    /**
     * Dividend
     */
    DIVIDEND(3),
    /**
     * Issue Right
     */
    ISSUERIGHT(4),
    /**
     * Split
     */
    SPLIT(5),
    /**
     * Reverse Split
     */
    REVERSESPLIT(6),
    /**
     * Bonus
     */
    BONUS(7),
    /**
     * Spin-Off
     */
    SPINOFF(8),
    /**
     * Tick Size Change
     */
    TICKSIZECHANGE(9),
    /**
     * Order Purge
     */
    ORDERPURGE(10),
    /**
     * Other reason
     */
    OTHERREASON(11);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, AdjustedClosingPriceReason> TYPES = new HashMap<>();
    static {
        for (AdjustedClosingPriceReason type : AdjustedClosingPriceReason.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    AdjustedClosingPriceReason(int newValue) {
        value = newValue;
    }
    
    /**
     * Get AdjustedClosingPriceReason by attribute
     * @param val
     * @return AdjustedClosingPriceReason enum or null if variant is undefined
     */
    public static AdjustedClosingPriceReason getAdjustedClosingPriceReason(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get AdjustedClosingPriceReason int value
     * @return int value
     */
    public int getAdjustedClosingPriceReasonValue() {
        return value; 
    }
    
    /**
     * Get AdjustedClosingPriceReason from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static AdjustedClosingPriceReason getAdjustedClosingPriceReason(byte[] bytes, int offset) {
        return getAdjustedClosingPriceReason(BendecUtils.uInt8FromByteArray(bytes, offset));
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