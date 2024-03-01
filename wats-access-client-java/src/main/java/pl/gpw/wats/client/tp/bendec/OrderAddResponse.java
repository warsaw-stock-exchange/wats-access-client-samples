package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>OrderAddResponse</h2>
 * <p>The message is a response to an OrderAdd message and includes the order execution status.</p>
 * <p>Byte length: 36</p>
 * <p>Header header - Header. | size 16</p>
 * <p>OrderId > BigInteger (u64) orderId - Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID. | size 8</p>
 * <p>PublicOrderId > BigInteger (u64) publicOrderId - Unique for single trading day order identifier assigned by the trading system and shared publicly in market data (public information). | size 8</p>
 * <p>OrderStatus status - Status of the given order. | size 1</p>
 * <p>OrderRejectionReason reason - Reason for rejecting the given order. | size 2</p>
 * <p>OrderSource source - Response message sources can include trading ports, order cancellation mechanisms, and more. | size 1</p>
 * */

public class OrderAddResponse implements ByteSerializable, Message {

    private Header header;
    private BigInteger orderId;
    private BigInteger publicOrderId;
    private OrderStatus status;
    private OrderRejectionReason reason;
    private OrderSource source;
    public static final int byteLength = 36;

    public OrderAddResponse(Header header, BigInteger orderId, BigInteger publicOrderId, OrderStatus status, OrderRejectionReason reason, OrderSource source) {
        this.header = header;
        this.orderId = orderId;
        this.publicOrderId = publicOrderId;
        this.status = status;
        this.reason = reason;
        this.source = source;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.ORDERADDRESPONSE);
    }

    public OrderAddResponse(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.orderId = BendecUtils.uInt64FromByteArray(bytes, offset + 16);
        this.publicOrderId = BendecUtils.uInt64FromByteArray(bytes, offset + 24);
        this.status = OrderStatus.getOrderStatus(bytes, offset + 32);
        this.reason = OrderRejectionReason.getOrderRejectionReason(bytes, offset + 33);
        this.source = OrderSource.getOrderSource(bytes, offset + 35);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.ORDERADDRESPONSE);
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
    };
    /**
     * @return Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID.
     */
    public BigInteger getOrderId() {
        return this.orderId;
    };
    /**
     * @return Unique for single trading day order identifier assigned by the trading system and shared publicly in market data (public information).
     */
    public BigInteger getPublicOrderId() {
        return this.publicOrderId;
    };
    /**
     * @return Status of the given order.
     */
    public OrderStatus getStatus() {
        return this.status;
    };
    /**
     * @return Reason for rejecting the given order.
     */
    public OrderRejectionReason getReason() {
        return this.reason;
    };
    /**
     * @return Response message sources can include trading ports, order cancellation mechanisms, and more.
     */
    public OrderSource getSource() {
        return this.source;
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
     * @param publicOrderId Unique for single trading day order identifier assigned by the trading system and shared publicly in market data (public information).
     */
    public void setPublicOrderId(BigInteger publicOrderId) {
        this.publicOrderId = publicOrderId;
    };
    /**
     * @param status Status of the given order.
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    };
    /**
     * @param reason Reason for rejecting the given order.
     */
    public void setReason(OrderRejectionReason reason) {
        this.reason = reason;
    };
    /**
     * @param source Response message sources can include trading ports, order cancellation mechanisms, and more.
     */
    public void setSource(OrderSource source) {
        this.source = source;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.orderId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.publicOrderId));
        status.toBytes(buffer);
        reason.toBytes(buffer);
        source.toBytes(buffer);
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.orderId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.publicOrderId));
        status.toBytes(buffer);
        reason.toBytes(buffer);
        source.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, orderId, publicOrderId, status, reason, source);
    }

    @Override
    public String toString() {
        return "OrderAddResponse{" +
            "header=" + header +
            ", orderId=" + orderId +
            ", publicOrderId=" + publicOrderId +
            ", status=" + status +
            ", reason=" + reason +
            ", source=" + source +
            '}';
        }
}
