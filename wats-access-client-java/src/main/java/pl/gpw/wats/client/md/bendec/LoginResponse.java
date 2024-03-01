package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>LoginResponse</h2>
 * <p>Response to a Login message.</p>
 * <p>Byte length: 40</p>
 * <p>Header header - Message header. | size 39</p>
 * <p>LoginResult result - see the LoginResult description. | size 1</p>
 * */

public class LoginResponse implements ByteSerializable, Message {

    private Header header;
    private LoginResult result;
    public static final int byteLength = 40;

    public LoginResponse(Header header, LoginResult result) {
        this.header = header;
        this.result = result;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.LOGINRESPONSE);
    }

    public LoginResponse(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.result = LoginResult.getLoginResult(bytes, offset + 39);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.LOGINRESPONSE);
    }

    public LoginResponse(byte[] bytes) {
        this(bytes, 0);
    }

    public LoginResponse() {
    }



    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return see the LoginResult description.
     */
    public LoginResult getResult() {
        return this.result;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param result see the LoginResult description.
     */
    public void setResult(LoginResult result) {
        this.result = result;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        result.toBytes(buffer);
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        result.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, result);
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
            "header=" + header +
            ", result=" + result +
            '}';
        }
}
