package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: TcrRejectionReason
 * Code to identify the reason for TCR rejection
 */
public enum TcrRejectionReason {
    /**
     * Not applicable.
     */
    NA(1),
    /**
     * Exchange closed.
     */
    EXCHANGECLOSED(2),
    /**
     * Invalid price increment.
     */
    INVALIDPRICEINCREMENT(18),
    /**
     * Other.
     */
    OTHER(99),
    /**
     * Unknown instrument.
     */
    UNKNOWNINSTRUMENTID(100),
    /**
     * Trading is not available for the instrument in its current phase.
     */
    INSTRUMENTPHASENOTRADING(106),
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
     * The order quantity must be greater than the minimum quanity.
     */
    ORDERQUANTITYMUSTBEGREATERTHANMINIMUMQUANTITY(1025),
    /**
     * The order quantity must be lower than the maximum quantity.
     */
    ORDERQUANTITYMUSTBELOWERTHANMAXIMUMQUANTITY(1026),
    /**
     * The order price must be greater than minimum price.
     */
    ORDERPRICEMUSTBEGREATERTHANMINIMUMPRICE(1027),
    /**
     * The order price must be greater than maximum price.
     */
    ORDERPRICEMUSTBELOWERTHANMAXIMUMPRICE(1028),
    /**
     * The order price must be non-zero.
     */
    ORDERPRICEMUSTBENONZERO(1029),
    /**
     * The order value must be greater than minimum value.
     */
    ORDERVALUEMUSTBEGREATERTHANMINIMUMVALUE(1030),
    /**
     * The order value must be lower than maximum value.
     */
    ORDERVALUEMUSTBELOWERTHANMAXIMUMVALUE(1031),
    /**
     * The validation for collars has failed. The price is too low.
     */
    PRICEBELOWLOWCOLLAR(1037),
    /**
     * The validation for collars has failed. The price is too high.
     */
    PRICEABOVEHIGHCOLLAR(1038),
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
     * Unknown trade report
     */
    UNKNOWNTRADEREPORT(2001),
    /**
     * Duplicate TradeReportId
     */
    DUPLICATETRADEREPORTID(2002),
    /**
     * TradeReportType not compatible with TradeReportTransType
     */
    TRADEREPORTTYPENOTCOMPATIBLEWITHTRADEREPORTTRANSTYPE(2005),
    /**
     * Invalid ExecType
     */
    INVALIDEXECTYPE(2008),
    /**
     * TradeReportRefId not allowed
     */
    TRADEREPORTREFIDNOTALLOWED(2009),
    /**
     * Settlement date cannot be earlier than minimum settlement date
     */
    SETTLEMENTDATECANNOTBEEARLIERTHANMINIMUMSETTLEMENTDATE(2015),
    /**
     * Settlement date cannot be later than maximum settlement date
     */
    SETTLEMENTDATECANNOTBELATERTHANMAXIMUMSETTLEMENTDATE(2016),
    /**
     * Unknown contra firm.
     */
    UNKNOWNCONTRAFIRM(2022),
    /**
     * Sent attribute does not match original value
     */
    SENTATTRIBUTEDOESNOTMATCHORIGINALVALUE(2024),
    /**
     * Request not allowed for BLOCK instrument
     */
    REQUESTNOTALLOWEDFORBLOCKINSTRUMENT(2026),
    /**
     * Request not allowed for CLOB instrument
     */
    REQUESTNOTALLOWEDFORCLOBINSTRUMENT(2027),
    /**
     * Request not allowed for CROSS instrument
     */
    REQUESTNOTALLOWEDFORCROSSINSTRUMENT(2028),
    /**
     * Invalid MatchStatus
     */
    INVALIDMATCHSTATUS(2029),
    /**
     * Cross not allowed outside of CLOB instrument spread
     */
    CROSSNOTALLOWEDOUTSIDEOFCLOBINSTRUMENTSPREAD(2030),
    /**
     * Cross price not equal to the reference price
     */
    CROSSPRICENOTEQUALTOTHEREFERENCEPRICE(2031),
    /**
     * Cross not allowed during CLOB instrument Auction or Suspension
     */
    CROSSNOTALLOWEDDURINGCLOBINSTRUMENTAUCTIONORSUSPENSION(2032),
    /**
     * Forbidden SecondaryTradeReportID
     */
    FORBIDDENSECONDARYTRADEREPORTID(2033),
    /**
     * Unknown SecondaryTradeReportID
     */
    UNKNOWNSECONDARYTRADEREPORTID(2034),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 2;


    private static final Map<Integer, TcrRejectionReason> TYPES = new HashMap<>();
    static {
        for (TcrRejectionReason type : TcrRejectionReason.values()) {
            TYPES.put(type.value, type);
        }
    }


    TcrRejectionReason(int newValue) {
        value = newValue;
    }

    /**
     Get TcrRejectionReason from java input
     * @param newValue
     * @return TcrRejectionReason enum
     */
    public static TcrRejectionReason getTcrRejectionReason(int newValue) {
        TcrRejectionReason val = TYPES.get(newValue);
        return val == null ? TcrRejectionReason.UNKNOWN : val;
    }

    /**
     * Get TcrRejectionReason int value
     * @return int value
     */
    public int getTcrRejectionReasonValue() { return value; }


    /**
     Get TcrRejectionReason from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static TcrRejectionReason getTcrRejectionReason(byte[] bytes, int offset) {
        return getTcrRejectionReason(BendecUtils.uInt16FromByteArray(bytes, offset));
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
