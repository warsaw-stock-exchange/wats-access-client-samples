package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: InitiateStateResult
 * The result of the state change.
 */
public enum InitiateStateResult {
    SUCCESS(1),
    FAILURE(2),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, InitiateStateResult> TYPES = new HashMap<>();
    static {
        for (InitiateStateResult type : InitiateStateResult.values()) {
            TYPES.put(type.value, type);
        }
    }


    InitiateStateResult(int newValue) {
        value = newValue;
    }

    /**
     Get InitiateStateResult from java input
     * @param newValue
     * @return InitiateStateResult enum
     */
    public static InitiateStateResult getInitiateStateResult(int newValue) {
        InitiateStateResult val = TYPES.get(newValue);
        return val == null ? InitiateStateResult.UNKNOWN : val;
    }

    /**
     * Get InitiateStateResult int value
     * @return int value
     */
    public int getInitiateStateResultValue() { return value; }


    /**
     Get InitiateStateResult from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static InitiateStateResult getInitiateStateResult(byte[] bytes, int offset) {
        return getInitiateStateResult(BendecUtils.uInt8FromByteArray(bytes, offset));
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
