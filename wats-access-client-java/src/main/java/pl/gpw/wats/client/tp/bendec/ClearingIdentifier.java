package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: ClearingIdentifier
 * Clearing member identifier type.
 */
public enum ClearingIdentifier {
    /**
     * Not Applicable.
     */
    NOTAPPLICABLE(33),
    /**
     * Legal Entity Identifier.
     */
    LEI(78),
    /**
     * Custom clearing identifier.
     */
    CUSTOM(68);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, ClearingIdentifier> TYPES = new HashMap<>();
    static {
        for (ClearingIdentifier type : ClearingIdentifier.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    ClearingIdentifier(int newValue) {
        value = newValue;
    }
    
    /**
     * Get ClearingIdentifier by attribute
     * @param val
     * @return ClearingIdentifier enum or null if variant is undefined
     */
    public static ClearingIdentifier getClearingIdentifier(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get ClearingIdentifier int value
     * @return int value
     */
    public int getClearingIdentifierValue() {
        return value; 
    }
    
    /**
     * Get ClearingIdentifier from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static ClearingIdentifier getClearingIdentifier(byte[] bytes, int offset) {
        return getClearingIdentifier(BendecUtils.uInt8FromByteArray(bytes, offset));
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