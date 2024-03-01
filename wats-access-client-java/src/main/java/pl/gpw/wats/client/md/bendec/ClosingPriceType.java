package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: ClosingPriceType
 * Closing Price Type
 */
public enum ClosingPriceType {
    /**
     * 0 = LTP
     */
    LTP(1),
    /**
     * 1 = Last ACP
     */
    LASTACP(2),
    /**
     * 2 = Fair Value
     */
    FAIRVALUE(3),
    /**
     * 3 = Daily Settlement Price
     */
    DAILYSETTLEMENTPRICE(4),
    /**
     * 4 = Final Settlement Price
     */
    FINALSETTLEMENTPRICE(5),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, ClosingPriceType> TYPES = new HashMap<>();
    static {
        for (ClosingPriceType type : ClosingPriceType.values()) {
            TYPES.put(type.value, type);
        }
    }


    ClosingPriceType(int newValue) {
        value = newValue;
    }

    /**
     Get ClosingPriceType from java input
     * @param newValue
     * @return ClosingPriceType enum
     */
    public static ClosingPriceType getClosingPriceType(int newValue) {
        ClosingPriceType val = TYPES.get(newValue);
        return val == null ? ClosingPriceType.UNKNOWN : val;
    }

    /**
     * Get ClosingPriceType int value
     * @return int value
     */
    public int getClosingPriceTypeValue() { return value; }


    /**
     Get ClosingPriceType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static ClosingPriceType getClosingPriceType(byte[] bytes, int offset) {
        return getClosingPriceType(BendecUtils.uInt8FromByteArray(bytes, offset));
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
