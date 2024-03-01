package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>CollarGroup</h2>
 * <p>Message defining a collar group.</p>
 * <p>Byte length: 59</p>
 * <p>Header header - Message header. | size 39</p>
 * <p>ElementId > long (u32) id - Collar group ID. | size 4</p>
 * <p>ElementId > long (u32) orderStaticCollarTableId - Collar table ID. | size 4</p>
 * <p>ElementId > long (u32) orderDynamicCollarTableId - Collar table ID. | size 4</p>
 * <p>ElementId > long (u32) tradeStaticCollarTableId - Collar table ID. | size 4</p>
 * <p>ElementId > long (u32) tradeDynamicCollarTableId - Collar table ID. | size 4</p>
 * */

public class CollarGroup implements ByteSerializable, Message {

    private Header header;
    private long id;
    private long orderStaticCollarTableId;
    private long orderDynamicCollarTableId;
    private long tradeStaticCollarTableId;
    private long tradeDynamicCollarTableId;
    public static final int byteLength = 59;

    public CollarGroup(Header header, long id, long orderStaticCollarTableId, long orderDynamicCollarTableId, long tradeStaticCollarTableId, long tradeDynamicCollarTableId) {
        this.header = header;
        this.id = id;
        this.orderStaticCollarTableId = orderStaticCollarTableId;
        this.orderDynamicCollarTableId = orderDynamicCollarTableId;
        this.tradeStaticCollarTableId = tradeStaticCollarTableId;
        this.tradeDynamicCollarTableId = tradeDynamicCollarTableId;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.COLLARGROUP);
    }

    public CollarGroup(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.id = BendecUtils.uInt32FromByteArray(bytes, offset + 39);
        this.orderStaticCollarTableId = BendecUtils.uInt32FromByteArray(bytes, offset + 43);
        this.orderDynamicCollarTableId = BendecUtils.uInt32FromByteArray(bytes, offset + 47);
        this.tradeStaticCollarTableId = BendecUtils.uInt32FromByteArray(bytes, offset + 51);
        this.tradeDynamicCollarTableId = BendecUtils.uInt32FromByteArray(bytes, offset + 55);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.COLLARGROUP);
    }

    public CollarGroup(byte[] bytes) {
        this(bytes, 0);
    }

    public CollarGroup() {
    }



    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return Collar group ID.
     */
    public long getId() {
        return this.id;
    };
    /**
     * @return Collar table ID.
     */
    public long getOrderStaticCollarTableId() {
        return this.orderStaticCollarTableId;
    };
    /**
     * @return Collar table ID.
     */
    public long getOrderDynamicCollarTableId() {
        return this.orderDynamicCollarTableId;
    };
    /**
     * @return Collar table ID.
     */
    public long getTradeStaticCollarTableId() {
        return this.tradeStaticCollarTableId;
    };
    /**
     * @return Collar table ID.
     */
    public long getTradeDynamicCollarTableId() {
        return this.tradeDynamicCollarTableId;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param id Collar group ID.
     */
    public void setId(long id) {
        this.id = id;
    };
    /**
     * @param orderStaticCollarTableId Collar table ID.
     */
    public void setOrderStaticCollarTableId(long orderStaticCollarTableId) {
        this.orderStaticCollarTableId = orderStaticCollarTableId;
    };
    /**
     * @param orderDynamicCollarTableId Collar table ID.
     */
    public void setOrderDynamicCollarTableId(long orderDynamicCollarTableId) {
        this.orderDynamicCollarTableId = orderDynamicCollarTableId;
    };
    /**
     * @param tradeStaticCollarTableId Collar table ID.
     */
    public void setTradeStaticCollarTableId(long tradeStaticCollarTableId) {
        this.tradeStaticCollarTableId = tradeStaticCollarTableId;
    };
    /**
     * @param tradeDynamicCollarTableId Collar table ID.
     */
    public void setTradeDynamicCollarTableId(long tradeDynamicCollarTableId) {
        this.tradeDynamicCollarTableId = tradeDynamicCollarTableId;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.id));
        buffer.put(BendecUtils.uInt32ToByteArray(this.orderStaticCollarTableId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.orderDynamicCollarTableId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradeStaticCollarTableId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradeDynamicCollarTableId));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.id));
        buffer.put(BendecUtils.uInt32ToByteArray(this.orderStaticCollarTableId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.orderDynamicCollarTableId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradeStaticCollarTableId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradeDynamicCollarTableId));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, id, orderStaticCollarTableId, orderDynamicCollarTableId, tradeStaticCollarTableId, tradeDynamicCollarTableId);
    }

    @Override
    public String toString() {
        return "CollarGroup{" +
            "header=" + header +
            ", id=" + id +
            ", orderStaticCollarTableId=" + orderStaticCollarTableId +
            ", orderDynamicCollarTableId=" + orderDynamicCollarTableId +
            ", tradeStaticCollarTableId=" + tradeStaticCollarTableId +
            ", tradeDynamicCollarTableId=" + tradeDynamicCollarTableId +
            '}';
        }
}
