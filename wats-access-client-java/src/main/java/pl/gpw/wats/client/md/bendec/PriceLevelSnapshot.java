package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>PriceLevelSnapshot</h2>
 * <p>BBO price levels and volumes.</p>
 * <p>Byte length: 669</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) instrumentId - ID of financial instrument. | size 4</p>
 * <p>u8 > int maxDepth - The number of BBO levels contained in the message. | size 1</p>
 * <p>u8 > int mmBuyQuoteLevel - Id of an level on which mm quote rests (0 means no mm). | size 1</p>
 * <p>u8 > int mmSellQuoteLevel - Id of an level on which mm quote rests (0 means no mm). | size 1</p>
 * <p>PriceLevels > PriceLevel[] (PriceLevel[]) buy - Price levels for buy side. | size 310</p>
 * <p>PriceLevels > PriceLevel[] (PriceLevel[]) sell - Price levels for sell side. | size 310</p>
 */
public class PriceLevelSnapshot implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private int maxDepth;
    private int mmBuyQuoteLevel;
    private int mmSellQuoteLevel;
    private PriceLevel[] buy;
    private PriceLevel[] sell;
    public static final int byteLength = 669;
    
    public PriceLevelSnapshot(Header header, long instrumentId, int maxDepth, int mmBuyQuoteLevel, int mmSellQuoteLevel, PriceLevel[] buy, PriceLevel[] sell) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.maxDepth = maxDepth;
        this.mmBuyQuoteLevel = mmBuyQuoteLevel;
        this.mmSellQuoteLevel = mmSellQuoteLevel;
        this.buy = buy;
        this.sell = sell;
    }
    
    public PriceLevelSnapshot(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.maxDepth = BendecUtils.uInt8FromByteArray(bytes, offset + 46);
        this.mmBuyQuoteLevel = BendecUtils.uInt8FromByteArray(bytes, offset + 47);
        this.mmSellQuoteLevel = BendecUtils.uInt8FromByteArray(bytes, offset + 48);
        this.buy = new PriceLevel[10];
        for(int i = 0; i < buy.length; i++) {
            this.buy[i] = new PriceLevel(bytes, offset + 49 + i * 31);
        }
        this.sell = new PriceLevel[10];
        for(int i = 0; i < sell.length; i++) {
            this.sell[i] = new PriceLevel(bytes, offset + 359 + i * 31);
        }
    }
    
    public PriceLevelSnapshot(byte[] bytes) {
        this(bytes, 0);
    }
    
    public PriceLevelSnapshot() {
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
     * @return The number of BBO levels contained in the message.
     */
    public int getMaxDepth() {
        return this.maxDepth;
    }
    
    /**
     * @return Id of an level on which mm quote rests (0 means no mm).
     */
    public int getMmBuyQuoteLevel() {
        return this.mmBuyQuoteLevel;
    }
    
    /**
     * @return Id of an level on which mm quote rests (0 means no mm).
     */
    public int getMmSellQuoteLevel() {
        return this.mmSellQuoteLevel;
    }
    
    /**
     * @return Price levels for buy side.
     */
    public PriceLevel[] getBuy() {
        return this.buy;
    }
    
    /**
     * @return Price levels for sell side.
     */
    public PriceLevel[] getSell() {
        return this.sell;
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
     * @param maxDepth The number of BBO levels contained in the message.
     */
    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }
    
    /**
     * @param mmBuyQuoteLevel Id of an level on which mm quote rests (0 means no mm).
     */
    public void setMmBuyQuoteLevel(int mmBuyQuoteLevel) {
        this.mmBuyQuoteLevel = mmBuyQuoteLevel;
    }
    
    /**
     * @param mmSellQuoteLevel Id of an level on which mm quote rests (0 means no mm).
     */
    public void setMmSellQuoteLevel(int mmSellQuoteLevel) {
        this.mmSellQuoteLevel = mmSellQuoteLevel;
    }
    
    /**
     * @param buy Price levels for buy side.
     */
    public void setBuy(PriceLevel[] buy) {
        this.buy = buy;
    }
    
    /**
     * @param sell Price levels for sell side.
     */
    public void setSell(PriceLevel[] sell) {
        this.sell = sell;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt8ToByteArray(this.maxDepth));
        buffer.put(BendecUtils.uInt8ToByteArray(this.mmBuyQuoteLevel));
        buffer.put(BendecUtils.uInt8ToByteArray(this.mmSellQuoteLevel));
        for(int i = 0; i < buy.length; i++) {
            buy[i].toBytes(buffer);
        }
        for(int i = 0; i < sell.length; i++) {
            sell[i].toBytes(buffer);
        }
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt8ToByteArray(this.maxDepth));
        buffer.put(BendecUtils.uInt8ToByteArray(this.mmBuyQuoteLevel));
        buffer.put(BendecUtils.uInt8ToByteArray(this.mmSellQuoteLevel));
        for(int i = 0; i < buy.length; i++) {
            buy[i].toBytes(buffer);
        }
        for(int i = 0; i < sell.length; i++) {
            sell[i].toBytes(buffer);
        }
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        maxDepth,
        mmBuyQuoteLevel,
        mmSellQuoteLevel,
        buy,
        sell);
    }
    
    @Override
    public String toString() {
        return "PriceLevelSnapshot {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", maxDepth=" + maxDepth +
            ", mmBuyQuoteLevel=" + mmBuyQuoteLevel +
            ", mmSellQuoteLevel=" + mmSellQuoteLevel +
            ", buy=" + buy +
            ", sell=" + sell +
            "}";
    }
}