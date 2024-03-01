package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: RiskLimitType
 * Indicates the risk controls.
 */
public enum RiskLimitType {
    /**
     * Per buy order volume.
     */
    PERBUYORDERVOLUME(301),
    /**
     * Per buy order notional value.
     */
    PERBUYORDERNOTIONALVALUE(302),
    /**
     * Per sell order volume.
     */
    PERSELLORDERVOLUME(311),
    /**
     * Per sell order notional value.
     */
    PERSELLORDERNOTIONALVALUE(312),
    /**
     * Per buy price limit.
     */
    PERBUYPRICELIMIT(313),
    /**
     * Per Sell Price Limit.
     */
    PERSELLPRICELIMIT(314),
    /**
     * Per total buy traded value.
     */
    PERTOTALBUYTRADEDVALUE(315),
    /**
     * Per total sell traded value.
     */
    PERTOTALSELLTRADEDVALUE(316),
    /**
     * Per total traded value.
     */
    PERTOTALTRADEDVALUE(317),
    /**
     * Per total buy open orders value.
     */
    PERTOTALBUYOPENORDERSVALUE(318),
    /**
     * Per total sell open orders value.
     */
    PERTOTALSELLOPENORDERSVALUE(319),
    /**
     * Per total open orders value.
     */
    PERTOTALOPENORDERSVALUE(320),
    /**
     * Per total buy risk value.
     */
    PERTOTALBUYRISKVALUE(321),
    /**
     * Per total sell risk value.
     */
    PERTOTALSELLRISKVALUE(322),
    /**
     * Per total risk value.
     */
    PERTOTALRISKVALUE(323),
    /**
     * Per total net risk value.
     */
    PERTOTALNETRISKVALUE(324),
    /**
     * Per total daily number Of orders.
     */
    PERTOTALDAILYNUMBEROFORDERS(325),
    /**
     * Requiered risk limits are missing.
     */
    RISKLIMITNOTDEFINED(326),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 2;


    private static final Map<Integer, RiskLimitType> TYPES = new HashMap<>();
    static {
        for (RiskLimitType type : RiskLimitType.values()) {
            TYPES.put(type.value, type);
        }
    }


    RiskLimitType(int newValue) {
        value = newValue;
    }

    /**
     Get RiskLimitType from java input
     * @param newValue
     * @return RiskLimitType enum
     */
    public static RiskLimitType getRiskLimitType(int newValue) {
        RiskLimitType val = TYPES.get(newValue);
        return val == null ? RiskLimitType.UNKNOWN : val;
    }

    /**
     * Get RiskLimitType int value
     * @return int value
     */
    public int getRiskLimitTypeValue() { return value; }


    /**
     Get RiskLimitType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static RiskLimitType getRiskLimitType(byte[] bytes, int offset) {
        return getRiskLimitType(BendecUtils.uInt16FromByteArray(bytes, offset));
    }

    byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        buffer.put(BendecUtils.uInt16ToByteArray(this.value));
        return buffer.array();
    }

    void toBytes(ByteBuffer buffer) {
        buffer.put(BendecUtils.uInt16ToByteArray(this.value));
    }

}
