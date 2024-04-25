package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: TargetPartyRole
 * Target party role filter selection field.
 */
public enum TargetPartyRole {
    /**
     * No filtering.
     */
    NONE(0),
    /**
     * Liquidity provider.
     */
    LIQUIDITYPROVIDER(35),
    /**
     * Sender location.
     */
    SENDERLOCATION(54),
    /**
     * Market maker.
     */
    MARKETMAKER(66),
    /**
     * Client ID.
     */
    CLIENTID(3),
    /**
     * Executing trader.
     */
    EXECUTINGTRADER(12),
    /**
     * Investment decision maker.
     */
    INVESTMENTDECISIONMAKER(122),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, TargetPartyRole> TYPES = new HashMap<>();
    static {
        for (TargetPartyRole type : TargetPartyRole.values()) {
            TYPES.put(type.value, type);
        }
    }


    TargetPartyRole(int newValue) {
        value = newValue;
    }

    /**
     Get TargetPartyRole from java input
     * @param newValue
     * @return TargetPartyRole enum
     */
    public static TargetPartyRole getTargetPartyRole(int newValue) {
        TargetPartyRole val = TYPES.get(newValue);
        return val == null ? TargetPartyRole.UNKNOWN : val;
    }

    /**
     * Get TargetPartyRole int value
     * @return int value
     */
    public int getTargetPartyRoleValue() { return value; }


    /**
     Get TargetPartyRole from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static TargetPartyRole getTargetPartyRole(byte[] bytes, int offset) {
        return getTargetPartyRole(BendecUtils.uInt8FromByteArray(bytes, offset));
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
