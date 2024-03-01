package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>OrderModifyResponse</h2>
 * <p>The response message for an OrderModify.</p>
 * <p>Byte length: 28</p>
 * <p>Header header - Header. | size 16</p>
 * <p>OrderId > BigInteger (u64) orderId - Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID. | size 8</p>
 * <p>OrderStatus status - Status of the given order. | size 1</p>
 * <p>PriorityFlag priorityFlag - Indicates whether the priority flag is lost or retained after modification. | size 1</p>
 * <p>OrderRejectionReason reason - Reason for rejecting the given order. | size 2</p>
 * */

public class OrderModifyResponse implements ByteSerializable, Message {

    private Header header;
    private BigInteger orderId;
    private OrderStatus status;
    private PriorityFlag priorityFlag;
    private OrderRejectionReason reason;
    public static final int byteLength = 28;

    public OrderModifyResponse(Header header, BigInteger orderId, OrderStatus status, PriorityFlag priorityFlag, OrderRejectionReason reason) {
        this.header = header;
        this.orderId = orderId;
        this.status = status;
        this.priorityFlag = priorityFlag;
        this.reason = reason;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.ORDERMODIFYRESPONSE);
    }

    public OrderModifyResponse(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.orderId = BendecUtils.uInt64FromByteArray(bytes, offset + 16);
        this.status = OrderStatus.getOrderStatus(bytes, offset + 24);
        this.priorityFlag = PriorityFlag.getPriorityFlag(bytes, offset + 25);
        this.reason = OrderRejectionReason.getOrderRejectionReason(bytes, offset + 26);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.ORDERMODIFYRESPONSE);
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
    };
    /**
     * @return Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID.
     */
    public BigInteger getOrderId() {
        return this.orderId;
    };
    /**
     * @return Status of the given order.
     */
    public OrderStatus getStatus() {
        return this.status;
    };
    /**
     * @return Indicates whether the priority flag is lost or retained after modification.
     */
    public PriorityFlag getPriorityFlag() {
        return this.priorityFlag;
    };
    /**
     * @return Reason for rejecting the given order.
     */
    public OrderRejectionReason getReason() {
        return this.reason;
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
     * @param status Status of the given order.
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    };
    /**
     * @param priorityFlag Indicates whether the priority flag is lost or retained after modification.
     */
    public void setPriorityFlag(PriorityFlag priorityFlag) {
        this.priorityFlag = priorityFlag;
    };
    /**
     * @param reason Reason for rejecting the given order.
     */
    public void setReason(OrderRejectionReason reason) {
        this.reason = reason;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.orderId));
        status.toBytes(buffer);
        priorityFlag.toBytes(buffer);
        reason.toBytes(buffer);
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.orderId));
        status.toBytes(buffer);
        priorityFlag.toBytes(buffer);
        reason.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, orderId, status, priorityFlag, reason);
    }

    @Override
    public String toString() {
        return "OrderModifyResponse{" +
            "header=" + header +
            ", orderId=" + orderId +
            ", status=" + status +
            ", priorityFlag=" + priorityFlag +
            ", reason=" + reason +
            '}';
        }
}
