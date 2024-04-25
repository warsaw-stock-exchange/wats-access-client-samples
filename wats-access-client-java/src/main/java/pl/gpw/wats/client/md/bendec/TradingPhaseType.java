package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: TradingPhaseType
 * Type of matching algorithm.
 */
public enum TradingPhaseType {
    /**
     * Continuous price and time.
     */
    CONTINUOUSPRICETIME(1),
    /**
     * Continuous time at reference price.
     */
    CONTINUOUSREFPRICETIME(2),
    /**
     * Trade at last.
     */
    CONTINUOUSLASTAUCTIONTIME(3),
    /**
     * Auction opening.
     */
    AUCTIONOPENING(4),
    /**
     * Block transaction, exact matching.
     */
    BLOCKEXACTMATCHING(5),
    /**
     * Trading disabled, no matching.
     */
    NOTRADINGCLOSED(6),
    /**
     * Uncrossing.
     */
    UNCROSSING(7),
    /**
     * Auction closing.
     */
    AUCTIONCLOSING(8),
    /**
     * Auction intraday.
     */
    AUCTIONINTRADAY(9),
    /**
     * Auction volatility static.
     */
    AUCTIONVOLATILITYSTATIC(10),
    /**
     * Auction volatility dynamic.
     */
    AUCTIONVOLATILITYDYNAMIC(11),
    /**
     * Auction extended volatility static.
     */
    AUCTIONEXTENDEDVOLATILITYSTATIC(12),
    /**
     * Auction extended volatility dynamic.
     */
    AUCTIONEXTENDEDVOLATILITYDYNAMIC(13),
    /**
     * Early Monitoring
     */
    NOTRADINGEARLYMONITORING(14),
    /**
     * Late Monitoring
     */
    NOTRADINGLATEMONITORING(15),
    /**
     * Hybrid normal trading
     */
    HYBRID(16),
    /**
     * Hybrid buy only phase
     */
    HYBRIDBUYONLY(17),
    /**
     * Hybrid pre trade phase
     */
    HYBRIDPRETRADE(18),
    /**
     * Auction after suspension
     */
    UNSUSPENSIONAUCTION(19),
    /**
     * Initial public offering phase
     */
    IPO(20),
    /**
     * Tender offer phase
     */
    TENDEROFFER(21),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, TradingPhaseType> TYPES = new HashMap<>();
    static {
        for (TradingPhaseType type : TradingPhaseType.values()) {
            TYPES.put(type.value, type);
        }
    }


    TradingPhaseType(int newValue) {
        value = newValue;
    }

    /**
     Get TradingPhaseType from java input
     * @param newValue
     * @return TradingPhaseType enum
     */
    public static TradingPhaseType getTradingPhaseType(int newValue) {
        TradingPhaseType val = TYPES.get(newValue);
        return val == null ? TradingPhaseType.UNKNOWN : val;
    }

    /**
     * Get TradingPhaseType int value
     * @return int value
     */
    public int getTradingPhaseTypeValue() { return value; }


    /**
     Get TradingPhaseType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static TradingPhaseType getTradingPhaseType(byte[] bytes, int offset) {
        return getTradingPhaseType(BendecUtils.uInt8FromByteArray(bytes, offset));
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
