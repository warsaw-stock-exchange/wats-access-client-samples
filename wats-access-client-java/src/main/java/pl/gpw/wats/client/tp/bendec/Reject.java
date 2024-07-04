package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>Reject</h2>
 * <p>The reject message is sent by the trading port service when receiving an erroneous message that cannot be further processed.</p>
 * <p>Byte length: 21</p>
 * <p>Header header - Header. | size 16</p>
 * <p>SeqNum > long (u32) refSeqNum - Sequence number of the rejected message. | size 4</p>
 * <p>RejectReason rejectReason - Reject reason. | size 1</p>
 */
public class Reject implements ByteSerializable, Message {
    private Header header;
    private long refSeqNum;
    private RejectReason rejectReason;
    public static final int byteLength = 21;
    
    public Reject(Header header, long refSeqNum, RejectReason rejectReason) {
        this.header = header;
        this.refSeqNum = refSeqNum;
        this.rejectReason = rejectReason;
    }
    
    public Reject(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.refSeqNum = BendecUtils.uInt32FromByteArray(bytes, offset + 16);
        this.rejectReason = RejectReason.getRejectReason(bytes, offset + 20);
    }
    
    public Reject(byte[] bytes) {
        this(bytes, 0);
    }
    
    public Reject() {
    }
    
    /**
     * @return Header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Sequence number of the rejected message.
     */
    public long getRefSeqNum() {
        return this.refSeqNum;
    }
    
    /**
     * @return Reject reason.
     */
    public RejectReason getRejectReason() {
        return this.rejectReason;
    }
    
    /**
     * @param header Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param refSeqNum Sequence number of the rejected message.
     */
    public void setRefSeqNum(long refSeqNum) {
        this.refSeqNum = refSeqNum;
    }
    
    /**
     * @param rejectReason Reject reason.
     */
    public void setRejectReason(RejectReason rejectReason) {
        this.rejectReason = rejectReason;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.refSeqNum));
        rejectReason.toBytes(buffer);
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.refSeqNum));
        rejectReason.toBytes(buffer);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        refSeqNum,
        rejectReason);
    }
    
    @Override
    public String toString() {
        return "Reject {" +
            "header=" + header +
            ", refSeqNum=" + refSeqNum +
            ", rejectReason=" + rejectReason +
            "}";
    }
}