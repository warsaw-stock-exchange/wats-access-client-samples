package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: MatchStatus
 * The status of this trade with respect to matching or comparison.
 */
public enum MatchStatus {
    /**
     * Not applicable.
     */
    NA(0),
    /**
     * Compared, matched or affirmed.
     */
    MATCHED(1),
    /**
     * Uncompared, unmatched, or unaffirmed.
     */
    UNMATCHED(2),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, MatchStatus> TYPES = new HashMap<>();
    static {
        for (MatchStatus type : MatchStatus.values()) {
            TYPES.put(type.value, type);
        }
    }


    MatchStatus(int newValue) {
        value = newValue;
    }

    /**
     Get MatchStatus from java input
     * @param newValue
     * @return MatchStatus enum
     */
    public static MatchStatus getMatchStatus(int newValue) {
        MatchStatus val = TYPES.get(newValue);
        return val == null ? MatchStatus.UNKNOWN : val;
    }

    /**
     * Get MatchStatus int value
     * @return int value
     */
    public int getMatchStatusValue() { return value; }


    /**
     Get MatchStatus from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MatchStatus getMatchStatus(byte[] bytes, int offset) {
        return getMatchStatus(BendecUtils.uInt8FromByteArray(bytes, offset));
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
