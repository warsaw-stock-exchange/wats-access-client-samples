package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: Country
 * Coutry codes based on ISO 3166
 */
public enum Country {
    /**
     * Afghanistan
     */
    AFG(4),
    /**
     * Albania
     */
    ALB(8),
    /**
     * Antarctica
     */
    ATA(10),
    /**
     * Algeria
     */
    DZA(12),
    /**
     * American Samoa
     */
    ASM(16),
    /**
     * Andorra
     */
    AND(20),
    /**
     * Angola
     */
    AGO(24),
    /**
     * Antigua and Barbuda
     */
    ATG(28),
    /**
     * Azerbaijan
     */
    AZE(31),
    /**
     * Argentina
     */
    ARG(32),
    /**
     * Australia
     */
    AUS(36),
    /**
     * Austria
     */
    AUT(40),
    /**
     * Bahamas
     */
    BHS(44),
    /**
     * Bahrain
     */
    BHR(48),
    /**
     * Bangladesh
     */
    BGD(50),
    /**
     * Armenia
     */
    ARM(51),
    /**
     * Barbados
     */
    BRB(52),
    /**
     * Belgium
     */
    BEL(56),
    /**
     * Bermuda
     */
    BMU(60),
    /**
     * Bhutan
     */
    BTN(64),
    /**
     * Bolivia
     */
    BOL(68),
    /**
     * Bosnia and Herzegovina
     */
    BIH(70),
    /**
     * Botswana
     */
    BWA(72),
    /**
     * Bouvet Island
     */
    BVT(74),
    /**
     * Brazil
     */
    BRA(76),
    /**
     * Belize
     */
    BLZ(84),
    /**
     * British Indian Ocean Territory
     */
    IOT(86),
    /**
     * Solomon Islands
     */
    SLB(90),
    /**
     * Virgin Islands (British)
     */
    VGB(92),
    /**
     * Brunei Darussalam
     */
    BRN(96),
    /**
     * Bulgaria
     */
    BGR(100),
    /**
     * Myanmar
     */
    MMR(104),
    /**
     * Burundi
     */
    BDI(108),
    /**
     * Belarus
     */
    BLR(112),
    /**
     * Cambodia
     */
    KHM(116),
    /**
     * Cameroon
     */
    CMR(120),
    /**
     * Canada
     */
    CAN(124),
    /**
     * Cabo Verde
     */
    CPV(132),
    /**
     * Cayman Islands
     */
    CYM(136),
    /**
     * Central African Republic
     */
    CAF(140),
    /**
     * Sri Lanka
     */
    LKA(144),
    /**
     * Chad
     */
    TCD(148),
    /**
     * Chile
     */
    CHL(152),
    /**
     * China
     */
    CHN(156),
    /**
     * Taiwan
     */
    TWN(158),
    /**
     * Christmas Island
     */
    CXR(162),
    /**
     * Cocos (Keeling) Islands
     */
    CCK(166),
    /**
     * Colombia
     */
    COL(170),
    /**
     * Comoros
     */
    COM(174),
    /**
     * Mayotte
     */
    MYT(175),
    /**
     * Congo
     */
    COG(178),
    /**
     * Democratic Republic of the Congo
     */
    COD(180),
    /**
     * Cook Islands
     */
    COK(184),
    /**
     * Costa Rica
     */
    CRI(188),
    /**
     * Croatia
     */
    HRV(191),
    /**
     * Cuba
     */
    CUB(192),
    /**
     * Cyprus
     */
    CYP(196),
    /**
     * Czechia
     */
    CZE(203),
    /**
     * Benin
     */
    BEN(204),
    /**
     * Denmark
     */
    DNK(208),
    /**
     * Dominica
     */
    DMA(212),
    /**
     * Dominican Republic
     */
    DOM(214),
    /**
     * Ecuador
     */
    ECU(218),
    /**
     * El Salvador
     */
    SLV(222),
    /**
     * Equatorial Guinea
     */
    GNQ(226),
    /**
     * Ethiopia
     */
    ETH(231),
    /**
     * Eritrea
     */
    ERI(232),
    /**
     * Estonia
     */
    EST(233),
    /**
     * Faroe Islands
     */
    FRO(234),
    /**
     * Falkland Islands (Malvinas)
     */
    FLK(238),
    /**
     * South Georgia and the South Sandwich Islands
     */
    SGS(239),
    /**
     * Fiji
     */
    FJI(242),
    /**
     * Finland
     */
    FIN(246),
    /**
     * AlandIslands
     */
    ALA(248),
    /**
     * France
     */
    FRA(250),
    /**
     * French Guiana
     */
    GUF(254),
    /**
     * French Polynesia
     */
    PYF(258),
    /**
     * French Southern Territories
     */
    ATF(260),
    /**
     * Djibouti
     */
    DJI(262),
    /**
     * Gabon
     */
    GAB(266),
    /**
     * Georgia
     */
    GEO(268),
    /**
     * Gambia
     */
    GMB(270),
    /**
     * Palestine
     */
    PSE(275),
    /**
     * Germany
     */
    DEU(276),
    /**
     * Ghana
     */
    GHA(288),
    /**
     * Gibraltar
     */
    GIB(292),
    /**
     * Kiribati
     */
    KIR(296),
    /**
     * Greece
     */
    GRC(300),
    /**
     * Greenland
     */
    GRL(304),
    /**
     * Grenada
     */
    GRD(308),
    /**
     * Guadeloupe
     */
    GLP(312),
    /**
     * Guam
     */
    GUM(316),
    /**
     * Guatemala
     */
    GTM(320),
    /**
     * Guinea
     */
    GIN(324),
    /**
     * Guyana
     */
    GUY(328),
    /**
     * Haiti
     */
    HTI(332),
    /**
     * Heard Island and McDonald Islands
     */
    HMD(334),
    /**
     * Holy See
     */
    VAT(336),
    /**
     * Honduras
     */
    HND(340),
    /**
     * Hong Kong
     */
    HKG(344),
    /**
     * Hungary
     */
    HUN(348),
    /**
     * Iceland
     */
    ISL(352),
    /**
     * India
     */
    IND(356),
    /**
     * Indonesia
     */
    IDN(360),
    /**
     * Iran
     */
    IRN(364),
    /**
     * Iraq
     */
    IRQ(368),
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
     * Cote d'Ivoire
     */
    CIV(384),
    /**
     * Jamaica
     */
    JAM(388),
    /**
     * Japan
     */
    JPN(392),
    /**
     * Kazakhstan
     */
    KAZ(398),
    /**
     * Jordan
     */
    JOR(400),
    /**
     * Kenya
     */
    KEN(404),
    /**
     * Democratic People's Republic of Korea
     */
    PRK(408),
    /**
     * Republic of Korea
     */
    KOR(410),
    /**
     * Kuwait
     */
    KWT(414),
    /**
     * Kyrgyzstan
     */
    KGZ(417),
    /**
     * Lao People's Democratic Republic
     */
    LAO(418),
    /**
     * Lebanon
     */
    LBN(422),
    /**
     * Lesotho
     */
    LSO(426),
    /**
     * Latvia
     */
    LVA(428),
    /**
     * Liberia
     */
    LBR(430),
    /**
     * Libya
     */
    LBY(434),
    /**
     * Liechtenstein
     */
    LIE(438),
    /**
     * Lithuania
     */
    LTU(440),
    /**
     * Luxembourg
     */
    LUX(442),
    /**
     * Macao
     */
    MAC(446),
    /**
     * Madagascar
     */
    MDG(450),
    /**
     * Malawi
     */
    MWI(454),
    /**
     * Malaysia
     */
    MYS(458),
    /**
     * Maldives
     */
    MDV(462),
    /**
     * Mali
     */
    MLI(466),
    /**
     * Malta
     */
    MLT(470),
    /**
     * Martinique
     */
    MTQ(474),
    /**
     * Mauritania
     */
    MRT(478),
    /**
     * Mauritius
     */
    MUS(480),
    /**
     * Mexico
     */
    MEX(484),
    /**
     * Monaco
     */
    MCO(492),
    /**
     * Mongolia
     */
    MNG(496),
    /**
     * Republic of Moldova
     */
    MDA(498),
    /**
     * Montenegro
     */
    MNE(499),
    /**
     * Montserrat
     */
    MSR(500),
    /**
     * Morocco
     */
    MAR(504),
    /**
     * Mozambique
     */
    MOZ(508),
    /**
     * Oman
     */
    OMN(512),
    /**
     * Namibia
     */
    NAM(516),
    /**
     * Nauru
     */
    NRU(520),
    /**
     * Nepal
     */
    NPL(524),
    /**
     * Netherlands (the)
     */
    NLD(528),
    /**
     * Curacao
     */
    CUW(531),
    /**
     * Aruba
     */
    ABW(533),
    /**
     * Sint Maarten (Dutch part)
     */
    SXM(534),
    /**
     * Bonaire, Sint Eustatius and Saba
     */
    BES(535),
    /**
     * New Caledonia
     */
    NCL(540),
    /**
     * Vanuatu
     */
    VUT(548),
    /**
     * New Zealand
     */
    NZL(554),
    /**
     * Nicaragua
     */
    NIC(558),
    /**
     * Niger
     */
    NER(562),
    /**
     * Nigeria
     */
    NGA(566),
    /**
     * Niue
     */
    NIU(570),
    /**
     * Norfolk Island
     */
    NFK(574),
    /**
     * Norway
     */
    NOR(578),
    /**
     * Northern Mariana Islands
     */
    MNP(580),
    /**
     * United States Minor Outlying Islands
     */
    UMI(581),
    /**
     * Micronesia, Federated States of
     */
    FSM(583),
    /**
     * Marshall Islands
     */
    MHL(584),
    /**
     * Palau
     */
    PLW(585),
    /**
     * Pakistan
     */
    PAK(586),
    /**
     * Panama
     */
    PAN(591),
    /**
     * Papua New Guinea
     */
    PNG(598),
    /**
     * Paraguay
     */
    PRY(600),
    /**
     * Peru
     */
    PER(604),
    /**
     * Philippines
     */
    PHL(608),
    /**
     * Pitcairn
     */
    PCN(612),
    /**
     * Poland
     */
    POL(616),
    /**
     * Portugal
     */
    PRT(620),
    /**
     * Guinea-Bissau
     */
    GNB(624),
    /**
     * Timor-Leste
     */
    TLS(626),
    /**
     * Puerto Rico
     */
    PRI(630),
    /**
     * Qatar
     */
    QAT(634),
    /**
     * Réunion
     */
    REU(638),
    /**
     * Romania
     */
    ROU(642),
    /**
     * Russian Federation
     */
    RUS(643),
    /**
     * Rwanda
     */
    RWA(646),
    /**
     * Saint Barthélemy
     */
    BLM(652),
    /**
     * Saint Helena, Ascension and Tristan da Cunha
     */
    SHN(654),
    /**
     * Saint Kitts and Nevis
     */
    KNA(659),
    /**
     * Anguilla
     */
    AIA(660),
    /**
     * Saint Lucia
     */
    LCA(662),
    /**
     * Saint Martin (French part)
     */
    MAF(663),
    /**
     * Saint Pierre and Miquelon
     */
    SPM(666),
    /**
     * Saint Vincent and the Grenadines
     */
    VCT(670),
    /**
     * San Marino
     */
    SMR(674),
    /**
     * Sao Tome and Principe
     */
    STP(678),
    /**
     * Saudi Arabia
     */
    SAU(682),
    /**
     * Senegal
     */
    SEN(686),
    /**
     * Serbia
     */
    SRB(688),
    /**
     * Seychelles
     */
    SYC(690),
    /**
     * Sierra Leone
     */
    SLE(694),
    /**
     * Singapore
     */
    SGP(702),
    /**
     * Slovakia
     */
    SVK(703),
    /**
     * Viet Nam
     */
    VNM(704),
    /**
     * Slovenia
     */
    SVN(705),
    /**
     * Somalia
     */
    SOM(706),
    /**
     * South Africa
     */
    ZAF(710),
    /**
     * Zimbabwe
     */
    ZWE(716),
    /**
     * Spain
     */
    ESP(724),
    /**
     * South Sudan
     */
    SSD(728),
    /**
     * Sudan
     */
    SDN(729),
    /**
     * Western Sahara
     */
    ESH(732),
    /**
     * Suriname
     */
    SUR(740),
    /**
     * Svalbard and Jan Mayen
     */
    SJM(744),
    /**
     * Eswatini
     */
    SWZ(748),
    /**
     * Sweden
     */
    SWE(752),
    /**
     * Switzerland
     */
    CHE(756),
    /**
     * Syrian Arab Republic
     */
    SYR(760),
    /**
     * Tajikistan
     */
    TJK(762),
    /**
     * Thailand
     */
    THA(764),
    /**
     * Togo
     */
    TGO(768),
    /**
     * Tokelau
     */
    TKL(772),
    /**
     * Tonga
     */
    TON(776),
    /**
     * Trinidad and Tobago
     */
    TTO(780),
    /**
     * United Arab Emirates
     */
    ARE(784),
    /**
     * Tunisia
     */
    TUN(788),
    /**
     * Türkiye
     */
    TUR(792),
    /**
     * Turkmenistan
     */
    TKM(795),
    /**
     * Turks and Caicos Islands
     */
    TCA(796),
    /**
     * Tuvalu
     */
    TUV(798),
    /**
     * Uganda
     */
    UGA(800),
    /**
     * Ukraine
     */
    UKR(804),
    /**
     * North Macedonia
     */
    MKD(807),
    /**
     * Egypt
     */
    EGY(818),
    /**
     * United Kingdom of Great Britain and Northern Ireland (the)
     */
    GBR(826),
    /**
     * Guernsey
     */
    GGY(831),
    /**
     * Jersey
     */
    JEY(832),
    /**
     * Isle of Man
     */
    IMN(833),
    /**
     * Tanzania, United Republic of
     */
    TZA(834),
    /**
     * United States of America (the)
     */
    USA(840),
    /**
     * Virgin Islands (U.S.)
     */
    VIR(850),
    /**
     * Burkina Faso
     */
    BFA(854),
    /**
     * Uruguay
     */
    URY(858),
    /**
     * Uzbekistan
     */
    UZB(860),
    /**
     * Venezuela
     */
    VEN(862),
    /**
     * Wallis and Futuna
     */
    WLF(876),
    /**
     * Samoa
     */
    WSM(882),
    /**
     * Yemen
     */
    YEM(887),
    /**
     * Zambia
     */
    ZMB(894);
    
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
     * Get Country by attribute
     * @param val
     * @return Country enum or null if variant is undefined
     */
    public static Country getCountry(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get Country int value
     * @return int value
     */
    public int getCountryValue() {
        return value; 
    }
    
    /**
     * Get Country from bytes
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