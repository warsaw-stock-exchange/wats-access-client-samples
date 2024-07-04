package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: LoginResult
 * Login response status code.
 */
public enum LoginResult {
    /**
     * Successful login.
     */
    OK(1),
    /**
     * User not found.
     */
    NOTFOUND(2),
    /**
     * Authorization failure.
     */
    INVALIDTOKEN(3),
    /**
     * Already logged in.
     */
    ALREADYLOGGEDIN(4),
    /**
     * Account locked.
     */
    ACCOUNTLOCKED(5),
    /**
     * Login is currently unavailable due to reasons such as service unavailability.
     */
    LOGINNOTALLOWED(6),
    /**
     * The login parameters, such as the connection ID, are invalid.
     */
    INVALIDLOGINPARAMETERS(7),
    /**
     * The login attempt failed due to exceeding the anti-flooding threshold.
     */
    THROTTLINGTEMPORARYLOCK(8),
    /**
     * Other errors.
     */
    OTHER(9);
    
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