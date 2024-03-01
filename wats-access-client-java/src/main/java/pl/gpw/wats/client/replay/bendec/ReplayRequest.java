package pl.gpw.wats.client.replay.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>ReplayRequest</h2>
 * <p>Message replay request.</p>
 * <p>Byte length: 12</p>
 * <p>ReplayHeader header - Message header. | size 4</p>
 * <p>SeqNum > long (u32) seqNum - Initial sequence number for the requested range. | size 4</p>
 * <p>SeqNum > long (u32) endSeqNum - Final sequence number for the requested range. | size 4</p>
 * */

public class ReplayRequest implements ByteSerializable, ReplayMessage {

    private ReplayHeader header;
    private long seqNum;
    private long endSeqNum;
    public static final int byteLength = 12;

    public ReplayRequest(ReplayHeader header, long seqNum, long endSeqNum) {
        this.header = header;
        this.seqNum = seqNum;
        this.endSeqNum = endSeqNum;
        this.header.setLength(this.byteLength);
        this.header.setReplayMsgType(ReplayMsgType.REPLAYREQUEST);
    }

    public ReplayRequest(byte[] bytes, int offset) {
        this.header = new ReplayHeader(bytes, offset);
        this.seqNum = BendecUtils.uInt32FromByteArray(bytes, offset + 4);
        this.endSeqNum = BendecUtils.uInt32FromByteArray(bytes, offset + 8);
        this.header.setLength(this.byteLength);
        this.header.setReplayMsgType(ReplayMsgType.REPLAYREQUEST);
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
    };
    /**
     * @return Initial sequence number for the requested range.
     */
    public long getSeqNum() {
        return this.seqNum;
    };
    /**
     * @return Final sequence number for the requested range.
     */
    public long getEndSeqNum() {
        return this.endSeqNum;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(ReplayHeader header) {
        this.header = header;
    };
    /**
     * @param seqNum Initial sequence number for the requested range.
     */
    public void setSeqNum(long seqNum) {
        this.seqNum = seqNum;
    };
    /**
     * @param endSeqNum Final sequence number for the requested range.
     */
    public void setEndSeqNum(long endSeqNum) {
        this.endSeqNum = endSeqNum;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.seqNum));
        buffer.put(BendecUtils.uInt32ToByteArray(this.endSeqNum));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.seqNum));
        buffer.put(BendecUtils.uInt32ToByteArray(this.endSeqNum));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, seqNum, endSeqNum);
    }

    @Override
    public String toString() {
        return "ReplayRequest{" +
            "header=" + header +
            ", seqNum=" + seqNum +
            ", endSeqNum=" + endSeqNum +
            '}';
        }
}
