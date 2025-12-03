package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>Login</h2>
 * <p>Login message to the Snapshot service.</p>
 * <p>Byte length: 53</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ConnectionId > int (u16) connectionId - ID of the connection. | size 2</p>
 * <p>Token > String (u8[]) token - Client token. | size 8</p>
 * <p>MessageFilter filter - Indicates which message types should be sent in stream. | size 1</p>
 */
public class Login implements ByteSerializable, Message {
    private Header header;
    private int connectionId;
    private String token;
    private MessageFilter filter;
    public static final int byteLength = 53;
    
    public Login(Header header, int connectionId, String token, MessageFilter filter) {
        this.header = header;
        this.connectionId = connectionId;
        this.token = token;
        this.filter = filter;
    }
    
    public Login(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.connectionId = BendecUtils.uInt16FromByteArray(bytes, offset + 42);
        this.token = BendecUtils.stringFromByteArray(bytes, offset + 44, 8);
        this.filter = new MessageFilter(bytes, offset + 52);
    }
    
    public Login(byte[] bytes) {
        this(bytes, 0);
    }
    
    public Login() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return ID of the connection.
     */
    public int getConnectionId() {
        return this.connectionId;
    }
    
    /**
     * @return Client token.
     */
    public String getToken() {
        return this.token;
    }
    
    /**
     * @return Indicates which message types should be sent in stream.
     */
    public MessageFilter getFilter() {
        return this.filter;
    }
    
    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param connectionId ID of the connection.
     */
    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }
    
    /**
     * @param token Client token.
     */
    public void setToken(String token) {
        this.token = token;
    }
    
    /**
     * @param filter Indicates which message types should be sent in stream.
     */
    public void setFilter(MessageFilter filter) {
        this.filter = filter;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt16ToByteArray(this.connectionId));
        buffer.put(BendecUtils.stringToByteArray(this.token, 8));
        filter.toBytes(buffer);
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt16ToByteArray(this.connectionId));
        buffer.put(BendecUtils.stringToByteArray(this.token, 8));
        filter.toBytes(buffer);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        connectionId,
        token,
        filter);
    }
    
    @Override
    public String toString() {
        return "Login {" +
            "header=" + header +
            ", connectionId=" + connectionId +
            ", token=" + token +
            ", filter=" + filter +
            "}";
    }
}