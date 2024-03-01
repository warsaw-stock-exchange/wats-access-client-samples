package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: MmtNegotitationIndicator
 * MMT Negotitation Indicator
 */
public enum MmtNegotitationIndicator {
    /**
     * N = Negotiated Trade
     */
    NEGOTIATEDTRADE(1),
    /**
     * 1 = Negotiated Trade in Liquid Financial Instruments
     */
    NEGOTIATEDTRADELIQUIDFINANCIALINSTRUMENTS(2),
    /**
     * 2 = Negotiated Trade in Illiquid Financial Instruments
     */
    NEGOTIATEDTRADEILLIQUIDFINANCIALINSTRUMENTS(3),
    /**
     * 3 = Negotiated Trade subject to conditions other than the current market price
     */
    NEGOTIATEDTRADEOTHER(4),
    /**
     * - = No Negotiated Trade
     */
    NONEGOTIATEDTRADE(5),
    /**
     * 4 = Pre-Trade Transparency Waiver for Illiquid Instrument on an SI (for RTS 1 only)
     */
    PRETRADETRANSPARENCYWAIVERILLIQUIDINSTRUMENT(6),
    /**
     * 5 = Pre-Trade Transparency Waiver for above Standard Market Size on an SI (for RTS 1 only)
     */
    PRETRADETRANSPARENCYWAIVERSTANDARDMARKETSIZE(7),
    /**
     * 6 = Pre-Trade Transparency Waivers of ILQD and SIZE (for RTS 1 only)
     */
    PRETRADETRANSPARENCYWAIVERSILQDANDSIZE(8),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, MmtNegotitationIndicator> TYPES = new HashMap<>();
    static {
        for (MmtNegotitationIndicator type : MmtNegotitationIndicator.values()) {
            TYPES.put(type.value, type);
        }
    }


    MmtNegotitationIndicator(int newValue) {
        value = newValue;
    }

    /**
     Get MmtNegotitationIndicator from java input
     * @param newValue
     * @return MmtNegotitationIndicator enum
     */
    public static MmtNegotitationIndicator getMmtNegotitationIndicator(int newValue) {
        MmtNegotitationIndicator val = TYPES.get(newValue);
        return val == null ? MmtNegotitationIndicator.UNKNOWN : val;
    }

    /**
     * Get MmtNegotitationIndicator int value
     * @return int value
     */
    public int getMmtNegotitationIndicatorValue() { return value; }


    /**
     Get MmtNegotitationIndicator from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MmtNegotitationIndicator getMmtNegotitationIndicator(byte[] bytes, int offset) {
        return getMmtNegotitationIndicator(BendecUtils.uInt8FromByteArray(bytes, offset));
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
