package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: CommandAction
 * The action for the Market Maker command request.
 */
public enum CommandAction {
    CHANGETOHYBRIDBUYONLY(1),
    CHANGETOKNOCKOUT(2),
    REVOKEKNOCKOUT(3);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, CommandAction> TYPES = new HashMap<>();
    static {
        for (CommandAction type : CommandAction.values()) {
            TYPES.put(type.value, type);
        }
    }

    CommandAction(int newValue) {
        value = newValue;
    }

    /**
     * Get CommandAction by attribute
     * @param val
     * @return CommandAction enum or null if variant is undefined
     */
    public static CommandAction getCommandAction(int val) {
        return TYPES.get(val);
    }

    /**
     * Get CommandAction int value
     * @return int value
     */
    public int getCommandActionValue() {
        return value;
    }
    
    /**
     * Get CommandAction from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static CommandAction getCommandAction(byte[] bytes, int offset) {
        return getCommandAction(BendecUtils.uInt8FromByteArray(bytes, offset));
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