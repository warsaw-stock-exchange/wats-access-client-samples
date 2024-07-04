package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: ExpressionType
 * Expression type (percentage or absolute value).
 */
public enum ExpressionType {
    /**
     * Percentage.
     */
    PERCENTAGE(1),
    /**
     * Absolute value.
     */
    ABSOLUTEVALUE(2);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, ExpressionType> TYPES = new HashMap<>();
    static {
        for (ExpressionType type : ExpressionType.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    ExpressionType(int newValue) {
        value = newValue;
    }
    
    /**
     * Get ExpressionType by attribute
     * @param val
     * @return ExpressionType enum or null if variant is undefined
     */
    public static ExpressionType getExpressionType(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get ExpressionType int value
     * @return int value
     */
    public int getExpressionTypeValue() {
        return value; 
    }
    
    /**
     * Get ExpressionType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static ExpressionType getExpressionType(byte[] bytes, int offset) {
        return getExpressionType(BendecUtils.uInt8FromByteArray(bytes, offset));
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