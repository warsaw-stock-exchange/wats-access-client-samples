package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: ClearingIdentifier
 * Clearing member identifier type.
 */
public enum ClearingIdentifier {
    /**
     * Not Applicable.
     */
    NOTAPPLICABLE(33),
    /**
     * Legal Entity Identifier.
     */
    LEI(78),
    /**
     * Business Identifier Code.
     */
    BIC(66),
    /**
     * Custom clearing identifier.
     */
    CUSTOM(68),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, ClearingIdentifier> TYPES = new HashMap<>();
    static {
        for (ClearingIdentifier type : ClearingIdentifier.values()) {
            TYPES.put(type.value, type);
        }
    }


    ClearingIdentifier(int newValue) {
        value = newValue;
    }

    /**
     Get ClearingIdentifier from java input
     * @param newValue
     * @return ClearingIdentifier enum
     */
    public static ClearingIdentifier getClearingIdentifier(int newValue) {
        ClearingIdentifier val = TYPES.get(newValue);
        return val == null ? ClearingIdentifier.UNKNOWN : val;
    }

    /**
     * Get ClearingIdentifier int value
     * @return int value
     */
    public int getClearingIdentifierValue() { return value; }


    /**
     Get ClearingIdentifier from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static ClearingIdentifier getClearingIdentifier(byte[] bytes, int offset) {
        return getClearingIdentifier(BendecUtils.uInt8FromByteArray(bytes, offset));
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
