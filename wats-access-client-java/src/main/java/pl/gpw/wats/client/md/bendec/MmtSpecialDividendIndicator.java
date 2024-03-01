package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: MmtSpecialDividendIndicator
 * MMT Special Dividend Indicator
 */
public enum MmtSpecialDividendIndicator {
    /**
     * E = Special Dividend Trade
     */
    SPECIALDIVIDENDTRADE(1),
    /**
     * - = No Special Dividend Trade
     */
    NOSPECIALDIVIDENDTRADE(2),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, MmtSpecialDividendIndicator> TYPES = new HashMap<>();
    static {
        for (MmtSpecialDividendIndicator type : MmtSpecialDividendIndicator.values()) {
            TYPES.put(type.value, type);
        }
    }


    MmtSpecialDividendIndicator(int newValue) {
        value = newValue;
    }

    /**
     Get MmtSpecialDividendIndicator from java input
     * @param newValue
     * @return MmtSpecialDividendIndicator enum
     */
    public static MmtSpecialDividendIndicator getMmtSpecialDividendIndicator(int newValue) {
        MmtSpecialDividendIndicator val = TYPES.get(newValue);
        return val == null ? MmtSpecialDividendIndicator.UNKNOWN : val;
    }

    /**
     * Get MmtSpecialDividendIndicator int value
     * @return int value
     */
    public int getMmtSpecialDividendIndicatorValue() { return value; }


    /**
     Get MmtSpecialDividendIndicator from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MmtSpecialDividendIndicator getMmtSpecialDividendIndicator(byte[] bytes, int offset) {
        return getMmtSpecialDividendIndicator(BendecUtils.uInt8FromByteArray(bytes, offset));
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
