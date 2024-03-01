package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
    INDEXED(5),
    UNKNOWN(99999);

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
     Get CouponType from java input
     * @param newValue
     * @return CouponType enum
     */
    public static CouponType getCouponType(int newValue) {
        CouponType val = TYPES.get(newValue);
        return val == null ? CouponType.UNKNOWN : val;
    }

    /**
     * Get CouponType int value
     * @return int value
     */
    public int getCouponTypeValue() { return value; }


    /**
     Get CouponType from bytes
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
