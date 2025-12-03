package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>OrderMassCancelResponse</h2>
 * <p>Response to message used to cancel multiple existing orders.</p>
 * <p>Byte length: 72</p>
 * <p>Header header - Header. | size 24</p>
 * <p>u64 > BigInteger totalAffectedOrders - Total number of orders affected by the mass cancel request. | size 8</p>
 * <p>MassCancelRequestType massCancelRequestType - Mass cancel request type. | size 1</p>
 * <p>OrderId > BigInteger (u64) massCancelId - Mass cancel request ID. | size 8</p>
 * <p>TargetPartyRole targetPartyRole - Target party role filter selection field. | size 1</p>
 * <p>u32 > long targetPartyId - Used to identify the party targeted for the action specified in the message. | size 4</p>
 * <p>ElementId > long (u32) marketSegmentId - Identifies the market segment for request type CancelOrdersForMarketSegment. | size 4</p>
 * <p>MassCancelRejectionReason reason - Reason for rejecting the given mass order cancel request. | size 2</p>
 * <p>ClientOrderId > String (u8[]) clientOrderId - Arbitrary user provided value associated with the mass cancel. | size 20</p>
 */
public class OrderMassCancelResponse implements ByteSerializable, Message {
    private Header header;
    private BigInteger totalAffectedOrders;
    private MassCancelRequestType massCancelRequestType;
    private BigInteger massCancelId;
    private TargetPartyRole targetPartyRole;
    private long targetPartyId;
    private long marketSegmentId;
    private MassCancelRejectionReason reason;
    private String clientOrderId;
    public static final int byteLength = 72;
    
    public OrderMassCancelResponse(Header header, BigInteger totalAffectedOrders, MassCancelRequestType massCancelRequestType, BigInteger massCancelId, TargetPartyRole targetPartyRole, long targetPartyId, long marketSegmentId, MassCancelRejectionReason reason, String clientOrderId) {
        this.header = header;
        this.totalAffectedOrders = totalAffectedOrders;
        this.massCancelRequestType = massCancelRequestType;
        this.massCancelId = massCancelId;
        this.targetPartyRole = targetPartyRole;
        this.targetPartyId = targetPartyId;
        this.marketSegmentId = marketSegmentId;
        this.reason = reason;
        this.clientOrderId = clientOrderId;
    }
    
    public OrderMassCancelResponse(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.totalAffectedOrders = BendecUtils.uInt64FromByteArray(bytes, offset + 24);
        this.massCancelRequestType = MassCancelRequestType.getMassCancelRequestType(bytes, offset + 32);
        this.massCancelId = BendecUtils.uInt64FromByteArray(bytes, offset + 33);
        this.targetPartyRole = TargetPartyRole.getTargetPartyRole(bytes, offset + 41);
        this.targetPartyId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.marketSegmentId = BendecUtils.uInt32FromByteArray(bytes, offset + 46);
        this.reason = MassCancelRejectionReason.getMassCancelRejectionReason(bytes, offset + 50);
        this.clientOrderId = BendecUtils.stringFromByteArray(bytes, offset + 52, 20);
    }
    
    public OrderMassCancelResponse(byte[] bytes) {
        this(bytes, 0);
    }
    
    public OrderMassCancelResponse() {
    }
    
    /**
     * @return Header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Total number of orders affected by the mass cancel request.
     */
    public BigInteger getTotalAffectedOrders() {
        return this.totalAffectedOrders;
    }
    
    /**
     * @return Mass cancel request type.
     */
    public MassCancelRequestType getMassCancelRequestType() {
        return this.massCancelRequestType;
    }
    
    /**
     * @return Mass cancel request ID.
     */
    public BigInteger getMassCancelId() {
        return this.massCancelId;
    }
    
    /**
     * @return Target party role filter selection field.
     */
    public TargetPartyRole getTargetPartyRole() {
        return this.targetPartyRole;
    }
    
    /**
     * @return Used to identify the party targeted for the action specified in the message.
     */
    public long getTargetPartyId() {
        return this.targetPartyId;
    }
    
    /**
     * @return Identifies the market segment for request type CancelOrdersForMarketSegment.
     */
    public long getMarketSegmentId() {
        return this.marketSegmentId;
    }
    
    /**
     * @return Reason for rejecting the given mass order cancel request.
     */
    public MassCancelRejectionReason getReason() {
        return this.reason;
    }
    
    /**
     * @return Arbitrary user provided value associated with the mass cancel.
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
     * @param totalAffectedOrders Total number of orders affected by the mass cancel request.
     */
    public void setTotalAffectedOrders(BigInteger totalAffectedOrders) {
        this.totalAffectedOrders = totalAffectedOrders;
    }
    
    /**
     * @param massCancelRequestType Mass cancel request type.
     */
    public void setMassCancelRequestType(MassCancelRequestType massCancelRequestType) {
        this.massCancelRequestType = massCancelRequestType;
    }
    
    /**
     * @param massCancelId Mass cancel request ID.
     */
    public void setMassCancelId(BigInteger massCancelId) {
        this.massCancelId = massCancelId;
    }
    
    /**
     * @param targetPartyRole Target party role filter selection field.
     */
    public void setTargetPartyRole(TargetPartyRole targetPartyRole) {
        this.targetPartyRole = targetPartyRole;
    }
    
    /**
     * @param targetPartyId Used to identify the party targeted for the action specified in the message.
     */
    public void setTargetPartyId(long targetPartyId) {
        this.targetPartyId = targetPartyId;
    }
    
    /**
     * @param marketSegmentId Identifies the market segment for request type CancelOrdersForMarketSegment.
     */
    public void setMarketSegmentId(long marketSegmentId) {
        this.marketSegmentId = marketSegmentId;
    }
    
    /**
     * @param reason Reason for rejecting the given mass order cancel request.
     */
    public void setReason(MassCancelRejectionReason reason) {
        this.reason = reason;
    }
    
    /**
     * @param clientOrderId Arbitrary user provided value associated with the mass cancel.
     */
    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.totalAffectedOrders));
        massCancelRequestType.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.massCancelId));
        targetPartyRole.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.targetPartyId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.marketSegmentId));
        reason.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.clientOrderId, 20));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.totalAffectedOrders));
        massCancelRequestType.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.massCancelId));
        targetPartyRole.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.targetPartyId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.marketSegmentId));
        reason.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.clientOrderId, 20));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        totalAffectedOrders,
        massCancelRequestType,
        massCancelId,
        targetPartyRole,
        targetPartyId,
        marketSegmentId,
        reason,
        clientOrderId);
    }
    
    @Override
    public String toString() {
        return "OrderMassCancelResponse {" +
            "header=" + header +
            ", totalAffectedOrders=" + totalAffectedOrders +
            ", massCancelRequestType=" + massCancelRequestType +
            ", massCancelId=" + massCancelId +
            ", targetPartyRole=" + targetPartyRole +
            ", targetPartyId=" + targetPartyId +
            ", marketSegmentId=" + marketSegmentId +
            ", reason=" + reason +
            ", clientOrderId=" + clientOrderId +
            "}";
    }
}