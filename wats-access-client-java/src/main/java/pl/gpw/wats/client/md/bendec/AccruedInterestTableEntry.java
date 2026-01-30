package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>AccruedInterestTableEntry</h2>
 * <p>Message describing an accrued interest table entry.</p>
 * <p>Byte length: 58</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) productId | size 4</p>
 * <p>Date > long (u32) accruedInterestTableEntryDate - Accrued interest table entry date. | size 4</p>
 * <p>Number > long (i64) accruedInterestTableEntryValue - Accrued interest table entry value. | size 8</p>
 */
public class AccruedInterestTableEntry implements ByteSerializable, Message {
    private Header header;
    private long productId;
    private long accruedInterestTableEntryDate;
    private long accruedInterestTableEntryValue;
    public static final int byteLength = 58;

    public AccruedInterestTableEntry(Header header, long productId, long accruedInterestTableEntryDate, long accruedInterestTableEntryValue) {
        this.header = header;
        this.productId = productId;
        this.accruedInterestTableEntryDate = accruedInterestTableEntryDate;
        this.accruedInterestTableEntryValue = accruedInterestTableEntryValue;
    }
    
    public AccruedInterestTableEntry(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.productId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.accruedInterestTableEntryDate = BendecUtils.uInt32FromByteArray(bytes, offset + 46);
        this.accruedInterestTableEntryValue = BendecUtils.int64FromByteArray(bytes, offset + 50);
    }
    
    public AccruedInterestTableEntry(byte[] bytes) {
        this(bytes, 0);
    }
    
    public AccruedInterestTableEntry() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    public long getProductId() {
        return this.productId;
    }
    
    /**
     * @return Accrued interest table entry date.
     */
    public long getAccruedInterestTableEntryDate() {
        return this.accruedInterestTableEntryDate;
    }
    
    /**
     * @return Accrued interest table entry value.
     */
    public long getAccruedInterestTableEntryValue() {
        return this.accruedInterestTableEntryValue;
    }

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    public void setProductId(long productId) {
        this.productId = productId;
    }
    
    /**
     * @param accruedInterestTableEntryDate Accrued interest table entry date.
     */
    public void setAccruedInterestTableEntryDate(long accruedInterestTableEntryDate) {
        this.accruedInterestTableEntryDate = accruedInterestTableEntryDate;
    }
    
    /**
     * @param accruedInterestTableEntryValue Accrued interest table entry value.
     */
    public void setAccruedInterestTableEntryValue(long accruedInterestTableEntryValue) {
        this.accruedInterestTableEntryValue = accruedInterestTableEntryValue;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.productId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.accruedInterestTableEntryDate));
        buffer.put(BendecUtils.int64ToByteArray(this.accruedInterestTableEntryValue));
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.productId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.accruedInterestTableEntryDate));
        buffer.put(BendecUtils.int64ToByteArray(this.accruedInterestTableEntryValue));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header,
        productId,
        accruedInterestTableEntryDate,
        accruedInterestTableEntryValue);
    }
    
    @Override
    public String toString() {
        return "AccruedInterestTableEntry {" +
            "header=" + header +
            ", productId=" + productId +
            ", accruedInterestTableEntryDate=" + accruedInterestTableEntryDate +
            ", accruedInterestTableEntryValue=" + accruedInterestTableEntryValue +
            "}";
    }
}