package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>MifidField</h2>
 * <p>Field related to the MiFID directive.</p>
 * <p>Byte length: 5</p>
 * <p>ShortCode > long (u32) shortCode - Short code of MiFID participant. | size 4</p>
 * <p>PartyRoleQualifier qualifier - Qualifier of MiFID participant. | size 1</p>
 * */

public class MifidField implements ByteSerializable {

    private long shortCode;
    private PartyRoleQualifier qualifier;
    public static final int byteLength = 5;

    public MifidField(long shortCode, PartyRoleQualifier qualifier) {
        this.shortCode = shortCode;
        this.qualifier = qualifier;
    }

    public MifidField(byte[] bytes, int offset) {
        this.shortCode = BendecUtils.uInt32FromByteArray(bytes, offset);
        this.qualifier = PartyRoleQualifier.getPartyRoleQualifier(bytes, offset + 4);
    }

    public MifidField(byte[] bytes) {
        this(bytes, 0);
    }

    public MifidField() {
    }



    /**
     * @return Short code of MiFID participant.
     */
    public long getShortCode() {
        return this.shortCode;
    };
    /**
     * @return Qualifier of MiFID participant.
     */
    public PartyRoleQualifier getQualifier() {
        return this.qualifier;
    };

    /**
     * @param shortCode Short code of MiFID participant.
     */
    public void setShortCode(long shortCode) {
        this.shortCode = shortCode;
    };
    /**
     * @param qualifier Qualifier of MiFID participant.
     */
    public void setQualifier(PartyRoleQualifier qualifier) {
        this.qualifier = qualifier;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        buffer.put(BendecUtils.uInt32ToByteArray(this.shortCode));
        qualifier.toBytes(buffer);
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        buffer.put(BendecUtils.uInt32ToByteArray(this.shortCode));
        qualifier.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortCode, qualifier);
    }

    @Override
    public String toString() {
        return "MifidField{" +
            "shortCode=" + shortCode +
            ", qualifier=" + qualifier +
            '}';
        }
}
