package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>OrderCancelResponse</h2>
 * <p>The message is a response to an order cancel request and contains information about its execution, in particular whether the order to cancel was found or not.</p>
 * <p>Byte length: 56</p>
 * <p>Header header - Header. | size 24</p>
 * <p>OrderId > BigInteger (u64) orderId - Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID. | size 8</p>
 * <p>OrderStatus status - Status of the given order. | size 1</p>
 * <p>OrderRejectionReason reason - Reason for rejecting the given order. | size 2</p>
 * <p>ExecTypeReason execTypeReason - Describes why an order was executed and the events related to its lifecycle. | size 1</p>
 * <p>ClientOrderId > String (u8[]) clientOrderId - Arbitrary user provided value associated with the order. | size 20</p>
 */
public class OrderCancelResponse implements ByteSerializable, Message {
    private Header header;
    private BigInteger orderId;
    private OrderStatus status;
    private OrderRejectionReason reason;
    private ExecTypeReason execTypeReason;
    private String clientOrderId;
    public static final int byteLength = 56;
    
    public OrderCancelResponse(Header header, BigInteger orderId, OrderStatus status, OrderRejectionReason reason, ExecTypeReason execTypeReason, String clientOrderId) {
        this.header = header;
        this.orderId = orderId;
        this.status = status;
        this.reason = reason;
        this.execTypeReason = execTypeReason;
        this.clientOrderId = clientOrderId;
    }
    
    public OrderCancelResponse(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.orderId = BendecUtils.uInt64FromByteArray(bytes, offset + 24);
        this.status = OrderStatus.getOrderStatus(bytes, offset + 32);
        this.reason = OrderRejectionReason.getOrderRejectionReason(bytes, offset + 33);
        this.execTypeReason = ExecTypeReason.getExecTypeReason(bytes, offset + 35);
        this.clientOrderId = BendecUtils.stringFromByteArray(bytes, offset + 36, 20);
    }
    
    public OrderCancelResponse(byte[] bytes) {
        this(bytes, 0);
    }
    
    public OrderCancelResponse() {
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
        status.toBytes(buffer);
        reason.toBytes(buffer);
        execTypeReason.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.clientOrderId, 20));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        orderId,
        status,
        reason,
        execTypeReason,
        clientOrderId);
    }
    
    @Override
    public String toString() {
        return "OrderCancelResponse {" +
            "header=" + header +
            ", orderId=" + orderId +
            ", status=" + status +
            ", reason=" + reason +
            ", execTypeReason=" + execTypeReason +
            ", clientOrderId=" + clientOrderId +
            "}";
    }
}