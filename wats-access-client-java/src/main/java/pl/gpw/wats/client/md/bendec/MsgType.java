package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: MsgType
 * undefined
 */
public enum MsgType {
    /**
     * A message type used to check connectivity.
     */
    HEARTBEAT(1),
    /**
     * New (limit) order added to order book.
     */
    ORDERADD(9),
    /**
     * Order modified.
     */
    ORDERMODIFY(10),
    /**
     * Order deleted.
     */
    ORDERCANCEL(11),
    /**
     * Execution report.
     */
    ORDEREXECUTE(12),
    /**
     * The Trading Session Status provides information on the status of a market and on a trading day events.
     */
    TRADINGSESSIONSTATUS(23),
    /**
     * Encryption key and ID.
     */
    ENCRYPTIONKEY(24),
    /**
     * Start of a new trading phase.
     */
    INSTRUMENTSTATUSCHANGE(26),
    /**
     * Trading phase definition.
     */
    TRADINGPHASESCHEDULEENTRY(27),
    /**
     * Tick size definition.
     */
    TICKTABLEENTRY(32),
    /**
     * Trading week plan.
     */
    WEEKPLAN(33),
    /**
     * Upcoming calendar day exception.
     */
    CALENDAREXCEPTION(35),
    /**
     * Message describing an accrued interest table entry.
     */
    ACCRUEDINTERESTTABLEENTRY(36),
    /**
     * Message describing an indexation table entry.
     */
    INDEXATIONTABLEENTRY(37),
    /**
     * Enriched trade report.
     */
    TRADE(38),
    /**
     * Collar table definition.
     */
    COLLARTABLEENTRY(108),
    /**
     * BBO Level 1.
     */
    TOPPRICELEVELUPDATE(256),
    /**
     * BBO price levels and volumes.
     */
    PRICELEVELSNAPSHOT(257),
    /**
     * Updates IMP/IMV and BBO during an auction.
     */
    AUCTIONUPDATE(268),
    /**
     * Auction summary, including auction price and quantity.
     */
    AUCTIONSUMMARY(270),
    /**
     * Message notifying about reference price changes.
     */
    PRICEUPDATE(271),
    /**
     * New order price collars.
     */
    ORDERCOLLARS(272),
    /**
     * New trade price collars.
     */
    TRADECOLLARS(273),
    /**
     * Messages defining the Trading Venue’s market structure.
     */
    MARKETSTRUCTURE(275),
    /**
     * Definition of a financial instrument.
     */
    INSTRUMENT(279),
    /**
     * Message defining a collar group.
     */
    COLLARGROUP(299),
    /**
     * Signal to clear the order book for upcoming auction.
     */
    ORDERBOOKEVENT(410),
    /**
     * The message handles the real-time characteristics of an index.
     */
    REALTIMEINDEX(500),
    /**
     * The message provides a summary of the data calculated in a stock index for a given day.
     */
    INDEXSUMMARY(501),
    /**
     * The message contains information about a product providing the index portfolio. The entire index portfolio is sent as successive IndexPortfolioEntry messages.
     */
    INDEXPORTFOLIOENTRY(502),
    /**
     * The metadata message for the given index.
     */
    INDEXPARAMS(503),
    /**
     * News message.
     */
    NEWS(517),
    /**
     * Login message to the Snapshot service.
     */
    LOGIN(667),
    /**
     * Response to a Login message.
     */
    LOGINRESPONSE(668),
    /**
     * A Logout message is sent by the client to initiate disconnection from the Replay Server side.
     */
    LOGOUT(669),
    /**
     * Marks the end of a snapshot.
     */
    ENDOFSNAPSHOT(670),
    /**
     * Instrument summary.
     */
    INSTRUMENTSUMMARY(671),
    /**
     * Product summary.
     */
    PRODUCTSUMMARY(672),
    /**
     * Report of open positions.
     */
    POSITIONREPORT(801),
    /**
     * The message contains a list of underlyings not traded in WATS. It concerns underlyings for derivatives and structured products.
     */
    EXTERNALUNDERLYING(806),
    /**
     * A message to relay test scenario information
     */
    TESTEVENT(8192);
    
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