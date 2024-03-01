package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: QuotationSystem
 * Quotation system
 */
public enum QuotationSystem {
    /**
     * Single price quotation system with a single fixing.
     */
    SINGLEPRICESINGLEFIXING(1),
    /**
     * Continuous trading.
     */
    CONTINUOUSTRADING(2),
    /**
     * Single price quotation system with two fixings.
     */
    SINGLEPRICETWOFIXINGS(3),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, QuotationSystem> TYPES = new HashMap<>();
    static {
        for (QuotationSystem type : QuotationSystem.values()) {
            TYPES.put(type.value, type);
        }
    }


    QuotationSystem(int newValue) {
        value = newValue;
    }

    /**
     Get QuotationSystem from java input
     * @param newValue
     * @return QuotationSystem enum
     */
    public static QuotationSystem getQuotationSystem(int newValue) {
        QuotationSystem val = TYPES.get(newValue);
        return val == null ? QuotationSystem.UNKNOWN : val;
    }

    /**
     * Get QuotationSystem int value
     * @return int value
     */
    public int getQuotationSystemValue() { return value; }


    /**
     Get QuotationSystem from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static QuotationSystem getQuotationSystem(byte[] bytes, int offset) {
        return getQuotationSystem(BendecUtils.uInt8FromByteArray(bytes, offset));
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
