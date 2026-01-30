package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>QuoteOrderResponse</h2>
 * <p>The message is a response to a single Quote and includes the order execution status.</p>
 * <p>Byte length: 23</p>
 * <p>ElementId > long (u32) instrumentId - ID of the instrument being traded. | size 4</p>
 * <p>OrderId > BigInteger (u64) bidOrderId - Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID. | size 8</p>
 * <p>OrderId > BigInteger (u64) askOrderId - Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID. | size 8</p>
 * <p>OrderStatus status - Status of the given order. | size 1</p>
 * <p>OrderRejectionReason reason - Reason for rejecting the given order. | size 2</p>
 */
public class QuoteOrderResponse implements ByteSerializable {
    private long instrumentId;
    private BigInteger bidOrderId;
    private BigInteger askOrderId;
    private OrderStatus status;
    private OrderRejectionReason reason;
    public static final int byteLength = 23;

    public QuoteOrderResponse(long instrumentId, BigInteger bidOrderId, BigInteger askOrderId, OrderStatus status, OrderRejectionReason reason) {
        this.instrumentId = instrumentId;
        this.bidOrderId = bidOrderId;
        this.askOrderId = askOrderId;
        this.status = status;
        this.reason = reason;
    }
    
    public QuoteOrderResponse(byte[] bytes, int offset) {
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset);
        this.bidOrderId = BendecUtils.uInt64FromByteArray(bytes, offset + 4);
        this.askOrderId = BendecUtils.uInt64FromByteArray(bytes, offset + 12);
        this.status = OrderStatus.getOrderStatus(bytes, offset + 20);
        this.reason = OrderRejectionReason.getOrderRejectionReason(bytes, offset + 21);
    }
    
    public QuoteOrderResponse(byte[] bytes) {
        this(bytes, 0);
    }
    
    public QuoteOrderResponse() {
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
    public BigInteger getBidOrderId() {
        return this.bidOrderId;
    }
    
    /**
     * @return Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID.
     */
    public BigInteger getAskOrderId() {
        return this.askOrderId;
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
     * @param instrumentId ID of the instrument being traded.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }
    
    /**
     * @param bidOrderId Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID.
     */
    public void setBidOrderId(BigInteger bidOrderId) {
        this.bidOrderId = bidOrderId;
    }
    
    /**
     * @param askOrderId Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID.
     */
    public void setAskOrderId(BigInteger askOrderId) {
        this.askOrderId = askOrderId;
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

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.bidOrderId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.askOrderId));
        status.toBytes(buffer);
        reason.toBytes(buffer);
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.bidOrderId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.askOrderId));
        status.toBytes(buffer);
        reason.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrumentId,
        bidOrderId,
        askOrderId,
        status,
        reason);
    }
    
    @Override
    public String toString() {
        return "QuoteOrderResponse {" +
            "instrumentId=" + instrumentId +
            ", bidOrderId=" + bidOrderId +
            ", askOrderId=" + askOrderId +
            ", status=" + status +
            ", reason=" + reason +
            "}";
    }
}