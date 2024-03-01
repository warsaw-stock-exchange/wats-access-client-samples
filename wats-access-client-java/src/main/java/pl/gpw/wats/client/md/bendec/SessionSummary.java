package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>SessionSummary</h2>
 * <p>Session day instrument summary.</p>
 * <p>Byte length: 373</p>
 * <p>Header header - Message header. | size 39</p>
 * <p>ElementId > long (u32) instrumentId - Identifier of the CLOB instrument. | size 4</p>
 * <p>Price > long (i64) lastTradedPrice - Last Traded Price (LTP). | size 8</p>
 * <p>Price > long (i64) closingPrice - Closing Price (CP). | size 8</p>
 * <p>ClosingPriceType closingPriceType - Closing Price Type. | size 1</p>
 * <p>PercentageChange > long (i64) pctChange - Percentage change. | size 8</p>
 * <p>Price > long (i64) vwap - Volume-weighted average price. | size 8</p>
 * <p>u64 > BigInteger noTrades - Total number of transations on the current trading day. | size 8</p>
 * <p>Quantity > BigInteger (u64) totalVolume - Total transaction volume. | size 8</p>
 * <p>Value > long (i64) totalValue - Total transaction value. | size 8</p>
 * <p>Price > long (i64) openingPrice - The price of the first trade on the current trading day. | size 8</p>
 * <p>Price > long (i64) maxPrice - Highest price of the instrument on the current trading day | size 8</p>
 * <p>Price > long (i64) minPrice - Lowest price of the instrument on the current trading day | size 8</p>
 * <p>ProductIdentificationType productIdentificationType - Type of product identification. | size 1</p>
 * <p>ProductIdentification > String (u8[]) productIdentification - Product identification, e.g. ISIN number. | size 30</p>
 * <p>Number > long (i64) AccumulatedInterest - Accumulated interest on the bonds or for mortgage-backed bonds on the day of settling the transaction. | size 8</p>
 * <p>Number > long (i64) interestRate - Interest rate is determined on the basis of WIBOR/WIBID/WIRON rates for each expiration date of options and futures. | size 8</p>
 * <p>Price > long (i64) referencePrice - Initial reference price (at the start of session day). | size 8</p>
 * <p>Price > long (i64) settlementPrice - Settlement price. | size 8</p>
 * <p>Price > long (i64) settlementValue - Settlement value. | size 8</p>
 * <p>Currency currency - Price currency (e.g. USD). | size 2</p>
 * <p>u32 > long openPositions - Number of open positions after the end of the session. | size 4</p>
 * <p>Date > long (u32) sessionDate - Stock Exchange session date. | size 4</p>
 * <p>Date > long (u32) endTradingDate - End of trading date. | size 4</p>
 * <p>Date > long (u32) lastTradeDate - The date of execution of the last trade for the given instrument. | size 4</p>
 * <p>u64 > BigInteger numberOfInstruments - Number of instruments admitted to trading. | size 8</p>
 * <p>Value > long (i64) tradingValueCurrency - The field comprises the trading value expressed in trading currency. | size 8</p>
 * <p>u64 > BigInteger blockNoTrades - The total number of block trades concluded on a particular instrument during the current trading session. | size 8</p>
 * <p>Quantity > BigInteger (u64) blockVolume - The total turnover volume of block trades concluded on a particular instrument during the current trading session. | size 8</p>
 * <p>Value > long (i64) blockValue - The total turnover value of block trades concluded on a particular instrument during the current trading session. | size 8</p>
 * <p>Price > long (i64) blockMinPrice - Minimum price of block trades in the instrument during the current trading session. If no block trades were concluded during the trading session, the field is completed with zeros. | size 8</p>
 * <p>Price > long (i64) blockMaxPrice - Maximum price of block trades in the instrument during the current trading session. If no block trades were concluded during the trading session, the field is completed with zeros. | size 8</p>
 * <p>Price > long (i64) vwapBlockTrade - Volume weighted average price of block trades for a particular instrument during a particular trading session. | size 8</p>
 * <p>InstrumentStatus status - Financial instrument status. | size 1</p>
 * <p>u16 > int sector - Defines the sector of the economy that the company belongs to. Possible values for shares, the field assumes were described in the WATS Market Data documetation. | size 2</p>
 * <p>Market market - Defines the market the instrument belongs to. | size 1</p>
 * <p>ChangeIndicator markerPriceChange - The field contains the market of the percentage change of the instrument closing price from the current session in relation to the reference price. | size 1</p>
 * <p>ElementId > long (u32) marketStructureId - ID of the financial instrument’s market segment. | size 4</p>
 * <p>bool > boolean lowerLiquidity - The positive field value (true) informs if the company was qualified to Lower Liquidity Space. A negative value has the opposite meaning. | size 1</p>
 * <p>Number > long (i64) multiplier - Instrument multiplier. | size 8</p>
 * <p>Number > long (i64) impliedVolatility - Implied volatility. | size 8</p>
 * <p>Number > long (i64) dividendRate - Dividend rate for the company is given on the basis of dividends paid by companies being the base instrument for futures and options. | size 8</p>
 * <p>Number > long (i64) delta - Value of Delta indicator from the current session. | size 8</p>
 * <p>Number > long (i64) gamma - Value of Gamma indicator from the current session. | size 8</p>
 * <p>Number > long (i64) rho - Value of Rho indicator from the current session. | size 8</p>
 * <p>Number > long (i64) theta - Value of Theta indicator from the current session. | size 8</p>
 * <p>Number > long (i64) vega - Value of Vega indicator from the current session. | size 8</p>
 * <p>Number > long (i64) volatility - Volatility to be calculated is based on the implied volatility (provided in field ImpliedVolatility) according to algorithm worked out by the WSE, which is available on the website: www.opcje.gpw.pl. | size 8</p>
 * <p>Price > long (i64) optionsStrikePrice - Option product strike price. | size 8</p>
 * <p>bool > boolean dividend - The positive field value (true) informs that the instruments are traded the first time after determining the right to dividend. A negative value has the opposite meaning. | size 1</p>
 * <p>bool > boolean subscriptionRight - The positive field value (true) informs that the instruments are traded the first time after determining the subscription right. A negative value has the opposite meaning. | size 1</p>
 * <p>bool > boolean interimDividendRight - The positive field value (true) informs that the instruments are traded the first time after determining the interim dividend right. A negative value has the opposite meaning. | size 1</p>
 * <p>bool > boolean split - The positive field value (true) informs that the instruments are traded the first time after the change of the nominal value of shares. A negative value has the opposite meaning. | size 1</p>
 * <p>QuotationSystem quotationSystem - The field informing about the system of listing the instrument. | size 1</p>
 * <p>bool > boolean liquiditySupportPge - The field informs if the company entered to Liquidity Support Programme. True - participation in the liquidity Programme, false - no participation in the liquidity Programme. | size 1</p>
 * */

public class SessionSummary implements ByteSerializable, Message {

    private Header header;
    private long instrumentId;
    private long lastTradedPrice;
    private long closingPrice;
    private ClosingPriceType closingPriceType;
    private long pctChange;
    private long vwap;
    private BigInteger noTrades;
    private BigInteger totalVolume;
    private long totalValue;
    private long openingPrice;
    private long maxPrice;
    private long minPrice;
    private ProductIdentificationType productIdentificationType;
    private String productIdentification;
    private long AccumulatedInterest;
    private long interestRate;
    private long referencePrice;
    private long settlementPrice;
    private long settlementValue;
    private Currency currency;
    private long openPositions;
    private long sessionDate;
    private long endTradingDate;
    private long lastTradeDate;
    private BigInteger numberOfInstruments;
    private long tradingValueCurrency;
    private BigInteger blockNoTrades;
    private BigInteger blockVolume;
    private long blockValue;
    private long blockMinPrice;
    private long blockMaxPrice;
    private long vwapBlockTrade;
    private InstrumentStatus status;
    private int sector;
    private Market market;
    private ChangeIndicator markerPriceChange;
    private long marketStructureId;
    private boolean lowerLiquidity;
    private long multiplier;
    private long impliedVolatility;
    private long dividendRate;
    private long delta;
    private long gamma;
    private long rho;
    private long theta;
    private long vega;
    private long volatility;
    private long optionsStrikePrice;
    private boolean dividend;
    private boolean subscriptionRight;
    private boolean interimDividendRight;
    private boolean split;
    private QuotationSystem quotationSystem;
    private boolean liquiditySupportPge;
    public static final int byteLength = 373;

    public SessionSummary(Header header, long instrumentId, long lastTradedPrice, long closingPrice, ClosingPriceType closingPriceType, long pctChange, long vwap, BigInteger noTrades, BigInteger totalVolume, long totalValue, long openingPrice, long maxPrice, long minPrice, ProductIdentificationType productIdentificationType, String productIdentification, long AccumulatedInterest, long interestRate, long referencePrice, long settlementPrice, long settlementValue, Currency currency, long openPositions, long sessionDate, long endTradingDate, long lastTradeDate, BigInteger numberOfInstruments, long tradingValueCurrency, BigInteger blockNoTrades, BigInteger blockVolume, long blockValue, long blockMinPrice, long blockMaxPrice, long vwapBlockTrade, InstrumentStatus status, int sector, Market market, ChangeIndicator markerPriceChange, long marketStructureId, boolean lowerLiquidity, long multiplier, long impliedVolatility, long dividendRate, long delta, long gamma, long rho, long theta, long vega, long volatility, long optionsStrikePrice, boolean dividend, boolean subscriptionRight, boolean interimDividendRight, boolean split, QuotationSystem quotationSystem, boolean liquiditySupportPge) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.lastTradedPrice = lastTradedPrice;
        this.closingPrice = closingPrice;
        this.closingPriceType = closingPriceType;
        this.pctChange = pctChange;
        this.vwap = vwap;
        this.noTrades = noTrades;
        this.totalVolume = totalVolume;
        this.totalValue = totalValue;
        this.openingPrice = openingPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.productIdentificationType = productIdentificationType;
        this.productIdentification = productIdentification;
        this.AccumulatedInterest = AccumulatedInterest;
        this.interestRate = interestRate;
        this.referencePrice = referencePrice;
        this.settlementPrice = settlementPrice;
        this.settlementValue = settlementValue;
        this.currency = currency;
        this.openPositions = openPositions;
        this.sessionDate = sessionDate;
        this.endTradingDate = endTradingDate;
        this.lastTradeDate = lastTradeDate;
        this.numberOfInstruments = numberOfInstruments;
        this.tradingValueCurrency = tradingValueCurrency;
        this.blockNoTrades = blockNoTrades;
        this.blockVolume = blockVolume;
        this.blockValue = blockValue;
        this.blockMinPrice = blockMinPrice;
        this.blockMaxPrice = blockMaxPrice;
        this.vwapBlockTrade = vwapBlockTrade;
        this.status = status;
        this.sector = sector;
        this.market = market;
        this.markerPriceChange = markerPriceChange;
        this.marketStructureId = marketStructureId;
        this.lowerLiquidity = lowerLiquidity;
        this.multiplier = multiplier;
        this.impliedVolatility = impliedVolatility;
        this.dividendRate = dividendRate;
        this.delta = delta;
        this.gamma = gamma;
        this.rho = rho;
        this.theta = theta;
        this.vega = vega;
        this.volatility = volatility;
        this.optionsStrikePrice = optionsStrikePrice;
        this.dividend = dividend;
        this.subscriptionRight = subscriptionRight;
        this.interimDividendRight = interimDividendRight;
        this.split = split;
        this.quotationSystem = quotationSystem;
        this.liquiditySupportPge = liquiditySupportPge;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.SESSIONSUMMARY);
    }

    public SessionSummary(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 39);
        this.lastTradedPrice = BendecUtils.int64FromByteArray(bytes, offset + 43);
        this.closingPrice = BendecUtils.int64FromByteArray(bytes, offset + 51);
        this.closingPriceType = ClosingPriceType.getClosingPriceType(bytes, offset + 59);
        this.pctChange = BendecUtils.int64FromByteArray(bytes, offset + 60);
        this.vwap = BendecUtils.int64FromByteArray(bytes, offset + 68);
        this.noTrades = BendecUtils.uInt64FromByteArray(bytes, offset + 76);
        this.totalVolume = BendecUtils.uInt64FromByteArray(bytes, offset + 84);
        this.totalValue = BendecUtils.int64FromByteArray(bytes, offset + 92);
        this.openingPrice = BendecUtils.int64FromByteArray(bytes, offset + 100);
        this.maxPrice = BendecUtils.int64FromByteArray(bytes, offset + 108);
        this.minPrice = BendecUtils.int64FromByteArray(bytes, offset + 116);
        this.productIdentificationType = ProductIdentificationType.getProductIdentificationType(bytes, offset + 124);
        this.productIdentification = BendecUtils.stringFromByteArray(bytes, offset + 125, 30);
        this.AccumulatedInterest = BendecUtils.int64FromByteArray(bytes, offset + 155);
        this.interestRate = BendecUtils.int64FromByteArray(bytes, offset + 163);
        this.referencePrice = BendecUtils.int64FromByteArray(bytes, offset + 171);
        this.settlementPrice = BendecUtils.int64FromByteArray(bytes, offset + 179);
        this.settlementValue = BendecUtils.int64FromByteArray(bytes, offset + 187);
        this.currency = Currency.getCurrency(bytes, offset + 195);
        this.openPositions = BendecUtils.uInt32FromByteArray(bytes, offset + 197);
        this.sessionDate = BendecUtils.uInt32FromByteArray(bytes, offset + 201);
        this.endTradingDate = BendecUtils.uInt32FromByteArray(bytes, offset + 205);
        this.lastTradeDate = BendecUtils.uInt32FromByteArray(bytes, offset + 209);
        this.numberOfInstruments = BendecUtils.uInt64FromByteArray(bytes, offset + 213);
        this.tradingValueCurrency = BendecUtils.int64FromByteArray(bytes, offset + 221);
        this.blockNoTrades = BendecUtils.uInt64FromByteArray(bytes, offset + 229);
        this.blockVolume = BendecUtils.uInt64FromByteArray(bytes, offset + 237);
        this.blockValue = BendecUtils.int64FromByteArray(bytes, offset + 245);
        this.blockMinPrice = BendecUtils.int64FromByteArray(bytes, offset + 253);
        this.blockMaxPrice = BendecUtils.int64FromByteArray(bytes, offset + 261);
        this.vwapBlockTrade = BendecUtils.int64FromByteArray(bytes, offset + 269);
        this.status = InstrumentStatus.getInstrumentStatus(bytes, offset + 277);
        this.sector = BendecUtils.uInt16FromByteArray(bytes, offset + 278);
        this.market = Market.getMarket(bytes, offset + 280);
        this.markerPriceChange = ChangeIndicator.getChangeIndicator(bytes, offset + 281);
        this.marketStructureId = BendecUtils.uInt32FromByteArray(bytes, offset + 282);
        this.lowerLiquidity = BendecUtils.booleanFromByteArray(bytes, offset + 286);
        this.multiplier = BendecUtils.int64FromByteArray(bytes, offset + 287);
        this.impliedVolatility = BendecUtils.int64FromByteArray(bytes, offset + 295);
        this.dividendRate = BendecUtils.int64FromByteArray(bytes, offset + 303);
        this.delta = BendecUtils.int64FromByteArray(bytes, offset + 311);
        this.gamma = BendecUtils.int64FromByteArray(bytes, offset + 319);
        this.rho = BendecUtils.int64FromByteArray(bytes, offset + 327);
        this.theta = BendecUtils.int64FromByteArray(bytes, offset + 335);
        this.vega = BendecUtils.int64FromByteArray(bytes, offset + 343);
        this.volatility = BendecUtils.int64FromByteArray(bytes, offset + 351);
        this.optionsStrikePrice = BendecUtils.int64FromByteArray(bytes, offset + 359);
        this.dividend = BendecUtils.booleanFromByteArray(bytes, offset + 367);
        this.subscriptionRight = BendecUtils.booleanFromByteArray(bytes, offset + 368);
        this.interimDividendRight = BendecUtils.booleanFromByteArray(bytes, offset + 369);
        this.split = BendecUtils.booleanFromByteArray(bytes, offset + 370);
        this.quotationSystem = QuotationSystem.getQuotationSystem(bytes, offset + 371);
        this.liquiditySupportPge = BendecUtils.booleanFromByteArray(bytes, offset + 372);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.SESSIONSUMMARY);
    }

    public SessionSummary(byte[] bytes) {
        this(bytes, 0);
    }

    public SessionSummary() {
    }



    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return Identifier of the CLOB instrument.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    };
    /**
     * @return Last Traded Price (LTP).
     */
    public long getLastTradedPrice() {
        return this.lastTradedPrice;
    };
    /**
     * @return Closing Price (CP).
     */
    public long getClosingPrice() {
        return this.closingPrice;
    };
    /**
     * @return Closing Price Type.
     */
    public ClosingPriceType getClosingPriceType() {
        return this.closingPriceType;
    };
    /**
     * @return Percentage change.
     */
    public long getPctChange() {
        return this.pctChange;
    };
    /**
     * @return Volume-weighted average price.
     */
    public long getVwap() {
        return this.vwap;
    };
    /**
     * @return Total number of transations on the current trading day.
     */
    public BigInteger getNoTrades() {
        return this.noTrades;
    };
    /**
     * @return Total transaction volume.
     */
    public BigInteger getTotalVolume() {
        return this.totalVolume;
    };
    /**
     * @return Total transaction value.
     */
    public long getTotalValue() {
        return this.totalValue;
    };
    /**
     * @return The price of the first trade on the current trading day.
     */
    public long getOpeningPrice() {
        return this.openingPrice;
    };
    /**
     * @return Highest price of the instrument on the current trading day
     */
    public long getMaxPrice() {
        return this.maxPrice;
    };
    /**
     * @return Lowest price of the instrument on the current trading day
     */
    public long getMinPrice() {
        return this.minPrice;
    };
    /**
     * @return Type of product identification.
     */
    public ProductIdentificationType getProductIdentificationType() {
        return this.productIdentificationType;
    };
    /**
     * @return Product identification, e.g. ISIN number.
     */
    public String getProductIdentification() {
        return this.productIdentification;
    };
    /**
     * @return Accumulated interest on the bonds or for mortgage-backed bonds on the day of settling the transaction.
     */
    public long getAccumulatedInterest() {
        return this.AccumulatedInterest;
    };
    /**
     * @return Interest rate is determined on the basis of WIBOR/WIBID/WIRON rates for each expiration date of options and futures.
     */
    public long getInterestRate() {
        return this.interestRate;
    };
    /**
     * @return Initial reference price (at the start of session day).
     */
    public long getReferencePrice() {
        return this.referencePrice;
    };
    /**
     * @return Settlement price.
     */
    public long getSettlementPrice() {
        return this.settlementPrice;
    };
    /**
     * @return Settlement value.
     */
    public long getSettlementValue() {
        return this.settlementValue;
    };
    /**
     * @return Price currency (e.g. USD).
     */
    public Currency getCurrency() {
        return this.currency;
    };
    /**
     * @return Number of open positions after the end of the session.
     */
    public long getOpenPositions() {
        return this.openPositions;
    };
    /**
     * @return Stock Exchange session date.
     */
    public long getSessionDate() {
        return this.sessionDate;
    };
    /**
     * @return End of trading date.
     */
    public long getEndTradingDate() {
        return this.endTradingDate;
    };
    /**
     * @return The date of execution of the last trade for the given instrument.
     */
    public long getLastTradeDate() {
        return this.lastTradeDate;
    };
    /**
     * @return Number of instruments admitted to trading.
     */
    public BigInteger getNumberOfInstruments() {
        return this.numberOfInstruments;
    };
    /**
     * @return The field comprises the trading value expressed in trading currency.
     */
    public long getTradingValueCurrency() {
        return this.tradingValueCurrency;
    };
    /**
     * @return The total number of block trades concluded on a particular instrument during the current trading session.
     */
    public BigInteger getBlockNoTrades() {
        return this.blockNoTrades;
    };
    /**
     * @return The total turnover volume of block trades concluded on a particular instrument during the current trading session.
     */
    public BigInteger getBlockVolume() {
        return this.blockVolume;
    };
    /**
     * @return The total turnover value of block trades concluded on a particular instrument during the current trading session.
     */
    public long getBlockValue() {
        return this.blockValue;
    };
    /**
     * @return Minimum price of block trades in the instrument during the current trading session. If no block trades were concluded during the trading session, the field is completed with zeros.
     */
    public long getBlockMinPrice() {
        return this.blockMinPrice;
    };
    /**
     * @return Maximum price of block trades in the instrument during the current trading session. If no block trades were concluded during the trading session, the field is completed with zeros.
     */
    public long getBlockMaxPrice() {
        return this.blockMaxPrice;
    };
    /**
     * @return Volume weighted average price of block trades for a particular instrument during a particular trading session.
     */
    public long getVwapBlockTrade() {
        return this.vwapBlockTrade;
    };
    /**
     * @return Financial instrument status.
     */
    public InstrumentStatus getStatus() {
        return this.status;
    };
    /**
     * @return Defines the sector of the economy that the company belongs to. Possible values for shares, the field assumes were described in the WATS Market Data documetation.
     */
    public int getSector() {
        return this.sector;
    };
    /**
     * @return Defines the market the instrument belongs to.
     */
    public Market getMarket() {
        return this.market;
    };
    /**
     * @return The field contains the market of the percentage change of the instrument closing price from the current session in relation to the reference price.
     */
    public ChangeIndicator getMarkerPriceChange() {
        return this.markerPriceChange;
    };
    /**
     * @return ID of the financial instrument’s market segment.
     */
    public long getMarketStructureId() {
        return this.marketStructureId;
    };
    /**
     * @return The positive field value (true) informs if the company was qualified to Lower Liquidity Space. A negative value has the opposite meaning.
     */
    public boolean getLowerLiquidity() {
        return this.lowerLiquidity;
    };
    /**
     * @return Instrument multiplier.
     */
    public long getMultiplier() {
        return this.multiplier;
    };
    /**
     * @return Implied volatility.
     */
    public long getImpliedVolatility() {
        return this.impliedVolatility;
    };
    /**
     * @return Dividend rate for the company is given on the basis of dividends paid by companies being the base instrument for futures and options.
     */
    public long getDividendRate() {
        return this.dividendRate;
    };
    /**
     * @return Value of Delta indicator from the current session.
     */
    public long getDelta() {
        return this.delta;
    };
    /**
     * @return Value of Gamma indicator from the current session.
     */
    public long getGamma() {
        return this.gamma;
    };
    /**
     * @return Value of Rho indicator from the current session.
     */
    public long getRho() {
        return this.rho;
    };
    /**
     * @return Value of Theta indicator from the current session.
     */
    public long getTheta() {
        return this.theta;
    };
    /**
     * @return Value of Vega indicator from the current session.
     */
    public long getVega() {
        return this.vega;
    };
    /**
     * @return Volatility to be calculated is based on the implied volatility (provided in field ImpliedVolatility) according to algorithm worked out by the WSE, which is available on the website: www.opcje.gpw.pl.
     */
    public long getVolatility() {
        return this.volatility;
    };
    /**
     * @return Option product strike price.
     */
    public long getOptionsStrikePrice() {
        return this.optionsStrikePrice;
    };
    /**
     * @return The positive field value (true) informs that the instruments are traded the first time after determining the right to dividend. A negative value has the opposite meaning.
     */
    public boolean getDividend() {
        return this.dividend;
    };
    /**
     * @return The positive field value (true) informs that the instruments are traded the first time after determining the subscription right. A negative value has the opposite meaning.
     */
    public boolean getSubscriptionRight() {
        return this.subscriptionRight;
    };
    /**
     * @return The positive field value (true) informs that the instruments are traded the first time after determining the interim dividend right. A negative value has the opposite meaning.
     */
    public boolean getInterimDividendRight() {
        return this.interimDividendRight;
    };
    /**
     * @return The positive field value (true) informs that the instruments are traded the first time after the change of the nominal value of shares. A negative value has the opposite meaning.
     */
    public boolean getSplit() {
        return this.split;
    };
    /**
     * @return The field informing about the system of listing the instrument.
     */
    public QuotationSystem getQuotationSystem() {
        return this.quotationSystem;
    };
    /**
     * @return The field informs if the company entered to Liquidity Support Programme. True - participation in the liquidity Programme, false - no participation in the liquidity Programme.
     */
    public boolean getLiquiditySupportPge() {
        return this.liquiditySupportPge;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param instrumentId Identifier of the CLOB instrument.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    };
    /**
     * @param lastTradedPrice Last Traded Price (LTP).
     */
    public void setLastTradedPrice(long lastTradedPrice) {
        this.lastTradedPrice = lastTradedPrice;
    };
    /**
     * @param closingPrice Closing Price (CP).
     */
    public void setClosingPrice(long closingPrice) {
        this.closingPrice = closingPrice;
    };
    /**
     * @param closingPriceType Closing Price Type.
     */
    public void setClosingPriceType(ClosingPriceType closingPriceType) {
        this.closingPriceType = closingPriceType;
    };
    /**
     * @param pctChange Percentage change.
     */
    public void setPctChange(long pctChange) {
        this.pctChange = pctChange;
    };
    /**
     * @param vwap Volume-weighted average price.
     */
    public void setVwap(long vwap) {
        this.vwap = vwap;
    };
    /**
     * @param noTrades Total number of transations on the current trading day.
     */
    public void setNoTrades(BigInteger noTrades) {
        this.noTrades = noTrades;
    };
    /**
     * @param totalVolume Total transaction volume.
     */
    public void setTotalVolume(BigInteger totalVolume) {
        this.totalVolume = totalVolume;
    };
    /**
     * @param totalValue Total transaction value.
     */
    public void setTotalValue(long totalValue) {
        this.totalValue = totalValue;
    };
    /**
     * @param openingPrice The price of the first trade on the current trading day.
     */
    public void setOpeningPrice(long openingPrice) {
        this.openingPrice = openingPrice;
    };
    /**
     * @param maxPrice Highest price of the instrument on the current trading day
     */
    public void setMaxPrice(long maxPrice) {
        this.maxPrice = maxPrice;
    };
    /**
     * @param minPrice Lowest price of the instrument on the current trading day
     */
    public void setMinPrice(long minPrice) {
        this.minPrice = minPrice;
    };
    /**
     * @param productIdentificationType Type of product identification.
     */
    public void setProductIdentificationType(ProductIdentificationType productIdentificationType) {
        this.productIdentificationType = productIdentificationType;
    };
    /**
     * @param productIdentification Product identification, e.g. ISIN number.
     */
    public void setProductIdentification(String productIdentification) {
        this.productIdentification = productIdentification;
    };
    /**
     * @param AccumulatedInterest Accumulated interest on the bonds or for mortgage-backed bonds on the day of settling the transaction.
     */
    public void setAccumulatedInterest(long AccumulatedInterest) {
        this.AccumulatedInterest = AccumulatedInterest;
    };
    /**
     * @param interestRate Interest rate is determined on the basis of WIBOR/WIBID/WIRON rates for each expiration date of options and futures.
     */
    public void setInterestRate(long interestRate) {
        this.interestRate = interestRate;
    };
    /**
     * @param referencePrice Initial reference price (at the start of session day).
     */
    public void setReferencePrice(long referencePrice) {
        this.referencePrice = referencePrice;
    };
    /**
     * @param settlementPrice Settlement price.
     */
    public void setSettlementPrice(long settlementPrice) {
        this.settlementPrice = settlementPrice;
    };
    /**
     * @param settlementValue Settlement value.
     */
    public void setSettlementValue(long settlementValue) {
        this.settlementValue = settlementValue;
    };
    /**
     * @param currency Price currency (e.g. USD).
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    };
    /**
     * @param openPositions Number of open positions after the end of the session.
     */
    public void setOpenPositions(long openPositions) {
        this.openPositions = openPositions;
    };
    /**
     * @param sessionDate Stock Exchange session date.
     */
    public void setSessionDate(long sessionDate) {
        this.sessionDate = sessionDate;
    };
    /**
     * @param endTradingDate End of trading date.
     */
    public void setEndTradingDate(long endTradingDate) {
        this.endTradingDate = endTradingDate;
    };
    /**
     * @param lastTradeDate The date of execution of the last trade for the given instrument.
     */
    public void setLastTradeDate(long lastTradeDate) {
        this.lastTradeDate = lastTradeDate;
    };
    /**
     * @param numberOfInstruments Number of instruments admitted to trading.
     */
    public void setNumberOfInstruments(BigInteger numberOfInstruments) {
        this.numberOfInstruments = numberOfInstruments;
    };
    /**
     * @param tradingValueCurrency The field comprises the trading value expressed in trading currency.
     */
    public void setTradingValueCurrency(long tradingValueCurrency) {
        this.tradingValueCurrency = tradingValueCurrency;
    };
    /**
     * @param blockNoTrades The total number of block trades concluded on a particular instrument during the current trading session.
     */
    public void setBlockNoTrades(BigInteger blockNoTrades) {
        this.blockNoTrades = blockNoTrades;
    };
    /**
     * @param blockVolume The total turnover volume of block trades concluded on a particular instrument during the current trading session.
     */
    public void setBlockVolume(BigInteger blockVolume) {
        this.blockVolume = blockVolume;
    };
    /**
     * @param blockValue The total turnover value of block trades concluded on a particular instrument during the current trading session.
     */
    public void setBlockValue(long blockValue) {
        this.blockValue = blockValue;
    };
    /**
     * @param blockMinPrice Minimum price of block trades in the instrument during the current trading session. If no block trades were concluded during the trading session, the field is completed with zeros.
     */
    public void setBlockMinPrice(long blockMinPrice) {
        this.blockMinPrice = blockMinPrice;
    };
    /**
     * @param blockMaxPrice Maximum price of block trades in the instrument during the current trading session. If no block trades were concluded during the trading session, the field is completed with zeros.
     */
    public void setBlockMaxPrice(long blockMaxPrice) {
        this.blockMaxPrice = blockMaxPrice;
    };
    /**
     * @param vwapBlockTrade Volume weighted average price of block trades for a particular instrument during a particular trading session.
     */
    public void setVwapBlockTrade(long vwapBlockTrade) {
        this.vwapBlockTrade = vwapBlockTrade;
    };
    /**
     * @param status Financial instrument status.
     */
    public void setStatus(InstrumentStatus status) {
        this.status = status;
    };
    /**
     * @param sector Defines the sector of the economy that the company belongs to. Possible values for shares, the field assumes were described in the WATS Market Data documetation.
     */
    public void setSector(int sector) {
        this.sector = sector;
    };
    /**
     * @param market Defines the market the instrument belongs to.
     */
    public void setMarket(Market market) {
        this.market = market;
    };
    /**
     * @param markerPriceChange The field contains the market of the percentage change of the instrument closing price from the current session in relation to the reference price.
     */
    public void setMarkerPriceChange(ChangeIndicator markerPriceChange) {
        this.markerPriceChange = markerPriceChange;
    };
    /**
     * @param marketStructureId ID of the financial instrument’s market segment.
     */
    public void setMarketStructureId(long marketStructureId) {
        this.marketStructureId = marketStructureId;
    };
    /**
     * @param lowerLiquidity The positive field value (true) informs if the company was qualified to Lower Liquidity Space. A negative value has the opposite meaning.
     */
    public void setLowerLiquidity(boolean lowerLiquidity) {
        this.lowerLiquidity = lowerLiquidity;
    };
    /**
     * @param multiplier Instrument multiplier.
     */
    public void setMultiplier(long multiplier) {
        this.multiplier = multiplier;
    };
    /**
     * @param impliedVolatility Implied volatility.
     */
    public void setImpliedVolatility(long impliedVolatility) {
        this.impliedVolatility = impliedVolatility;
    };
    /**
     * @param dividendRate Dividend rate for the company is given on the basis of dividends paid by companies being the base instrument for futures and options.
     */
    public void setDividendRate(long dividendRate) {
        this.dividendRate = dividendRate;
    };
    /**
     * @param delta Value of Delta indicator from the current session.
     */
    public void setDelta(long delta) {
        this.delta = delta;
    };
    /**
     * @param gamma Value of Gamma indicator from the current session.
     */
    public void setGamma(long gamma) {
        this.gamma = gamma;
    };
    /**
     * @param rho Value of Rho indicator from the current session.
     */
    public void setRho(long rho) {
        this.rho = rho;
    };
    /**
     * @param theta Value of Theta indicator from the current session.
     */
    public void setTheta(long theta) {
        this.theta = theta;
    };
    /**
     * @param vega Value of Vega indicator from the current session.
     */
    public void setVega(long vega) {
        this.vega = vega;
    };
    /**
     * @param volatility Volatility to be calculated is based on the implied volatility (provided in field ImpliedVolatility) according to algorithm worked out by the WSE, which is available on the website: www.opcje.gpw.pl.
     */
    public void setVolatility(long volatility) {
        this.volatility = volatility;
    };
    /**
     * @param optionsStrikePrice Option product strike price.
     */
    public void setOptionsStrikePrice(long optionsStrikePrice) {
        this.optionsStrikePrice = optionsStrikePrice;
    };
    /**
     * @param dividend The positive field value (true) informs that the instruments are traded the first time after determining the right to dividend. A negative value has the opposite meaning.
     */
    public void setDividend(boolean dividend) {
        this.dividend = dividend;
    };
    /**
     * @param subscriptionRight The positive field value (true) informs that the instruments are traded the first time after determining the subscription right. A negative value has the opposite meaning.
     */
    public void setSubscriptionRight(boolean subscriptionRight) {
        this.subscriptionRight = subscriptionRight;
    };
    /**
     * @param interimDividendRight The positive field value (true) informs that the instruments are traded the first time after determining the interim dividend right. A negative value has the opposite meaning.
     */
    public void setInterimDividendRight(boolean interimDividendRight) {
        this.interimDividendRight = interimDividendRight;
    };
    /**
     * @param split The positive field value (true) informs that the instruments are traded the first time after the change of the nominal value of shares. A negative value has the opposite meaning.
     */
    public void setSplit(boolean split) {
        this.split = split;
    };
    /**
     * @param quotationSystem The field informing about the system of listing the instrument.
     */
    public void setQuotationSystem(QuotationSystem quotationSystem) {
        this.quotationSystem = quotationSystem;
    };
    /**
     * @param liquiditySupportPge The field informs if the company entered to Liquidity Support Programme. True - participation in the liquidity Programme, false - no participation in the liquidity Programme.
     */
    public void setLiquiditySupportPge(boolean liquiditySupportPge) {
        this.liquiditySupportPge = liquiditySupportPge;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.int64ToByteArray(this.lastTradedPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.closingPrice));
        closingPriceType.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.pctChange));
        buffer.put(BendecUtils.int64ToByteArray(this.vwap));
        buffer.put(BendecUtils.uInt64ToByteArray(this.noTrades));
        buffer.put(BendecUtils.uInt64ToByteArray(this.totalVolume));
        buffer.put(BendecUtils.int64ToByteArray(this.totalValue));
        buffer.put(BendecUtils.int64ToByteArray(this.openingPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.maxPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.minPrice));
        productIdentificationType.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.productIdentification, 30));
        buffer.put(BendecUtils.int64ToByteArray(this.AccumulatedInterest));
        buffer.put(BendecUtils.int64ToByteArray(this.interestRate));
        buffer.put(BendecUtils.int64ToByteArray(this.referencePrice));
        buffer.put(BendecUtils.int64ToByteArray(this.settlementPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.settlementValue));
        currency.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.openPositions));
        buffer.put(BendecUtils.uInt32ToByteArray(this.sessionDate));
        buffer.put(BendecUtils.uInt32ToByteArray(this.endTradingDate));
        buffer.put(BendecUtils.uInt32ToByteArray(this.lastTradeDate));
        buffer.put(BendecUtils.uInt64ToByteArray(this.numberOfInstruments));
        buffer.put(BendecUtils.int64ToByteArray(this.tradingValueCurrency));
        buffer.put(BendecUtils.uInt64ToByteArray(this.blockNoTrades));
        buffer.put(BendecUtils.uInt64ToByteArray(this.blockVolume));
        buffer.put(BendecUtils.int64ToByteArray(this.blockValue));
        buffer.put(BendecUtils.int64ToByteArray(this.blockMinPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.blockMaxPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.vwapBlockTrade));
        status.toBytes(buffer);
        buffer.put(BendecUtils.uInt16ToByteArray(this.sector));
        market.toBytes(buffer);
        markerPriceChange.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.marketStructureId));
        buffer.put(BendecUtils.booleanToByteArray(this.lowerLiquidity));
        buffer.put(BendecUtils.int64ToByteArray(this.multiplier));
        buffer.put(BendecUtils.int64ToByteArray(this.impliedVolatility));
        buffer.put(BendecUtils.int64ToByteArray(this.dividendRate));
        buffer.put(BendecUtils.int64ToByteArray(this.delta));
        buffer.put(BendecUtils.int64ToByteArray(this.gamma));
        buffer.put(BendecUtils.int64ToByteArray(this.rho));
        buffer.put(BendecUtils.int64ToByteArray(this.theta));
        buffer.put(BendecUtils.int64ToByteArray(this.vega));
        buffer.put(BendecUtils.int64ToByteArray(this.volatility));
        buffer.put(BendecUtils.int64ToByteArray(this.optionsStrikePrice));
        buffer.put(BendecUtils.booleanToByteArray(this.dividend));
        buffer.put(BendecUtils.booleanToByteArray(this.subscriptionRight));
        buffer.put(BendecUtils.booleanToByteArray(this.interimDividendRight));
        buffer.put(BendecUtils.booleanToByteArray(this.split));
        quotationSystem.toBytes(buffer);
        buffer.put(BendecUtils.booleanToByteArray(this.liquiditySupportPge));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.int64ToByteArray(this.lastTradedPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.closingPrice));
        closingPriceType.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.pctChange));
        buffer.put(BendecUtils.int64ToByteArray(this.vwap));
        buffer.put(BendecUtils.uInt64ToByteArray(this.noTrades));
        buffer.put(BendecUtils.uInt64ToByteArray(this.totalVolume));
        buffer.put(BendecUtils.int64ToByteArray(this.totalValue));
        buffer.put(BendecUtils.int64ToByteArray(this.openingPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.maxPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.minPrice));
        productIdentificationType.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.productIdentification, 30));
        buffer.put(BendecUtils.int64ToByteArray(this.AccumulatedInterest));
        buffer.put(BendecUtils.int64ToByteArray(this.interestRate));
        buffer.put(BendecUtils.int64ToByteArray(this.referencePrice));
        buffer.put(BendecUtils.int64ToByteArray(this.settlementPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.settlementValue));
        currency.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.openPositions));
        buffer.put(BendecUtils.uInt32ToByteArray(this.sessionDate));
        buffer.put(BendecUtils.uInt32ToByteArray(this.endTradingDate));
        buffer.put(BendecUtils.uInt32ToByteArray(this.lastTradeDate));
        buffer.put(BendecUtils.uInt64ToByteArray(this.numberOfInstruments));
        buffer.put(BendecUtils.int64ToByteArray(this.tradingValueCurrency));
        buffer.put(BendecUtils.uInt64ToByteArray(this.blockNoTrades));
        buffer.put(BendecUtils.uInt64ToByteArray(this.blockVolume));
        buffer.put(BendecUtils.int64ToByteArray(this.blockValue));
        buffer.put(BendecUtils.int64ToByteArray(this.blockMinPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.blockMaxPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.vwapBlockTrade));
        status.toBytes(buffer);
        buffer.put(BendecUtils.uInt16ToByteArray(this.sector));
        market.toBytes(buffer);
        markerPriceChange.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.marketStructureId));
        buffer.put(BendecUtils.booleanToByteArray(this.lowerLiquidity));
        buffer.put(BendecUtils.int64ToByteArray(this.multiplier));
        buffer.put(BendecUtils.int64ToByteArray(this.impliedVolatility));
        buffer.put(BendecUtils.int64ToByteArray(this.dividendRate));
        buffer.put(BendecUtils.int64ToByteArray(this.delta));
        buffer.put(BendecUtils.int64ToByteArray(this.gamma));
        buffer.put(BendecUtils.int64ToByteArray(this.rho));
        buffer.put(BendecUtils.int64ToByteArray(this.theta));
        buffer.put(BendecUtils.int64ToByteArray(this.vega));
        buffer.put(BendecUtils.int64ToByteArray(this.volatility));
        buffer.put(BendecUtils.int64ToByteArray(this.optionsStrikePrice));
        buffer.put(BendecUtils.booleanToByteArray(this.dividend));
        buffer.put(BendecUtils.booleanToByteArray(this.subscriptionRight));
        buffer.put(BendecUtils.booleanToByteArray(this.interimDividendRight));
        buffer.put(BendecUtils.booleanToByteArray(this.split));
        quotationSystem.toBytes(buffer);
        buffer.put(BendecUtils.booleanToByteArray(this.liquiditySupportPge));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, instrumentId, lastTradedPrice, closingPrice, closingPriceType, pctChange, vwap, noTrades, totalVolume, totalValue, openingPrice, maxPrice, minPrice, productIdentificationType, productIdentification, AccumulatedInterest, interestRate, referencePrice, settlementPrice, settlementValue, currency, openPositions, sessionDate, endTradingDate, lastTradeDate, numberOfInstruments, tradingValueCurrency, blockNoTrades, blockVolume, blockValue, blockMinPrice, blockMaxPrice, vwapBlockTrade, status, sector, market, markerPriceChange, marketStructureId, lowerLiquidity, multiplier, impliedVolatility, dividendRate, delta, gamma, rho, theta, vega, volatility, optionsStrikePrice, dividend, subscriptionRight, interimDividendRight, split, quotationSystem, liquiditySupportPge);
    }

    @Override
    public String toString() {
        return "SessionSummary{" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", lastTradedPrice=" + lastTradedPrice +
            ", closingPrice=" + closingPrice +
            ", closingPriceType=" + closingPriceType +
            ", pctChange=" + pctChange +
            ", vwap=" + vwap +
            ", noTrades=" + noTrades +
            ", totalVolume=" + totalVolume +
            ", totalValue=" + totalValue +
            ", openingPrice=" + openingPrice +
            ", maxPrice=" + maxPrice +
            ", minPrice=" + minPrice +
            ", productIdentificationType=" + productIdentificationType +
            ", productIdentification=" + productIdentification +
            ", AccumulatedInterest=" + AccumulatedInterest +
            ", interestRate=" + interestRate +
            ", referencePrice=" + referencePrice +
            ", settlementPrice=" + settlementPrice +
            ", settlementValue=" + settlementValue +
            ", currency=" + currency +
            ", openPositions=" + openPositions +
            ", sessionDate=" + sessionDate +
            ", endTradingDate=" + endTradingDate +
            ", lastTradeDate=" + lastTradeDate +
            ", numberOfInstruments=" + numberOfInstruments +
            ", tradingValueCurrency=" + tradingValueCurrency +
            ", blockNoTrades=" + blockNoTrades +
            ", blockVolume=" + blockVolume +
            ", blockValue=" + blockValue +
            ", blockMinPrice=" + blockMinPrice +
            ", blockMaxPrice=" + blockMaxPrice +
            ", vwapBlockTrade=" + vwapBlockTrade +
            ", status=" + status +
            ", sector=" + sector +
            ", market=" + market +
            ", markerPriceChange=" + markerPriceChange +
            ", marketStructureId=" + marketStructureId +
            ", lowerLiquidity=" + lowerLiquidity +
            ", multiplier=" + multiplier +
            ", impliedVolatility=" + impliedVolatility +
            ", dividendRate=" + dividendRate +
            ", delta=" + delta +
            ", gamma=" + gamma +
            ", rho=" + rho +
            ", theta=" + theta +
            ", vega=" + vega +
            ", volatility=" + volatility +
            ", optionsStrikePrice=" + optionsStrikePrice +
            ", dividend=" + dividend +
            ", subscriptionRight=" + subscriptionRight +
            ", interimDividendRight=" + interimDividendRight +
            ", split=" + split +
            ", quotationSystem=" + quotationSystem +
            ", liquiditySupportPge=" + liquiditySupportPge +
            '}';
        }
}
