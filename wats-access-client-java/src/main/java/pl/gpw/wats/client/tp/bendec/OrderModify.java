package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>OrderModify</h2>
 * <p>Message used to modify the submitted order.</p>
 * <p>Byte length: 107</p>
 * <p>Header header - Header. | size 24</p>
 * <p>OrderId > BigInteger (u64) orderId - Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID. | size 8</p>
 * <p>Price > long (i64) price - Indicates the price of the given order. | size 8</p>
 * <p>Price > long (i64) triggerPrice - Indicates the trigger price (Last Trade Price - LTP) after which the order should be added to the order book. | size 8</p>
 * <p>Quantity > BigInteger (u64) quantity - Indicates the quantity of the instrument included in the order. | size 8</p>
 * <p>Quantity > BigInteger (u64) displayQty - Used only for iceberg order. The quantity to be displayed. | size 8</p>
 * <p>Timestamp > BigInteger (u64) expire - Expiration time indicating the validity of the order - relevant only when TimeInForce is set to GTD (Good Till Date). | size 8</p>
 * <p>MifidFields mifidFields - MifidFields structure. | size 15</p>
 * <p>ClientOrderId > String (u8[]) clientOrderId - Arbitrary user provided value associated with the order. | size 20</p>
 */
public class OrderModify implements ByteSerializable, Message {
    private Header header;
    private BigInteger orderId;
    private long price;
    private long triggerPrice;
    private BigInteger quantity;
    private BigInteger displayQty;
    private BigInteger expire;
    private MifidFields mifidFields;
    private String clientOrderId;
    public static final int byteLength = 107;
    
    public OrderModify(Header header, BigInteger orderId, long price, long triggerPrice, BigInteger quantity, BigInteger displayQty, BigInteger expire, MifidFields mifidFields, String clientOrderId) {
        this.header = header;
        this.orderId = orderId;
        this.price = price;
        this.triggerPrice = triggerPrice;
        this.quantity = quantity;
        this.displayQty = displayQty;
        this.expire = expire;
        this.mifidFields = mifidFields;
        this.clientOrderId = clientOrderId;
    }
    
    public OrderModify(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.orderId = BendecUtils.uInt64FromByteArray(bytes, offset + 24);
        this.price = BendecUtils.int64FromByteArray(bytes, offset + 32);
        this.triggerPrice = BendecUtils.int64FromByteArray(bytes, offset + 40);
        this.quantity = BendecUtils.uInt64FromByteArray(bytes, offset + 48);
        this.displayQty = BendecUtils.uInt64FromByteArray(bytes, offset + 56);
        this.expire = BendecUtils.uInt64FromByteArray(bytes, offset + 64);
        this.mifidFields = new MifidFields(bytes, offset + 72);
        this.clientOrderId = BendecUtils.stringFromByteArray(bytes, offset + 87, 20);
    }
    
    public OrderModify(byte[] bytes) {
        this(bytes, 0);
    }
    
    public OrderModify() {
    }
    
    /**
     * @return Header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID.
     */
    public BigInteger getOrderId() {
        return this.orderId;
    }
    
    /**
     * @return Indicates the price of the given order.
     */
    public long getPrice() {
        return this.price;
    }
    
    /**
     * @return Indicates the trigger price (Last Trade Price - LTP) after which the order should be added to the order book.
     */
    public long getTriggerPrice() {
        return this.triggerPrice;
    }
    
    /**
     * @return Indicates the quantity of the instrument included in the order.
     */
    public BigInteger getQuantity() {
        return this.quantity;
    }
    
    /**
     * @return Used only for iceberg order. The quantity to be displayed.
     */
    public BigInteger getDisplayQty() {
        return this.displayQty;
    }
    
    /**
     * @return Expiration time indicating the validity of the order - relevant only when TimeInForce is set to GTD (Good Till Date).
     */
    public BigInteger getExpire() {
        return this.expire;
    }
    
    /**
     * @return MifidFields structure.
     */
    public MifidFields getMifidFields() {
        return this.mifidFields;
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
     * @param orderId Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID.
     */
    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    }
    
    /**
     * @param price Indicates the price of the given order.
     */
    public void setPrice(long price) {
        this.price = price;
    }
    
    /**
     * @param triggerPrice Indicates the trigger price (Last Trade Price - LTP) after which the order should be added to the order book.
     */
    public void setTriggerPrice(long triggerPrice) {
        this.triggerPrice = triggerPrice;
    }
    
    /**
     * @param quantity Indicates the quantity of the instrument included in the order.
     */
    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }
    
    /**
     * @param displayQty Used only for iceberg order. The quantity to be displayed.
     */
    public void setDisplayQty(BigInteger displayQty) {
        this.displayQty = displayQty;
    }
    
    /**
     * @param expire Expiration time indicating the validity of the order - relevant only when TimeInForce is set to GTD (Good Till Date).
     */
    public void setExpire(BigInteger expire) {
        this.expire = expire;
    }
    
    /**
     * @param mifidFields MifidFields structure.
     */
    public void setMifidFields(MifidFields mifidFields) {
        this.mifidFields = mifidFields;
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
        buffer.put(BendecUtils.uInt64ToByteArray(this.orderId));
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.int64ToByteArray(this.triggerPrice));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        buffer.put(BendecUtils.uInt64ToByteArray(this.displayQty));
        buffer.put(BendecUtils.uInt64ToByteArray(this.expire));
        mifidFields.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.clientOrderId, 20));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.orderId));
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.int64ToByteArray(this.triggerPrice));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        buffer.put(BendecUtils.uInt64ToByteArray(this.displayQty));
        buffer.put(BendecUtils.uInt64ToByteArray(this.expire));
        mifidFields.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.clientOrderId, 20));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        orderId,
        price,
        triggerPrice,
        quantity,
        displayQty,
        expire,
        mifidFields,
        clientOrderId);
    }
    
    @Override
    public String toString() {
        return "OrderModify {" +
            "header=" + header +
            ", orderId=" + orderId +
            ", price=" + price +
            ", triggerPrice=" + triggerPrice +
            ", quantity=" + quantity +
            ", displayQty=" + displayQty +
            ", expire=" + expire +
            ", mifidFields=" + mifidFields +
            ", clientOrderId=" + clientOrderId +
            "}";
    }
}