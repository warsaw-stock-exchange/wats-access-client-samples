package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: OrderRejectionReason
 * A code used to identify the reason for order rejection.
 */
public enum OrderRejectionReason {
    /**
     * Not applicable.
     */
    NA(0),
    /**
     * Order id not recognized by the system.
     */
    UNKNOWNORDER(1),
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
     * Cannot modify MiFID flags.
     */
    CANNOTMODIFYMIFIDFLAGS(1012),
    /**
     * The display quantity (displayQty) cannot exceed the order quantity.
     */
    WRONGDISPLAYQTYVALUE(1013),
    /**
     * The Display quantity (displayQty) not allowed for specified order type - only for Iceberg.
     */
    INVALIDDISPLAYQTY(1014),
    /**
     * The value of the iceberg order is less than the required.
     */
    ICEBERGORDERVALUELESSTHANREQUIRED(1015),
    /**
     * Attempt to submit an order with ExecInst = CancelOnConnectionLoss, while CoD being disabled for the ConnectionID used..
     */
    CODDISABLEDFORTHECONNECTIONID(1019),
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
     * Invalid OrdType for selected market model
     */
    INVALIDORDTYPEFORSELECTEDMARKETMODEL(1032),
    /**
     * The remaining quantity (LeavesQty) must be greater than 0 after the modification.
     */
    LEAVESQUANTITYMUSTBEGREATERTHANZEROAFTERMODIFICATION(1034),
    /**
     * The price is not allowed for a market order. In the binary protocol, the price field should have a value of 0.
     */
    PRICENOTALLOWED(1035),
    /**
     * Invalid TimeInForce for specified OrderType
     */
    INVALIDTIMEINFORCEFORORDERTYPE(1039),
    /**
     * Invalid TimeInForce for current market phase
     */
    INVALIDTIMEINFORCEFORCURRENTMARKETPHASE(1040),
    /**
     * Invalid TimeInForce for selected market model
     */
    INVALIDTIMEINFORCEFORSELECTEDMARKETMODEL(1041),
    /**
     * Time In Force vs. Order Type combination restriction per Market Segment imposed by Market Operator.
     */
    FORBIDDENORDTYPEANDTIMEINFORCECOMBINATIONFORMARKETSEGMENT(1042),
    /**
     * ExpireTime (126) cannot be modified.
     */
    EXPIRETIMECANNOTBEMODIFIED(1043),
    /**
     * ExpireDate (432) not allowed for selected TimeInForce (59).
     */
    OBSOLETEEXPIREDATE(1044),
    /**
     * The expiration date is earlier than the current date.
     */
    EXPIREDATEINPAST(1045),
    /**
     * ExpireTime (126) not allowed for selected TimeInForce (59).
     */
    OBSOLETEEXPIRETIME(1046),
    /**
     * The expiration time is earlier than the current time.
     */
    EXPIRETIMEINPAST(1047),
    /**
     * Please provide either a time or a date, but not both.
     */
    AMBIGOUSEXPIRE(1048),
    /**
     * The expiration date exceeds the allowed limit.
     */
    EXPIREDATEEXCEEDSLIMIT(1049),
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
     * Missing OrderOrigination for sponsored access connection.
     */
    MISSINGORDERORIGINATIONFORSPONSOREDACCESSCONNECTION(1055),
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
     * Entry/modification/cancellation of orders and quotes is not possible when an instrument is being uncrossed.
     */
    OPERATIONSONORDERSANDQUOTESFORBIDDENDURINGUNCROSSING(1060),
    /**
     * Entry/modification/cancellation of orders and quotes is not possible when an instrument is suspended.
     */
    OPERATIONSONORDERSANDQUOTESFORBIDDENDURINGINSTRUMENTSUSPENSION(1061),
    /**
     * Operations on orders and quotes forbidden due to Drop Copy disconnection.
     */
    OPERATIONSONORDERSANDQUOTESFORBIDDENDUETODROPCOPYDISCONNECTION(1062),
    /**
     * The trigger price not allowed for a specified order type.
     */
    TRIGGERPRICENOTALLOWED(1063),
    /**
     * The trigger price is not higher than the last trade price.
     */
    TRIGGERPRICENOTHIGHERTHANLTP(1064),
    /**
     * The trigger price is not lower than the last trade price.
     */
    TRIGGERPRICENOTLOWERTHANLTP(1065),
    /**
     * The trigger price is lower than the limit price.
     */
    TRIGGERPRICELOWERTHANPRICE(1066),
    /**
     * The trigger price is higher than the limit price.
     */
    TRIGGERPRICEHIGHERTHANPRICE(1067),
    /**
     * The trigger price has been modified for the activated order.
     */
    TRIGGERPRICEMODIFIEDFORACTIVATEDORDER(1068),
    /**
     * TriggerPrice (1102) must be greater than 0.
     */
    TRIGGERPRICEMUSTBEGREATERTHANZERO(1069),
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
     * Invalid Client ID for sponsored connection
     */
    INVALIDCLIENTIDFORSPONSOREDCONNECTION(1078),
    /**
     * Invalid OrdType for sponsored connection
     */
    INVALIDORDTYPEFORSPONSOREDCONNECTION(1079),
    /**
     * Forbidden OrderCapacity value for sponsored connection
     */
    FORBIDDENORDERCAPACITYVALUEFORSPONSOREDCONNECTION(1080),
    /**
     * Market Model not supported sponsored connection
     */
    MARKETMODELNOTSUPPORTEDONSPONSOREDCONNECTION(1081),
    /**
     * Invalid TimeInForce for sponsored connection
     */
    INVALIDTIMEINFORCEFORSPONSOREDCONNECTION(1083),
    /**
     * Mass Quote not allowed for selected Market Model.
     */
    MASSQUOTENOTALLOWEDFORSELECTEDMARKETMODEL(1201),
    /**
     * Instrument closed for trading.
     */
    INSTRUMENTCLOSED(1203),
    /**
     * OfferPx (133) must be greater than BidPx (132).
     */
    INVALIDBIDASKSPREAD(1208),
    /**
     * Firm is not a Market Maker for this SecurityID (48).
     */
    NOTAUTHORIZEDTOQUOTEINSTRUMENT(1209),
    /**
     * Buy orders from participants are not allowed in BuyOnly phase.
     */
    BUYORDERNOTALLOWED(1301),
    /**
     * Stop Orders not allowed when no opposite LP quote available
     */
    STOPORDERSNOTALLOWED(1307),
    /**
     * TriggerPrice (1102) must be higher than LP sell quote for buy Stop Order
     */
    TRIGGERPRICEMUSTBEHIGHERTHANLPSELLQUOTE(1308),
    /**
     * TriggerPrice (1102) must be lower than LP buy quote for sell Stop Order
     */
    TRIGGERPRICEMUSTBELOWERTHANLPBUYQUOTE(1309),
    /**
     * Market Making in Hybrid market model is only possible using Mass Quote message.
     */
    MARKETMAKINGVIAMASSQUOTEONLYONHYBRIDMARKET(1310),
    /**
     * During BuyOnly  state in Hybrid market model sell quotes are not accepted
     */
    SELLQUOTESNOTALLOWEDDURINGBUYONLYSTATE(1311),
    /**
     * As soon as the instrument's knock-out barrier is reached by the underlying, any orders/quotes are no longer accepted into the order book.
     */
    INSTRUMENTKNOCKEDOUT(1312),
    /**
     * Only one sell order is allowed for IPO instrument
     */
    ONLYONESELLORDERISALLOWEDFORIPO(1401),
    /**
     * Request not allowed for BLOCK instrument
     */
    REQUESTNOTALLOWEDFORBLOCKINSTRUMENT(2026),
    /**
     * Request not allowed for CROSS instrument
     */
    REQUESTNOTALLOWEDFORCROSSINSTRUMENT(2028),
    /**
     * The risk limit has not been defined.
     */
    RISKLIMITNOTDEFINED(7000),
    /**
     * The maximum order volume for the risk limit has been exceeded.
     */
    RISKMAXIMUMORDERVOLUMEEXCEEDED(7001),
    /**
     * The maximum order value for the risk limit has been exceeded.
     */
    RISKMAXIMUMORDERVALUEEXCEEDED(7002),
    /**
     * The order price has exceeded the risk limit.
     */
    RISKORDERPRICECOLLAREXCEEDED(7003),
    /**
     * Dynamic Order Price Collar not defined.
     */
    DYNAMICORDERPRICECOLLARNOTDEFINED(7004),
    /**
     * Static Order Price Collar not defined.
     */
    STATICORDERPRICECOLLARNOTDEFINED(7005),
    /**
     * Total traded value has exceeded the risk limit.
     */
    TOTALTRADEDVALUEEXCEEDED(7011),
    /**
     * Total traded buy value has exceeded the risk limit.
     */
    TOTALTRADEDBUYVALUEEXCEEDED(7012),
    /**
     * Total traded sell value has exceeded the risk limit.
     */
    TOTALTRADEDSELLVALUEEXCEEDED(7013),
    /**
     * Total open value has exceeded the risk limit.
     */
    TOTALOPENVALUEEXCEEDED(7014),
    /**
     * Total open buy value has exceeded the risk limit.
     */
    TOTALOPENBUYVALUEEXCEEDED(7015),
    /**
     * Total open sell value has exceeded the risk limit.
     */
    TOTALOPENSELLVALUEEXCEEDED(7016),
    /**
     * Total risk value has exceeded the risk limit.
     */
    TOTALRISKVALUEEXCEEDED(7017),
    /**
     * Total buy risk value has exceeded the risk limit.
     */
    TOTALBUYRISKVALUEEXCEEDED(7018),
    /**
     * Total sell risk value has exceeded the risk limit.
     */
    TOTALSELLRISKVALUEEXCEEDED(7019),
    /**
     * Total net risk value has exceeded the risk limit.
     */
    TOTALNETRISKVALUEEXCEEDED(7020),
    /**
     * Maximum order count has exceeded the risk limit.
     */
    MAXORDERCOUNTEXCEEDED(7021),
    /**
     * Rejected due to kill switch.
     */
    KILLSWITCH(7022);
    
    private final int value;
    private final int byteLength = 2;
    
    private static final Map<Integer, OrderRejectionReason> TYPES = new HashMap<>();
    static {
        for (OrderRejectionReason type : OrderRejectionReason.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    OrderRejectionReason(int newValue) {
        value = newValue;
    }
    
    /**
     * Get OrderRejectionReason by attribute
     * @param val
     * @return OrderRejectionReason enum or null if variant is undefined
     */
    public static OrderRejectionReason getOrderRejectionReason(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get OrderRejectionReason int value
     * @return int value
     */
    public int getOrderRejectionReasonValue() {
        return value; 
    }
    
    /**
     * Get OrderRejectionReason from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static OrderRejectionReason getOrderRejectionReason(byte[] bytes, int offset) {
        return getOrderRejectionReason(BendecUtils.uInt16FromByteArray(bytes, offset));
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