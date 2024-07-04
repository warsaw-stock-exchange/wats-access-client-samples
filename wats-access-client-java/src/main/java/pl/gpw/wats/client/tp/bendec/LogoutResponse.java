package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>LogoutResponse</h2>
 * <p>The logout response message confirms the client logout message.</p>
 * <p>Byte length: 16</p>
 * <p>Header header - Header. | size 16</p>
 */
public class LogoutResponse implements ByteSerializable, Message {
    private Header header;
    public static final int byteLength = 16;
    
    public LogoutResponse(Header header) {
        this.header = header;
    }
    
    public LogoutResponse(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
    }
    
    public LogoutResponse(byte[] bytes) {
        this(bytes, 0);
    }
    
    public LogoutResponse() {
    }
    
    /**
     * @return Header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @param header Header.
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
        return "LogoutResponse {" +
            "header=" + header +
            "}";
    }
}