package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>AuctionUpdate</h2>
 * <p>Updates IMP/IMV and BBO during an auction.</p>
 * <p>Byte length: 110</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) instrumentId - ID of financial instrument. | size 4</p>
 * <p>Price > long (i64) indicativeMatchingPrice - Indicative Matching Price (IMP). | size 8</p>
 * <p>Quantity > BigInteger (u64) indicativeMatchingVolume - Indicative Matching Volume (IMV). | size 8</p>
 * <p>Quantity > BigInteger (u64) totalSellQuantityAtImp - Total sell quantity at IMP. | size 8</p>
 * <p>Quantity > BigInteger (u64) totalBuyQuantityAtImp - Total buy quantity at IMP. | size 8</p>
 * <p>Price > long (i64) bestSellLevel - Best sell price level (1st BBO level). | size 8</p>
 * <p>Quantity > BigInteger (u64) bestSellLevelQuantity - Quantity at the best sell price level. | size 8</p>
 * <p>Price > long (i64) bestBuyLevel - Best buy price level (1st BBO level). | size 8</p>
 * <p>Quantity > BigInteger (u64) bestBuyLevelQuantity - Quantity at the best buy price level. | size 8</p>
 */
public class AuctionUpdate implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private long indicativeMatchingPrice;
    private BigInteger indicativeMatchingVolume;
    private BigInteger totalSellQuantityAtImp;
    private BigInteger totalBuyQuantityAtImp;
    private long bestSellLevel;
    private BigInteger bestSellLevelQuantity;
    private long bestBuyLevel;
    private BigInteger bestBuyLevelQuantity;
    public static final int byteLength = 110;
    
    public AuctionUpdate(Header header, long instrumentId, long indicativeMatchingPrice, BigInteger indicativeMatchingVolume, BigInteger totalSellQuantityAtImp, BigInteger totalBuyQuantityAtImp, long bestSellLevel, BigInteger bestSellLevelQuantity, long bestBuyLevel, BigInteger bestBuyLevelQuantity) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.indicativeMatchingPrice = indicativeMatchingPrice;
        this.indicativeMatchingVolume = indicativeMatchingVolume;
        this.totalSellQuantityAtImp = totalSellQuantityAtImp;
        this.totalBuyQuantityAtImp = totalBuyQuantityAtImp;
        this.bestSellLevel = bestSellLevel;
        this.bestSellLevelQuantity = bestSellLevelQuantity;
        this.bestBuyLevel = bestBuyLevel;
        this.bestBuyLevelQuantity = bestBuyLevelQuantity;
    }
    
    public AuctionUpdate(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.indicativeMatchingPrice = BendecUtils.int64FromByteArray(bytes, offset + 46);
        this.indicativeMatchingVolume = BendecUtils.uInt64FromByteArray(bytes, offset + 54);
        this.totalSellQuantityAtImp = BendecUtils.uInt64FromByteArray(bytes, offset + 62);
        this.totalBuyQuantityAtImp = BendecUtils.uInt64FromByteArray(bytes, offset + 70);
        this.bestSellLevel = BendecUtils.int64FromByteArray(bytes, offset + 78);
        this.bestSellLevelQuantity = BendecUtils.uInt64FromByteArray(bytes, offset + 86);
        this.bestBuyLevel = BendecUtils.int64FromByteArray(bytes, offset + 94);
        this.bestBuyLevelQuantity = BendecUtils.uInt64FromByteArray(bytes, offset + 102);
    }
    
    public AuctionUpdate(byte[] bytes) {
        this(bytes, 0);
    }
    
    public AuctionUpdate() {
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
     * @return Indicative Matching Price (IMP).
     */
    public long getIndicativeMatchingPrice() {
        return this.indicativeMatchingPrice;
    }
    
    /**
     * @return Indicative Matching Volume (IMV).
     */
    public BigInteger getIndicativeMatchingVolume() {
        return this.indicativeMatchingVolume;
    }
    
    /**
     * @return Total sell quantity at IMP.
     */
    public BigInteger getTotalSellQuantityAtImp() {
        return this.totalSellQuantityAtImp;
    }
    
    /**
     * @return Total buy quantity at IMP.
     */
    public BigInteger getTotalBuyQuantityAtImp() {
        return this.totalBuyQuantityAtImp;
    }
    
    /**
     * @return Best sell price level (1st BBO level).
     */
    public long getBestSellLevel() {
        return this.bestSellLevel;
    }
    
    /**
     * @return Quantity at the best sell price level.
     */
    public BigInteger getBestSellLevelQuantity() {
        return this.bestSellLevelQuantity;
    }
    
    /**
     * @return Best buy price level (1st BBO level).
     */
    public long getBestBuyLevel() {
        return this.bestBuyLevel;
    }
    
    /**
     * @return Quantity at the best buy price level.
     */
    public BigInteger getBestBuyLevelQuantity() {
        return this.bestBuyLevelQuantity;
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
     * @param indicativeMatchingPrice Indicative Matching Price (IMP).
     */
    public void setIndicativeMatchingPrice(long indicativeMatchingPrice) {
        this.indicativeMatchingPrice = indicativeMatchingPrice;
    }
    
    /**
     * @param indicativeMatchingVolume Indicative Matching Volume (IMV).
     */
    public void setIndicativeMatchingVolume(BigInteger indicativeMatchingVolume) {
        this.indicativeMatchingVolume = indicativeMatchingVolume;
    }
    
    /**
     * @param totalSellQuantityAtImp Total sell quantity at IMP.
     */
    public void setTotalSellQuantityAtImp(BigInteger totalSellQuantityAtImp) {
        this.totalSellQuantityAtImp = totalSellQuantityAtImp;
    }
    
    /**
     * @param totalBuyQuantityAtImp Total buy quantity at IMP.
     */
    public void setTotalBuyQuantityAtImp(BigInteger totalBuyQuantityAtImp) {
        this.totalBuyQuantityAtImp = totalBuyQuantityAtImp;
    }
    
    /**
     * @param bestSellLevel Best sell price level (1st BBO level).
     */
    public void setBestSellLevel(long bestSellLevel) {
        this.bestSellLevel = bestSellLevel;
    }
    
    /**
     * @param bestSellLevelQuantity Quantity at the best sell price level.
     */
    public void setBestSellLevelQuantity(BigInteger bestSellLevelQuantity) {
        this.bestSellLevelQuantity = bestSellLevelQuantity;
    }
    
    /**
     * @param bestBuyLevel Best buy price level (1st BBO level).
     */
    public void setBestBuyLevel(long bestBuyLevel) {
        this.bestBuyLevel = bestBuyLevel;
    }
    
    /**
     * @param bestBuyLevelQuantity Quantity at the best buy price level.
     */
    public void setBestBuyLevelQuantity(BigInteger bestBuyLevelQuantity) {
        this.bestBuyLevelQuantity = bestBuyLevelQuantity;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.int64ToByteArray(this.indicativeMatchingPrice));
        buffer.put(BendecUtils.uInt64ToByteArray(this.indicativeMatchingVolume));
        buffer.put(BendecUtils.uInt64ToByteArray(this.totalSellQuantityAtImp));
        buffer.put(BendecUtils.uInt64ToByteArray(this.totalBuyQuantityAtImp));
        buffer.put(BendecUtils.int64ToByteArray(this.bestSellLevel));
        buffer.put(BendecUtils.uInt64ToByteArray(this.bestSellLevelQuantity));
        buffer.put(BendecUtils.int64ToByteArray(this.bestBuyLevel));
        buffer.put(BendecUtils.uInt64ToByteArray(this.bestBuyLevelQuantity));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.int64ToByteArray(this.indicativeMatchingPrice));
        buffer.put(BendecUtils.uInt64ToByteArray(this.indicativeMatchingVolume));
        buffer.put(BendecUtils.uInt64ToByteArray(this.totalSellQuantityAtImp));
        buffer.put(BendecUtils.uInt64ToByteArray(this.totalBuyQuantityAtImp));
        buffer.put(BendecUtils.int64ToByteArray(this.bestSellLevel));
        buffer.put(BendecUtils.uInt64ToByteArray(this.bestSellLevelQuantity));
        buffer.put(BendecUtils.int64ToByteArray(this.bestBuyLevel));
        buffer.put(BendecUtils.uInt64ToByteArray(this.bestBuyLevelQuantity));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        indicativeMatchingPrice,
        indicativeMatchingVolume,
        totalSellQuantityAtImp,
        totalBuyQuantityAtImp,
        bestSellLevel,
        bestSellLevelQuantity,
        bestBuyLevel,
        bestBuyLevelQuantity);
    }
    
    @Override
    public String toString() {
        return "AuctionUpdate {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", indicativeMatchingPrice=" + indicativeMatchingPrice +
            ", indicativeMatchingVolume=" + indicativeMatchingVolume +
            ", totalSellQuantityAtImp=" + totalSellQuantityAtImp +
            ", totalBuyQuantityAtImp=" + totalBuyQuantityAtImp +
            ", bestSellLevel=" + bestSellLevel +
            ", bestSellLevelQuantity=" + bestSellLevelQuantity +
            ", bestBuyLevel=" + bestBuyLevel +
            ", bestBuyLevelQuantity=" + bestBuyLevelQuantity +
            "}";
    }
}