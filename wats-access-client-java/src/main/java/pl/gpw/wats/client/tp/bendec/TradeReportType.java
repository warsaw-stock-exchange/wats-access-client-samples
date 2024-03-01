package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
    UNKNOWN(99999);

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
     Get TradeReportType from java input
     * @param newValue
     * @return TradeReportType enum
     */
    public static TradeReportType getTradeReportType(int newValue) {
        TradeReportType val = TYPES.get(newValue);
        return val == null ? TradeReportType.UNKNOWN : val;
    }

    /**
     * Get TradeReportType int value
     * @return int value
     */
    public int getTradeReportTypeValue() { return value; }


    /**
     Get TradeReportType from bytes
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
