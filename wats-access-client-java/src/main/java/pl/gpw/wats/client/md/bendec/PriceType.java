package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: PriceType
 * Price type.
 */
public enum PriceType {
    REFERENCEPRICE(1),
    MIDPOINT(2),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, PriceType> TYPES = new HashMap<>();
    static {
        for (PriceType type : PriceType.values()) {
            TYPES.put(type.value, type);
        }
    }


    PriceType(int newValue) {
        value = newValue;
    }

    /**
     Get PriceType from java input
     * @param newValue
     * @return PriceType enum
     */
    public static PriceType getPriceType(int newValue) {
        PriceType val = TYPES.get(newValue);
        return val == null ? PriceType.UNKNOWN : val;
    }

    /**
     * Get PriceType int value
     * @return int value
     */
    public int getPriceTypeValue() { return value; }


    /**
     Get PriceType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static PriceType getPriceType(byte[] bytes, int offset) {
        return getPriceType(BendecUtils.uInt8FromByteArray(bytes, offset));
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
