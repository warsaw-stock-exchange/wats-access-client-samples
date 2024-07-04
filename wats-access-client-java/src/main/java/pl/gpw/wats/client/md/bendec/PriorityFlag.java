package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: PriorityFlag
 * Priority loss flag.
 */
public enum PriorityFlag {
    /**
     * Order modification caused loss of priority.
     */
    LOST(1),
    /**
     * Order modification did not cause loss of priority.
     */
    RETAINED(2);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, PriorityFlag> TYPES = new HashMap<>();
    static {
        for (PriorityFlag type : PriorityFlag.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    PriorityFlag(int newValue) {
        value = newValue;
    }
    
    /**
     * Get PriorityFlag by attribute
     * @param val
     * @return PriorityFlag enum or null if variant is undefined
     */
    public static PriorityFlag getPriorityFlag(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get PriorityFlag int value
     * @return int value
     */
    public int getPriorityFlagValue() {
        return value; 
    }
    
    /**
     * Get PriorityFlag from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static PriorityFlag getPriorityFlag(byte[] bytes, int offset) {
        return getPriorityFlag(BendecUtils.uInt8FromByteArray(bytes, offset));
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