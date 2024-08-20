package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>IndexParams</h2>
 * <p>The metadata message for the given index.</p>
 * <p>Byte length: 186</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) instrumentId - ID of financial instrument. | size 4</p>
 * <p>IndexValue > long (i64) indexBaseValue - Index base value. | size 8</p>
 * <p>Value > long (i64) indexBaseCapitalisation - Index base capitalisation | size 8</p>
 * <p>Date > long (u32) indexBaseDate - Index base date. | size 4</p>
 * <p>Number > long (i64) correctionCoefficient - Index adjustment coefficient K(t) | size 8</p>
 * <p>u16 > int numOfComponents - Total number of instruments in the portfolio. | size 2</p>
 * <p>IndexPublicationSchedule indexPublicationSchedule - Mode of index dissemination. | size 1</p>
 * <p>IndexType indexType - Index product classification | size 1</p>
 * <p>u8 > int daysSinceLastPublication - Number of calendar days between current session and the previous session day. | size 1</p>
 * <p>u16 > int numberOfDividends - Number of dividends included in the index calculation. | size 2</p>
 * <p>IndexUnderlyings indexUnderlyings - List of underlying instruments for index. | size 97</p>
 * <p>u16 > int publicationOrder - Index publication presentation order. | size 2</p>
 * <p>Currency currency - Currency (e.g. USD). | size 2</p>
 * <p>Date > long (u32) dateValidity - Date of validity of index portfolio. | size 4</p>
 */
public class IndexParams implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private long indexBaseValue;
    private long indexBaseCapitalisation;
    private long indexBaseDate;
    private long correctionCoefficient;
    private int numOfComponents;
    private IndexPublicationSchedule indexPublicationSchedule;
    private IndexType indexType;
    private int daysSinceLastPublication;
    private int numberOfDividends;
    private IndexUnderlyings indexUnderlyings;
    private int publicationOrder;
    private Currency currency;
    private long dateValidity;
    public static final int byteLength = 186;
    
    public IndexParams(Header header, long instrumentId, long indexBaseValue, long indexBaseCapitalisation, long indexBaseDate, long correctionCoefficient, int numOfComponents, IndexPublicationSchedule indexPublicationSchedule, IndexType indexType, int daysSinceLastPublication, int numberOfDividends, IndexUnderlyings indexUnderlyings, int publicationOrder, Currency currency, long dateValidity) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.indexBaseValue = indexBaseValue;
        this.indexBaseCapitalisation = indexBaseCapitalisation;
        this.indexBaseDate = indexBaseDate;
        this.correctionCoefficient = correctionCoefficient;
        this.numOfComponents = numOfComponents;
        this.indexPublicationSchedule = indexPublicationSchedule;
        this.indexType = indexType;
        this.daysSinceLastPublication = daysSinceLastPublication;
        this.numberOfDividends = numberOfDividends;
        this.indexUnderlyings = indexUnderlyings;
        this.publicationOrder = publicationOrder;
        this.currency = currency;
        this.dateValidity = dateValidity;
    }
    
    public IndexParams(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.indexBaseValue = BendecUtils.int64FromByteArray(bytes, offset + 46);
        this.indexBaseCapitalisation = BendecUtils.int64FromByteArray(bytes, offset + 54);
        this.indexBaseDate = BendecUtils.uInt32FromByteArray(bytes, offset + 62);
        this.correctionCoefficient = BendecUtils.int64FromByteArray(bytes, offset + 66);
        this.numOfComponents = BendecUtils.uInt16FromByteArray(bytes, offset + 74);
        this.indexPublicationSchedule = IndexPublicationSchedule.getIndexPublicationSchedule(bytes, offset + 76);
        this.indexType = IndexType.getIndexType(bytes, offset + 77);
        this.daysSinceLastPublication = BendecUtils.uInt8FromByteArray(bytes, offset + 78);
        this.numberOfDividends = BendecUtils.uInt16FromByteArray(bytes, offset + 79);
        this.indexUnderlyings = new IndexUnderlyings(bytes, offset + 81);
        this.publicationOrder = BendecUtils.uInt16FromByteArray(bytes, offset + 178);
        this.currency = Currency.getCurrency(bytes, offset + 180);
        this.dateValidity = BendecUtils.uInt32FromByteArray(bytes, offset + 182);
    }
    
    public IndexParams(byte[] bytes) {
        this(bytes, 0);
    }
    
    public IndexParams() {
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
     * @return Index base value.
     */
    public long getIndexBaseValue() {
        return this.indexBaseValue;
    }
    
    /**
     * @return Index base capitalisation
     */
    public long getIndexBaseCapitalisation() {
        return this.indexBaseCapitalisation;
    }
    
    /**
     * @return Index base date.
     */
    public long getIndexBaseDate() {
        return this.indexBaseDate;
    }
    
    /**
     * @return Index adjustment coefficient K(t)
     */
    public long getCorrectionCoefficient() {
        return this.correctionCoefficient;
    }
    
    /**
     * @return Total number of instruments in the portfolio.
     */
    public int getNumOfComponents() {
        return this.numOfComponents;
    }
    
    /**
     * @return Mode of index dissemination.
     */
    public IndexPublicationSchedule getIndexPublicationSchedule() {
        return this.indexPublicationSchedule;
    }
    
    /**
     * @return Index product classification
     */
    public IndexType getIndexType() {
        return this.indexType;
    }
    
    /**
     * @return Number of calendar days between current session and the previous session day.
     */
    public int getDaysSinceLastPublication() {
        return this.daysSinceLastPublication;
    }
    
    /**
     * @return Number of dividends included in the index calculation.
     */
    public int getNumberOfDividends() {
        return this.numberOfDividends;
    }
    
    /**
     * @return List of underlying instruments for index.
     */
    public IndexUnderlyings getIndexUnderlyings() {
        return this.indexUnderlyings;
    }
    
    /**
     * @return Index publication presentation order.
     */
    public int getPublicationOrder() {
        return this.publicationOrder;
    }
    
    /**
     * @return Currency (e.g. USD).
     */
    public Currency getCurrency() {
        return this.currency;
    }
    
    /**
     * @return Date of validity of index portfolio.
     */
    public long getDateValidity() {
        return this.dateValidity;
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
     * @param indexBaseValue Index base value.
     */
    public void setIndexBaseValue(long indexBaseValue) {
        this.indexBaseValue = indexBaseValue;
    }
    
    /**
     * @param indexBaseCapitalisation Index base capitalisation
     */
    public void setIndexBaseCapitalisation(long indexBaseCapitalisation) {
        this.indexBaseCapitalisation = indexBaseCapitalisation;
    }
    
    /**
     * @param indexBaseDate Index base date.
     */
    public void setIndexBaseDate(long indexBaseDate) {
        this.indexBaseDate = indexBaseDate;
    }
    
    /**
     * @param correctionCoefficient Index adjustment coefficient K(t)
     */
    public void setCorrectionCoefficient(long correctionCoefficient) {
        this.correctionCoefficient = correctionCoefficient;
    }
    
    /**
     * @param numOfComponents Total number of instruments in the portfolio.
     */
    public void setNumOfComponents(int numOfComponents) {
        this.numOfComponents = numOfComponents;
    }
    
    /**
     * @param indexPublicationSchedule Mode of index dissemination.
     */
    public void setIndexPublicationSchedule(IndexPublicationSchedule indexPublicationSchedule) {
        this.indexPublicationSchedule = indexPublicationSchedule;
    }
    
    /**
     * @param indexType Index product classification
     */
    public void setIndexType(IndexType indexType) {
        this.indexType = indexType;
    }
    
    /**
     * @param daysSinceLastPublication Number of calendar days between current session and the previous session day.
     */
    public void setDaysSinceLastPublication(int daysSinceLastPublication) {
        this.daysSinceLastPublication = daysSinceLastPublication;
    }
    
    /**
     * @param numberOfDividends Number of dividends included in the index calculation.
     */
    public void setNumberOfDividends(int numberOfDividends) {
        this.numberOfDividends = numberOfDividends;
    }
    
    /**
     * @param indexUnderlyings List of underlying instruments for index.
     */
    public void setIndexUnderlyings(IndexUnderlyings indexUnderlyings) {
        this.indexUnderlyings = indexUnderlyings;
    }
    
    /**
     * @param publicationOrder Index publication presentation order.
     */
    public void setPublicationOrder(int publicationOrder) {
        this.publicationOrder = publicationOrder;
    }
    
    /**
     * @param currency Currency (e.g. USD).
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    
    /**
     * @param dateValidity Date of validity of index portfolio.
     */
    public void setDateValidity(long dateValidity) {
        this.dateValidity = dateValidity;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.int64ToByteArray(this.indexBaseValue));
        buffer.put(BendecUtils.int64ToByteArray(this.indexBaseCapitalisation));
        buffer.put(BendecUtils.uInt32ToByteArray(this.indexBaseDate));
        buffer.put(BendecUtils.int64ToByteArray(this.correctionCoefficient));
        buffer.put(BendecUtils.uInt16ToByteArray(this.numOfComponents));
        indexPublicationSchedule.toBytes(buffer);
        indexType.toBytes(buffer);
        buffer.put(BendecUtils.uInt8ToByteArray(this.daysSinceLastPublication));
        buffer.put(BendecUtils.uInt16ToByteArray(this.numberOfDividends));
        indexUnderlyings.toBytes(buffer);
        buffer.put(BendecUtils.uInt16ToByteArray(this.publicationOrder));
        currency.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.dateValidity));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.int64ToByteArray(this.indexBaseValue));
        buffer.put(BendecUtils.int64ToByteArray(this.indexBaseCapitalisation));
        buffer.put(BendecUtils.uInt32ToByteArray(this.indexBaseDate));
        buffer.put(BendecUtils.int64ToByteArray(this.correctionCoefficient));
        buffer.put(BendecUtils.uInt16ToByteArray(this.numOfComponents));
        indexPublicationSchedule.toBytes(buffer);
        indexType.toBytes(buffer);
        buffer.put(BendecUtils.uInt8ToByteArray(this.daysSinceLastPublication));
        buffer.put(BendecUtils.uInt16ToByteArray(this.numberOfDividends));
        indexUnderlyings.toBytes(buffer);
        buffer.put(BendecUtils.uInt16ToByteArray(this.publicationOrder));
        currency.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.dateValidity));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        indexBaseValue,
        indexBaseCapitalisation,
        indexBaseDate,
        correctionCoefficient,
        numOfComponents,
        indexPublicationSchedule,
        indexType,
        daysSinceLastPublication,
        numberOfDividends,
        indexUnderlyings,
        publicationOrder,
        currency,
        dateValidity);
    }
    
    @Override
    public String toString() {
        return "IndexParams {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", indexBaseValue=" + indexBaseValue +
            ", indexBaseCapitalisation=" + indexBaseCapitalisation +
            ", indexBaseDate=" + indexBaseDate +
            ", correctionCoefficient=" + correctionCoefficient +
            ", numOfComponents=" + numOfComponents +
            ", indexPublicationSchedule=" + indexPublicationSchedule +
            ", indexType=" + indexType +
            ", daysSinceLastPublication=" + daysSinceLastPublication +
            ", numberOfDividends=" + numberOfDividends +
            ", indexUnderlyings=" + indexUnderlyings +
            ", publicationOrder=" + publicationOrder +
            ", currency=" + currency +
            ", dateValidity=" + dateValidity +
            "}";
    }
}