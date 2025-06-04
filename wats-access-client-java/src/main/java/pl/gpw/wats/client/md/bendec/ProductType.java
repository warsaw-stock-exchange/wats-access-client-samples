package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: ProductType
 * undefined
 */
public enum ProductType {
    /**
     * Equity
     */
    EQUITY(1),
    /**
     * Fixed income
     */
    FIXEDINCOME(2),
    /**
     * Derivative Futures
     */
    DERIVATIVEFUTURES(3),
    /**
     * Derivative Options
     */
    DERIVATIVEOPTIONS(4),
    /**
     * Index
     */
    INDEX(5),
    /**
     * Currency
     */
    CURRENCY(6),
    /**
     * Structured Product
     */
    STRUCTUREDPRODUCT(7),
    TRACKER(9);
    
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
     * Get ProductType by attribute
     * @param val
     * @return ProductType enum or null if variant is undefined
     */
    public static ProductType getProductType(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get ProductType int value
     * @return int value
     */
    public int getProductTypeValue() {
        return value; 
    }
    
    /**
     * Get ProductType from bytes
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