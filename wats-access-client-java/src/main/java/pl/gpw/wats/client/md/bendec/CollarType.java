package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: CollarType
 * Collar type can be dynamic or static.
 */
public enum CollarType {
    STATIC(1),
    DYNAMIC(2);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, CollarType> TYPES = new HashMap<>();
    static {
        for (CollarType type : CollarType.values()) {
            TYPES.put(type.value, type);
        }
    }

    CollarType(int newValue) {
        value = newValue;
    }

    /**
     * Get CollarType by attribute
     * @param val
     * @return CollarType enum or null if variant is undefined
     */
    public static CollarType getCollarType(int val) {
        return TYPES.get(val);
    }

    /**
     * Get CollarType int value
     * @return int value
     */
    public int getCollarTypeValue() {
        return value;
    }
    
    /**
     * Get CollarType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static CollarType getCollarType(byte[] bytes, int offset) {
        return getCollarType(BendecUtils.uInt8FromByteArray(bytes, offset));
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