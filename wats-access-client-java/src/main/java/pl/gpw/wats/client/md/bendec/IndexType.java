package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: IndexType
 * Type of index.
 */
public enum IndexType {
    /**
     * WSE index from the main or alternative markets.
     */
    W(1),
    /**
     * External index.
     */
    Z(2),
    /**
     * Another WSE information product.
     */
    V(3),
    /**
     * WSE strategy dividend point index.
     */
    M(4),
    /**
     * WSE strategy index of short type.
     */
    S(5),
    /**
     * WSE strategy index of leverage type.
     */
    L(6),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, IndexType> TYPES = new HashMap<>();
    static {
        for (IndexType type : IndexType.values()) {
            TYPES.put(type.value, type);
        }
    }


    IndexType(int newValue) {
        value = newValue;
    }

    /**
     Get IndexType from java input
     * @param newValue
     * @return IndexType enum
     */
    public static IndexType getIndexType(int newValue) {
        IndexType val = TYPES.get(newValue);
        return val == null ? IndexType.UNKNOWN : val;
    }

    /**
     * Get IndexType int value
     * @return int value
     */
    public int getIndexTypeValue() { return value; }


    /**
     Get IndexType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static IndexType getIndexType(byte[] bytes, int offset) {
        return getIndexType(BendecUtils.uInt8FromByteArray(bytes, offset));
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
