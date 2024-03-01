package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>RiskLimitBreach</h2>
 * <p>Message to inform about violation of risk limit.</p>
 * <p>Byte length: 53</p>
 * <p>Header header - Header. | size 16</p>
 * <p>RiskDefinitionId > BigInteger (u64) id - Risk limit definition id. | size 8</p>
 * <p>RiskLimitAmount > BigInteger (u64) amount - Amount of the overrun. | size 8</p>
 * <p>RiskLimitRequestType riskLimitRequestType - Type of risk limit information. | size 1</p>
 * <p>bool > boolean unsolicitedIndicator - Indicates whether or not message is being sent as a result of a subscription request or not. | size 1</p>
 * <p>RiskLimitType limitType - Risk limit definition limit type. | size 2</p>
 * <p>RiskWarningLevelAction riskWarningLevelAction - Action that was take because warning level was exceeded. | size 1</p>
 * <p>RiskLimitAmount > BigInteger (u64) riskLimitUtilizationAmount - Absolute amount of utilization of a party's set risk limit. | size 8</p>
 * <p>Percentage > long (i64) riskLimitUtilizationPercent - Percentage of utilization of a party's set risk limit. | size 8</p>
 * */

public class RiskLimitBreach implements ByteSerializable, Message {

    private Header header;
    private BigInteger id;
    private BigInteger amount;
    private RiskLimitRequestType riskLimitRequestType;
    private boolean unsolicitedIndicator;
    private RiskLimitType limitType;
    private RiskWarningLevelAction riskWarningLevelAction;
    private BigInteger riskLimitUtilizationAmount;
    private long riskLimitUtilizationPercent;
    public static final int byteLength = 53;

    public RiskLimitBreach(Header header, BigInteger id, BigInteger amount, RiskLimitRequestType riskLimitRequestType, boolean unsolicitedIndicator, RiskLimitType limitType, RiskWarningLevelAction riskWarningLevelAction, BigInteger riskLimitUtilizationAmount, long riskLimitUtilizationPercent) {
        this.header = header;
        this.id = id;
        this.amount = amount;
        this.riskLimitRequestType = riskLimitRequestType;
        this.unsolicitedIndicator = unsolicitedIndicator;
        this.limitType = limitType;
        this.riskWarningLevelAction = riskWarningLevelAction;
        this.riskLimitUtilizationAmount = riskLimitUtilizationAmount;
        this.riskLimitUtilizationPercent = riskLimitUtilizationPercent;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.RISKLIMITBREACH);
    }

    public RiskLimitBreach(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.id = BendecUtils.uInt64FromByteArray(bytes, offset + 16);
        this.amount = BendecUtils.uInt64FromByteArray(bytes, offset + 24);
        this.riskLimitRequestType = RiskLimitRequestType.getRiskLimitRequestType(bytes, offset + 32);
        this.unsolicitedIndicator = BendecUtils.booleanFromByteArray(bytes, offset + 33);
        this.limitType = RiskLimitType.getRiskLimitType(bytes, offset + 34);
        this.riskWarningLevelAction = RiskWarningLevelAction.getRiskWarningLevelAction(bytes, offset + 36);
        this.riskLimitUtilizationAmount = BendecUtils.uInt64FromByteArray(bytes, offset + 37);
        this.riskLimitUtilizationPercent = BendecUtils.int64FromByteArray(bytes, offset + 45);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.RISKLIMITBREACH);
    }

    public RiskLimitBreach(byte[] bytes) {
        this(bytes, 0);
    }

    public RiskLimitBreach() {
    }



    /**
     * @return Header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return Risk limit definition id.
     */
    public BigInteger getId() {
        return this.id;
    };
    /**
     * @return Amount of the overrun.
     */
    public BigInteger getAmount() {
        return this.amount;
    };
    /**
     * @return Type of risk limit information.
     */
    public RiskLimitRequestType getRiskLimitRequestType() {
        return this.riskLimitRequestType;
    };
    /**
     * @return Indicates whether or not message is being sent as a result of a subscription request or not.
     */
    public boolean getUnsolicitedIndicator() {
        return this.unsolicitedIndicator;
    };
    /**
     * @return Risk limit definition limit type.
     */
    public RiskLimitType getLimitType() {
        return this.limitType;
    };
    /**
     * @return Action that was take because warning level was exceeded.
     */
    public RiskWarningLevelAction getRiskWarningLevelAction() {
        return this.riskWarningLevelAction;
    };
    /**
     * @return Absolute amount of utilization of a party's set risk limit.
     */
    public BigInteger getRiskLimitUtilizationAmount() {
        return this.riskLimitUtilizationAmount;
    };
    /**
     * @return Percentage of utilization of a party's set risk limit.
     */
    public long getRiskLimitUtilizationPercent() {
        return this.riskLimitUtilizationPercent;
    };

    /**
     * @param header Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param id Risk limit definition id.
     */
    public void setId(BigInteger id) {
        this.id = id;
    };
    /**
     * @param amount Amount of the overrun.
     */
    public void setAmount(BigInteger amount) {
        this.amount = amount;
    };
    /**
     * @param riskLimitRequestType Type of risk limit information.
     */
    public void setRiskLimitRequestType(RiskLimitRequestType riskLimitRequestType) {
        this.riskLimitRequestType = riskLimitRequestType;
    };
    /**
     * @param unsolicitedIndicator Indicates whether or not message is being sent as a result of a subscription request or not.
     */
    public void setUnsolicitedIndicator(boolean unsolicitedIndicator) {
        this.unsolicitedIndicator = unsolicitedIndicator;
    };
    /**
     * @param limitType Risk limit definition limit type.
     */
    public void setLimitType(RiskLimitType limitType) {
        this.limitType = limitType;
    };
    /**
     * @param riskWarningLevelAction Action that was take because warning level was exceeded.
     */
    public void setRiskWarningLevelAction(RiskWarningLevelAction riskWarningLevelAction) {
        this.riskWarningLevelAction = riskWarningLevelAction;
    };
    /**
     * @param riskLimitUtilizationAmount Absolute amount of utilization of a party's set risk limit.
     */
    public void setRiskLimitUtilizationAmount(BigInteger riskLimitUtilizationAmount) {
        this.riskLimitUtilizationAmount = riskLimitUtilizationAmount;
    };
    /**
     * @param riskLimitUtilizationPercent Percentage of utilization of a party's set risk limit.
     */
    public void setRiskLimitUtilizationPercent(long riskLimitUtilizationPercent) {
        this.riskLimitUtilizationPercent = riskLimitUtilizationPercent;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.id));
        buffer.put(BendecUtils.uInt64ToByteArray(this.amount));
        riskLimitRequestType.toBytes(buffer);
        buffer.put(BendecUtils.booleanToByteArray(this.unsolicitedIndicator));
        limitType.toBytes(buffer);
        riskWarningLevelAction.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.riskLimitUtilizationAmount));
        buffer.put(BendecUtils.int64ToByteArray(this.riskLimitUtilizationPercent));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.id));
        buffer.put(BendecUtils.uInt64ToByteArray(this.amount));
        riskLimitRequestType.toBytes(buffer);
        buffer.put(BendecUtils.booleanToByteArray(this.unsolicitedIndicator));
        limitType.toBytes(buffer);
        riskWarningLevelAction.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.riskLimitUtilizationAmount));
        buffer.put(BendecUtils.int64ToByteArray(this.riskLimitUtilizationPercent));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, id, amount, riskLimitRequestType, unsolicitedIndicator, limitType, riskWarningLevelAction, riskLimitUtilizationAmount, riskLimitUtilizationPercent);
    }

    @Override
    public String toString() {
        return "RiskLimitBreach{" +
            "header=" + header +
            ", id=" + id +
            ", amount=" + amount +
            ", riskLimitRequestType=" + riskLimitRequestType +
            ", unsolicitedIndicator=" + unsolicitedIndicator +
            ", limitType=" + limitType +
            ", riskWarningLevelAction=" + riskWarningLevelAction +
            ", riskLimitUtilizationAmount=" + riskLimitUtilizationAmount +
            ", riskLimitUtilizationPercent=" + riskLimitUtilizationPercent +
            '}';
        }
}
