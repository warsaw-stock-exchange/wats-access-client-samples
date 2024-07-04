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