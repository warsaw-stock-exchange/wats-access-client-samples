package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: ProductType
 * undefined
 */
public enum ProductType {
    /**
     * Equity
     */
    FINANCIALPRODUCTSHARE(1),
    /**
     * Fixed income
     */
    FINANCIALPRODUCTBOND(2),
    /**
     * Futures
     */
    FINANCIALPRODUCTDERIVATIVEFUTURES(3),
    /**
     * Options
     */
    FINANCIALPRODUCTDERIVATIVEOPTIONS(4),
    /**
     * Index
     */
    FINANCIALPRODUCTINDEX(5),
    /**
     * Currency
     */
    FINANCIALPRODUCTCURRENCY(6),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, ProductType> TYPES = new HashMap<>();
    static {
        for (ProductType type : ProductType.values()) {
            TYPES.put(type.value, type);
        }
    }


    ProductType(int newValue) {
        value = newValue;
    }

    /**
     Get ProductType from java input
     * @param newValue
     * @return ProductType enum
     */
    public static ProductType getProductType(int newValue) {
        ProductType val = TYPES.get(newValue);
        return val == null ? ProductType.UNKNOWN : val;
    }

    /**
     * Get ProductType int value
     * @return int value
     */
    public int getProductTypeValue() { return value; }


    /**
     Get ProductType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static ProductType getProductType(byte[] bytes, int offset) {
        return getProductType(BendecUtils.uInt8FromByteArray(bytes, offset));
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
