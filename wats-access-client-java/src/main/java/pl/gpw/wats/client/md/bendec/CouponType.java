package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: CouponType
 * A bond's coupon type.
 */
public enum CouponType {
    /**
     * NotApplicable.
     */
    NOTAPPLICABLE(1),
    /**
     * Zero coupon.
     */
    ZERO(2),
    /**
     * Fixed coupon.
     */
    FIXED(3),
    /**
     * Floating coupon.
     */
    FLOATING(4),
    /**
     * Indexed coupon.
     */
    INDEXED(5);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, CouponType> TYPES = new HashMap<>();
    static {
        for (CouponType type : CouponType.values()) {
            TYPES.put(type.value, type);
        }
    }

    CouponType(int newValue) {
        value = newValue;
    }

    /**
     * Get CouponType by attribute
     * @param val
     * @return CouponType enum or null if variant is undefined
     */
    public static CouponType getCouponType(int val) {
        return TYPES.get(val);
    }

    /**
     * Get CouponType int value
     * @return int value
     */
    public int getCouponTypeValue() {
        return value;
    }
    
    /**
     * Get CouponType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static CouponType getCouponType(byte[] bytes, int offset) {
        return getCouponType(BendecUtils.uInt8FromByteArray(bytes, offset));
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