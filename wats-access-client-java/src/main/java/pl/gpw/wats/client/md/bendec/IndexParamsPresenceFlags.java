package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * IndexParamsPresenceFlags
 * Indicates presence of values within IndexParams fields.
 */
public class IndexParamsPresenceFlags {
    private int value;
    private final int byteLength = 1;

    public IndexParamsPresenceFlags(int value) {
        this.value = value;
    }

    public IndexParamsPresenceFlags(byte[] bytes, int offset) {
        this(BendecUtils.uInt8FromByteArray(bytes, offset));
    }

    public void add(IndexParamsPresenceFlagsOptions flag) {
        this.value = this.value | flag.getOptionValue();
    }

    public void remove(IndexParamsPresenceFlagsOptions flag) {
        this.value = this.value ^ flag.getOptionValue();
    }

    public Set<IndexParamsPresenceFlagsOptions> getFlags() {
        HashSet<IndexParamsPresenceFlagsOptions> options = new HashSet<>();
        for (IndexParamsPresenceFlagsOptions option : IndexParamsPresenceFlagsOptions.values()) {
            if (isAdded(option))
                options.add(option);
        }
        if (options.size() > 1)
            options.remove(IndexParamsPresenceFlagsOptions.TYPES.get(0));
        return options;
    }

    public boolean isAdded(IndexParamsPresenceFlagsOptions flag) {
        return (this.value | flag.getOptionValue()) == this.value;
    }

    public int getValue() {
        return value;
    }
    
    byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        buffer.put(BendecUtils.uInt8ToByteArray(this.value));
        return buffer.array();
    }
    
    void toBytes(ByteBuffer buffer) {
        buffer.put(BendecUtils.uInt8ToByteArray(this.value));
    }

    public enum IndexParamsPresenceFlagsOptions {
        NONE(0),
        HASDAYSSINCELASTPUBLICATION(1),
        HASNUMBEROFDIVIDENDS(2);

        private final int optionValue;
        private static final Map<Integer, IndexParamsPresenceFlagsOptions> TYPES = new HashMap<>();
        static {
            for (IndexParamsPresenceFlagsOptions type : IndexParamsPresenceFlagsOptions.values()) {
                TYPES.put(type.optionValue, type);
            }
        }

        /**
         * Get IndexParamsPresenceFlagsOptions by attribute
         * @param val
         * @return IndexParamsPresenceFlagsOptions enum or null if variant is undefined
         */
        public static IndexParamsPresenceFlagsOptions getIndexParamsPresenceFlags(int val) {
            return TYPES.get(val);
        }

        IndexParamsPresenceFlagsOptions(int newValue) {
            this.optionValue = newValue;
        }

        public int getOptionValue() {
            return optionValue;
        }
    }
}