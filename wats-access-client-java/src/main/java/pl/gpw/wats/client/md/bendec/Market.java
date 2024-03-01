package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: Market
 * Defines the market the instrument belongs to.
 */
public enum Market {
    /**
     * Primary market.
     */
    PRIMARY(1),
    /**
     * Parallel market.
     */
    PARALLEL(2),
    /**
     * New Connect Market (price driven market).
     */
    NEWCONNECTPRICEDRIVEN(3),
    /**
     * New Connect Market (order driven market).
     */
    NEWCONNECTORDERDRIVEN(4),
    /**
     * Catalyst Regulated Market.
     */
    CATALYSTREGULATED(5),
    /**
     *  Catalyst ASO.
     */
    CATALYSTASO(6),
    /**
     * Other market.
     */
    OTHER(7),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, Market> TYPES = new HashMap<>();
    static {
        for (Market type : Market.values()) {
            TYPES.put(type.value, type);
        }
    }


    Market(int newValue) {
        value = newValue;
    }

    /**
     Get Market from java input
     * @param newValue
     * @return Market enum
     */
    public static Market getMarket(int newValue) {
        Market val = TYPES.get(newValue);
        return val == null ? Market.UNKNOWN : val;
    }

    /**
     * Get Market int value
     * @return int value
     */
    public int getMarketValue() { return value; }


    /**
     Get Market from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static Market getMarket(byte[] bytes, int offset) {
        return getMarket(BendecUtils.uInt8FromByteArray(bytes, offset));
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
