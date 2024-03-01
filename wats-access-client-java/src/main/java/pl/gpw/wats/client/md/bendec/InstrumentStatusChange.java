package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>InstrumentStatusChange</h2>
 * <p>Start of a new trading phase.</p>
 * <p>Byte length: 49</p>
 * <p>Header header - Message header. | size 39</p>
 * <p>ElementId > long (u32) instrumentId - ID of financial instrument. | size 4</p>
 * <p>ElementId > long (u32) tradingPhaseId - Trading phase ID. | size 4</p>
 * <p>InstrumentStatus status - Financial instrument status. | size 1</p>
 * <p>bool > boolean stressedMarket - Stressed market conditions is called for when instrument experience high and short term intraday volatility. | size 1</p>
 * */

public class InstrumentStatusChange implements ByteSerializable, Message {

    private Header header;
    private long instrumentId;
    private long tradingPhaseId;
    private InstrumentStatus status;
    private boolean stressedMarket;
    public static final int byteLength = 49;

    public InstrumentStatusChange(Header header, long instrumentId, long tradingPhaseId, InstrumentStatus status, boolean stressedMarket) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.tradingPhaseId = tradingPhaseId;
        this.status = status;
        this.stressedMarket = stressedMarket;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.INSTRUMENTSTATUSCHANGE);
    }

    public InstrumentStatusChange(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 39);
        this.tradingPhaseId = BendecUtils.uInt32FromByteArray(bytes, offset + 43);
        this.status = InstrumentStatus.getInstrumentStatus(bytes, offset + 47);
        this.stressedMarket = BendecUtils.booleanFromByteArray(bytes, offset + 48);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.INSTRUMENTSTATUSCHANGE);
    }

    public InstrumentStatusChange(byte[] bytes) {
        this(bytes, 0);
    }

    public InstrumentStatusChange() {
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
     * @return Trading phase ID.
     */
    public long getTradingPhaseId() {
        return this.tradingPhaseId;
    };
    /**
     * @return Financial instrument status.
     */
    public InstrumentStatus getStatus() {
        return this.status;
    };
    /**
     * @return Stressed market conditions is called for when instrument experience high and short term intraday volatility.
     */
    public boolean getStressedMarket() {
        return this.stressedMarket;
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
     * @param tradingPhaseId Trading phase ID.
     */
    public void setTradingPhaseId(long tradingPhaseId) {
        this.tradingPhaseId = tradingPhaseId;
    };
    /**
     * @param status Financial instrument status.
     */
    public void setStatus(InstrumentStatus status) {
        this.status = status;
    };
    /**
     * @param stressedMarket Stressed market conditions is called for when instrument experience high and short term intraday volatility.
     */
    public void setStressedMarket(boolean stressedMarket) {
        this.stressedMarket = stressedMarket;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradingPhaseId));
        status.toBytes(buffer);
        buffer.put(BendecUtils.booleanToByteArray(this.stressedMarket));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradingPhaseId));
        status.toBytes(buffer);
        buffer.put(BendecUtils.booleanToByteArray(this.stressedMarket));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, instrumentId, tradingPhaseId, status, stressedMarket);
    }

    @Override
    public String toString() {
        return "InstrumentStatusChange{" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", tradingPhaseId=" + tradingPhaseId +
            ", status=" + status +
            ", stressedMarket=" + stressedMarket +
            '}';
        }
}
