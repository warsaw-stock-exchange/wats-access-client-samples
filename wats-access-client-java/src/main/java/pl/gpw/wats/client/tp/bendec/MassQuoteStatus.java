package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: MassQuoteStatus
 * Indicates the status of the mass quote.
 */
public enum MassQuoteStatus {
    /**
     * Mass quote acknowledged by system.
     */
    ACCEPTED(1),
    /**
     * Mass quote rejected.
     */
    REJECTED(2),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, MassQuoteStatus> TYPES = new HashMap<>();
    static {
        for (MassQuoteStatus type : MassQuoteStatus.values()) {
            TYPES.put(type.value, type);
        }
    }


    MassQuoteStatus(int newValue) {
        value = newValue;
    }

    /**
     Get MassQuoteStatus from java input
     * @param newValue
     * @return MassQuoteStatus enum
     */
    public static MassQuoteStatus getMassQuoteStatus(int newValue) {
        MassQuoteStatus val = TYPES.get(newValue);
        return val == null ? MassQuoteStatus.UNKNOWN : val;
    }

    /**
     * Get MassQuoteStatus int value
     * @return int value
     */
    public int getMassQuoteStatusValue() { return value; }


    /**
     Get MassQuoteStatus from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MassQuoteStatus getMassQuoteStatus(byte[] bytes, int offset) {
        return getMassQuoteStatus(BendecUtils.uInt8FromByteArray(bytes, offset));
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
