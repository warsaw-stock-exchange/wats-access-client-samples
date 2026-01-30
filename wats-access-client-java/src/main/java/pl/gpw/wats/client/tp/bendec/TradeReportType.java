package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: TradeReportType
 * Type of Trade Report.
 */
public enum TradeReportType {
    /**
     * Submit
     */
    SUBMIT(1),
    /**
     * Alleged
     */
    ALLEGED(2),
    /**
     * Accept
     */
    ACCEPT(3),
    /**
     * Decline
     */
    DECLINE(4),
    /**
     * Trade Report Cancel
     */
    TRADEREPORTCANCEL(7),
    /**
     * Trade Break
     */
    TRADEBREAK(8);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, TradeReportType> TYPES = new HashMap<>();
    static {
        for (TradeReportType type : TradeReportType.values()) {
            TYPES.put(type.value, type);
        }
    }

    TradeReportType(int newValue) {
        value = newValue;
    }

    /**
     * Get TradeReportType by attribute
     * @param val
     * @return TradeReportType enum or null if variant is undefined
     */
    public static TradeReportType getTradeReportType(int val) {
        return TYPES.get(val);
    }

    /**
     * Get TradeReportType int value
     * @return int value
     */
    public int getTradeReportTypeValue() {
        return value;
    }
    
    /**
     * Get TradeReportType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static TradeReportType getTradeReportType(byte[] bytes, int offset) {
        return getTradeReportType(BendecUtils.uInt8FromByteArray(bytes, offset));
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