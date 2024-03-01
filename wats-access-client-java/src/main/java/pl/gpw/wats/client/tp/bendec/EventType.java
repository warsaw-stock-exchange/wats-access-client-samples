package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: EventType
 * Test event type
 */
public enum EventType {
    /**
     * Flush the Test scenario
     */
    FLUSH(1),
    /**
     * Start of test scenario
     */
    SCENARIOSTART(2),
    /**
     * End of test scenario
     */
    SCENARIOEND(3),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, EventType> TYPES = new HashMap<>();
    static {
        for (EventType type : EventType.values()) {
            TYPES.put(type.value, type);
        }
    }


    EventType(int newValue) {
        value = newValue;
    }

    /**
     Get EventType from java input
     * @param newValue
     * @return EventType enum
     */
    public static EventType getEventType(int newValue) {
        EventType val = TYPES.get(newValue);
        return val == null ? EventType.UNKNOWN : val;
    }

    /**
     * Get EventType int value
     * @return int value
     */
    public int getEventTypeValue() { return value; }


    /**
     Get EventType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static EventType getEventType(byte[] bytes, int offset) {
        return getEventType(BendecUtils.uInt8FromByteArray(bytes, offset));
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
