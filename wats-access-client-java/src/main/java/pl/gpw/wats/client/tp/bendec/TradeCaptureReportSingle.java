package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>TradeCaptureReportSingle</h2>
 * <p>Trade Capture Report - single side.</p>
 * <p>Byte length: 206</p>
 * <p>Header header - Header. | size 16</p>
 * <p>ElementId > long (u32) instrumentId - ID of the instrument included in the order. | size 4</p>
 * <p>TradeReportId > String (u8[]) tradeReportId - Unique identifier of the trade capture report. | size 21</p>
 * <p>OrderId > BigInteger (u64) secondaryTradeReportId - ID of the trade capture report. | size 8</p>
 * <p>TradeId > long (u32) tradeId - The unique ID assigned to the trade entity once it is received or matched by the exchange or central counterparty. | size 4</p>
 * <p>TradeReportTransType tradeReportTransType - Identifies Trade Report message transaction type. | size 1</p>
 * <p>TradeReportType tradeReportType - Type of Trade Report. | size 1</p>
 * <p>TradeType tradeType - Type of trade. | size 1</p>
 * <p>AlgorithmicTradeIndicator algorithmicTradeIndicator - Indicates algorithmic trader. | size 1</p>
 * <p>ExecType execType - Type of execution being reported. Uses subset of ExecType for trade capture reports. | size 1</p>
 * <p>TradeReportRefID > String (u8[]) tradeReportRefId - Reference identifier used with Cancel and Replace transaction types. The TradeReportID that is being referenced for trade correction or cancelation. | size 21</p>
 * <p>Quantity > BigInteger (u64) lastQty - Quantity (e.g. shares) bought/sold on this (last) fill. | size 8</p>
 * <p>Price > long (i64) lastPx - Price of this (last) fill. | size 8</p>
 * <p>Date > long (u32) settlementDate - Settlement date of the trade is equal to current date plus actual settlement offset calendar days. | size 4</p>
 * <p>OrderSide side - Side of order. | size 1</p>
 * <p>CcpCode > String (u8[]) counterpartyCode - CCP code of the counterparty. | size 16</p>
 * <p>TcrParty tcrParty - TCR party. | size 90</p>
 */
public class TradeCaptureReportSingle implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private String tradeReportId;
    private BigInteger secondaryTradeReportId;
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
    private OrderSide side;
    private String counterpartyCode;
    private TcrParty tcrParty;
    public static final int byteLength = 206;
    
    public TradeCaptureReportSingle(Header header, long instrumentId, String tradeReportId, BigInteger secondaryTradeReportId, long tradeId, TradeReportTransType tradeReportTransType, TradeReportType tradeReportType, TradeType tradeType, AlgorithmicTradeIndicator algorithmicTradeIndicator, ExecType execType, String tradeReportRefId, BigInteger lastQty, long lastPx, long settlementDate, OrderSide side, String counterpartyCode, TcrParty tcrParty) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.tradeReportId = tradeReportId;
        this.secondaryTradeReportId = secondaryTradeReportId;
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
        this.side = side;
        this.counterpartyCode = counterpartyCode;
        this.tcrParty = tcrParty;
    }
    
    public TradeCaptureReportSingle(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 16);
        this.tradeReportId = BendecUtils.stringFromByteArray(bytes, offset + 20, 21);
        this.secondaryTradeReportId = BendecUtils.uInt64FromByteArray(bytes, offset + 41);
        this.tradeId = BendecUtils.uInt32FromByteArray(bytes, offset + 49);
        this.tradeReportTransType = TradeReportTransType.getTradeReportTransType(bytes, offset + 53);
        this.tradeReportType = TradeReportType.getTradeReportType(bytes, offset + 54);
        this.tradeType = TradeType.getTradeType(bytes, offset + 55);
        this.algorithmicTradeIndicator = AlgorithmicTradeIndicator.getAlgorithmicTradeIndicator(bytes, offset + 56);
        this.execType = ExecType.getExecType(bytes, offset + 57);
        this.tradeReportRefId = BendecUtils.stringFromByteArray(bytes, offset + 58, 21);
        this.lastQty = BendecUtils.uInt64FromByteArray(bytes, offset + 79);
        this.lastPx = BendecUtils.int64FromByteArray(bytes, offset + 87);
        this.settlementDate = BendecUtils.uInt32FromByteArray(bytes, offset + 95);
        this.side = OrderSide.getOrderSide(bytes, offset + 99);
        this.counterpartyCode = BendecUtils.stringFromByteArray(bytes, offset + 100, 16);
        this.tcrParty = new TcrParty(bytes, offset + 116);
    }
    
    public TradeCaptureReportSingle(byte[] bytes) {
        this(bytes, 0);
    }
    
    public TradeCaptureReportSingle() {
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
     * @return Unique identifier of the trade capture report.
     */
    public String getTradeReportId() {
        return this.tradeReportId;
    }
    
    /**
     * @return ID of the trade capture report.
     */
    public BigInteger getSecondaryTradeReportId() {
        return this.secondaryTradeReportId;
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
     * @return Indicates algorithmic trader.
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
     * @return Side of order.
     */
    public OrderSide getSide() {
        return this.side;
    }
    
    /**
     * @return CCP code of the counterparty.
     */
    public String getCounterpartyCode() {
        return this.counterpartyCode;
    }
    
    /**
     * @return TCR party.
     */
    public TcrParty getTcrParty() {
        return this.tcrParty;
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
     * @param tradeReportId Unique identifier of the trade capture report.
     */
    public void setTradeReportId(String tradeReportId) {
        this.tradeReportId = tradeReportId;
    }
    
    /**
     * @param secondaryTradeReportId ID of the trade capture report.
     */
    public void setSecondaryTradeReportId(BigInteger secondaryTradeReportId) {
        this.secondaryTradeReportId = secondaryTradeReportId;
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
     * @param algorithmicTradeIndicator Indicates algorithmic trader.
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
     * @param side Side of order.
     */
    public void setSide(OrderSide side) {
        this.side = side;
    }
    
    /**
     * @param counterpartyCode CCP code of the counterparty.
     */
    public void setCounterpartyCode(String counterpartyCode) {
        this.counterpartyCode = counterpartyCode;
    }
    
    /**
     * @param tcrParty TCR party.
     */
    public void setTcrParty(TcrParty tcrParty) {
        this.tcrParty = tcrParty;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.stringToByteArray(this.tradeReportId, 21));
        buffer.put(BendecUtils.uInt64ToByteArray(this.secondaryTradeReportId));
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
        side.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.counterpartyCode, 16));
        tcrParty.toBytes(buffer);
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.stringToByteArray(this.tradeReportId, 21));
        buffer.put(BendecUtils.uInt64ToByteArray(this.secondaryTradeReportId));
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
        side.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.counterpartyCode, 16));
        tcrParty.toBytes(buffer);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        tradeReportId,
        secondaryTradeReportId,
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
        side,
        counterpartyCode,
        tcrParty);
    }
    
    @Override
    public String toString() {
        return "TradeCaptureReportSingle {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", tradeReportId=" + tradeReportId +
            ", secondaryTradeReportId=" + secondaryTradeReportId +
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
            ", side=" + side +
            ", counterpartyCode=" + counterpartyCode +
            ", tcrParty=" + tcrParty +
            "}";
    }
}