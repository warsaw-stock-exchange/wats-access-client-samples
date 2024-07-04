package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>OrderCollars</h2>
 * <p>New order price collars.</p>
 * <p>Byte length: 87</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) instrumentId - ID of financial instrument. | size 4</p>
 * <p>CollarType collarType - Collars type. | size 1</p>
 * <p>Price > long (i64) price - The reference price. | size 8</p>
 * <p>Bound > long (i64) lowerBid - Value of the lower bid Price collar. | size 8</p>
 * <p>Bound > long (i64) upperBid - Value of the upper bid Price collar. | size 8</p>
 * <p>Bound > long (i64) lowerAsk - Value of the lower ask Price collar. | size 8</p>
 * <p>Bound > long (i64) upperAsk - Value of the upper ask Price collar. | size 8</p>
 */
public class OrderCollars implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private CollarType collarType;
    private long price;
    private long lowerBid;
    private long upperBid;
    private long lowerAsk;
    private long upperAsk;
    public static final int byteLength = 87;
    
    public OrderCollars(Header header, long instrumentId, CollarType collarType, long price, long lowerBid, long upperBid, long lowerAsk, long upperAsk) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.collarType = collarType;
        this.price = price;
        this.lowerBid = lowerBid;
        this.upperBid = upperBid;
        this.lowerAsk = lowerAsk;
        this.upperAsk = upperAsk;
    }
    
    public OrderCollars(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.collarType = CollarType.getCollarType(bytes, offset + 46);
        this.price = BendecUtils.int64FromByteArray(bytes, offset + 47);
        this.lowerBid = BendecUtils.int64FromByteArray(bytes, offset + 55);
        this.upperBid = BendecUtils.int64FromByteArray(bytes, offset + 63);
        this.lowerAsk = BendecUtils.int64FromByteArray(bytes, offset + 71);
        this.upperAsk = BendecUtils.int64FromByteArray(bytes, offset + 79);
    }
    
    public OrderCollars(byte[] bytes) {
        this(bytes, 0);
    }
    
    public OrderCollars() {
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
     * @return Collars type.
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
     * @return Value of the lower bid Price collar.
     */
    public long getLowerBid() {
        return this.lowerBid;
    }
    
    /**
     * @return Value of the upper bid Price collar.
     */
    public long getUpperBid() {
        return this.upperBid;
    }
    
    /**
     * @return Value of the lower ask Price collar.
     */
    public long getLowerAsk() {
        return this.lowerAsk;
    }
    
    /**
     * @return Value of the upper ask Price collar.
     */
    public long getUpperAsk() {
        return this.upperAsk;
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
     * @param collarType Collars type.
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
     * @param lowerBid Value of the lower bid Price collar.
     */
    public void setLowerBid(long lowerBid) {
        this.lowerBid = lowerBid;
    }
    
    /**
     * @param upperBid Value of the upper bid Price collar.
     */
    public void setUpperBid(long upperBid) {
        this.upperBid = upperBid;
    }
    
    /**
     * @param lowerAsk Value of the lower ask Price collar.
     */
    public void setLowerAsk(long lowerAsk) {
        this.lowerAsk = lowerAsk;
    }
    
    /**
     * @param upperAsk Value of the upper ask Price collar.
     */
    public void setUpperAsk(long upperAsk) {
        this.upperAsk = upperAsk;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        collarType.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.int64ToByteArray(this.lowerBid));
        buffer.put(BendecUtils.int64ToByteArray(this.upperBid));
        buffer.put(BendecUtils.int64ToByteArray(this.lowerAsk));
        buffer.put(BendecUtils.int64ToByteArray(this.upperAsk));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        collarType.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.int64ToByteArray(this.lowerBid));
        buffer.put(BendecUtils.int64ToByteArray(this.upperBid));
        buffer.put(BendecUtils.int64ToByteArray(this.lowerAsk));
        buffer.put(BendecUtils.int64ToByteArray(this.upperAsk));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        collarType,
        price,
        lowerBid,
        upperBid,
        lowerAsk,
        upperAsk);
    }
    
    @Override
    public String toString() {
        return "OrderCollars {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", collarType=" + collarType +
            ", price=" + price +
            ", lowerBid=" + lowerBid +
            ", upperBid=" + upperBid +
            ", lowerAsk=" + lowerAsk +
            ", upperAsk=" + upperAsk +
            "}";
    }
}