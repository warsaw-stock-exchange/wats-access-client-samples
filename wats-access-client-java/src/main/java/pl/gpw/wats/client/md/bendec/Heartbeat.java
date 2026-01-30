package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>Heartbeat</h2>
 * <p>A message type used to check connectivity.</p>
 * <p>Byte length: 42</p>
 * <p>Header header - Message header. | size 42</p>
 */
public class Heartbeat implements ByteSerializable, Message {
    private Header header;
    public static final int byteLength = 42;

    public Heartbeat(Header header) {
        this.header = header;
    }
    
    public Heartbeat(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
    }
    
    public Heartbeat(byte[] bytes) {
        this(bytes, 0);
    }
    
    public Heartbeat() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header);
    }
    
    @Override
    public String toString() {
        return "Heartbeat {" +
            "header=" + header +
            "}";
    }
}