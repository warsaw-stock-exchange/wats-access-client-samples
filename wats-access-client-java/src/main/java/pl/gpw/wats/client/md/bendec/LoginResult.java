package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: LoginResult
 * Login  response code of a BIN session.
 */
public enum LoginResult {
    /**
     * Successful login.
     */
    OK(1),
    /**
     * Authorization failure.
     */
    INVALIDTOKEN(2),
    /**
     * Already logged in.
     */
    ALREADYLOGGEDIN(3),
    /**
     * Login is currently unavailable due to reasons such as service unavailability.
     */
    LOGINNOTALLOWED(4),
    /**
     * Other errors.
     */
    OTHER(5);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, LoginResult> TYPES = new HashMap<>();
    static {
        for (LoginResult type : LoginResult.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    LoginResult(int newValue) {
        value = newValue;
    }
    
    /**
     * Get LoginResult by attribute
     * @param val
     * @return LoginResult enum or null if variant is undefined
     */
    public static LoginResult getLoginResult(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get LoginResult int value
     * @return int value
     */
    public int getLoginResultValue() {
        return value; 
    }
    
    /**
     * Get LoginResult from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static LoginResult getLoginResult(byte[] bytes, int offset) {
        return getLoginResult(BendecUtils.uInt8FromByteArray(bytes, offset));
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