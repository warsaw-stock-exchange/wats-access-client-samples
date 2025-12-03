package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: MsgType
 * undefined
 */
public enum MsgType {
    /**
     * The internal message for a test purpose.
     */
    TEST(1),
    /**
     * The login message authenticates a user establishing a connection to the trading port service. The login message must be the first message sent by the client application to request the initiation of a trading port session.
     */
    LOGIN(2),
    /**
     * The login response message. The result field describes the login status, indicating whether the login was successful or not (i.e. successful login).
     */
    LOGINRESPONSE(3),
    /**
     * Message used to add new orders to the system.
     */
    ORDERADD(4),
    /**
     * The message is a response to an OrderAdd message and includes the order execution status.
     */
    ORDERADDRESPONSE(5),
    /**
     * Message used to cancel the previously submitted orders.
     */
    ORDERCANCEL(6),
    /**
     * The message is a response to an order cancel request and contains information about its execution, in particular whether the order to cancel was found or not.
     */
    ORDERCANCELRESPONSE(7),
    /**
     * Message used to modify the submitted order.
     */
    ORDERMODIFY(8),
    /**
     * The response message for an OrderModify.
     */
    ORDERMODIFYRESPONSE(9),
    /**
     * The message used to report trades between counterparties (i.e. generated when two or more orders are matched).
     */
    ORDEREXECUTE(10),
    /**
     * The logout message is sent from the client application to terminate the communication session with the trading port.
     */
    LOGOUT(11),
    /**
     * The ConnectionClose message confirms the termination of a session through the trading port service.
     */
    CONNECTIONCLOSE(12),
    /**
     * Heartbeat message.
     */
    HEARTBEAT(13),
    /**
     * The logout response message confirms the client logout message.
     */
    LOGOUTRESPONSE(14),
    /**
     * The reject message is sent by the trading port service when receiving an erroneous message that cannot be further processed.
     */
    REJECT(15),
    /**
     * Trade Capture Report - single side.
     */
    TRADECAPTUREREPORTSINGLE(18),
    /**
     * Trade Capture Report - dual sided.
     */
    TRADECAPTUREREPORTDUAL(19),
    /**
     * The message is a response to an Trade Capture Report message, containing the state of TCR execution.
     */
    TRADECAPTUREREPORTRESPONSE(20),
    /**
     * TradeBust.
     */
    TRADEBUST(23),
    /**
     * MassQuote.
     */
    MASSQUOTE(24),
    /**
     * MassQuoteResponse.
     */
    MASSQUOTERESPONSE(25),
    /**
     * Informs MM that execution has been requested.
     */
    REQUESTFOREXECUTION(28),
    /**
     * Message used to cancel multiple existing orders.
     */
    ORDERMASSCANCEL(29),
    /**
     * Response to message used to cancel multiple existing orders.
     */
    ORDERMASSCANCELRESPONSE(30),
    /**
     * Message send during the IPO to the sell side or during the Tender Offer to the buy side.
     */
    BIDOFFERUPDATE(31),
    /**
     * Market Maker command request.
     */
    MARKETMAKERCOMMAND(32),
    /**
     * The response to the Market Maker command.
     */
    MARKETMAKERCOMMANDRESPONSE(33),
    /**
     * Create a SeqNum gap bettwen current SeqNum and header::seqNum
     */
    GAPFILL(34),
    /**
     * The Trading Session Status provides information on the status of a trading session.
     */
    TRADINGSESSIONSTATUS(35),
    /**
     * A message to relay test scenario information
     */
    TESTEVENT(255);
    
    private final int value;
    private final int byteLength = 2;
    
    private static final Map<Integer, MsgType> TYPES = new HashMap<>();
    static {
        for (MsgType type : MsgType.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    MsgType(int newValue) {
        value = newValue;
    }
    
    /**
     * Get MsgType by attribute
     * @param val
     * @return MsgType enum or null if variant is undefined
     */
    public static MsgType getMsgType(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get MsgType int value
     * @return int value
     */
    public int getMsgTypeValue() {
        return value; 
    }
    
    /**
     * Get MsgType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MsgType getMsgType(byte[] bytes, int offset) {
        return getMsgType(BendecUtils.uInt16FromByteArray(bytes, offset));
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