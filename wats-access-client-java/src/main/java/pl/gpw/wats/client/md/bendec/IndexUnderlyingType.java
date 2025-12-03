package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: IndexUnderlyingType
 * Underlying type of index.
 */
public enum IndexUnderlyingType {
    /**
     * Not applicable
     */
    NOTAPPLICABLE(1),
    /**
     * Index.
     */
    INDEX(2),
    /**
     * Reference Rate.
     */
    REFERENCERATE(3);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, IndexUnderlyingType> TYPES = new HashMap<>();
    static {
        for (IndexUnderlyingType type : IndexUnderlyingType.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    IndexUnderlyingType(int newValue) {
        value = newValue;
    }
    
    /**
     * Get IndexUnderlyingType by attribute
     * @param val
     * @return IndexUnderlyingType enum or null if variant is undefined
     */
    public static IndexUnderlyingType getIndexUnderlyingType(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get IndexUnderlyingType int value
     * @return int value
     */
    public int getIndexUnderlyingTypeValue() {
        return value; 
    }
    
    /**
     * Get IndexUnderlyingType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static IndexUnderlyingType getIndexUnderlyingType(byte[] bytes, int offset) {
        return getIndexUnderlyingType(BendecUtils.uInt8FromByteArray(bytes, offset));
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