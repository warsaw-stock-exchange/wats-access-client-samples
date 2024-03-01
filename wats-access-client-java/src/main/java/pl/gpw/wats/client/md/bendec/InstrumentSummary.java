package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>InstrumentSummary</h2>
 * <p>Provides brief instrument summary.</p>
 * <p>Byte length: 133</p>
 * <p>Header header - Message header. | size 39</p>
 * <p>ElementId > long (u32) instrumentId - Identifier of the CLOB instrument. | size 4</p>
 * <p>Price > long (i64) lastTradedPrice - Last Traded Price (LTP). | size 8</p>
 * <p>Price > long (i64) closingPrice - Closing Price (CP). | size 8</p>
 * <p>ClosingPriceType closingPriceType - Closing Price Type. | size 1</p>
 * <p>Price > long (i64) adjustedClosingPrice - Adjusted Closing Price (ACP). | size 8</p>
 * <p>AdjustedClosingPriceReason adjustedClosingPriceReason - Adjusted Closing Price Reason. | size 1</p>
 * <p>PercentageChange > long (i64) pctChange - Percentage change. | size 8</p>
 * <p>Price > long (i64) VWAP - Volume-weighted average price. | size 8</p>
 * <p>u64 > BigInteger noTrades - Total number of transations on the current trading day. | size 8</p>
 * <p>Quantity > BigInteger (u64) totalVolume - Total transaction volume. | size 8</p>
 * <p>Value > long (i64) totalValue - Total transaction value. | size 8</p>
 * <p>Price > long (i64) openingPrice - The price of the first trade on the current trading day. | size 8</p>
 * <p>Price > long (i64) maxPrice - Highest price of the instrument on the current trading day | size 8</p>
 * <p>Price > long (i64) minPrice - Lowest price of the instrument on the current trading day | size 8</p>
 * */

public class InstrumentSummary implements ByteSerializable, Message {

    private Header header;
    private long instrumentId;
    private long lastTradedPrice;
    private long closingPrice;
    private ClosingPriceType closingPriceType;
    private long adjustedClosingPrice;
    private AdjustedClosingPriceReason adjustedClosingPriceReason;
    private long pctChange;
    private long VWAP;
    private BigInteger noTrades;
    private BigInteger totalVolume;
    private long totalValue;
    private long openingPrice;
    private long maxPrice;
    private long minPrice;
    public static final int byteLength = 133;

    public InstrumentSummary(Header header, long instrumentId, long lastTradedPrice, long closingPrice, ClosingPriceType closingPriceType, long adjustedClosingPrice, AdjustedClosingPriceReason adjustedClosingPriceReason, long pctChange, long VWAP, BigInteger noTrades, BigInteger totalVolume, long totalValue, long openingPrice, long maxPrice, long minPrice) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.lastTradedPrice = lastTradedPrice;
        this.closingPrice = closingPrice;
        this.closingPriceType = closingPriceType;
        this.adjustedClosingPrice = adjustedClosingPrice;
        this.adjustedClosingPriceReason = adjustedClosingPriceReason;
        this.pctChange = pctChange;
        this.VWAP = VWAP;
        this.noTrades = noTrades;
        this.totalVolume = totalVolume;
        this.totalValue = totalValue;
        this.openingPrice = openingPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.INSTRUMENTSUMMARY);
    }

    public InstrumentSummary(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 39);
        this.lastTradedPrice = BendecUtils.int64FromByteArray(bytes, offset + 43);
        this.closingPrice = BendecUtils.int64FromByteArray(bytes, offset + 51);
        this.closingPriceType = ClosingPriceType.getClosingPriceType(bytes, offset + 59);
        this.adjustedClosingPrice = BendecUtils.int64FromByteArray(bytes, offset + 60);
        this.adjustedClosingPriceReason = AdjustedClosingPriceReason.getAdjustedClosingPriceReason(bytes, offset + 68);
        this.pctChange = BendecUtils.int64FromByteArray(bytes, offset + 69);
        this.VWAP = BendecUtils.int64FromByteArray(bytes, offset + 77);
        this.noTrades = BendecUtils.uInt64FromByteArray(bytes, offset + 85);
        this.totalVolume = BendecUtils.uInt64FromByteArray(bytes, offset + 93);
        this.totalValue = BendecUtils.int64FromByteArray(bytes, offset + 101);
        this.openingPrice = BendecUtils.int64FromByteArray(bytes, offset + 109);
        this.maxPrice = BendecUtils.int64FromByteArray(bytes, offset + 117);
        this.minPrice = BendecUtils.int64FromByteArray(bytes, offset + 125);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.INSTRUMENTSUMMARY);
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
    };
    /**
     * @return Identifier of the CLOB instrument.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    };
    /**
     * @return Last Traded Price (LTP).
     */
    public long getLastTradedPrice() {
        return this.lastTradedPrice;
    };
    /**
     * @return Closing Price (CP).
     */
    public long getClosingPrice() {
        return this.closingPrice;
    };
    /**
     * @return Closing Price Type.
     */
    public ClosingPriceType getClosingPriceType() {
        return this.closingPriceType;
    };
    /**
     * @return Adjusted Closing Price (ACP).
     */
    public long getAdjustedClosingPrice() {
        return this.adjustedClosingPrice;
    };
    /**
     * @return Adjusted Closing Price Reason.
     */
    public AdjustedClosingPriceReason getAdjustedClosingPriceReason() {
        return this.adjustedClosingPriceReason;
    };
    /**
     * @return Percentage change.
     */
    public long getPctChange() {
        return this.pctChange;
    };
    /**
     * @return Volume-weighted average price.
     */
    public long getVWAP() {
        return this.VWAP;
    };
    /**
     * @return Total number of transations on the current trading day.
     */
    public BigInteger getNoTrades() {
        return this.noTrades;
    };
    /**
     * @return Total transaction volume.
     */
    public BigInteger getTotalVolume() {
        return this.totalVolume;
    };
    /**
     * @return Total transaction value.
     */
    public long getTotalValue() {
        return this.totalValue;
    };
    /**
     * @return The price of the first trade on the current trading day.
     */
    public long getOpeningPrice() {
        return this.openingPrice;
    };
    /**
     * @return Highest price of the instrument on the current trading day
     */
    public long getMaxPrice() {
        return this.maxPrice;
    };
    /**
     * @return Lowest price of the instrument on the current trading day
     */
    public long getMinPrice() {
        return this.minPrice;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param instrumentId Identifier of the CLOB instrument.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    };
    /**
     * @param lastTradedPrice Last Traded Price (LTP).
     */
    public void setLastTradedPrice(long lastTradedPrice) {
        this.lastTradedPrice = lastTradedPrice;
    };
    /**
     * @param closingPrice Closing Price (CP).
     */
    public void setClosingPrice(long closingPrice) {
        this.closingPrice = closingPrice;
    };
    /**
     * @param closingPriceType Closing Price Type.
     */
    public void setClosingPriceType(ClosingPriceType closingPriceType) {
        this.closingPriceType = closingPriceType;
    };
    /**
     * @param adjustedClosingPrice Adjusted Closing Price (ACP).
     */
    public void setAdjustedClosingPrice(long adjustedClosingPrice) {
        this.adjustedClosingPrice = adjustedClosingPrice;
    };
    /**
     * @param adjustedClosingPriceReason Adjusted Closing Price Reason.
     */
    public void setAdjustedClosingPriceReason(AdjustedClosingPriceReason adjustedClosingPriceReason) {
        this.adjustedClosingPriceReason = adjustedClosingPriceReason;
    };
    /**
     * @param pctChange Percentage change.
     */
    public void setPctChange(long pctChange) {
        this.pctChange = pctChange;
    };
    /**
     * @param VWAP Volume-weighted average price.
     */
    public void setVWAP(long VWAP) {
        this.VWAP = VWAP;
    };
    /**
     * @param noTrades Total number of transations on the current trading day.
     */
    public void setNoTrades(BigInteger noTrades) {
        this.noTrades = noTrades;
    };
    /**
     * @param totalVolume Total transaction volume.
     */
    public void setTotalVolume(BigInteger totalVolume) {
        this.totalVolume = totalVolume;
    };
    /**
     * @param totalValue Total transaction value.
     */
    public void setTotalValue(long totalValue) {
        this.totalValue = totalValue;
    };
    /**
     * @param openingPrice The price of the first trade on the current trading day.
     */
    public void setOpeningPrice(long openingPrice) {
        this.openingPrice = openingPrice;
    };
    /**
     * @param maxPrice Highest price of the instrument on the current trading day
     */
    public void setMaxPrice(long maxPrice) {
        this.maxPrice = maxPrice;
    };
    /**
     * @param minPrice Lowest price of the instrument on the current trading day
     */
    public void setMinPrice(long minPrice) {
        this.minPrice = minPrice;
    };


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
        buffer.put(BendecUtils.int64ToByteArray(this.VWAP));
        buffer.put(BendecUtils.uInt64ToByteArray(this.noTrades));
        buffer.put(BendecUtils.uInt64ToByteArray(this.totalVolume));
        buffer.put(BendecUtils.int64ToByteArray(this.totalValue));
        buffer.put(BendecUtils.int64ToByteArray(this.openingPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.maxPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.minPrice));
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
        buffer.put(BendecUtils.int64ToByteArray(this.VWAP));
        buffer.put(BendecUtils.uInt64ToByteArray(this.noTrades));
        buffer.put(BendecUtils.uInt64ToByteArray(this.totalVolume));
        buffer.put(BendecUtils.int64ToByteArray(this.totalValue));
        buffer.put(BendecUtils.int64ToByteArray(this.openingPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.maxPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.minPrice));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, instrumentId, lastTradedPrice, closingPrice, closingPriceType, adjustedClosingPrice, adjustedClosingPriceReason, pctChange, VWAP, noTrades, totalVolume, totalValue, openingPrice, maxPrice, minPrice);
    }

    @Override
    public String toString() {
        return "InstrumentSummary{" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", lastTradedPrice=" + lastTradedPrice +
            ", closingPrice=" + closingPrice +
            ", closingPriceType=" + closingPriceType +
            ", adjustedClosingPrice=" + adjustedClosingPrice +
            ", adjustedClosingPriceReason=" + adjustedClosingPriceReason +
            ", pctChange=" + pctChange +
            ", VWAP=" + VWAP +
            ", noTrades=" + noTrades +
            ", totalVolume=" + totalVolume +
            ", totalValue=" + totalValue +
            ", openingPrice=" + openingPrice +
            ", maxPrice=" + maxPrice +
            ", minPrice=" + minPrice +
            '}';
        }
}
