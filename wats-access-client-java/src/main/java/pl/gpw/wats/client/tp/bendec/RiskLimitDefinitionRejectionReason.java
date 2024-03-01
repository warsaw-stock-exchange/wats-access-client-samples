package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: RiskLimitDefinitionRejectionReason
 * Indicates the reason why the RiskLimitDefinition message was rejected.
 */
public enum RiskLimitDefinitionRejectionReason {
    /**
     * Not applicable.
     */
    NA(0),
    /**
     * Invalid party.
     */
    INVALIDPARTY(1),
    /**
     * Invalid related party.
     */
    INVALIDRELATEDPARTY(2),
    /**
     * Invalid risk limit type.
     */
    INVALIDRISKLIMITTYPE(3),
    /**
     * Invalid risk limit ID.
     */
    INVALIDRISKLIMITID(4),
    /**
     * Invalid risk limit amount.
     */
    INVALIDRISKLIMITAMOUNT(5),
    /**
     * Invalid risk warning level action.
     */
    INVALIDRISKWARNINGLEVELACTION(6),
    /**
     * Invalid risk instrument scope.
     */
    INVALIDRISKINSTRUMENTSCOPE(7),
    /**
     * Risk limit actions not supported.
     */
    RISKLIMITACTIONSNOTSUPPORTED(8),
    /**
     * Warning levels not supported.
     */
    WARNINGLEVELSNOTSUPPORTED(9),
    /**
     * Warning level actions not supported.
     */
    WARNINGLEVELACTIONSNOTSUPPORTED(10),
    /**
     * Risk instrument scope not supported.
     */
    RISKINSTRUMENTSCOPENOTSUPPORTED(11),
    /**
     * Risk limit not approved for party.
     */
    RISKLIMITNOTAPPROVEDFORPARTY(12),
    /**
     * Risk limit already defined for party.
     */
    RISKLIMITALREADYDEFINEDFORPARTY(13),
    /**
     * Instrument not approved for party.
     */
    INSTRUMENTNOTAPPROVEDFORPARTY(14),
    /**
     * Not authorized.
     */
    NOTAUTHORIZED(98),
    /**
     * Other.
     */
    OTHER(99),
    /**
     * Missing Mic code.
     */
    MISSINGMICCODE(101),
    /**
     * Invalid party role qualifier.
     */
    INVALIDPARTYROLEQUALIFIER(102),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, RiskLimitDefinitionRejectionReason> TYPES = new HashMap<>();
    static {
        for (RiskLimitDefinitionRejectionReason type : RiskLimitDefinitionRejectionReason.values()) {
            TYPES.put(type.value, type);
        }
    }


    RiskLimitDefinitionRejectionReason(int newValue) {
        value = newValue;
    }

    /**
     Get RiskLimitDefinitionRejectionReason from java input
     * @param newValue
     * @return RiskLimitDefinitionRejectionReason enum
     */
    public static RiskLimitDefinitionRejectionReason getRiskLimitDefinitionRejectionReason(int newValue) {
        RiskLimitDefinitionRejectionReason val = TYPES.get(newValue);
        return val == null ? RiskLimitDefinitionRejectionReason.UNKNOWN : val;
    }

    /**
     * Get RiskLimitDefinitionRejectionReason int value
     * @return int value
     */
    public int getRiskLimitDefinitionRejectionReasonValue() { return value; }


    /**
     Get RiskLimitDefinitionRejectionReason from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static RiskLimitDefinitionRejectionReason getRiskLimitDefinitionRejectionReason(byte[] bytes, int offset) {
        return getRiskLimitDefinitionRejectionReason(BendecUtils.uInt8FromByteArray(bytes, offset));
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
