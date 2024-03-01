package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: MarketModelType
 * Market Model.
 */
public enum MarketModelType {
    /**
     * A Central Limit Order Book (CLOB)
     */
    CLOB(1),
    /**
     * BLOCK
     */
    BLOCK(2),
    /**
     * HYBRID market model
     */
    HYBRID(3),
    /**
     * CROSS
     */
    CROSS(4),
    /**
     * Not applicable
     */
    NOTAPPLICABLE(5),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, MarketModelType> TYPES = new HashMap<>();
    static {
        for (MarketModelType type : MarketModelType.values()) {
            TYPES.put(type.value, type);
        }
    }


    MarketModelType(int newValue) {
        value = newValue;
    }

    /**
     Get MarketModelType from java input
     * @param newValue
     * @return MarketModelType enum
     */
    public static MarketModelType getMarketModelType(int newValue) {
        MarketModelType val = TYPES.get(newValue);
        return val == null ? MarketModelType.UNKNOWN : val;
    }

    /**
     * Get MarketModelType int value
     * @return int value
     */
    public int getMarketModelTypeValue() { return value; }


    /**
     Get MarketModelType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MarketModelType getMarketModelType(byte[] bytes, int offset) {
        return getMarketModelType(BendecUtils.uInt8FromByteArray(bytes, offset));
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
