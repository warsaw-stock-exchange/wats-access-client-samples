package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: PartyRoleQualifier
 * Party role qualifier.
 */
public enum PartyRoleQualifier {
    NA(1),
    ALGORITHM(2),
    FIRMORLEGALENTITY(3),
    NATURALPERSON(4),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, PartyRoleQualifier> TYPES = new HashMap<>();
    static {
        for (PartyRoleQualifier type : PartyRoleQualifier.values()) {
            TYPES.put(type.value, type);
        }
    }


    PartyRoleQualifier(int newValue) {
        value = newValue;
    }

    /**
     Get PartyRoleQualifier from java input
     * @param newValue
     * @return PartyRoleQualifier enum
     */
    public static PartyRoleQualifier getPartyRoleQualifier(int newValue) {
        PartyRoleQualifier val = TYPES.get(newValue);
        return val == null ? PartyRoleQualifier.UNKNOWN : val;
    }

    /**
     * Get PartyRoleQualifier int value
     * @return int value
     */
    public int getPartyRoleQualifierValue() { return value; }


    /**
     Get PartyRoleQualifier from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static PartyRoleQualifier getPartyRoleQualifier(byte[] bytes, int offset) {
        return getPartyRoleQualifier(BendecUtils.uInt8FromByteArray(bytes, offset));
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
