package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * Enum: ProductSubtype
 * Product Subtype.
 */
public enum ProductSubtype {
    /**
     * Share
     */
    SHARE(1),
    /**
     * Allotment Right
     */
    ALLOTMENTRIGHT(2),
    /**
     * Subscription Right
     */
    SUBSCRIPTIONRIGHT(3),
    /**
     * Tender Offer
     */
    TENDEROFFER(4),
    /**
     * Issue Right
     */
    ISSUERIGHT(5),
    /**
     * Subscription Warrant
     */
    SUBSCRIPTIONWARRANT(6),
    /**
     * Bank Securities
     */
    BANKSECURITIES(7),
    /**
     * Bond
     */
    BOND(8),
    /**
     * Treasury Bond
     */
    TREASURYBOND(9),
    /**
     * Bond issued by a government institution
     */
    AGENCYBOND(10),
    /**
     * Municipal Bond
     */
    MUNICIPALBOND(11),
    /**
     * Corporate Bond - bank
     */
    CORPORATEBANKBOND(12),
    /**
     * Corporate Bond - firm
     */
    CORPORATEFIRMBOND(13),
    /**
     * Convertible Bond
     */
    CONVERTIBLEBOND(14),
    /**
     * Mortgage Bond
     */
    MORTGAGEBOND(15),
    /**
     * Mortgage Backed Bond
     */
    MORTGAGEBACKEDBOND(16),
    /**
     * Public Mortgage Bond
     */
    PUBLICMORTGAGEBOND(17),
    /**
     * Bill
     */
    BILL(18),
    /**
     * Treasury Bill
     */
    TREASURYBILL(19),
    /**
     * Commercial Bill
     */
    COMMERCIALBILL(20),
    /**
     * ETF - Exchange Traded Fund
     */
    EXCHANGETRADEDFUND(21),
    /**
     * ETN - Exchange Traded Note
     */
    EXCHANGETRADEDNOTE(22),
    /**
     * ETC - Exchange Traded Commodity
     */
    EXCHANGETRADEDCOMMODITY(23),
    /**
     * Investment Certificate
     */
    INVESTMENTCERTIFICATE(24),
    /**
     * Index Futures
     */
    INDEXFUTURES(25),
    /**
     * Stock Futures
     */
    STOCKFUTURES(26),
    /**
     * Currency Futures
     */
    CURRENCYFUTURES(27),
    /**
     * Bond Futures
     */
    BONDFUTURES(28),
    /**
     * Interest Rate Futures
     */
    INTERESTRATEFUTURES(29),
    /**
     * Index Options
     */
    INDEXOPTIONS(30),
    /**
     * Stock Options
     */
    STOCKOPTIONS(31),
    /**
     * Option warrants
     */
    OPTIONWARRANTS(32),
    /**
     * Factor certificates
     */
    FACTORCERTIFICATES(33),
    /**
     * Turbo certificates
     */
    TURBOCERTIFICATES(34),
    /**
     * Capital protection certificates
     */
    CAPITALPROTECTIONCERTIFICATES(35),
    /**
     * Bonus certificates
     */
    BONUSCERTIFICATES(36),
    /**
     * Reverse convertible certificates
     */
    REVERSECONVERTIBLECERTIFICATES(37),
    /**
     * Express certificates
     */
    EXPRESSCERTIFICATES(38),
    /**
     * Discount certificates
     */
    DISCOUNTCERTIFICATES(39),
    /**
     * Index/tracker certificates
     */
    INDEXTRACKERCERTIFICATES(40),
    /**
     * Other investment certificates (non leveraged)
     */
    OTHERINVESTMENTCERTIFICATES(41),
    /**
     * Structured notes
     */
    STRUCTUREDNOTES(42),
    /**
     * Reference rate
     */
    REFERENCERATE(43),
    /**
     * Interest rate
     */
    INTERESTRATE(44),
    /**
     * Exchange rate
     */
    EXCHANGERATE(45),
    /**
     * Price index
     */
    PRICEINDEX(46),
    /**
     * Total return index
     */
    TOTALRETURNINDEX(47),
    /**
     * Sector price index
     */
    SECTORPRICEINDEX(48),
    /**
     * Sector total return index
     */
    SECTORTOTALRETURNINDEX(49),
    /**
     * Dividend index
     */
    DIVIDENDINDEX(50),
    /**
     * Strategy price index
     */
    STRATEGYPRICEINDEX(51),
    /**
     * Strategy total return index
     */
    STRATEGYTOTALRETURNINDEX(52),
    /**
     * Commodity index
     */
    COMMODITYINDEX(53),
    /**
     * Bond total return index
     */
    BONDTOTALRETURNINDEX(54),
    /**
     * Supranational Bond
     */
    SUPRANATIONALBOND(55),
    /**
     * Warrant with Knock Out
     */
    WARRANTWITHKNOCKOUT(56);
    
    private final int value;
    private final int byteLength = 1;
    
    private static final Map<Integer, ProductSubtype> TYPES = new HashMap<>();
    static {
        for (ProductSubtype type : ProductSubtype.values()) {
            TYPES.put(type.value, type);
        }
    }
    
    ProductSubtype(int newValue) {
        value = newValue;
    }
    
    /**
     * Get ProductSubtype by attribute
     * @param val
     * @return ProductSubtype enum or null if variant is undefined
     */
    public static ProductSubtype getProductSubtype(int val) {
        return TYPES.get(val);
    }
    
    /**
     * Get ProductSubtype int value
     * @return int value
     */
    public int getProductSubtypeValue() {
        return value; 
    }
    
    /**
     * Get ProductSubtype from bytes
     * @param bytes byte[]
     * @param offset - int
     */
    public static ProductSubtype getProductSubtype(byte[] bytes, int offset) {
        return getProductSubtype(BendecUtils.uInt8FromByteArray(bytes, offset));
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