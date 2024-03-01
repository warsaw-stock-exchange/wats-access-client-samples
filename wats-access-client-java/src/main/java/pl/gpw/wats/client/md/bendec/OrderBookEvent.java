package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>OrderBookEvent</h2>
 * <p>Order book event for given instrument</p>
 * <p>Byte length: 44</p>
 * <p>Header header - Message header. | size 39</p>
 * <p>ElementId > long (u32) instrumentId - ID of the instrument. | size 4</p>
 * <p>OrderBookEventType eventType - Type/Kind of book event | size 1</p>
 * */

public class OrderBookEvent implements ByteSerializable, Message {

    private Header header;
    private long instrumentId;
    private OrderBookEventType eventType;
    public static final int byteLength = 44;

    public OrderBookEvent(Header header, long instrumentId, OrderBookEventType eventType) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.eventType = eventType;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.ORDERBOOKEVENT);
    }

    public OrderBookEvent(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 39);
        this.eventType = OrderBookEventType.getOrderBookEventType(bytes, offset + 43);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.ORDERBOOKEVENT);
    }

    public OrderBookEvent(byte[] bytes) {
        this(bytes, 0);
    }

    public OrderBookEvent() {
    }



    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return ID of the instrument.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    };
    /**
     * @return Type/Kind of book event
     */
    public OrderBookEventType getEventType() {
        return this.eventType;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param instrumentId ID of the instrument.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    };
    /**
     * @param eventType Type/Kind of book event
     */
    public void setEventType(OrderBookEventType eventType) {
        this.eventType = eventType;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        eventType.toBytes(buffer);
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        eventType.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, instrumentId, eventType);
    }

    @Override
    public String toString() {
        return "OrderBookEvent{" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", eventType=" + eventType +
            '}';
        }
}
