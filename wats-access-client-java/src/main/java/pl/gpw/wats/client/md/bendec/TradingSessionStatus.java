package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>TradingSessionStatus</h2>
 * <p>The Trading Session Status provides information on the status of a market and on a trading day events.</p>
 * <p>Byte length: 51</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>MicCode > String (u8[]) marketId - Market structure's Market Identifier Code (MIC) as specified in ISO 10383. | size 4</p>
 * <p>ElementId > long (u32) marketStructureId - ID of the financial instrument's market segment. | size 4</p>
 * <p>TradingSessionEvent tradingSessionEvent - Identifies an event related to the trading status of a trading session. | size 1</p>
 */
public class TradingSessionStatus implements ByteSerializable, Message {
    private Header header;
    private String marketId;
    private long marketStructureId;
    private TradingSessionEvent tradingSessionEvent;
    public static final int byteLength = 51;
    
    public TradingSessionStatus(Header header, String marketId, long marketStructureId, TradingSessionEvent tradingSessionEvent) {
        this.header = header;
        this.marketId = marketId;
        this.marketStructureId = marketStructureId;
        this.tradingSessionEvent = tradingSessionEvent;
    }
    
    public TradingSessionStatus(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.marketId = BendecUtils.stringFromByteArray(bytes, offset + 42, 4);
        this.marketStructureId = BendecUtils.uInt32FromByteArray(bytes, offset + 46);
        this.tradingSessionEvent = TradingSessionEvent.getTradingSessionEvent(bytes, offset + 50);
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
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.marketId, 4));
        buffer.put(BendecUtils.uInt32ToByteArray(this.marketStructureId));
        tradingSessionEvent.toBytes(buffer);
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.marketId, 4));
        buffer.put(BendecUtils.uInt32ToByteArray(this.marketStructureId));
        tradingSessionEvent.toBytes(buffer);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        marketId,
        marketStructureId,
        tradingSessionEvent);
    }
    
    @Override
    public String toString() {
        return "TradingSessionStatus {" +
            "header=" + header +
            ", marketId=" + marketId +
            ", marketStructureId=" + marketStructureId +
            ", tradingSessionEvent=" + tradingSessionEvent +
            "}";
    }
}