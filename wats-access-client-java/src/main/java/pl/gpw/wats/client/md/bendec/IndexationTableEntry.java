package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>IndexationTableEntry</h2>
 * <p>Message describing an indexation table entry.</p>
 * <p>Byte length: 55</p>
 * <p>Header header - Message header. | size 39</p>
 * <p>ElementId > long (u32) productId - Product ID. | size 4</p>
 * <p>Date > long (u32) indexationTableEntryDate - Indexation table entry date. | size 4</p>
 * <p>Number > long (i64) indexationTableEntryValue - Indexation table entry value. | size 8</p>
 * */

public class IndexationTableEntry implements ByteSerializable, Message {

    private Header header;
    private long productId;
    private long indexationTableEntryDate;
    private long indexationTableEntryValue;
    public static final int byteLength = 55;

    public IndexationTableEntry(Header header, long productId, long indexationTableEntryDate, long indexationTableEntryValue) {
        this.header = header;
        this.productId = productId;
        this.indexationTableEntryDate = indexationTableEntryDate;
        this.indexationTableEntryValue = indexationTableEntryValue;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.INDEXATIONTABLEENTRY);
    }

    public IndexationTableEntry(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.productId = BendecUtils.uInt32FromByteArray(bytes, offset + 39);
        this.indexationTableEntryDate = BendecUtils.uInt32FromByteArray(bytes, offset + 43);
        this.indexationTableEntryValue = BendecUtils.int64FromByteArray(bytes, offset + 47);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.INDEXATIONTABLEENTRY);
    }

    public IndexationTableEntry(byte[] bytes) {
        this(bytes, 0);
    }

    public IndexationTableEntry() {
    }



    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return Product ID.
     */
    public long getProductId() {
        return this.productId;
    };
    /**
     * @return Indexation table entry date.
     */
    public long getIndexationTableEntryDate() {
        return this.indexationTableEntryDate;
    };
    /**
     * @return Indexation table entry value.
     */
    public long getIndexationTableEntryValue() {
        return this.indexationTableEntryValue;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param productId Product ID.
     */
    public void setProductId(long productId) {
        this.productId = productId;
    };
    /**
     * @param indexationTableEntryDate Indexation table entry date.
     */
    public void setIndexationTableEntryDate(long indexationTableEntryDate) {
        this.indexationTableEntryDate = indexationTableEntryDate;
    };
    /**
     * @param indexationTableEntryValue Indexation table entry value.
     */
    public void setIndexationTableEntryValue(long indexationTableEntryValue) {
        this.indexationTableEntryValue = indexationTableEntryValue;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.productId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.indexationTableEntryDate));
        buffer.put(BendecUtils.int64ToByteArray(this.indexationTableEntryValue));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.productId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.indexationTableEntryDate));
        buffer.put(BendecUtils.int64ToByteArray(this.indexationTableEntryValue));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, productId, indexationTableEntryDate, indexationTableEntryValue);
    }

    @Override
    public String toString() {
        return "IndexationTableEntry{" +
            "header=" + header +
            ", productId=" + productId +
            ", indexationTableEntryDate=" + indexationTableEntryDate +
            ", indexationTableEntryValue=" + indexationTableEntryValue +
            '}';
        }
}
