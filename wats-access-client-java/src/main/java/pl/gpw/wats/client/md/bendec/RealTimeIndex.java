package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>RealTimeIndex</h2>
 * <p>Message providing real-time index values for instruments.</p>
 * <p>Byte length: 129</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) instrumentId - ID of the instrument. | size 4</p>
 * <p>IndexLevelCode indexLevelCode - Level of the index. | size 1</p>
 * <p>u16 > int numOfActiveInstruments - Number of instruments – participants of the index, for which the quotation was set during the session. | size 2</p>
 * <p>PercentageChange > long (i64) indIndexOpeningPortfolio - Indicator of the index opening portfolio W(t). Percentage share of instruments with at least one trade during the session. | size 8</p>
 * <p>IndexValue > long (i64) indexValue - The value of the last level for the index according to the definition provided in the indexLevelCode. | size 8</p>
 * <p>PercentageChange > long (i64) pctChangeIndexValPrevSession - Percentage change of the index value in relation to the value of the closing index from the previous session. | size 8</p>
 * <p>Value > long (i64) tradingValue - Value of trading in the instruments from the given index. | size 8</p>
 * <p>IndexValue > long (i64) sessionLow - Lowest value of the day. | size 8</p>
 * <p>IndexValue > long (i64) sessionHigh - Highest value of the day. | size 8</p>
 * <p>IndexValue > long (i64) indexValueBid - Value of the buy index based on the best sell offers of instruments. | size 8</p>
 * <p>IndexValue > long (i64) indexValueAsk - Value of the sell index based on the best sell offers of instruments. | size 8</p>
 * <p>IndexValue > long (i64) midSpreadIndex - Mean of indexValueBid and indexValueAsk. | size 8</p>
 * <p>IndexValue > long (i64) differenceCentralSpread - The point difference between midSpreadIndex and indexValue. | size 8</p>
 */
public class RealTimeIndex implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private IndexLevelCode indexLevelCode;
    private int numOfActiveInstruments;
    private long indIndexOpeningPortfolio;
    private long indexValue;
    private long pctChangeIndexValPrevSession;
    private long tradingValue;
    private long sessionLow;
    private long sessionHigh;
    private long indexValueBid;
    private long indexValueAsk;
    private long midSpreadIndex;
    private long differenceCentralSpread;
    public static final int byteLength = 129;
    
    public RealTimeIndex(Header header, long instrumentId, IndexLevelCode indexLevelCode, int numOfActiveInstruments, long indIndexOpeningPortfolio, long indexValue, long pctChangeIndexValPrevSession, long tradingValue, long sessionLow, long sessionHigh, long indexValueBid, long indexValueAsk, long midSpreadIndex, long differenceCentralSpread) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.indexLevelCode = indexLevelCode;
        this.numOfActiveInstruments = numOfActiveInstruments;
        this.indIndexOpeningPortfolio = indIndexOpeningPortfolio;
        this.indexValue = indexValue;
        this.pctChangeIndexValPrevSession = pctChangeIndexValPrevSession;
        this.tradingValue = tradingValue;
        this.sessionLow = sessionLow;
        this.sessionHigh = sessionHigh;
        this.indexValueBid = indexValueBid;
        this.indexValueAsk = indexValueAsk;
        this.midSpreadIndex = midSpreadIndex;
        this.differenceCentralSpread = differenceCentralSpread;
    }
    
    public RealTimeIndex(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.indexLevelCode = IndexLevelCode.getIndexLevelCode(bytes, offset + 46);
        this.numOfActiveInstruments = BendecUtils.uInt16FromByteArray(bytes, offset + 47);
        this.indIndexOpeningPortfolio = BendecUtils.int64FromByteArray(bytes, offset + 49);
        this.indexValue = BendecUtils.int64FromByteArray(bytes, offset + 57);
        this.pctChangeIndexValPrevSession = BendecUtils.int64FromByteArray(bytes, offset + 65);
        this.tradingValue = BendecUtils.int64FromByteArray(bytes, offset + 73);
        this.sessionLow = BendecUtils.int64FromByteArray(bytes, offset + 81);
        this.sessionHigh = BendecUtils.int64FromByteArray(bytes, offset + 89);
        this.indexValueBid = BendecUtils.int64FromByteArray(bytes, offset + 97);
        this.indexValueAsk = BendecUtils.int64FromByteArray(bytes, offset + 105);
        this.midSpreadIndex = BendecUtils.int64FromByteArray(bytes, offset + 113);
        this.differenceCentralSpread = BendecUtils.int64FromByteArray(bytes, offset + 121);
    }
    
    public RealTimeIndex(byte[] bytes) {
        this(bytes, 0);
    }
    
    public RealTimeIndex() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return ID of the instrument.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    }
    
    /**
     * @return Level of the index.
     */
    public IndexLevelCode getIndexLevelCode() {
        return this.indexLevelCode;
    }
    
    /**
     * @return Number of instruments – participants of the index, for which the quotation was set during the session.
     */
    public int getNumOfActiveInstruments() {
        return this.numOfActiveInstruments;
    }
    
    /**
     * @return Indicator of the index opening portfolio W(t). Percentage share of instruments with at least one trade during the session.
     */
    public long getIndIndexOpeningPortfolio() {
        return this.indIndexOpeningPortfolio;
    }
    
    /**
     * @return The value of the last level for the index according to the definition provided in the indexLevelCode.
     */
    public long getIndexValue() {
        return this.indexValue;
    }
    
    /**
     * @return Percentage change of the index value in relation to the value of the closing index from the previous session.
     */
    public long getPctChangeIndexValPrevSession() {
        return this.pctChangeIndexValPrevSession;
    }
    
    /**
     * @return Value of trading in the instruments from the given index.
     */
    public long getTradingValue() {
        return this.tradingValue;
    }
    
    /**
     * @return Lowest value of the day.
     */
    public long getSessionLow() {
        return this.sessionLow;
    }
    
    /**
     * @return Highest value of the day.
     */
    public long getSessionHigh() {
        return this.sessionHigh;
    }
    
    /**
     * @return Value of the buy index based on the best sell offers of instruments.
     */
    public long getIndexValueBid() {
        return this.indexValueBid;
    }
    
    /**
     * @return Value of the sell index based on the best sell offers of instruments.
     */
    public long getIndexValueAsk() {
        return this.indexValueAsk;
    }
    
    /**
     * @return Mean of indexValueBid and indexValueAsk.
     */
    public long getMidSpreadIndex() {
        return this.midSpreadIndex;
    }
    
    /**
     * @return The point difference between midSpreadIndex and indexValue.
     */
    public long getDifferenceCentralSpread() {
        return this.differenceCentralSpread;
    }
    
    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param instrumentId ID of the instrument.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }
    
    /**
     * @param indexLevelCode Level of the index.
     */
    public void setIndexLevelCode(IndexLevelCode indexLevelCode) {
        this.indexLevelCode = indexLevelCode;
    }
    
    /**
     * @param numOfActiveInstruments Number of instruments – participants of the index, for which the quotation was set during the session.
     */
    public void setNumOfActiveInstruments(int numOfActiveInstruments) {
        this.numOfActiveInstruments = numOfActiveInstruments;
    }
    
    /**
     * @param indIndexOpeningPortfolio Indicator of the index opening portfolio W(t). Percentage share of instruments with at least one trade during the session.
     */
    public void setIndIndexOpeningPortfolio(long indIndexOpeningPortfolio) {
        this.indIndexOpeningPortfolio = indIndexOpeningPortfolio;
    }
    
    /**
     * @param indexValue The value of the last level for the index according to the definition provided in the indexLevelCode.
     */
    public void setIndexValue(long indexValue) {
        this.indexValue = indexValue;
    }
    
    /**
     * @param pctChangeIndexValPrevSession Percentage change of the index value in relation to the value of the closing index from the previous session.
     */
    public void setPctChangeIndexValPrevSession(long pctChangeIndexValPrevSession) {
        this.pctChangeIndexValPrevSession = pctChangeIndexValPrevSession;
    }
    
    /**
     * @param tradingValue Value of trading in the instruments from the given index.
     */
    public void setTradingValue(long tradingValue) {
        this.tradingValue = tradingValue;
    }
    
    /**
     * @param sessionLow Lowest value of the day.
     */
    public void setSessionLow(long sessionLow) {
        this.sessionLow = sessionLow;
    }
    
    /**
     * @param sessionHigh Highest value of the day.
     */
    public void setSessionHigh(long sessionHigh) {
        this.sessionHigh = sessionHigh;
    }
    
    /**
     * @param indexValueBid Value of the buy index based on the best sell offers of instruments.
     */
    public void setIndexValueBid(long indexValueBid) {
        this.indexValueBid = indexValueBid;
    }
    
    /**
     * @param indexValueAsk Value of the sell index based on the best sell offers of instruments.
     */
    public void setIndexValueAsk(long indexValueAsk) {
        this.indexValueAsk = indexValueAsk;
    }
    
    /**
     * @param midSpreadIndex Mean of indexValueBid and indexValueAsk.
     */
    public void setMidSpreadIndex(long midSpreadIndex) {
        this.midSpreadIndex = midSpreadIndex;
    }
    
    /**
     * @param differenceCentralSpread The point difference between midSpreadIndex and indexValue.
     */
    public void setDifferenceCentralSpread(long differenceCentralSpread) {
        this.differenceCentralSpread = differenceCentralSpread;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        indexLevelCode.toBytes(buffer);
        buffer.put(BendecUtils.uInt16ToByteArray(this.numOfActiveInstruments));
        buffer.put(BendecUtils.int64ToByteArray(this.indIndexOpeningPortfolio));
        buffer.put(BendecUtils.int64ToByteArray(this.indexValue));
        buffer.put(BendecUtils.int64ToByteArray(this.pctChangeIndexValPrevSession));
        buffer.put(BendecUtils.int64ToByteArray(this.tradingValue));
        buffer.put(BendecUtils.int64ToByteArray(this.sessionLow));
        buffer.put(BendecUtils.int64ToByteArray(this.sessionHigh));
        buffer.put(BendecUtils.int64ToByteArray(this.indexValueBid));
        buffer.put(BendecUtils.int64ToByteArray(this.indexValueAsk));
        buffer.put(BendecUtils.int64ToByteArray(this.midSpreadIndex));
        buffer.put(BendecUtils.int64ToByteArray(this.differenceCentralSpread));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        indexLevelCode.toBytes(buffer);
        buffer.put(BendecUtils.uInt16ToByteArray(this.numOfActiveInstruments));
        buffer.put(BendecUtils.int64ToByteArray(this.indIndexOpeningPortfolio));
        buffer.put(BendecUtils.int64ToByteArray(this.indexValue));
        buffer.put(BendecUtils.int64ToByteArray(this.pctChangeIndexValPrevSession));
        buffer.put(BendecUtils.int64ToByteArray(this.tradingValue));
        buffer.put(BendecUtils.int64ToByteArray(this.sessionLow));
        buffer.put(BendecUtils.int64ToByteArray(this.sessionHigh));
        buffer.put(BendecUtils.int64ToByteArray(this.indexValueBid));
        buffer.put(BendecUtils.int64ToByteArray(this.indexValueAsk));
        buffer.put(BendecUtils.int64ToByteArray(this.midSpreadIndex));
        buffer.put(BendecUtils.int64ToByteArray(this.differenceCentralSpread));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        indexLevelCode,
        numOfActiveInstruments,
        indIndexOpeningPortfolio,
        indexValue,
        pctChangeIndexValPrevSession,
        tradingValue,
        sessionLow,
        sessionHigh,
        indexValueBid,
        indexValueAsk,
        midSpreadIndex,
        differenceCentralSpread);
    }
    
    @Override
    public String toString() {
        return "RealTimeIndex {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", indexLevelCode=" + indexLevelCode +
            ", numOfActiveInstruments=" + numOfActiveInstruments +
            ", indIndexOpeningPortfolio=" + indIndexOpeningPortfolio +
            ", indexValue=" + indexValue +
            ", pctChangeIndexValPrevSession=" + pctChangeIndexValPrevSession +
            ", tradingValue=" + tradingValue +
            ", sessionLow=" + sessionLow +
            ", sessionHigh=" + sessionHigh +
            ", indexValueBid=" + indexValueBid +
            ", indexValueAsk=" + indexValueAsk +
            ", midSpreadIndex=" + midSpreadIndex +
            ", differenceCentralSpread=" + differenceCentralSpread +
            "}";
    }
}