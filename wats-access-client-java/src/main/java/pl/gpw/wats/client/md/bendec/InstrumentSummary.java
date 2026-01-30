package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>InstrumentSummary</h2>
 * <p>Provides brief instrument summary.</p>
 * <p>Byte length: 137</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) instrumentId - Identifier of the instrument. | size 4</p>
 * <p>Price > long (i64) lastTradedPrice - Last Traded Price (LTP). | size 8</p>
 * <p>Price > long (i64) closingPrice - Closing Price (CP). | size 8</p>
 * <p>ClosingPriceType closingPriceType - Closing Price Type. For OffBook means last transaction price. | size 1</p>
 * <p>Price > long (i64) adjustedClosingPrice - Adjusted Closing Price (ACP). | size 8</p>
 * <p>AdjustedClosingPriceReason adjustedClosingPriceReason - Reason for adjusting the Closing Price. | size 1</p>
 * <p>PercentageChange > long (i64) pctChange - Percentage change from last session closing price. | size 8</p>
 * <p>Price > long (i64) vwap - Volume-weighted average price for the market model in which there is an instrument. | size 8</p>
 * <p>u64 > BigInteger noTrades - Total number of transactions on the current trading day. | size 8</p>
 * <p>Quantity > BigInteger (u64) totalVolume - Total transaction volume. | size 8</p>
 * <p>Value > long (i64) totalValue - Total transaction value. | size 8</p>
 * <p>Price > long (i64) openingPrice - The price of the first trade on the current trading day. | size 8</p>
 * <p>Price > long (i64) maxPrice - Highest price of the instrument on the current trading day. | size 8</p>
 * <p>Price > long (i64) minPrice - Lowest price of the instrument on the current trading day. | size 8</p>
 * <p>bool > boolean isCorrection - Previous message correction flag. | size 1</p>
 */
public class InstrumentSummary implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private long lastTradedPrice;
    private long closingPrice;
    private ClosingPriceType closingPriceType;
    private long adjustedClosingPrice;
    private AdjustedClosingPriceReason adjustedClosingPriceReason;
    private long pctChange;
    private long vwap;
    private BigInteger noTrades;
    private BigInteger totalVolume;
    private long totalValue;
    private long openingPrice;
    private long maxPrice;
    private long minPrice;
    private boolean isCorrection;
    public static final int byteLength = 137;

    public InstrumentSummary(Header header, long instrumentId, long lastTradedPrice, long closingPrice, ClosingPriceType closingPriceType, long adjustedClosingPrice, AdjustedClosingPriceReason adjustedClosingPriceReason, long pctChange, long vwap, BigInteger noTrades, BigInteger totalVolume, long totalValue, long openingPrice, long maxPrice, long minPrice, boolean isCorrection) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.lastTradedPrice = lastTradedPrice;
        this.closingPrice = closingPrice;
        this.closingPriceType = closingPriceType;
        this.adjustedClosingPrice = adjustedClosingPrice;
        this.adjustedClosingPriceReason = adjustedClosingPriceReason;
        this.pctChange = pctChange;
        this.vwap = vwap;
        this.noTrades = noTrades;
        this.totalVolume = totalVolume;
        this.totalValue = totalValue;
        this.openingPrice = openingPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.isCorrection = isCorrection;
    }
    
    public InstrumentSummary(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.lastTradedPrice = BendecUtils.int64FromByteArray(bytes, offset + 46);
        this.closingPrice = BendecUtils.int64FromByteArray(bytes, offset + 54);
        this.closingPriceType = ClosingPriceType.getClosingPriceType(bytes, offset + 62);
        this.adjustedClosingPrice = BendecUtils.int64FromByteArray(bytes, offset + 63);
        this.adjustedClosingPriceReason = AdjustedClosingPriceReason.getAdjustedClosingPriceReason(bytes, offset + 71);
        this.pctChange = BendecUtils.int64FromByteArray(bytes, offset + 72);
        this.vwap = BendecUtils.int64FromByteArray(bytes, offset + 80);
        this.noTrades = BendecUtils.uInt64FromByteArray(bytes, offset + 88);
        this.totalVolume = BendecUtils.uInt64FromByteArray(bytes, offset + 96);
        this.totalValue = BendecUtils.int64FromByteArray(bytes, offset + 104);
        this.openingPrice = BendecUtils.int64FromByteArray(bytes, offset + 112);
        this.maxPrice = BendecUtils.int64FromByteArray(bytes, offset + 120);
        this.minPrice = BendecUtils.int64FromByteArray(bytes, offset + 128);
        this.isCorrection = BendecUtils.booleanFromByteArray(bytes, offset + 136);
    }
    
    public InstrumentSummary(byte[] bytes) {
        this(bytes, 0);
    }
    
    public InstrumentSummary() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Identifier of the instrument.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    }
    
    /**
     * @return Last Traded Price (LTP).
     */
    public long getLastTradedPrice() {
        return this.lastTradedPrice;
    }
    
    /**
     * @return Closing Price (CP).
     */
    public long getClosingPrice() {
        return this.closingPrice;
    }
    
    /**
     * @return Closing Price Type. For OffBook means last transaction price.
     */
    public ClosingPriceType getClosingPriceType() {
        return this.closingPriceType;
    }
    
    /**
     * @return Adjusted Closing Price (ACP).
     */
    public long getAdjustedClosingPrice() {
        return this.adjustedClosingPrice;
    }
    
    /**
     * @return Reason for adjusting the Closing Price.
     */
    public AdjustedClosingPriceReason getAdjustedClosingPriceReason() {
        return this.adjustedClosingPriceReason;
    }
    
    /**
     * @return Percentage change from last session closing price.
     */
    public long getPctChange() {
        return this.pctChange;
    }
    
    /**
     * @return Volume-weighted average price for the market model in which there is an instrument.
     */
    public long getVwap() {
        return this.vwap;
    }
    
    /**
     * @return Total number of transactions on the current trading day.
     */
    public BigInteger getNoTrades() {
        return this.noTrades;
    }
    
    /**
     * @return Total transaction volume.
     */
    public BigInteger getTotalVolume() {
        return this.totalVolume;
    }
    
    /**
     * @return Total transaction value.
     */
    public long getTotalValue() {
        return this.totalValue;
    }
    
    /**
     * @return The price of the first trade on the current trading day.
     */
    public long getOpeningPrice() {
        return this.openingPrice;
    }
    
    /**
     * @return Highest price of the instrument on the current trading day.
     */
    public long getMaxPrice() {
        return this.maxPrice;
    }
    
    /**
     * @return Lowest price of the instrument on the current trading day.
     */
    public long getMinPrice() {
        return this.minPrice;
    }
    
    /**
     * @return Previous message correction flag.
     */
    public boolean getIsCorrection() {
        return this.isCorrection;
    }

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param instrumentId Identifier of the instrument.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }
    
    /**
     * @param lastTradedPrice Last Traded Price (LTP).
     */
    public void setLastTradedPrice(long lastTradedPrice) {
        this.lastTradedPrice = lastTradedPrice;
    }
    
    /**
     * @param closingPrice Closing Price (CP).
     */
    public void setClosingPrice(long closingPrice) {
        this.closingPrice = closingPrice;
    }
    
    /**
     * @param closingPriceType Closing Price Type. For OffBook means last transaction price.
     */
    public void setClosingPriceType(ClosingPriceType closingPriceType) {
        this.closingPriceType = closingPriceType;
    }
    
    /**
     * @param adjustedClosingPrice Adjusted Closing Price (ACP).
     */
    public void setAdjustedClosingPrice(long adjustedClosingPrice) {
        this.adjustedClosingPrice = adjustedClosingPrice;
    }
    
    /**
     * @param adjustedClosingPriceReason Reason for adjusting the Closing Price.
     */
    public void setAdjustedClosingPriceReason(AdjustedClosingPriceReason adjustedClosingPriceReason) {
        this.adjustedClosingPriceReason = adjustedClosingPriceReason;
    }
    
    /**
     * @param pctChange Percentage change from last session closing price.
     */
    public void setPctChange(long pctChange) {
        this.pctChange = pctChange;
    }
    
    /**
     * @param vwap Volume-weighted average price for the market model in which there is an instrument.
     */
    public void setVwap(long vwap) {
        this.vwap = vwap;
    }
    
    /**
     * @param noTrades Total number of transactions on the current trading day.
     */
    public void setNoTrades(BigInteger noTrades) {
        this.noTrades = noTrades;
    }
    
    /**
     * @param totalVolume Total transaction volume.
     */
    public void setTotalVolume(BigInteger totalVolume) {
        this.totalVolume = totalVolume;
    }
    
    /**
     * @param totalValue Total transaction value.
     */
    public void setTotalValue(long totalValue) {
        this.totalValue = totalValue;
    }
    
    /**
     * @param openingPrice The price of the first trade on the current trading day.
     */
    public void setOpeningPrice(long openingPrice) {
        this.openingPrice = openingPrice;
    }
    
    /**
     * @param maxPrice Highest price of the instrument on the current trading day.
     */
    public void setMaxPrice(long maxPrice) {
        this.maxPrice = maxPrice;
    }
    
    /**
     * @param minPrice Lowest price of the instrument on the current trading day.
     */
    public void setMinPrice(long minPrice) {
        this.minPrice = minPrice;
    }
    
    /**
     * @param isCorrection Previous message correction flag.
     */
    public void setIsCorrection(boolean isCorrection) {
        this.isCorrection = isCorrection;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.int64ToByteArray(this.lastTradedPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.closingPrice));
        closingPriceType.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.adjustedClosingPrice));
        adjustedClosingPriceReason.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.pctChange));
        buffer.put(BendecUtils.int64ToByteArray(this.vwap));
        buffer.put(BendecUtils.uInt64ToByteArray(this.noTrades));
        buffer.put(BendecUtils.uInt64ToByteArray(this.totalVolume));
        buffer.put(BendecUtils.int64ToByteArray(this.totalValue));
        buffer.put(BendecUtils.int64ToByteArray(this.openingPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.maxPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.minPrice));
        buffer.put(BendecUtils.booleanToByteArray(this.isCorrection));
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.int64ToByteArray(this.lastTradedPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.closingPrice));
        closingPriceType.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.adjustedClosingPrice));
        adjustedClosingPriceReason.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.pctChange));
        buffer.put(BendecUtils.int64ToByteArray(this.vwap));
        buffer.put(BendecUtils.uInt64ToByteArray(this.noTrades));
        buffer.put(BendecUtils.uInt64ToByteArray(this.totalVolume));
        buffer.put(BendecUtils.int64ToByteArray(this.totalValue));
        buffer.put(BendecUtils.int64ToByteArray(this.openingPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.maxPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.minPrice));
        buffer.put(BendecUtils.booleanToByteArray(this.isCorrection));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        lastTradedPrice,
        closingPrice,
        closingPriceType,
        adjustedClosingPrice,
        adjustedClosingPriceReason,
        pctChange,
        vwap,
        noTrades,
        totalVolume,
        totalValue,
        openingPrice,
        maxPrice,
        minPrice,
        isCorrection);
    }
    
    @Override
    public String toString() {
        return "InstrumentSummary {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", lastTradedPrice=" + lastTradedPrice +
            ", closingPrice=" + closingPrice +
            ", closingPriceType=" + closingPriceType +
            ", adjustedClosingPrice=" + adjustedClosingPrice +
            ", adjustedClosingPriceReason=" + adjustedClosingPriceReason +
            ", pctChange=" + pctChange +
            ", vwap=" + vwap +
            ", noTrades=" + noTrades +
            ", totalVolume=" + totalVolume +
            ", totalValue=" + totalValue +
            ", openingPrice=" + openingPrice +
            ", maxPrice=" + maxPrice +
            ", minPrice=" + minPrice +
            ", isCorrection=" + isCorrection +
            "}";
    }
}