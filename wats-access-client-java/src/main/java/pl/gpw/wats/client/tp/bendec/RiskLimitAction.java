package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: RiskLimitAction
 * Risk limit definition, request action type.
 */
public enum RiskLimitAction {
    /**
     * Reject when limits are exceeded.
     */
    TOREJECT(1),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, RiskLimitAction> TYPES = new HashMap<>();
    static {
        for (RiskLimitAction type : RiskLimitAction.values()) {
            TYPES.put(type.value, type);
        }
    }


    RiskLimitAction(int newValue) {
        value = newValue;
    }

    /**
     Get RiskLimitAction from java input
     * @param newValue
     * @return RiskLimitAction enum
     */
    public static RiskLimitAction getRiskLimitAction(int newValue) {
        RiskLimitAction val = TYPES.get(newValue);
        return val == null ? RiskLimitAction.UNKNOWN : val;
    }

    /**
     * Get RiskLimitAction int value
     * @return int value
     */
    public int getRiskLimitActionValue() { return value; }


    /**
     Get RiskLimitAction from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static RiskLimitAction getRiskLimitAction(byte[] bytes, int offset) {
        return getRiskLimitAction(BendecUtils.uInt8FromByteArray(bytes, offset));
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
