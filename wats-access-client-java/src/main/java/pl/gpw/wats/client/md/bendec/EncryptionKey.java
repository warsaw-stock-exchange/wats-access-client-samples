package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>EncryptionKey</h2>
 * <p>Encryption key and ID.</p>
 * <p>Byte length: 78</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) id - Encryption key ID. | size 4</p>
 * <p>EncryptionGroupKey > String (u8[]) secretKey - Secret Key value. | size 32</p>
 */
public class EncryptionKey implements ByteSerializable, Message {
    private Header header;
    private long id;
    private String secretKey;
    public static final int byteLength = 78;

    public EncryptionKey(Header header, long id, String secretKey) {
        this.header = header;
        this.id = id;
        this.secretKey = secretKey;
    }
    
    public EncryptionKey(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.id = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.secretKey = BendecUtils.stringFromByteArray(bytes, offset + 46, 32);
    }
    
    public EncryptionKey(byte[] bytes) {
        this(bytes, 0);
    }
    
    public EncryptionKey() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Encryption key ID.
     */
    public long getId() {
        return this.id;
    }
    
    /**
     * @return Secret Key value.
     */
    public String getSecretKey() {
        return this.secretKey;
    }

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param id Encryption key ID.
     */
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * @param secretKey Secret Key value.
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.id));
        buffer.put(BendecUtils.stringToByteArray(this.secretKey, 32));
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.id));
        buffer.put(BendecUtils.stringToByteArray(this.secretKey, 32));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header,
        id,
        secretKey);
    }
    
    @Override
    public String toString() {
        return "EncryptionKey {" +
            "header=" + header +
            ", id=" + id +
            ", secretKey=" + secretKey +
            "}";
    }
}