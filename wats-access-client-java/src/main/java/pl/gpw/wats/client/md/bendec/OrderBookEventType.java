package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: OrderBookEventType
 * Book event for given instrument
 */
public enum OrderBookEventType {
    /**
     * Clear order book
     */
    CLEAR(1),
    /**
     * Order book resend start (resend will begin shortly)
     */
    RESENDSTART(2),
    /**
     * Order book resend end (resend is done)
     */
    RESENDEND(3);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, OrderBookEventType> TYPES = new HashMap<>();
    static {
        for (OrderBookEventType type : OrderBookEventType.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    OrderBookEventType(int newValue) {
        value = newValue;
    }
    
    /**
     * Get OrderBookEventType by attribute
     * @param val
     * @return OrderBookEventType enum or null if variant is undefined
     */
    public static OrderBookEventType getOrderBookEventType(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get OrderBookEventType int value
     * @return int value
     */
    public int getOrderBookEventTypeValue() {
        return value; 
    }
    
    /**
     * Get OrderBookEventType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static OrderBookEventType getOrderBookEventType(byte[] bytes, int offset) {
        return getOrderBookEventType(BendecUtils.uInt8FromByteArray(bytes, offset));
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