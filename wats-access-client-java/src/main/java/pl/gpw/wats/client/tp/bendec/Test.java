package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>Test</h2>
 * <p>The internal message for a test purpose.</p>
 * <p>Byte length: 24</p>
 * <p>Header header - Header. | size 16</p>
 * <p>Timestamp > BigInteger (u64) targetTimestamp - Test timestamp (e.g. the send time of a message). | size 8</p>
 */
public class Test implements ByteSerializable, Message {
    private Header header;
    private BigInteger targetTimestamp;
    public static final int byteLength = 24;
    
    public Test(Header header, BigInteger targetTimestamp) {
        this.header = header;
        this.targetTimestamp = targetTimestamp;
    }
    
    public Test(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.targetTimestamp = BendecUtils.uInt64FromByteArray(bytes, offset + 16);
    }
    
    public Test(byte[] bytes) {
        this(bytes, 0);
    }
    
    public Test() {
    }
    
    /**
     * @return Header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Test timestamp (e.g. the send time of a message).
     */
    public BigInteger getTargetTimestamp() {
        return this.targetTimestamp;
    }
    
    /**
     * @param header Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param targetTimestamp Test timestamp (e.g. the send time of a message).
     */
    public void setTargetTimestamp(BigInteger targetTimestamp) {
        this.targetTimestamp = targetTimestamp;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.targetTimestamp));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.targetTimestamp));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        targetTimestamp);
    }
    
    @Override
    public String toString() {
        return "Test {" +
            "header=" + header +
            ", targetTimestamp=" + targetTimestamp +
            "}";
    }
}