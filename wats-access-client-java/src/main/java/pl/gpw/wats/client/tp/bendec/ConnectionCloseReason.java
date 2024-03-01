package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: ConnectionCloseReason
 * Reasons of the logout.
 */
public enum ConnectionCloseReason {
    /**
     * Invalid message or frame length.
     */
    PROTOCOLERROR(1),
    /**
     * Message came with an incorrect sequence number.
     */
    INVALIDSEQNUM(2),
    /**
     * The session day has come to an end.
     */
    ENDOFDAY(3),
    /**
     * The synchronization of messages has failed.
     */
    SYNCFAIL(4),
    /**
     * The second level of the throttling limit has been exceeded.
     */
    ANTIFLOODINGTHRESHOLDEXCEEDED(5),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, ConnectionCloseReason> TYPES = new HashMap<>();
    static {
        for (ConnectionCloseReason type : ConnectionCloseReason.values()) {
            TYPES.put(type.value, type);
        }
    }


    ConnectionCloseReason(int newValue) {
        value = newValue;
    }

    /**
     Get ConnectionCloseReason from java input
     * @param newValue
     * @return ConnectionCloseReason enum
     */
    public static ConnectionCloseReason getConnectionCloseReason(int newValue) {
        ConnectionCloseReason val = TYPES.get(newValue);
        return val == null ? ConnectionCloseReason.UNKNOWN : val;
    }

    /**
     * Get ConnectionCloseReason int value
     * @return int value
     */
    public int getConnectionCloseReasonValue() { return value; }


    /**
     Get ConnectionCloseReason from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static ConnectionCloseReason getConnectionCloseReason(byte[] bytes, int offset) {
        return getConnectionCloseReason(BendecUtils.uInt8FromByteArray(bytes, offset));
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
