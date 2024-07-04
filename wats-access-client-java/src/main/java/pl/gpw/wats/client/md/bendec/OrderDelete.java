package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>OrderDelete</h2>
 * <p>Order deleted.</p>
 * <p>Byte length: 54</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) instrumentId - ID of financial instrument. | size 4</p>
 * <p>PublicOrderId > BigInteger (u64) publicOrderId - Order identifier (ID). | size 8</p>
 */
public class OrderDelete implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private BigInteger publicOrderId;
    public static final int byteLength = 54;
    
    public OrderDelete(Header header, long instrumentId, BigInteger publicOrderId) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.publicOrderId = publicOrderId;
    }
    
    public OrderDelete(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.publicOrderId = BendecUtils.uInt64FromByteArray(bytes, offset + 46);
    }
    
    public OrderDelete(byte[] bytes) {
        this(bytes, 0);
    }
    
    public OrderDelete() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return ID of financial instrument.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    }
    
    /**
     * @return Order identifier (ID).
     */
    public BigInteger getPublicOrderId() {
        return this.publicOrderId;
    }
    
    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param instrumentId ID of financial instrument.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }
    
    /**
     * @param publicOrderId Order identifier (ID).
     */
    public void setPublicOrderId(BigInteger publicOrderId) {
        this.publicOrderId = publicOrderId;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.publicOrderId));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.publicOrderId));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        publicOrderId);
    }
    
    @Override
    public String toString() {
        return "OrderDelete {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", publicOrderId=" + publicOrderId +
            "}";
    }
}