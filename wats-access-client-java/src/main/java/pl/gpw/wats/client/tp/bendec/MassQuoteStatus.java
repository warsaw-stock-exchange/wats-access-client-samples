package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: MassQuoteStatus
 * Indicates the status of the mass quote.
 */
public enum MassQuoteStatus {
    /**
     * Mass quote acknowledged by system.
     */
    ACCEPTED(1),
    /**
     * Mass quote rejected.
     */
    REJECTED(2);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, MassQuoteStatus> TYPES = new HashMap<>();
    static {
        for (MassQuoteStatus type : MassQuoteStatus.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    MassQuoteStatus(int newValue) {
        value = newValue;
    }
    
    /**
     * Get MassQuoteStatus by attribute
     * @param val
     * @return MassQuoteStatus enum or null if variant is undefined
     */
    public static MassQuoteStatus getMassQuoteStatus(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get MassQuoteStatus int value
     * @return int value
     */
    public int getMassQuoteStatusValue() {
        return value; 
    }
    
    /**
     * Get MassQuoteStatus from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MassQuoteStatus getMassQuoteStatus(byte[] bytes, int offset) {
        return getMassQuoteStatus(BendecUtils.uInt8FromByteArray(bytes, offset));
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