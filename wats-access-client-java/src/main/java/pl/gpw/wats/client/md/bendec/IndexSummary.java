package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>IndexSummary</h2>
 * <p>The index summary message provides a summary of the data calculated in a stock index for a given day.</p>
 * <p>Byte length: 118</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) instrumentId - ID of the instrument. | size 4</p>
 * <p>IndexValue > long (i64) openingIndexValue - Opening index value | size 8</p>
 * <p>IndexValue > long (i64) sessionLow - Lowest value of the day. | size 8</p>
 * <p>IndexValue > long (i64) sessionHigh - Highest value of the day. | size 8</p>
 * <p>IndexValue > long (i64) closingIndexValue - Closing index value. | size 8</p>
 * <p>IndexValue > long (i64) sessionAvg - Average value of the day. | size 8</p>
 * <p>PercentageChange > long (i64) pctChangeIndexValPrevSession - Percentage change of the index value in relation to the value of the closing index from the previous session. | size 8</p>
 * <p>PercentageChange > long (i64) pctChangeIndexValPrevYear - Percentage change of the index value in relation to its value at the end of the previous year | size 8</p>
 * <p>Value > long (i64) closingCapitalisationPortfolio - Capitalisation of the portfolio for the closing index expressed in the currency of the index, on basis of which the closing index is calculated. | size 8</p>
 * <p>PercentageChange > long (i64) indIndexOpeningPortfolio - Indicator of the index opening portfolio W(t). Percentage share of instruments with at least one trade during the session. | size 8</p>
 */
public class IndexSummary implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private long openingIndexValue;
    private long sessionLow;
    private long sessionHigh;
    private long closingIndexValue;
    private long sessionAvg;
    private long pctChangeIndexValPrevSession;
    private long pctChangeIndexValPrevYear;
    private long closingCapitalisationPortfolio;
    private long indIndexOpeningPortfolio;
    public static final int byteLength = 118;
    
    public IndexSummary(Header header, long instrumentId, long openingIndexValue, long sessionLow, long sessionHigh, long closingIndexValue, long sessionAvg, long pctChangeIndexValPrevSession, long pctChangeIndexValPrevYear, long closingCapitalisationPortfolio, long indIndexOpeningPortfolio) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.openingIndexValue = openingIndexValue;
        this.sessionLow = sessionLow;
        this.sessionHigh = sessionHigh;
        this.closingIndexValue = closingIndexValue;
        this.sessionAvg = sessionAvg;
        this.pctChangeIndexValPrevSession = pctChangeIndexValPrevSession;
        this.pctChangeIndexValPrevYear = pctChangeIndexValPrevYear;
        this.closingCapitalisationPortfolio = closingCapitalisationPortfolio;
        this.indIndexOpeningPortfolio = indIndexOpeningPortfolio;
    }
    
    public IndexSummary(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.openingIndexValue = BendecUtils.int64FromByteArray(bytes, offset + 46);
        this.sessionLow = BendecUtils.int64FromByteArray(bytes, offset + 54);
        this.sessionHigh = BendecUtils.int64FromByteArray(bytes, offset + 62);
        this.closingIndexValue = BendecUtils.int64FromByteArray(bytes, offset + 70);
        this.sessionAvg = BendecUtils.int64FromByteArray(bytes, offset + 78);
        this.pctChangeIndexValPrevSession = BendecUtils.int64FromByteArray(bytes, offset + 86);
        this.pctChangeIndexValPrevYear = BendecUtils.int64FromByteArray(bytes, offset + 94);
        this.closingCapitalisationPortfolio = BendecUtils.int64FromByteArray(bytes, offset + 102);
        this.indIndexOpeningPortfolio = BendecUtils.int64FromByteArray(bytes, offset + 110);
    }
    
    public IndexSummary(byte[] bytes) {
        this(bytes, 0);
    }
    
    public IndexSummary() {
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
     * @return Opening index value
     */
    public long getOpeningIndexValue() {
        return this.openingIndexValue;
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
     * @return Closing index value.
     */
    public long getClosingIndexValue() {
        return this.closingIndexValue;
    }
    
    /**
     * @return Average value of the day.
     */
    public long getSessionAvg() {
        return this.sessionAvg;
    }
    
    /**
     * @return Percentage change of the index value in relation to the value of the closing index from the previous session.
     */
    public long getPctChangeIndexValPrevSession() {
        return this.pctChangeIndexValPrevSession;
    }
    
    /**
     * @return Percentage change of the index value in relation to its value at the end of the previous year
     */
    public long getPctChangeIndexValPrevYear() {
        return this.pctChangeIndexValPrevYear;
    }
    
    /**
     * @return Capitalisation of the portfolio for the closing index expressed in the currency of the index, on basis of which the closing index is calculated.
     */
    public long getClosingCapitalisationPortfolio() {
        return this.closingCapitalisationPortfolio;
    }
    
    /**
     * @return Indicator of the index opening portfolio W(t). Percentage share of instruments with at least one trade during the session.
     */
    public long getIndIndexOpeningPortfolio() {
        return this.indIndexOpeningPortfolio;
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
     * @param openingIndexValue Opening index value
     */
    public void setOpeningIndexValue(long openingIndexValue) {
        this.openingIndexValue = openingIndexValue;
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
     * @param closingIndexValue Closing index value.
     */
    public void setClosingIndexValue(long closingIndexValue) {
        this.closingIndexValue = closingIndexValue;
    }
    
    /**
     * @param sessionAvg Average value of the day.
     */
    public void setSessionAvg(long sessionAvg) {
        this.sessionAvg = sessionAvg;
    }
    
    /**
     * @param pctChangeIndexValPrevSession Percentage change of the index value in relation to the value of the closing index from the previous session.
     */
    public void setPctChangeIndexValPrevSession(long pctChangeIndexValPrevSession) {
        this.pctChangeIndexValPrevSession = pctChangeIndexValPrevSession;
    }
    
    /**
     * @param pctChangeIndexValPrevYear Percentage change of the index value in relation to its value at the end of the previous year
     */
    public void setPctChangeIndexValPrevYear(long pctChangeIndexValPrevYear) {
        this.pctChangeIndexValPrevYear = pctChangeIndexValPrevYear;
    }
    
    /**
     * @param closingCapitalisationPortfolio Capitalisation of the portfolio for the closing index expressed in the currency of the index, on basis of which the closing index is calculated.
     */
    public void setClosingCapitalisationPortfolio(long closingCapitalisationPortfolio) {
        this.closingCapitalisationPortfolio = closingCapitalisationPortfolio;
    }
    
    /**
     * @param indIndexOpeningPortfolio Indicator of the index opening portfolio W(t). Percentage share of instruments with at least one trade during the session.
     */
    public void setIndIndexOpeningPortfolio(long indIndexOpeningPortfolio) {
        this.indIndexOpeningPortfolio = indIndexOpeningPortfolio;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.int64ToByteArray(this.openingIndexValue));
        buffer.put(BendecUtils.int64ToByteArray(this.sessionLow));
        buffer.put(BendecUtils.int64ToByteArray(this.sessionHigh));
        buffer.put(BendecUtils.int64ToByteArray(this.closingIndexValue));
        buffer.put(BendecUtils.int64ToByteArray(this.sessionAvg));
        buffer.put(BendecUtils.int64ToByteArray(this.pctChangeIndexValPrevSession));
        buffer.put(BendecUtils.int64ToByteArray(this.pctChangeIndexValPrevYear));
        buffer.put(BendecUtils.int64ToByteArray(this.closingCapitalisationPortfolio));
        buffer.put(BendecUtils.int64ToByteArray(this.indIndexOpeningPortfolio));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.int64ToByteArray(this.openingIndexValue));
        buffer.put(BendecUtils.int64ToByteArray(this.sessionLow));
        buffer.put(BendecUtils.int64ToByteArray(this.sessionHigh));
        buffer.put(BendecUtils.int64ToByteArray(this.closingIndexValue));
        buffer.put(BendecUtils.int64ToByteArray(this.sessionAvg));
        buffer.put(BendecUtils.int64ToByteArray(this.pctChangeIndexValPrevSession));
        buffer.put(BendecUtils.int64ToByteArray(this.pctChangeIndexValPrevYear));
        buffer.put(BendecUtils.int64ToByteArray(this.closingCapitalisationPortfolio));
        buffer.put(BendecUtils.int64ToByteArray(this.indIndexOpeningPortfolio));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        openingIndexValue,
        sessionLow,
        sessionHigh,
        closingIndexValue,
        sessionAvg,
        pctChangeIndexValPrevSession,
        pctChangeIndexValPrevYear,
        closingCapitalisationPortfolio,
        indIndexOpeningPortfolio);
    }
    
    @Override
    public String toString() {
        return "IndexSummary {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", openingIndexValue=" + openingIndexValue +
            ", sessionLow=" + sessionLow +
            ", sessionHigh=" + sessionHigh +
            ", closingIndexValue=" + closingIndexValue +
            ", sessionAvg=" + sessionAvg +
            ", pctChangeIndexValPrevSession=" + pctChangeIndexValPrevSession +
            ", pctChangeIndexValPrevYear=" + pctChangeIndexValPrevYear +
            ", closingCapitalisationPortfolio=" + closingCapitalisationPortfolio +
            ", indIndexOpeningPortfolio=" + indIndexOpeningPortfolio +
            "}";
    }
}