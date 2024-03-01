package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: Country
 * Coutry codes based on ISO 3166
 */
public enum Country {
    /**
     * Australia
     */
    AUS(36),
    /**
     * Austria
     */
    AUT(40),
    /**
     * Belgium
     */
    BEL(56),
    /**
     * Bulgaria
     */
    BGR(100),
    /**
     * Canada
     */
    CAN(124),
    /**
     * Cyprus
     */
    CYP(196),
    /**
     * Czechia
     */
    CZE(203),
    /**
     * Estonia
     */
    EST(233),
    /**
     * France
     */
    FRA(250),
    /**
     * Germany
     */
    DEU(276),
    /**
     * Hungary
     */
    HUN(348),
    /**
     * Ireland
     */
    IRL(372),
    /**
     * Israel
     */
    ISR(376),
    /**
     * Italy
     */
    ITA(380),
    /**
     * Lithuania
     */
    LTU(440),
    /**
     * Luxembourg
     */
    LUX(442),
    /**
     * Netherlands (the)
     */
    NLD(528),
    /**
     * Poland
     */
    POL(616),
    /**
     * Portugal
     */
    PRT(620),
    /**
     * Romania
     */
    ROU(642),
    /**
     * Slovakia
     */
    SVK(703),
    /**
     * Slovenia
     */
    SVN(705),
    /**
     * Spain
     */
    ESP(724),
    /**
     * Sweden
     */
    SWE(752),
    /**
     * Ukraine
     */
    UKR(804),
    /**
     * United Kingdom of Great Britain and Northern Ireland (the)
     */
    GBR(826),
    /**
     * United States of America (the)
     */
    USA(840),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 2;


    private static final Map<Integer, Country> TYPES = new HashMap<>();
    static {
        for (Country type : Country.values()) {
            TYPES.put(type.value, type);
        }
    }


    Country(int newValue) {
        value = newValue;
    }

    /**
     Get Country from java input
     * @param newValue
     * @return Country enum
     */
    public static Country getCountry(int newValue) {
        Country val = TYPES.get(newValue);
        return val == null ? Country.UNKNOWN : val;
    }

    /**
     * Get Country int value
     * @return int value
     */
    public int getCountryValue() { return value; }


    /**
     Get Country from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static Country getCountry(byte[] bytes, int offset) {
        return getCountry(BendecUtils.uInt16FromByteArray(bytes, offset));
    }

    byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        buffer.put(BendecUtils.uInt16ToByteArray(this.value));
        return buffer.array();
    }

    void toBytes(ByteBuffer buffer) {
        buffer.put(BendecUtils.uInt16ToByteArray(this.value));
    }

}
