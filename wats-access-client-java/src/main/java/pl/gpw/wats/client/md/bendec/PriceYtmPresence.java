package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * PriceYtmPresence
 * Bit flags indicating the filling of 'value', 'valueAsk', 'valueBid' attributes in the price update message.
 */
public class PriceYtmPresence {
    private int value;
    private final int byteLength = 2;
    
    public PriceYtmPresence(int value) {
        this.value = value;
    }

    public PriceYtmPresence(byte[] bytes, int offset) {
        this(BendecUtils.uInt8FromByteArray(bytes, offset));
    }

    public void add(PriceYtmPresenceOptions flag) {
        this.value = this.value | flag.getOptionValue();
    }
    
    public void remove(PriceYtmPresenceOptions flag) {
        this.value = this.value ^ flag.getOptionValue();
    }

    public Set<PriceYtmPresenceOptions> getFlags() {
        HashSet<PriceYtmPresenceOptions> options = new HashSet<>();
        for (PriceYtmPresenceOptions option : PriceYtmPresenceOptions.values()) {
            if (isAdded(option))
                options.add(option);
        }
        if (options.size() > 1)
            options.remove(PriceYtmPresenceOptions.TYPES.get(0));
        return options;
    }

    public boolean isAdded(PriceYtmPresenceOptions flag) {
        return (this.value | flag.getOptionValue()) == this.value;
    }

    public int getValue() {
        return value;
    }
    
    byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        buffer.put(BendecUtils.uInt16ToByteArray(this.value));
        return buffer.array();
    }
    
    void toBytes(ByteBuffer buffer) {
        buffer.put(BendecUtils.uInt16ToByteArray(this.value));
    }
    
    public enum PriceYtmPresenceOptions {
        /**
         * No values provided.
         */
        NONE(0),
        /**
         * A price was provided.
         */
        VALUE(1),
        /**
         * A bid price was provided.
         */
        VALUEBID(2),
        /**
         * An ask price was provided.
         */
        VALUEASK(4),
        /**
         * YTM was not provided due to the absence of its calculation.
         */
        YTMNOCALC(8),
        /**
         * Bid YTM was not provided due to the absence of its calculation.
         */
        YTMBIDNOCALC(16),
        /**
         * Ask YTM was not provided due to the absence of its calculation.
         */
        YTMASKNOCALC(32);
        
        private final int optionValue;
        private static final Map<Integer, PriceYtmPresenceOptions> TYPES = new HashMap<>();
        static {
            for (PriceYtmPresenceOptions type : PriceYtmPresenceOptions.values()) {
                TYPES.put(type.optionValue, type);
            }
        }
        
        /**
         * Get PriceYtmPresenceOptions by attribute
         * @param val
         * @return PriceYtmPresenceOptions enum or null if variant is undefined
         */
        public static PriceYtmPresenceOptions getPriceYtmPresence(int val) {
            return TYPES.get(val);
        }
        
        PriceYtmPresenceOptions(int newValue) {
            this.optionValue = newValue;
        }
        
        public int getOptionValue() {
            return optionValue;
        }
    }
}