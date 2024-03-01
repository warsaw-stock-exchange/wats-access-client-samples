package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: PartyIdSource
 * Used to identify classification source of the party id.
 */
public enum PartyIdSource {
    /**
     * Proprietary / Custom code.
     */
    PROPRIETARY(68),
    /**
     * Short code identifier.
     */
    SHORTCODE(80),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, PartyIdSource> TYPES = new HashMap<>();
    static {
        for (PartyIdSource type : PartyIdSource.values()) {
            TYPES.put(type.value, type);
        }
    }


    PartyIdSource(int newValue) {
        value = newValue;
    }

    /**
     Get PartyIdSource from java input
     * @param newValue
     * @return PartyIdSource enum
     */
    public static PartyIdSource getPartyIdSource(int newValue) {
        PartyIdSource val = TYPES.get(newValue);
        return val == null ? PartyIdSource.UNKNOWN : val;
    }

    /**
     * Get PartyIdSource int value
     * @return int value
     */
    public int getPartyIdSourceValue() { return value; }


    /**
     Get PartyIdSource from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static PartyIdSource getPartyIdSource(byte[] bytes, int offset) {
        return getPartyIdSource(BendecUtils.uInt8FromByteArray(bytes, offset));
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
