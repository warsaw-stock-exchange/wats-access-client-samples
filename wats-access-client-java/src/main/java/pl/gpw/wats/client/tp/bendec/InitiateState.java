package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>InitiateState</h2>
 * <p>The information that the market switches to given state. Sent by MM.</p>
 * <p>Byte length: 21</p>
 * <p>Header header - Message header. | size 16</p>
 * <p>ElementId > long (u32) instrumentId - Instrument ID | size 4</p>
 * <p>HybridMarketState state - The state the mm wants the hybrid market to switch to. | size 1</p>
 * */

public class InitiateState implements ByteSerializable, Message {

    private Header header;
    private long instrumentId;
    private HybridMarketState state;
    public static final int byteLength = 21;

    public InitiateState(Header header, long instrumentId, HybridMarketState state) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.state = state;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.INITIATESTATE);
    }

    public InitiateState(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 16);
        this.state = HybridMarketState.getHybridMarketState(bytes, offset + 20);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.INITIATESTATE);
    }

    public InitiateState(byte[] bytes) {
        this(bytes, 0);
    }

    public InitiateState() {
    }



    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return Instrument ID
     */
    public long getInstrumentId() {
        return this.instrumentId;
    };
    /**
     * @return The state the mm wants the hybrid market to switch to.
     */
    public HybridMarketState getState() {
        return this.state;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param instrumentId Instrument ID
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    };
    /**
     * @param state The state the mm wants the hybrid market to switch to.
     */
    public void setState(HybridMarketState state) {
        this.state = state;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        state.toBytes(buffer);
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        state.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, instrumentId, state);
    }

    @Override
    public String toString() {
        return "InitiateState{" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", state=" + state +
            '}';
        }
}
