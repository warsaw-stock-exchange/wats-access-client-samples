package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>PriceUpdate</h2>
 * <p>Message indicating a price or YTM update</p>
 * <p>Byte length: 73</p>
 * <p>Header header - Message Header. | size 42</p>
 * <p>ElementId > long (u32) instrumentId - Instrument to which the update refers. | size 4</p>
 * <p>PriceUpdateType priceUpdateType - Indicates price update message variant. | size 1</p>
 * <p>PriceYtmPresence priceYtmPresence - Indicates the filling of fields: value, valueBid, valueAsk. | size 2</p>
 * <p>Price > long (i64) value - Indicates the updated price or YTM. | size 8</p>
 * <p>Price > long (i64) valueBid - Indicates the updated bid price or bid YTM. | size 8</p>
 * <p>Price > long (i64) valueAsk - Indicates the updated ask price or ask YTM. | size 8</p>
 */
public class PriceUpdate implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private PriceUpdateType priceUpdateType;
    private PriceYtmPresence priceYtmPresence;
    private long value;
    private long valueBid;
    private long valueAsk;
    public static final int byteLength = 73;
    
    public PriceUpdate(Header header, long instrumentId, PriceUpdateType priceUpdateType, PriceYtmPresence priceYtmPresence, long value, long valueBid, long valueAsk) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.priceUpdateType = priceUpdateType;
        this.priceYtmPresence = priceYtmPresence;
        this.value = value;
        this.valueBid = valueBid;
        this.valueAsk = valueAsk;
    }
    
    public PriceUpdate(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.priceUpdateType = PriceUpdateType.getPriceUpdateType(bytes, offset + 46);
        this.priceYtmPresence = new PriceYtmPresence(bytes, offset + 47);
        this.value = BendecUtils.int64FromByteArray(bytes, offset + 49);
        this.valueBid = BendecUtils.int64FromByteArray(bytes, offset + 57);
        this.valueAsk = BendecUtils.int64FromByteArray(bytes, offset + 65);
    }
    
    public PriceUpdate(byte[] bytes) {
        this(bytes, 0);
    }
    
    public PriceUpdate() {
    }
    
    /**
     * @return Message Header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Instrument to which the update refers.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    }
    
    /**
     * @return Indicates price update message variant.
     */
    public PriceUpdateType getPriceUpdateType() {
        return this.priceUpdateType;
    }
    
    /**
     * @return Indicates the filling of fields: value, valueBid, valueAsk.
     */
    public PriceYtmPresence getPriceYtmPresence() {
        return this.priceYtmPresence;
    }
    
    /**
     * @return Indicates the updated price or YTM.
     */
    public long getValue() {
        return this.value;
    }
    
    /**
     * @return Indicates the updated bid price or bid YTM.
     */
    public long getValueBid() {
        return this.valueBid;
    }
    
    /**
     * @return Indicates the updated ask price or ask YTM.
     */
    public long getValueAsk() {
        return this.valueAsk;
    }
    
    /**
     * @param header Message Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param instrumentId Instrument to which the update refers.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }
    
    /**
     * @param priceUpdateType Indicates price update message variant.
     */
    public void setPriceUpdateType(PriceUpdateType priceUpdateType) {
        this.priceUpdateType = priceUpdateType;
    }
    
    /**
     * @param priceYtmPresence Indicates the filling of fields: value, valueBid, valueAsk.
     */
    public void setPriceYtmPresence(PriceYtmPresence priceYtmPresence) {
        this.priceYtmPresence = priceYtmPresence;
    }
    
    /**
     * @param value Indicates the updated price or YTM.
     */
    public void setValue(long value) {
        this.value = value;
    }
    
    /**
     * @param valueBid Indicates the updated bid price or bid YTM.
     */
    public void setValueBid(long valueBid) {
        this.valueBid = valueBid;
    }
    
    /**
     * @param valueAsk Indicates the updated ask price or ask YTM.
     */
    public void setValueAsk(long valueAsk) {
        this.valueAsk = valueAsk;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        priceUpdateType.toBytes(buffer);
        priceYtmPresence.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.value));
        buffer.put(BendecUtils.int64ToByteArray(this.valueBid));
        buffer.put(BendecUtils.int64ToByteArray(this.valueAsk));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        priceUpdateType.toBytes(buffer);
        priceYtmPresence.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.value));
        buffer.put(BendecUtils.int64ToByteArray(this.valueBid));
        buffer.put(BendecUtils.int64ToByteArray(this.valueAsk));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        priceUpdateType,
        priceYtmPresence,
        value,
        valueBid,
        valueAsk);
    }
    
    @Override
    public String toString() {
        return "PriceUpdate {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", priceUpdateType=" + priceUpdateType +
            ", priceYtmPresence=" + priceYtmPresence +
            ", value=" + value +
            ", valueBid=" + valueBid +
            ", valueAsk=" + valueAsk +
            "}";
    }
}