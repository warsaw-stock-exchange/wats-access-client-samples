package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>ConnectionClose</h2>
 * <p>The ConnectionClose message confirms the termination of a session through the trading port service.</p>
 * <p>Byte length: 25</p>
 * <p>Header header - Header. | size 24</p>
 * <p>ConnectionCloseReason reason - Connection close reason. | size 1</p>
 */
public class ConnectionClose implements ByteSerializable, Message {
    private Header header;
    private ConnectionCloseReason reason;
    public static final int byteLength = 25;

    public ConnectionClose(Header header, ConnectionCloseReason reason) {
        this.header = header;
        this.reason = reason;
    }
    
    public ConnectionClose(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.reason = ConnectionCloseReason.getConnectionCloseReason(bytes, offset + 24);
    }
    
    public ConnectionClose(byte[] bytes) {
        this(bytes, 0);
    }
    
    public ConnectionClose() {
    }
    
    /**
     * @return Header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Connection close reason.
     */
    public ConnectionCloseReason getReason() {
        return this.reason;
    }

    /**
     * @param header Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param reason Connection close reason.
     */
    public void setReason(ConnectionCloseReason reason) {
        this.reason = reason;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        reason.toBytes(buffer);
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        reason.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header,
        reason);
    }
    
    @Override
    public String toString() {
        return "ConnectionClose {" +
            "header=" + header +
            ", reason=" + reason +
            "}";
    }
}