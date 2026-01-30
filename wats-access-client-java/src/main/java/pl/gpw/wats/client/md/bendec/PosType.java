package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: PosType
 * Identifies type of quantity returned.
 */
public enum PosType {
    /**
     * Start-of-Day Qty.
     */
    SOD(1),
    /**
     * Intraday Qty.
     */
    ITD(2),
    /**
     * End-of-Day Qty.
     */
    FIN(3);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, PosType> TYPES = new HashMap<>();
    static {
        for (PosType type : PosType.values()) {
            TYPES.put(type.value, type);
        }
    }

    PosType(int newValue) {
        value = newValue;
    }

    /**
     * Get PosType by attribute
     * @param val
     * @return PosType enum or null if variant is undefined
     */
    public static PosType getPosType(int val) {
        return TYPES.get(val);
    }

    /**
     * Get PosType int value
     * @return int value
     */
    public int getPosTypeValue() {
        return value;
    }
    
    /**
     * Get PosType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static PosType getPosType(byte[] bytes, int offset) {
        return getPosType(BendecUtils.uInt8FromByteArray(bytes, offset));
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