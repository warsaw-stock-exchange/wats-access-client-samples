package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: InstrumentStatus
 * Financial instrument status.
 */
public enum InstrumentStatus {
    /**
     * Financial instrument is active.
     */
    ACTIVE(1),
    /**
     * Financial instrument is inactive and is not trading.
     */
    INACTIVE(2),
    /**
     * Manual suspension by Market Operations.
     */
    MARKETOPERATIONSSUSPENSION(3),
    /**
     * Outside static trade price collars.
     */
    OUTSIDECOLLARSSTATIC(4),
    /**
     * Outside dynamic trade price collars.
     */
    OUTSIDECOLLARSDYNAMIC(5),
    /**
     * Regulatory suspension
     */
    REGULATORYSUSPENSION(6),
    /**
     * TechnicalHalt.
     */
    TECHNICALHALT(7),
    /**
     * Hybrid no valid quotes.
     */
    HYBRIDNOQUOTES(8),
    /**
     * Hybrid pause.
     */
    HYBRIDPAUSE(9),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, InstrumentStatus> TYPES = new HashMap<>();
    static {
        for (InstrumentStatus type : InstrumentStatus.values()) {
            TYPES.put(type.value, type);
        }
    }


    InstrumentStatus(int newValue) {
        value = newValue;
    }

    /**
     Get InstrumentStatus from java input
     * @param newValue
     * @return InstrumentStatus enum
     */
    public static InstrumentStatus getInstrumentStatus(int newValue) {
        InstrumentStatus val = TYPES.get(newValue);
        return val == null ? InstrumentStatus.UNKNOWN : val;
    }

    /**
     * Get InstrumentStatus int value
     * @return int value
     */
    public int getInstrumentStatusValue() { return value; }


    /**
     Get InstrumentStatus from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static InstrumentStatus getInstrumentStatus(byte[] bytes, int offset) {
        return getInstrumentStatus(BendecUtils.uInt8FromByteArray(bytes, offset));
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
