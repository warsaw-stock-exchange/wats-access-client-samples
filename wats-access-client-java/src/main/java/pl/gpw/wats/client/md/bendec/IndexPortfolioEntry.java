package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>IndexPortfolioEntry</h2>
 * <p>The message contains information about a product providing the index portfolio. The entire index portfolio is sent as successive IndexPortfolioEntry messages.</p>
 * <p>Byte length: 96</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) id - The identifier of the entry within the given index. | size 4</p>
 * <p>ElementId > long (u32) instrumentId - ID of the instrument. | size 4</p>
 * <p>PublicProductIdentification publicProductIdentification - Product identification type and code. | size 31</p>
 * <p>MicCode > String (u8[]) mic - Market structure's Market Identifier Code (MIC) as specified in ISO 10383. | size 4</p>
 * <p>Currency currency - Currency (e.g. USD). | size 2</p>
 * <p>u64 > BigInteger instrumentPacket - Instrument packet in index portfolio / of information product. Number of units (usually stocks/shares) of a given instrument in the index portfolio or in the information product. | size 8</p>
 * <p>bool > boolean suspended - Is instrument suspended. | size 1</p>
 */
public class IndexPortfolioEntry implements ByteSerializable, Message {
    private Header header;
    private long id;
    private long instrumentId;
    private PublicProductIdentification publicProductIdentification;
    private String mic;
    private Currency currency;
    private BigInteger instrumentPacket;
    private boolean suspended;
    public static final int byteLength = 96;
    
    public IndexPortfolioEntry(Header header, long id, long instrumentId, PublicProductIdentification publicProductIdentification, String mic, Currency currency, BigInteger instrumentPacket, boolean suspended) {
        this.header = header;
        this.id = id;
        this.instrumentId = instrumentId;
        this.publicProductIdentification = publicProductIdentification;
        this.mic = mic;
        this.currency = currency;
        this.instrumentPacket = instrumentPacket;
        this.suspended = suspended;
    }
    
    public IndexPortfolioEntry(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.id = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 46);
        this.publicProductIdentification = new PublicProductIdentification(bytes, offset + 50);
        this.mic = BendecUtils.stringFromByteArray(bytes, offset + 81, 4);
        this.currency = Currency.getCurrency(bytes, offset + 85);
        this.instrumentPacket = BendecUtils.uInt64FromByteArray(bytes, offset + 87);
        this.suspended = BendecUtils.booleanFromByteArray(bytes, offset + 95);
    }
    
    public IndexPortfolioEntry(byte[] bytes) {
        this(bytes, 0);
    }
    
    public IndexPortfolioEntry() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return The identifier of the entry within the given index.
     */
    public long getId() {
        return this.id;
    }
    
    /**
     * @return ID of the instrument.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    }
    
    /**
     * @return Product identification type and code.
     */
    public PublicProductIdentification getPublicProductIdentification() {
        return this.publicProductIdentification;
    }
    
    /**
     * @return Market structure's Market Identifier Code (MIC) as specified in ISO 10383.
     */
    public String getMic() {
        return this.mic;
    }
    
    /**
     * @return Currency (e.g. USD).
     */
    public Currency getCurrency() {
        return this.currency;
    }
    
    /**
     * @return Instrument packet in index portfolio / of information product. Number of units (usually stocks/shares) of a given instrument in the index portfolio or in the information product.
     */
    public BigInteger getInstrumentPacket() {
        return this.instrumentPacket;
    }
    
    /**
     * @return Is instrument suspended.
     */
    public boolean getSuspended() {
        return this.suspended;
    }
    
    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param id The identifier of the entry within the given index.
     */
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * @param instrumentId ID of the instrument.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }
    
    /**
     * @param publicProductIdentification Product identification type and code.
     */
    public void setPublicProductIdentification(PublicProductIdentification publicProductIdentification) {
        this.publicProductIdentification = publicProductIdentification;
    }
    
    /**
     * @param mic Market structure's Market Identifier Code (MIC) as specified in ISO 10383.
     */
    public void setMic(String mic) {
        this.mic = mic;
    }
    
    /**
     * @param currency Currency (e.g. USD).
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    
    /**
     * @param instrumentPacket Instrument packet in index portfolio / of information product. Number of units (usually stocks/shares) of a given instrument in the index portfolio or in the information product.
     */
    public void setInstrumentPacket(BigInteger instrumentPacket) {
        this.instrumentPacket = instrumentPacket;
    }
    
    /**
     * @param suspended Is instrument suspended.
     */
    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.id));
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        publicProductIdentification.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.mic, 4));
        currency.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.instrumentPacket));
        buffer.put(BendecUtils.booleanToByteArray(this.suspended));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.id));
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        publicProductIdentification.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.mic, 4));
        currency.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.instrumentPacket));
        buffer.put(BendecUtils.booleanToByteArray(this.suspended));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        id,
        instrumentId,
        publicProductIdentification,
        mic,
        currency,
        instrumentPacket,
        suspended);
    }
    
    @Override
    public String toString() {
        return "IndexPortfolioEntry {" +
            "header=" + header +
            ", id=" + id +
            ", instrumentId=" + instrumentId +
            ", publicProductIdentification=" + publicProductIdentification +
            ", mic=" + mic +
            ", currency=" + currency +
            ", instrumentPacket=" + instrumentPacket +
            ", suspended=" + suspended +
            "}";
    }
}