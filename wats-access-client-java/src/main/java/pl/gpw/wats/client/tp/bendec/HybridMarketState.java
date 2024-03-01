package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: HybridMarketState
 * Enumeration of possible hybrid market states that mm can initiate the transition to.
 */
public enum HybridMarketState {
    /**
     * Regular trading.
     */
    REGULAR(1),
    /**
     * Buy only trading.
     */
    BUYONLY(2),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, HybridMarketState> TYPES = new HashMap<>();
    static {
        for (HybridMarketState type : HybridMarketState.values()) {
            TYPES.put(type.value, type);
        }
    }


    HybridMarketState(int newValue) {
        value = newValue;
    }

    /**
     Get HybridMarketState from java input
     * @param newValue
     * @return HybridMarketState enum
     */
    public static HybridMarketState getHybridMarketState(int newValue) {
        HybridMarketState val = TYPES.get(newValue);
        return val == null ? HybridMarketState.UNKNOWN : val;
    }

    /**
     * Get HybridMarketState int value
     * @return int value
     */
    public int getHybridMarketStateValue() { return value; }


    /**
     Get HybridMarketState from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static HybridMarketState getHybridMarketState(byte[] bytes, int offset) {
        return getHybridMarketState(BendecUtils.uInt8FromByteArray(bytes, offset));
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
