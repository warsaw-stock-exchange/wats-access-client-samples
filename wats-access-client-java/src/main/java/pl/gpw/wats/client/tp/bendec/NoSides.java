package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: NoSides
 * Number of sides.
 */
public enum NoSides {
    /**
     * One Side.
     */
    ONESIDE(1),
    /**
     * Both Sides.
     */
    BOTHSIDES(2),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, NoSides> TYPES = new HashMap<>();
    static {
        for (NoSides type : NoSides.values()) {
            TYPES.put(type.value, type);
        }
    }


    NoSides(int newValue) {
        value = newValue;
    }

    /**
     Get NoSides from java input
     * @param newValue
     * @return NoSides enum
     */
    public static NoSides getNoSides(int newValue) {
        NoSides val = TYPES.get(newValue);
        return val == null ? NoSides.UNKNOWN : val;
    }

    /**
     * Get NoSides int value
     * @return int value
     */
    public int getNoSidesValue() { return value; }


    /**
     Get NoSides from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static NoSides getNoSides(byte[] bytes, int offset) {
        return getNoSides(BendecUtils.uInt8FromByteArray(bytes, offset));
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
