package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: SecurityIdSource
 * Identifies class or source of the SecurityID value.
 */
public enum SecurityIdSource {
    /**
     * CUSIP.
     */
    CUSIP(1),
    /**
     * SEDOL.
     */
    SEDOL(2),
    /**
     * ISIN.
     */
    ISIN(4),
    /**
     * Exchange Symbol.
     */
    EXCHANGESYMBOL(8),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, SecurityIdSource> TYPES = new HashMap<>();
    static {
        for (SecurityIdSource type : SecurityIdSource.values()) {
            TYPES.put(type.value, type);
        }
    }


    SecurityIdSource(int newValue) {
        value = newValue;
    }

    /**
     Get SecurityIdSource from java input
     * @param newValue
     * @return SecurityIdSource enum
     */
    public static SecurityIdSource getSecurityIdSource(int newValue) {
        SecurityIdSource val = TYPES.get(newValue);
        return val == null ? SecurityIdSource.UNKNOWN : val;
    }

    /**
     * Get SecurityIdSource int value
     * @return int value
     */
    public int getSecurityIdSourceValue() { return value; }


    /**
     Get SecurityIdSource from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static SecurityIdSource getSecurityIdSource(byte[] bytes, int offset) {
        return getSecurityIdSource(BendecUtils.uInt8FromByteArray(bytes, offset));
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
