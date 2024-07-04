package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>Trade</h2>
 * <p>Enriched trade report.</p>
 * <p>Byte length: 220</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) instrumentId - ID of financial instrument. | size 4</p>
 * <p>Value > long (i64) settlementValue - Settlement value. | size 8</p>
 * <p>Date > long (u32) settlementDate - Settlement date. | size 4</p>
 * <p>Timestamp > BigInteger (u64) tradingTimestamp - Date and time when the transaction was executed. | size 8</p>
 * <p>PublicProductIdentification publicProductIdentification - Product identification type and code. | size 31</p>
 * <p>Price > long (i64) price - Trade price. | size 8</p>
 * <p>Currency priceCurrency - Currency in which the price is expressed (applicable if the price is expressed as monetary value). | size 2</p>
 * <p>PriceExpressionType priceNotation - Indication as to whether the price is expressed in monetary value, in percentage or in yield. | size 1</p>
 * <p>Quantity > BigInteger (u64) quantity - Trade quantity. | size 8</p>
 * <p>Value > long (i64) nominalValue - Nominal value. | size 8</p>
 * <p>Currency nominalCurrency - Currency in which the notional is denominated. | size 2</p>
 * <p>MicCode > String (u8[]) mic - Identification of the venue where the transaction was executed. | size 4</p>
 * <p>Timestamp > BigInteger (u64) publicationTimestamp - Date and time when the transaction was published by a trading venue. | size 8</p>
 * <p>TradeId > long (u32) tradeId - ID of the trade. | size 4</p>
 * <p>bool > boolean tradeToBeCleared - Code to identify whether the transaction will be cleared. | size 1</p>
 * <p>PercentageChange > long (i64) pctChange - Percentage change. | size 8</p>
 * <p>Price > long (i64) vwap - Volume-weighted average price. | size 8</p>
 * <p>u64 > BigInteger noTrades - Total number of transactions. | size 8</p>
 * <p>Quantity > BigInteger (u64) totalVolume - Total transaction volume. | size 8</p>
 * <p>Value > long (i64) totalValue - Total transaction value. | size 8</p>
 * <p>Price > long (i64) openingPrice - The price of the first trade on the current trading day. | size 8</p>
 * <p>Price > long (i64) maxPrice - Highest price of the instrument on the current trading day. | size 8</p>
 * <p>Price > long (i64) minPrice - Lowest price of the instrument on the current trading day. | size 8</p>
 * <p>MmtMarketMechanism mmtMarketMechanism - MMT Market Mechanism | size 1</p>
 * <p>MmtTradingMode mmtTradingMode - MMT Trading Mode | size 1</p>
 * <p>MmtTransactionCategory mmtTransactionCategory - MMT Transaction Category | size 1</p>
 * <p>MmtNegotiationIndicator mmtNegotiationIndicator - MMT Negotiation Indicator | size 1</p>
 * <p>MmtAgencyCrossTradeIndicator mmtAgencyCrossTradeIndicator - MMT Agency Cross Trade Indicator | size 1</p>
 * <p>MmtModificationIndicator mmtModificationIndicator - MMT Modification Indicator | size 1</p>
 * <p>MmtBenchmarkReferencePriceIndicator mmtBenchmarkReferencePriceIndicator - MMT Benchmark / Reference Price Indicator | size 1</p>
 * <p>MmtSpecialDividendIndicator mmtSpecialDividendIndicator - MMT Special Dividend Indicator | size 1</p>
 * <p>MmtOffBookAutomatedIndicator mmtOffBookAutomatedIndicator - MMT Off Book Automated Indicator | size 1</p>
 * <p>MmtOrdinaryTradeIndicator mmtOrdinaryTradeIndicator - MMT Ordinary Trade Indicator | size 1</p>
 * <p>MmtAlgorithmicIndicator mmtAlgorithmicIndicator - MMT Algorithmic Indicator | size 1</p>
 * <p>MmtPostTradeDeferralReason mmtPostTradeDeferralReason - MMT Post-Trade Deferral Reason | size 1</p>
 * <p>MmtPostTradeDeferralType mmtPostTradeDeferralType - MMT Post-Trade Deferral Type | size 1</p>
 */
public class Trade implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private long settlementValue;
    private long settlementDate;
    private BigInteger tradingTimestamp;
    private PublicProductIdentification publicProductIdentification;
    private long price;
    private Currency priceCurrency;
    private PriceExpressionType priceNotation;
    private BigInteger quantity;
    private long nominalValue;
    private Currency nominalCurrency;
    private String mic;
    private BigInteger publicationTimestamp;
    private long tradeId;
    private boolean tradeToBeCleared;
    private long pctChange;
    private long vwap;
    private BigInteger noTrades;
    private BigInteger totalVolume;
    private long totalValue;
    private long openingPrice;
    private long maxPrice;
    private long minPrice;
    private MmtMarketMechanism mmtMarketMechanism;
    private MmtTradingMode mmtTradingMode;
    private MmtTransactionCategory mmtTransactionCategory;
    private MmtNegotiationIndicator mmtNegotiationIndicator;
    private MmtAgencyCrossTradeIndicator mmtAgencyCrossTradeIndicator;
    private MmtModificationIndicator mmtModificationIndicator;
    private MmtBenchmarkReferencePriceIndicator mmtBenchmarkReferencePriceIndicator;
    private MmtSpecialDividendIndicator mmtSpecialDividendIndicator;
    private MmtOffBookAutomatedIndicator mmtOffBookAutomatedIndicator;
    private MmtOrdinaryTradeIndicator mmtOrdinaryTradeIndicator;
    private MmtAlgorithmicIndicator mmtAlgorithmicIndicator;
    private MmtPostTradeDeferralReason mmtPostTradeDeferralReason;
    private MmtPostTradeDeferralType mmtPostTradeDeferralType;
    public static final int byteLength = 220;
    
    public Trade(Header header, long instrumentId, long settlementValue, long settlementDate, BigInteger tradingTimestamp, PublicProductIdentification publicProductIdentification, long price, Currency priceCurrency, PriceExpressionType priceNotation, BigInteger quantity, long nominalValue, Currency nominalCurrency, String mic, BigInteger publicationTimestamp, long tradeId, boolean tradeToBeCleared, long pctChange, long vwap, BigInteger noTrades, BigInteger totalVolume, long totalValue, long openingPrice, long maxPrice, long minPrice, MmtMarketMechanism mmtMarketMechanism, MmtTradingMode mmtTradingMode, MmtTransactionCategory mmtTransactionCategory, MmtNegotiationIndicator mmtNegotiationIndicator, MmtAgencyCrossTradeIndicator mmtAgencyCrossTradeIndicator, MmtModificationIndicator mmtModificationIndicator, MmtBenchmarkReferencePriceIndicator mmtBenchmarkReferencePriceIndicator, MmtSpecialDividendIndicator mmtSpecialDividendIndicator, MmtOffBookAutomatedIndicator mmtOffBookAutomatedIndicator, MmtOrdinaryTradeIndicator mmtOrdinaryTradeIndicator, MmtAlgorithmicIndicator mmtAlgorithmicIndicator, MmtPostTradeDeferralReason mmtPostTradeDeferralReason, MmtPostTradeDeferralType mmtPostTradeDeferralType) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.settlementValue = settlementValue;
        this.settlementDate = settlementDate;
        this.tradingTimestamp = tradingTimestamp;
        this.publicProductIdentification = publicProductIdentification;
        this.price = price;
        this.priceCurrency = priceCurrency;
        this.priceNotation = priceNotation;
        this.quantity = quantity;
        this.nominalValue = nominalValue;
        this.nominalCurrency = nominalCurrency;
        this.mic = mic;
        this.publicationTimestamp = publicationTimestamp;
        this.tradeId = tradeId;
        this.tradeToBeCleared = tradeToBeCleared;
        this.pctChange = pctChange;
        this.vwap = vwap;
        this.noTrades = noTrades;
        this.totalVolume = totalVolume;
        this.totalValue = totalValue;
        this.openingPrice = openingPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.mmtMarketMechanism = mmtMarketMechanism;
        this.mmtTradingMode = mmtTradingMode;
        this.mmtTransactionCategory = mmtTransactionCategory;
        this.mmtNegotiationIndicator = mmtNegotiationIndicator;
        this.mmtAgencyCrossTradeIndicator = mmtAgencyCrossTradeIndicator;
        this.mmtModificationIndicator = mmtModificationIndicator;
        this.mmtBenchmarkReferencePriceIndicator = mmtBenchmarkReferencePriceIndicator;
        this.mmtSpecialDividendIndicator = mmtSpecialDividendIndicator;
        this.mmtOffBookAutomatedIndicator = mmtOffBookAutomatedIndicator;
        this.mmtOrdinaryTradeIndicator = mmtOrdinaryTradeIndicator;
        this.mmtAlgorithmicIndicator = mmtAlgorithmicIndicator;
        this.mmtPostTradeDeferralReason = mmtPostTradeDeferralReason;
        this.mmtPostTradeDeferralType = mmtPostTradeDeferralType;
    }
    
    public Trade(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.settlementValue = BendecUtils.int64FromByteArray(bytes, offset + 46);
        this.settlementDate = BendecUtils.uInt32FromByteArray(bytes, offset + 54);
        this.tradingTimestamp = BendecUtils.uInt64FromByteArray(bytes, offset + 58);
        this.publicProductIdentification = new PublicProductIdentification(bytes, offset + 66);
        this.price = BendecUtils.int64FromByteArray(bytes, offset + 97);
        this.priceCurrency = Currency.getCurrency(bytes, offset + 105);
        this.priceNotation = PriceExpressionType.getPriceExpressionType(bytes, offset + 107);
        this.quantity = BendecUtils.uInt64FromByteArray(bytes, offset + 108);
        this.nominalValue = BendecUtils.int64FromByteArray(bytes, offset + 116);
        this.nominalCurrency = Currency.getCurrency(bytes, offset + 124);
        this.mic = BendecUtils.stringFromByteArray(bytes, offset + 126, 4);
        this.publicationTimestamp = BendecUtils.uInt64FromByteArray(bytes, offset + 130);
        this.tradeId = BendecUtils.uInt32FromByteArray(bytes, offset + 138);
        this.tradeToBeCleared = BendecUtils.booleanFromByteArray(bytes, offset + 142);
        this.pctChange = BendecUtils.int64FromByteArray(bytes, offset + 143);
        this.vwap = BendecUtils.int64FromByteArray(bytes, offset + 151);
        this.noTrades = BendecUtils.uInt64FromByteArray(bytes, offset + 159);
        this.totalVolume = BendecUtils.uInt64FromByteArray(bytes, offset + 167);
        this.totalValue = BendecUtils.int64FromByteArray(bytes, offset + 175);
        this.openingPrice = BendecUtils.int64FromByteArray(bytes, offset + 183);
        this.maxPrice = BendecUtils.int64FromByteArray(bytes, offset + 191);
        this.minPrice = BendecUtils.int64FromByteArray(bytes, offset + 199);
        this.mmtMarketMechanism = MmtMarketMechanism.getMmtMarketMechanism(bytes, offset + 207);
        this.mmtTradingMode = MmtTradingMode.getMmtTradingMode(bytes, offset + 208);
        this.mmtTransactionCategory = MmtTransactionCategory.getMmtTransactionCategory(bytes, offset + 209);
        this.mmtNegotiationIndicator = MmtNegotiationIndicator.getMmtNegotiationIndicator(bytes, offset + 210);
        this.mmtAgencyCrossTradeIndicator = MmtAgencyCrossTradeIndicator.getMmtAgencyCrossTradeIndicator(bytes, offset + 211);
        this.mmtModificationIndicator = MmtModificationIndicator.getMmtModificationIndicator(bytes, offset + 212);
        this.mmtBenchmarkReferencePriceIndicator = MmtBenchmarkReferencePriceIndicator.getMmtBenchmarkReferencePriceIndicator(bytes, offset + 213);
        this.mmtSpecialDividendIndicator = MmtSpecialDividendIndicator.getMmtSpecialDividendIndicator(bytes, offset + 214);
        this.mmtOffBookAutomatedIndicator = MmtOffBookAutomatedIndicator.getMmtOffBookAutomatedIndicator(bytes, offset + 215);
        this.mmtOrdinaryTradeIndicator = MmtOrdinaryTradeIndicator.getMmtOrdinaryTradeIndicator(bytes, offset + 216);
        this.mmtAlgorithmicIndicator = MmtAlgorithmicIndicator.getMmtAlgorithmicIndicator(bytes, offset + 217);
        this.mmtPostTradeDeferralReason = MmtPostTradeDeferralReason.getMmtPostTradeDeferralReason(bytes, offset + 218);
        this.mmtPostTradeDeferralType = MmtPostTradeDeferralType.getMmtPostTradeDeferralType(bytes, offset + 219);
    }
    
    public Trade(byte[] bytes) {
        this(bytes, 0);
    }
    
    public Trade() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return ID of financial instrument.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    }
    
    /**
     * @return Settlement value.
     */
    public long getSettlementValue() {
        return this.settlementValue;
    }
    
    /**
     * @return Settlement date.
     */
    public long getSettlementDate() {
        return this.settlementDate;
    }
    
    /**
     * @return Date and time when the transaction was executed.
     */
    public BigInteger getTradingTimestamp() {
        return this.tradingTimestamp;
    }
    
    /**
     * @return Product identification type and code.
     */
    public PublicProductIdentification getPublicProductIdentification() {
        return this.publicProductIdentification;
    }
    
    /**
     * @return Trade price.
     */
    public long getPrice() {
        return this.price;
    }
    
    /**
     * @return Currency in which the price is expressed (applicable if the price is expressed as monetary value).
     */
    public Currency getPriceCurrency() {
        return this.priceCurrency;
    }
    
    /**
     * @return Indication as to whether the price is expressed in monetary value, in percentage or in yield.
     */
    public PriceExpressionType getPriceNotation() {
        return this.priceNotation;
    }
    
    /**
     * @return Trade quantity.
     */
    public BigInteger getQuantity() {
        return this.quantity;
    }
    
    /**
     * @return Nominal value.
     */
    public long getNominalValue() {
        return this.nominalValue;
    }
    
    /**
     * @return Currency in which the notional is denominated.
     */
    public Currency getNominalCurrency() {
        return this.nominalCurrency;
    }
    
    /**
     * @return Identification of the venue where the transaction was executed.
     */
    public String getMic() {
        return this.mic;
    }
    
    /**
     * @return Date and time when the transaction was published by a trading venue.
     */
    public BigInteger getPublicationTimestamp() {
        return this.publicationTimestamp;
    }
    
    /**
     * @return ID of the trade.
     */
    public long getTradeId() {
        return this.tradeId;
    }
    
    /**
     * @return Code to identify whether the transaction will be cleared.
     */
    public boolean getTradeToBeCleared() {
        return this.tradeToBeCleared;
    }
    
    /**
     * @return Percentage change.
     */
    public long getPctChange() {
        return this.pctChange;
    }
    
    /**
     * @return Volume-weighted average price.
     */
    public long getVwap() {
        return this.vwap;
    }
    
    /**
     * @return Total number of transactions.
     */
    public BigInteger getNoTrades() {
        return this.noTrades;
    }
    
    /**
     * @return Total transaction volume.
     */
    public BigInteger getTotalVolume() {
        return this.totalVolume;
    }
    
    /**
     * @return Total transaction value.
     */
    public long getTotalValue() {
        return this.totalValue;
    }
    
    /**
     * @return The price of the first trade on the current trading day.
     */
    public long getOpeningPrice() {
        return this.openingPrice;
    }
    
    /**
     * @return Highest price of the instrument on the current trading day.
     */
    public long getMaxPrice() {
        return this.maxPrice;
    }
    
    /**
     * @return Lowest price of the instrument on the current trading day.
     */
    public long getMinPrice() {
        return this.minPrice;
    }
    
    /**
     * @return MMT Market Mechanism
     */
    public MmtMarketMechanism getMmtMarketMechanism() {
        return this.mmtMarketMechanism;
    }
    
    /**
     * @return MMT Trading Mode
     */
    public MmtTradingMode getMmtTradingMode() {
        return this.mmtTradingMode;
    }
    
    /**
     * @return MMT Transaction Category
     */
    public MmtTransactionCategory getMmtTransactionCategory() {
        return this.mmtTransactionCategory;
    }
    
    /**
     * @return MMT Negotiation Indicator
     */
    public MmtNegotiationIndicator getMmtNegotiationIndicator() {
        return this.mmtNegotiationIndicator;
    }
    
    /**
     * @return MMT Agency Cross Trade Indicator
     */
    public MmtAgencyCrossTradeIndicator getMmtAgencyCrossTradeIndicator() {
        return this.mmtAgencyCrossTradeIndicator;
    }
    
    /**
     * @return MMT Modification Indicator
     */
    public MmtModificationIndicator getMmtModificationIndicator() {
        return this.mmtModificationIndicator;
    }
    
    /**
     * @return MMT Benchmark / Reference Price Indicator
     */
    public MmtBenchmarkReferencePriceIndicator getMmtBenchmarkReferencePriceIndicator() {
        return this.mmtBenchmarkReferencePriceIndicator;
    }
    
    /**
     * @return MMT Special Dividend Indicator
     */
    public MmtSpecialDividendIndicator getMmtSpecialDividendIndicator() {
        return this.mmtSpecialDividendIndicator;
    }
    
    /**
     * @return MMT Off Book Automated Indicator
     */
    public MmtOffBookAutomatedIndicator getMmtOffBookAutomatedIndicator() {
        return this.mmtOffBookAutomatedIndicator;
    }
    
    /**
     * @return MMT Ordinary Trade Indicator
     */
    public MmtOrdinaryTradeIndicator getMmtOrdinaryTradeIndicator() {
        return this.mmtOrdinaryTradeIndicator;
    }
    
    /**
     * @return MMT Algorithmic Indicator
     */
    public MmtAlgorithmicIndicator getMmtAlgorithmicIndicator() {
        return this.mmtAlgorithmicIndicator;
    }
    
    /**
     * @return MMT Post-Trade Deferral Reason
     */
    public MmtPostTradeDeferralReason getMmtPostTradeDeferralReason() {
        return this.mmtPostTradeDeferralReason;
    }
    
    /**
     * @return MMT Post-Trade Deferral Type
     */
    public MmtPostTradeDeferralType getMmtPostTradeDeferralType() {
        return this.mmtPostTradeDeferralType;
    }
    
    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param instrumentId ID of financial instrument.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }
    
    /**
     * @param settlementValue Settlement value.
     */
    public void setSettlementValue(long settlementValue) {
        this.settlementValue = settlementValue;
    }
    
    /**
     * @param settlementDate Settlement date.
     */
    public void setSettlementDate(long settlementDate) {
        this.settlementDate = settlementDate;
    }
    
    /**
     * @param tradingTimestamp Date and time when the transaction was executed.
     */
    public void setTradingTimestamp(BigInteger tradingTimestamp) {
        this.tradingTimestamp = tradingTimestamp;
    }
    
    /**
     * @param publicProductIdentification Product identification type and code.
     */
    public void setPublicProductIdentification(PublicProductIdentification publicProductIdentification) {
        this.publicProductIdentification = publicProductIdentification;
    }
    
    /**
     * @param price Trade price.
     */
    public void setPrice(long price) {
        this.price = price;
    }
    
    /**
     * @param priceCurrency Currency in which the price is expressed (applicable if the price is expressed as monetary value).
     */
    public void setPriceCurrency(Currency priceCurrency) {
        this.priceCurrency = priceCurrency;
    }
    
    /**
     * @param priceNotation Indication as to whether the price is expressed in monetary value, in percentage or in yield.
     */
    public void setPriceNotation(PriceExpressionType priceNotation) {
        this.priceNotation = priceNotation;
    }
    
    /**
     * @param quantity Trade quantity.
     */
    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }
    
    /**
     * @param nominalValue Nominal value.
     */
    public void setNominalValue(long nominalValue) {
        this.nominalValue = nominalValue;
    }
    
    /**
     * @param nominalCurrency Currency in which the notional is denominated.
     */
    public void setNominalCurrency(Currency nominalCurrency) {
        this.nominalCurrency = nominalCurrency;
    }
    
    /**
     * @param mic Identification of the venue where the transaction was executed.
     */
    public void setMic(String mic) {
        this.mic = mic;
    }
    
    /**
     * @param publicationTimestamp Date and time when the transaction was published by a trading venue.
     */
    public void setPublicationTimestamp(BigInteger publicationTimestamp) {
        this.publicationTimestamp = publicationTimestamp;
    }
    
    /**
     * @param tradeId ID of the trade.
     */
    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }
    
    /**
     * @param tradeToBeCleared Code to identify whether the transaction will be cleared.
     */
    public void setTradeToBeCleared(boolean tradeToBeCleared) {
        this.tradeToBeCleared = tradeToBeCleared;
    }
    
    /**
     * @param pctChange Percentage change.
     */
    public void setPctChange(long pctChange) {
        this.pctChange = pctChange;
    }
    
    /**
     * @param vwap Volume-weighted average price.
     */
    public void setVwap(long vwap) {
        this.vwap = vwap;
    }
    
    /**
     * @param noTrades Total number of transactions.
     */
    public void setNoTrades(BigInteger noTrades) {
        this.noTrades = noTrades;
    }
    
    /**
     * @param totalVolume Total transaction volume.
     */
    public void setTotalVolume(BigInteger totalVolume) {
        this.totalVolume = totalVolume;
    }
    
    /**
     * @param totalValue Total transaction value.
     */
    public void setTotalValue(long totalValue) {
        this.totalValue = totalValue;
    }
    
    /**
     * @param openingPrice The price of the first trade on the current trading day.
     */
    public void setOpeningPrice(long openingPrice) {
        this.openingPrice = openingPrice;
    }
    
    /**
     * @param maxPrice Highest price of the instrument on the current trading day.
     */
    public void setMaxPrice(long maxPrice) {
        this.maxPrice = maxPrice;
    }
    
    /**
     * @param minPrice Lowest price of the instrument on the current trading day.
     */
    public void setMinPrice(long minPrice) {
        this.minPrice = minPrice;
    }
    
    /**
     * @param mmtMarketMechanism MMT Market Mechanism
     */
    public void setMmtMarketMechanism(MmtMarketMechanism mmtMarketMechanism) {
        this.mmtMarketMechanism = mmtMarketMechanism;
    }
    
    /**
     * @param mmtTradingMode MMT Trading Mode
     */
    public void setMmtTradingMode(MmtTradingMode mmtTradingMode) {
        this.mmtTradingMode = mmtTradingMode;
    }
    
    /**
     * @param mmtTransactionCategory MMT Transaction Category
     */
    public void setMmtTransactionCategory(MmtTransactionCategory mmtTransactionCategory) {
        this.mmtTransactionCategory = mmtTransactionCategory;
    }
    
    /**
     * @param mmtNegotiationIndicator MMT Negotiation Indicator
     */
    public void setMmtNegotiationIndicator(MmtNegotiationIndicator mmtNegotiationIndicator) {
        this.mmtNegotiationIndicator = mmtNegotiationIndicator;
    }
    
    /**
     * @param mmtAgencyCrossTradeIndicator MMT Agency Cross Trade Indicator
     */
    public void setMmtAgencyCrossTradeIndicator(MmtAgencyCrossTradeIndicator mmtAgencyCrossTradeIndicator) {
        this.mmtAgencyCrossTradeIndicator = mmtAgencyCrossTradeIndicator;
    }
    
    /**
     * @param mmtModificationIndicator MMT Modification Indicator
     */
    public void setMmtModificationIndicator(MmtModificationIndicator mmtModificationIndicator) {
        this.mmtModificationIndicator = mmtModificationIndicator;
    }
    
    /**
     * @param mmtBenchmarkReferencePriceIndicator MMT Benchmark / Reference Price Indicator
     */
    public void setMmtBenchmarkReferencePriceIndicator(MmtBenchmarkReferencePriceIndicator mmtBenchmarkReferencePriceIndicator) {
        this.mmtBenchmarkReferencePriceIndicator = mmtBenchmarkReferencePriceIndicator;
    }
    
    /**
     * @param mmtSpecialDividendIndicator MMT Special Dividend Indicator
     */
    public void setMmtSpecialDividendIndicator(MmtSpecialDividendIndicator mmtSpecialDividendIndicator) {
        this.mmtSpecialDividendIndicator = mmtSpecialDividendIndicator;
    }
    
    /**
     * @param mmtOffBookAutomatedIndicator MMT Off Book Automated Indicator
     */
    public void setMmtOffBookAutomatedIndicator(MmtOffBookAutomatedIndicator mmtOffBookAutomatedIndicator) {
        this.mmtOffBookAutomatedIndicator = mmtOffBookAutomatedIndicator;
    }
    
    /**
     * @param mmtOrdinaryTradeIndicator MMT Ordinary Trade Indicator
     */
    public void setMmtOrdinaryTradeIndicator(MmtOrdinaryTradeIndicator mmtOrdinaryTradeIndicator) {
        this.mmtOrdinaryTradeIndicator = mmtOrdinaryTradeIndicator;
    }
    
    /**
     * @param mmtAlgorithmicIndicator MMT Algorithmic Indicator
     */
    public void setMmtAlgorithmicIndicator(MmtAlgorithmicIndicator mmtAlgorithmicIndicator) {
        this.mmtAlgorithmicIndicator = mmtAlgorithmicIndicator;
    }
    
    /**
     * @param mmtPostTradeDeferralReason MMT Post-Trade Deferral Reason
     */
    public void setMmtPostTradeDeferralReason(MmtPostTradeDeferralReason mmtPostTradeDeferralReason) {
        this.mmtPostTradeDeferralReason = mmtPostTradeDeferralReason;
    }
    
    /**
     * @param mmtPostTradeDeferralType MMT Post-Trade Deferral Type
     */
    public void setMmtPostTradeDeferralType(MmtPostTradeDeferralType mmtPostTradeDeferralType) {
        this.mmtPostTradeDeferralType = mmtPostTradeDeferralType;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.int64ToByteArray(this.settlementValue));
        buffer.put(BendecUtils.uInt32ToByteArray(this.settlementDate));
        buffer.put(BendecUtils.uInt64ToByteArray(this.tradingTimestamp));
        publicProductIdentification.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        priceCurrency.toBytes(buffer);
        priceNotation.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        buffer.put(BendecUtils.int64ToByteArray(this.nominalValue));
        nominalCurrency.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.mic, 4));
        buffer.put(BendecUtils.uInt64ToByteArray(this.publicationTimestamp));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradeId));
        buffer.put(BendecUtils.booleanToByteArray(this.tradeToBeCleared));
        buffer.put(BendecUtils.int64ToByteArray(this.pctChange));
        buffer.put(BendecUtils.int64ToByteArray(this.vwap));
        buffer.put(BendecUtils.uInt64ToByteArray(this.noTrades));
        buffer.put(BendecUtils.uInt64ToByteArray(this.totalVolume));
        buffer.put(BendecUtils.int64ToByteArray(this.totalValue));
        buffer.put(BendecUtils.int64ToByteArray(this.openingPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.maxPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.minPrice));
        mmtMarketMechanism.toBytes(buffer);
        mmtTradingMode.toBytes(buffer);
        mmtTransactionCategory.toBytes(buffer);
        mmtNegotiationIndicator.toBytes(buffer);
        mmtAgencyCrossTradeIndicator.toBytes(buffer);
        mmtModificationIndicator.toBytes(buffer);
        mmtBenchmarkReferencePriceIndicator.toBytes(buffer);
        mmtSpecialDividendIndicator.toBytes(buffer);
        mmtOffBookAutomatedIndicator.toBytes(buffer);
        mmtOrdinaryTradeIndicator.toBytes(buffer);
        mmtAlgorithmicIndicator.toBytes(buffer);
        mmtPostTradeDeferralReason.toBytes(buffer);
        mmtPostTradeDeferralType.toBytes(buffer);
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.int64ToByteArray(this.settlementValue));
        buffer.put(BendecUtils.uInt32ToByteArray(this.settlementDate));
        buffer.put(BendecUtils.uInt64ToByteArray(this.tradingTimestamp));
        publicProductIdentification.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        priceCurrency.toBytes(buffer);
        priceNotation.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        buffer.put(BendecUtils.int64ToByteArray(this.nominalValue));
        nominalCurrency.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.mic, 4));
        buffer.put(BendecUtils.uInt64ToByteArray(this.publicationTimestamp));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradeId));
        buffer.put(BendecUtils.booleanToByteArray(this.tradeToBeCleared));
        buffer.put(BendecUtils.int64ToByteArray(this.pctChange));
        buffer.put(BendecUtils.int64ToByteArray(this.vwap));
        buffer.put(BendecUtils.uInt64ToByteArray(this.noTrades));
        buffer.put(BendecUtils.uInt64ToByteArray(this.totalVolume));
        buffer.put(BendecUtils.int64ToByteArray(this.totalValue));
        buffer.put(BendecUtils.int64ToByteArray(this.openingPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.maxPrice));
        buffer.put(BendecUtils.int64ToByteArray(this.minPrice));
        mmtMarketMechanism.toBytes(buffer);
        mmtTradingMode.toBytes(buffer);
        mmtTransactionCategory.toBytes(buffer);
        mmtNegotiationIndicator.toBytes(buffer);
        mmtAgencyCrossTradeIndicator.toBytes(buffer);
        mmtModificationIndicator.toBytes(buffer);
        mmtBenchmarkReferencePriceIndicator.toBytes(buffer);
        mmtSpecialDividendIndicator.toBytes(buffer);
        mmtOffBookAutomatedIndicator.toBytes(buffer);
        mmtOrdinaryTradeIndicator.toBytes(buffer);
        mmtAlgorithmicIndicator.toBytes(buffer);
        mmtPostTradeDeferralReason.toBytes(buffer);
        mmtPostTradeDeferralType.toBytes(buffer);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        settlementValue,
        settlementDate,
        tradingTimestamp,
        publicProductIdentification,
        price,
        priceCurrency,
        priceNotation,
        quantity,
        nominalValue,
        nominalCurrency,
        mic,
        publicationTimestamp,
        tradeId,
        tradeToBeCleared,
        pctChange,
        vwap,
        noTrades,
        totalVolume,
        totalValue,
        openingPrice,
        maxPrice,
        minPrice,
        mmtMarketMechanism,
        mmtTradingMode,
        mmtTransactionCategory,
        mmtNegotiationIndicator,
        mmtAgencyCrossTradeIndicator,
        mmtModificationIndicator,
        mmtBenchmarkReferencePriceIndicator,
        mmtSpecialDividendIndicator,
        mmtOffBookAutomatedIndicator,
        mmtOrdinaryTradeIndicator,
        mmtAlgorithmicIndicator,
        mmtPostTradeDeferralReason,
        mmtPostTradeDeferralType);
    }
    
    @Override
    public String toString() {
        return "Trade {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", settlementValue=" + settlementValue +
            ", settlementDate=" + settlementDate +
            ", tradingTimestamp=" + tradingTimestamp +
            ", publicProductIdentification=" + publicProductIdentification +
            ", price=" + price +
            ", priceCurrency=" + priceCurrency +
            ", priceNotation=" + priceNotation +
            ", quantity=" + quantity +
            ", nominalValue=" + nominalValue +
            ", nominalCurrency=" + nominalCurrency +
            ", mic=" + mic +
            ", publicationTimestamp=" + publicationTimestamp +
            ", tradeId=" + tradeId +
            ", tradeToBeCleared=" + tradeToBeCleared +
            ", pctChange=" + pctChange +
            ", vwap=" + vwap +
            ", noTrades=" + noTrades +
            ", totalVolume=" + totalVolume +
            ", totalValue=" + totalValue +
            ", openingPrice=" + openingPrice +
            ", maxPrice=" + maxPrice +
            ", minPrice=" + minPrice +
            ", mmtMarketMechanism=" + mmtMarketMechanism +
            ", mmtTradingMode=" + mmtTradingMode +
            ", mmtTransactionCategory=" + mmtTransactionCategory +
            ", mmtNegotiationIndicator=" + mmtNegotiationIndicator +
            ", mmtAgencyCrossTradeIndicator=" + mmtAgencyCrossTradeIndicator +
            ", mmtModificationIndicator=" + mmtModificationIndicator +
            ", mmtBenchmarkReferencePriceIndicator=" + mmtBenchmarkReferencePriceIndicator +
            ", mmtSpecialDividendIndicator=" + mmtSpecialDividendIndicator +
            ", mmtOffBookAutomatedIndicator=" + mmtOffBookAutomatedIndicator +
            ", mmtOrdinaryTradeIndicator=" + mmtOrdinaryTradeIndicator +
            ", mmtAlgorithmicIndicator=" + mmtAlgorithmicIndicator +
            ", mmtPostTradeDeferralReason=" + mmtPostTradeDeferralReason +
            ", mmtPostTradeDeferralType=" + mmtPostTradeDeferralType +
            "}";
    }
}