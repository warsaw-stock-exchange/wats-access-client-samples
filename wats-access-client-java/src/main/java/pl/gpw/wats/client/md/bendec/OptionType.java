package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: OptionType
 * Type of option.
 */
public enum OptionType {
    /**
     * Not applicable.
     */
    NOTAPPLICABLE(1),
    /**
     * Call option.
     */
    CALL(2),
    /**
     * Put option.
     */
    PUT(3);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, OptionType> TYPES = new HashMap<>();
    static {
        for (OptionType type : OptionType.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    OptionType(int newValue) {
        value = newValue;
    }
    
    /**
     * Get OptionType by attribute
     * @param val
     * @return OptionType enum or null if variant is undefined
     */
    public static OptionType getOptionType(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get OptionType int value
     * @return int value
     */
    public int getOptionTypeValue() {
        return value; 
    }
    
    /**
     * Get OptionType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static OptionType getOptionType(byte[] bytes, int offset) {
        return getOptionType(BendecUtils.uInt8FromByteArray(bytes, offset));
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