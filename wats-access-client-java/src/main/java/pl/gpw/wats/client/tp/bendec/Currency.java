package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;

/**
 * Enum: Currency
 * Currency - ISO 4217 (2022/04/01) code (e.g. PLN).
 */
public enum Currency {
    /**
     * Lek
     */
    ALL(8),
    /**
     * Algerian Dinar
     */
    DZD(12),
    /**
     * Argentine Peso
     */
    ARS(32),
    /**
     * Australian Dollar
     */
    AUD(36),
    /**
     * Bahamian Dollar
     */
    BSD(44),
    /**
     * Bahraini Dinar
     */
    BHD(48),
    /**
     * Taka
     */
    BDT(50),
    /**
     * Armenian Dram
     */
    AMD(51),
    /**
     * Barbados Dollar
     */
    BBD(52),
    /**
     * Bermudian Dollar
     */
    BMD(60),
    /**
     * Ngultrum
     */
    BTN(64),
    /**
     * Boliviano
     */
    BOB(68),
    /**
     * Pula
     */
    BWP(72),
    /**
     * Belize Dollar
     */
    BZD(84),
    /**
     * Solomon Islands Dollar
     */
    SBD(90),
    /**
     * Brunei Dollar
     */
    BND(96),
    /**
     * Kyat
     */
    MMK(104),
    /**
     * Burundi Franc
     */
    BIF(108),
    /**
     * Riel
     */
    KHR(116),
    /**
     * Canadian Dollar
     */
    CAD(124),
    /**
     * Cabo Verde Escudo
     */
    CVE(132),
    /**
     * Cayman Islands Dollar
     */
    KYD(136),
    /**
     * Sri Lanka Rupee
     */
    LKR(144),
    /**
     * Chilean Peso
     */
    CLP(152),
    /**
     * Yuan Renminbi
     */
    CNY(156),
    /**
     * Colombian Peso
     */
    COP(170),
    /**
     * Comorian Franc 
     */
    KMF(174),
    /**
     * Costa Rican Colon
     */
    CRC(188),
    /**
     * Kuna
     */
    HRK(191),
    /**
     * Cuban Peso
     */
    CUP(192),
    /**
     * Czech Koruna
     */
    CZK(203),
    /**
     * Danish Krone
     */
    DKK(208),
    /**
     * Dominican Peso
     */
    DOP(214),
    /**
     * El Salvador Colon
     */
    SVC(222),
    /**
     * Ethiopian Birr
     */
    ETB(230),
    /**
     * Nakfa
     */
    ERN(232),
    /**
     * Falkland Islands Pound
     */
    FKP(238),
    /**
     * Fiji Dollar
     */
    FJD(242),
    /**
     * Djibouti Franc
     */
    DJF(262),
    /**
     * Dalasi
     */
    GMD(270),
    /**
     * Gibraltar Pound
     */
    GIP(292),
    /**
     * Quetzal
     */
    GTQ(320),
    /**
     * Guinean Franc
     */
    GNF(324),
    /**
     * Guyana Dollar
     */
    GYD(328),
    /**
     * Gourde
     */
    HTG(332),
    /**
     * Lempira
     */
    HNL(340),
    /**
     * Hong Kong Dollar
     */
    HKD(344),
    /**
     * Forint
     */
    HUF(348),
    /**
     * Iceland Krona
     */
    ISK(352),
    /**
     * Indian Rupee
     */
    INR(356),
    /**
     * Rupiah
     */
    IDR(360),
    /**
     * Iranian Rial
     */
    IRR(364),
    /**
     * Iraqi Dinar
     */
    IQD(368),
    /**
     * New Israeli Sheqel
     */
    ILS(376),
    /**
     * Jamaican Dollar
     */
    JMD(388),
    /**
     * Yen
     */
    JPY(392),
    /**
     * Tenge
     */
    KZT(398),
    /**
     * Jordanian Dinar
     */
    JOD(400),
    /**
     * Kenyan Shilling
     */
    KES(404),
    /**
     * North Korean Won
     */
    KPW(408),
    /**
     * Won
     */
    KRW(410),
    /**
     * Kuwaiti Dinar
     */
    KWD(414),
    /**
     * Som
     */
    KGS(417),
    /**
     * Lao Kip
     */
    LAK(418),
    /**
     * Lebanese Pound
     */
    LBP(422),
    /**
     * Loti
     */
    LSL(426),
    /**
     * Liberian Dollar
     */
    LRD(430),
    /**
     * Libyan Dinar
     */
    LYD(434),
    /**
     * Pataca
     */
    MOP(446),
    /**
     * Malawi Kwacha
     */
    MWK(454),
    /**
     * Malaysian Ringgit
     */
    MYR(458),
    /**
     * Rufiyaa
     */
    MVR(462),
    /**
     * Mauritius Rupee
     */
    MUR(480),
    /**
     * Mexican Peso
     */
    MXN(484),
    /**
     * Tugrik
     */
    MNT(496),
    /**
     * Moldovan Leu
     */
    MDL(498),
    /**
     * Moroccan Dirham
     */
    MAD(504),
    /**
     * Rial Omani
     */
    OMR(512),
    /**
     * Namibia Dollar
     */
    NAD(516),
    /**
     * Nepalese Rupee
     */
    NPR(524),
    /**
     * Netherlands Antillean Guilder
     */
    ANG(532),
    /**
     * Aruban Florin
     */
    AWG(533),
    /**
     * Vatu
     */
    VUV(548),
    /**
     * New Zealand Dollar
     */
    NZD(554),
    /**
     * Cordoba Oro
     */
    NIO(558),
    /**
     * Naira
     */
    NGN(566),
    /**
     * Norwegian Krone
     */
    NOK(578),
    /**
     * Pakistan Rupee
     */
    PKR(586),
    /**
     * Balboa
     */
    PAB(590),
    /**
     * Kina
     */
    PGK(598),
    /**
     * Guarani
     */
    PYG(600),
    /**
     * Sol
     */
    PEN(604),
    /**
     * Philippine Peso
     */
    PHP(608),
    /**
     * Qatari Rial
     */
    QAR(634),
    /**
     * Russian Ruble
     */
    RUB(643),
    /**
     * Rwanda Franc
     */
    RWF(646),
    /**
     * Saint Helena Pound
     */
    SHP(654),
    /**
     * Saudi Riyal
     */
    SAR(682),
    /**
     * Seychelles Rupee
     */
    SCR(690),
    /**
     * Singapore Dollar
     */
    SGD(702),
    /**
     * Dong
     */
    VND(704),
    /**
     * Somali Shilling
     */
    SOS(706),
    /**
     * Rand
     */
    ZAR(710),
    /**
     * South Sudanese Pound
     */
    SSP(728),
    /**
     * Lilangeni
     */
    SZL(748),
    /**
     * Swedish Krona
     */
    SEK(752),
    /**
     * Swiss Franc
     */
    CHF(756),
    /**
     * Syrian Pound
     */
    SYP(760),
    /**
     * Baht
     */
    THB(764),
    /**
     * Pa’anga
     */
    TOP(776),
    /**
     * Trinidad and Tobago Dollar
     */
    TTD(780),
    /**
     * UAE Dirham
     */
    AED(784),
    /**
     * Tunisian Dinar
     */
    TND(788),
    /**
     * Uganda Shilling
     */
    UGX(800),
    /**
     * Denar
     */
    MKD(807),
    /**
     * Egyptian Pound
     */
    EGP(818),
    /**
     * Pound Sterling
     */
    GBP(826),
    /**
     * Tanzanian Shilling
     */
    TZS(834),
    /**
     * US Dollar
     */
    USD(840),
    /**
     * Peso Uruguayo
     */
    UYU(858),
    /**
     * Uzbekistan Sum
     */
    UZS(860),
    /**
     * Tala
     */
    WST(882),
    /**
     * Yemeni Rial
     */
    YER(886),
    /**
     * New Taiwan Dollar
     */
    TWD(901),
    /**
     * Leone
     */
    SLE(925),
    /**
     * Bolívar Soberano
     */
    VED(926),
    /**
     * Unidad Previsional
     */
    UYW(927),
    /**
     * Bolívar Soberano
     */
    VES(928),
    /**
     * Ouguiya
     */
    MRU(929),
    /**
     * Dobra
     */
    STN(930),
    /**
     * Peso Convertible
     */
    CUC(931),
    /**
     * Zimbabwe Dollar
     */
    ZWL(932),
    /**
     * Belarusian Ruble
     */
    BYN(933),
    /**
     * Turkmenistan New Manat
     */
    TMT(934),
    /**
     * Ghana Cedi
     */
    GHS(936),
    /**
     * Sudanese Pound
     */
    SDG(938),
    /**
     * [object Object]
     */
    UYI(940),
    /**
     * Serbian Dinar
     */
    RSD(941),
    /**
     * Mozambique Metical
     */
    MZN(943),
    /**
     * Azerbaijan Manat
     */
    AZN(944),
    /**
     * Romanian Leu
     */
    RON(946),
    /**
     * [object Object]
     */
    CHE(947),
    /**
     * [object Object]
     */
    CHW(948),
    /**
     * Turkish Lira
     */
    TRY(949),
    /**
     * CFA Franc BEAC
     */
    XAF(950),
    /**
     * East Caribbean Dollar
     */
    XCD(951),
    /**
     * CFA Franc BCEAO
     */
    XOF(952),
    /**
     * CFP Franc
     */
    XPF(953),
    /**
     * Bond Markets Unit European Composite Unit (EURCO)
     */
    XBA(955),
    /**
     * Bond Markets Unit European Monetary Unit (E.M.U.-6)
     */
    XBB(956),
    /**
     * Bond Markets Unit European Unit of Account 9 (E.U.A.-9)
     */
    XBC(957),
    /**
     * Bond Markets Unit European Unit of Account 17 (E.U.A.-17)
     */
    XBD(958),
    /**
     * Gold
     */
    XAU(959),
    /**
     * SDR (Special Drawing Right)
     */
    XDR(960),
    /**
     * Silver
     */
    XAG(961),
    /**
     * Platinum
     */
    XPT(962),
    /**
     * Codes specifically reserved for testing purposes
     */
    XTS(963),
    /**
     * Palladium
     */
    XPD(964),
    /**
     * ADB Unit of Account
     */
    XUA(965),
    /**
     * Zambian Kwacha
     */
    ZMW(967),
    /**
     * Surinam Dollar
     */
    SRD(968),
    /**
     * Malagasy Ariary
     */
    MGA(969),
    /**
     * [object Object]
     */
    COU(970),
    /**
     * Afghani
     */
    AFN(971),
    /**
     * Somoni
     */
    TJS(972),
    /**
     * Kwanza
     */
    AOA(973),
    /**
     * Bulgarian Lev
     */
    BGN(975),
    /**
     * Congolese Franc
     */
    CDF(976),
    /**
     * Convertible Mark
     */
    BAM(977),
    /**
     * Euro
     */
    EUR(978),
    /**
     * [object Object]
     */
    MXV(979),
    /**
     * Hryvnia
     */
    UAH(980),
    /**
     * Lari
     */
    GEL(981),
    /**
     * [object Object]
     */
    BOV(984),
    /**
     * Zloty
     */
    PLN(985),
    /**
     * Brazilian Real
     */
    BRL(986),
    /**
     * [object Object]
     */
    CLF(990),
    /**
     * Sucre
     */
    XSU(994),
    /**
     * [object Object]
     */
    USN(997),
    /**
     * The codes assigned for transactions where no currency is involved
     */
    XXX(999),
    UNKNOWN(99999);

    private final int value;

    private final int byteLength = 2;


    private static final Map<Integer, Currency> TYPES = new HashMap<>();
    static {
        for (Currency type : Currency.values()) {
            TYPES.put(type.value, type);
        }
    }


    Currency(int newValue) {
        value = newValue;
    }

    /**
     Get Currency from java input
     * @param newValue
     * @return Currency enum
     */
    public static Currency getCurrency(int newValue) {
        Currency val = TYPES.get(newValue);
        return val == null ? Currency.UNKNOWN : val;
    }

    /**
     * Get Currency int value
     * @return int value
     */
    public int getCurrencyValue() { return value; }


    /**
     Get Currency from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static Currency getCurrency(byte[] bytes, int offset) {
        return getCurrency(BendecUtils.uInt16FromByteArray(bytes, offset));
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
