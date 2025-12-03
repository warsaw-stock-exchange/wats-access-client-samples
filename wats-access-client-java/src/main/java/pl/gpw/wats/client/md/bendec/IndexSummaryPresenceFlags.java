package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * IndexSummaryPresenceFlags
 * Indicates presence of values within IndexSummary fields.
 */
public class IndexSummaryPresenceFlags {
    private int value;
    private final int byteLength = 1;
    
    public IndexSummaryPresenceFlags(int value) {
        this.value = value;
    }

    public IndexSummaryPresenceFlags(byte[] bytes, int offset) {
        this(BendecUtils.uInt8FromByteArray(bytes, offset));
    }

    public void add(IndexSummaryPresenceFlagsOptions flag) {
        this.value = this.value | flag.getOptionValue();
    }
    
    public void remove(IndexSummaryPresenceFlagsOptions flag) {
        this.value = this.value ^ flag.getOptionValue();
    }

    public Set<IndexSummaryPresenceFlagsOptions> getFlags() {
        HashSet<IndexSummaryPresenceFlagsOptions> options = new HashSet<>();
        for (IndexSummaryPresenceFlagsOptions option : IndexSummaryPresenceFlagsOptions.values()) {
            if (isAdded(option))
                options.add(option);
        }
        if (options.size() > 1)
            options.remove(IndexSummaryPresenceFlagsOptions.TYPES.get(0));
        return options;
    }

    public boolean isAdded(IndexSummaryPresenceFlagsOptions flag) {
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
    
    public enum IndexSummaryPresenceFlagsOptions {
        NONE(0),
        HASSESSIONAVG(1);
        
        private final int optionValue;
        private static final Map<Integer, IndexSummaryPresenceFlagsOptions> TYPES = new HashMap<>();
        static {
            for (IndexSummaryPresenceFlagsOptions type : IndexSummaryPresenceFlagsOptions.values()) {
                TYPES.put(type.optionValue, type);
            }
        }
        
        /**
         * Get IndexSummaryPresenceFlagsOptions by attribute
         * @param val
         * @return IndexSummaryPresenceFlagsOptions enum or null if variant is undefined
         */
        public static IndexSummaryPresenceFlagsOptions getIndexSummaryPresenceFlags(int val) {
            return TYPES.get(val);
        }
        
        IndexSummaryPresenceFlagsOptions(int newValue) {
            this.optionValue = newValue;
        }
        
        public int getOptionValue() {
            return optionValue;
        }
    }
}