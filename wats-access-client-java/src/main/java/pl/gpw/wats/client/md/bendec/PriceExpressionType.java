package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: PriceExpressionType
 * Price expression type for the financial instrument.
 */
public enum PriceExpressionType {
    /**
     * Not applicable.
     */
    NOTAPPLICABLE(3),
    /**
     * Price expressed as absolute value.
     */
    PRICE(1),
    /**
     * Price expressed as percentage.
     */
    PERCENTAGE(2);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, PriceExpressionType> TYPES = new HashMap<>();
    static {
        for (PriceExpressionType type : PriceExpressionType.values()) {
            TYPES.put(type.value, type);
        }
    }

    PriceExpressionType(int newValue) {
        value = newValue;
    }

    /**
     * Get PriceExpressionType by attribute
     * @param val
     * @return PriceExpressionType enum or null if variant is undefined
     */
    public static PriceExpressionType getPriceExpressionType(int val) {
        return TYPES.get(val);
    }

    /**
     * Get PriceExpressionType int value
     * @return int value
     */
    public int getPriceExpressionTypeValue() {
        return value;
    }
    
    /**
     * Get PriceExpressionType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static PriceExpressionType getPriceExpressionType(byte[] bytes, int offset) {
        return getPriceExpressionType(BendecUtils.uInt8FromByteArray(bytes, offset));
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