package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * MessageFilter
 * Bit flags indicating which message types filtered out in stream.
 */
public class MessageFilter {
    private int value;
    private final int byteLength = 1;

    public MessageFilter(int value) {
        this.value = value;
    }

    public MessageFilter(byte[] bytes, int offset) {
        this(BendecUtils.uInt8FromByteArray(bytes, offset));
    }

    public void add(MessageFilterOptions flag) {
        this.value = this.value | flag.getOptionValue();
    }

    public void remove(MessageFilterOptions flag) {
        this.value = this.value ^ flag.getOptionValue();
    }

    public Set<MessageFilterOptions> getFlags() {
        HashSet<MessageFilterOptions> options = new HashSet<>();
        for (MessageFilterOptions option : MessageFilterOptions.values()) {
            if (isAdded(option))
                options.add(option);
        }
        if (options.size() > 1)
            options.remove(MessageFilterOptions.TYPES.get(0));
        return options;
    }

    public boolean isAdded(MessageFilterOptions flag) {
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

    public enum MessageFilterOptions {
        /**
         * Disable trades and ref_data.
         */
        NONE(0),
        /**
         * Enable trades.
         */
        TRADES(1),
        /**
         * Enable ref_data.
         */
        REFDATA(2);

        private final int optionValue;
        private static final Map<Integer, MessageFilterOptions> TYPES = new HashMap<>();
        static {
            for (MessageFilterOptions type : MessageFilterOptions.values()) {
                TYPES.put(type.optionValue, type);
            }
        }

        /**
         * Get MessageFilterOptions by attribute
         * @param val
         * @return MessageFilterOptions enum or null if variant is undefined
         */
        public static MessageFilterOptions getMessageFilter(int val) {
            return TYPES.get(val);
        }

        MessageFilterOptions(int newValue) {
            this.optionValue = newValue;
        }

        public int getOptionValue() {
            return optionValue;
        }
    }
}