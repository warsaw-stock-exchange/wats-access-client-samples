package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
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
    /**
     * Connection configuration has changed
     */
    CONNECTIONCONFIGCHANGED(6),
    /**
     * Service closed the connection because of an exchange operation  (e.g. supervision operation).
     */
    CLOSEOPS(7),
    /**
     * Service closed the connection because of an internal operation (e.g. grace period end).
     */
    DISCONNECT(8);
    
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
     * Get ConnectionCloseReason by attribute
     * @param val
     * @return ConnectionCloseReason enum or null if variant is undefined
     */
    public static ConnectionCloseReason getConnectionCloseReason(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get ConnectionCloseReason int value
     * @return int value
     */
    public int getConnectionCloseReasonValue() {
        return value; 
    }
    
    /**
     * Get ConnectionCloseReason from bytes
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