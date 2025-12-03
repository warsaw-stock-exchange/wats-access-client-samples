package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>TradeBust</h2>
 * <p>Message used to inform about cancellation of previously accepted Trade.</p>
 * <p>Byte length: 28</p>
 * <p>Header header - Header. | size 24</p>
 * <p>TradeId > long (u32) tradeId - The unique ID assigned to the trade entity once it is received or matched by the exchange or central counterparty. | size 4</p>
 */
public class TradeBust implements ByteSerializable, Message {
    private Header header;
    private long tradeId;
    public static final int byteLength = 28;
    
    public TradeBust(Header header, long tradeId) {
        this.header = header;
        this.tradeId = tradeId;
    }
    
    public TradeBust(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.tradeId = BendecUtils.uInt32FromByteArray(bytes, offset + 24);
    }
    
    public TradeBust(byte[] bytes) {
        this(bytes, 0);
    }
    
    public TradeBust() {
    }
    
    /**
     * @return Header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return The unique ID assigned to the trade entity once it is received or matched by the exchange or central counterparty.
     */
    public long getTradeId() {
        return this.tradeId;
    }
    
    /**
     * @param header Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param tradeId The unique ID assigned to the trade entity once it is received or matched by the exchange or central counterparty.
     */
    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradeId));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradeId));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        tradeId);
    }
    
    @Override
    public String toString() {
        return "TradeBust {" +
            "header=" + header +
            ", tradeId=" + tradeId +
            "}";
    }
}