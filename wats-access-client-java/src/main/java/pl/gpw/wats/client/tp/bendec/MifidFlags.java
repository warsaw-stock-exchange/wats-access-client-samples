package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: MifidFlags
 * Mifid related flags.
 */
public enum MifidFlags {
    NONE(0),
    LIQUIDITYPROVISIONACTIVITY(1),
    DIRECTORSPONSOREDACCESS(2),
    ALGORITHMICTRADE(4),
    MARKETMAKERORSPECIALIST(8),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, MifidFlags> TYPES = new HashMap<>();
    static {
        for (MifidFlags type : MifidFlags.values()) {
            TYPES.put(type.value, type);
        }
    }


    MifidFlags(int newValue) {
        value = newValue;
    }

    /**
     Get MifidFlags from java input
     * @param newValue
     * @return MifidFlags enum
     */
    public static MifidFlags getMifidFlags(int newValue) {
        MifidFlags val = TYPES.get(newValue);
        return val == null ? MifidFlags.UNKNOWN : val;
    }

    /**
     * Get MifidFlags int value
     * @return int value
     */
    public int getMifidFlagsValue() { return value; }


    /**
     Get MifidFlags from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MifidFlags getMifidFlags(byte[] bytes, int offset) {
        return getMifidFlags(BendecUtils.uInt8FromByteArray(bytes, offset));
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
