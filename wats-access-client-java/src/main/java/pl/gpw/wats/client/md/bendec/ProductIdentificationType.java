package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: ProductIdentificationType
 * Type of product identification (for example ISIN or SEDOL).
 */
public enum ProductIdentificationType {
    /**
     * Financial instrument's ISIN.
     */
    ISIN(1),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, ProductIdentificationType> TYPES = new HashMap<>();
    static {
        for (ProductIdentificationType type : ProductIdentificationType.values()) {
            TYPES.put(type.value, type);
        }
    }


    ProductIdentificationType(int newValue) {
        value = newValue;
    }

    /**
     Get ProductIdentificationType from java input
     * @param newValue
     * @return ProductIdentificationType enum
     */
    public static ProductIdentificationType getProductIdentificationType(int newValue) {
        ProductIdentificationType val = TYPES.get(newValue);
        return val == null ? ProductIdentificationType.UNKNOWN : val;
    }

    /**
     * Get ProductIdentificationType int value
     * @return int value
     */
    public int getProductIdentificationTypeValue() { return value; }


    /**
     Get ProductIdentificationType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static ProductIdentificationType getProductIdentificationType(byte[] bytes, int offset) {
        return getProductIdentificationType(BendecUtils.uInt8FromByteArray(bytes, offset));
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
