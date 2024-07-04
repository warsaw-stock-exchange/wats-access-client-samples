package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>OrderCancel</h2>
 * <p>Message used to cancel the previously submitted order.</p>
 * <p>Byte length: 40</p>
 * <p>Header header - Header. | size 16</p>
 * <p>OrderId > BigInteger (u64) orderId - Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID. | size 8</p>
 * <p>MifidFields mifidFields - MifidFields structure. | size 16</p>
 */
public class OrderCancel implements ByteSerializable, Message {
    private Header header;
    private BigInteger orderId;
    private MifidFields mifidFields;
    public static final int byteLength = 40;
    
    public OrderCancel(Header header, BigInteger orderId, MifidFields mifidFields) {
        this.header = header;
        this.orderId = orderId;
        this.mifidFields = mifidFields;
    }
    
    public OrderCancel(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.orderId = BendecUtils.uInt64FromByteArray(bytes, offset + 16);
        this.mifidFields = new MifidFields(bytes, offset + 24);
    }
    
    public OrderCancel(byte[] bytes) {
        this(bytes, 0);
    }
    
    public OrderCancel() {
    }
    
    /**
     * @return Header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID.
     */
    public BigInteger getOrderId() {
        return this.orderId;
    }
    
    /**
     * @return MifidFields structure.
     */
    public MifidFields getMifidFields() {
        return this.mifidFields;
    }
    
    /**
     * @param header Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param orderId Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID.
     */
    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    }
    
    /**
     * @param mifidFields MifidFields structure.
     */
    public void setMifidFields(MifidFields mifidFields) {
        this.mifidFields = mifidFields;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.orderId));
        mifidFields.toBytes(buffer);
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.orderId));
        mifidFields.toBytes(buffer);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        orderId,
        mifidFields);
    }
    
    @Override
    public String toString() {
        return "OrderCancel {" +
            "header=" + header +
            ", orderId=" + orderId +
            ", mifidFields=" + mifidFields +
            "}";
    }
}