package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>LoginResponse</h2>
 * <p>The login response message. The result field describes the login status, indicating whether the login was successful or not (i.e. successful login).</p>
 * <p>Byte length: 35</p>
 * <p>Header header - Header. | size 24</p>
 * <p>LoginResult result - Login response status code. | size 1</p>
 * <p>SeqNum > long (u32) nextExpectedSeqNum - Next expected message sequence number value to be received. | size 4</p>
 * <p>SeqNum > long (u32) lastReplaySeqNum - Last replay sequence number. | size 4</p>
 * <p>SessionId > int (u16) sessionId - ID of the session. | size 2</p>
 */
public class LoginResponse implements ByteSerializable, Message {
    private Header header;
    private LoginResult result;
    private long nextExpectedSeqNum;
    private long lastReplaySeqNum;
    private int sessionId;
    public static final int byteLength = 35;

    public LoginResponse(Header header, LoginResult result, long nextExpectedSeqNum, long lastReplaySeqNum, int sessionId) {
        this.header = header;
        this.result = result;
        this.nextExpectedSeqNum = nextExpectedSeqNum;
        this.lastReplaySeqNum = lastReplaySeqNum;
        this.sessionId = sessionId;
    }
    
    public LoginResponse(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.result = LoginResult.getLoginResult(bytes, offset + 24);
        this.nextExpectedSeqNum = BendecUtils.uInt32FromByteArray(bytes, offset + 25);
        this.lastReplaySeqNum = BendecUtils.uInt32FromByteArray(bytes, offset + 29);
        this.sessionId = BendecUtils.uInt16FromByteArray(bytes, offset + 33);
    }
    
    public LoginResponse(byte[] bytes) {
        this(bytes, 0);
    }
    
    public LoginResponse() {
    }
    
    /**
     * @return Header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Login response status code.
     */
    public LoginResult getResult() {
        return this.result;
    }
    
    /**
     * @return Next expected message sequence number value to be received.
     */
    public long getNextExpectedSeqNum() {
        return this.nextExpectedSeqNum;
    }
    
    /**
     * @return Last replay sequence number.
     */
    public long getLastReplaySeqNum() {
        return this.lastReplaySeqNum;
    }
    
    /**
     * @return ID of the session.
     */
    public int getSessionId() {
        return this.sessionId;
    }

    /**
     * @param header Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param result Login response status code.
     */
    public void setResult(LoginResult result) {
        this.result = result;
    }
    
    /**
     * @param nextExpectedSeqNum Next expected message sequence number value to be received.
     */
    public void setNextExpectedSeqNum(long nextExpectedSeqNum) {
        this.nextExpectedSeqNum = nextExpectedSeqNum;
    }
    
    /**
     * @param lastReplaySeqNum Last replay sequence number.
     */
    public void setLastReplaySeqNum(long lastReplaySeqNum) {
        this.lastReplaySeqNum = lastReplaySeqNum;
    }
    
    /**
     * @param sessionId ID of the session.
     */
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        result.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.nextExpectedSeqNum));
        buffer.put(BendecUtils.uInt32ToByteArray(this.lastReplaySeqNum));
        buffer.put(BendecUtils.uInt16ToByteArray(this.sessionId));
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        result.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.nextExpectedSeqNum));
        buffer.put(BendecUtils.uInt32ToByteArray(this.lastReplaySeqNum));
        buffer.put(BendecUtils.uInt16ToByteArray(this.sessionId));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header,
        result,
        nextExpectedSeqNum,
        lastReplaySeqNum,
        sessionId);
    }
    
    @Override
    public String toString() {
        return "LoginResponse {" +
            "header=" + header +
            ", result=" + result +
            ", nextExpectedSeqNum=" + nextExpectedSeqNum +
            ", lastReplaySeqNum=" + lastReplaySeqNum +
            ", sessionId=" + sessionId +
            "}";
    }
}