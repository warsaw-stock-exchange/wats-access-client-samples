package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: RejectReason
 * Code to identify reason for a Reject message.
 */
public enum RejectReason {
    /**
     * Not applicable
     */
    NA(0),
    /**
     * Max throughput exceeded.
     */
    MAXTHROUGHPUTEXCEEDED(1),
    /**
     * Invalid message type.
     */
    INVALIDMSGTYPE(2),
    /**
     * Invalid expire time precision.
     */
    INVALIDEXPIRETIMEPRECISION(3),
    /**
     * Invalid settlement date.
     */
    INVALIDSETTLEMENTDATE(4),
    /**
     * Settlement date required.
     */
    SETTLEMENTDATEREQUIRED(5),
    /**
     * Trade report ID required.
     */
    TRADEREPORTIDREQUIRED(6),
    /**
     * Missing report id (SecondaryTradeReportId or TradeReportRefId).
     */
    MISSINGREPORTIDSECONDARYTRADEREPORTIDORTRADEREPORTREFID(7),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, RejectReason> TYPES = new HashMap<>();
    static {
        for (RejectReason type : RejectReason.values()) {
            TYPES.put(type.value, type);
        }
    }


    RejectReason(int newValue) {
        value = newValue;
    }

    /**
     Get RejectReason from java input
     * @param newValue
     * @return RejectReason enum
     */
    public static RejectReason getRejectReason(int newValue) {
        RejectReason val = TYPES.get(newValue);
        return val == null ? RejectReason.UNKNOWN : val;
    }

    /**
     * Get RejectReason int value
     * @return int value
     */
    public int getRejectReasonValue() { return value; }


    /**
     Get RejectReason from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static RejectReason getRejectReason(byte[] bytes, int offset) {
        return getRejectReason(BendecUtils.uInt8FromByteArray(bytes, offset));
    }

    byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        buffer.put(BendecUtils.uInt8ToByteArray(this.value));
        return buffer.array();
    }

    void toBytes(ByteBuffer buffer) {
        buffer.put(BendecUtils.uInt8ToByteArray(this.value));
    }

}
