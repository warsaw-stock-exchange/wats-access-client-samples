package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: MmtTransationCategory
 * MMT Transaction Category
 */
public enum MmtTransationCategory {
    /**
     * D = Dark Trade
     */
    DARKTRADE(1),
    /**
     * R = Trade that has received price improvement
     */
    TRADEPRICEIMPROVEMENT (2),
    /**
     * Z = Package Trade (excluding Exchange For Physicals)
     */
    PACKAGETRADE(3),
    /**
     * Y = Exchange For Physicals Trade
     */
    EXCHANGEFORPHYSICALSTRADE(4),
    /**
     * - = None apply (a standard trade for the Market Mechanism and Trading Mode)
     */
    NONE(5),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, MmtTransationCategory> TYPES = new HashMap<>();
    static {
        for (MmtTransationCategory type : MmtTransationCategory.values()) {
            TYPES.put(type.value, type);
        }
    }


    MmtTransationCategory(int newValue) {
        value = newValue;
    }

    /**
     Get MmtTransationCategory from java input
     * @param newValue
     * @return MmtTransationCategory enum
     */
    public static MmtTransationCategory getMmtTransationCategory(int newValue) {
        MmtTransationCategory val = TYPES.get(newValue);
        return val == null ? MmtTransationCategory.UNKNOWN : val;
    }

    /**
     * Get MmtTransationCategory int value
     * @return int value
     */
    public int getMmtTransationCategoryValue() { return value; }


    /**
     Get MmtTransationCategory from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MmtTransationCategory getMmtTransationCategory(byte[] bytes, int offset) {
        return getMmtTransationCategory(BendecUtils.uInt8FromByteArray(bytes, offset));
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
