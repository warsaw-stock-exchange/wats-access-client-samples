package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>Login</h2>
 * <p>The login message authenticates a user establishing a connection to the trading port service. The login message must be the first message sent by the client application to request the initiation of a trading port session.</p>
 * <p>Byte length: 36</p>
 * <p>Header header - Header. | size 16</p>
 * <p>MsgVersion > int (u16) version - Indicates the version of the protocol in which the message is defined. | size 2</p>
 * <p>Token > String (u8[]) token - The security data required for authentication, which is a token received by the exchange member during the registration process. | size 8</p>
 * <p>ConnectionId > int (u16) connectionId - ID of the connection. | size 2</p>
 * <p>SeqNum > long (u32) nextExpectedSeqNum - Next expected message sequence number value to be received. | size 4</p>
 * <p>SeqNum > long (u32) lastSentSeqNum - Last sent sequence number. | size 4</p>
 * */

public class Login implements ByteSerializable, Message {

    private Header header;
    private int version;
    private String token;
    private int connectionId;
    private long nextExpectedSeqNum;
    private long lastSentSeqNum;
    public static final int byteLength = 36;

    public Login(Header header, int version, String token, int connectionId, long nextExpectedSeqNum, long lastSentSeqNum) {
        this.header = header;
        this.version = version;
        this.token = token;
        this.connectionId = connectionId;
        this.nextExpectedSeqNum = nextExpectedSeqNum;
        this.lastSentSeqNum = lastSentSeqNum;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.LOGIN);
    }

    public Login(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.version = BendecUtils.uInt16FromByteArray(bytes, offset + 16);
        this.token = BendecUtils.stringFromByteArray(bytes, offset + 18, 8);
        this.connectionId = BendecUtils.uInt16FromByteArray(bytes, offset + 26);
        this.nextExpectedSeqNum = BendecUtils.uInt32FromByteArray(bytes, offset + 28);
        this.lastSentSeqNum = BendecUtils.uInt32FromByteArray(bytes, offset + 32);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.LOGIN);
    }

    public Login(byte[] bytes) {
        this(bytes, 0);
    }

    public Login() {
    }



    /**
     * @return Header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return Indicates the version of the protocol in which the message is defined.
     */
    public int getVersion() {
        return this.version;
    };
    /**
     * @return The security data required for authentication, which is a token received by the exchange member during the registration process.
     */
    public String getToken() {
        return this.token;
    };
    /**
     * @return ID of the connection.
     */
    public int getConnectionId() {
        return this.connectionId;
    };
    /**
     * @return Next expected message sequence number value to be received.
     */
    public long getNextExpectedSeqNum() {
        return this.nextExpectedSeqNum;
    };
    /**
     * @return Last sent sequence number.
     */
    public long getLastSentSeqNum() {
        return this.lastSentSeqNum;
    };

    /**
     * @param header Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param version Indicates the version of the protocol in which the message is defined.
     */
    public void setVersion(int version) {
        this.version = version;
    };
    /**
     * @param token The security data required for authentication, which is a token received by the exchange member during the registration process.
     */
    public void setToken(String token) {
        this.token = token;
    };
    /**
     * @param connectionId ID of the connection.
     */
    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    };
    /**
     * @param nextExpectedSeqNum Next expected message sequence number value to be received.
     */
    public void setNextExpectedSeqNum(long nextExpectedSeqNum) {
        this.nextExpectedSeqNum = nextExpectedSeqNum;
    };
    /**
     * @param lastSentSeqNum Last sent sequence number.
     */
    public void setLastSentSeqNum(long lastSentSeqNum) {
        this.lastSentSeqNum = lastSentSeqNum;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt16ToByteArray(this.version));
        buffer.put(BendecUtils.stringToByteArray(this.token, 8));
        buffer.put(BendecUtils.uInt16ToByteArray(this.connectionId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.nextExpectedSeqNum));
        buffer.put(BendecUtils.uInt32ToByteArray(this.lastSentSeqNum));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt16ToByteArray(this.version));
        buffer.put(BendecUtils.stringToByteArray(this.token, 8));
        buffer.put(BendecUtils.uInt16ToByteArray(this.connectionId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.nextExpectedSeqNum));
        buffer.put(BendecUtils.uInt32ToByteArray(this.lastSentSeqNum));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, version, token, connectionId, nextExpectedSeqNum, lastSentSeqNum);
    }

    @Override
    public String toString() {
        return "Login{" +
            "header=" + header +
            ", version=" + version +
            ", token=" + token +
            ", connectionId=" + connectionId +
            ", nextExpectedSeqNum=" + nextExpectedSeqNum +
            ", lastSentSeqNum=" + lastSentSeqNum +
            '}';
        }
}
