package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: MmtPostTradeDeferralType
 * MMT Post-Trade Deferral Type
 */
public enum MmtPostTradeDeferralType {
    /**
     * 1 = Limited Details Trade
     */
    LIMITEDDETAILSTRADE(1),
    /**
     * 2 = Daily Aggregated Trade
     */
    DAILYAGGREGATEDTRADE(2),
    /**
     * 3 = Volume Omission Trade
     */
    VOLUMEOMISSIONTRADE(3),
    /**
     * 4 = Four Weeks Aggregation Trade
     */
    FOURWEEKSAGGREGATIONTRADE(4),
    /**
     * 5 = Indefinite Aggregation Trade
     */
    INDEFINITEAGGREGATIONTRADE(5),
    /**
     * 6 = Volume Omission Trade, eligible for subsequent enrichment in aggregated form
     */
    VOLUMEOMISSIONTRADEAGGREGATED(6),
    /**
     * - = Not applicable / No relevant deferral or enrichment type
     */
    NOTAPPLICABLE(7),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, MmtPostTradeDeferralType> TYPES = new HashMap<>();
    static {
        for (MmtPostTradeDeferralType type : MmtPostTradeDeferralType.values()) {
            TYPES.put(type.value, type);
        }
    }


    MmtPostTradeDeferralType(int newValue) {
        value = newValue;
    }

    /**
     Get MmtPostTradeDeferralType from java input
     * @param newValue
     * @return MmtPostTradeDeferralType enum
     */
    public static MmtPostTradeDeferralType getMmtPostTradeDeferralType(int newValue) {
        MmtPostTradeDeferralType val = TYPES.get(newValue);
        return val == null ? MmtPostTradeDeferralType.UNKNOWN : val;
    }

    /**
     * Get MmtPostTradeDeferralType int value
     * @return int value
     */
    public int getMmtPostTradeDeferralTypeValue() { return value; }


    /**
     Get MmtPostTradeDeferralType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MmtPostTradeDeferralType getMmtPostTradeDeferralType(byte[] bytes, int offset) {
        return getMmtPostTradeDeferralType(BendecUtils.uInt8FromByteArray(bytes, offset));
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
