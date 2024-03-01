package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
    UNKNOWN(99999);

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
     Get LoginResult from java input
     * @param newValue
     * @return LoginResult enum
     */
    public static LoginResult getLoginResult(int newValue) {
        LoginResult val = TYPES.get(newValue);
        return val == null ? LoginResult.UNKNOWN : val;
    }

    /**
     * Get LoginResult int value
     * @return int value
     */
    public int getLoginResultValue() { return value; }


    /**
     Get LoginResult from bytes
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
