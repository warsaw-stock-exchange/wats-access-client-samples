package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: RiskLimitRequestType
 * undefined
 */
public enum RiskLimitRequestType {
    /**
     * Definitions.
     */
    DEFINITIONS(1),
    /**
     * Utilization.
     */
    UTILIZATION(2),
    /**
     * Definitions and utilization.
     */
    DEFINITIONSANDUTILIZATIONS(3),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, RiskLimitRequestType> TYPES = new HashMap<>();
    static {
        for (RiskLimitRequestType type : RiskLimitRequestType.values()) {
            TYPES.put(type.value, type);
        }
    }


    RiskLimitRequestType(int newValue) {
        value = newValue;
    }

    /**
     Get RiskLimitRequestType from java input
     * @param newValue
     * @return RiskLimitRequestType enum
     */
    public static RiskLimitRequestType getRiskLimitRequestType(int newValue) {
        RiskLimitRequestType val = TYPES.get(newValue);
        return val == null ? RiskLimitRequestType.UNKNOWN : val;
    }

    /**
     * Get RiskLimitRequestType int value
     * @return int value
     */
    public int getRiskLimitRequestTypeValue() { return value; }


    /**
     Get RiskLimitRequestType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static RiskLimitRequestType getRiskLimitRequestType(byte[] bytes, int offset) {
        return getRiskLimitRequestType(BendecUtils.uInt8FromByteArray(bytes, offset));
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
