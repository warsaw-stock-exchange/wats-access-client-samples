package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
     * I = Non-Price Forming Trade (formerly defined as a Technical Trade)
     */
    NONPRICEFORMINGTRADE(2),
    /**
     * J = Trade not contributing to the price discovery process (formerly defined as a Technical Trade)
     */
    TRADENOTCONTRIBUTINGTOPRICEDISCOVERY(3),
    /**
     * N = Price is currently not available but pending
     */
    PRICEPENDING(4),
    UNKNOWN(99999);

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
     Get MmtOrdinaryTradeIndicator from java input
     * @param newValue
     * @return MmtOrdinaryTradeIndicator enum
     */
    public static MmtOrdinaryTradeIndicator getMmtOrdinaryTradeIndicator(int newValue) {
        MmtOrdinaryTradeIndicator val = TYPES.get(newValue);
        return val == null ? MmtOrdinaryTradeIndicator.UNKNOWN : val;
    }

    /**
     * Get MmtOrdinaryTradeIndicator int value
     * @return int value
     */
    public int getMmtOrdinaryTradeIndicatorValue() { return value; }


    /**
     Get MmtOrdinaryTradeIndicator from bytes
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
