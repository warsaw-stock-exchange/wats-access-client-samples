package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: TradingSessionEvent
 * Identifies an event related to the status of a trading session.
 */
public enum TradingSessionEvent {
    /**
     * Previous day restate start.
     */
    PREVIOUSDAYRESTATESTART(1),
    /**
     * Previous day restate end.
     */
    PREVIOUSDAYRESTATEEND(2),
    /**
     * Start of trading session MIC.
     */
    STARTOFTRADINGSESSIONMIC(3),
    /**
     * End of trading session MIC.
     */
    ENDOFTRADINGSESSIONMIC(4);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, TradingSessionEvent> TYPES = new HashMap<>();
    static {
        for (TradingSessionEvent type : TradingSessionEvent.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    TradingSessionEvent(int newValue) {
        value = newValue;
    }
    
    /**
     * Get TradingSessionEvent by attribute
     * @param val
     * @return TradingSessionEvent enum or null if variant is undefined
     */
    public static TradingSessionEvent getTradingSessionEvent(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get TradingSessionEvent int value
     * @return int value
     */
    public int getTradingSessionEventValue() {
        return value; 
    }
    
    /**
     * Get TradingSessionEvent from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static TradingSessionEvent getTradingSessionEvent(byte[] bytes, int offset) {
        return getTradingSessionEvent(BendecUtils.uInt8FromByteArray(bytes, offset));
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