package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: ExecType
 * Type of execution being reported. Uses subset of ExecType for trade capture reports.
 */
public enum ExecType {
    /**
     * Not applicable
     */
    NA(1),
    /**
     * Rejected.
     */
    REJECTED(8),
    /**
     * Expired
     */
    EXPIRED(12),
    /**
     * Trade.
     */
    TRADE(15),
    /**
     * Trade Correct.
     */
    TRADECORRECT(16),
    /**
     * Trade Cancel.
     */
    TRADECANCEL(17);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, ExecType> TYPES = new HashMap<>();
    static {
        for (ExecType type : ExecType.values()) {
            TYPES.put(type.value, type);
        }
    }

    ExecType(int newValue) {
        value = newValue;
    }

    /**
     * Get ExecType by attribute
     * @param val
     * @return ExecType enum or null if variant is undefined
     */
    public static ExecType getExecType(int val) {
        return TYPES.get(val);
    }

    /**
     * Get ExecType int value
     * @return int value
     */
    public int getExecTypeValue() {
        return value;
    }
    
    /**
     * Get ExecType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static ExecType getExecType(byte[] bytes, int offset) {
        return getExecType(BendecUtils.uInt8FromByteArray(bytes, offset));
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