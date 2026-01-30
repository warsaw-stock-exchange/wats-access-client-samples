package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: AccountType
 * Account type.
 */
public enum AccountType {
    /**
     * Account is missing. Account is expected to be filled with 0x00.
     */
    MISSING(1),
    /**
     * Account is carried on customer side of the books.
     */
    CUSTOMER(2),
    /**
     * House trader.
     */
    HOUSE(3);

    private final int value;
    private final int byteLength = 1;

    private static final Map<Integer, AccountType> TYPES = new HashMap<>();
    static {
        for (AccountType type : AccountType.values()) {
            TYPES.put(type.value, type);
        }
    }

    AccountType(int newValue) {
        value = newValue;
    }

    /**
     * Get AccountType by attribute
     * @param val
     * @return AccountType enum or null if variant is undefined
     */
    public static AccountType getAccountType(int val) {
        return TYPES.get(val);
    }

    /**
     * Get AccountType int value
     * @return int value
     */
    public int getAccountTypeValue() {
        return value;
    }
    
    /**
     * Get AccountType from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static AccountType getAccountType(byte[] bytes, int offset) {
        return getAccountType(BendecUtils.uInt8FromByteArray(bytes, offset));
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