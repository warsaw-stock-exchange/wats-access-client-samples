package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: MmtMarketMechanism
 * MMT Market Mechanism
 */
public enum MmtMarketMechanism {
    /**
     * 1 = Central Limit Order Book
     */
    CENTRALLIMITORDERBOOK(1),
    /**
     * 2 = Quote Driven Market
     */
    QUOTEDRIVENMARKET(2),
    /**
     * 3 = Dark Order Book
     */
    DARKORDERBOOK(3),
    /**
     * 4 = Off Book
     */
    OFFBOOK(4),
    /**
     * 5 = Periodic Auction
     */
    PERIODICAUCTION(5),
    /**
     * 6 = Request For Quotes
     */
    REQUESTFORQUOTES(6),
    /**
     * 7 = Any other including Hybrid
     */
    OTHER(7),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, MmtMarketMechanism> TYPES = new HashMap<>();
    static {
        for (MmtMarketMechanism type : MmtMarketMechanism.values()) {
            TYPES.put(type.value, type);
        }
    }


    MmtMarketMechanism(int newValue) {
        value = newValue;
    }

    /**
     Get MmtMarketMechanism from java input
     * @param newValue
     * @return MmtMarketMechanism enum
     */
    public static MmtMarketMechanism getMmtMarketMechanism(int newValue) {
        MmtMarketMechanism val = TYPES.get(newValue);
        return val == null ? MmtMarketMechanism.UNKNOWN : val;
    }

    /**
     * Get MmtMarketMechanism int value
     * @return int value
     */
    public int getMmtMarketMechanismValue() { return value; }


    /**
     Get MmtMarketMechanism from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MmtMarketMechanism getMmtMarketMechanism(byte[] bytes, int offset) {
        return getMmtMarketMechanism(BendecUtils.uInt8FromByteArray(bytes, offset));
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
