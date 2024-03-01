package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>PriceLevelSnapshot</h2>
 * <p>BBO price levels and volumes.</p>
 * <p>Byte length: 664</p>
 * <p>Header header - Message header. | size 39</p>
 * <p>ElementId > long (u32) instrumentId - ID of financial instrument. | size 4</p>
 * <p>u8 > int maxDepth - The number of BBO levels contained in the message. | size 1</p>
 * <p>PriceLevels > PriceLevel[] (PriceLevel[]) buy - Price levels for buy side. | size 310</p>
 * <p>PriceLevels > PriceLevel[] (PriceLevel[]) sell - Price levels for sell side. | size 310</p>
 * */

public class PriceLevelSnapshot implements ByteSerializable, Message {

    private Header header;
    private long instrumentId;
    private int maxDepth;
    private PriceLevel[] buy;
    private PriceLevel[] sell;
    public static final int byteLength = 664;

    public PriceLevelSnapshot(Header header, long instrumentId, int maxDepth, PriceLevel[] buy, PriceLevel[] sell) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.maxDepth = maxDepth;
        this.buy = buy;
        this.sell = sell;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.PRICELEVELSNAPSHOT);
    }

    public PriceLevelSnapshot(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 39);
        this.maxDepth = BendecUtils.uInt8FromByteArray(bytes, offset + 43);
        this.buy = new PriceLevel[10];
        for(int i = 0; i < buy.length; i++) {
            this.buy[i] = new PriceLevel(bytes, offset + 44 + i * 31);
        }
        this.sell = new PriceLevel[10];
        for(int i = 0; i < sell.length; i++) {
            this.sell[i] = new PriceLevel(bytes, offset + 354 + i * 31);
        }
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.PRICELEVELSNAPSHOT);
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
    };
    /**
     * @return ID of financial instrument.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    };
    /**
     * @return The number of BBO levels contained in the message.
     */
    public int getMaxDepth() {
        return this.maxDepth;
    };
    /**
     * @return Price levels for buy side.
     */
    public PriceLevel[] getBuy() {
        return this.buy;
    };
    /**
     * @return Price levels for sell side.
     */
    public PriceLevel[] getSell() {
        return this.sell;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param instrumentId ID of financial instrument.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    };
    /**
     * @param maxDepth The number of BBO levels contained in the message.
     */
    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    };
    /**
     * @param buy Price levels for buy side.
     */
    public void setBuy(PriceLevel[] buy) {
        this.buy = buy;
    };
    /**
     * @param sell Price levels for sell side.
     */
    public void setSell(PriceLevel[] sell) {
        this.sell = sell;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt8ToByteArray(this.maxDepth));
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
        for(int i = 0; i < buy.length; i++) {
            buy[i].toBytes(buffer);
        }
        for(int i = 0; i < sell.length; i++) {
            sell[i].toBytes(buffer);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, instrumentId, maxDepth, buy, sell);
    }

    @Override
    public String toString() {
        return "PriceLevelSnapshot{" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", maxDepth=" + maxDepth +
            ", buy=" + buy +
            ", sell=" + sell +
            '}';
        }
}
