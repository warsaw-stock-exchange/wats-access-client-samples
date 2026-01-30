package pl.gpw.wats.client.replay.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>ReplayHeader</h2>
 * <p>Market Data message header.</p>
 * <p>Byte length: 4</p>
 * <p>MsgLength > int (u16) length - Message length. | size 2</p>
 * <p>ReplayMsgType replayMsgType - Type of the message. | size 2</p>
 */
public class ReplayHeader implements ByteSerializable {
    private int length;
    private ReplayMsgType replayMsgType;
    public static final int byteLength = 4;

    public ReplayHeader(int length, ReplayMsgType replayMsgType) {
        this.length = length;
        this.replayMsgType = replayMsgType;
    }
    
    public ReplayHeader(byte[] bytes, int offset) {
        this.length = BendecUtils.uInt16FromByteArray(bytes, offset);
        this.replayMsgType = ReplayMsgType.getReplayMsgType(bytes, offset + 2);
    }
    
    public ReplayHeader(byte[] bytes) {
        this(bytes, 0);
    }
    
    public ReplayHeader() {
    }
    
    /**
     * @return Message length.
     */
    public int getLength() {
        return this.length;
    }
    
    /**
     * @return Type of the message.
     */
    public ReplayMsgType getReplayMsgType() {
        return this.replayMsgType;
    }

    /**
     * @param length Message length.
     */
    public void setLength(int length) {
        this.length = length;
    }
    
    /**
     * @param replayMsgType Type of the message.
     */
    public void setReplayMsgType(ReplayMsgType replayMsgType) {
        this.replayMsgType = replayMsgType;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        buffer.put(BendecUtils.uInt16ToByteArray(this.length));
        replayMsgType.toBytes(buffer);
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        buffer.put(BendecUtils.uInt16ToByteArray(this.length));
        replayMsgType.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(length,
        replayMsgType);
    }
    
    @Override
    public String toString() {
        return "ReplayHeader {" +
            "length=" + length +
            ", replayMsgType=" + replayMsgType +
            "}";
    }
}