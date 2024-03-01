package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>RequestForExecution</h2>
 * <p>The information for the MM that one of the quotes has been crossed.</p>
 * <p>Byte length: 37</p>
 * <p>Header header - Message header. | size 16</p>
 * <p>ElementId > long (u32) instrumentId - Instrument ID | size 4</p>
 * <p>OrderSide side - Indicates the side of the order | size 1</p>
 * <p>Price > long (i64) price - Indicates the price of the order. | size 8</p>
 * <p>Quantity > BigInteger (u64) quantity - Indicates the quantity of the instrument included in the order. | size 8</p>
 * */

public class RequestForExecution implements ByteSerializable, Message {

    private Header header;
    private long instrumentId;
    private OrderSide side;
    private long price;
    private BigInteger quantity;
    public static final int byteLength = 37;

    public RequestForExecution(Header header, long instrumentId, OrderSide side, long price, BigInteger quantity) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.REQUESTFOREXECUTION);
    }

    public RequestForExecution(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 16);
        this.side = OrderSide.getOrderSide(bytes, offset + 20);
        this.price = BendecUtils.int64FromByteArray(bytes, offset + 21);
        this.quantity = BendecUtils.uInt64FromByteArray(bytes, offset + 29);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.REQUESTFOREXECUTION);
    }

    public RequestForExecution(byte[] bytes) {
        this(bytes, 0);
    }

    public RequestForExecution() {
    }



    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return Instrument ID
     */
    public long getInstrumentId() {
        return this.instrumentId;
    };
    /**
     * @return Indicates the side of the order
     */
    public OrderSide getSide() {
        return this.side;
    };
    /**
     * @return Indicates the price of the order.
     */
    public long getPrice() {
        return this.price;
    };
    /**
     * @return Indicates the quantity of the instrument included in the order.
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
     * @param instrumentId Instrument ID
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    };
    /**
     * @param side Indicates the side of the order
     */
    public void setSide(OrderSide side) {
        this.side = side;
    };
    /**
     * @param price Indicates the price of the order.
     */
    public void setPrice(long price) {
        this.price = price;
    };
    /**
     * @param quantity Indicates the quantity of the instrument included in the order.
     */
    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        side.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        side.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, instrumentId, side, price, quantity);
    }

    @Override
    public String toString() {
        return "RequestForExecution{" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", side=" + side +
            ", price=" + price +
            ", quantity=" + quantity +
            '}';
        }
}
