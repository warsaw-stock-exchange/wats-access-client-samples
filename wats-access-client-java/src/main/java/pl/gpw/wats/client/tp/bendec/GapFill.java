package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>GapFill</h2>
 * <p>Create a SeqNum gap bettwen current SeqNum and header::seqNum</p>
 * <p>Byte length: 24</p>
 * <p>Header header - Message header. | size 24</p>
 */
public class GapFill implements ByteSerializable, Message {
    private Header header;
    public static final int byteLength = 24;
    
    public GapFill(Header header) {
        this.header = header;
    }
    
    public GapFill(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
    }
    
    public GapFill(byte[] bytes) {
        this(bytes, 0);
    }
    
    public GapFill() {
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
        return "GapFill {" +
            "header=" + header +
            "}";
    }
}