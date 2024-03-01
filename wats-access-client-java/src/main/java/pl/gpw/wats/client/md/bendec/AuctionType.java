package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: AuctionType
 * Type of auction.
 */
public enum AuctionType {
    /**
     * NotApplicable.
     */
    NOTAPPLICABLE(1),
    /**
     * Opening auction.
     */
    OPENINGAUCTION(2),
    /**
     * Closing auction.
     */
    CLOSINGAUCTION(3),
    /**
     * Intraday auction.
     */
    INTRADAYAUCTION(4),
    /**
     * Volatility auction after static collars breach.
     */
    VOLATILITYAUCTIONSTATIC(5),
    /**
     * Volatility auction after dynamic collars breach.
     */
    VOLATILITYAUCTIONDYNAMIC(6),
    EXTENDEDVOLATILITYAUCTIONSTATIC(7),
    EXTENDEDVOLATILITYAUCTIONDYNAMIC(8),
    UNSUSPENSIONAUCTION(9),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, AuctionType> TYPES = new HashMap<>();
    static {
        for (AuctionType type : AuctionType.values()) {
            TYPES.put(type.value, type);
        }
    }


    AuctionType(int newValue) {
        value = newValue;
    }

    /**
     Get AuctionType from java input
     * @param newValue
     * @return AuctionType enum
     */
    public static AuctionType getAuctionType(int newValue) {
        AuctionType val = TYPES.get(newValue);
        return val == null ? AuctionType.UNKNOWN : val;
    }

    /**
     * Get AuctionType int value
     * @return int value
     */
    public int getAuctionTypeValue() { return value; }


    /**
     Get AuctionType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static AuctionType getAuctionType(byte[] bytes, int offset) {
        return getAuctionType(BendecUtils.uInt8FromByteArray(bytes, offset));
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
