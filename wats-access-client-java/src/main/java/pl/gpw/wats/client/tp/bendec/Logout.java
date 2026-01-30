package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>Logout</h2>
 * <p>The logout message is sent from the client application to terminate the communication session with the trading port.</p>
 * <p>Byte length: 24</p>
 * <p>Header header - Header. | size 24</p>
 */
public class Logout implements ByteSerializable, Message {
    private Header header;
    public static final int byteLength = 24;

    public Logout(Header header) {
        this.header = header;
    }
    
    public Logout(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
    }
    
    public Logout(byte[] bytes) {
        this(bytes, 0);
    }
    
    public Logout() {
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
        return "Logout {" +
            "header=" + header +
            "}";
    }
}