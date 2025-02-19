package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>TradeCaptureReportDual</h2>
 * <p>Trade Capture Report - dual sided.</p>
 * <p>Byte length: 271</p>
 * <p>Header header - Header. | size 16</p>
 * <p>ElementId > long (u32) instrumentId - ID of the instrument included in the order. | size 4</p>
 * <p>TradeReportId > String (u8[]) tradeReportId - Unique identifier of trade capture report. | size 21</p>
 * <p>TradeId > long (u32) tradeId - The unique ID assigned to the trade entity once it is received or matched by the exchange or central counterparty. | size 4</p>
 * <p>TradeReportTransType tradeReportTransType - Identifies Trade Report message transaction type. | size 1</p>
 * <p>TradeReportType tradeReportType - Type of Trade Report. | size 1</p>
 * <p>TradeType tradeType - Type of trade. | size 1</p>
 * <p>AlgorithmicTradeIndicator algorithmicTradeIndicator - Indicates algorithmic trade. | size 1</p>
 * <p>ExecType execType - Type of execution being reported. Uses subset of ExecType for trade capture reports. | size 1</p>
 * <p>TradeReportRefID > String (u8[]) tradeReportRefId - Reference identifier used with Cancel and Replace transaction types. The TradeReportID that is being referenced for trade correction or cancelation. | size 21</p>
 * <p>Quantity > BigInteger (u64) lastQty - Quantity (e.g. shares) bought/sold on this (last) fill. | size 8</p>
 * <p>Price > long (i64) lastPx - Price of this (last) fill. | size 8</p>
 * <p>Date > long (u32) settlementDate - Settlement date of the trade is equal to current date plus actual settlement offset calendar days. | size 4</p>
 * <p>TcrParty tcrPartyBuy - TCR party - buy side. | size 90</p>
 * <p>TcrParty tcrPartySell - TCR party - sell side. | size 90</p>
 */
public class TradeCaptureReportDual implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private String tradeReportId;
    private long tradeId;
    private TradeReportTransType tradeReportTransType;
    private TradeReportType tradeReportType;
    private TradeType tradeType;
    private AlgorithmicTradeIndicator algorithmicTradeIndicator;
    private ExecType execType;
    private String tradeReportRefId;
    private BigInteger lastQty;
    private long lastPx;
    private long settlementDate;
    private TcrParty tcrPartyBuy;
    private TcrParty tcrPartySell;
    public static final int byteLength = 271;
    
    public TradeCaptureReportDual(Header header, long instrumentId, String tradeReportId, long tradeId, TradeReportTransType tradeReportTransType, TradeReportType tradeReportType, TradeType tradeType, AlgorithmicTradeIndicator algorithmicTradeIndicator, ExecType execType, String tradeReportRefId, BigInteger lastQty, long lastPx, long settlementDate, TcrParty tcrPartyBuy, TcrParty tcrPartySell) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.tradeReportId = tradeReportId;
        this.tradeId = tradeId;
        this.tradeReportTransType = tradeReportTransType;
        this.tradeReportType = tradeReportType;
        this.tradeType = tradeType;
        this.algorithmicTradeIndicator = algorithmicTradeIndicator;
        this.execType = execType;
        this.tradeReportRefId = tradeReportRefId;
        this.lastQty = lastQty;
        this.lastPx = lastPx;
        this.settlementDate = settlementDate;
        this.tcrPartyBuy = tcrPartyBuy;
        this.tcrPartySell = tcrPartySell;
    }
    
    public TradeCaptureReportDual(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 16);
        this.tradeReportId = BendecUtils.stringFromByteArray(bytes, offset + 20, 21);
        this.tradeId = BendecUtils.uInt32FromByteArray(bytes, offset + 41);
        this.tradeReportTransType = TradeReportTransType.getTradeReportTransType(bytes, offset + 45);
        this.tradeReportType = TradeReportType.getTradeReportType(bytes, offset + 46);
        this.tradeType = TradeType.getTradeType(bytes, offset + 47);
        this.algorithmicTradeIndicator = AlgorithmicTradeIndicator.getAlgorithmicTradeIndicator(bytes, offset + 48);
        this.execType = ExecType.getExecType(bytes, offset + 49);
        this.tradeReportRefId = BendecUtils.stringFromByteArray(bytes, offset + 50, 21);
        this.lastQty = BendecUtils.uInt64FromByteArray(bytes, offset + 71);
        this.lastPx = BendecUtils.int64FromByteArray(bytes, offset + 79);
        this.settlementDate = BendecUtils.uInt32FromByteArray(bytes, offset + 87);
        this.tcrPartyBuy = new TcrParty(bytes, offset + 91);
        this.tcrPartySell = new TcrParty(bytes, offset + 181);
    }
    
    public TradeCaptureReportDual(byte[] bytes) {
        this(bytes, 0);
    }
    
    public TradeCaptureReportDual() {
    }
    
    /**
     * @return Header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return ID of the instrument included in the order.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    }
    
    /**
     * @return Unique identifier of trade capture report.
     */
    public String getTradeReportId() {
        return this.tradeReportId;
    }
    
    /**
     * @return The unique ID assigned to the trade entity once it is received or matched by the exchange or central counterparty.
     */
    public long getTradeId() {
        return this.tradeId;
    }
    
    /**
     * @return Identifies Trade Report message transaction type.
     */
    public TradeReportTransType getTradeReportTransType() {
        return this.tradeReportTransType;
    }
    
    /**
     * @return Type of Trade Report.
     */
    public TradeReportType getTradeReportType() {
        return this.tradeReportType;
    }
    
    /**
     * @return Type of trade.
     */
    public TradeType getTradeType() {
        return this.tradeType;
    }
    
    /**
     * @return Indicates algorithmic trade.
     */
    public AlgorithmicTradeIndicator getAlgorithmicTradeIndicator() {
        return this.algorithmicTradeIndicator;
    }
    
    /**
     * @return Type of execution being reported. Uses subset of ExecType for trade capture reports.
     */
    public ExecType getExecType() {
        return this.execType;
    }
    
    /**
     * @return Reference identifier used with Cancel and Replace transaction types. The TradeReportID that is being referenced for trade correction or cancelation.
     */
    public String getTradeReportRefId() {
        return this.tradeReportRefId;
    }
    
    /**
     * @return Quantity (e.g. shares) bought/sold on this (last) fill.
     */
    public BigInteger getLastQty() {
        return this.lastQty;
    }
    
    /**
     * @return Price of this (last) fill.
     */
    public long getLastPx() {
        return this.lastPx;
    }
    
    /**
     * @return Settlement date of the trade is equal to current date plus actual settlement offset calendar days.
     */
    public long getSettlementDate() {
        return this.settlementDate;
    }
    
    /**
     * @return TCR party - buy side.
     */
    public TcrParty getTcrPartyBuy() {
        return this.tcrPartyBuy;
    }
    
    /**
     * @return TCR party - sell side.
     */
    public TcrParty getTcrPartySell() {
        return this.tcrPartySell;
    }
    
    /**
     * @param header Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param instrumentId ID of the instrument included in the order.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }
    
    /**
     * @param tradeReportId Unique identifier of trade capture report.
     */
    public void setTradeReportId(String tradeReportId) {
        this.tradeReportId = tradeReportId;
    }
    
    /**
     * @param tradeId The unique ID assigned to the trade entity once it is received or matched by the exchange or central counterparty.
     */
    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }
    
    /**
     * @param tradeReportTransType Identifies Trade Report message transaction type.
     */
    public void setTradeReportTransType(TradeReportTransType tradeReportTransType) {
        this.tradeReportTransType = tradeReportTransType;
    }
    
    /**
     * @param tradeReportType Type of Trade Report.
     */
    public void setTradeReportType(TradeReportType tradeReportType) {
        this.tradeReportType = tradeReportType;
    }
    
    /**
     * @param tradeType Type of trade.
     */
    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }
    
    /**
     * @param algorithmicTradeIndicator Indicates algorithmic trade.
     */
    public void setAlgorithmicTradeIndicator(AlgorithmicTradeIndicator algorithmicTradeIndicator) {
        this.algorithmicTradeIndicator = algorithmicTradeIndicator;
    }
    
    /**
     * @param execType Type of execution being reported. Uses subset of ExecType for trade capture reports.
     */
    public void setExecType(ExecType execType) {
        this.execType = execType;
    }
    
    /**
     * @param tradeReportRefId Reference identifier used with Cancel and Replace transaction types. The TradeReportID that is being referenced for trade correction or cancelation.
     */
    public void setTradeReportRefId(String tradeReportRefId) {
        this.tradeReportRefId = tradeReportRefId;
    }
    
    /**
     * @param lastQty Quantity (e.g. shares) bought/sold on this (last) fill.
     */
    public void setLastQty(BigInteger lastQty) {
        this.lastQty = lastQty;
    }
    
    /**
     * @param lastPx Price of this (last) fill.
     */
    public void setLastPx(long lastPx) {
        this.lastPx = lastPx;
    }
    
    /**
     * @param settlementDate Settlement date of the trade is equal to current date plus actual settlement offset calendar days.
     */
    public void setSettlementDate(long settlementDate) {
        this.settlementDate = settlementDate;
    }
    
    /**
     * @param tcrPartyBuy TCR party - buy side.
     */
    public void setTcrPartyBuy(TcrParty tcrPartyBuy) {
        this.tcrPartyBuy = tcrPartyBuy;
    }
    
    /**
     * @param tcrPartySell TCR party - sell side.
     */
    public void setTcrPartySell(TcrParty tcrPartySell) {
        this.tcrPartySell = tcrPartySell;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.stringToByteArray(this.tradeReportId, 21));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradeId));
        tradeReportTransType.toBytes(buffer);
        tradeReportType.toBytes(buffer);
        tradeType.toBytes(buffer);
        algorithmicTradeIndicator.toBytes(buffer);
        execType.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.tradeReportRefId, 21));
        buffer.put(BendecUtils.uInt64ToByteArray(this.lastQty));
        buffer.put(BendecUtils.int64ToByteArray(this.lastPx));
        buffer.put(BendecUtils.uInt32ToByteArray(this.settlementDate));
        tcrPartyBuy.toBytes(buffer);
        tcrPartySell.toBytes(buffer);
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.stringToByteArray(this.tradeReportId, 21));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradeId));
        tradeReportTransType.toBytes(buffer);
        tradeReportType.toBytes(buffer);
        tradeType.toBytes(buffer);
        algorithmicTradeIndicator.toBytes(buffer);
        execType.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.tradeReportRefId, 21));
        buffer.put(BendecUtils.uInt64ToByteArray(this.lastQty));
        buffer.put(BendecUtils.int64ToByteArray(this.lastPx));
        buffer.put(BendecUtils.uInt32ToByteArray(this.settlementDate));
        tcrPartyBuy.toBytes(buffer);
        tcrPartySell.toBytes(buffer);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        tradeReportId,
        tradeId,
        tradeReportTransType,
        tradeReportType,
        tradeType,
        algorithmicTradeIndicator,
        execType,
        tradeReportRefId,
        lastQty,
        lastPx,
        settlementDate,
        tcrPartyBuy,
        tcrPartySell);
    }
    
    @Override
    public String toString() {
        return "TradeCaptureReportDual {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", tradeReportId=" + tradeReportId +
            ", tradeId=" + tradeId +
            ", tradeReportTransType=" + tradeReportTransType +
            ", tradeReportType=" + tradeReportType +
            ", tradeType=" + tradeType +
            ", algorithmicTradeIndicator=" + algorithmicTradeIndicator +
            ", execType=" + execType +
            ", tradeReportRefId=" + tradeReportRefId +
            ", lastQty=" + lastQty +
            ", lastPx=" + lastPx +
            ", settlementDate=" + settlementDate +
            ", tcrPartyBuy=" + tcrPartyBuy +
            ", tcrPartySell=" + tcrPartySell +
            "}";
    }
}