package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>EndOfSnapshot</h2>
 * <p>Marks the end of a snapshot.</p>
 * <p>Byte length: 43</p>
 * <p>Header header - Message header. | size 39</p>
 * <p>SeqNum > long (u32) lastSeqNum - Sequence number of the last Market Data message transmitted by the Snapshot. | size 4</p>
 * */

public class EndOfSnapshot implements ByteSerializable, Message {

    private Header header;
    private long lastSeqNum;
    public static final int byteLength = 43;

    public EndOfSnapshot(Header header, long lastSeqNum) {
        this.header = header;
        this.lastSeqNum = lastSeqNum;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.ENDOFSNAPSHOT);
    }

    public EndOfSnapshot(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.lastSeqNum = BendecUtils.uInt32FromByteArray(bytes, offset + 39);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.ENDOFSNAPSHOT);
    }

    public EndOfSnapshot(byte[] bytes) {
        this(bytes, 0);
    }

    public EndOfSnapshot() {
    }



    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return Sequence number of the last Market Data message transmitted by the Snapshot.
     */
    public long getLastSeqNum() {
        return this.lastSeqNum;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param lastSeqNum Sequence number of the last Market Data message transmitted by the Snapshot.
     */
    public void setLastSeqNum(long lastSeqNum) {
        this.lastSeqNum = lastSeqNum;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.lastSeqNum));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.lastSeqNum));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, lastSeqNum);
    }

    @Override
    public String toString() {
        return "EndOfSnapshot{" +
            "header=" + header +
            ", lastSeqNum=" + lastSeqNum +
            '}';
        }
}
