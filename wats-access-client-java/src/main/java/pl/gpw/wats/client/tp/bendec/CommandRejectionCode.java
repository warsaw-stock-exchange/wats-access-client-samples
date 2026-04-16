package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: CommandRejectionCode
 * Reason for rejecting the Market Maker command.
 */
public enum CommandRejectionCode {
    OTHER(99),
    UNKNOWNINSTRUMENT(1001),
    /**
     * Market Maker Command may be submitted only by firm that is a Market Maker for the instrument.
     */
    FIRMNOTAUTHORIZEDFORMMCOMMAND(1304),
    /**
     * Attempt to knock-out an instrument that is already in knocked-out state.
     */
    INSTRUMENTALREADYKNOCKEDOUT(1313),
    /**
     * Market Maker's attempt to revoke Market Operation's knock-out on an instrument.
     */
    MMCANNOTREVOKEMARKETOPERATIONKNOCKOUT(1314),
    /**
     * Cannot knockout or revoke knockout for suspended instrument.
     */
    KNOCKOUTORREVOKEKNOCKOUTOPERATIONSNOTALLOWEDDURINGINSTRUMENTSUSPENSION(1315),
    /**
     * Revoke knockout cannot be submitted once knock-out barrier has been reached.
     */
    REVOKEKNOCKOUTOPERATIONSNOTALLOWEDONCEBARRIERISREACHED(1316),
    /**
     * Market maker command not allowed for selected market model.
     */
    MMCOMMANDNOTALLOWEDFORSELECTEDMARKETMODEL(1317),
    /**
     * Revoke hybrid knockout command is not applicable for active instruments.
     */
    REVOKEKNOCKOUTOPERATIONSNOTALLOWEDFORACTIVEINSTRUMENT(1318),
    /**
     * BLOCK instrument cannot be put in hybrid knockout
     */
    REQUESTNOTALLOWEDFORBLOCKINSTRUMENT(2026),
    /**
     * CLOB instrument cannot be put in hybrid knockout
     */
    REQUESTNOTALLOWEDFORCLOBINSTRUMENT(2027),
    /**
     * CROSS instrument cannot be put in hybrid knockout
     */
    REQUESTNOTALLOWEDFORCROSSINSTRUMENT(2028),
    EXCHANGECLOSED(3002),
    FIRMNOTAUTHORIZEDTOQUOTEINSTRUMENT(3009),
    COMMANDNOTALLOWEDINCURRENTSTATE(3020);

    private final int value;
    private final int byteLength = 2;

    private static final Map<Integer, CommandRejectionCode> TYPES = new HashMap<>();
    static {
        for (CommandRejectionCode type : CommandRejectionCode.values()) {
            TYPES.put(type.value, type);
        }
    }

    CommandRejectionCode(int newValue) {
        value = newValue;
    }

    /**
     * Get CommandRejectionCode by attribute
     * @param val
     * @return CommandRejectionCode enum or null if variant is undefined
     */
    public static CommandRejectionCode getCommandRejectionCode(int val) {
        return TYPES.get(val);
    }

    /**
     * Get CommandRejectionCode int value
     * @return int value
     */
    public int getCommandRejectionCodeValue() {
        return value;
    }
    
    /**
     * Get CommandRejectionCode from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static CommandRejectionCode getCommandRejectionCode(byte[] bytes, int offset) {
        return getCommandRejectionCode(BendecUtils.uInt16FromByteArray(bytes, offset));
    }
    
    byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        buffer.put(BendecUtils.uInt16ToByteArray(this.value));
        return buffer.array();
    }
    
    void toBytes(ByteBuffer buffer) {
        buffer.put(BendecUtils.uInt16ToByteArray(this.value));
    }
}