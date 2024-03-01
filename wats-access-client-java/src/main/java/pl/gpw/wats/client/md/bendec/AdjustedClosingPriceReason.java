package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: AdjustedClosingPriceReason
 * Adjusted Closing Price Reason
 */
public enum AdjustedClosingPriceReason {
    /**
     * 0 = Regular
     */
    REGULAR(1),
    /**
     * 1 = Dividend
     */
    DIVIDEND(2),
    /**
     * 2 = Issue Right
     */
    ISSUERIGHT(3),
    /**
     * 3 = Split
     */
    SPLIT(4),
    /**
     * 4 = Reverse Split
     */
    REVERSESPLIT(5),
    /**
     * 5 = Bonus
     */
    BONUS(6),
    /**
     * 6 = Spin-Off
     */
    SPINOFF(7),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, AdjustedClosingPriceReason> TYPES = new HashMap<>();
    static {
        for (AdjustedClosingPriceReason type : AdjustedClosingPriceReason.values()) {
            TYPES.put(type.value, type);
        }
    }


    AdjustedClosingPriceReason(int newValue) {
        value = newValue;
    }

    /**
     Get AdjustedClosingPriceReason from java input
     * @param newValue
     * @return AdjustedClosingPriceReason enum
     */
    public static AdjustedClosingPriceReason getAdjustedClosingPriceReason(int newValue) {
        AdjustedClosingPriceReason val = TYPES.get(newValue);
        return val == null ? AdjustedClosingPriceReason.UNKNOWN : val;
    }

    /**
     * Get AdjustedClosingPriceReason int value
     * @return int value
     */
    public int getAdjustedClosingPriceReasonValue() { return value; }


    /**
     Get AdjustedClosingPriceReason from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static AdjustedClosingPriceReason getAdjustedClosingPriceReason(byte[] bytes, int offset) {
        return getAdjustedClosingPriceReason(BendecUtils.uInt8FromByteArray(bytes, offset));
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
