package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: CollarType
 * Collar type can be dynamic or static.
 */
public enum CollarType {
    STATIC(1),
    DYNAMIC(2),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, CollarType> TYPES = new HashMap<>();
    static {
        for (CollarType type : CollarType.values()) {
            TYPES.put(type.value, type);
        }
    }


    CollarType(int newValue) {
        value = newValue;
    }

    /**
     Get CollarType from java input
     * @param newValue
     * @return CollarType enum
     */
    public static CollarType getCollarType(int newValue) {
        CollarType val = TYPES.get(newValue);
        return val == null ? CollarType.UNKNOWN : val;
    }

    /**
     * Get CollarType int value
     * @return int value
     */
    public int getCollarTypeValue() { return value; }


    /**
     Get CollarType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static CollarType getCollarType(byte[] bytes, int offset) {
        return getCollarType(BendecUtils.uInt8FromByteArray(bytes, offset));
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
