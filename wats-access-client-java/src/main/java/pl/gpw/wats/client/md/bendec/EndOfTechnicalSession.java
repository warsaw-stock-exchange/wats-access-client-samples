package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>EndOfTechnicalSession</h2>
 * <p>End of a technical session.</p>
 * <p>Byte length: 41</p>
 * <p>Header header - Message header. | size 39</p>
 * <p>SessionId > int (u16) sessionId - ID of the session. | size 2</p>
 * */

public class EndOfTechnicalSession implements ByteSerializable, Message {

    private Header header;
    private int sessionId;
    public static final int byteLength = 41;

    public EndOfTechnicalSession(Header header, int sessionId) {
        this.header = header;
        this.sessionId = sessionId;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.ENDOFTECHNICALSESSION);
    }

    public EndOfTechnicalSession(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.sessionId = BendecUtils.uInt16FromByteArray(bytes, offset + 39);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.ENDOFTECHNICALSESSION);
    }

    public EndOfTechnicalSession(byte[] bytes) {
        this(bytes, 0);
    }

    public EndOfTechnicalSession() {
    }



    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return ID of the session.
     */
    public int getSessionId() {
        return this.sessionId;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param sessionId ID of the session.
     */
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt16ToByteArray(this.sessionId));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt16ToByteArray(this.sessionId));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, sessionId);
    }

    @Override
    public String toString() {
        return "EndOfTechnicalSession{" +
            "header=" + header +
            ", sessionId=" + sessionId +
            '}';
        }
}
