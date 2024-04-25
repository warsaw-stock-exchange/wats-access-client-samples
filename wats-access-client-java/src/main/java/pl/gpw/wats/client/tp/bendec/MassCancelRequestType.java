package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: MassCancelRequestType
 * Mass cancel request type.
 */
public enum MassCancelRequestType {
    /**
     * Cancel orders for a security.
     */
    CANCELFORSECURITY(1),
    /**
     * Cancel all orders.
     */
    CANCELALLORDERS(7),
    /**
     * Cancel orders for a market segment.
     */
    CANCELORDERSFORMARKETSEGMENT(9),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, MassCancelRequestType> TYPES = new HashMap<>();
    static {
        for (MassCancelRequestType type : MassCancelRequestType.values()) {
            TYPES.put(type.value, type);
        }
    }


    MassCancelRequestType(int newValue) {
        value = newValue;
    }

    /**
     Get MassCancelRequestType from java input
     * @param newValue
     * @return MassCancelRequestType enum
     */
    public static MassCancelRequestType getMassCancelRequestType(int newValue) {
        MassCancelRequestType val = TYPES.get(newValue);
        return val == null ? MassCancelRequestType.UNKNOWN : val;
    }

    /**
     * Get MassCancelRequestType int value
     * @return int value
     */
    public int getMassCancelRequestTypeValue() { return value; }


    /**
     Get MassCancelRequestType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static MassCancelRequestType getMassCancelRequestType(byte[] bytes, int offset) {
        return getMassCancelRequestType(BendecUtils.uInt8FromByteArray(bytes, offset));
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
