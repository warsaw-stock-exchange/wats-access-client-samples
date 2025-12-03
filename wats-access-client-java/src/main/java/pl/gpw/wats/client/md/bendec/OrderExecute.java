package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>OrderExecute</h2>
 * <p>Execution report.</p>
 * <p>Byte length: 83</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>Quantity > BigInteger (u64) quantity - Remaining un-executed order quantity. | size 8</p>
 * <p>ElementId > long (u32) instrumentId - ID of financial instrument. | size 4</p>
 * <p>PublicOrderId > BigInteger (u64) publicOrderId - Order identifier (ID). | size 8</p>
 * <p>TradeId > long (u32) tradeId - ID of the underlying trade (equal to TradeID in Trade.) | size 4</p>
 * <p>Price > long (i64) executionPrice - Price at which the order was executed. | size 8</p>
 * <p>Quantity > BigInteger (u64) executionQuantity - The order’s executed quantity. | size 8</p>
 * <p>OrderSide side - Order side. | size 1</p>
 */
public class OrderExecute implements ByteSerializable, Message {
    private Header header;
    private BigInteger quantity;
    private long instrumentId;
    private BigInteger publicOrderId;
    private long tradeId;
    private long executionPrice;
    private BigInteger executionQuantity;
    private OrderSide side;
    public static final int byteLength = 83;
    
    public OrderExecute(Header header, BigInteger quantity, long instrumentId, BigInteger publicOrderId, long tradeId, long executionPrice, BigInteger executionQuantity, OrderSide side) {
        this.header = header;
        this.quantity = quantity;
        this.instrumentId = instrumentId;
        this.publicOrderId = publicOrderId;
        this.tradeId = tradeId;
        this.executionPrice = executionPrice;
        this.executionQuantity = executionQuantity;
        this.side = side;
    }
    
    public OrderExecute(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.quantity = BendecUtils.uInt64FromByteArray(bytes, offset + 42);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 50);
        this.publicOrderId = BendecUtils.uInt64FromByteArray(bytes, offset + 54);
        this.tradeId = BendecUtils.uInt32FromByteArray(bytes, offset + 62);
        this.executionPrice = BendecUtils.int64FromByteArray(bytes, offset + 66);
        this.executionQuantity = BendecUtils.uInt64FromByteArray(bytes, offset + 74);
        this.side = OrderSide.getOrderSide(bytes, offset + 82);
    }
    
    public OrderExecute(byte[] bytes) {
        this(bytes, 0);
    }
    
    public OrderExecute() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Remaining un-executed order quantity.
     */
    public BigInteger getQuantity() {
        return this.quantity;
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
     * @return ID of the underlying trade (equal to TradeID in Trade.)
     */
    public long getTradeId() {
        return this.tradeId;
    }
    
    /**
     * @return Price at which the order was executed.
     */
    public long getExecutionPrice() {
        return this.executionPrice;
    }
    
    /**
     * @return The order’s executed quantity.
     */
    public BigInteger getExecutionQuantity() {
        return this.executionQuantity;
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
     * @param quantity Remaining un-executed order quantity.
     */
    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
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
     * @param tradeId ID of the underlying trade (equal to TradeID in Trade.)
     */
    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }
    
    /**
     * @param executionPrice Price at which the order was executed.
     */
    public void setExecutionPrice(long executionPrice) {
        this.executionPrice = executionPrice;
    }
    
    /**
     * @param executionQuantity The order’s executed quantity.
     */
    public void setExecutionQuantity(BigInteger executionQuantity) {
        this.executionQuantity = executionQuantity;
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
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.publicOrderId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradeId));
        buffer.put(BendecUtils.int64ToByteArray(this.executionPrice));
        buffer.put(BendecUtils.uInt64ToByteArray(this.executionQuantity));
        side.toBytes(buffer);
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.publicOrderId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradeId));
        buffer.put(BendecUtils.int64ToByteArray(this.executionPrice));
        buffer.put(BendecUtils.uInt64ToByteArray(this.executionQuantity));
        side.toBytes(buffer);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        quantity,
        instrumentId,
        publicOrderId,
        tradeId,
        executionPrice,
        executionQuantity,
        side);
    }
    
    @Override
    public String toString() {
        return "OrderExecute {" +
            "header=" + header +
            ", quantity=" + quantity +
            ", instrumentId=" + instrumentId +
            ", publicOrderId=" + publicOrderId +
            ", tradeId=" + tradeId +
            ", executionPrice=" + executionPrice +
            ", executionQuantity=" + executionQuantity +
            ", side=" + side +
            "}";
    }
}