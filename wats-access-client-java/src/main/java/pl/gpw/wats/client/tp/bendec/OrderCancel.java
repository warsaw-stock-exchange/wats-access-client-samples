package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>OrderCancel</h2>
 * <p>Message used to cancel the previously submitted order.</p>
 * <p>Byte length: 24</p>
 * <p>Header header - Header. | size 16</p>
 * <p>OrderId > BigInteger (u64) orderId - Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID. | size 8</p>
 * */

public class OrderCancel implements ByteSerializable, Message {

    private Header header;
    private BigInteger orderId;
    public static final int byteLength = 24;

    public OrderCancel(Header header, BigInteger orderId) {
        this.header = header;
        this.orderId = orderId;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.ORDERCANCEL);
    }

    public OrderCancel(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.orderId = BendecUtils.uInt64FromByteArray(bytes, offset + 16);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.ORDERCANCEL);
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
    };
    /**
     * @return Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID.
     */
    public BigInteger getOrderId() {
        return this.orderId;
    };

    /**
     * @param header Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param orderId Unique for each trading day order identifier based on the sequence number of order message, bulk sequence number, session ID and connection ID.
     */
    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.orderId));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.orderId));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, orderId);
    }

    @Override
    public String toString() {
        return "OrderCancel{" +
            "header=" + header +
            ", orderId=" + orderId +
            '}';
        }
}
