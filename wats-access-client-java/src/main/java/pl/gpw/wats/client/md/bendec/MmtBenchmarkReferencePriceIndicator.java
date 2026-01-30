package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: MmtBenchmarkReferencePriceIndicator
 * MMT Benchmark / Reference Price Indicator
 */
public enum MmtBenchmarkReferencePriceIndicator {
    /**
     * B = Benchmark Trade
     */
    BENCHMARKTRADE(1),
    /**
     * S = Reference Price Trade
     */
    REFERENCEPRICETRADE(2),
    /**
     * - = No Benchmark or Reference Price Trade
     */
    NOBENCHMARKORREFERENCEPRICETRADE(3);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, MmtBenchmarkReferencePriceIndicator> TYPES = new HashMap<>();
    static {
        for (MmtBenchmarkReferencePriceIndicator type : MmtBenchmarkReferencePriceIndicator.values()) {
            TYPES.put(type.value, type);
        }
    }

    MmtBenchmarkReferencePriceIndicator(int newValue) {
        value = newValue;
    }

    /**
     * Get MmtBenchmarkReferencePriceIndicator by attribute
     * @param val
     * @return MmtBenchmarkReferencePriceIndicator enum or null if variant is undefined
     */
    public static MmtBenchmarkReferencePriceIndicator getMmtBenchmarkReferencePriceIndicator(int val) {
        return TYPES.get(val);
    }

    /**
     * Get MmtBenchmarkReferencePriceIndicator int value
     * @return int value
     */
    public int getMmtBenchmarkReferencePriceIndicatorValue() {
        return value;
    }
    
    /**
     * Get MmtBenchmarkReferencePriceIndicator from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MmtBenchmarkReferencePriceIndicator getMmtBenchmarkReferencePriceIndicator(byte[] bytes, int offset) {
        return getMmtBenchmarkReferencePriceIndicator(BendecUtils.uInt8FromByteArray(bytes, offset));
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