package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: CollarMode
 * Collar mode (trade price collar or order price collar).
 */
public enum CollarMode {
    /**
     * Trade price collar.
     */
    TRADEPRICECOLLAR(1),
    /**
     * Order price collar.
     */
    ORDERPRICECOLLAR(2),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, CollarMode> TYPES = new HashMap<>();
    static {
        for (CollarMode type : CollarMode.values()) {
            TYPES.put(type.value, type);
        }
    }


    CollarMode(int newValue) {
        value = newValue;
    }

    /**
     Get CollarMode from java input
     * @param newValue
     * @return CollarMode enum
     */
    public static CollarMode getCollarMode(int newValue) {
        CollarMode val = TYPES.get(newValue);
        return val == null ? CollarMode.UNKNOWN : val;
    }

    /**
     * Get CollarMode int value
     * @return int value
     */
    public int getCollarModeValue() { return value; }


    /**
     Get CollarMode from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static CollarMode getCollarMode(byte[] bytes, int offset) {
        return getCollarMode(BendecUtils.uInt8FromByteArray(bytes, offset));
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
