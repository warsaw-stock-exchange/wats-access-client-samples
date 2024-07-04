package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: RequestForExecutionReason
 * The reason for this RFE.
 */
public enum RequestForExecutionReason {
    PASSIVEQUOTE(1),
    AGGRESSIVEQUOTE(2);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, RequestForExecutionReason> TYPES = new HashMap<>();
    static {
        for (RequestForExecutionReason type : RequestForExecutionReason.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    RequestForExecutionReason(int newValue) {
        value = newValue;
    }
    
    /**
     * Get RequestForExecutionReason by attribute
     * @param val
     * @return RequestForExecutionReason enum or null if variant is undefined
     */
    public static RequestForExecutionReason getRequestForExecutionReason(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get RequestForExecutionReason int value
     * @return int value
     */
    public int getRequestForExecutionReasonValue() {
        return value; 
    }
    
    /**
     * Get RequestForExecutionReason from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static RequestForExecutionReason getRequestForExecutionReason(byte[] bytes, int offset) {
        return getRequestForExecutionReason(BendecUtils.uInt8FromByteArray(bytes, offset));
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