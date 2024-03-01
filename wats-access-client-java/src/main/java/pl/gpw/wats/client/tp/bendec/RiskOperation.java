package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: RiskOperation
 * Risk limit definition message operation type.
 */
public enum RiskOperation {
    /**
     * Add operation.
     */
    ADD(1),
    /**
     * Modify operation.
     */
    MODIFY(2),
    /**
     * Delete operation.
     */
    DELETE(3),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, RiskOperation> TYPES = new HashMap<>();
    static {
        for (RiskOperation type : RiskOperation.values()) {
            TYPES.put(type.value, type);
        }
    }


    RiskOperation(int newValue) {
        value = newValue;
    }

    /**
     Get RiskOperation from java input
     * @param newValue
     * @return RiskOperation enum
     */
    public static RiskOperation getRiskOperation(int newValue) {
        RiskOperation val = TYPES.get(newValue);
        return val == null ? RiskOperation.UNKNOWN : val;
    }

    /**
     * Get RiskOperation int value
     * @return int value
     */
    public int getRiskOperationValue() { return value; }


    /**
     Get RiskOperation from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static RiskOperation getRiskOperation(byte[] bytes, int offset) {
        return getRiskOperation(BendecUtils.uInt8FromByteArray(bytes, offset));
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
