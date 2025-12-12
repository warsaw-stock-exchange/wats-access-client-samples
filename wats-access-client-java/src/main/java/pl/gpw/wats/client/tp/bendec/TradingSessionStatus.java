package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>TradingSessionStatus</h2>
 * <p>The Trading Session Status provides information on the status of a trading session.</p>
 * <p>Byte length: 33</p>
 * <p>Header header - Message header. | size 24</p>
 * <p>MicCode > String (u8[]) marketId - Market structure's Market Identifier Code (MIC) as specified in ISO 10383. | size 4</p>
 * <p>Date > long (u32) date - Date of the business session when provided and 0 for StartOfTechnicalSession and EndOfTechnicalSession events. | size 4</p>
 * <p>TradingSessionEvent tradingSessionEvent - Identifies an event related to the trading status of a trading session. | size 1</p>
 */
public class TradingSessionStatus implements ByteSerializable, Message {
    private Header header;
    private String marketId;
    private long date;
    private TradingSessionEvent tradingSessionEvent;
    public static final int byteLength = 33;
    
    public TradingSessionStatus(Header header, String marketId, long date, TradingSessionEvent tradingSessionEvent) {
        this.header = header;
        this.marketId = marketId;
        this.date = date;
        this.tradingSessionEvent = tradingSessionEvent;
    }
    
    public TradingSessionStatus(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.marketId = BendecUtils.stringFromByteArray(bytes, offset + 24, 4);
        this.date = BendecUtils.uInt32FromByteArray(bytes, offset + 28);
        this.tradingSessionEvent = TradingSessionEvent.getTradingSessionEvent(bytes, offset + 32);
    }
    
    public TradingSessionStatus(byte[] bytes) {
        this(bytes, 0);
    }
    
    public TradingSessionStatus() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Market structure's Market Identifier Code (MIC) as specified in ISO 10383.
     */
    public String getMarketId() {
        return this.marketId;
    }
    
    /**
     * @return Date of the business session when provided and 0 for StartOfTechnicalSession and EndOfTechnicalSession events.
     */
    public long getDate() {
        return this.date;
    }
    
    /**
     * @return Identifies an event related to the trading status of a trading session.
     */
    public TradingSessionEvent getTradingSessionEvent() {
        return this.tradingSessionEvent;
    }
    
    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param marketId Market structure's Market Identifier Code (MIC) as specified in ISO 10383.
     */
    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }
    
    /**
     * @param date Date of the business session when provided and 0 for StartOfTechnicalSession and EndOfTechnicalSession events.
     */
    public void setDate(long date) {
        this.date = date;
    }
    
    /**
     * @param tradingSessionEvent Identifies an event related to the trading status of a trading session.
     */
    public void setTradingSessionEvent(TradingSessionEvent tradingSessionEvent) {
        this.tradingSessionEvent = tradingSessionEvent;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.marketId, 4));
        buffer.put(BendecUtils.uInt32ToByteArray(this.date));
        tradingSessionEvent.toBytes(buffer);
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.marketId, 4));
        buffer.put(BendecUtils.uInt32ToByteArray(this.date));
        tradingSessionEvent.toBytes(buffer);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        marketId,
        date,
        tradingSessionEvent);
    }
    
    @Override
    public String toString() {
        return "TradingSessionStatus {" +
            "header=" + header +
            ", marketId=" + marketId +
            ", date=" + date +
            ", tradingSessionEvent=" + tradingSessionEvent +
            "}";
    }
}