package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: MmtTradingMode
 * MMT Trading Mode
 */
public enum MmtTradingMode {
    /**
     * 1 = Undefined Auction
     */
    UNDEFINEDAUCTION(1),
    /**
     * O = Scheduled Opening Auction
     */
    SCHEDULEDOPENINGAUCTION(2),
    /**
     * K = Scheduled Closing Auction
     */
    SCHEDULEDCLOSINGAUCTION(3),
    /**
     * I = Scheduled Intraday Auction
     */
    SCHEDULEDINTRADAYAUCTION(4),
    /**
     * U = Unscheduled Auction
     */
    UNSCHEDULEDAUCTION(5),
    /**
     * 2 = Continuous Trading
     */
    CONTINUOUSTRADING(6),
    /**
     * 3 = At Market Close Trading
     */
    ATMARKETCLOSETRADING(7),
    /**
     * 4 = Out Of Main Session Trading
     */
    OUTOFMAINSESSIONTRADING(8),
    /**
     * 5 = Trade Reporting (On Exchange)
     */
    TRADEREPORTINGONEXCHANGE(9),
    /**
     * 6 = Trade Reporting (Off Exchange)
     */
    TRADEREPORTINGOFFEXCHANGE(10),
    /**
     * 7 = Trade Reporting (Systematic Internaliser)
     */
    TRADEREPORTINGSYSTEMATICINTERNALISER(11);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, MmtTradingMode> TYPES = new HashMap<>();
    static {
        for (MmtTradingMode type : MmtTradingMode.values()) {
            TYPES.put(type.value, type);
        }
    }

    MmtTradingMode(int newValue) {
        value = newValue;
    }

    /**
     * Get MmtTradingMode by attribute
     * @param val
     * @return MmtTradingMode enum or null if variant is undefined
     */
    public static MmtTradingMode getMmtTradingMode(int val) {
        return TYPES.get(val);
    }

    /**
     * Get MmtTradingMode int value
     * @return int value
     */
    public int getMmtTradingModeValue() {
        return value;
    }
    
    /**
     * Get MmtTradingMode from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MmtTradingMode getMmtTradingMode(byte[] bytes, int offset) {
        return getMmtTradingMode(BendecUtils.uInt8FromByteArray(bytes, offset));
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