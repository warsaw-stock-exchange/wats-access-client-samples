package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: MassCancelRejectionReason
 * Indicates a mass cancel rejection reason.
 */
public enum MassCancelRejectionReason {
    /**
     * Not applicable.
     */
    NA(101),
    /**
     * Unknown instrument ID.
     */
    UNKNOWNINSTRUMENTID(100),
    /**
     * Unknown market segment ID.
     */
    UNKNOWNMARKETSEGMENTID(1016),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 2;


    private static final Map<Integer, MassCancelRejectionReason> TYPES = new HashMap<>();
    static {
        for (MassCancelRejectionReason type : MassCancelRejectionReason.values()) {
            TYPES.put(type.value, type);
        }
    }


    MassCancelRejectionReason(int newValue) {
        value = newValue;
    }

    /**
     Get MassCancelRejectionReason from java input
     * @param newValue
     * @return MassCancelRejectionReason enum
     */
    public static MassCancelRejectionReason getMassCancelRejectionReason(int newValue) {
        MassCancelRejectionReason val = TYPES.get(newValue);
        return val == null ? MassCancelRejectionReason.UNKNOWN : val;
    }

    /**
     * Get MassCancelRejectionReason int value
     * @return int value
     */
    public int getMassCancelRejectionReasonValue() { return value; }


    /**
     Get MassCancelRejectionReason from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MassCancelRejectionReason getMassCancelRejectionReason(byte[] bytes, int offset) {
        return getMassCancelRejectionReason(BendecUtils.uInt16FromByteArray(bytes, offset));
    }

    byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        buffer.put(BendecUtils.uInt16ToByteArray(this.value));
        return buffer.array();
    }

    void toBytes(ByteBuffer buffer) {
        buffer.put(BendecUtils.uInt16ToByteArray(this.value));
    }

}
