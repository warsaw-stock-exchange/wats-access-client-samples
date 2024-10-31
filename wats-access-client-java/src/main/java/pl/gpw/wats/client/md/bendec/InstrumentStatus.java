package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
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
     * Hybrid no valid quotes.
     */
    HYBRIDNOQUOTES(8),
    /**
     * Hybrid knockout.
     */
    HYBRIDKNOCKOUT(9),
    /**
     * Hybrid knockout by issuer.
     */
    HYBRIDKNOCKOUTBYISSUER(10);
    
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
     * Get InstrumentStatus by attribute
     * @param val
     * @return InstrumentStatus enum or null if variant is undefined
     */
    public static InstrumentStatus getInstrumentStatus(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get InstrumentStatus int value
     * @return int value
     */
    public int getInstrumentStatusValue() {
        return value; 
    }
    
    /**
     * Get InstrumentStatus from bytes
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