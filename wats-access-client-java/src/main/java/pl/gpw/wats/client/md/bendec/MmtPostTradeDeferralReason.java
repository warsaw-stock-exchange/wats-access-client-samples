package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: MmtPostTradeDeferralReason
 * MMT Post-Trade Deferral Reason
 */
public enum MmtPostTradeDeferralReason {
    /**
     * - = Immediate Publication
     */
    IMMEDIATEPUBLICATION(1),
    /**
     * 1 = Non-Immediate Publication
     */
    NONIMMEDIATEPUBLICATION(2),
    /**
     * 2 = Non-Immediate Publication : Deferral for 'Large in Scale'
     */
    NONIMMEDIATEPUBLICATIONLARGEINSCALE(3),
    /**
     * 3 = Non-Immediate Publication : Deferral for 'Illiquid Instrument' (for RTS 2 only)
     */
    NONIMMEDIATEPUBLICATIONILLIQUIDINSTRUMENT(4),
    /**
     * 4 = Non-Immediate Publication : Deferral for 'Size Specific' (for RTS 2 only)
     */
    NONIMMEDIATEPUBLICATIONSIZESPECIFIC(5),
    /**
     * 5 = Non-Immediate Publication : Deferrals of ILQD and SIZE (for RTS 2 use only)
     */
    NONIMMEDIATEPUBLICATIONILQDANDSIZE(6),
    /**
     * 6 = Non-Immediate Publication : Deferrals of ILQD and LRGS (for RTS 2 use only)
     */
    NONIMMEDIATEPUBLICATIONILQDANDLRGS(7);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, MmtPostTradeDeferralReason> TYPES = new HashMap<>();
    static {
        for (MmtPostTradeDeferralReason type : MmtPostTradeDeferralReason.values()) {
            TYPES.put(type.value, type);
        }
    }

    MmtPostTradeDeferralReason(int newValue) {
        value = newValue;
    }

    /**
     * Get MmtPostTradeDeferralReason by attribute
     * @param val
     * @return MmtPostTradeDeferralReason enum or null if variant is undefined
     */
    public static MmtPostTradeDeferralReason getMmtPostTradeDeferralReason(int val) {
        return TYPES.get(val);
    }

    /**
     * Get MmtPostTradeDeferralReason int value
     * @return int value
     */
    public int getMmtPostTradeDeferralReasonValue() {
        return value;
    }
    
    /**
     * Get MmtPostTradeDeferralReason from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MmtPostTradeDeferralReason getMmtPostTradeDeferralReason(byte[] bytes, int offset) {
        return getMmtPostTradeDeferralReason(BendecUtils.uInt8FromByteArray(bytes, offset));
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