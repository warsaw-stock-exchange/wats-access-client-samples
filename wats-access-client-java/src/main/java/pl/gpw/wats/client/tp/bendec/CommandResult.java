package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: CommandResult
 * Confirmation of achieving the intended action. If the action (e.g., Buy Only state) has already been achieved before the command, the result will be positive.
 */
public enum CommandResult {
    SUCCESS(1),
    FAILURE(2);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, CommandResult> TYPES = new HashMap<>();
    static {
        for (CommandResult type : CommandResult.values()) {
            TYPES.put(type.value, type);
        }
    }

    CommandResult(int newValue) {
        value = newValue;
    }

    /**
     * Get CommandResult by attribute
     * @param val
     * @return CommandResult enum or null if variant is undefined
     */
    public static CommandResult getCommandResult(int val) {
        return TYPES.get(val);
    }

    /**
     * Get CommandResult int value
     * @return int value
     */
    public int getCommandResultValue() {
        return value;
    }
    
    /**
     * Get CommandResult from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static CommandResult getCommandResult(byte[] bytes, int offset) {
        return getCommandResult(BendecUtils.uInt8FromByteArray(bytes, offset));
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