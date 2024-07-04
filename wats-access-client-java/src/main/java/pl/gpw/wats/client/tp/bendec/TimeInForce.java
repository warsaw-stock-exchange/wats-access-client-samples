package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
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
     * A Fill or Kill order must be immediately fully filled or canceled.
     */
    FOK(4),
    /**
     * Valid For Auction.
     */
    VFA(5),
    /**
     * A Good Till Date order must be filled before timestamp provided in `Expire` field or canceled.
     */
    GTD(6),
    /**
     * Valid For Closing.
     */
    VFC(7),
    /**
     * A Good Till Time order must be filled before timestamp provided in `Expire` field or canceled within the day of submission.
     */
    GTT(8);
    
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
     * Get TimeInForce by attribute
     * @param val
     * @return TimeInForce enum or null if variant is undefined
     */
    public static TimeInForce getTimeInForce(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get TimeInForce int value
     * @return int value
     */
    public int getTimeInForceValue() {
        return value; 
    }
    
    /**
     * Get TimeInForce from bytes
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