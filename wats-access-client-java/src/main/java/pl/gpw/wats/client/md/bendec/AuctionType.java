package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
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
    AUCTIONOPENING(2),
    /**
     * Closing auction.
     */
    AUCTIONCLOSING(3),
    /**
     * Intraday auction.
     */
    AUCTIONINTRADAY(4),
    /**
     * Volatility auction after static collars breach.
     */
    AUCTIONVOLATILITYSTATIC(5),
    /**
     * Volatility auction after dynamic collars breach.
     */
    AUCTIONVOLATILITYDYNAMIC(6),
    AUCTIONEXTENDEDVOLATILITYSTATIC(7),
    AUCTIONEXTENDEDVOLATILITYDYNAMIC(8),
    UNSUSPENSIONAUCTION(9);
    
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
     * Get AuctionType by attribute
     * @param val
     * @return AuctionType enum or null if variant is undefined
     */
    public static AuctionType getAuctionType(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get AuctionType int value
     * @return int value
     */
    public int getAuctionTypeValue() {
        return value; 
    }
    
    /**
     * Get AuctionType from bytes
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