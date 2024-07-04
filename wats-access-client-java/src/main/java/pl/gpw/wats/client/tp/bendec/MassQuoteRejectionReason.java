package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: MassQuoteRejectionReason
 * Indicates the status of the mass quote.
 */
public enum MassQuoteRejectionReason {
    /**
     * Not applicable.
     */
    NA(1),
    /**
     * Exchange closed.
     */
    EXCHANGECLOSED(2),
    /**
     * Other.
     */
    OTHER(99),
    /**
     * Invalid execution trader.
     */
    INVALIDEXECUTIONTRADER(1005),
    /**
     * Invalid decision maker.
     */
    INVALIDDECISIONMAKER(1006),
    /**
     * Invalid client.
     */
    INVALIDCLIENTID(1007),
    /**
     * Invalid Party Role Qualifier for Client Id Party group
     */
    INVALIDPARTYROLEQUALIFIERFORCLIENTID(1008),
    /**
     * Invalid Party Role Qualifier for Executing Trader Party group
     */
    INVALIDPARTYROLEQUALIFIERFOREXECUTINGTRADER(1009),
    /**
     * Invalid Party Role Qualifier for Investment Decision Maker Party group
     */
    INVALIDPARTYROLEQUALIFIERFORINVESTMENTDECISIONMAKER(1010),
    /**
     * Invalid PartyID (448) for Client ID
     */
    INVALIDPARTYIDFORCLIENTID(1070),
    /**
     * Invalid PartyID (448) for Executing Trader
     */
    INVALIDPARTYIDFOREXECUTINGTRADER(1071),
    /**
     * Invalid PartyID (448) for Investment Decision Maker
     */
    INVALIDPARTYIDFORINVESTMENTDECISIONMAKER(1072),
    /**
     * Invalid PartyRoleQualifier (2376) for PartyID (448)
     */
    INVALIDPARTYROLEQUALIFIERFORPARTYID(1075),
    /**
     * Multiple quotes for the same instrument within Mass Quote message.
     */
    DUPLICATEINSTRUMENT(1202),
    /**
     * Invalid quotes count value.
     */
    INVALIDQUOTESCOUNT(1204),
    /**
     * Forbidden OrderCapacity (528) value for Mass Quote.
     */
    FORBIDDENORDERCAPACITYVALUE(1210);
    
    private final int value;
    private final int byteLength = 2;
    
    private static final Map<Integer, MassQuoteRejectionReason> TYPES = new HashMap<>();
    static {
        for (MassQuoteRejectionReason type : MassQuoteRejectionReason.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    MassQuoteRejectionReason(int newValue) {
        value = newValue;
    }
    
    /**
     * Get MassQuoteRejectionReason by attribute
     * @param val
     * @return MassQuoteRejectionReason enum or null if variant is undefined
     */
    public static MassQuoteRejectionReason getMassQuoteRejectionReason(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get MassQuoteRejectionReason int value
     * @return int value
     */
    public int getMassQuoteRejectionReasonValue() {
        return value; 
    }
    
    /**
     * Get MassQuoteRejectionReason from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MassQuoteRejectionReason getMassQuoteRejectionReason(byte[] bytes, int offset) {
        return getMassQuoteRejectionReason(BendecUtils.uInt16FromByteArray(bytes, offset));
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