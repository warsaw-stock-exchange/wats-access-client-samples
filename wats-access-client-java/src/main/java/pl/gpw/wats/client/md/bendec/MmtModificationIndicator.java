package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: MmtModificationIndicator
 * MMT Modification Indicator
 */
public enum MmtModificationIndicator {
    /**
     * C = Trade Cancellation
     */
    TRADECANCELLATION(1),
    /**
     * A = Trade Amendment
     */
    TRADEAMENDMENT(2),
    /**
     * - = New Trade
     */
    NEWTRADE(3),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, MmtModificationIndicator> TYPES = new HashMap<>();
    static {
        for (MmtModificationIndicator type : MmtModificationIndicator.values()) {
            TYPES.put(type.value, type);
        }
    }


    MmtModificationIndicator(int newValue) {
        value = newValue;
    }

    /**
     Get MmtModificationIndicator from java input
     * @param newValue
     * @return MmtModificationIndicator enum
     */
    public static MmtModificationIndicator getMmtModificationIndicator(int newValue) {
        MmtModificationIndicator val = TYPES.get(newValue);
        return val == null ? MmtModificationIndicator.UNKNOWN : val;
    }

    /**
     * Get MmtModificationIndicator int value
     * @return int value
     */
    public int getMmtModificationIndicatorValue() { return value; }


    /**
     Get MmtModificationIndicator from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MmtModificationIndicator getMmtModificationIndicator(byte[] bytes, int offset) {
        return getMmtModificationIndicator(BendecUtils.uInt8FromByteArray(bytes, offset));
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
