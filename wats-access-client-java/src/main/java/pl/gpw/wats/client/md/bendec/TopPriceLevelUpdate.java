package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>TopPriceLevelUpdate</h2>
 * <p>BBO Level 1.</p>
 * <p>Byte length: 65</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) instrumentId - ID of financial instrument. | size 4</p>
 * <p>Price > long (i64) price - BBO level 1 price level in currency units. | size 8</p>
 * <p>Quantity > BigInteger (u64) quantity - BBO level 1 quantity at price level. | size 8</p>
 * <p>OrderSide side - Side (buy or sell). | size 1</p>
 * <p>OrderCount > int (u16) orderCount - Number of open orders at BBO level 1. | size 2</p>
 */
public class TopPriceLevelUpdate implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private long price;
    private BigInteger quantity;
    private OrderSide side;
    private int orderCount;
    public static final int byteLength = 65;
    
    public TopPriceLevelUpdate(Header header, long instrumentId, long price, BigInteger quantity, OrderSide side, int orderCount) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.price = price;
        this.quantity = quantity;
        this.side = side;
        this.orderCount = orderCount;
    }
    
    public TopPriceLevelUpdate(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.price = BendecUtils.int64FromByteArray(bytes, offset + 46);
        this.quantity = BendecUtils.uInt64FromByteArray(bytes, offset + 54);
        this.side = OrderSide.getOrderSide(bytes, offset + 62);
        this.orderCount = BendecUtils.uInt16FromByteArray(bytes, offset + 63);
    }
    
    public TopPriceLevelUpdate(byte[] bytes) {
        this(bytes, 0);
    }
    
    public TopPriceLevelUpdate() {
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
     * @return BBO level 1 price level in currency units.
     */
    public long getPrice() {
        return this.price;
    }
    
    /**
     * @return BBO level 1 quantity at price level.
     */
    public BigInteger getQuantity() {
        return this.quantity;
    }
    
    /**
     * @return Side (buy or sell).
     */
    public OrderSide getSide() {
        return this.side;
    }
    
    /**
     * @return Number of open orders at BBO level 1.
     */
    public int getOrderCount() {
        return this.orderCount;
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
     * @param price BBO level 1 price level in currency units.
     */
    public void setPrice(long price) {
        this.price = price;
    }
    
    /**
     * @param quantity BBO level 1 quantity at price level.
     */
    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }
    
    /**
     * @param side Side (buy or sell).
     */
    public void setSide(OrderSide side) {
        this.side = side;
    }
    
    /**
     * @param orderCount Number of open orders at BBO level 1.
     */
    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        side.toBytes(buffer);
        buffer.put(BendecUtils.uInt16ToByteArray(this.orderCount));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        side.toBytes(buffer);
        buffer.put(BendecUtils.uInt16ToByteArray(this.orderCount));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        price,
        quantity,
        side,
        orderCount);
    }
    
    @Override
    public String toString() {
        return "TopPriceLevelUpdate {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", price=" + price +
            ", quantity=" + quantity +
            ", side=" + side +
            ", orderCount=" + orderCount +
            "}";
    }
}