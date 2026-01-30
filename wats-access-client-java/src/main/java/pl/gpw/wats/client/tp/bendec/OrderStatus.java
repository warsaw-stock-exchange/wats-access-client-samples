package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: OrderStatus
 * Indicates the status of the order.
 */
public enum OrderStatus {
    /**
     * New order( for Mass Quotes ->operation accepted).
     */
    NEW(1),
    /**
     * Order cancelled (Not applicable to mass quotes).
     */
    CANCELLED(2),
    /**
     * Order rejected.
     */
    REJECTED(3),
    /**
     * Order filled (Not applicable to mass quotes).
     */
    FILLED(4),
    /**
     * Order partially filled (Not applicable to mass quotes).
     */
    PARTIALLYFILLED(5),
    /**
     * Order expired (Not applicable to mass quotes).
     */
    EXPIRED(6);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, OrderStatus> TYPES = new HashMap<>();
    static {
        for (OrderStatus type : OrderStatus.values()) {
            TYPES.put(type.value, type);
        }
    }

    OrderStatus(int newValue) {
        value = newValue;
    }

    /**
     * Get OrderStatus by attribute
     * @param val
     * @return OrderStatus enum or null if variant is undefined
     */
    public static OrderStatus getOrderStatus(int val) {
        return TYPES.get(val);
    }

    /**
     * Get OrderStatus int value
     * @return int value
     */
    public int getOrderStatusValue() {
        return value;
    }
    
    /**
     * Get OrderStatus from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static OrderStatus getOrderStatus(byte[] bytes, int offset) {
        return getOrderStatus(BendecUtils.uInt8FromByteArray(bytes, offset));
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