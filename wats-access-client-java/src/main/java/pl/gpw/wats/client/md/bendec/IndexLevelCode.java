package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: IndexLevelCode
 * Enumeration of index levels.
 */
public enum IndexLevelCode {
    /**
     * Closing index value from previous session (reference index).
     */
    PREVIOUSCLOSINGVALUE(1),
    /**
     * Theoretical opening index before opening the session.
     */
    THEORETICALVALUEBEFOREOPENING(2),
    /**
     * Theoretical value of the index before its opening.
     */
    VALUEBEFOREOPENING(3),
    /**
     * Index opening value.
     */
    OPENINGVALUE(4),
    /**
     * Current index value.
     */
    CURRENTVALUE(5),
    /**
     * Preliminary closing value.
     */
    PRECLOSINGVALUE(6),
    /**
     * Closing index value.
     */
    CLOSINGVALUE(7),
    /**
     * Index value without opening.
     */
    VALUEWITHOUTOPENING(8),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, IndexLevelCode> TYPES = new HashMap<>();
    static {
        for (IndexLevelCode type : IndexLevelCode.values()) {
            TYPES.put(type.value, type);
        }
    }


    IndexLevelCode(int newValue) {
        value = newValue;
    }

    /**
     Get IndexLevelCode from java input
     * @param newValue
     * @return IndexLevelCode enum
     */
    public static IndexLevelCode getIndexLevelCode(int newValue) {
        IndexLevelCode val = TYPES.get(newValue);
        return val == null ? IndexLevelCode.UNKNOWN : val;
    }

    /**
     * Get IndexLevelCode int value
     * @return int value
     */
    public int getIndexLevelCodeValue() { return value; }


    /**
     Get IndexLevelCode from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static IndexLevelCode getIndexLevelCode(byte[] bytes, int offset) {
        return getIndexLevelCode(BendecUtils.uInt8FromByteArray(bytes, offset));
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
