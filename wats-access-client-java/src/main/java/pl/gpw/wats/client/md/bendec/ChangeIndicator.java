package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: ChangeIndicator
 * Defines a vaule change indicator (e.g. market price).
 */
public enum ChangeIndicator {
    /**
     * Increase.
     */
    INCREASE(1),
    /**
     * Decrease.
     */
    DECREASE(2),
    /**
     * No change.
     */
    NOCHANGE(3),
    /**
     * Not Applicable.
     */
    NOTAPPLICABLE(4),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, ChangeIndicator> TYPES = new HashMap<>();
    static {
        for (ChangeIndicator type : ChangeIndicator.values()) {
            TYPES.put(type.value, type);
        }
    }


    ChangeIndicator(int newValue) {
        value = newValue;
    }

    /**
     Get ChangeIndicator from java input
     * @param newValue
     * @return ChangeIndicator enum
     */
    public static ChangeIndicator getChangeIndicator(int newValue) {
        ChangeIndicator val = TYPES.get(newValue);
        return val == null ? ChangeIndicator.UNKNOWN : val;
    }

    /**
     * Get ChangeIndicator int value
     * @return int value
     */
    public int getChangeIndicatorValue() { return value; }


    /**
     Get ChangeIndicator from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static ChangeIndicator getChangeIndicator(byte[] bytes, int offset) {
        return getChangeIndicator(BendecUtils.uInt8FromByteArray(bytes, offset));
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
