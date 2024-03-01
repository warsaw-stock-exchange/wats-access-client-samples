package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: MmtOffBookAutomatedIndicator
 * MMT Off Book Automated Indicator
 */
public enum MmtOffBookAutomatedIndicator {
    /**
     * '- = Unspecified or does not apply
     */
    UNSPECIFIEDORNOTAPPLY(1),
    /**
     * M = Off Book Non-Automated
     */
    OFFBOOKNONAUTOMATED(2),
    /**
     * Q = Off Book Automated
     */
    OFFBOOKAUTOMATED(3),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, MmtOffBookAutomatedIndicator> TYPES = new HashMap<>();
    static {
        for (MmtOffBookAutomatedIndicator type : MmtOffBookAutomatedIndicator.values()) {
            TYPES.put(type.value, type);
        }
    }


    MmtOffBookAutomatedIndicator(int newValue) {
        value = newValue;
    }

    /**
     Get MmtOffBookAutomatedIndicator from java input
     * @param newValue
     * @return MmtOffBookAutomatedIndicator enum
     */
    public static MmtOffBookAutomatedIndicator getMmtOffBookAutomatedIndicator(int newValue) {
        MmtOffBookAutomatedIndicator val = TYPES.get(newValue);
        return val == null ? MmtOffBookAutomatedIndicator.UNKNOWN : val;
    }

    /**
     * Get MmtOffBookAutomatedIndicator int value
     * @return int value
     */
    public int getMmtOffBookAutomatedIndicatorValue() { return value; }


    /**
     Get MmtOffBookAutomatedIndicator from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MmtOffBookAutomatedIndicator getMmtOffBookAutomatedIndicator(byte[] bytes, int offset) {
        return getMmtOffBookAutomatedIndicator(BendecUtils.uInt8FromByteArray(bytes, offset));
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
