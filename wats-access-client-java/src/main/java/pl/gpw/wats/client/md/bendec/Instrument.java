package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>Instrument</h2>
 * <p>Definition of a financial instrument.</p>
 * <p>Byte length: 557</p>
 * <p>Header header - Message header. | size 39</p>
 * <p>ElementId > long (u32) instrumentId - ID of financial instrument. | size 4</p>
 * <p>ProductType productType - Type of the product. | size 1</p>
 * <p>Date > long (u32) firstTradingDate - First trading day of the financial instrument. | size 4</p>
 * <p>Date > long (u32) lastTradingDate - Last trading day of the financial instrument. | size 4</p>
 * <p>ElementId > long (u32) productId - Reference to financial product definition. | size 4</p>
 * <p>Currency currency - Trading currency (e.g. USD). | size 2</p>
 * <p>LotSize > long (u32) lotSize - Lot size for the instrument. | size 4</p>
 * <p>PriceExpressionType priceExpressionType - Price expression type for the financial instrument. | size 1</p>
 * <p>ElementId > long (u32) calendarId - ID of trading calendar for the financial instrument. | size 4</p>
 * <p>ElementId > long (u32) marketStructureId - ID of the financial instrument’s market segment. | size 4</p>
 * <p>ElementId > long (u32) tickTableId - ID of the tick table used for the financial instrument. | size 4</p>
 * <p>ElementId > long (u32) collarGroupId - Collar group ID. | size 4</p>
 * <p>ElementId > long (u32) tradingScheduleId - Trading schedule ID. | size 4</p>
 * <p>InstrumentCode > String (u8[]) code - Financial instrument code. | size 64</p>
 * <p>MicCode > String (u8[]) mic - Market structure's Market Identifier Code (MIC) as specified in ISO 10383. | size 4</p>
 * <p>Price > long (i64) nominalValue - Nominal value of the financial instrument. | size 8</p>
 * <p>NominalValueType nominalValueType - Type of the product's nominal value (no nominal, constant or unknown). | size 1</p>
 * <p>Multiplier > long (i64) multiplier - Product value multiplier. | size 8</p>
 * <p>Price > long (i64) strikePrice - Product strike price. | size 8</p>
 * <p>ProductIdentification > String (u8[]) productIdentification - Product identification, e.g. ISIN number. | size 30</p>
 * <p>ProductIdentificationType productIdentificationType - Type of product identification. | size 1</p>
 * <p>ElementId > long (u32) settlementCalendarId - Settlement calendar ID. | size 4</p>
 * <p>CouponType bondCouponType - Coupon type for a bond. | size 1</p>
 * <p>Price > long (i64) preTradeCheckMinPrice - Minimum order price for pre-trade check. | size 8</p>
 * <p>Price > long (i64) preTradeCheckMaxPrice - Maximum order price for pre-trade check. | size 8</p>
 * <p>Quantity > BigInteger (u64) preTradeCheckMinQuantity - Minimum volume for pre-trade check. | size 8</p>
 * <p>Quantity > BigInteger (u64) preTradeCheckMaxQuantity - Maximum volume for pre-trade check. | size 8</p>
 * <p>Value > long (i64) preTradeCheckMaxValue - Maximum value for pre-trade check. | size 8</p>
 * <p>Value > long (i64) preTradeCheckMinValue - Minimum value for pre-trade check. | size 8</p>
 * <p>bool > boolean liquidity - Liquidity flag, true - liquid, false - Illiquid. | size 1</p>
 * <p>MarketModelType marketModelType - Market Model. | size 1</p>
 * <p>Issuer > String (u8[]) issuer - Name of the issuer. | size 150</p>
 * <p>Country issuerRegCountry - Identifier of the country of the registration. | size 2</p>
 * <p>ElementId > long (u32) underlyingInstrumentId - Identifier of the underlying instrument. | size 4</p>
 * <p>Code > String (u8[]) productCode - Unique code of the product. | size 16</p>
 * <p>CfiCode > String (u8[]) cfi - CFI (Classification of Financial Instrument) code. | size 6</p>
 * <p>FisnCode > String (u8[]) fisn - Product FISN (Financial Instrument Short Name) code. | size 35</p>
 * <p>u64 > BigInteger issueSize - Size of the issue (depends on the issue size type) | size 8</p>
 * <p>Currency nominalCurrency - Identifier of the nominal currency (ISO-4217). | size 2</p>
 * <p>UsIndicator usIndicator - Identifier of the us indicator. | size 1</p>
 * <p>Date > long (u32) expiryDate - Expiration date. | size 4</p>
 * <p>u8 > int versionNumber - Version of the derivative. Version is incremented when corporate event adjusting underlying closing price occurs. | size 1</p>
 * <p>SettlementType settlementType - Identifier of settlement type. | size 1</p>
 * <p>OptionType optionType - Identifier of option type. | size 1</p>
 * <p>ExcerciseType excerciseType - Identifier of exercise style. | size 1</p>
 * <p>Name > String (u8[]) productName - Name of the product. | size 50</p>
 * <p>Price > long (i64) referencePrice - Reference price | size 8</p>
 * <p>InstrumentStatus status - Instrument status. | size 1</p>
 * <p>ElementId > long (u32) initialPhaseId - Id of starting phase for the instrument | size 4</p>
 * */

public class Instrument implements ByteSerializable, Message {

    private Header header;
    private long instrumentId;
    private ProductType productType;
    private long firstTradingDate;
    private long lastTradingDate;
    private long productId;
    private Currency currency;
    private long lotSize;
    private PriceExpressionType priceExpressionType;
    private long calendarId;
    private long marketStructureId;
    private long tickTableId;
    private long collarGroupId;
    private long tradingScheduleId;
    private String code;
    private String mic;
    private long nominalValue;
    private NominalValueType nominalValueType;
    private long multiplier;
    private long strikePrice;
    private String productIdentification;
    private ProductIdentificationType productIdentificationType;
    private long settlementCalendarId;
    private CouponType bondCouponType;
    private long preTradeCheckMinPrice;
    private long preTradeCheckMaxPrice;
    private BigInteger preTradeCheckMinQuantity;
    private BigInteger preTradeCheckMaxQuantity;
    private long preTradeCheckMaxValue;
    private long preTradeCheckMinValue;
    private boolean liquidity;
    private MarketModelType marketModelType;
    private String issuer;
    private Country issuerRegCountry;
    private long underlyingInstrumentId;
    private String productCode;
    private String cfi;
    private String fisn;
    private BigInteger issueSize;
    private Currency nominalCurrency;
    private UsIndicator usIndicator;
    private long expiryDate;
    private int versionNumber;
    private SettlementType settlementType;
    private OptionType optionType;
    private ExcerciseType excerciseType;
    private String productName;
    private long referencePrice;
    private InstrumentStatus status;
    private long initialPhaseId;
    public static final int byteLength = 557;

    public Instrument(Header header, long instrumentId, ProductType productType, long firstTradingDate, long lastTradingDate, long productId, Currency currency, long lotSize, PriceExpressionType priceExpressionType, long calendarId, long marketStructureId, long tickTableId, long collarGroupId, long tradingScheduleId, String code, String mic, long nominalValue, NominalValueType nominalValueType, long multiplier, long strikePrice, String productIdentification, ProductIdentificationType productIdentificationType, long settlementCalendarId, CouponType bondCouponType, long preTradeCheckMinPrice, long preTradeCheckMaxPrice, BigInteger preTradeCheckMinQuantity, BigInteger preTradeCheckMaxQuantity, long preTradeCheckMaxValue, long preTradeCheckMinValue, boolean liquidity, MarketModelType marketModelType, String issuer, Country issuerRegCountry, long underlyingInstrumentId, String productCode, String cfi, String fisn, BigInteger issueSize, Currency nominalCurrency, UsIndicator usIndicator, long expiryDate, int versionNumber, SettlementType settlementType, OptionType optionType, ExcerciseType excerciseType, String productName, long referencePrice, InstrumentStatus status, long initialPhaseId) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.productType = productType;
        this.firstTradingDate = firstTradingDate;
        this.lastTradingDate = lastTradingDate;
        this.productId = productId;
        this.currency = currency;
        this.lotSize = lotSize;
        this.priceExpressionType = priceExpressionType;
        this.calendarId = calendarId;
        this.marketStructureId = marketStructureId;
        this.tickTableId = tickTableId;
        this.collarGroupId = collarGroupId;
        this.tradingScheduleId = tradingScheduleId;
        this.code = code;
        this.mic = mic;
        this.nominalValue = nominalValue;
        this.nominalValueType = nominalValueType;
        this.multiplier = multiplier;
        this.strikePrice = strikePrice;
        this.productIdentification = productIdentification;
        this.productIdentificationType = productIdentificationType;
        this.settlementCalendarId = settlementCalendarId;
        this.bondCouponType = bondCouponType;
        this.preTradeCheckMinPrice = preTradeCheckMinPrice;
        this.preTradeCheckMaxPrice = preTradeCheckMaxPrice;
        this.preTradeCheckMinQuantity = preTradeCheckMinQuantity;
        this.preTradeCheckMaxQuantity = preTradeCheckMaxQuantity;
        this.preTradeCheckMaxValue = preTradeCheckMaxValue;
        this.preTradeCheckMinValue = preTradeCheckMinValue;
        this.liquidity = liquidity;
        this.marketModelType = marketModelType;
        this.issuer = issuer;
        this.issuerRegCountry = issuerRegCountry;
        this.underlyingInstrumentId = underlyingInstrumentId;
        this.productCode = productCode;
        this.cfi = cfi;
        this.fisn = fisn;
        this.issueSize = issueSize;
        this.nominalCurrency = nominalCurrency;
        this.usIndicator = usIndicator;
        this.expiryDate = expiryDate;
        this.versionNumber = versionNumber;
        this.settlementType = settlementType;
        this.optionType = optionType;
        this.excerciseType = excerciseType;
        this.productName = productName;
        this.referencePrice = referencePrice;
        this.status = status;
        this.initialPhaseId = initialPhaseId;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.INSTRUMENT);
    }

    public Instrument(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 39);
        this.productType = ProductType.getProductType(bytes, offset + 43);
        this.firstTradingDate = BendecUtils.uInt32FromByteArray(bytes, offset + 44);
        this.lastTradingDate = BendecUtils.uInt32FromByteArray(bytes, offset + 48);
        this.productId = BendecUtils.uInt32FromByteArray(bytes, offset + 52);
        this.currency = Currency.getCurrency(bytes, offset + 56);
        this.lotSize = BendecUtils.uInt32FromByteArray(bytes, offset + 58);
        this.priceExpressionType = PriceExpressionType.getPriceExpressionType(bytes, offset + 62);
        this.calendarId = BendecUtils.uInt32FromByteArray(bytes, offset + 63);
        this.marketStructureId = BendecUtils.uInt32FromByteArray(bytes, offset + 67);
        this.tickTableId = BendecUtils.uInt32FromByteArray(bytes, offset + 71);
        this.collarGroupId = BendecUtils.uInt32FromByteArray(bytes, offset + 75);
        this.tradingScheduleId = BendecUtils.uInt32FromByteArray(bytes, offset + 79);
        this.code = BendecUtils.stringFromByteArray(bytes, offset + 83, 64);
        this.mic = BendecUtils.stringFromByteArray(bytes, offset + 147, 4);
        this.nominalValue = BendecUtils.int64FromByteArray(bytes, offset + 151);
        this.nominalValueType = NominalValueType.getNominalValueType(bytes, offset + 159);
        this.multiplier = BendecUtils.int64FromByteArray(bytes, offset + 160);
        this.strikePrice = BendecUtils.int64FromByteArray(bytes, offset + 168);
        this.productIdentification = BendecUtils.stringFromByteArray(bytes, offset + 176, 30);
        this.productIdentificationType = ProductIdentificationType.getProductIdentificationType(bytes, offset + 206);
        this.settlementCalendarId = BendecUtils.uInt32FromByteArray(bytes, offset + 207);
        this.bondCouponType = CouponType.getCouponType(bytes, offset + 211);
        this.preTradeCheckMinPrice = BendecUtils.int64FromByteArray(bytes, offset + 212);
        this.preTradeCheckMaxPrice = BendecUtils.int64FromByteArray(bytes, offset + 220);
        this.preTradeCheckMinQuantity = BendecUtils.uInt64FromByteArray(bytes, offset + 228);
        this.preTradeCheckMaxQuantity = BendecUtils.uInt64FromByteArray(bytes, offset + 236);
        this.preTradeCheckMaxValue = BendecUtils.int64FromByteArray(bytes, offset + 244);
        this.preTradeCheckMinValue = BendecUtils.int64FromByteArray(bytes, offset + 252);
        this.liquidity = BendecUtils.booleanFromByteArray(bytes, offset + 260);
        this.marketModelType = MarketModelType.getMarketModelType(bytes, offset + 261);
        this.issuer = BendecUtils.stringFromByteArray(bytes, offset + 262, 150);
        this.issuerRegCountry = Country.getCountry(bytes, offset + 412);
        this.underlyingInstrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 414);
        this.productCode = BendecUtils.stringFromByteArray(bytes, offset + 418, 16);
        this.cfi = BendecUtils.stringFromByteArray(bytes, offset + 434, 6);
        this.fisn = BendecUtils.stringFromByteArray(bytes, offset + 440, 35);
        this.issueSize = BendecUtils.uInt64FromByteArray(bytes, offset + 475);
        this.nominalCurrency = Currency.getCurrency(bytes, offset + 483);
        this.usIndicator = UsIndicator.getUsIndicator(bytes, offset + 485);
        this.expiryDate = BendecUtils.uInt32FromByteArray(bytes, offset + 486);
        this.versionNumber = BendecUtils.uInt8FromByteArray(bytes, offset + 490);
        this.settlementType = SettlementType.getSettlementType(bytes, offset + 491);
        this.optionType = OptionType.getOptionType(bytes, offset + 492);
        this.excerciseType = ExcerciseType.getExcerciseType(bytes, offset + 493);
        this.productName = BendecUtils.stringFromByteArray(bytes, offset + 494, 50);
        this.referencePrice = BendecUtils.int64FromByteArray(bytes, offset + 544);
        this.status = InstrumentStatus.getInstrumentStatus(bytes, offset + 552);
        this.initialPhaseId = BendecUtils.uInt32FromByteArray(bytes, offset + 553);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.INSTRUMENT);
    }

    public Instrument(byte[] bytes) {
        this(bytes, 0);
    }

    public Instrument() {
    }



    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return ID of financial instrument.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    };
    /**
     * @return Type of the product.
     */
    public ProductType getProductType() {
        return this.productType;
    };
    /**
     * @return First trading day of the financial instrument.
     */
    public long getFirstTradingDate() {
        return this.firstTradingDate;
    };
    /**
     * @return Last trading day of the financial instrument.
     */
    public long getLastTradingDate() {
        return this.lastTradingDate;
    };
    /**
     * @return Reference to financial product definition.
     */
    public long getProductId() {
        return this.productId;
    };
    /**
     * @return Trading currency (e.g. USD).
     */
    public Currency getCurrency() {
        return this.currency;
    };
    /**
     * @return Lot size for the instrument.
     */
    public long getLotSize() {
        return this.lotSize;
    };
    /**
     * @return Price expression type for the financial instrument.
     */
    public PriceExpressionType getPriceExpressionType() {
        return this.priceExpressionType;
    };
    /**
     * @return ID of trading calendar for the financial instrument.
     */
    public long getCalendarId() {
        return this.calendarId;
    };
    /**
     * @return ID of the financial instrument’s market segment.
     */
    public long getMarketStructureId() {
        return this.marketStructureId;
    };
    /**
     * @return ID of the tick table used for the financial instrument.
     */
    public long getTickTableId() {
        return this.tickTableId;
    };
    /**
     * @return Collar group ID.
     */
    public long getCollarGroupId() {
        return this.collarGroupId;
    };
    /**
     * @return Trading schedule ID.
     */
    public long getTradingScheduleId() {
        return this.tradingScheduleId;
    };
    /**
     * @return Financial instrument code.
     */
    public String getCode() {
        return this.code;
    };
    /**
     * @return Market structure's Market Identifier Code (MIC) as specified in ISO 10383.
     */
    public String getMic() {
        return this.mic;
    };
    /**
     * @return Nominal value of the financial instrument.
     */
    public long getNominalValue() {
        return this.nominalValue;
    };
    /**
     * @return Type of the product's nominal value (no nominal, constant or unknown).
     */
    public NominalValueType getNominalValueType() {
        return this.nominalValueType;
    };
    /**
     * @return Product value multiplier.
     */
    public long getMultiplier() {
        return this.multiplier;
    };
    /**
     * @return Product strike price.
     */
    public long getStrikePrice() {
        return this.strikePrice;
    };
    /**
     * @return Product identification, e.g. ISIN number.
     */
    public String getProductIdentification() {
        return this.productIdentification;
    };
    /**
     * @return Type of product identification.
     */
    public ProductIdentificationType getProductIdentificationType() {
        return this.productIdentificationType;
    };
    /**
     * @return Settlement calendar ID.
     */
    public long getSettlementCalendarId() {
        return this.settlementCalendarId;
    };
    /**
     * @return Coupon type for a bond.
     */
    public CouponType getBondCouponType() {
        return this.bondCouponType;
    };
    /**
     * @return Minimum order price for pre-trade check.
     */
    public long getPreTradeCheckMinPrice() {
        return this.preTradeCheckMinPrice;
    };
    /**
     * @return Maximum order price for pre-trade check.
     */
    public long getPreTradeCheckMaxPrice() {
        return this.preTradeCheckMaxPrice;
    };
    /**
     * @return Minimum volume for pre-trade check.
     */
    public BigInteger getPreTradeCheckMinQuantity() {
        return this.preTradeCheckMinQuantity;
    };
    /**
     * @return Maximum volume for pre-trade check.
     */
    public BigInteger getPreTradeCheckMaxQuantity() {
        return this.preTradeCheckMaxQuantity;
    };
    /**
     * @return Maximum value for pre-trade check.
     */
    public long getPreTradeCheckMaxValue() {
        return this.preTradeCheckMaxValue;
    };
    /**
     * @return Minimum value for pre-trade check.
     */
    public long getPreTradeCheckMinValue() {
        return this.preTradeCheckMinValue;
    };
    /**
     * @return Liquidity flag, true - liquid, false - Illiquid.
     */
    public boolean getLiquidity() {
        return this.liquidity;
    };
    /**
     * @return Market Model.
     */
    public MarketModelType getMarketModelType() {
        return this.marketModelType;
    };
    /**
     * @return Name of the issuer.
     */
    public String getIssuer() {
        return this.issuer;
    };
    /**
     * @return Identifier of the country of the registration.
     */
    public Country getIssuerRegCountry() {
        return this.issuerRegCountry;
    };
    /**
     * @return Identifier of the underlying instrument.
     */
    public long getUnderlyingInstrumentId() {
        return this.underlyingInstrumentId;
    };
    /**
     * @return Unique code of the product.
     */
    public String getProductCode() {
        return this.productCode;
    };
    /**
     * @return CFI (Classification of Financial Instrument) code.
     */
    public String getCfi() {
        return this.cfi;
    };
    /**
     * @return Product FISN (Financial Instrument Short Name) code.
     */
    public String getFisn() {
        return this.fisn;
    };
    /**
     * @return Size of the issue (depends on the issue size type)
     */
    public BigInteger getIssueSize() {
        return this.issueSize;
    };
    /**
     * @return Identifier of the nominal currency (ISO-4217).
     */
    public Currency getNominalCurrency() {
        return this.nominalCurrency;
    };
    /**
     * @return Identifier of the us indicator.
     */
    public UsIndicator getUsIndicator() {
        return this.usIndicator;
    };
    /**
     * @return Expiration date.
     */
    public long getExpiryDate() {
        return this.expiryDate;
    };
    /**
     * @return Version of the derivative. Version is incremented when corporate event adjusting underlying closing price occurs.
     */
    public int getVersionNumber() {
        return this.versionNumber;
    };
    /**
     * @return Identifier of settlement type.
     */
    public SettlementType getSettlementType() {
        return this.settlementType;
    };
    /**
     * @return Identifier of option type.
     */
    public OptionType getOptionType() {
        return this.optionType;
    };
    /**
     * @return Identifier of exercise style.
     */
    public ExcerciseType getExcerciseType() {
        return this.excerciseType;
    };
    /**
     * @return Name of the product.
     */
    public String getProductName() {
        return this.productName;
    };
    /**
     * @return Reference price
     */
    public long getReferencePrice() {
        return this.referencePrice;
    };
    /**
     * @return Instrument status.
     */
    public InstrumentStatus getStatus() {
        return this.status;
    };
    /**
     * @return Id of starting phase for the instrument
     */
    public long getInitialPhaseId() {
        return this.initialPhaseId;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param instrumentId ID of financial instrument.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    };
    /**
     * @param productType Type of the product.
     */
    public void setProductType(ProductType productType) {
        this.productType = productType;
    };
    /**
     * @param firstTradingDate First trading day of the financial instrument.
     */
    public void setFirstTradingDate(long firstTradingDate) {
        this.firstTradingDate = firstTradingDate;
    };
    /**
     * @param lastTradingDate Last trading day of the financial instrument.
     */
    public void setLastTradingDate(long lastTradingDate) {
        this.lastTradingDate = lastTradingDate;
    };
    /**
     * @param productId Reference to financial product definition.
     */
    public void setProductId(long productId) {
        this.productId = productId;
    };
    /**
     * @param currency Trading currency (e.g. USD).
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    };
    /**
     * @param lotSize Lot size for the instrument.
     */
    public void setLotSize(long lotSize) {
        this.lotSize = lotSize;
    };
    /**
     * @param priceExpressionType Price expression type for the financial instrument.
     */
    public void setPriceExpressionType(PriceExpressionType priceExpressionType) {
        this.priceExpressionType = priceExpressionType;
    };
    /**
     * @param calendarId ID of trading calendar for the financial instrument.
     */
    public void setCalendarId(long calendarId) {
        this.calendarId = calendarId;
    };
    /**
     * @param marketStructureId ID of the financial instrument’s market segment.
     */
    public void setMarketStructureId(long marketStructureId) {
        this.marketStructureId = marketStructureId;
    };
    /**
     * @param tickTableId ID of the tick table used for the financial instrument.
     */
    public void setTickTableId(long tickTableId) {
        this.tickTableId = tickTableId;
    };
    /**
     * @param collarGroupId Collar group ID.
     */
    public void setCollarGroupId(long collarGroupId) {
        this.collarGroupId = collarGroupId;
    };
    /**
     * @param tradingScheduleId Trading schedule ID.
     */
    public void setTradingScheduleId(long tradingScheduleId) {
        this.tradingScheduleId = tradingScheduleId;
    };
    /**
     * @param code Financial instrument code.
     */
    public void setCode(String code) {
        this.code = code;
    };
    /**
     * @param mic Market structure's Market Identifier Code (MIC) as specified in ISO 10383.
     */
    public void setMic(String mic) {
        this.mic = mic;
    };
    /**
     * @param nominalValue Nominal value of the financial instrument.
     */
    public void setNominalValue(long nominalValue) {
        this.nominalValue = nominalValue;
    };
    /**
     * @param nominalValueType Type of the product's nominal value (no nominal, constant or unknown).
     */
    public void setNominalValueType(NominalValueType nominalValueType) {
        this.nominalValueType = nominalValueType;
    };
    /**
     * @param multiplier Product value multiplier.
     */
    public void setMultiplier(long multiplier) {
        this.multiplier = multiplier;
    };
    /**
     * @param strikePrice Product strike price.
     */
    public void setStrikePrice(long strikePrice) {
        this.strikePrice = strikePrice;
    };
    /**
     * @param productIdentification Product identification, e.g. ISIN number.
     */
    public void setProductIdentification(String productIdentification) {
        this.productIdentification = productIdentification;
    };
    /**
     * @param productIdentificationType Type of product identification.
     */
    public void setProductIdentificationType(ProductIdentificationType productIdentificationType) {
        this.productIdentificationType = productIdentificationType;
    };
    /**
     * @param settlementCalendarId Settlement calendar ID.
     */
    public void setSettlementCalendarId(long settlementCalendarId) {
        this.settlementCalendarId = settlementCalendarId;
    };
    /**
     * @param bondCouponType Coupon type for a bond.
     */
    public void setBondCouponType(CouponType bondCouponType) {
        this.bondCouponType = bondCouponType;
    };
    /**
     * @param preTradeCheckMinPrice Minimum order price for pre-trade check.
     */
    public void setPreTradeCheckMinPrice(long preTradeCheckMinPrice) {
        this.preTradeCheckMinPrice = preTradeCheckMinPrice;
    };
    /**
     * @param preTradeCheckMaxPrice Maximum order price for pre-trade check.
     */
    public void setPreTradeCheckMaxPrice(long preTradeCheckMaxPrice) {
        this.preTradeCheckMaxPrice = preTradeCheckMaxPrice;
    };
    /**
     * @param preTradeCheckMinQuantity Minimum volume for pre-trade check.
     */
    public void setPreTradeCheckMinQuantity(BigInteger preTradeCheckMinQuantity) {
        this.preTradeCheckMinQuantity = preTradeCheckMinQuantity;
    };
    /**
     * @param preTradeCheckMaxQuantity Maximum volume for pre-trade check.
     */
    public void setPreTradeCheckMaxQuantity(BigInteger preTradeCheckMaxQuantity) {
        this.preTradeCheckMaxQuantity = preTradeCheckMaxQuantity;
    };
    /**
     * @param preTradeCheckMaxValue Maximum value for pre-trade check.
     */
    public void setPreTradeCheckMaxValue(long preTradeCheckMaxValue) {
        this.preTradeCheckMaxValue = preTradeCheckMaxValue;
    };
    /**
     * @param preTradeCheckMinValue Minimum value for pre-trade check.
     */
    public void setPreTradeCheckMinValue(long preTradeCheckMinValue) {
        this.preTradeCheckMinValue = preTradeCheckMinValue;
    };
    /**
     * @param liquidity Liquidity flag, true - liquid, false - Illiquid.
     */
    public void setLiquidity(boolean liquidity) {
        this.liquidity = liquidity;
    };
    /**
     * @param marketModelType Market Model.
     */
    public void setMarketModelType(MarketModelType marketModelType) {
        this.marketModelType = marketModelType;
    };
    /**
     * @param issuer Name of the issuer.
     */
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    };
    /**
     * @param issuerRegCountry Identifier of the country of the registration.
     */
    public void setIssuerRegCountry(Country issuerRegCountry) {
        this.issuerRegCountry = issuerRegCountry;
    };
    /**
     * @param underlyingInstrumentId Identifier of the underlying instrument.
     */
    public void setUnderlyingInstrumentId(long underlyingInstrumentId) {
        this.underlyingInstrumentId = underlyingInstrumentId;
    };
    /**
     * @param productCode Unique code of the product.
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    };
    /**
     * @param cfi CFI (Classification of Financial Instrument) code.
     */
    public void setCfi(String cfi) {
        this.cfi = cfi;
    };
    /**
     * @param fisn Product FISN (Financial Instrument Short Name) code.
     */
    public void setFisn(String fisn) {
        this.fisn = fisn;
    };
    /**
     * @param issueSize Size of the issue (depends on the issue size type)
     */
    public void setIssueSize(BigInteger issueSize) {
        this.issueSize = issueSize;
    };
    /**
     * @param nominalCurrency Identifier of the nominal currency (ISO-4217).
     */
    public void setNominalCurrency(Currency nominalCurrency) {
        this.nominalCurrency = nominalCurrency;
    };
    /**
     * @param usIndicator Identifier of the us indicator.
     */
    public void setUsIndicator(UsIndicator usIndicator) {
        this.usIndicator = usIndicator;
    };
    /**
     * @param expiryDate Expiration date.
     */
    public void setExpiryDate(long expiryDate) {
        this.expiryDate = expiryDate;
    };
    /**
     * @param versionNumber Version of the derivative. Version is incremented when corporate event adjusting underlying closing price occurs.
     */
    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    };
    /**
     * @param settlementType Identifier of settlement type.
     */
    public void setSettlementType(SettlementType settlementType) {
        this.settlementType = settlementType;
    };
    /**
     * @param optionType Identifier of option type.
     */
    public void setOptionType(OptionType optionType) {
        this.optionType = optionType;
    };
    /**
     * @param excerciseType Identifier of exercise style.
     */
    public void setExcerciseType(ExcerciseType excerciseType) {
        this.excerciseType = excerciseType;
    };
    /**
     * @param productName Name of the product.
     */
    public void setProductName(String productName) {
        this.productName = productName;
    };
    /**
     * @param referencePrice Reference price
     */
    public void setReferencePrice(long referencePrice) {
        this.referencePrice = referencePrice;
    };
    /**
     * @param status Instrument status.
     */
    public void setStatus(InstrumentStatus status) {
        this.status = status;
    };
    /**
     * @param initialPhaseId Id of starting phase for the instrument
     */
    public void setInitialPhaseId(long initialPhaseId) {
        this.initialPhaseId = initialPhaseId;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        productType.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.firstTradingDate));
        buffer.put(BendecUtils.uInt32ToByteArray(this.lastTradingDate));
        buffer.put(BendecUtils.uInt32ToByteArray(this.productId));
        currency.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.lotSize));
        priceExpressionType.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.calendarId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.marketStructureId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tickTableId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.collarGroupId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradingScheduleId));
        buffer.put(BendecUtils.stringToByteArray(this.code, 64));
        buffer.put(BendecUtils.stringToByteArray(this.mic, 4));
        buffer.put(BendecUtils.int64ToByteArray(this.nominalValue));
        nominalValueType.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.multiplier));
        buffer.put(BendecUtils.int64ToByteArray(this.strikePrice));
        buffer.put(BendecUtils.stringToByteArray(this.productIdentification, 30));
        productIdentificationType.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.settlementCalendarId));
        bondCouponType.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.preTradeCheckMinPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.preTradeCheckMaxPrice));
        buffer.put(BendecUtils.uInt64ToByteArray(this.preTradeCheckMinQuantity));
        buffer.put(BendecUtils.uInt64ToByteArray(this.preTradeCheckMaxQuantity));
        buffer.put(BendecUtils.int64ToByteArray(this.preTradeCheckMaxValue));
        buffer.put(BendecUtils.int64ToByteArray(this.preTradeCheckMinValue));
        buffer.put(BendecUtils.booleanToByteArray(this.liquidity));
        marketModelType.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.issuer, 150));
        issuerRegCountry.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.underlyingInstrumentId));
        buffer.put(BendecUtils.stringToByteArray(this.productCode, 16));
        buffer.put(BendecUtils.stringToByteArray(this.cfi, 6));
        buffer.put(BendecUtils.stringToByteArray(this.fisn, 35));
        buffer.put(BendecUtils.uInt64ToByteArray(this.issueSize));
        nominalCurrency.toBytes(buffer);
        usIndicator.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.expiryDate));
        buffer.put(BendecUtils.uInt8ToByteArray(this.versionNumber));
        settlementType.toBytes(buffer);
        optionType.toBytes(buffer);
        excerciseType.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.productName, 50));
        buffer.put(BendecUtils.int64ToByteArray(this.referencePrice));
        status.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.initialPhaseId));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        productType.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.firstTradingDate));
        buffer.put(BendecUtils.uInt32ToByteArray(this.lastTradingDate));
        buffer.put(BendecUtils.uInt32ToByteArray(this.productId));
        currency.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.lotSize));
        priceExpressionType.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.calendarId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.marketStructureId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tickTableId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.collarGroupId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradingScheduleId));
        buffer.put(BendecUtils.stringToByteArray(this.code, 64));
        buffer.put(BendecUtils.stringToByteArray(this.mic, 4));
        buffer.put(BendecUtils.int64ToByteArray(this.nominalValue));
        nominalValueType.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.multiplier));
        buffer.put(BendecUtils.int64ToByteArray(this.strikePrice));
        buffer.put(BendecUtils.stringToByteArray(this.productIdentification, 30));
        productIdentificationType.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.settlementCalendarId));
        bondCouponType.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.preTradeCheckMinPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.preTradeCheckMaxPrice));
        buffer.put(BendecUtils.uInt64ToByteArray(this.preTradeCheckMinQuantity));
        buffer.put(BendecUtils.uInt64ToByteArray(this.preTradeCheckMaxQuantity));
        buffer.put(BendecUtils.int64ToByteArray(this.preTradeCheckMaxValue));
        buffer.put(BendecUtils.int64ToByteArray(this.preTradeCheckMinValue));
        buffer.put(BendecUtils.booleanToByteArray(this.liquidity));
        marketModelType.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.issuer, 150));
        issuerRegCountry.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.underlyingInstrumentId));
        buffer.put(BendecUtils.stringToByteArray(this.productCode, 16));
        buffer.put(BendecUtils.stringToByteArray(this.cfi, 6));
        buffer.put(BendecUtils.stringToByteArray(this.fisn, 35));
        buffer.put(BendecUtils.uInt64ToByteArray(this.issueSize));
        nominalCurrency.toBytes(buffer);
        usIndicator.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.expiryDate));
        buffer.put(BendecUtils.uInt8ToByteArray(this.versionNumber));
        settlementType.toBytes(buffer);
        optionType.toBytes(buffer);
        excerciseType.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.productName, 50));
        buffer.put(BendecUtils.int64ToByteArray(this.referencePrice));
        status.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.initialPhaseId));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, instrumentId, productType, firstTradingDate, lastTradingDate, productId, currency, lotSize, priceExpressionType, calendarId, marketStructureId, tickTableId, collarGroupId, tradingScheduleId, code, mic, nominalValue, nominalValueType, multiplier, strikePrice, productIdentification, productIdentificationType, settlementCalendarId, bondCouponType, preTradeCheckMinPrice, preTradeCheckMaxPrice, preTradeCheckMinQuantity, preTradeCheckMaxQuantity, preTradeCheckMaxValue, preTradeCheckMinValue, liquidity, marketModelType, issuer, issuerRegCountry, underlyingInstrumentId, productCode, cfi, fisn, issueSize, nominalCurrency, usIndicator, expiryDate, versionNumber, settlementType, optionType, excerciseType, productName, referencePrice, status, initialPhaseId);
    }

    @Override
    public String toString() {
        return "Instrument{" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", productType=" + productType +
            ", firstTradingDate=" + firstTradingDate +
            ", lastTradingDate=" + lastTradingDate +
            ", productId=" + productId +
            ", currency=" + currency +
            ", lotSize=" + lotSize +
            ", priceExpressionType=" + priceExpressionType +
            ", calendarId=" + calendarId +
            ", marketStructureId=" + marketStructureId +
            ", tickTableId=" + tickTableId +
            ", collarGroupId=" + collarGroupId +
            ", tradingScheduleId=" + tradingScheduleId +
            ", code=" + code +
            ", mic=" + mic +
            ", nominalValue=" + nominalValue +
            ", nominalValueType=" + nominalValueType +
            ", multiplier=" + multiplier +
            ", strikePrice=" + strikePrice +
            ", productIdentification=" + productIdentification +
            ", productIdentificationType=" + productIdentificationType +
            ", settlementCalendarId=" + settlementCalendarId +
            ", bondCouponType=" + bondCouponType +
            ", preTradeCheckMinPrice=" + preTradeCheckMinPrice +
            ", preTradeCheckMaxPrice=" + preTradeCheckMaxPrice +
            ", preTradeCheckMinQuantity=" + preTradeCheckMinQuantity +
            ", preTradeCheckMaxQuantity=" + preTradeCheckMaxQuantity +
            ", preTradeCheckMaxValue=" + preTradeCheckMaxValue +
            ", preTradeCheckMinValue=" + preTradeCheckMinValue +
            ", liquidity=" + liquidity +
            ", marketModelType=" + marketModelType +
            ", issuer=" + issuer +
            ", issuerRegCountry=" + issuerRegCountry +
            ", underlyingInstrumentId=" + underlyingInstrumentId +
            ", productCode=" + productCode +
            ", cfi=" + cfi +
            ", fisn=" + fisn +
            ", issueSize=" + issueSize +
            ", nominalCurrency=" + nominalCurrency +
            ", usIndicator=" + usIndicator +
            ", expiryDate=" + expiryDate +
            ", versionNumber=" + versionNumber +
            ", settlementType=" + settlementType +
            ", optionType=" + optionType +
            ", excerciseType=" + excerciseType +
            ", productName=" + productName +
            ", referencePrice=" + referencePrice +
            ", status=" + status +
            ", initialPhaseId=" + initialPhaseId +
            '}';
        }
}
