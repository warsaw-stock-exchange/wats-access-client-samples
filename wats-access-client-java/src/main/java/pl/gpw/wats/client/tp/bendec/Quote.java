package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>Quote</h2>
 * <p>Quote with bid / ask</p>
 * <p>Byte length: 36</p>
 * <p>ElementId > long (u32) instrumentId - ID of the instrument being traded. | size 4</p>
 * <p>QuoteOrder bid - Bid side of the quote | size 16</p>
 * <p>QuoteOrder ask - Ask side of the quote | size 16</p>
 * */

public class Quote implements ByteSerializable {

    private long instrumentId;
    private QuoteOrder bid;
    private QuoteOrder ask;
    public static final int byteLength = 36;

    public Quote(long instrumentId, QuoteOrder bid, QuoteOrder ask) {
        this.instrumentId = instrumentId;
        this.bid = bid;
        this.ask = ask;
    }

    public Quote(byte[] bytes, int offset) {
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset);
        this.bid = new QuoteOrder(bytes, offset + 4);
        this.ask = new QuoteOrder(bytes, offset + 20);
    }

    public Quote(byte[] bytes) {
        this(bytes, 0);
    }

    public Quote() {
    }



    /**
     * @return ID of the instrument being traded.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    };
    /**
     * @return Bid side of the quote
     */
    public QuoteOrder getBid() {
        return this.bid;
    };
    /**
     * @return Ask side of the quote
     */
    public QuoteOrder getAsk() {
        return this.ask;
    };

    /**
     * @param instrumentId ID of the instrument being traded.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    };
    /**
     * @param bid Bid side of the quote
     */
    public void setBid(QuoteOrder bid) {
        this.bid = bid;
    };
    /**
     * @param ask Ask side of the quote
     */
    public void setAsk(QuoteOrder ask) {
        this.ask = ask;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        bid.toBytes(buffer);
        ask.toBytes(buffer);
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        bid.toBytes(buffer);
        ask.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrumentId, bid, ask);
    }

    @Override
    public String toString() {
        return "Quote{" +
            "instrumentId=" + instrumentId +
            ", bid=" + bid +
            ", ask=" + ask +
            '}';
        }
}
