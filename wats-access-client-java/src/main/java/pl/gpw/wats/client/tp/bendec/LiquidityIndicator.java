package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: LiquidityIndicator
 * Liquidity indicator
 */
public enum LiquidityIndicator {
    /**
     * Buy order execution
     */
    BUYORDEREXECUTION(1),
    /**
     * Sell order execution
     */
    SELLORDEREXECUTION(2),
    /**
     * Auction execution
     */
    AUCTIONEXECUTION(3),
    /**
     * Not applicable
     */
    NOTAPPLICABLE(4);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, LiquidityIndicator> TYPES = new HashMap<>();
    static {
        for (LiquidityIndicator type : LiquidityIndicator.values()) {
            TYPES.put(type.value, type);
        }
    }

    LiquidityIndicator(int newValue) {
        value = newValue;
    }

    /**
     * Get LiquidityIndicator by attribute
     * @param val
     * @return LiquidityIndicator enum or null if variant is undefined
     */
    public static LiquidityIndicator getLiquidityIndicator(int val) {
        return TYPES.get(val);
    }

    /**
     * Get LiquidityIndicator int value
     * @return int value
     */
    public int getLiquidityIndicatorValue() {
        return value;
    }
    
    /**
     * Get LiquidityIndicator from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static LiquidityIndicator getLiquidityIndicator(byte[] bytes, int offset) {
        return getLiquidityIndicator(BendecUtils.uInt8FromByteArray(bytes, offset));
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