package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: UsIndicator
 * US Regulation S indicator.
 */
public enum UsIndicator {
    /**
     * No trading restrictions.
     */
    NOTAPPLICABLE(1),
    /**
     * Trading restricted due to US Regulation S.
     */
    REGULATIONS(2),
    /**
     * Trading restricted due to US Regulation S with Rule 144A.
     */
    REGULATIONSPLUSRULE144A(3);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, UsIndicator> TYPES = new HashMap<>();
    static {
        for (UsIndicator type : UsIndicator.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    UsIndicator(int newValue) {
        value = newValue;
    }
    
    /**
     * Get UsIndicator by attribute
     * @param val
     * @return UsIndicator enum or null if variant is undefined
     */
    public static UsIndicator getUsIndicator(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get UsIndicator int value
     * @return int value
     */
    public int getUsIndicatorValue() {
        return value; 
    }
    
    /**
     * Get UsIndicator from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static UsIndicator getUsIndicator(byte[] bytes, int offset) {
        return getUsIndicator(BendecUtils.uInt8FromByteArray(bytes, offset));
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