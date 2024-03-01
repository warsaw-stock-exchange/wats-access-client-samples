package pl.gpw.wats.client.replay.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: ReplayMsgType
 * undefined
 */
public enum ReplayMsgType {
    /**
     * Replay service request
     */
    REPLAYREQUEST(1),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 2;


    private static final Map<Integer, ReplayMsgType> TYPES = new HashMap<>();
    static {
        for (ReplayMsgType type : ReplayMsgType.values()) {
            TYPES.put(type.value, type);
        }
    }


    ReplayMsgType(int newValue) {
        value = newValue;
    }

    /**
     Get ReplayMsgType from java input
     * @param newValue
     * @return ReplayMsgType enum
     */
    public static ReplayMsgType getReplayMsgType(int newValue) {
        ReplayMsgType val = TYPES.get(newValue);
        return val == null ? ReplayMsgType.UNKNOWN : val;
    }

    /**
     * Get ReplayMsgType int value
     * @return int value
     */
    public int getReplayMsgTypeValue() { return value; }


    /**
     Get ReplayMsgType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static ReplayMsgType getReplayMsgType(byte[] bytes, int offset) {
        return getReplayMsgType(BendecUtils.uInt16FromByteArray(bytes, offset));
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
