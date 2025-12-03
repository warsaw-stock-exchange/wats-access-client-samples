package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>MarketMakerCommand</h2>
 * <p>Market Maker command request.</p>
 * <p>Byte length: 29</p>
 * <p>Header header - Message header. | size 24</p>
 * <p>ElementId > long (u32) instrumentId - Instrument ID. | size 4</p>
 * <p>CommandAction action - The action for the Market Maker command request. | size 1</p>
 */
public class MarketMakerCommand implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private CommandAction action;
    public static final int byteLength = 29;
    
    public MarketMakerCommand(Header header, long instrumentId, CommandAction action) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.action = action;
    }
    
    public MarketMakerCommand(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 24);
        this.action = CommandAction.getCommandAction(bytes, offset + 28);
    }
    
    public MarketMakerCommand(byte[] bytes) {
        this(bytes, 0);
    }
    
    public MarketMakerCommand() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Instrument ID.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    }
    
    /**
     * @return The action for the Market Maker command request.
     */
    public CommandAction getAction() {
        return this.action;
    }
    
    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param instrumentId Instrument ID.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }
    
    /**
     * @param action The action for the Market Maker command request.
     */
    public void setAction(CommandAction action) {
        this.action = action;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        action.toBytes(buffer);
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        action.toBytes(buffer);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        action);
    }
    
    @Override
    public String toString() {
        return "MarketMakerCommand {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", action=" + action +
            "}";
    }
}