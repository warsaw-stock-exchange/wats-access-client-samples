package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: ExcerciseType
 * Dictionary with option exercise styles
 */
public enum ExcerciseType {
    /**
     * AMER
     */
    AMER(1),
    /**
     * EURO
     */
    EURO(2),
    /**
     * Not applicable
     */
    NA(3),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, ExcerciseType> TYPES = new HashMap<>();
    static {
        for (ExcerciseType type : ExcerciseType.values()) {
            TYPES.put(type.value, type);
        }
    }


    ExcerciseType(int newValue) {
        value = newValue;
    }

    /**
     Get ExcerciseType from java input
     * @param newValue
     * @return ExcerciseType enum
     */
    public static ExcerciseType getExcerciseType(int newValue) {
        ExcerciseType val = TYPES.get(newValue);
        return val == null ? ExcerciseType.UNKNOWN : val;
    }

    /**
     * Get ExcerciseType int value
     * @return int value
     */
    public int getExcerciseTypeValue() { return value; }


    /**
     Get ExcerciseType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static ExcerciseType getExcerciseType(byte[] bytes, int offset) {
        return getExcerciseType(BendecUtils.uInt8FromByteArray(bytes, offset));
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
