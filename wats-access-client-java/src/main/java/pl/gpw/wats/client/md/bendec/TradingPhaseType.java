package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
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
     * Opening auction.
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
     * Closing auction.
     */
    AUCTIONCLOSING(8),
    /**
     * Intraday auction.
     */
    AUCTIONINTRADAY(9),
    /**
     * Volatility auction after static collars breach.
     */
    AUCTIONVOLATILITYSTATIC(10),
    /**
     * Volatility auction after dynamic collars breach.
     */
    AUCTIONVOLATILITYDYNAMIC(11),
    /**
     * Additional volatility auction after static collar breach. It can be activated independently or as an extension of the previous AuctionVolatilityStatic phase.
     */
    AUCTIONEXTENDEDVOLATILITYSTATIC(12),
    /**
     * Additional volatility auction after dynamic collar breach. It can be activated independently or as an extension of the previous AuctionVolatilityDynamic phase.
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
     * A phase triggered whenever an instrument’s changes its status from RegulatorySuspension or MarketOperationSuspension before entering into continuous phase.
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
    /**
     * Hybrid pre trade BuyOnly phase
     */
    HYBRIDPRETRADEBUYONLY(22),
    /**
     * Instrument distribution phase
     */
    DISTRIBUTION(23);

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
     * Get TradingPhaseType by attribute
     * @param val
     * @return TradingPhaseType enum or null if variant is undefined
     */
    public static TradingPhaseType getTradingPhaseType(int val) {
        return TYPES.get(val);
    }

    /**
     * Get TradingPhaseType int value
     * @return int value
     */
    public int getTradingPhaseTypeValue() {
        return value;
    }
    
    /**
     * Get TradingPhaseType from bytes
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