package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>AuctionSummary</h2>
 * <p>Auction summary, including auction price and quantity.</p>
 * <p>Byte length: 63</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) instrumentId - ID of financial instrument. | size 4</p>
 * <p>Price > long (i64) price - Auction price (final auction price). | size 8</p>
 * <p>Quantity > BigInteger (u64) quantity - Auction quantity (final auction volume). | size 8</p>
 * <p>AuctionType auctionType - Type of auction. | size 1</p>
 */
public class AuctionSummary implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private long price;
    private BigInteger quantity;
    private AuctionType auctionType;
    public static final int byteLength = 63;

    public AuctionSummary(Header header, long instrumentId, long price, BigInteger quantity, AuctionType auctionType) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.price = price;
        this.quantity = quantity;
        this.auctionType = auctionType;
    }
    
    public AuctionSummary(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.price = BendecUtils.int64FromByteArray(bytes, offset + 46);
        this.quantity = BendecUtils.uInt64FromByteArray(bytes, offset + 54);
        this.auctionType = AuctionType.getAuctionType(bytes, offset + 62);
    }
    
    public AuctionSummary(byte[] bytes) {
        this(bytes, 0);
    }
    
    public AuctionSummary() {
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
     * @return Auction price (final auction price).
     */
    public long getPrice() {
        return this.price;
    }
    
    /**
     * @return Auction quantity (final auction volume).
     */
    public BigInteger getQuantity() {
        return this.quantity;
    }
    
    /**
     * @return Type of auction.
     */
    public AuctionType getAuctionType() {
        return this.auctionType;
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
     * @param price Auction price (final auction price).
     */
    public void setPrice(long price) {
        this.price = price;
    }
    
    /**
     * @param quantity Auction quantity (final auction volume).
     */
    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }
    
    /**
     * @param auctionType Type of auction.
     */
    public void setAuctionType(AuctionType auctionType) {
        this.auctionType = auctionType;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        auctionType.toBytes(buffer);
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        auctionType.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        price,
        quantity,
        auctionType);
    }
    
    @Override
    public String toString() {
        return "AuctionSummary {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", price=" + price +
            ", quantity=" + quantity +
            ", auctionType=" + auctionType +
            "}";
    }
}