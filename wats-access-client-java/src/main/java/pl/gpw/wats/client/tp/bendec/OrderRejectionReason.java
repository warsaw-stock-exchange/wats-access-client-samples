package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: OrderRejectionReason
 * A code used to identify the reason for order rejection.
 */
public enum OrderRejectionReason {
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
     * The order id is unrecognized.
     */
    UNKNOWNORDER(1001),
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
    WRONGICEBERGORDERVALUE(1015),
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
     * Market orders are not permitted during the auction.
     */
    MARKETORDERNOTALLOWEDINAUCTION(1032),
    /**
     * The remaining quantity (LeavesQty) must be greater than 0 after the modification.
     */
    LEAVESQUANTITYMUSTBEGREATERTHANZEROAFTERMODIFICATION(1034),
    /**
     * The price is not allowed for a market order. In the binary protocol, the price field should have a value of 0.
     */
    PRICENOTALLOWED(1035),
    /**
     * A market order can only have a TimeInForce value of 3 (IOC - Immediate or Cancel) or 4 (FOK - Fill or Kill).
     */
    INVALIDMARKETORDERTIMEINFORCE(1039),
    /**
     * The TimeInForce value of 3 (IOC - Immediate or Cancel) or 4 (FOK - Fill or Kill) is not allowed during the auction.
     */
    INVALIDAUCTIONTIMEINFORCE(1040),
    /**
     * Iceberg and Stop orders can only have a TimeInForce value of DAY, GTC (Good Till Cancel), GTD (Good Till Date), or GTT (Good Till Time).
     */
    INVALIDICEBERGANDSTOPORDERTIMEINFORCE(1041),
    /**
     * The expiration date is earlier than the current date.
     */
    EXPIREDATEINPAST(1045),
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
     * OfferPx (133) must be greater than BidPx (132).
     */
    INVALIDBIDASKSPREAD(1208),
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
    UNKNOWN(99999);

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
     Get OrderRejectionReason from java input
     * @param newValue
     * @return OrderRejectionReason enum
     */
    public static OrderRejectionReason getOrderRejectionReason(int newValue) {
        OrderRejectionReason val = TYPES.get(newValue);
        return val == null ? OrderRejectionReason.UNKNOWN : val;
    }

    /**
     * Get OrderRejectionReason int value
     * @return int value
     */
    public int getOrderRejectionReasonValue() { return value; }


    /**
     Get OrderRejectionReason from bytes
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
