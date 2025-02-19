package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: TradingSessionEvent
 * Identifies an event related to the trading status of a trading session.
 */
public enum TradingSessionEvent {
    /**
     * Not applicable.
     */
    NA(1),
    /**
     * Start of technical session.
     */
    STARTOFTECHNICALSESSION(2),
    /**
     * End of technical session.
     */
    ENDOFTECHNICALSESSION(3),
    /**
     * Initial reference data start.
     */
    INITIALREFERENCEDATASTART(4),
    /**
     * Initial reference data end.
     */
    INITIALREFERENCEDATAEND(5),
    /**
     * Previous day restate start.
     */
    PREVIOUSDAYRESTATESTART(6),
    /**
     * Previous day restate end.
     */
    PREVIOUSDAYRESTATEEND(7),
    /**
     * Next session reference data start.
     */
    NEXTSESSIONREFERENCEDATASTART(8),
    /**
     * Next session reference data end.
     */
    NEXTSESSIONREFERENCEDATAEND(9);
    
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