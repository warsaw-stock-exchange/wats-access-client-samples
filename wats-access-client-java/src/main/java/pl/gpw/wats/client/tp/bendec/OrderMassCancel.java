package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>OrderMassCancel</h2>
 * <p>Message used to cancel multiple existing orders.</p>
 * <p>Byte length: 63</p>
 * <p>Header header - Header. | size 24</p>
 * <p>MassCancelRequestType massCancelRequestType - Mass cancel request type. | size 1</p>
 * <p>TargetPartyRole targetPartyRole - Target party role filter selection field. | size 1</p>
 * <p>u32 > long targetPartyId - Used to identify the party targeted for the action specified in the message. | size 4</p>
 * <p>ElementId > long (u32) marketSegmentId - Identifies the market segment for request type CancelOrdersForMarketSegment. | size 4</p>
 * <p>ElementId > long (u32) instrumentId | size 4</p>
 * <p>MifidField executingTrader - MifidField of Executing Trader. | size 5</p>
 * <p>ClientOrderId > String (u8[]) clientOrderId - Arbitrary user provided value associated with the mass cancel. | size 20</p>
 */
public class OrderMassCancel implements ByteSerializable, Message {
    private Header header;
    private MassCancelRequestType massCancelRequestType;
    private TargetPartyRole targetPartyRole;
    private long targetPartyId;
    private long marketSegmentId;
    private long instrumentId;
    private MifidField executingTrader;
    private String clientOrderId;
    public static final int byteLength = 63;
    
    public OrderMassCancel(Header header, MassCancelRequestType massCancelRequestType, TargetPartyRole targetPartyRole, long targetPartyId, long marketSegmentId, long instrumentId, MifidField executingTrader, String clientOrderId) {
        this.header = header;
        this.massCancelRequestType = massCancelRequestType;
        this.targetPartyRole = targetPartyRole;
        this.targetPartyId = targetPartyId;
        this.marketSegmentId = marketSegmentId;
        this.instrumentId = instrumentId;
        this.executingTrader = executingTrader;
        this.clientOrderId = clientOrderId;
    }
    
    public OrderMassCancel(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.massCancelRequestType = MassCancelRequestType.getMassCancelRequestType(bytes, offset + 24);
        this.targetPartyRole = TargetPartyRole.getTargetPartyRole(bytes, offset + 25);
        this.targetPartyId = BendecUtils.uInt32FromByteArray(bytes, offset + 26);
        this.marketSegmentId = BendecUtils.uInt32FromByteArray(bytes, offset + 30);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 34);
        this.executingTrader = new MifidField(bytes, offset + 38);
        this.clientOrderId = BendecUtils.stringFromByteArray(bytes, offset + 43, 20);
    }
    
    public OrderMassCancel(byte[] bytes) {
        this(bytes, 0);
    }
    
    public OrderMassCancel() {
    }
    
    /**
     * @return Header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Mass cancel request type.
     */
    public MassCancelRequestType getMassCancelRequestType() {
        return this.massCancelRequestType;
    }
    
    /**
     * @return Target party role filter selection field.
     */
    public TargetPartyRole getTargetPartyRole() {
        return this.targetPartyRole;
    }
    
    /**
     * @return Used to identify the party targeted for the action specified in the message.
     */
    public long getTargetPartyId() {
        return this.targetPartyId;
    }
    
    /**
     * @return Identifies the market segment for request type CancelOrdersForMarketSegment.
     */
    public long getMarketSegmentId() {
        return this.marketSegmentId;
    }
    
    public long getInstrumentId() {
        return this.instrumentId;
    }
    
    /**
     * @return MifidField of Executing Trader.
     */
    public MifidField getExecutingTrader() {
        return this.executingTrader;
    }
    
    /**
     * @return Arbitrary user provided value associated with the mass cancel.
     */
    public String getClientOrderId() {
        return this.clientOrderId;
    }
    
    /**
     * @param header Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param massCancelRequestType Mass cancel request type.
     */
    public void setMassCancelRequestType(MassCancelRequestType massCancelRequestType) {
        this.massCancelRequestType = massCancelRequestType;
    }
    
    /**
     * @param targetPartyRole Target party role filter selection field.
     */
    public void setTargetPartyRole(TargetPartyRole targetPartyRole) {
        this.targetPartyRole = targetPartyRole;
    }
    
    /**
     * @param targetPartyId Used to identify the party targeted for the action specified in the message.
     */
    public void setTargetPartyId(long targetPartyId) {
        this.targetPartyId = targetPartyId;
    }
    
    /**
     * @param marketSegmentId Identifies the market segment for request type CancelOrdersForMarketSegment.
     */
    public void setMarketSegmentId(long marketSegmentId) {
        this.marketSegmentId = marketSegmentId;
    }
    
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }
    
    /**
     * @param executingTrader MifidField of Executing Trader.
     */
    public void setExecutingTrader(MifidField executingTrader) {
        this.executingTrader = executingTrader;
    }
    
    /**
     * @param clientOrderId Arbitrary user provided value associated with the mass cancel.
     */
    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        massCancelRequestType.toBytes(buffer);
        targetPartyRole.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.targetPartyId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.marketSegmentId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        executingTrader.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.clientOrderId, 20));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        massCancelRequestType.toBytes(buffer);
        targetPartyRole.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.targetPartyId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.marketSegmentId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        executingTrader.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.clientOrderId, 20));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        massCancelRequestType,
        targetPartyRole,
        targetPartyId,
        marketSegmentId,
        instrumentId,
        executingTrader,
        clientOrderId);
    }
    
    @Override
    public String toString() {
        return "OrderMassCancel {" +
            "header=" + header +
            ", massCancelRequestType=" + massCancelRequestType +
            ", targetPartyRole=" + targetPartyRole +
            ", targetPartyId=" + targetPartyId +
            ", marketSegmentId=" + marketSegmentId +
            ", instrumentId=" + instrumentId +
            ", executingTrader=" + executingTrader +
            ", clientOrderId=" + clientOrderId +
            "}";
    }
}