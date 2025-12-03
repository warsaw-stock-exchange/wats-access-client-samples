package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>ProductSummary</h2>
 * <p>Session day product summary.</p>
 * <p>Byte length: 671</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>SingleInstrumentSummary clobInstrument - Provides brief CLOB instrument summary. | size 119</p>
 * <p>SingleInstrumentSummary crossInstrument - Provides brief CROSS instrument summary. | size 119</p>
 * <p>SingleInstrumentSummary blockInstrument - Provides brief BLOCK instrument summary. | size 119</p>
 * <p>SingleInstrumentSummary hybridInstrument - Provides brief HYBRID instrument summary. | size 119</p>
 * <p>ProductIdentificationType productIdentificationType - Type of product identification. | size 1</p>
 * <p>ProductIdentification > String (u8[]) productIdentification - Product identification, e.g. ISIN number. | size 30</p>
 * <p>ElementId > long (u32) productId - ID of the product. | size 4</p>
 * <p>MicCode > String (u8[]) mic - Market structure's Market Identifier Code (MIC) as specified in ISO 10383. | size 4</p>
 * <p>Number > long (i64) AccumulatedInterest - Accumulated interest on the bonds or for mortgage-backed bonds on the day of settling the transaction. | size 8</p>
 * <p>Number > long (i64) interestRate - Interest rate is determined on the basis of WIBOR/WIBID/WIRON rates for each expiration date of options and futures. | size 8</p>
 * <p>Currency currency - Price currency (e.g. USD). | size 2</p>
 * <p>Date > long (u32) sessionDate - Stock Exchange session date. | size 4</p>
 * <p>Value > long (i64) tradingValueCurrency - The field comprises the trading value expressed in trading currency. | size 8</p>
 * <p>InstrumentStatus status - Financial instrument status. | size 1</p>
 * <p>u16 > int sector - Defines the sector of the economy that the company belongs to. Possible values for shares, the field assumes were described in the WATS Market Data documentation. | size 2</p>
 * <p>u8 > int market - Defines the market the instrument belongs to. The field assumes were described in the WATS Market Data documentation. | size 1</p>
 * <p>ChangeIndicator markerPriceChange - The field contains the market of the percentage change of the instrument closing price from the current session in relation to the reference price. | size 1</p>
 * <p>bool > boolean lowerLiquidity - The positive field value (true) informs if the company was qualified to Lower Liquidity Space. A negative value has the opposite meaning. | size 1</p>
 * <p>Number > long (i64) multiplier - Instrument multiplier. | size 8</p>
 * <p>Number > long (i64) dividendRate - Dividend rate for the company is given on the basis of dividends paid by companies being the base instrument for futures and options. | size 8</p>
 * <p>Number > long (i64) delta - Value of Delta indicator from the current session. | size 8</p>
 * <p>Number > long (i64) gamma - Value of Gamma indicator from the current session. | size 8</p>
 * <p>Number > long (i64) rho - Value of Rho indicator from the current session. | size 8</p>
 * <p>Number > long (i64) theta - Value of Theta indicator from the current session. | size 8</p>
 * <p>Number > long (i64) vega - Value of Vega indicator from the current session. | size 8</p>
 * <p>Number > long (i64) volatility - Volatility is calculated according to algorithm worked out by the WSE. | size 8</p>
 * <p>Price > long (i64) optionsStrikePrice - Option product strike price. | size 8</p>
 * <p>bool > boolean dividend - The positive field value (true) informs that the instruments are traded the first time after determining the right to dividend. A negative value has the opposite meaning. | size 1</p>
 * <p>bool > boolean subscriptionRight - The positive field value (true) informs that the instruments are traded the first time after determining the subscription right. A negative value has the opposite meaning. | size 1</p>
 * <p>bool > boolean interimDividendRight - The positive field value (true) informs that the instruments are traded the first time after determining the interim dividend right. A negative value has the opposite meaning. | size 1</p>
 * <p>bool > boolean split - The positive field value (true) informs that the instruments are traded the first time after the change of the nominal value of shares. A negative value has the opposite meaning. | size 1</p>
 * <p>QuotationSystem quotationSystem - The field informing about the system of listing the instrument. | size 1</p>
 * <p>bool > boolean liquiditySupportPge - The field informs if the company entered to Liquidity Support Programme. True - participation in the liquidity Programme, false - no participation in the liquidity Programme. | size 1</p>
 */
public class ProductSummary implements ByteSerializable, Message {
    private Header header;
    private SingleInstrumentSummary clobInstrument;
    private SingleInstrumentSummary crossInstrument;
    private SingleInstrumentSummary blockInstrument;
    private SingleInstrumentSummary hybridInstrument;
    private ProductIdentificationType productIdentificationType;
    private String productIdentification;
    private long productId;
    private String mic;
    private long AccumulatedInterest;
    private long interestRate;
    private Currency currency;
    private long sessionDate;
    private long tradingValueCurrency;
    private InstrumentStatus status;
    private int sector;
    private int market;
    private ChangeIndicator markerPriceChange;
    private boolean lowerLiquidity;
    private long multiplier;
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
    public static final int byteLength = 671;
    
    public ProductSummary(Header header, SingleInstrumentSummary clobInstrument, SingleInstrumentSummary crossInstrument, SingleInstrumentSummary blockInstrument, SingleInstrumentSummary hybridInstrument, ProductIdentificationType productIdentificationType, String productIdentification, long productId, String mic, long AccumulatedInterest, long interestRate, Currency currency, long sessionDate, long tradingValueCurrency, InstrumentStatus status, int sector, int market, ChangeIndicator markerPriceChange, boolean lowerLiquidity, long multiplier, long dividendRate, long delta, long gamma, long rho, long theta, long vega, long volatility, long optionsStrikePrice, boolean dividend, boolean subscriptionRight, boolean interimDividendRight, boolean split, QuotationSystem quotationSystem, boolean liquiditySupportPge) {
        this.header = header;
        this.clobInstrument = clobInstrument;
        this.crossInstrument = crossInstrument;
        this.blockInstrument = blockInstrument;
        this.hybridInstrument = hybridInstrument;
        this.productIdentificationType = productIdentificationType;
        this.productIdentification = productIdentification;
        this.productId = productId;
        this.mic = mic;
        this.AccumulatedInterest = AccumulatedInterest;
        this.interestRate = interestRate;
        this.currency = currency;
        this.sessionDate = sessionDate;
        this.tradingValueCurrency = tradingValueCurrency;
        this.status = status;
        this.sector = sector;
        this.market = market;
        this.markerPriceChange = markerPriceChange;
        this.lowerLiquidity = lowerLiquidity;
        this.multiplier = multiplier;
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
    }
    
    public ProductSummary(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.clobInstrument = new SingleInstrumentSummary(bytes, offset + 42);
        this.crossInstrument = new SingleInstrumentSummary(bytes, offset + 161);
        this.blockInstrument = new SingleInstrumentSummary(bytes, offset + 280);
        this.hybridInstrument = new SingleInstrumentSummary(bytes, offset + 399);
        this.productIdentificationType = ProductIdentificationType.getProductIdentificationType(bytes, offset + 518);
        this.productIdentification = BendecUtils.stringFromByteArray(bytes, offset + 519, 30);
        this.productId = BendecUtils.uInt32FromByteArray(bytes, offset + 549);
        this.mic = BendecUtils.stringFromByteArray(bytes, offset + 553, 4);
        this.AccumulatedInterest = BendecUtils.int64FromByteArray(bytes, offset + 557);
        this.interestRate = BendecUtils.int64FromByteArray(bytes, offset + 565);
        this.currency = Currency.getCurrency(bytes, offset + 573);
        this.sessionDate = BendecUtils.uInt32FromByteArray(bytes, offset + 575);
        this.tradingValueCurrency = BendecUtils.int64FromByteArray(bytes, offset + 579);
        this.status = InstrumentStatus.getInstrumentStatus(bytes, offset + 587);
        this.sector = BendecUtils.uInt16FromByteArray(bytes, offset + 588);
        this.market = BendecUtils.uInt8FromByteArray(bytes, offset + 590);
        this.markerPriceChange = ChangeIndicator.getChangeIndicator(bytes, offset + 591);
        this.lowerLiquidity = BendecUtils.booleanFromByteArray(bytes, offset + 592);
        this.multiplier = BendecUtils.int64FromByteArray(bytes, offset + 593);
        this.dividendRate = BendecUtils.int64FromByteArray(bytes, offset + 601);
        this.delta = BendecUtils.int64FromByteArray(bytes, offset + 609);
        this.gamma = BendecUtils.int64FromByteArray(bytes, offset + 617);
        this.rho = BendecUtils.int64FromByteArray(bytes, offset + 625);
        this.theta = BendecUtils.int64FromByteArray(bytes, offset + 633);
        this.vega = BendecUtils.int64FromByteArray(bytes, offset + 641);
        this.volatility = BendecUtils.int64FromByteArray(bytes, offset + 649);
        this.optionsStrikePrice = BendecUtils.int64FromByteArray(bytes, offset + 657);
        this.dividend = BendecUtils.booleanFromByteArray(bytes, offset + 665);
        this.subscriptionRight = BendecUtils.booleanFromByteArray(bytes, offset + 666);
        this.interimDividendRight = BendecUtils.booleanFromByteArray(bytes, offset + 667);
        this.split = BendecUtils.booleanFromByteArray(bytes, offset + 668);
        this.quotationSystem = QuotationSystem.getQuotationSystem(bytes, offset + 669);
        this.liquiditySupportPge = BendecUtils.booleanFromByteArray(bytes, offset + 670);
    }
    
    public ProductSummary(byte[] bytes) {
        this(bytes, 0);
    }
    
    public ProductSummary() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Provides brief CLOB instrument summary.
     */
    public SingleInstrumentSummary getClobInstrument() {
        return this.clobInstrument;
    }
    
    /**
     * @return Provides brief CROSS instrument summary.
     */
    public SingleInstrumentSummary getCrossInstrument() {
        return this.crossInstrument;
    }
    
    /**
     * @return Provides brief BLOCK instrument summary.
     */
    public SingleInstrumentSummary getBlockInstrument() {
        return this.blockInstrument;
    }
    
    /**
     * @return Provides brief HYBRID instrument summary.
     */
    public SingleInstrumentSummary getHybridInstrument() {
        return this.hybridInstrument;
    }
    
    /**
     * @return Type of product identification.
     */
    public ProductIdentificationType getProductIdentificationType() {
        return this.productIdentificationType;
    }
    
    /**
     * @return Product identification, e.g. ISIN number.
     */
    public String getProductIdentification() {
        return this.productIdentification;
    }
    
    /**
     * @return ID of the product.
     */
    public long getProductId() {
        return this.productId;
    }
    
    /**
     * @return Market structure's Market Identifier Code (MIC) as specified in ISO 10383.
     */
    public String getMic() {
        return this.mic;
    }
    
    /**
     * @return Accumulated interest on the bonds or for mortgage-backed bonds on the day of settling the transaction.
     */
    public long getAccumulatedInterest() {
        return this.AccumulatedInterest;
    }
    
    /**
     * @return Interest rate is determined on the basis of WIBOR/WIBID/WIRON rates for each expiration date of options and futures.
     */
    public long getInterestRate() {
        return this.interestRate;
    }
    
    /**
     * @return Price currency (e.g. USD).
     */
    public Currency getCurrency() {
        return this.currency;
    }
    
    /**
     * @return Stock Exchange session date.
     */
    public long getSessionDate() {
        return this.sessionDate;
    }
    
    /**
     * @return The field comprises the trading value expressed in trading currency.
     */
    public long getTradingValueCurrency() {
        return this.tradingValueCurrency;
    }
    
    /**
     * @return Financial instrument status.
     */
    public InstrumentStatus getStatus() {
        return this.status;
    }
    
    /**
     * @return Defines the sector of the economy that the company belongs to. Possible values for shares, the field assumes were described in the WATS Market Data documentation.
     */
    public int getSector() {
        return this.sector;
    }
    
    /**
     * @return Defines the market the instrument belongs to. The field assumes were described in the WATS Market Data documentation.
     */
    public int getMarket() {
        return this.market;
    }
    
    /**
     * @return The field contains the market of the percentage change of the instrument closing price from the current session in relation to the reference price.
     */
    public ChangeIndicator getMarkerPriceChange() {
        return this.markerPriceChange;
    }
    
    /**
     * @return The positive field value (true) informs if the company was qualified to Lower Liquidity Space. A negative value has the opposite meaning.
     */
    public boolean getLowerLiquidity() {
        return this.lowerLiquidity;
    }
    
    /**
     * @return Instrument multiplier.
     */
    public long getMultiplier() {
        return this.multiplier;
    }
    
    /**
     * @return Dividend rate for the company is given on the basis of dividends paid by companies being the base instrument for futures and options.
     */
    public long getDividendRate() {
        return this.dividendRate;
    }
    
    /**
     * @return Value of Delta indicator from the current session.
     */
    public long getDelta() {
        return this.delta;
    }
    
    /**
     * @return Value of Gamma indicator from the current session.
     */
    public long getGamma() {
        return this.gamma;
    }
    
    /**
     * @return Value of Rho indicator from the current session.
     */
    public long getRho() {
        return this.rho;
    }
    
    /**
     * @return Value of Theta indicator from the current session.
     */
    public long getTheta() {
        return this.theta;
    }
    
    /**
     * @return Value of Vega indicator from the current session.
     */
    public long getVega() {
        return this.vega;
    }
    
    /**
     * @return Volatility is calculated according to algorithm worked out by the WSE.
     */
    public long getVolatility() {
        return this.volatility;
    }
    
    /**
     * @return Option product strike price.
     */
    public long getOptionsStrikePrice() {
        return this.optionsStrikePrice;
    }
    
    /**
     * @return The positive field value (true) informs that the instruments are traded the first time after determining the right to dividend. A negative value has the opposite meaning.
     */
    public boolean getDividend() {
        return this.dividend;
    }
    
    /**
     * @return The positive field value (true) informs that the instruments are traded the first time after determining the subscription right. A negative value has the opposite meaning.
     */
    public boolean getSubscriptionRight() {
        return this.subscriptionRight;
    }
    
    /**
     * @return The positive field value (true) informs that the instruments are traded the first time after determining the interim dividend right. A negative value has the opposite meaning.
     */
    public boolean getInterimDividendRight() {
        return this.interimDividendRight;
    }
    
    /**
     * @return The positive field value (true) informs that the instruments are traded the first time after the change of the nominal value of shares. A negative value has the opposite meaning.
     */
    public boolean getSplit() {
        return this.split;
    }
    
    /**
     * @return The field informing about the system of listing the instrument.
     */
    public QuotationSystem getQuotationSystem() {
        return this.quotationSystem;
    }
    
    /**
     * @return The field informs if the company entered to Liquidity Support Programme. True - participation in the liquidity Programme, false - no participation in the liquidity Programme.
     */
    public boolean getLiquiditySupportPge() {
        return this.liquiditySupportPge;
    }
    
    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param clobInstrument Provides brief CLOB instrument summary.
     */
    public void setClobInstrument(SingleInstrumentSummary clobInstrument) {
        this.clobInstrument = clobInstrument;
    }
    
    /**
     * @param crossInstrument Provides brief CROSS instrument summary.
     */
    public void setCrossInstrument(SingleInstrumentSummary crossInstrument) {
        this.crossInstrument = crossInstrument;
    }
    
    /**
     * @param blockInstrument Provides brief BLOCK instrument summary.
     */
    public void setBlockInstrument(SingleInstrumentSummary blockInstrument) {
        this.blockInstrument = blockInstrument;
    }
    
    /**
     * @param hybridInstrument Provides brief HYBRID instrument summary.
     */
    public void setHybridInstrument(SingleInstrumentSummary hybridInstrument) {
        this.hybridInstrument = hybridInstrument;
    }
    
    /**
     * @param productIdentificationType Type of product identification.
     */
    public void setProductIdentificationType(ProductIdentificationType productIdentificationType) {
        this.productIdentificationType = productIdentificationType;
    }
    
    /**
     * @param productIdentification Product identification, e.g. ISIN number.
     */
    public void setProductIdentification(String productIdentification) {
        this.productIdentification = productIdentification;
    }
    
    /**
     * @param productId ID of the product.
     */
    public void setProductId(long productId) {
        this.productId = productId;
    }
    
    /**
     * @param mic Market structure's Market Identifier Code (MIC) as specified in ISO 10383.
     */
    public void setMic(String mic) {
        this.mic = mic;
    }
    
    /**
     * @param AccumulatedInterest Accumulated interest on the bonds or for mortgage-backed bonds on the day of settling the transaction.
     */
    public void setAccumulatedInterest(long AccumulatedInterest) {
        this.AccumulatedInterest = AccumulatedInterest;
    }
    
    /**
     * @param interestRate Interest rate is determined on the basis of WIBOR/WIBID/WIRON rates for each expiration date of options and futures.
     */
    public void setInterestRate(long interestRate) {
        this.interestRate = interestRate;
    }
    
    /**
     * @param currency Price currency (e.g. USD).
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    
    /**
     * @param sessionDate Stock Exchange session date.
     */
    public void setSessionDate(long sessionDate) {
        this.sessionDate = sessionDate;
    }
    
    /**
     * @param tradingValueCurrency The field comprises the trading value expressed in trading currency.
     */
    public void setTradingValueCurrency(long tradingValueCurrency) {
        this.tradingValueCurrency = tradingValueCurrency;
    }
    
    /**
     * @param status Financial instrument status.
     */
    public void setStatus(InstrumentStatus status) {
        this.status = status;
    }
    
    /**
     * @param sector Defines the sector of the economy that the company belongs to. Possible values for shares, the field assumes were described in the WATS Market Data documentation.
     */
    public void setSector(int sector) {
        this.sector = sector;
    }
    
    /**
     * @param market Defines the market the instrument belongs to. The field assumes were described in the WATS Market Data documentation.
     */
    public void setMarket(int market) {
        this.market = market;
    }
    
    /**
     * @param markerPriceChange The field contains the market of the percentage change of the instrument closing price from the current session in relation to the reference price.
     */
    public void setMarkerPriceChange(ChangeIndicator markerPriceChange) {
        this.markerPriceChange = markerPriceChange;
    }
    
    /**
     * @param lowerLiquidity The positive field value (true) informs if the company was qualified to Lower Liquidity Space. A negative value has the opposite meaning.
     */
    public void setLowerLiquidity(boolean lowerLiquidity) {
        this.lowerLiquidity = lowerLiquidity;
    }
    
    /**
     * @param multiplier Instrument multiplier.
     */
    public void setMultiplier(long multiplier) {
        this.multiplier = multiplier;
    }
    
    /**
     * @param dividendRate Dividend rate for the company is given on the basis of dividends paid by companies being the base instrument for futures and options.
     */
    public void setDividendRate(long dividendRate) {
        this.dividendRate = dividendRate;
    }
    
    /**
     * @param delta Value of Delta indicator from the current session.
     */
    public void setDelta(long delta) {
        this.delta = delta;
    }
    
    /**
     * @param gamma Value of Gamma indicator from the current session.
     */
    public void setGamma(long gamma) {
        this.gamma = gamma;
    }
    
    /**
     * @param rho Value of Rho indicator from the current session.
     */
    public void setRho(long rho) {
        this.rho = rho;
    }
    
    /**
     * @param theta Value of Theta indicator from the current session.
     */
    public void setTheta(long theta) {
        this.theta = theta;
    }
    
    /**
     * @param vega Value of Vega indicator from the current session.
     */
    public void setVega(long vega) {
        this.vega = vega;
    }
    
    /**
     * @param volatility Volatility is calculated according to algorithm worked out by the WSE.
     */
    public void setVolatility(long volatility) {
        this.volatility = volatility;
    }
    
    /**
     * @param optionsStrikePrice Option product strike price.
     */
    public void setOptionsStrikePrice(long optionsStrikePrice) {
        this.optionsStrikePrice = optionsStrikePrice;
    }
    
    /**
     * @param dividend The positive field value (true) informs that the instruments are traded the first time after determining the right to dividend. A negative value has the opposite meaning.
     */
    public void setDividend(boolean dividend) {
        this.dividend = dividend;
    }
    
    /**
     * @param subscriptionRight The positive field value (true) informs that the instruments are traded the first time after determining the subscription right. A negative value has the opposite meaning.
     */
    public void setSubscriptionRight(boolean subscriptionRight) {
        this.subscriptionRight = subscriptionRight;
    }
    
    /**
     * @param interimDividendRight The positive field value (true) informs that the instruments are traded the first time after determining the interim dividend right. A negative value has the opposite meaning.
     */
    public void setInterimDividendRight(boolean interimDividendRight) {
        this.interimDividendRight = interimDividendRight;
    }
    
    /**
     * @param split The positive field value (true) informs that the instruments are traded the first time after the change of the nominal value of shares. A negative value has the opposite meaning.
     */
    public void setSplit(boolean split) {
        this.split = split;
    }
    
    /**
     * @param quotationSystem The field informing about the system of listing the instrument.
     */
    public void setQuotationSystem(QuotationSystem quotationSystem) {
        this.quotationSystem = quotationSystem;
    }
    
    /**
     * @param liquiditySupportPge The field informs if the company entered to Liquidity Support Programme. True - participation in the liquidity Programme, false - no participation in the liquidity Programme.
     */
    public void setLiquiditySupportPge(boolean liquiditySupportPge) {
        this.liquiditySupportPge = liquiditySupportPge;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        clobInstrument.toBytes(buffer);
        crossInstrument.toBytes(buffer);
        blockInstrument.toBytes(buffer);
        hybridInstrument.toBytes(buffer);
        productIdentificationType.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.productIdentification, 30));
        buffer.put(BendecUtils.uInt32ToByteArray(this.productId));
        buffer.put(BendecUtils.stringToByteArray(this.mic, 4));
        buffer.put(BendecUtils.int64ToByteArray(this.AccumulatedInterest));
        buffer.put(BendecUtils.int64ToByteArray(this.interestRate));
        currency.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.sessionDate));
        buffer.put(BendecUtils.int64ToByteArray(this.tradingValueCurrency));
        status.toBytes(buffer);
        buffer.put(BendecUtils.uInt16ToByteArray(this.sector));
        buffer.put(BendecUtils.uInt8ToByteArray(this.market));
        markerPriceChange.toBytes(buffer);
        buffer.put(BendecUtils.booleanToByteArray(this.lowerLiquidity));
        buffer.put(BendecUtils.int64ToByteArray(this.multiplier));
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
        clobInstrument.toBytes(buffer);
        crossInstrument.toBytes(buffer);
        blockInstrument.toBytes(buffer);
        hybridInstrument.toBytes(buffer);
        productIdentificationType.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.productIdentification, 30));
        buffer.put(BendecUtils.uInt32ToByteArray(this.productId));
        buffer.put(BendecUtils.stringToByteArray(this.mic, 4));
        buffer.put(BendecUtils.int64ToByteArray(this.AccumulatedInterest));
        buffer.put(BendecUtils.int64ToByteArray(this.interestRate));
        currency.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.sessionDate));
        buffer.put(BendecUtils.int64ToByteArray(this.tradingValueCurrency));
        status.toBytes(buffer);
        buffer.put(BendecUtils.uInt16ToByteArray(this.sector));
        buffer.put(BendecUtils.uInt8ToByteArray(this.market));
        markerPriceChange.toBytes(buffer);
        buffer.put(BendecUtils.booleanToByteArray(this.lowerLiquidity));
        buffer.put(BendecUtils.int64ToByteArray(this.multiplier));
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
        return Objects.hash(header,
        clobInstrument,
        crossInstrument,
        blockInstrument,
        hybridInstrument,
        productIdentificationType,
        productIdentification,
        productId,
        mic,
        AccumulatedInterest,
        interestRate,
        currency,
        sessionDate,
        tradingValueCurrency,
        status,
        sector,
        market,
        markerPriceChange,
        lowerLiquidity,
        multiplier,
        dividendRate,
        delta,
        gamma,
        rho,
        theta,
        vega,
        volatility,
        optionsStrikePrice,
        dividend,
        subscriptionRight,
        interimDividendRight,
        split,
        quotationSystem,
        liquiditySupportPge);
    }
    
    @Override
    public String toString() {
        return "ProductSummary {" +
            "header=" + header +
            ", clobInstrument=" + clobInstrument +
            ", crossInstrument=" + crossInstrument +
            ", blockInstrument=" + blockInstrument +
            ", hybridInstrument=" + hybridInstrument +
            ", productIdentificationType=" + productIdentificationType +
            ", productIdentification=" + productIdentification +
            ", productId=" + productId +
            ", mic=" + mic +
            ", AccumulatedInterest=" + AccumulatedInterest +
            ", interestRate=" + interestRate +
            ", currency=" + currency +
            ", sessionDate=" + sessionDate +
            ", tradingValueCurrency=" + tradingValueCurrency +
            ", status=" + status +
            ", sector=" + sector +
            ", market=" + market +
            ", markerPriceChange=" + markerPriceChange +
            ", lowerLiquidity=" + lowerLiquidity +
            ", multiplier=" + multiplier +
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
            "}";
    }
}