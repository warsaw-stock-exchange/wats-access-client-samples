package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: MmtNegotiationIndicator
 * MMT Negotiation Indicator
 */
public enum MmtNegotiationIndicator {
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
    PRETRADETRANSPARENCYWAIVERSILQDANDSIZE(8);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, MmtNegotiationIndicator> TYPES = new HashMap<>();
    static {
        for (MmtNegotiationIndicator type : MmtNegotiationIndicator.values()) {
            TYPES.put(type.value, type);
        }
    }

    MmtNegotiationIndicator(int newValue) {
        value = newValue;
    }

    /**
     * Get MmtNegotiationIndicator by attribute
     * @param val
     * @return MmtNegotiationIndicator enum or null if variant is undefined
     */
    public static MmtNegotiationIndicator getMmtNegotiationIndicator(int val) {
        return TYPES.get(val);
    }

    /**
     * Get MmtNegotiationIndicator int value
     * @return int value
     */
    public int getMmtNegotiationIndicatorValue() {
        return value;
    }
    
    /**
     * Get MmtNegotiationIndicator from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MmtNegotiationIndicator getMmtNegotiationIndicator(byte[] bytes, int offset) {
        return getMmtNegotiationIndicator(BendecUtils.uInt8FromByteArray(bytes, offset));
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