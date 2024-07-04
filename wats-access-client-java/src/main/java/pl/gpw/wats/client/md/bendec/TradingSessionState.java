package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: TradingSessionState
 * State of the trading session.
 */
public enum TradingSessionState {
    /**
     * Unknown (off).
     */
    UNKNOWN(1),
    /**
     * Open.
     */
    OPEN(2),
    /**
     * Close.
     */
    CLOSE(3);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, TradingSessionState> TYPES = new HashMap<>();
    static {
        for (TradingSessionState type : TradingSessionState.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    TradingSessionState(int newValue) {
        value = newValue;
    }
    
    /**
     * Get TradingSessionState by attribute
     * @param val
     * @return TradingSessionState enum or null if variant is undefined
     */
    public static TradingSessionState getTradingSessionState(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get TradingSessionState int value
     * @return int value
     */
    public int getTradingSessionStateValue() {
        return value; 
    }
    
    /**
     * Get TradingSessionState from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static TradingSessionState getTradingSessionState(byte[] bytes, int offset) {
        return getTradingSessionState(BendecUtils.uInt8FromByteArray(bytes, offset));
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