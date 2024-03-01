package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>Trade</h2>
 * <p>The message used to report trades between counterparties (i.e. generated when two or more orders are matched).</p>
 * <p>Byte length: 52</p>
 * <p>Header header - Header. | size 16</p>
 * <p>OrderId > BigInteger (u64) orderId - Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID. | size 8</p>
 * <p>TradeId > long (u32) id - ID of the trade. | size 4</p>
 * <p>Price > long (i64) price - Price of the given trade. | size 8</p>
 * <p>Quantity > BigInteger (u64) quantity - Quantity of the instrument involved in the given trade. | size 8</p>
 * <p>Quantity > BigInteger (u64) leavesQty - How much of the given security is left on the market after the trade is concluded. | size 8</p>
 * */

public class Trade implements ByteSerializable, Message {

    private Header header;
    private BigInteger orderId;
    private long id;
    private long price;
    private BigInteger quantity;
    private BigInteger leavesQty;
    public static final int byteLength = 52;

    public Trade(Header header, BigInteger orderId, long id, long price, BigInteger quantity, BigInteger leavesQty) {
        this.header = header;
        this.orderId = orderId;
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.leavesQty = leavesQty;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.TRADE);
    }

    public Trade(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.orderId = BendecUtils.uInt64FromByteArray(bytes, offset + 16);
        this.id = BendecUtils.uInt32FromByteArray(bytes, offset + 24);
        this.price = BendecUtils.int64FromByteArray(bytes, offset + 28);
        this.quantity = BendecUtils.uInt64FromByteArray(bytes, offset + 36);
        this.leavesQty = BendecUtils.uInt64FromByteArray(bytes, offset + 44);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.TRADE);
    }

    public Trade(byte[] bytes) {
        this(bytes, 0);
    }

    public Trade() {
    }



    /**
     * @return Header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID.
     */
    public BigInteger getOrderId() {
        return this.orderId;
    };
    /**
     * @return ID of the trade.
     */
    public long getId() {
        return this.id;
    };
    /**
     * @return Price of the given trade.
     */
    public long getPrice() {
        return this.price;
    };
    /**
     * @return Quantity of the instrument involved in the given trade.
     */
    public BigInteger getQuantity() {
        return this.quantity;
    };
    /**
     * @return How much of the given security is left on the market after the trade is concluded.
     */
    public BigInteger getLeavesQty() {
        return this.leavesQty;
    };

    /**
     * @param header Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param orderId Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID.
     */
    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    };
    /**
     * @param id ID of the trade.
     */
    public void setId(long id) {
        this.id = id;
    };
    /**
     * @param price Price of the given trade.
     */
    public void setPrice(long price) {
        this.price = price;
    };
    /**
     * @param quantity Quantity of the instrument involved in the given trade.
     */
    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    };
    /**
     * @param leavesQty How much of the given security is left on the market after the trade is concluded.
     */
    public void setLeavesQty(BigInteger leavesQty) {
        this.leavesQty = leavesQty;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.orderId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.id));
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        buffer.put(BendecUtils.uInt64ToByteArray(this.leavesQty));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.orderId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.id));
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        buffer.put(BendecUtils.uInt64ToByteArray(this.leavesQty));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, orderId, id, price, quantity, leavesQty);
    }

    @Override
    public String toString() {
        return "Trade{" +
            "header=" + header +
            ", orderId=" + orderId +
            ", id=" + id +
            ", price=" + price +
            ", quantity=" + quantity +
            ", leavesQty=" + leavesQty +
            '}';
        }
}
