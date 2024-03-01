package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
    OPEN(2),
    UNKNOWN(99999);

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
     Get CalendarExceptionType from java input
     * @param newValue
     * @return CalendarExceptionType enum
     */
    public static CalendarExceptionType getCalendarExceptionType(int newValue) {
        CalendarExceptionType val = TYPES.get(newValue);
        return val == null ? CalendarExceptionType.UNKNOWN : val;
    }

    /**
     * Get CalendarExceptionType int value
     * @return int value
     */
    public int getCalendarExceptionTypeValue() { return value; }


    /**
     Get CalendarExceptionType from bytes
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
