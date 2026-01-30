package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>OrderModifyResponse</h2>
 * <p>The response message for an OrderModify.</p>
 * <p>Byte length: 64</p>
 * <p>Header header - Header. | size 24</p>
 * <p>OrderId > BigInteger (u64) orderId - Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID. | size 8</p>
 * <p>Quantity > BigInteger (u64) filled - Indicates the quantity of the order which has already been filled. | size 8</p>
 * <p>OrderStatus status - Status of the given order. | size 1</p>
 * <p>PriorityFlag priorityFlag - Indicates whether the priority flag is lost or retained after modification. | size 1</p>
 * <p>OrderRejectionReason reason - Reason for rejecting the given order. | size 2</p>
 * <p>ClientOrderId > String (u8[]) clientOrderId - Arbitrary user provided value associated with the order. | size 20</p>
 */
public class OrderModifyResponse implements ByteSerializable, Message {
    private Header header;
    private BigInteger orderId;
    private BigInteger filled;
    private OrderStatus status;
    private PriorityFlag priorityFlag;
    private OrderRejectionReason reason;
    private String clientOrderId;
    public static final int byteLength = 64;

    public OrderModifyResponse(Header header, BigInteger orderId, BigInteger filled, OrderStatus status, PriorityFlag priorityFlag, OrderRejectionReason reason, String clientOrderId) {
        this.header = header;
        this.orderId = orderId;
        this.filled = filled;
        this.status = status;
        this.priorityFlag = priorityFlag;
        this.reason = reason;
        this.clientOrderId = clientOrderId;
    }
    
    public OrderModifyResponse(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.orderId = BendecUtils.uInt64FromByteArray(bytes, offset + 24);
        this.filled = BendecUtils.uInt64FromByteArray(bytes, offset + 32);
        this.status = OrderStatus.getOrderStatus(bytes, offset + 40);
        this.priorityFlag = PriorityFlag.getPriorityFlag(bytes, offset + 41);
        this.reason = OrderRejectionReason.getOrderRejectionReason(bytes, offset + 42);
        this.clientOrderId = BendecUtils.stringFromByteArray(bytes, offset + 44, 20);
    }
    
    public OrderModifyResponse(byte[] bytes) {
        this(bytes, 0);
    }
    
    public OrderModifyResponse() {
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
     * @return Indicates whether the priority flag is lost or retained after modification.
     */
    public PriorityFlag getPriorityFlag() {
        return this.priorityFlag;
    }
    
    /**
     * @return Reason for rejecting the given order.
     */
    public OrderRejectionReason getReason() {
        return this.reason;
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
     * @param priorityFlag Indicates whether the priority flag is lost or retained after modification.
     */
    public void setPriorityFlag(PriorityFlag priorityFlag) {
        this.priorityFlag = priorityFlag;
    }
    
    /**
     * @param reason Reason for rejecting the given order.
     */
    public void setReason(OrderRejectionReason reason) {
        this.reason = reason;
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
        buffer.put(BendecUtils.uInt64ToByteArray(this.filled));
        status.toBytes(buffer);
        priorityFlag.toBytes(buffer);
        reason.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.clientOrderId, 20));
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.orderId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.filled));
        status.toBytes(buffer);
        priorityFlag.toBytes(buffer);
        reason.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.clientOrderId, 20));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header,
        orderId,
        filled,
        status,
        priorityFlag,
        reason,
        clientOrderId);
    }
    
    @Override
    public String toString() {
        return "OrderModifyResponse {" +
            "header=" + header +
            ", orderId=" + orderId +
            ", filled=" + filled +
            ", status=" + status +
            ", priorityFlag=" + priorityFlag +
            ", reason=" + reason +
            ", clientOrderId=" + clientOrderId +
            "}";
    }
}