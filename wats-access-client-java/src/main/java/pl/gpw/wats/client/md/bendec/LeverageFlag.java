package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: LeverageFlag
 * Identifier of leverage instruments
 */
public enum LeverageFlag {
    /**
     * Not applicable
     */
    NOTAPPLICABLE(1),
    /**
     * Leveraged
     */
    YES(2),
    /**
     * Not leveraged
     */
    NO(3);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, LeverageFlag> TYPES = new HashMap<>();
    static {
        for (LeverageFlag type : LeverageFlag.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    LeverageFlag(int newValue) {
        value = newValue;
    }
    
    /**
     * Get LeverageFlag by attribute
     * @param val
     * @return LeverageFlag enum or null if variant is undefined
     */
    public static LeverageFlag getLeverageFlag(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get LeverageFlag int value
     * @return int value
     */
    public int getLeverageFlagValue() {
        return value; 
    }
    
    /**
     * Get LeverageFlag from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static LeverageFlag getLeverageFlag(byte[] bytes, int offset) {
        return getLeverageFlag(BendecUtils.uInt8FromByteArray(bytes, offset));
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