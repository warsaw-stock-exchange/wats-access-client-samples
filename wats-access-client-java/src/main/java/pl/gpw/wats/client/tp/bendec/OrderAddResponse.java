package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>OrderAddResponse</h2>
 * <p>The message is a response to an OrderAdd message and includes the order execution status.</p>
 * <p>Byte length: 80</p>
 * <p>Header header - Header. | size 24</p>
 * <p>OrderId > BigInteger (u64) orderId - Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID. | size 8</p>
 * <p>PublicOrderId > BigInteger (u64) publicOrderId - Unique for single trading day order identifier assigned by the trading system and shared publicly in market data (public information). | size 8</p>
 * <p>Quantity > BigInteger (u64) displayQty - Used only for iceberg order. The quantity to be displayed. | size 8</p>
 * <p>Quantity > BigInteger (u64) filled - Indicates the quantity of the order which has already been filled. | size 8</p>
 * <p>OrderStatus status - Status of the given order. | size 1</p>
 * <p>OrderRejectionReason reason - Reason for rejecting the given order. | size 2</p>
 * <p>ExecTypeReason execTypeReason - Describes why an order was executed and the events related to its lifecycle. | size 1</p>
 * <p>ClientOrderId > String (u8[]) clientOrderId - Arbitrary user provided value associated with the order. | size 20</p>
 */
public class OrderAddResponse implements ByteSerializable, Message {
    private Header header;
    private BigInteger orderId;
    private BigInteger publicOrderId;
    private BigInteger displayQty;
    private BigInteger filled;
    private OrderStatus status;
    private OrderRejectionReason reason;
    private ExecTypeReason execTypeReason;
    private String clientOrderId;
    public static final int byteLength = 80;

    public OrderAddResponse(Header header, BigInteger orderId, BigInteger publicOrderId, BigInteger displayQty, BigInteger filled, OrderStatus status, OrderRejectionReason reason, ExecTypeReason execTypeReason, String clientOrderId) {
        this.header = header;
        this.orderId = orderId;
        this.publicOrderId = publicOrderId;
        this.displayQty = displayQty;
        this.filled = filled;
        this.status = status;
        this.reason = reason;
        this.execTypeReason = execTypeReason;
        this.clientOrderId = clientOrderId;
    }
    
    public OrderAddResponse(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.orderId = BendecUtils.uInt64FromByteArray(bytes, offset + 24);
        this.publicOrderId = BendecUtils.uInt64FromByteArray(bytes, offset + 32);
        this.displayQty = BendecUtils.uInt64FromByteArray(bytes, offset + 40);
        this.filled = BendecUtils.uInt64FromByteArray(bytes, offset + 48);
        this.status = OrderStatus.getOrderStatus(bytes, offset + 56);
        this.reason = OrderRejectionReason.getOrderRejectionReason(bytes, offset + 57);
        this.execTypeReason = ExecTypeReason.getExecTypeReason(bytes, offset + 59);
        this.clientOrderId = BendecUtils.stringFromByteArray(bytes, offset + 60, 20);
    }
    
    public OrderAddResponse(byte[] bytes) {
        this(bytes, 0);
    }
    
    public OrderAddResponse() {
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
     * @return Unique for single trading day order identifier assigned by the trading system and shared publicly in market data (public information).
     */
    public BigInteger getPublicOrderId() {
        return this.publicOrderId;
    }
    
    /**
     * @return Used only for iceberg order. The quantity to be displayed.
     */
    public BigInteger getDisplayQty() {
        return this.displayQty;
    }
    
    /**
     * @return Indicates the quantity of the order which has already been filled.
     */
    public BigInteger getFilled() {
        return this.filled;
    }
    
    /**
     * @return Status of the given order.
     */
    public OrderStatus getStatus() {
        return this.status;
    }
    
    /**
     * @return Reason for rejecting the given order.
     */
    public OrderRejectionReason getReason() {
        return this.reason;
    }
    
    /**
     * @return Describes why an order was executed and the events related to its lifecycle.
     */
    public ExecTypeReason getExecTypeReason() {
        return this.execTypeReason;
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
     * @param publicOrderId Unique for single trading day order identifier assigned by the trading system and shared publicly in market data (public information).
     */
    public void setPublicOrderId(BigInteger publicOrderId) {
        this.publicOrderId = publicOrderId;
    }
    
    /**
     * @param displayQty Used only for iceberg order. The quantity to be displayed.
     */
    public void setDisplayQty(BigInteger displayQty) {
        this.displayQty = displayQty;
    }
    
    /**
     * @param filled Indicates the quantity of the order which has already been filled.
     */
    public void setFilled(BigInteger filled) {
        this.filled = filled;
    }
    
    /**
     * @param status Status of the given order.
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
    /**
     * @param reason Reason for rejecting the given order.
     */
    public void setReason(OrderRejectionReason reason) {
        this.reason = reason;
    }
    
    /**
     * @param execTypeReason Describes why an order was executed and the events related to its lifecycle.
     */
    public void setExecTypeReason(ExecTypeReason execTypeReason) {
        this.execTypeReason = execTypeReason;
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
        buffer.put(BendecUtils.uInt64ToByteArray(this.publicOrderId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.displayQty));
        buffer.put(BendecUtils.uInt64ToByteArray(this.filled));
        status.toBytes(buffer);
        reason.toBytes(buffer);
        execTypeReason.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.clientOrderId, 20));
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.orderId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.publicOrderId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.displayQty));
        buffer.put(BendecUtils.uInt64ToByteArray(this.filled));
        status.toBytes(buffer);
        reason.toBytes(buffer);
        execTypeReason.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.clientOrderId, 20));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header,
        orderId,
        publicOrderId,
        displayQty,
        filled,
        status,
        reason,
        execTypeReason,
        clientOrderId);
    }
    
    @Override
    public String toString() {
        return "OrderAddResponse {" +
            "header=" + header +
            ", orderId=" + orderId +
            ", publicOrderId=" + publicOrderId +
            ", displayQty=" + displayQty +
            ", filled=" + filled +
            ", status=" + status +
            ", reason=" + reason +
            ", execTypeReason=" + execTypeReason +
            ", clientOrderId=" + clientOrderId +
            "}";
    }
}