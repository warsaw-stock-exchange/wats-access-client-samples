package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: TradeType
 * Type of trade.
 */
public enum TradeType {
    /**
     * Privately negotiated trade.
     */
    PRIVATELYNEGOTIATEDTRADE(22),
    /**
     * Block trade.
     */
    BLOCKTRADE(38);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, TradeType> TYPES = new HashMap<>();
    static {
        for (TradeType type : TradeType.values()) {
            TYPES.put(type.value, type);
        }
    }

    TradeType(int newValue) {
        value = newValue;
    }

    /**
     * Get TradeType by attribute
     * @param val
     * @return TradeType enum or null if variant is undefined
     */
    public static TradeType getTradeType(int val) {
        return TYPES.get(val);
    }

    /**
     * Get TradeType int value
     * @return int value
     */
    public int getTradeTypeValue() {
        return value;
    }
    
    /**
     * Get TradeType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static TradeType getTradeType(byte[] bytes, int offset) {
        return getTradeType(BendecUtils.uInt8FromByteArray(bytes, offset));
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