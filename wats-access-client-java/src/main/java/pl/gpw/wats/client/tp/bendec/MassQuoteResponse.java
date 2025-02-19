package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>MassQuoteResponse</h2>
 * <p>The response to a MassQuote message.</p>
 * <p>Byte length: 719</p>
 * <p>Header header - Message header. | size 16</p>
 * <p>OrderId > BigInteger (u64) massQuoteId - Quote id | size 8</p>
 * <p>QuoteOrderResponses responses - The slice of responses. | size 691</p>
 * <p>MassQuoteStatus status - Status of the given mass quote order. | size 1</p>
 * <p>MassQuoteRejectionReason reason - Reason for rejecting the given mass quote order. | size 2</p>
 * <p>u8 > int feeStructureId - Optional identifier of a fee scheme for billing purposes. | size 1</p>
 */
public class MassQuoteResponse implements ByteSerializable, Message {
    private Header header;
    private BigInteger massQuoteId;
    private QuoteOrderResponses responses;
    private MassQuoteStatus status;
    private MassQuoteRejectionReason reason;
    private int feeStructureId;
    public static final int byteLength = 719;
    
    public MassQuoteResponse(Header header, BigInteger massQuoteId, QuoteOrderResponses responses, MassQuoteStatus status, MassQuoteRejectionReason reason, int feeStructureId) {
        this.header = header;
        this.massQuoteId = massQuoteId;
        this.responses = responses;
        this.status = status;
        this.reason = reason;
        this.feeStructureId = feeStructureId;
    }
    
    public MassQuoteResponse(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.massQuoteId = BendecUtils.uInt64FromByteArray(bytes, offset + 16);
        this.responses = new QuoteOrderResponses(bytes, offset + 24);
        this.status = MassQuoteStatus.getMassQuoteStatus(bytes, offset + 715);
        this.reason = MassQuoteRejectionReason.getMassQuoteRejectionReason(bytes, offset + 716);
        this.feeStructureId = BendecUtils.uInt8FromByteArray(bytes, offset + 718);
    }
    
    public MassQuoteResponse(byte[] bytes) {
        this(bytes, 0);
    }
    
    public MassQuoteResponse() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Quote id
     */
    public BigInteger getMassQuoteId() {
        return this.massQuoteId;
    }
    
    /**
     * @return The slice of responses.
     */
    public QuoteOrderResponses getResponses() {
        return this.responses;
    }
    
    /**
     * @return Status of the given mass quote order.
     */
    public MassQuoteStatus getStatus() {
        return this.status;
    }
    
    /**
     * @return Reason for rejecting the given mass quote order.
     */
    public MassQuoteRejectionReason getReason() {
        return this.reason;
    }
    
    /**
     * @return Optional identifier of a fee scheme for billing purposes.
     */
    public int getFeeStructureId() {
        return this.feeStructureId;
    }
    
    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param massQuoteId Quote id
     */
    public void setMassQuoteId(BigInteger massQuoteId) {
        this.massQuoteId = massQuoteId;
    }
    
    /**
     * @param responses The slice of responses.
     */
    public void setResponses(QuoteOrderResponses responses) {
        this.responses = responses;
    }
    
    /**
     * @param status Status of the given mass quote order.
     */
    public void setStatus(MassQuoteStatus status) {
        this.status = status;
    }
    
    /**
     * @param reason Reason for rejecting the given mass quote order.
     */
    public void setReason(MassQuoteRejectionReason reason) {
        this.reason = reason;
    }
    
    /**
     * @param feeStructureId Optional identifier of a fee scheme for billing purposes.
     */
    public void setFeeStructureId(int feeStructureId) {
        this.feeStructureId = feeStructureId;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.massQuoteId));
        responses.toBytes(buffer);
        status.toBytes(buffer);
        reason.toBytes(buffer);
        buffer.put(BendecUtils.uInt8ToByteArray(this.feeStructureId));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.massQuoteId));
        responses.toBytes(buffer);
        status.toBytes(buffer);
        reason.toBytes(buffer);
        buffer.put(BendecUtils.uInt8ToByteArray(this.feeStructureId));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        massQuoteId,
        responses,
        status,
        reason,
        feeStructureId);
    }
    
    @Override
    public String toString() {
        return "MassQuoteResponse {" +
            "header=" + header +
            ", massQuoteId=" + massQuoteId +
            ", responses=" + responses +
            ", status=" + status +
            ", reason=" + reason +
            ", feeStructureId=" + feeStructureId +
            "}";
    }
}