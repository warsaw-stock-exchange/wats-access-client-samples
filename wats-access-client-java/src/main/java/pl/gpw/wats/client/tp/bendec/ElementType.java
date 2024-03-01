package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: ElementType
 * Market structure element type.
 */
public enum ElementType {
    MARKETSTRUCTURE(1),
    INSTRUMENT(2),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, ElementType> TYPES = new HashMap<>();
    static {
        for (ElementType type : ElementType.values()) {
            TYPES.put(type.value, type);
        }
    }


    ElementType(int newValue) {
        value = newValue;
    }

    /**
     Get ElementType from java input
     * @param newValue
     * @return ElementType enum
     */
    public static ElementType getElementType(int newValue) {
        ElementType val = TYPES.get(newValue);
        return val == null ? ElementType.UNKNOWN : val;
    }

    /**
     * Get ElementType int value
     * @return int value
     */
    public int getElementTypeValue() { return value; }


    /**
     Get ElementType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static ElementType getElementType(byte[] bytes, int offset) {
        return getElementType(BendecUtils.uInt8FromByteArray(bytes, offset));
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
