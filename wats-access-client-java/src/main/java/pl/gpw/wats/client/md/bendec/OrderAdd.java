package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>OrderAdd</h2>
 * <p>New (limit) order added to order book.</p>
 * <p>Byte length: 68</p>
 * <p>Header header - Message header. | size 39</p>
 * <p>PublicOrderId > BigInteger (u64) publicOrderId - Order identifier (ID).  | size 8</p>
 * <p>ElementId > long (u32) instrumentId - ID of financial instrument. | size 4</p>
 * <p>OrderSide side - Order side (buy or sell). | size 1</p>
 * <p>Price > long (i64) price - Order (limit) price. | size 8</p>
 * <p>Quantity > BigInteger (u64) quantity - Order quantity. | size 8</p>
 * */

public class OrderAdd implements ByteSerializable, Message {

    private Header header;
    private BigInteger publicOrderId;
    private long instrumentId;
    private OrderSide side;
    private long price;
    private BigInteger quantity;
    public static final int byteLength = 68;

    public OrderAdd(Header header, BigInteger publicOrderId, long instrumentId, OrderSide side, long price, BigInteger quantity) {
        this.header = header;
        this.publicOrderId = publicOrderId;
        this.instrumentId = instrumentId;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.ORDERADD);
    }

    public OrderAdd(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.publicOrderId = BendecUtils.uInt64FromByteArray(bytes, offset + 39);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 47);
        this.side = OrderSide.getOrderSide(bytes, offset + 51);
        this.price = BendecUtils.int64FromByteArray(bytes, offset + 52);
        this.quantity = BendecUtils.uInt64FromByteArray(bytes, offset + 60);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.ORDERADD);
    }

    public OrderAdd(byte[] bytes) {
        this(bytes, 0);
    }

    public OrderAdd() {
    }



    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return Order identifier (ID). 
     */
    public BigInteger getPublicOrderId() {
        return this.publicOrderId;
    };
    /**
     * @return ID of financial instrument.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    };
    /**
     * @return Order side (buy or sell).
     */
    public OrderSide getSide() {
        return this.side;
    };
    /**
     * @return Order (limit) price.
     */
    public long getPrice() {
        return this.price;
    };
    /**
     * @return Order quantity.
     */
    public BigInteger getQuantity() {
        return this.quantity;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param publicOrderId Order identifier (ID). 
     */
    public void setPublicOrderId(BigInteger publicOrderId) {
        this.publicOrderId = publicOrderId;
    };
    /**
     * @param instrumentId ID of financial instrument.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    };
    /**
     * @param side Order side (buy or sell).
     */
    public void setSide(OrderSide side) {
        this.side = side;
    };
    /**
     * @param price Order (limit) price.
     */
    public void setPrice(long price) {
        this.price = price;
    };
    /**
     * @param quantity Order quantity.
     */
    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.publicOrderId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        side.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.publicOrderId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        side.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, publicOrderId, instrumentId, side, price, quantity);
    }

    @Override
    public String toString() {
        return "OrderAdd{" +
            "header=" + header +
            ", publicOrderId=" + publicOrderId +
            ", instrumentId=" + instrumentId +
            ", side=" + side +
            ", price=" + price +
            ", quantity=" + quantity +
            '}';
        }
}
