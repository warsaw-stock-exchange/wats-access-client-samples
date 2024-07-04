package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * MifidFlags
 * Mifid related flags.
 */
public class MifidFlags {
    private int value;
    private final int byteLength = 1;
    
    public MifidFlags(int value) {
        this.value = value;
    }

    public MifidFlags(byte[] bytes, int offset) {
        this(BendecUtils.uInt8FromByteArray(bytes, offset));
    }

    public void add(MifidFlagsOptions flag) {
        this.value = this.value | flag.getOptionValue();
    }
    
    public void remove(MifidFlagsOptions flag) {
        this.value = this.value ^ flag.getOptionValue();
    }

    public Set<MifidFlagsOptions> getFlags() {
        HashSet<MifidFlagsOptions> options = new HashSet<>();
        for (MifidFlagsOptions option : MifidFlagsOptions.values()) {
            if (isAdded(option))
                options.add(option);
        }
        if (options.size() > 1)
            options.remove(MifidFlagsOptions.TYPES.get(0));
        return options;
    }

    public boolean isAdded(MifidFlagsOptions flag) {
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
    
    public enum MifidFlagsOptions {
        NONE(0),
        LIQUIDITYPROVISIONACTIVITY(1),
        DIRECTORSPONSOREDACCESS(2),
        MARKETMAKERORSPECIALIST(4);
        
        private final int optionValue;
        private static final Map<Integer, MifidFlagsOptions> TYPES = new HashMap<>();
        static {
            for (MifidFlagsOptions type : MifidFlagsOptions.values()) {
                TYPES.put(type.optionValue, type);
            }
        }
        
        /**
         * Get MifidFlagsOptions by attribute
         * @param val
         * @return MifidFlagsOptions enum or null if variant is undefined
         */
        public static MifidFlagsOptions getMifidFlags(int val) {
            return TYPES.get(val);
        }
        
        MifidFlagsOptions(int newValue) {
            this.optionValue = newValue;
        }
        
        public int getOptionValue() {
            return optionValue;
        }
    }
}