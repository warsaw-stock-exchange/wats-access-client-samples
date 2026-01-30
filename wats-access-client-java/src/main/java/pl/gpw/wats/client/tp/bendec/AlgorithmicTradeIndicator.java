package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: AlgorithmicTradeIndicator
 * Indicates algorithmic trade.
 */
public enum AlgorithmicTradeIndicator {
    /**
     * Not applicable.
     */
    NA(1),
    /**
     * Non-algorithmic trade.
     */
    NONALGORITHMICTRADE(2),
    /**
     * Algorithmic trade.
     */
    ALGORITHMICTRADE(3);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, AlgorithmicTradeIndicator> TYPES = new HashMap<>();
    static {
        for (AlgorithmicTradeIndicator type : AlgorithmicTradeIndicator.values()) {
            TYPES.put(type.value, type);
        }
    }

    AlgorithmicTradeIndicator(int newValue) {
        value = newValue;
    }

    /**
     * Get AlgorithmicTradeIndicator by attribute
     * @param val
     * @return AlgorithmicTradeIndicator enum or null if variant is undefined
     */
    public static AlgorithmicTradeIndicator getAlgorithmicTradeIndicator(int val) {
        return TYPES.get(val);
    }

    /**
     * Get AlgorithmicTradeIndicator int value
     * @return int value
     */
    public int getAlgorithmicTradeIndicatorValue() {
        return value;
    }
    
    /**
     * Get AlgorithmicTradeIndicator from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static AlgorithmicTradeIndicator getAlgorithmicTradeIndicator(byte[] bytes, int offset) {
        return getAlgorithmicTradeIndicator(BendecUtils.uInt8FromByteArray(bytes, offset));
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