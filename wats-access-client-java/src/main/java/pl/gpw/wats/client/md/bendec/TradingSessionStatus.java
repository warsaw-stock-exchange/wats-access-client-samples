package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>TradingSessionStatus</h2>
 * <p>The Trading Session Status provides information on the status of a market and on a trading day events.</p>
 * <p>Byte length: 55</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>MicCode > String (u8[]) marketId - Market structure's Market Identifier Code (MIC) as specified in ISO 10383. | size 4</p>
 * <p>ElementId > long (u32) marketStructureId - ID of the financial instrument's market segment. | size 4</p>
 * <p>TradingSessionEvent tradingSessionEvent - Identifies an event related to the trading status of a trading session. | size 1</p>
 * <p>Date > long (u32) date - Date of the business session when provided and 0 for StartOfTechnicalSession and EndOfTechnicalSession events. | size 4</p>
 */
public class TradingSessionStatus implements ByteSerializable, Message {
    private Header header;
    private String marketId;
    private long marketStructureId;
    private TradingSessionEvent tradingSessionEvent;
    private long date;
    public static final int byteLength = 55;

    public TradingSessionStatus(Header header, String marketId, long marketStructureId, TradingSessionEvent tradingSessionEvent, long date) {
        this.header = header;
        this.marketId = marketId;
        this.marketStructureId = marketStructureId;
        this.tradingSessionEvent = tradingSessionEvent;
        this.date = date;
    }
    
    public TradingSessionStatus(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.marketId = BendecUtils.stringFromByteArray(bytes, offset + 42, 4);
        this.marketStructureId = BendecUtils.uInt32FromByteArray(bytes, offset + 46);
        this.tradingSessionEvent = TradingSessionEvent.getTradingSessionEvent(bytes, offset + 50);
        this.date = BendecUtils.uInt32FromByteArray(bytes, offset + 51);
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
     * @return ID of the financial instrument's market segment.
     */
    public long getMarketStructureId() {
        return this.marketStructureId;
    }
    
    /**
     * @return Identifies an event related to the trading status of a trading session.
     */
    public TradingSessionEvent getTradingSessionEvent() {
        return this.tradingSessionEvent;
    }
    
    /**
     * @return Date of the business session when provided and 0 for StartOfTechnicalSession and EndOfTechnicalSession events.
     */
    public long getDate() {
        return this.date;
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
     * @param marketStructureId ID of the financial instrument's market segment.
     */
    public void setMarketStructureId(long marketStructureId) {
        this.marketStructureId = marketStructureId;
    }
    
    /**
     * @param tradingSessionEvent Identifies an event related to the trading status of a trading session.
     */
    public void setTradingSessionEvent(TradingSessionEvent tradingSessionEvent) {
        this.tradingSessionEvent = tradingSessionEvent;
    }
    
    /**
     * @param date Date of the business session when provided and 0 for StartOfTechnicalSession and EndOfTechnicalSession events.
     */
    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.marketId, 4));
        buffer.put(BendecUtils.uInt32ToByteArray(this.marketStructureId));
        tradingSessionEvent.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.date));
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.marketId, 4));
        buffer.put(BendecUtils.uInt32ToByteArray(this.marketStructureId));
        tradingSessionEvent.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.date));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header,
        marketId,
        marketStructureId,
        tradingSessionEvent,
        date);
    }
    
    @Override
    public String toString() {
        return "TradingSessionStatus {" +
            "header=" + header +
            ", marketId=" + marketId +
            ", marketStructureId=" + marketStructureId +
            ", tradingSessionEvent=" + tradingSessionEvent +
            ", date=" + date +
            "}";
    }
}