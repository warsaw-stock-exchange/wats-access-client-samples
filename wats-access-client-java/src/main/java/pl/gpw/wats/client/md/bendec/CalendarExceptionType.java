package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: CalendarExceptionType
 * Calendar exception type.
 */
public enum CalendarExceptionType {
    /**
     * The day is closed for trading.
     */
    CLOSED(1),
    /**
     * The day is open for trading.
     */
    OPEN(2);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, CalendarExceptionType> TYPES = new HashMap<>();
    static {
        for (CalendarExceptionType type : CalendarExceptionType.values()) {
            TYPES.put(type.value, type);
        }
    }

    CalendarExceptionType(int newValue) {
        value = newValue;
    }

    /**
     * Get CalendarExceptionType by attribute
     * @param val
     * @return CalendarExceptionType enum or null if variant is undefined
     */
    public static CalendarExceptionType getCalendarExceptionType(int val) {
        return TYPES.get(val);
    }

    /**
     * Get CalendarExceptionType int value
     * @return int value
     */
    public int getCalendarExceptionTypeValue() {
        return value;
    }
    
    /**
     * Get CalendarExceptionType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static CalendarExceptionType getCalendarExceptionType(byte[] bytes, int offset) {
        return getCalendarExceptionType(BendecUtils.uInt8FromByteArray(bytes, offset));
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