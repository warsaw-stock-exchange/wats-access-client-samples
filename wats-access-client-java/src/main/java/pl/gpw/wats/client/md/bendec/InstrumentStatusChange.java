package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>InstrumentStatusChange</h2>
 * <p>Start of a new trading phase.</p>
 * <p>Byte length: 53</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) instrumentId - ID of financial instrument. | size 4</p>
 * <p>ElementId > long (u32) tradingPhaseId - Trading phase ID. | size 4</p>
 * <p>TradingPhaseType tradingPhaseType - Type of matching algorithm. | size 1</p>
 * <p>InstrumentStatus status - Financial instrument status. | size 1</p>
 * <p>bool > boolean stressedMarket - Stressed market conditions is called for when instrument experience high and short term intraday volatility. | size 1</p>
 */
public class InstrumentStatusChange implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private long tradingPhaseId;
    private TradingPhaseType tradingPhaseType;
    private InstrumentStatus status;
    private boolean stressedMarket;
    public static final int byteLength = 53;

    public InstrumentStatusChange(Header header, long instrumentId, long tradingPhaseId, TradingPhaseType tradingPhaseType, InstrumentStatus status, boolean stressedMarket) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.tradingPhaseId = tradingPhaseId;
        this.tradingPhaseType = tradingPhaseType;
        this.status = status;
        this.stressedMarket = stressedMarket;
    }
    
    public InstrumentStatusChange(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.tradingPhaseId = BendecUtils.uInt32FromByteArray(bytes, offset + 46);
        this.tradingPhaseType = TradingPhaseType.getTradingPhaseType(bytes, offset + 50);
        this.status = InstrumentStatus.getInstrumentStatus(bytes, offset + 51);
        this.stressedMarket = BendecUtils.booleanFromByteArray(bytes, offset + 52);
    }
    
    public InstrumentStatusChange(byte[] bytes) {
        this(bytes, 0);
    }
    
    public InstrumentStatusChange() {
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
     * @return Trading phase ID.
     */
    public long getTradingPhaseId() {
        return this.tradingPhaseId;
    }
    
    /**
     * @return Type of matching algorithm.
     */
    public TradingPhaseType getTradingPhaseType() {
        return this.tradingPhaseType;
    }
    
    /**
     * @return Financial instrument status.
     */
    public InstrumentStatus getStatus() {
        return this.status;
    }
    
    /**
     * @return Stressed market conditions is called for when instrument experience high and short term intraday volatility.
     */
    public boolean getStressedMarket() {
        return this.stressedMarket;
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
     * @param tradingPhaseId Trading phase ID.
     */
    public void setTradingPhaseId(long tradingPhaseId) {
        this.tradingPhaseId = tradingPhaseId;
    }
    
    /**
     * @param tradingPhaseType Type of matching algorithm.
     */
    public void setTradingPhaseType(TradingPhaseType tradingPhaseType) {
        this.tradingPhaseType = tradingPhaseType;
    }
    
    /**
     * @param status Financial instrument status.
     */
    public void setStatus(InstrumentStatus status) {
        this.status = status;
    }
    
    /**
     * @param stressedMarket Stressed market conditions is called for when instrument experience high and short term intraday volatility.
     */
    public void setStressedMarket(boolean stressedMarket) {
        this.stressedMarket = stressedMarket;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradingPhaseId));
        tradingPhaseType.toBytes(buffer);
        status.toBytes(buffer);
        buffer.put(BendecUtils.booleanToByteArray(this.stressedMarket));
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradingPhaseId));
        tradingPhaseType.toBytes(buffer);
        status.toBytes(buffer);
        buffer.put(BendecUtils.booleanToByteArray(this.stressedMarket));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        tradingPhaseId,
        tradingPhaseType,
        status,
        stressedMarket);
    }
    
    @Override
    public String toString() {
        return "InstrumentStatusChange {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", tradingPhaseId=" + tradingPhaseId +
            ", tradingPhaseType=" + tradingPhaseType +
            ", status=" + status +
            ", stressedMarket=" + stressedMarket +
            "}";
    }
}