package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>LoginResponse</h2>
 * <p>Response to a Login message.</p>
 * <p>Byte length: 43</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>LoginResult result - see the LoginResult description. | size 1</p>
 */
public class LoginResponse implements ByteSerializable, Message {
    private Header header;
    private LoginResult result;
    public static final int byteLength = 43;

    public LoginResponse(Header header, LoginResult result) {
        this.header = header;
        this.result = result;
    }
    
    public LoginResponse(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.result = LoginResult.getLoginResult(bytes, offset + 42);
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
    }
    
    /**
     * @return see the LoginResult description.
     */
    public LoginResult getResult() {
        return this.result;
    }

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param result see the LoginResult description.
     */
    public void setResult(LoginResult result) {
        this.result = result;
    }

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
        return Objects.hash(header,
        result);
    }
    
    @Override
    public String toString() {
        return "LoginResponse {" +
            "header=" + header +
            ", result=" + result +
            "}";
    }
}