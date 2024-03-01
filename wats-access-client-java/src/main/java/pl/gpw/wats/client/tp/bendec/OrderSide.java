package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: OrderSide
 * Trading Port - indicates order side (buy or sell).
 */
public enum OrderSide {
    /**
     * Indicates a buy-side order.
     */
    BUY(1),
    /**
     * Indicates a sell-side order.
     */
    SELL(2),
    UNKNOWN(99999);

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
     Get OrderSide from java input
     * @param newValue
     * @return OrderSide enum
     */
    public static OrderSide getOrderSide(int newValue) {
        OrderSide val = TYPES.get(newValue);
        return val == null ? OrderSide.UNKNOWN : val;
    }

    /**
     * Get OrderSide int value
     * @return int value
     */
    public int getOrderSideValue() { return value; }


    /**
     Get OrderSide from bytes
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
