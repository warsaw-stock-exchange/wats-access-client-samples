package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>Header</h2>
 * <p>Header used in trading port messages.</p>
 * <p>Byte length: 16</p>
 * <p>MsgLength > int (u16) length - Total length of the message. | size 2</p>
 * <p>MsgType msgType - Type of the message (e.g. Login). | size 2</p>
 * <p>SeqNum > long (u32) seqNum - Sequence number of the message added by the sender. | size 4</p>
 * <p>Timestamp > BigInteger (u64) timestamp - Processing time. | size 8</p>
 */
public class Header implements ByteSerializable {
    private int length;
    private MsgType msgType;
    private long seqNum;
    private BigInteger timestamp;
    public static final int byteLength = 16;
    
    public Header(int length, MsgType msgType, long seqNum, BigInteger timestamp) {
        this.length = length;
        this.msgType = msgType;
        this.seqNum = seqNum;
        this.timestamp = timestamp;
    }
    
    public Header(byte[] bytes, int offset) {
        this.length = BendecUtils.uInt16FromByteArray(bytes, offset);
        this.msgType = MsgType.getMsgType(bytes, offset + 2);
        this.seqNum = BendecUtils.uInt32FromByteArray(bytes, offset + 4);
        this.timestamp = BendecUtils.uInt64FromByteArray(bytes, offset + 8);
    }
    
    public Header(byte[] bytes) {
        this(bytes, 0);
    }
    
    public Header() {
    }
    
    /**
     * @return Total length of the message.
     */
    public int getLength() {
        return this.length;
    }
    
    /**
     * @return Type of the message (e.g. Login).
     */
    public MsgType getMsgType() {
        return this.msgType;
    }
    
    /**
     * @return Sequence number of the message added by the sender.
     */
    public long getSeqNum() {
        return this.seqNum;
    }
    
    /**
     * @return Processing time.
     */
    public BigInteger getTimestamp() {
        return this.timestamp;
    }
    
    /**
     * @param length Total length of the message.
     */
    public void setLength(int length) {
        this.length = length;
    }
    
    /**
     * @param msgType Type of the message (e.g. Login).
     */
    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }
    
    /**
     * @param seqNum Sequence number of the message added by the sender.
     */
    public void setSeqNum(long seqNum) {
        this.seqNum = seqNum;
    }
    
    /**
     * @param timestamp Processing time.
     */
    public void setTimestamp(BigInteger timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        buffer.put(BendecUtils.uInt16ToByteArray(this.length));
        msgType.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.seqNum));
        buffer.put(BendecUtils.uInt64ToByteArray(this.timestamp));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        buffer.put(BendecUtils.uInt16ToByteArray(this.length));
        msgType.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.seqNum));
        buffer.put(BendecUtils.uInt64ToByteArray(this.timestamp));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(length,
        msgType,
        seqNum,
        timestamp);
    }
    
    @Override
    public String toString() {
        return "Header {" +
            "length=" + length +
            ", msgType=" + msgType +
            ", seqNum=" + seqNum +
            ", timestamp=" + timestamp +
            "}";
    }
}