package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: ExerciseType
 * Dictionary with option exercise styles
 */
public enum ExerciseType {
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
    NA(3);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, ExerciseType> TYPES = new HashMap<>();
    static {
        for (ExerciseType type : ExerciseType.values()) {
            TYPES.put(type.value, type);
        }
    }

    ExerciseType(int newValue) {
        value = newValue;
    }

    /**
     * Get ExerciseType by attribute
     * @param val
     * @return ExerciseType enum or null if variant is undefined
     */
    public static ExerciseType getExerciseType(int val) {
        return TYPES.get(val);
    }

    /**
     * Get ExerciseType int value
     * @return int value
     */
    public int getExerciseTypeValue() {
        return value;
    }
    
    /**
     * Get ExerciseType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static ExerciseType getExerciseType(byte[] bytes, int offset) {
        return getExerciseType(BendecUtils.uInt8FromByteArray(bytes, offset));
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