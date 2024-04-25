package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>OrderMassCancel</h2>
 * <p>Message used to cancel multiple existing orders.</p>
 * <p>Byte length: 30</p>
 * <p>Header header - Header. | size 16</p>
 * <p>MassCancelRequestType massCancelRequestType - Mass cancel request type. | size 1</p>
 * <p>TargetPartyRole targetPartyRole - Target party role filter selection field. | size 1</p>
 * <p>u32 > long targetPartyId - Used to identify the party targeted for the action specified in the message. | size 4</p>
 * <p>ElementId > long (u32) marketSegmentId - Identifies the market segment for request type CancelOrdersForMarketSegment. | size 4</p>
 * <p>ElementId > long (u32) instrumentId | size 4</p>
 * */

public class OrderMassCancel implements ByteSerializable, Message {

    private Header header;
    private MassCancelRequestType massCancelRequestType;
    private TargetPartyRole targetPartyRole;
    private long targetPartyId;
    private long marketSegmentId;
    private long instrumentId;
    public static final int byteLength = 30;

    public OrderMassCancel(Header header, MassCancelRequestType massCancelRequestType, TargetPartyRole targetPartyRole, long targetPartyId, long marketSegmentId, long instrumentId) {
        this.header = header;
        this.massCancelRequestType = massCancelRequestType;
        this.targetPartyRole = targetPartyRole;
        this.targetPartyId = targetPartyId;
        this.marketSegmentId = marketSegmentId;
        this.instrumentId = instrumentId;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.ORDERMASSCANCEL);
    }

    public OrderMassCancel(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.massCancelRequestType = MassCancelRequestType.getMassCancelRequestType(bytes, offset + 16);
        this.targetPartyRole = TargetPartyRole.getTargetPartyRole(bytes, offset + 17);
        this.targetPartyId = BendecUtils.uInt32FromByteArray(bytes, offset + 18);
        this.marketSegmentId = BendecUtils.uInt32FromByteArray(bytes, offset + 22);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 26);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.ORDERMASSCANCEL);
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
    };
    /**
     * @return Mass cancel request type.
     */
    public MassCancelRequestType getMassCancelRequestType() {
        return this.massCancelRequestType;
    };
    /**
     * @return Target party role filter selection field.
     */
    public TargetPartyRole getTargetPartyRole() {
        return this.targetPartyRole;
    };
    /**
     * @return Used to identify the party targeted for the action specified in the message.
     */
    public long getTargetPartyId() {
        return this.targetPartyId;
    };
    /**
     * @return Identifies the market segment for request type CancelOrdersForMarketSegment.
     */
    public long getMarketSegmentId() {
        return this.marketSegmentId;
    };
    public long getInstrumentId() {
        return this.instrumentId;
    };

    /**
     * @param header Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param massCancelRequestType Mass cancel request type.
     */
    public void setMassCancelRequestType(MassCancelRequestType massCancelRequestType) {
        this.massCancelRequestType = massCancelRequestType;
    };
    /**
     * @param targetPartyRole Target party role filter selection field.
     */
    public void setTargetPartyRole(TargetPartyRole targetPartyRole) {
        this.targetPartyRole = targetPartyRole;
    };
    /**
     * @param targetPartyId Used to identify the party targeted for the action specified in the message.
     */
    public void setTargetPartyId(long targetPartyId) {
        this.targetPartyId = targetPartyId;
    };
    /**
     * @param marketSegmentId Identifies the market segment for request type CancelOrdersForMarketSegment.
     */
    public void setMarketSegmentId(long marketSegmentId) {
        this.marketSegmentId = marketSegmentId;
    };
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        massCancelRequestType.toBytes(buffer);
        targetPartyRole.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.targetPartyId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.marketSegmentId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
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
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, massCancelRequestType, targetPartyRole, targetPartyId, marketSegmentId, instrumentId);
    }

    @Override
    public String toString() {
        return "OrderMassCancel{" +
            "header=" + header +
            ", massCancelRequestType=" + massCancelRequestType +
            ", targetPartyRole=" + targetPartyRole +
            ", targetPartyId=" + targetPartyId +
            ", marketSegmentId=" + marketSegmentId +
            ", instrumentId=" + instrumentId +
            '}';
        }
}
