package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>RiskLimitDefinition</h2>
 * <p>Risk limit definition message.</p>
 * <p>Byte length: 102</p>
 * <p>Header header - Header. | size 16</p>
 * <p>RiskDefinitionId > BigInteger (u64) id - RiskLimitDefinition message identifier. | size 8</p>
 * <p>RiskDefinitionId > BigInteger (u64) RiskLimitId - Risk limit row identifier. | size 8</p>
 * <p>ElementId > long (u32) instrumentId - Id of an instrument. | size 4</p>
 * <p>MicCode > String (u8[]) Mic - Mic code is replacing instrument and market segment. | size 4</p>
 * <p>RiskLimitAction action - Risk limit definition action after breach. | size 1</p>
 * <p>RiskLimitAmount > BigInteger (u64) amount - Risk limit definition amount | size 8</p>
 * <p>RiskLimitType limitType - Risk limit definition limit type. | size 2</p>
 * <p>LeiCode > String (u8[]) riskMemberCode - LEI code of the participant imposing the Limits and managing the risk. | size 20</p>
 * <p>LeiCode > String (u8[]) memberPartyId - Exchange member risk entity identifier, should be LEI. | size 20</p>
 * <p>ShortCode > long (u32) clientPartyId - Exchange member client risk identifier, is optional. | size 4</p>
 * <p>PartyRoleQualifier clientRoleQualifier - Risk entity role qualifier. | size 1</p>
 * <p>RiskOperation operation - Risk limit definition operation type. | size 1</p>
 * <p>ElementId > long (u32) marketSegmentId - A market segment id. | size 4</p>
 * <p>Capacity capacity - Capacity of the party making the order (either principal or agency). | size 1</p>
 * */

public class RiskLimitDefinition implements ByteSerializable, Message {

    private Header header;
    private BigInteger id;
    private BigInteger RiskLimitId;
    private long instrumentId;
    private String Mic;
    private RiskLimitAction action;
    private BigInteger amount;
    private RiskLimitType limitType;
    private String riskMemberCode;
    private String memberPartyId;
    private long clientPartyId;
    private PartyRoleQualifier clientRoleQualifier;
    private RiskOperation operation;
    private long marketSegmentId;
    private Capacity capacity;
    public static final int byteLength = 102;

    public RiskLimitDefinition(Header header, BigInteger id, BigInteger RiskLimitId, long instrumentId, String Mic, RiskLimitAction action, BigInteger amount, RiskLimitType limitType, String riskMemberCode, String memberPartyId, long clientPartyId, PartyRoleQualifier clientRoleQualifier, RiskOperation operation, long marketSegmentId, Capacity capacity) {
        this.header = header;
        this.id = id;
        this.RiskLimitId = RiskLimitId;
        this.instrumentId = instrumentId;
        this.Mic = Mic;
        this.action = action;
        this.amount = amount;
        this.limitType = limitType;
        this.riskMemberCode = riskMemberCode;
        this.memberPartyId = memberPartyId;
        this.clientPartyId = clientPartyId;
        this.clientRoleQualifier = clientRoleQualifier;
        this.operation = operation;
        this.marketSegmentId = marketSegmentId;
        this.capacity = capacity;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.RISKLIMITDEFINITION);
    }

    public RiskLimitDefinition(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.id = BendecUtils.uInt64FromByteArray(bytes, offset + 16);
        this.RiskLimitId = BendecUtils.uInt64FromByteArray(bytes, offset + 24);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 32);
        this.Mic = BendecUtils.stringFromByteArray(bytes, offset + 36, 4);
        this.action = RiskLimitAction.getRiskLimitAction(bytes, offset + 40);
        this.amount = BendecUtils.uInt64FromByteArray(bytes, offset + 41);
        this.limitType = RiskLimitType.getRiskLimitType(bytes, offset + 49);
        this.riskMemberCode = BendecUtils.stringFromByteArray(bytes, offset + 51, 20);
        this.memberPartyId = BendecUtils.stringFromByteArray(bytes, offset + 71, 20);
        this.clientPartyId = BendecUtils.uInt32FromByteArray(bytes, offset + 91);
        this.clientRoleQualifier = PartyRoleQualifier.getPartyRoleQualifier(bytes, offset + 95);
        this.operation = RiskOperation.getRiskOperation(bytes, offset + 96);
        this.marketSegmentId = BendecUtils.uInt32FromByteArray(bytes, offset + 97);
        this.capacity = Capacity.getCapacity(bytes, offset + 101);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.RISKLIMITDEFINITION);
    }

    public RiskLimitDefinition(byte[] bytes) {
        this(bytes, 0);
    }

    public RiskLimitDefinition() {
    }



    /**
     * @return Header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return RiskLimitDefinition message identifier.
     */
    public BigInteger getId() {
        return this.id;
    };
    /**
     * @return Risk limit row identifier.
     */
    public BigInteger getRiskLimitId() {
        return this.RiskLimitId;
    };
    /**
     * @return Id of an instrument.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    };
    /**
     * @return Mic code is replacing instrument and market segment.
     */
    public String getMic() {
        return this.Mic;
    };
    /**
     * @return Risk limit definition action after breach.
     */
    public RiskLimitAction getAction() {
        return this.action;
    };
    /**
     * @return Risk limit definition amount
     */
    public BigInteger getAmount() {
        return this.amount;
    };
    /**
     * @return Risk limit definition limit type.
     */
    public RiskLimitType getLimitType() {
        return this.limitType;
    };
    /**
     * @return LEI code of the participant imposing the Limits and managing the risk.
     */
    public String getRiskMemberCode() {
        return this.riskMemberCode;
    };
    /**
     * @return Exchange member risk entity identifier, should be LEI.
     */
    public String getMemberPartyId() {
        return this.memberPartyId;
    };
    /**
     * @return Exchange member client risk identifier, is optional.
     */
    public long getClientPartyId() {
        return this.clientPartyId;
    };
    /**
     * @return Risk entity role qualifier.
     */
    public PartyRoleQualifier getClientRoleQualifier() {
        return this.clientRoleQualifier;
    };
    /**
     * @return Risk limit definition operation type.
     */
    public RiskOperation getOperation() {
        return this.operation;
    };
    /**
     * @return A market segment id.
     */
    public long getMarketSegmentId() {
        return this.marketSegmentId;
    };
    /**
     * @return Capacity of the party making the order (either principal or agency).
     */
    public Capacity getCapacity() {
        return this.capacity;
    };

    /**
     * @param header Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param id RiskLimitDefinition message identifier.
     */
    public void setId(BigInteger id) {
        this.id = id;
    };
    /**
     * @param RiskLimitId Risk limit row identifier.
     */
    public void setRiskLimitId(BigInteger RiskLimitId) {
        this.RiskLimitId = RiskLimitId;
    };
    /**
     * @param instrumentId Id of an instrument.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    };
    /**
     * @param Mic Mic code is replacing instrument and market segment.
     */
    public void setMic(String Mic) {
        this.Mic = Mic;
    };
    /**
     * @param action Risk limit definition action after breach.
     */
    public void setAction(RiskLimitAction action) {
        this.action = action;
    };
    /**
     * @param amount Risk limit definition amount
     */
    public void setAmount(BigInteger amount) {
        this.amount = amount;
    };
    /**
     * @param limitType Risk limit definition limit type.
     */
    public void setLimitType(RiskLimitType limitType) {
        this.limitType = limitType;
    };
    /**
     * @param riskMemberCode LEI code of the participant imposing the Limits and managing the risk.
     */
    public void setRiskMemberCode(String riskMemberCode) {
        this.riskMemberCode = riskMemberCode;
    };
    /**
     * @param memberPartyId Exchange member risk entity identifier, should be LEI.
     */
    public void setMemberPartyId(String memberPartyId) {
        this.memberPartyId = memberPartyId;
    };
    /**
     * @param clientPartyId Exchange member client risk identifier, is optional.
     */
    public void setClientPartyId(long clientPartyId) {
        this.clientPartyId = clientPartyId;
    };
    /**
     * @param clientRoleQualifier Risk entity role qualifier.
     */
    public void setClientRoleQualifier(PartyRoleQualifier clientRoleQualifier) {
        this.clientRoleQualifier = clientRoleQualifier;
    };
    /**
     * @param operation Risk limit definition operation type.
     */
    public void setOperation(RiskOperation operation) {
        this.operation = operation;
    };
    /**
     * @param marketSegmentId A market segment id.
     */
    public void setMarketSegmentId(long marketSegmentId) {
        this.marketSegmentId = marketSegmentId;
    };
    /**
     * @param capacity Capacity of the party making the order (either principal or agency).
     */
    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.id));
        buffer.put(BendecUtils.uInt64ToByteArray(this.RiskLimitId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.stringToByteArray(this.Mic, 4));
        action.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.amount));
        limitType.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.riskMemberCode, 20));
        buffer.put(BendecUtils.stringToByteArray(this.memberPartyId, 20));
        buffer.put(BendecUtils.uInt32ToByteArray(this.clientPartyId));
        clientRoleQualifier.toBytes(buffer);
        operation.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.marketSegmentId));
        capacity.toBytes(buffer);
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.id));
        buffer.put(BendecUtils.uInt64ToByteArray(this.RiskLimitId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.stringToByteArray(this.Mic, 4));
        action.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.amount));
        limitType.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.riskMemberCode, 20));
        buffer.put(BendecUtils.stringToByteArray(this.memberPartyId, 20));
        buffer.put(BendecUtils.uInt32ToByteArray(this.clientPartyId));
        clientRoleQualifier.toBytes(buffer);
        operation.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.marketSegmentId));
        capacity.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, id, RiskLimitId, instrumentId, Mic, action, amount, limitType, riskMemberCode, memberPartyId, clientPartyId, clientRoleQualifier, operation, marketSegmentId, capacity);
    }

    @Override
    public String toString() {
        return "RiskLimitDefinition{" +
            "header=" + header +
            ", id=" + id +
            ", RiskLimitId=" + RiskLimitId +
            ", instrumentId=" + instrumentId +
            ", Mic=" + Mic +
            ", action=" + action +
            ", amount=" + amount +
            ", limitType=" + limitType +
            ", riskMemberCode=" + riskMemberCode +
            ", memberPartyId=" + memberPartyId +
            ", clientPartyId=" + clientPartyId +
            ", clientRoleQualifier=" + clientRoleQualifier +
            ", operation=" + operation +
            ", marketSegmentId=" + marketSegmentId +
            ", capacity=" + capacity +
            '}';
        }
}
