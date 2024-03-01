package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: OrderType
 * Indicates the type of a given order.
 */
public enum OrderType {
    /**
     * Limit order type.
     */
    LIMIT(1),
    /**
     * Market order type.
     */
    MARKET(2),
    /**
     * Market to limit order type.
     */
    MARKETTOLIMIT(3),
    /**
     * Iceberg order type.
     */
    ICEBERG(4),
    /**
     * Stop limit order type.
     */
    STOPLIMIT(5),
    /**
     * Stop loss order type.
     */
    STOPLOSS(6),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, OrderType> TYPES = new HashMap<>();
    static {
        for (OrderType type : OrderType.values()) {
            TYPES.put(type.value, type);
        }
    }


    OrderType(int newValue) {
        value = newValue;
    }

    /**
     Get OrderType from java input
     * @param newValue
     * @return OrderType enum
     */
    public static OrderType getOrderType(int newValue) {
        OrderType val = TYPES.get(newValue);
        return val == null ? OrderType.UNKNOWN : val;
    }

    /**
     * Get OrderType int value
     * @return int value
     */
    public int getOrderTypeValue() { return value; }


    /**
     Get OrderType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static OrderType getOrderType(byte[] bytes, int offset) {
        return getOrderType(BendecUtils.uInt8FromByteArray(bytes, offset));
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
