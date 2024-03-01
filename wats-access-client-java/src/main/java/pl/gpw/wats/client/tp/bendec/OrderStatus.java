package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: OrderStatus
 * Indicates the status of the order.
 */
public enum OrderStatus {
    /**
     * Order acknowledged by system.
     */
    ACK(1),
    /**
     * Order canceled.
     */
    CANCELLED(2),
    /**
     * Order rejected.
     */
    REJECTED(3),
    /**
     * Order filled.
     */
    FILLED(4),
    /**
     * Order modified.
     */
    MODIFIED(5),
    UNKNOWN(99999);

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
     Get OrderStatus from java input
     * @param newValue
     * @return OrderStatus enum
     */
    public static OrderStatus getOrderStatus(int newValue) {
        OrderStatus val = TYPES.get(newValue);
        return val == null ? OrderStatus.UNKNOWN : val;
    }

    /**
     * Get OrderStatus int value
     * @return int value
     */
    public int getOrderStatusValue() { return value; }


    /**
     Get OrderStatus from bytes
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
