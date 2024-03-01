package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: TimeInForce
 * Indicates the order's time in force (e.g. GTC).
 */
public enum TimeInForce {
    /**
     * A day order is valid until the end of the trading day.
     */
    DAY(1),
    /**
     * A GTC order is good till canceled.
     */
    GTC(2),
    /**
     * An Immediate or Cancel order must be filled immediately or canceled.
     */
    IOC(3),
    /**
     * A Fill or Kill order must be filled or canceled.
     */
    FOK(4),
    /**
     * A market or limit-price order to be executed at the opening of the stock or not at all; all or part of any order not executed at the opening is treated as canceled.
     */
    VFA(5),
    /**
     * A Good Till Date order must be filled before timestamp provided in `Expire` field or canceled.
     */
    GTD(6),
    /**
     * Indicated price is to be around the closing price, however, not held to the closing price.
     */
    VFC(7),
    /**
     * A Good Till Time order must be filled before timestamp provided in `Expire` field or canceled within the day of submission.
     */
    GTT(8),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, TimeInForce> TYPES = new HashMap<>();
    static {
        for (TimeInForce type : TimeInForce.values()) {
            TYPES.put(type.value, type);
        }
    }


    TimeInForce(int newValue) {
        value = newValue;
    }

    /**
     Get TimeInForce from java input
     * @param newValue
     * @return TimeInForce enum
     */
    public static TimeInForce getTimeInForce(int newValue) {
        TimeInForce val = TYPES.get(newValue);
        return val == null ? TimeInForce.UNKNOWN : val;
    }

    /**
     * Get TimeInForce int value
     * @return int value
     */
    public int getTimeInForceValue() { return value; }


    /**
     Get TimeInForce from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static TimeInForce getTimeInForce(byte[] bytes, int offset) {
        return getTimeInForce(BendecUtils.uInt8FromByteArray(bytes, offset));
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
