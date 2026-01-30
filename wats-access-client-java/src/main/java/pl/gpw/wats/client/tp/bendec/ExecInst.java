package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * ExecInst
 * Instructions for order handling on exchange trading floor.
 */
public class ExecInst {
    private int value;
    private final int byteLength = 1;

    public ExecInst(int value) {
        this.value = value;
    }

    public ExecInst(byte[] bytes, int offset) {
        this(BendecUtils.uInt8FromByteArray(bytes, offset));
    }

    public void add(ExecInstOptions flag) {
        this.value = this.value | flag.getOptionValue();
    }

    public void remove(ExecInstOptions flag) {
        this.value = this.value ^ flag.getOptionValue();
    }

    public Set<ExecInstOptions> getFlags() {
        HashSet<ExecInstOptions> options = new HashSet<>();
        for (ExecInstOptions option : ExecInstOptions.values()) {
            if (isAdded(option))
                options.add(option);
        }
        if (options.size() > 1)
            options.remove(ExecInstOptions.TYPES.get(0));
        return options;
    }

    public boolean isAdded(ExecInstOptions flag) {
        return (this.value | flag.getOptionValue()) == this.value;
    }

    public int getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("|", "[", "]");
        for (ExecInstOptions option: ExecInstOptions.values()) {
            if (isAdded(option))
                sj.add(option.name());
        }
        return sj.toString();
    }
    
    byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        buffer.put(BendecUtils.uInt8ToByteArray(this.value));
        return buffer.array();
    }
    
    void toBytes(ByteBuffer buffer) {
        buffer.put(BendecUtils.uInt8ToByteArray(this.value));
    }

    public enum ExecInstOptions {
        NONE(0),
        CANCELONCONNECTIONLOSS(1);

        private final int optionValue;
        private static final Map<Integer, ExecInstOptions> TYPES = new HashMap<>();
        static {
            for (ExecInstOptions type : ExecInstOptions.values()) {
                TYPES.put(type.optionValue, type);
            }
        }

        /**
         * Get ExecInstOptions by attribute
         * @param val
         * @return ExecInstOptions enum or null if variant is undefined
         */
        public static ExecInstOptions getExecInst(int val) {
            return TYPES.get(val);
        }

        ExecInstOptions(int newValue) {
            this.optionValue = newValue;
        }

        public int getOptionValue() {
            return optionValue;
        }
    }
}