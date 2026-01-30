package pl.gpw.wats.client.replay.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>ReplayRequest</h2>
 * <p>Message replay request.</p>
 * <p>Byte length: 13</p>
 * <p>ReplayHeader header - Message header. | size 4</p>
 * <p>SeqNum > long (u32) seqNum - Initial sequence number for the requested range. | size 4</p>
 * <p>SeqNum > long (u32) endSeqNum - Final sequence number for the requested range. | size 4</p>
 * <p>StreamId > int (u8) streamId - ID of the md stream. | size 1</p>
 */
public class ReplayRequest implements ByteSerializable, ReplayMessage {
    private ReplayHeader header;
    private long seqNum;
    private long endSeqNum;
    private int streamId;
    public static final int byteLength = 13;

    public ReplayRequest(ReplayHeader header, long seqNum, long endSeqNum, int streamId) {
        this.header = header;
        this.seqNum = seqNum;
        this.endSeqNum = endSeqNum;
        this.streamId = streamId;
    }
    
    public ReplayRequest(byte[] bytes, int offset) {
        this.header = new ReplayHeader(bytes, offset);
        this.seqNum = BendecUtils.uInt32FromByteArray(bytes, offset + 4);
        this.endSeqNum = BendecUtils.uInt32FromByteArray(bytes, offset + 8);
        this.streamId = BendecUtils.uInt8FromByteArray(bytes, offset + 12);
    }
    
    public ReplayRequest(byte[] bytes) {
        this(bytes, 0);
    }
    
    public ReplayRequest() {
    }
    
    /**
     * @return Message header.
     */
    public ReplayHeader getHeader() {
        return this.header;
    }
    
    /**
     * @return Initial sequence number for the requested range.
     */
    public long getSeqNum() {
        return this.seqNum;
    }
    
    /**
     * @return Final sequence number for the requested range.
     */
    public long getEndSeqNum() {
        return this.endSeqNum;
    }
    
    /**
     * @return ID of the md stream.
     */
    public int getStreamId() {
        return this.streamId;
    }

    /**
     * @param header Message header.
     */
    public void setHeader(ReplayHeader header) {
        this.header = header;
    }
    
    /**
     * @param seqNum Initial sequence number for the requested range.
     */
    public void setSeqNum(long seqNum) {
        this.seqNum = seqNum;
    }
    
    /**
     * @param endSeqNum Final sequence number for the requested range.
     */
    public void setEndSeqNum(long endSeqNum) {
        this.endSeqNum = endSeqNum;
    }
    
    /**
     * @param streamId ID of the md stream.
     */
    public void setStreamId(int streamId) {
        this.streamId = streamId;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.seqNum));
        buffer.put(BendecUtils.uInt32ToByteArray(this.endSeqNum));
        buffer.put(BendecUtils.uInt8ToByteArray(this.streamId));
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.seqNum));
        buffer.put(BendecUtils.uInt32ToByteArray(this.endSeqNum));
        buffer.put(BendecUtils.uInt8ToByteArray(this.streamId));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header,
        seqNum,
        endSeqNum,
        streamId);
    }
    
    @Override
    public String toString() {
        return "ReplayRequest {" +
            "header=" + header +
            ", seqNum=" + seqNum +
            ", endSeqNum=" + endSeqNum +
            ", streamId=" + streamId +
            "}";
    }
}