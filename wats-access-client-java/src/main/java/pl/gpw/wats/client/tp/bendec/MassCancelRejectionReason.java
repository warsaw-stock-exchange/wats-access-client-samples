package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: MassCancelRejectionReason
 * Indicates a mass cancel rejection reason.
 */
public enum MassCancelRejectionReason {
    /**
     * Other.
     */
    OTHER(99),
    /**
     * Not applicable.
     */
    NA(101),
    /**
     * Unknown instrument.
     */
    UNKNOWNINSTRUMENT(1001),
    /**
     * Missing Executing Trader repeating group
     */
    INVALIDEXECUTIONTRADER(1005),
    /**
     * Unknown market segment ID.
     */
    UNKNOWNMARKETSEGMENTID(1016),
    /**
     * Unknown connection ID.
     */
    UNKNOWNCONNECTIONID(1017),
    /**
     * Instrument forbidden.
     */
    INSTRUMENTFORBIDDEN(1053),
    /**
     * Market segment ID forbidden.
     */
    MARKETSEGMENTIDFORBIDDEN(1054),
    /**
     * Operation on redistributed instruments forbidden.
     */
    OPERATIONONREDISTRIBUTEDINSTRUMENTSFORBIDDEN(1056),
    /**
     * Request not allowed on sponsored connection.
     */
    REQUESTNOTALLOWEDONSPONSOREDCONNECTION(2025);
    
    private final int value;
    private final int byteLength = 2;
    
    private static final Map<Integer, MassCancelRejectionReason> TYPES = new HashMap<>();
    static {
        for (MassCancelRejectionReason type : MassCancelRejectionReason.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    MassCancelRejectionReason(int newValue) {
        value = newValue;
    }
    
    /**
     * Get MassCancelRejectionReason by attribute
     * @param val
     * @return MassCancelRejectionReason enum or null if variant is undefined
     */
    public static MassCancelRejectionReason getMassCancelRejectionReason(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get MassCancelRejectionReason int value
     * @return int value
     */
    public int getMassCancelRejectionReasonValue() {
        return value; 
    }
    
    /**
     * Get MassCancelRejectionReason from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MassCancelRejectionReason getMassCancelRejectionReason(byte[] bytes, int offset) {
        return getMassCancelRejectionReason(BendecUtils.uInt16FromByteArray(bytes, offset));
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