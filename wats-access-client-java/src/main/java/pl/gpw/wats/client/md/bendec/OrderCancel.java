package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>OrderCancel</h2>
 * <p>Order deleted.</p>
 * <p>Byte length: 55</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) instrumentId - ID of financial instrument. | size 4</p>
 * <p>PublicOrderId > BigInteger (u64) publicOrderId - Order identifier (ID). | size 8</p>
 * <p>OrderSide side - Order side. | size 1</p>
 */
public class OrderCancel implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private BigInteger publicOrderId;
    private OrderSide side;
    public static final int byteLength = 55;

    public OrderCancel(Header header, long instrumentId, BigInteger publicOrderId, OrderSide side) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.publicOrderId = publicOrderId;
        this.side = side;
    }
    
    public OrderCancel(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.publicOrderId = BendecUtils.uInt64FromByteArray(bytes, offset + 46);
        this.side = OrderSide.getOrderSide(bytes, offset + 54);
    }
    
    public OrderCancel(byte[] bytes) {
        this(bytes, 0);
    }
    
    public OrderCancel() {
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
     * @return Order side.
     */
    public OrderSide getSide() {
        return this.side;
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
    
    /**
     * @param side Order side.
     */
    public void setSide(OrderSide side) {
        this.side = side;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.publicOrderId));
        side.toBytes(buffer);
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.publicOrderId));
        side.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        publicOrderId,
        side);
    }
    
    @Override
    public String toString() {
        return "OrderCancel {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", publicOrderId=" + publicOrderId +
            ", side=" + side +
            "}";
    }
}