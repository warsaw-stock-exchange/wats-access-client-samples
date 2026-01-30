package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>TradeCollars</h2>
 * <p>New trade price collars.</p>
 * <p>Byte length: 71</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) instrumentId - ID of financial instrument. | size 4</p>
 * <p>CollarType collarType - Collar type. | size 1</p>
 * <p>Price > long (i64) price - The reference price. | size 8</p>
 * <p>Bound > long (i64) lower - Value of the lower Price collar. | size 8</p>
 * <p>Bound > long (i64) upper - Value of the upper Price collar. | size 8</p>
 */
public class TradeCollars implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private CollarType collarType;
    private long price;
    private long lower;
    private long upper;
    public static final int byteLength = 71;

    public TradeCollars(Header header, long instrumentId, CollarType collarType, long price, long lower, long upper) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.collarType = collarType;
        this.price = price;
        this.lower = lower;
        this.upper = upper;
    }
    
    public TradeCollars(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.collarType = CollarType.getCollarType(bytes, offset + 46);
        this.price = BendecUtils.int64FromByteArray(bytes, offset + 47);
        this.lower = BendecUtils.int64FromByteArray(bytes, offset + 55);
        this.upper = BendecUtils.int64FromByteArray(bytes, offset + 63);
    }
    
    public TradeCollars(byte[] bytes) {
        this(bytes, 0);
    }
    
    public TradeCollars() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return ID of financial instrument.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    }
    
    /**
     * @return Collar type.
     */
    public CollarType getCollarType() {
        return this.collarType;
    }
    
    /**
     * @return The reference price.
     */
    public long getPrice() {
        return this.price;
    }
    
    /**
     * @return Value of the lower Price collar.
     */
    public long getLower() {
        return this.lower;
    }
    
    /**
     * @return Value of the upper Price collar.
     */
    public long getUpper() {
        return this.upper;
    }

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param instrumentId ID of financial instrument.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }
    
    /**
     * @param collarType Collar type.
     */
    public void setCollarType(CollarType collarType) {
        this.collarType = collarType;
    }
    
    /**
     * @param price The reference price.
     */
    public void setPrice(long price) {
        this.price = price;
    }
    
    /**
     * @param lower Value of the lower Price collar.
     */
    public void setLower(long lower) {
        this.lower = lower;
    }
    
    /**
     * @param upper Value of the upper Price collar.
     */
    public void setUpper(long upper) {
        this.upper = upper;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        collarType.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.int64ToByteArray(this.lower));
        buffer.put(BendecUtils.int64ToByteArray(this.upper));
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        collarType.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.int64ToByteArray(this.lower));
        buffer.put(BendecUtils.int64ToByteArray(this.upper));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        collarType,
        price,
        lower,
        upper);
    }
    
    @Override
    public String toString() {
        return "TradeCollars {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", collarType=" + collarType +
            ", price=" + price +
            ", lower=" + lower +
            ", upper=" + upper +
            "}";
    }
}