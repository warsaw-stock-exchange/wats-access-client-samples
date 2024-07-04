package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>Header</h2>
 * <p>Market Data message header.</p>
 * <p>Byte length: 42</p>
 * <p>MsgLength > int (u16) length - Message length. | size 2</p>
 * <p>MsgType msgType - Type of message (e.g. OrderExecute). | size 2</p>
 * <p>MsgVersion > int (u16) version - Version of the Market Data protocol. | size 2</p>
 * <p>SeqNum > long (u32) seqNum - Sequence number of the message added by the Market Data Sequencer. | size 4</p>
 * <p>Timestamp > BigInteger (u64) timestamp - Timestamp indicating when the message was sequenced. | size 8</p>
 * <p>Timestamp > BigInteger (u64) sourceTimestamp - Timestamp added by the service which sent the message. | size 8</p>
 * <p>bool > boolean isEncrypted - True if message is encrypted. | size 1</p>
 * <p>u64 > BigInteger encryptionOffset - Encryption byte offset. | size 8</p>
 * <p>ElementId > long (u32) encryptionKeyId - Reference to an EncryptionKey message. | size 4</p>
 * <p>SessionId > int (u16) sessionId - ID of the session. | size 2</p>
 * <p>StreamId > int (u8) streamId - ID of the md stream. | size 1</p>
 */
public class Header implements ByteSerializable {
    private int length;
    private MsgType msgType;
    private int version;
    private long seqNum;
    private BigInteger timestamp;
    private BigInteger sourceTimestamp;
    private boolean isEncrypted;
    private BigInteger encryptionOffset;
    private long encryptionKeyId;
    private int sessionId;
    private int streamId;
    public static final int byteLength = 42;
    
    public Header(int length, MsgType msgType, int version, long seqNum, BigInteger timestamp, BigInteger sourceTimestamp, boolean isEncrypted, BigInteger encryptionOffset, long encryptionKeyId, int sessionId, int streamId) {
        this.length = length;
        this.msgType = msgType;
        this.version = version;
        this.seqNum = seqNum;
        this.timestamp = timestamp;
        this.sourceTimestamp = sourceTimestamp;
        this.isEncrypted = isEncrypted;
        this.encryptionOffset = encryptionOffset;
        this.encryptionKeyId = encryptionKeyId;
        this.sessionId = sessionId;
        this.streamId = streamId;
    }
    
    public Header(byte[] bytes, int offset) {
        this.length = BendecUtils.uInt16FromByteArray(bytes, offset);
        this.msgType = MsgType.getMsgType(bytes, offset + 2);
        this.version = BendecUtils.uInt16FromByteArray(bytes, offset + 4);
        this.seqNum = BendecUtils.uInt32FromByteArray(bytes, offset + 6);
        this.timestamp = BendecUtils.uInt64FromByteArray(bytes, offset + 10);
        this.sourceTimestamp = BendecUtils.uInt64FromByteArray(bytes, offset + 18);
        this.isEncrypted = BendecUtils.booleanFromByteArray(bytes, offset + 26);
        this.encryptionOffset = BendecUtils.uInt64FromByteArray(bytes, offset + 27);
        this.encryptionKeyId = BendecUtils.uInt32FromByteArray(bytes, offset + 35);
        this.sessionId = BendecUtils.uInt16FromByteArray(bytes, offset + 39);
        this.streamId = BendecUtils.uInt8FromByteArray(bytes, offset + 41);
    }
    
    public Header(byte[] bytes) {
        this(bytes, 0);
    }
    
    public Header() {
    }
    
    /**
     * @return Message length.
     */
    public int getLength() {
        return this.length;
    }
    
    /**
     * @return Type of message (e.g. OrderExecute).
     */
    public MsgType getMsgType() {
        return this.msgType;
    }
    
    /**
     * @return Version of the Market Data protocol.
     */
    public int getVersion() {
        return this.version;
    }
    
    /**
     * @return Sequence number of the message added by the Market Data Sequencer.
     */
    public long getSeqNum() {
        return this.seqNum;
    }
    
    /**
     * @return Timestamp indicating when the message was sequenced.
     */
    public BigInteger getTimestamp() {
        return this.timestamp;
    }
    
    /**
     * @return Timestamp added by the service which sent the message.
     */
    public BigInteger getSourceTimestamp() {
        return this.sourceTimestamp;
    }
    
    /**
     * @return True if message is encrypted.
     */
    public boolean getIsEncrypted() {
        return this.isEncrypted;
    }
    
    /**
     * @return Encryption byte offset.
     */
    public BigInteger getEncryptionOffset() {
        return this.encryptionOffset;
    }
    
    /**
     * @return Reference to an EncryptionKey message.
     */
    public long getEncryptionKeyId() {
        return this.encryptionKeyId;
    }
    
    /**
     * @return ID of the session.
     */
    public int getSessionId() {
        return this.sessionId;
    }
    
    /**
     * @return ID of the md stream.
     */
    public int getStreamId() {
        return this.streamId;
    }
    
    /**
     * @param length Message length.
     */
    public void setLength(int length) {
        this.length = length;
    }
    
    /**
     * @param msgType Type of message (e.g. OrderExecute).
     */
    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }
    
    /**
     * @param version Version of the Market Data protocol.
     */
    public void setVersion(int version) {
        this.version = version;
    }
    
    /**
     * @param seqNum Sequence number of the message added by the Market Data Sequencer.
     */
    public void setSeqNum(long seqNum) {
        this.seqNum = seqNum;
    }
    
    /**
     * @param timestamp Timestamp indicating when the message was sequenced.
     */
    public void setTimestamp(BigInteger timestamp) {
        this.timestamp = timestamp;
    }
    
    /**
     * @param sourceTimestamp Timestamp added by the service which sent the message.
     */
    public void setSourceTimestamp(BigInteger sourceTimestamp) {
        this.sourceTimestamp = sourceTimestamp;
    }
    
    /**
     * @param isEncrypted True if message is encrypted.
     */
    public void setIsEncrypted(boolean isEncrypted) {
        this.isEncrypted = isEncrypted;
    }
    
    /**
     * @param encryptionOffset Encryption byte offset.
     */
    public void setEncryptionOffset(BigInteger encryptionOffset) {
        this.encryptionOffset = encryptionOffset;
    }
    
    /**
     * @param encryptionKeyId Reference to an EncryptionKey message.
     */
    public void setEncryptionKeyId(long encryptionKeyId) {
        this.encryptionKeyId = encryptionKeyId;
    }
    
    /**
     * @param sessionId ID of the session.
     */
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
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
        buffer.put(BendecUtils.uInt16ToByteArray(this.length));
        msgType.toBytes(buffer);
        buffer.put(BendecUtils.uInt16ToByteArray(this.version));
        buffer.put(BendecUtils.uInt32ToByteArray(this.seqNum));
        buffer.put(BendecUtils.uInt64ToByteArray(this.timestamp));
        buffer.put(BendecUtils.uInt64ToByteArray(this.sourceTimestamp));
        buffer.put(BendecUtils.booleanToByteArray(this.isEncrypted));
        buffer.put(BendecUtils.uInt64ToByteArray(this.encryptionOffset));
        buffer.put(BendecUtils.uInt32ToByteArray(this.encryptionKeyId));
        buffer.put(BendecUtils.uInt16ToByteArray(this.sessionId));
        buffer.put(BendecUtils.uInt8ToByteArray(this.streamId));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        buffer.put(BendecUtils.uInt16ToByteArray(this.length));
        msgType.toBytes(buffer);
        buffer.put(BendecUtils.uInt16ToByteArray(this.version));
        buffer.put(BendecUtils.uInt32ToByteArray(this.seqNum));
        buffer.put(BendecUtils.uInt64ToByteArray(this.timestamp));
        buffer.put(BendecUtils.uInt64ToByteArray(this.sourceTimestamp));
        buffer.put(BendecUtils.booleanToByteArray(this.isEncrypted));
        buffer.put(BendecUtils.uInt64ToByteArray(this.encryptionOffset));
        buffer.put(BendecUtils.uInt32ToByteArray(this.encryptionKeyId));
        buffer.put(BendecUtils.uInt16ToByteArray(this.sessionId));
        buffer.put(BendecUtils.uInt8ToByteArray(this.streamId));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(length,
        msgType,
        version,
        seqNum,
        timestamp,
        sourceTimestamp,
        isEncrypted,
        encryptionOffset,
        encryptionKeyId,
        sessionId,
        streamId);
    }
    
    @Override
    public String toString() {
        return "Header {" +
            "length=" + length +
            ", msgType=" + msgType +
            ", version=" + version +
            ", seqNum=" + seqNum +
            ", timestamp=" + timestamp +
            ", sourceTimestamp=" + sourceTimestamp +
            ", isEncrypted=" + isEncrypted +
            ", encryptionOffset=" + encryptionOffset +
            ", encryptionKeyId=" + encryptionKeyId +
            ", sessionId=" + sessionId +
            ", streamId=" + streamId +
            "}";
    }
}