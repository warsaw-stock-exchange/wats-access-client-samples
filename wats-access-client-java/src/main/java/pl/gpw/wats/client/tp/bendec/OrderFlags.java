package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * OrderFlags
 * Order related flags.
 */
public class OrderFlags {
    private int value;
    private final int byteLength = 1;
    
    public OrderFlags(int value) {
        this.value = value;
    }

    public OrderFlags(byte[] bytes, int offset) {
        this(BendecUtils.uInt8FromByteArray(bytes, offset));
    }

    public void add(OrderFlagsOptions flag) {
        this.value = this.value | flag.getOptionValue();
    }
    
    public void remove(OrderFlagsOptions flag) {
        this.value = this.value ^ flag.getOptionValue();
    }

    public Set<OrderFlagsOptions> getFlags() {
        HashSet<OrderFlagsOptions> options = new HashSet<>();
        for (OrderFlagsOptions option : OrderFlagsOptions.values()) {
            if (isAdded(option))
                options.add(option);
        }
        if (options.size() > 1)
            options.remove(OrderFlagsOptions.TYPES.get(0));
        return options;
    }

    public boolean isAdded(OrderFlagsOptions flag) {
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
    
    public enum OrderFlagsOptions {
        NONE(0),
        LIQUIDITYPROVISIONACTIVITY(1),
        DIRECTORSPONSOREDACCESS(2),
        MARKETMAKERORSPECIALIST(4);
        
        private final int optionValue;
        private static final Map<Integer, OrderFlagsOptions> TYPES = new HashMap<>();
        static {
            for (OrderFlagsOptions type : OrderFlagsOptions.values()) {
                TYPES.put(type.optionValue, type);
            }
        }
        
        /**
         * Get OrderFlagsOptions by attribute
         * @param val
         * @return OrderFlagsOptions enum or null if variant is undefined
         */
        public static OrderFlagsOptions getOrderFlags(int val) {
            return TYPES.get(val);
        }
        
        OrderFlagsOptions(int newValue) {
            this.optionValue = newValue;
        }
        
        public int getOptionValue() {
            return optionValue;
        }
    }
}