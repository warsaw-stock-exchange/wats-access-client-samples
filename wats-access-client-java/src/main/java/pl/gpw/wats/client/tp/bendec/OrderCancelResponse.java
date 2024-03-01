package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>OrderCancelResponse</h2>
 * <p>The message is a response to an order cancel request and contains information about its execution, in particular whether the order to cancel was found or not.</p>
 * <p>Byte length: 27</p>
 * <p>Header header - Header. | size 16</p>
 * <p>OrderId > BigInteger (u64) orderId - Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID. | size 8</p>
 * <p>OrderRejectionReason reason - Reason for rejecting the given order. | size 2</p>
 * <p>OrderSource source - Response message sources can include trading ports, order cancellation mechanisms, and more. | size 1</p>
 * */

public class OrderCancelResponse implements ByteSerializable, Message {

    private Header header;
    private BigInteger orderId;
    private OrderRejectionReason reason;
    private OrderSource source;
    public static final int byteLength = 27;

    public OrderCancelResponse(Header header, BigInteger orderId, OrderRejectionReason reason, OrderSource source) {
        this.header = header;
        this.orderId = orderId;
        this.reason = reason;
        this.source = source;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.ORDERCANCELRESPONSE);
    }

    public OrderCancelResponse(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.orderId = BendecUtils.uInt64FromByteArray(bytes, offset + 16);
        this.reason = OrderRejectionReason.getOrderRejectionReason(bytes, offset + 24);
        this.source = OrderSource.getOrderSource(bytes, offset + 26);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.ORDERCANCELRESPONSE);
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
    };
    /**
     * @return Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID.
     */
    public BigInteger getOrderId() {
        return this.orderId;
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
        reason.toBytes(buffer);
        source.toBytes(buffer);
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.orderId));
        reason.toBytes(buffer);
        source.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, orderId, reason, source);
    }

    @Override
    public String toString() {
        return "OrderCancelResponse{" +
            "header=" + header +
            ", orderId=" + orderId +
            ", reason=" + reason +
            ", source=" + source +
            '}';
        }
}
