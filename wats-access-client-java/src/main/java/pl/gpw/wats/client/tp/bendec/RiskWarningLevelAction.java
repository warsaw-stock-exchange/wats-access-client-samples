package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: RiskWarningLevelAction
 * Action that was take because warning level was exceeded.
 */
public enum RiskWarningLevelAction {
    /**
     * Reject.
     */
    REJECT(3),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 1;


    private static final Map<Integer, RiskWarningLevelAction> TYPES = new HashMap<>();
    static {
        for (RiskWarningLevelAction type : RiskWarningLevelAction.values()) {
            TYPES.put(type.value, type);
        }
    }


    RiskWarningLevelAction(int newValue) {
        value = newValue;
    }

    /**
     Get RiskWarningLevelAction from java input
     * @param newValue
     * @return RiskWarningLevelAction enum
     */
    public static RiskWarningLevelAction getRiskWarningLevelAction(int newValue) {
        RiskWarningLevelAction val = TYPES.get(newValue);
        return val == null ? RiskWarningLevelAction.UNKNOWN : val;
    }

    /**
     * Get RiskWarningLevelAction int value
     * @return int value
     */
    public int getRiskWarningLevelActionValue() { return value; }


    /**
     Get RiskWarningLevelAction from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static RiskWarningLevelAction getRiskWarningLevelAction(byte[] bytes, int offset) {
        return getRiskWarningLevelAction(BendecUtils.uInt8FromByteArray(bytes, offset));
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
