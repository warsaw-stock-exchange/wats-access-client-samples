package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: Capacity
 * Designates the capacity of the firm placing the order.
 */
public enum Capacity {
    /**
     * Agency (mapped to AOTC).
     */
    AGENCY(1),
    /**
     * Principal (mapped to DEAL).
     */
    PRINCIPAL(2),
    /**
     * Riskless Principal (mapped to MTCH)
     */
    RISKLESSPRINCIPAL(3),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, Capacity> TYPES = new HashMap<>();
    static {
        for (Capacity type : Capacity.values()) {
            TYPES.put(type.value, type);
        }
    }


    Capacity(int newValue) {
        value = newValue;
    }

    /**
     Get Capacity from java input
     * @param newValue
     * @return Capacity enum
     */
    public static Capacity getCapacity(int newValue) {
        Capacity val = TYPES.get(newValue);
        return val == null ? Capacity.UNKNOWN : val;
    }

    /**
     * Get Capacity int value
     * @return int value
     */
    public int getCapacityValue() { return value; }


    /**
     Get Capacity from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static Capacity getCapacity(byte[] bytes, int offset) {
        return getCapacity(BendecUtils.uInt8FromByteArray(bytes, offset));
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
