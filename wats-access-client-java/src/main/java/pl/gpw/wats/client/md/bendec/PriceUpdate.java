package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>PriceUpdate</h2>
 * <p>Message indicating a price update</p>
 * <p>Byte length: 52</p>
 * <p>Header header - Message header. | size 39</p>
 * <p>ElementId > long (u32) instrumentId - Instrument to which the price update refers. | size 4</p>
 * <p>PriceType priceType - Price type. | size 1</p>
 * <p>Price > long (i64) price - Indicates the updated price. | size 8</p>
 * */

public class PriceUpdate implements ByteSerializable, Message {

    private Header header;
    private long instrumentId;
    private PriceType priceType;
    private long price;
    public static final int byteLength = 52;

    public PriceUpdate(Header header, long instrumentId, PriceType priceType, long price) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.priceType = priceType;
        this.price = price;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.PRICEUPDATE);
    }

    public PriceUpdate(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 39);
        this.priceType = PriceType.getPriceType(bytes, offset + 43);
        this.price = BendecUtils.int64FromByteArray(bytes, offset + 44);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.PRICEUPDATE);
    }

    public PriceUpdate(byte[] bytes) {
        this(bytes, 0);
    }

    public PriceUpdate() {
    }



    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return Instrument to which the price update refers.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    };
    /**
     * @return Price type.
     */
    public PriceType getPriceType() {
        return this.priceType;
    };
    /**
     * @return Indicates the updated price.
     */
    public long getPrice() {
        return this.price;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param instrumentId Instrument to which the price update refers.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    };
    /**
     * @param priceType Price type.
     */
    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    };
    /**
     * @param price Indicates the updated price.
     */
    public void setPrice(long price) {
        this.price = price;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        priceType.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        priceType.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.price));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, instrumentId, priceType, price);
    }

    @Override
    public String toString() {
        return "PriceUpdate{" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", priceType=" + priceType +
            ", price=" + price +
            '}';
        }
}
