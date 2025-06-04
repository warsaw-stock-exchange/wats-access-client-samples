package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
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
     * Trading is not available for the instrument in its current phase.
     */
    INSTRUMENTPHASENOTRADING(106),
    /**
     * Unknown instrument.
     */
    UNKNOWNINSTRUMENT(1001),
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
     * Firm is not a Market Maker for this SecurityID (48)
     */
    FIRMISNOTAMARKETMAKER(1050),
    /**
     * Operation on redistributed instruments forbidden.
     */
    OPERATIONONREDISTRIBUTEDINSTRUMENTSFORBIDDEN(1056),
    /**
     * Firm has not been granted permission to buy and sell the selected instrument. Order entry/modification/cancellation is not possible.
     */
    FIRMNOTAUTHORIZEDTOBUYANDSELLTHEINSTRUMENT(1057),
    /**
     * Firm has not been granted permission to buy the selected instrument (only selling is allowed). Order entry/modification/cancellation on the buy side is not possible.
     */
    FIRMNOTAUTHORIZEDTOBUYTHEINSTRUMENT(1058),
    /**
     * Firm has not been granted permission to sell the selected instrument (only buying is allowed). Order entry/modification/cancellation on the sell side is not possible.
     */
    FIRMNOTAUTHORIZEDTOSELLTHEINSTRUMENT(1059),
    /**
     * Entry/modification/cancellation of orders and quotes is not possible when an instrument is suspended.
     */
    OPERATIONSONORDERSANDQUOTESFORBIDDENDURINGINSTRUMENTSUSPENSION(1061),
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
     * Missing ClearingMemberCode
     */
    MISSINGCLEARINGMEMBERCODE(1076),
    /**
     * Forbidden ClearingMemberCode
     */
    FORBIDDENCLEARINGMEMBERCODE(1077),
    /**
     * Market Model not supported sponsored connection
     */
    MARKETMODELNOTSUPPORTEDONSPONSOREDCONNECTION(1081),
    /**
     * Firm is not a Market Maker for this SecurityID (48).
     */
    NOTAUTHORIZEDTOQUOTEINSTRUMENT(1209),
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
     * In case settlement date provided does not follow settlement calendar
     */
    SETTLEMENTDATEMUSTBEASETTLEMENTDAY(2017),
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
    /**
     * Trading on BLOCK and CROSS instruments is not alllowed, because linked CLOB instrument has not been traded yet.
     */
    NOTRADEFORCLOBREFERENCEINSTRUMENT(2035);
    
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
     * Get TcrRejectionReason by attribute
     * @param val
     * @return TcrRejectionReason enum or null if variant is undefined
     */
    public static TcrRejectionReason getTcrRejectionReason(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get TcrRejectionReason int value
     * @return int value
     */
    public int getTcrRejectionReasonValue() {
        return value; 
    }
    
    /**
     * Get TcrRejectionReason from bytes
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