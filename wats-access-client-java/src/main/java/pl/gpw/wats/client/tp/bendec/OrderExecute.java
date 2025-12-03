package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>OrderExecute</h2>
 * <p>The message used to report trades between counterparties (i.e. generated when two or more orders are matched).</p>
 * <p>Byte length: 88</p>
 * <p>Header header - Header. | size 24</p>
 * <p>ElementId > long (u32) instrumentId - ID of the instrument being traded. | size 4</p>
 * <p>OrderId > BigInteger (u64) orderId - Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID. | size 8</p>
 * <p>TradeId > long (u32) tradeId - ID of the trade. | size 4</p>
 * <p>Price > long (i64) price - Price of the given trade. | size 8</p>
 * <p>Quantity > BigInteger (u64) quantity - Quantity of the instrument involved in the given trade. | size 8</p>
 * <p>Quantity > BigInteger (u64) leavesQty - How much of the given security is left on the market after the trade is concluded. | size 8</p>
 * <p>LiquidityIndicator liquidityIndicator - Liquidity indicator | size 1</p>
 * <p>Currency currency - Currency (e.g. USD). | size 2</p>
 * <p>OrderSide side - Indicates the order's side (buy or sell). | size 1</p>
 * <p>ClientOrderId > String (u8[]) clientOrderId - Arbitrary user provided value associated with the order. | size 20</p>
 */
public class OrderExecute implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private BigInteger orderId;
    private long tradeId;
    private long price;
    private BigInteger quantity;
    private BigInteger leavesQty;
    private LiquidityIndicator liquidityIndicator;
    private Currency currency;
    private OrderSide side;
    private String clientOrderId;
    public static final int byteLength = 88;
    
    public OrderExecute(Header header, long instrumentId, BigInteger orderId, long tradeId, long price, BigInteger quantity, BigInteger leavesQty, LiquidityIndicator liquidityIndicator, Currency currency, OrderSide side, String clientOrderId) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.orderId = orderId;
        this.tradeId = tradeId;
        this.price = price;
        this.quantity = quantity;
        this.leavesQty = leavesQty;
        this.liquidityIndicator = liquidityIndicator;
        this.currency = currency;
        this.side = side;
        this.clientOrderId = clientOrderId;
    }
    
    public OrderExecute(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 24);
        this.orderId = BendecUtils.uInt64FromByteArray(bytes, offset + 28);
        this.tradeId = BendecUtils.uInt32FromByteArray(bytes, offset + 36);
        this.price = BendecUtils.int64FromByteArray(bytes, offset + 40);
        this.quantity = BendecUtils.uInt64FromByteArray(bytes, offset + 48);
        this.leavesQty = BendecUtils.uInt64FromByteArray(bytes, offset + 56);
        this.liquidityIndicator = LiquidityIndicator.getLiquidityIndicator(bytes, offset + 64);
        this.currency = Currency.getCurrency(bytes, offset + 65);
        this.side = OrderSide.getOrderSide(bytes, offset + 67);
        this.clientOrderId = BendecUtils.stringFromByteArray(bytes, offset + 68, 20);
    }
    
    public OrderExecute(byte[] bytes) {
        this(bytes, 0);
    }
    
    public OrderExecute() {
    }
    
    /**
     * @return Header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return ID of the instrument being traded.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    }
    
    /**
     * @return Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID.
     */
    public BigInteger getOrderId() {
        return this.orderId;
    }
    
    /**
     * @return ID of the trade.
     */
    public long getTradeId() {
        return this.tradeId;
    }
    
    /**
     * @return Price of the given trade.
     */
    public long getPrice() {
        return this.price;
    }
    
    /**
     * @return Quantity of the instrument involved in the given trade.
     */
    public BigInteger getQuantity() {
        return this.quantity;
    }
    
    /**
     * @return How much of the given security is left on the market after the trade is concluded.
     */
    public BigInteger getLeavesQty() {
        return this.leavesQty;
    }
    
    /**
     * @return Liquidity indicator
     */
    public LiquidityIndicator getLiquidityIndicator() {
        return this.liquidityIndicator;
    }
    
    /**
     * @return Currency (e.g. USD).
     */
    public Currency getCurrency() {
        return this.currency;
    }
    
    /**
     * @return Indicates the order's side (buy or sell).
     */
    public OrderSide getSide() {
        return this.side;
    }
    
    /**
     * @return Arbitrary user provided value associated with the order.
     */
    public String getClientOrderId() {
        return this.clientOrderId;
    }
    
    /**
     * @param header Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param instrumentId ID of the instrument being traded.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }
    
    /**
     * @param orderId Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID.
     */
    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    }
    
    /**
     * @param tradeId ID of the trade.
     */
    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }
    
    /**
     * @param price Price of the given trade.
     */
    public void setPrice(long price) {
        this.price = price;
    }
    
    /**
     * @param quantity Quantity of the instrument involved in the given trade.
     */
    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }
    
    /**
     * @param leavesQty How much of the given security is left on the market after the trade is concluded.
     */
    public void setLeavesQty(BigInteger leavesQty) {
        this.leavesQty = leavesQty;
    }
    
    /**
     * @param liquidityIndicator Liquidity indicator
     */
    public void setLiquidityIndicator(LiquidityIndicator liquidityIndicator) {
        this.liquidityIndicator = liquidityIndicator;
    }
    
    /**
     * @param currency Currency (e.g. USD).
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    
    /**
     * @param side Indicates the order's side (buy or sell).
     */
    public void setSide(OrderSide side) {
        this.side = side;
    }
    
    /**
     * @param clientOrderId Arbitrary user provided value associated with the order.
     */
    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.orderId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradeId));
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        buffer.put(BendecUtils.uInt64ToByteArray(this.leavesQty));
        liquidityIndicator.toBytes(buffer);
        currency.toBytes(buffer);
        side.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.clientOrderId, 20));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.orderId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradeId));
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        buffer.put(BendecUtils.uInt64ToByteArray(this.leavesQty));
        liquidityIndicator.toBytes(buffer);
        currency.toBytes(buffer);
        side.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.clientOrderId, 20));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        orderId,
        tradeId,
        price,
        quantity,
        leavesQty,
        liquidityIndicator,
        currency,
        side,
        clientOrderId);
    }
    
    @Override
    public String toString() {
        return "OrderExecute {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", orderId=" + orderId +
            ", tradeId=" + tradeId +
            ", price=" + price +
            ", quantity=" + quantity +
            ", leavesQty=" + leavesQty +
            ", liquidityIndicator=" + liquidityIndicator +
            ", currency=" + currency +
            ", side=" + side +
            ", clientOrderId=" + clientOrderId +
            "}";
    }
}