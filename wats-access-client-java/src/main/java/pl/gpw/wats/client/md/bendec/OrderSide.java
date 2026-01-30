package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: OrderSide
 * Indicates order side (buy or sell).
 */
public enum OrderSide {
    /**
     * Buy order.
     */
    BUY(1),
    /**
     * Sell order.
     */
    SELL(2);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, OrderSide> TYPES = new HashMap<>();
    static {
        for (OrderSide type : OrderSide.values()) {
            TYPES.put(type.value, type);
        }
    }

    OrderSide(int newValue) {
        value = newValue;
    }

    /**
     * Get OrderSide by attribute
     * @param val
     * @return OrderSide enum or null if variant is undefined
     */
    public static OrderSide getOrderSide(int val) {
        return TYPES.get(val);
    }

    /**
     * Get OrderSide int value
     * @return int value
     */
    public int getOrderSideValue() {
        return value;
    }
    
    /**
     * Get OrderSide from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static OrderSide getOrderSide(byte[] bytes, int offset) {
        return getOrderSide(BendecUtils.uInt8FromByteArray(bytes, offset));
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