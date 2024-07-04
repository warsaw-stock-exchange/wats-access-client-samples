package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: NominalValueType
 * Type of the product's nominal value (no nominal, constant or unknown).
 */
public enum NominalValueType {
    /**
     * Not applicable.
     */
    NOTAPPLICABLE(1),
    /**
     * Indicates that the security has a constant nominal value.
     */
    CONSTANT(2);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, NominalValueType> TYPES = new HashMap<>();
    static {
        for (NominalValueType type : NominalValueType.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    NominalValueType(int newValue) {
        value = newValue;
    }
    
    /**
     * Get NominalValueType by attribute
     * @param val
     * @return NominalValueType enum or null if variant is undefined
     */
    public static NominalValueType getNominalValueType(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get NominalValueType int value
     * @return int value
     */
    public int getNominalValueTypeValue() {
        return value; 
    }
    
    /**
     * Get NominalValueType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static NominalValueType getNominalValueType(byte[] bytes, int offset) {
        return getNominalValueType(BendecUtils.uInt8FromByteArray(bytes, offset));
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