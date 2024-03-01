package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: SettlementType
 * Indicates settlement type of the derivative.
 */
public enum SettlementType {
    /**
     * Not applicable.
     */
    NOTAPPLICABLE(1),
    /**
     * Derivatives are settled in cash.
     */
    CASHSETTLEMENT(2),
    /**
     * Derivatives are settled as physical delivery of the underlying.
     */
    PHYSICALDELIVERY(3),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, SettlementType> TYPES = new HashMap<>();
    static {
        for (SettlementType type : SettlementType.values()) {
            TYPES.put(type.value, type);
        }
    }


    SettlementType(int newValue) {
        value = newValue;
    }

    /**
     Get SettlementType from java input
     * @param newValue
     * @return SettlementType enum
     */
    public static SettlementType getSettlementType(int newValue) {
        SettlementType val = TYPES.get(newValue);
        return val == null ? SettlementType.UNKNOWN : val;
    }

    /**
     * Get SettlementType int value
     * @return int value
     */
    public int getSettlementTypeValue() { return value; }


    /**
     Get SettlementType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static SettlementType getSettlementType(byte[] bytes, int offset) {
        return getSettlementType(BendecUtils.uInt8FromByteArray(bytes, offset));
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
