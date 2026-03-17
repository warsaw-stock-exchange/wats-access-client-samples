package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: MmtOrdinaryTradeIndicator
 * MMT Ordinary Trade Indicator
 */
public enum MmtOrdinaryTradeIndicator {
    /**
     * P = Plain-Vanilla Trade
     */
    PLAINVANILLATRADE(1),
    /**
     * T = Non-Price Forming Trade (formerly defined as a Technical Trade)
     */
    NONPRICEFORMINGTRADE(2),
    /**
     * J = Trade not contributing to the price discovery process (formerly defined as a Technical Trade)
     */
    TRADENOTCONTRIBUTINGTOPRICEDISCOVERY(3),
    /**
     * N = Price is currently not available but pending
     */
    PRICEPENDING(4);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, MmtOrdinaryTradeIndicator> TYPES = new HashMap<>();
    static {
        for (MmtOrdinaryTradeIndicator type : MmtOrdinaryTradeIndicator.values()) {
            TYPES.put(type.value, type);
        }
    }

    MmtOrdinaryTradeIndicator(int newValue) {
        value = newValue;
    }

    /**
     * Get MmtOrdinaryTradeIndicator by attribute
     * @param val
     * @return MmtOrdinaryTradeIndicator enum or null if variant is undefined
     */
    public static MmtOrdinaryTradeIndicator getMmtOrdinaryTradeIndicator(int val) {
        return TYPES.get(val);
    }

    /**
     * Get MmtOrdinaryTradeIndicator int value
     * @return int value
     */
    public int getMmtOrdinaryTradeIndicatorValue() {
        return value;
    }
    
    /**
     * Get MmtOrdinaryTradeIndicator from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MmtOrdinaryTradeIndicator getMmtOrdinaryTradeIndicator(byte[] bytes, int offset) {
        return getMmtOrdinaryTradeIndicator(BendecUtils.uInt8FromByteArray(bytes, offset));
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