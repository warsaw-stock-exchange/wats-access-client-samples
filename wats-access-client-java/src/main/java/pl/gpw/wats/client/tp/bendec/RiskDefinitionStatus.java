package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: RiskDefinitionStatus
 * Risk limit definition request status.
 */
public enum RiskDefinitionStatus {
    /**
     * Ack
     */
    ACK(1),
    /**
     * Rejected
     */
    REJECTED(3),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, RiskDefinitionStatus> TYPES = new HashMap<>();
    static {
        for (RiskDefinitionStatus type : RiskDefinitionStatus.values()) {
            TYPES.put(type.value, type);
        }
    }


    RiskDefinitionStatus(int newValue) {
        value = newValue;
    }

    /**
     Get RiskDefinitionStatus from java input
     * @param newValue
     * @return RiskDefinitionStatus enum
     */
    public static RiskDefinitionStatus getRiskDefinitionStatus(int newValue) {
        RiskDefinitionStatus val = TYPES.get(newValue);
        return val == null ? RiskDefinitionStatus.UNKNOWN : val;
    }

    /**
     * Get RiskDefinitionStatus int value
     * @return int value
     */
    public int getRiskDefinitionStatusValue() { return value; }


    /**
     Get RiskDefinitionStatus from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static RiskDefinitionStatus getRiskDefinitionStatus(byte[] bytes, int offset) {
        return getRiskDefinitionStatus(BendecUtils.uInt8FromByteArray(bytes, offset));
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
