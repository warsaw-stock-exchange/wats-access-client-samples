package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * RealTimeIndexPresenceFlags
 * Indicates presence of values within RealTimeIndex fields.
 */
public class RealTimeIndexPresenceFlags {
    private int value;
    private final int byteLength = 1;
    
    public RealTimeIndexPresenceFlags(int value) {
        this.value = value;
    }

    public RealTimeIndexPresenceFlags(byte[] bytes, int offset) {
        this(BendecUtils.uInt8FromByteArray(bytes, offset));
    }

    public void add(RealTimeIndexPresenceFlagsOptions flag) {
        this.value = this.value | flag.getOptionValue();
    }
    
    public void remove(RealTimeIndexPresenceFlagsOptions flag) {
        this.value = this.value ^ flag.getOptionValue();
    }

    public Set<RealTimeIndexPresenceFlagsOptions> getFlags() {
        HashSet<RealTimeIndexPresenceFlagsOptions> options = new HashSet<>();
        for (RealTimeIndexPresenceFlagsOptions option : RealTimeIndexPresenceFlagsOptions.values()) {
            if (isAdded(option))
                options.add(option);
        }
        if (options.size() > 1)
            options.remove(RealTimeIndexPresenceFlagsOptions.TYPES.get(0));
        return options;
    }

    public boolean isAdded(RealTimeIndexPresenceFlagsOptions flag) {
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
    
    public enum RealTimeIndexPresenceFlagsOptions {
        NONE(0),
        HASTRADINGVALUE(1),
        HASINDEXVALUEBID(2),
        HASINDEXVALUEASK(4),
        HASMIDSPREADINDEX(8),
        HASDIFFERENCECENTRALSPREAD(16);
        
        private final int optionValue;
        private static final Map<Integer, RealTimeIndexPresenceFlagsOptions> TYPES = new HashMap<>();
        static {
            for (RealTimeIndexPresenceFlagsOptions type : RealTimeIndexPresenceFlagsOptions.values()) {
                TYPES.put(type.optionValue, type);
            }
        }
        
        /**
         * Get RealTimeIndexPresenceFlagsOptions by attribute
         * @param val
         * @return RealTimeIndexPresenceFlagsOptions enum or null if variant is undefined
         */
        public static RealTimeIndexPresenceFlagsOptions getRealTimeIndexPresenceFlags(int val) {
            return TYPES.get(val);
        }
        
        RealTimeIndexPresenceFlagsOptions(int newValue) {
            this.optionValue = newValue;
        }
        
        public int getOptionValue() {
            return optionValue;
        }
    }
}