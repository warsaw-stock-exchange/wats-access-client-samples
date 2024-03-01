package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: MmtAlgorithmicIndicator
 * MMT Algorithmic Indicator
 */
public enum MmtAlgorithmicIndicator {
    /**
     * H = Algorithmic Trade
     */
    ALGORITHMICTRADE(1),
    /**
     * - = No Algorithmic Trade
     */
    NOALGORITHMICTRADE(2),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, MmtAlgorithmicIndicator> TYPES = new HashMap<>();
    static {
        for (MmtAlgorithmicIndicator type : MmtAlgorithmicIndicator.values()) {
            TYPES.put(type.value, type);
        }
    }


    MmtAlgorithmicIndicator(int newValue) {
        value = newValue;
    }

    /**
     Get MmtAlgorithmicIndicator from java input
     * @param newValue
     * @return MmtAlgorithmicIndicator enum
     */
    public static MmtAlgorithmicIndicator getMmtAlgorithmicIndicator(int newValue) {
        MmtAlgorithmicIndicator val = TYPES.get(newValue);
        return val == null ? MmtAlgorithmicIndicator.UNKNOWN : val;
    }

    /**
     * Get MmtAlgorithmicIndicator int value
     * @return int value
     */
    public int getMmtAlgorithmicIndicatorValue() { return value; }


    /**
     Get MmtAlgorithmicIndicator from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MmtAlgorithmicIndicator getMmtAlgorithmicIndicator(byte[] bytes, int offset) {
        return getMmtAlgorithmicIndicator(BendecUtils.uInt8FromByteArray(bytes, offset));
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
