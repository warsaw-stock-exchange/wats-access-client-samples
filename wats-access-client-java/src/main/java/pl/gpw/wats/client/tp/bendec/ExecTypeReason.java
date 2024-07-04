package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: ExecTypeReason
 * Describes why an order was executed and the events related to its lifecycle.
 */
public enum ExecTypeReason {
    /**
     * Not applicable.
     */
    NA(1),
    /**
     * Order cancelled by the canceller on client disconnect.
     */
    CANCELONDISCONNECT(2),
    /**
     * Order cancelled by the canceller due to expiration.
     */
    EXPIRED(3),
    /**
     * Notification of Stop or VFA/VFC order activation.
     */
    TRIGGERED(4),
    /**
     * Order cancelled by canceller due to instrument suspension.
     */
    CANCELONSUSPENSION(5),
    /**
     * Order reinstated.
     */
    ORDERRESTATEMENT(6),
    /**
     * Iceberg order refill.
     */
    ICEBERGORDERREFILL(7),
    /**
     * Order cancelled due to self-trade prevention.
     */
    CANCELBYSTP(8),
    /**
     * Order cancelled due to submitted Corporate Action.
     */
    CANCELBYCORPORATEACTION(9),
    /**
     * Order cancelled due to mass cancel
     */
    CANCELBYMASSCANCEL(10),
    /**
     * Order cancelled due to IOC/FOK.
     */
    CANCELIOCFOKORDER(11),
    /**
     * Order cancelled due to market operations.
     */
    CANCELBYMARKETOPERATIONS(12),
    /**
     * Order replaced.
     */
    REPLACED(13),
    /**
     * First trade on aggressive order.
     */
    FIRSTTRADEONAGGRESSIVEORDER(14),
    /**
     * Order rejected.
     */
    REJECTED(15);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, ExecTypeReason> TYPES = new HashMap<>();
    static {
        for (ExecTypeReason type : ExecTypeReason.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    ExecTypeReason(int newValue) {
        value = newValue;
    }
    
    /**
     * Get ExecTypeReason by attribute
     * @param val
     * @return ExecTypeReason enum or null if variant is undefined
     */
    public static ExecTypeReason getExecTypeReason(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get ExecTypeReason int value
     * @return int value
     */
    public int getExecTypeReasonValue() {
        return value; 
    }
    
    /**
     * Get ExecTypeReason from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static ExecTypeReason getExecTypeReason(byte[] bytes, int offset) {
        return getExecTypeReason(BendecUtils.uInt8FromByteArray(bytes, offset));
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