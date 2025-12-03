package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>BidOfferUpdate</h2>
 * <p>Message send during the IPO to the sell side or during the Tender Offer to the buy side.</p>
 * <p>Byte length: 53</p>
 * <p>Header header - Message header. | size 24</p>
 * <p>ElementId > long (u32) instrumentId - Instrument ID. | size 4</p>
 * <p>BidOfferUpdateType updateType - Indicates a type of the BidOfferUpdate message. | size 1</p>
 * <p>Quantity > BigInteger (u64) totalBidSize - Specifies the total bid size. | size 8</p>
 * <p>Quantity > BigInteger (u64) totalOfferSize - Specifies the total offer size. | size 8</p>
 * <p>u32 > long bidOrders - Number of bid orders. | size 4</p>
 * <p>u32 > long offerOrders - Number of offer orders. | size 4</p>
 */
public class BidOfferUpdate implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private BidOfferUpdateType updateType;
    private BigInteger totalBidSize;
    private BigInteger totalOfferSize;
    private long bidOrders;
    private long offerOrders;
    public static final int byteLength = 53;
    
    public BidOfferUpdate(Header header, long instrumentId, BidOfferUpdateType updateType, BigInteger totalBidSize, BigInteger totalOfferSize, long bidOrders, long offerOrders) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.updateType = updateType;
        this.totalBidSize = totalBidSize;
        this.totalOfferSize = totalOfferSize;
        this.bidOrders = bidOrders;
        this.offerOrders = offerOrders;
    }
    
    public BidOfferUpdate(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 24);
        this.updateType = BidOfferUpdateType.getBidOfferUpdateType(bytes, offset + 28);
        this.totalBidSize = BendecUtils.uInt64FromByteArray(bytes, offset + 29);
        this.totalOfferSize = BendecUtils.uInt64FromByteArray(bytes, offset + 37);
        this.bidOrders = BendecUtils.uInt32FromByteArray(bytes, offset + 45);
        this.offerOrders = BendecUtils.uInt32FromByteArray(bytes, offset + 49);
    }
    
    public BidOfferUpdate(byte[] bytes) {
        this(bytes, 0);
    }
    
    public BidOfferUpdate() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Instrument ID.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    }
    
    /**
     * @return Indicates a type of the BidOfferUpdate message.
     */
    public BidOfferUpdateType getUpdateType() {
        return this.updateType;
    }
    
    /**
     * @return Specifies the total bid size.
     */
    public BigInteger getTotalBidSize() {
        return this.totalBidSize;
    }
    
    /**
     * @return Specifies the total offer size.
     */
    public BigInteger getTotalOfferSize() {
        return this.totalOfferSize;
    }
    
    /**
     * @return Number of bid orders.
     */
    public long getBidOrders() {
        return this.bidOrders;
    }
    
    /**
     * @return Number of offer orders.
     */
    public long getOfferOrders() {
        return this.offerOrders;
    }
    
    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param instrumentId Instrument ID.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }
    
    /**
     * @param updateType Indicates a type of the BidOfferUpdate message.
     */
    public void setUpdateType(BidOfferUpdateType updateType) {
        this.updateType = updateType;
    }
    
    /**
     * @param totalBidSize Specifies the total bid size.
     */
    public void setTotalBidSize(BigInteger totalBidSize) {
        this.totalBidSize = totalBidSize;
    }
    
    /**
     * @param totalOfferSize Specifies the total offer size.
     */
    public void setTotalOfferSize(BigInteger totalOfferSize) {
        this.totalOfferSize = totalOfferSize;
    }
    
    /**
     * @param bidOrders Number of bid orders.
     */
    public void setBidOrders(long bidOrders) {
        this.bidOrders = bidOrders;
    }
    
    /**
     * @param offerOrders Number of offer orders.
     */
    public void setOfferOrders(long offerOrders) {
        this.offerOrders = offerOrders;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        updateType.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.totalBidSize));
        buffer.put(BendecUtils.uInt64ToByteArray(this.totalOfferSize));
        buffer.put(BendecUtils.uInt32ToByteArray(this.bidOrders));
        buffer.put(BendecUtils.uInt32ToByteArray(this.offerOrders));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        updateType.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.totalBidSize));
        buffer.put(BendecUtils.uInt64ToByteArray(this.totalOfferSize));
        buffer.put(BendecUtils.uInt32ToByteArray(this.bidOrders));
        buffer.put(BendecUtils.uInt32ToByteArray(this.offerOrders));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        updateType,
        totalBidSize,
        totalOfferSize,
        bidOrders,
        offerOrders);
    }
    
    @Override
    public String toString() {
        return "BidOfferUpdate {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", updateType=" + updateType +
            ", totalBidSize=" + totalBidSize +
            ", totalOfferSize=" + totalOfferSize +
            ", bidOrders=" + bidOrders +
            ", offerOrders=" + offerOrders +
            "}";
    }
}