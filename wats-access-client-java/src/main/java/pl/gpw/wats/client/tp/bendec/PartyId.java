package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: PartyId
 * Identification of the party.
 */
public enum PartyId {
    /**
     * No client for this order.
     */
    NONE(1),
    /**
     * An aggregation of multiple client orders.
     */
    AGGR(2),
    /**
     * Clients are pending allocation.
     */
    PNAL(3),
    /**
     * Timing and location of the execution determined by the client of the participant.
     */
    NORE(4),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, PartyId> TYPES = new HashMap<>();
    static {
        for (PartyId type : PartyId.values()) {
            TYPES.put(type.value, type);
        }
    }


    PartyId(int newValue) {
        value = newValue;
    }

    /**
     Get PartyId from java input
     * @param newValue
     * @return PartyId enum
     */
    public static PartyId getPartyId(int newValue) {
        PartyId val = TYPES.get(newValue);
        return val == null ? PartyId.UNKNOWN : val;
    }

    /**
     * Get PartyId int value
     * @return int value
     */
    public int getPartyIdValue() { return value; }


    /**
     Get PartyId from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static PartyId getPartyId(byte[] bytes, int offset) {
        return getPartyId(BendecUtils.uInt8FromByteArray(bytes, offset));
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
