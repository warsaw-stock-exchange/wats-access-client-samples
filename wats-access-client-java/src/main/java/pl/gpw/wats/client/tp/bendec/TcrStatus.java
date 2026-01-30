package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: TcrStatus
 * Trade Report status.
 */
public enum TcrStatus {
    /**
     * New.
     */
    NEW(1),
    /**
     * Accepted.
     */
    ACCEPTED(2),
    /**
     * Rejected.
     */
    REJECTED(3),
    /**
     * Cancelled.
     */
    CANCELLED(4);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, TcrStatus> TYPES = new HashMap<>();
    static {
        for (TcrStatus type : TcrStatus.values()) {
            TYPES.put(type.value, type);
        }
    }

    TcrStatus(int newValue) {
        value = newValue;
    }

    /**
     * Get TcrStatus by attribute
     * @param val
     * @return TcrStatus enum or null if variant is undefined
     */
    public static TcrStatus getTcrStatus(int val) {
        return TYPES.get(val);
    }

    /**
     * Get TcrStatus int value
     * @return int value
     */
    public int getTcrStatusValue() {
        return value;
    }
    
    /**
     * Get TcrStatus from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static TcrStatus getTcrStatus(byte[] bytes, int offset) {
        return getTcrStatus(BendecUtils.uInt8FromByteArray(bytes, offset));
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