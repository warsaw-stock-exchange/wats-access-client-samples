package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * IndexPortfolioEntryPresenceFlags
 * Indicates presence of values within IndexPortfolioEntry fields.
 */
public class IndexPortfolioEntryPresenceFlags {
    private int value;
    private final int byteLength = 1;

    public IndexPortfolioEntryPresenceFlags(int value) {
        this.value = value;
    }

    public IndexPortfolioEntryPresenceFlags(byte[] bytes, int offset) {
        this(BendecUtils.uInt8FromByteArray(bytes, offset));
    }

    public void add(IndexPortfolioEntryPresenceFlagsOptions flag) {
        this.value = this.value | flag.getOptionValue();
    }

    public void remove(IndexPortfolioEntryPresenceFlagsOptions flag) {
        this.value = this.value ^ flag.getOptionValue();
    }

    public Set<IndexPortfolioEntryPresenceFlagsOptions> getFlags() {
        HashSet<IndexPortfolioEntryPresenceFlagsOptions> options = new HashSet<>();
        for (IndexPortfolioEntryPresenceFlagsOptions option : IndexPortfolioEntryPresenceFlagsOptions.values()) {
            if (isAdded(option))
                options.add(option);
        }
        if (options.size() > 1)
            options.remove(IndexPortfolioEntryPresenceFlagsOptions.TYPES.get(0));
        return options;
    }

    public boolean isAdded(IndexPortfolioEntryPresenceFlagsOptions flag) {
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

    public enum IndexPortfolioEntryPresenceFlagsOptions {
        NONE(0),
        HASINSTRUMENTID(1);

        private final int optionValue;
        private static final Map<Integer, IndexPortfolioEntryPresenceFlagsOptions> TYPES = new HashMap<>();
        static {
            for (IndexPortfolioEntryPresenceFlagsOptions type : IndexPortfolioEntryPresenceFlagsOptions.values()) {
                TYPES.put(type.optionValue, type);
            }
        }

        /**
         * Get IndexPortfolioEntryPresenceFlagsOptions by attribute
         * @param val
         * @return IndexPortfolioEntryPresenceFlagsOptions enum or null if variant is undefined
         */
        public static IndexPortfolioEntryPresenceFlagsOptions getIndexPortfolioEntryPresenceFlags(int val) {
            return TYPES.get(val);
        }

        IndexPortfolioEntryPresenceFlagsOptions(int newValue) {
            this.optionValue = newValue;
        }

        public int getOptionValue() {
            return optionValue;
        }
    }
}