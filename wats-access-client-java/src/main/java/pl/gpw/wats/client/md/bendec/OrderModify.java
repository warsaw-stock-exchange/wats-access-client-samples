package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>OrderModify</h2>
 * <p>Order modified.</p>
 * <p>Byte length: 71</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) instrumentId - ID of financial instrument. | size 4</p>
 * <p>PublicOrderId > BigInteger (u64) publicOrderId - Order identifier (ID). | size 8</p>
 * <p>Price > long (i64) price - New order price. | size 8</p>
 * <p>Quantity > BigInteger (u64) quantity - New order quantity. | size 8</p>
 * <p>PriorityFlag priorityFlag - Priority loss flag. | size 1</p>
 */
public class OrderModify implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private BigInteger publicOrderId;
    private long price;
    private BigInteger quantity;
    private PriorityFlag priorityFlag;
    public static final int byteLength = 71;
    
    public OrderModify(Header header, long instrumentId, BigInteger publicOrderId, long price, BigInteger quantity, PriorityFlag priorityFlag) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.publicOrderId = publicOrderId;
        this.price = price;
        this.quantity = quantity;
        this.priorityFlag = priorityFlag;
    }
    
    public OrderModify(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.publicOrderId = BendecUtils.uInt64FromByteArray(bytes, offset + 46);
        this.price = BendecUtils.int64FromByteArray(bytes, offset + 54);
        this.quantity = BendecUtils.uInt64FromByteArray(bytes, offset + 62);
        this.priorityFlag = PriorityFlag.getPriorityFlag(bytes, offset + 70);
    }
    
    public OrderModify(byte[] bytes) {
        this(bytes, 0);
    }
    
    public OrderModify() {
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
     * @return New order price.
     */
    public long getPrice() {
        return this.price;
    }
    
    /**
     * @return New order quantity.
     */
    public BigInteger getQuantity() {
        return this.quantity;
    }
    
    /**
     * @return Priority loss flag.
     */
    public PriorityFlag getPriorityFlag() {
        return this.priorityFlag;
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
     * @param price New order price.
     */
    public void setPrice(long price) {
        this.price = price;
    }
    
    /**
     * @param quantity New order quantity.
     */
    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }
    
    /**
     * @param priorityFlag Priority loss flag.
     */
    public void setPriorityFlag(PriorityFlag priorityFlag) {
        this.priorityFlag = priorityFlag;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.publicOrderId));
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        priorityFlag.toBytes(buffer);
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.publicOrderId));
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        priorityFlag.toBytes(buffer);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        publicOrderId,
        price,
        quantity,
        priorityFlag);
    }
    
    @Override
    public String toString() {
        return "OrderModify {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", publicOrderId=" + publicOrderId +
            ", price=" + price +
            ", quantity=" + quantity +
            ", priorityFlag=" + priorityFlag +
            "}";
    }
}