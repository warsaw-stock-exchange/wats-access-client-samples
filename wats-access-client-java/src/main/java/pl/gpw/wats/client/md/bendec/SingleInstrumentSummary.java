package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>SingleInstrumentSummary</h2>
 * <p>Provides brief instrument summary.</p>
 * <p>Byte length: 119</p>
 * <p>ElementId > long (u32) instrumentId - Identifier of the instrument. | size 4</p>
 * <p>MarketModelType marketModelType - Market model type. | size 1</p>
 * <p>Price > long (i64) lastTradedPrice - Last Traded Price (LTP). | size 8</p>
 * <p>Price > long (i64) closingPrice - Closing Price (CP). | size 8</p>
 * <p>ClosingPriceType closingPriceType - Closing Price Type. | size 1</p>
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
 * <p>Price > long (i64) settlementPrice - Settlement price. | size 8</p>
 * <p>Price > long (i64) settlementValue - Settlement value. | size 8</p>
 * <p>Date > long (u32) endTradingDate - End of trading date. | size 4</p>
 * <p>Date > long (u32) lastTradeDate - The date of execution of the last trade for the given instrument. | size 4</p>
 */
public class SingleInstrumentSummary implements ByteSerializable {
    private long instrumentId;
    private MarketModelType marketModelType;
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
    private long settlementPrice;
    private long settlementValue;
    private long endTradingDate;
    private long lastTradeDate;
    public static final int byteLength = 119;
    
    public SingleInstrumentSummary(long instrumentId, MarketModelType marketModelType, long lastTradedPrice, long closingPrice, ClosingPriceType closingPriceType, long adjustedClosingPrice, AdjustedClosingPriceReason adjustedClosingPriceReason, long pctChange, long vwap, BigInteger noTrades, BigInteger totalVolume, long totalValue, long openingPrice, long maxPrice, long minPrice, long settlementPrice, long settlementValue, long endTradingDate, long lastTradeDate) {
        this.instrumentId = instrumentId;
        this.marketModelType = marketModelType;
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
        this.settlementPrice = settlementPrice;
        this.settlementValue = settlementValue;
        this.endTradingDate = endTradingDate;
        this.lastTradeDate = lastTradeDate;
    }
    
    public SingleInstrumentSummary(byte[] bytes, int offset) {
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset);
        this.marketModelType = MarketModelType.getMarketModelType(bytes, offset + 4);
        this.lastTradedPrice = BendecUtils.int64FromByteArray(bytes, offset + 5);
        this.closingPrice = BendecUtils.int64FromByteArray(bytes, offset + 13);
        this.closingPriceType = ClosingPriceType.getClosingPriceType(bytes, offset + 21);
        this.adjustedClosingPrice = BendecUtils.int64FromByteArray(bytes, offset + 22);
        this.adjustedClosingPriceReason = AdjustedClosingPriceReason.getAdjustedClosingPriceReason(bytes, offset + 30);
        this.pctChange = BendecUtils.int64FromByteArray(bytes, offset + 31);
        this.vwap = BendecUtils.int64FromByteArray(bytes, offset + 39);
        this.noTrades = BendecUtils.uInt64FromByteArray(bytes, offset + 47);
        this.totalVolume = BendecUtils.uInt64FromByteArray(bytes, offset + 55);
        this.totalValue = BendecUtils.int64FromByteArray(bytes, offset + 63);
        this.openingPrice = BendecUtils.int64FromByteArray(bytes, offset + 71);
        this.maxPrice = BendecUtils.int64FromByteArray(bytes, offset + 79);
        this.minPrice = BendecUtils.int64FromByteArray(bytes, offset + 87);
        this.settlementPrice = BendecUtils.int64FromByteArray(bytes, offset + 95);
        this.settlementValue = BendecUtils.int64FromByteArray(bytes, offset + 103);
        this.endTradingDate = BendecUtils.uInt32FromByteArray(bytes, offset + 111);
        this.lastTradeDate = BendecUtils.uInt32FromByteArray(bytes, offset + 115);
    }
    
    public SingleInstrumentSummary(byte[] bytes) {
        this(bytes, 0);
    }
    
    public SingleInstrumentSummary() {
    }
    
    /**
     * @return Identifier of the instrument.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    }
    
    /**
     * @return Market model type.
     */
    public MarketModelType getMarketModelType() {
        return this.marketModelType;
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
     * @return Closing Price Type.
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
     * @return Settlement price.
     */
    public long getSettlementPrice() {
        return this.settlementPrice;
    }
    
    /**
     * @return Settlement value.
     */
    public long getSettlementValue() {
        return this.settlementValue;
    }
    
    /**
     * @return End of trading date.
     */
    public long getEndTradingDate() {
        return this.endTradingDate;
    }
    
    /**
     * @return The date of execution of the last trade for the given instrument.
     */
    public long getLastTradeDate() {
        return this.lastTradeDate;
    }
    
    /**
     * @param instrumentId Identifier of the instrument.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }
    
    /**
     * @param marketModelType Market model type.
     */
    public void setMarketModelType(MarketModelType marketModelType) {
        this.marketModelType = marketModelType;
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
     * @param closingPriceType Closing Price Type.
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
     * @param settlementPrice Settlement price.
     */
    public void setSettlementPrice(long settlementPrice) {
        this.settlementPrice = settlementPrice;
    }
    
    /**
     * @param settlementValue Settlement value.
     */
    public void setSettlementValue(long settlementValue) {
        this.settlementValue = settlementValue;
    }
    
    /**
     * @param endTradingDate End of trading date.
     */
    public void setEndTradingDate(long endTradingDate) {
        this.endTradingDate = endTradingDate;
    }
    
    /**
     * @param lastTradeDate The date of execution of the last trade for the given instrument.
     */
    public void setLastTradeDate(long lastTradeDate) {
        this.lastTradeDate = lastTradeDate;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        marketModelType.toBytes(buffer);
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
        buffer.put(BendecUtils.int64ToByteArray(this.settlementPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.settlementValue));
        buffer.put(BendecUtils.uInt32ToByteArray(this.endTradingDate));
        buffer.put(BendecUtils.uInt32ToByteArray(this.lastTradeDate));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        marketModelType.toBytes(buffer);
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
        buffer.put(BendecUtils.int64ToByteArray(this.settlementPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.settlementValue));
        buffer.put(BendecUtils.uInt32ToByteArray(this.endTradingDate));
        buffer.put(BendecUtils.uInt32ToByteArray(this.lastTradeDate));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(instrumentId,
        marketModelType,
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
        settlementPrice,
        settlementValue,
        endTradingDate,
        lastTradeDate);
    }
    
    @Override
    public String toString() {
        return "SingleInstrumentSummary {" +
            "instrumentId=" + instrumentId +
            ", marketModelType=" + marketModelType +
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
            ", settlementPrice=" + settlementPrice +
            ", settlementValue=" + settlementValue +
            ", endTradingDate=" + endTradingDate +
            ", lastTradeDate=" + lastTradeDate +
            "}";
    }
}