package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: IndexPublicationSchedule
 * Mode of index dissemination.
 */
public enum IndexPublicationSchedule {
    /**
     * Index is quoted in determined time intervals.
     */
    CONTINUOUS(1),
    /**
     * Index is quoted on determined hours.
     */
    FIXING(2),
    /**
     * Index is quoted on closing.
     */
    CLOSING(3);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, IndexPublicationSchedule> TYPES = new HashMap<>();
    static {
        for (IndexPublicationSchedule type : IndexPublicationSchedule.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    IndexPublicationSchedule(int newValue) {
        value = newValue;
    }
    
    /**
     * Get IndexPublicationSchedule by attribute
     * @param val
     * @return IndexPublicationSchedule enum or null if variant is undefined
     */
    public static IndexPublicationSchedule getIndexPublicationSchedule(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get IndexPublicationSchedule int value
     * @return int value
     */
    public int getIndexPublicationScheduleValue() {
        return value; 
    }
    
    /**
     * Get IndexPublicationSchedule from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static IndexPublicationSchedule getIndexPublicationSchedule(byte[] bytes, int offset) {
        return getIndexPublicationSchedule(BendecUtils.uInt8FromByteArray(bytes, offset));
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