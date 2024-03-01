package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: OrderSource
 * The message source of the order response.
 */
public enum OrderSource {
    /**
     * Order message coming from the trading port.
     */
    SUBMITTED(1),
    /**
     * Order cancelled by the canceller on client disconnect.
     */
    COD(2),
    /**
     * Order cancelled by the canceller due to expiration.
     */
    EXPIRED(3),
    /**
     * Order message coming from the stop order subsystem, after the stop order got triggered.
     */
    STOPORDER(4),
    /**
     * Order cancelled by canceller due to instrument suspension.
     */
    SUSPENDED(5),
    /**
     * Order reinstated
     */
    REINSTATED(6),
    /**
     * Iceberg order refill
     */
    ICEBERGREFILL(7),
    /**
     * Order book rebuild after auction
     */
    ORDERBOOKREBUILD(8),
    /**
     * VFA/C order got activated, from now on it will take part in matching
     */
    ACTIVATED(9),
    /**
     * Order cancelled due to self-trade prevention
     */
    STP(10),
    /**
     * Order cancelled due to submitted Corporate Action.
     */
    CORPORATEACTION(11),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, OrderSource> TYPES = new HashMap<>();
    static {
        for (OrderSource type : OrderSource.values()) {
            TYPES.put(type.value, type);
        }
    }


    OrderSource(int newValue) {
        value = newValue;
    }

    /**
     Get OrderSource from java input
     * @param newValue
     * @return OrderSource enum
     */
    public static OrderSource getOrderSource(int newValue) {
        OrderSource val = TYPES.get(newValue);
        return val == null ? OrderSource.UNKNOWN : val;
    }

    /**
     * Get OrderSource int value
     * @return int value
     */
    public int getOrderSourceValue() { return value; }


    /**
     Get OrderSource from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static OrderSource getOrderSource(byte[] bytes, int offset) {
        return getOrderSource(BendecUtils.uInt8FromByteArray(bytes, offset));
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
