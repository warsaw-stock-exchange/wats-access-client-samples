package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>Trade</h2>
 * <p>Enriched trade report.</p>
 * <p>Byte length: 153</p>
 * <p>Header header - Message header. | size 39</p>
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
 * <p>MmtMarketMechanism mmtMarketMechanism - MMT Market Mechanism | size 1</p>
 * <p>MmtTradingMode mmtTradingMode - MMT Trading Mode | size 1</p>
 * <p>MmtTransationCategory mmtTransationCategory - MMT Transaction Category | size 1</p>
 * <p>MmtNegotitationIndicator mmtNegotitationIndicator - MMT Negotitation Indicator | size 1</p>
 * <p>MmtAgencyCrossTradeIndicator mmtAgencyCrossTradeIndicator - MMT Agency Cross Trade Indicator | size 1</p>
 * <p>MmtModificationIndicator mmtModificationIndicator - MMT Modification Indicator | size 1</p>
 * <p>MmtBenchmarkReferencePriceIndicator mmtBenchmarkReferencePriceIndicator - MMT Benchmark / Reference Price Indicator | size 1</p>
 * <p>MmtSpecialDividendIndicator mmtSpecialDividendIndicator - MMT Special Dividend Indicator | size 1</p>
 * <p>MmtOffBookAutomatedIndicator mmtOffBookAutomatedIndicator - MMT Off Book Automated Indicator | size 1</p>
 * <p>MmtOrdinaryTradeIndicator mmtOrdinaryTradeIndicator - MMT Ordinary Trade Indicator | size 1</p>
 * <p>MmtAlgorithmicIndicator mmtAlgorithmicIndicator - MMT Algorithmic Indicator | size 1</p>
 * <p>MmtPostTradeDeferralReason mmtPostTradeDeferralReason - MMT Post-Trade Deferral Reason | size 1</p>
 * <p>MmtPostTradeDeferralType mmtPostTradeDeferralType - MMT Post-Trade Deferral Type | size 1</p>
 * */

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
    private MmtMarketMechanism mmtMarketMechanism;
    private MmtTradingMode mmtTradingMode;
    private MmtTransationCategory mmtTransationCategory;
    private MmtNegotitationIndicator mmtNegotitationIndicator;
    private MmtAgencyCrossTradeIndicator mmtAgencyCrossTradeIndicator;
    private MmtModificationIndicator mmtModificationIndicator;
    private MmtBenchmarkReferencePriceIndicator mmtBenchmarkReferencePriceIndicator;
    private MmtSpecialDividendIndicator mmtSpecialDividendIndicator;
    private MmtOffBookAutomatedIndicator mmtOffBookAutomatedIndicator;
    private MmtOrdinaryTradeIndicator mmtOrdinaryTradeIndicator;
    private MmtAlgorithmicIndicator mmtAlgorithmicIndicator;
    private MmtPostTradeDeferralReason mmtPostTradeDeferralReason;
    private MmtPostTradeDeferralType mmtPostTradeDeferralType;
    public static final int byteLength = 153;

    public Trade(Header header, long instrumentId, long settlementValue, long settlementDate, BigInteger tradingTimestamp, PublicProductIdentification publicProductIdentification, long price, Currency priceCurrency, PriceExpressionType priceNotation, BigInteger quantity, long nominalValue, Currency nominalCurrency, String mic, BigInteger publicationTimestamp, long tradeId, boolean tradeToBeCleared, MmtMarketMechanism mmtMarketMechanism, MmtTradingMode mmtTradingMode, MmtTransationCategory mmtTransationCategory, MmtNegotitationIndicator mmtNegotitationIndicator, MmtAgencyCrossTradeIndicator mmtAgencyCrossTradeIndicator, MmtModificationIndicator mmtModificationIndicator, MmtBenchmarkReferencePriceIndicator mmtBenchmarkReferencePriceIndicator, MmtSpecialDividendIndicator mmtSpecialDividendIndicator, MmtOffBookAutomatedIndicator mmtOffBookAutomatedIndicator, MmtOrdinaryTradeIndicator mmtOrdinaryTradeIndicator, MmtAlgorithmicIndicator mmtAlgorithmicIndicator, MmtPostTradeDeferralReason mmtPostTradeDeferralReason, MmtPostTradeDeferralType mmtPostTradeDeferralType) {
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
        this.mmtMarketMechanism = mmtMarketMechanism;
        this.mmtTradingMode = mmtTradingMode;
        this.mmtTransationCategory = mmtTransationCategory;
        this.mmtNegotitationIndicator = mmtNegotitationIndicator;
        this.mmtAgencyCrossTradeIndicator = mmtAgencyCrossTradeIndicator;
        this.mmtModificationIndicator = mmtModificationIndicator;
        this.mmtBenchmarkReferencePriceIndicator = mmtBenchmarkReferencePriceIndicator;
        this.mmtSpecialDividendIndicator = mmtSpecialDividendIndicator;
        this.mmtOffBookAutomatedIndicator = mmtOffBookAutomatedIndicator;
        this.mmtOrdinaryTradeIndicator = mmtOrdinaryTradeIndicator;
        this.mmtAlgorithmicIndicator = mmtAlgorithmicIndicator;
        this.mmtPostTradeDeferralReason = mmtPostTradeDeferralReason;
        this.mmtPostTradeDeferralType = mmtPostTradeDeferralType;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.TRADE);
    }

    public Trade(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 39);
        this.settlementValue = BendecUtils.int64FromByteArray(bytes, offset + 43);
        this.settlementDate = BendecUtils.uInt32FromByteArray(bytes, offset + 51);
        this.tradingTimestamp = BendecUtils.uInt64FromByteArray(bytes, offset + 55);
        this.publicProductIdentification = new PublicProductIdentification(bytes, offset + 63);
        this.price = BendecUtils.int64FromByteArray(bytes, offset + 94);
        this.priceCurrency = Currency.getCurrency(bytes, offset + 102);
        this.priceNotation = PriceExpressionType.getPriceExpressionType(bytes, offset + 104);
        this.quantity = BendecUtils.uInt64FromByteArray(bytes, offset + 105);
        this.nominalValue = BendecUtils.int64FromByteArray(bytes, offset + 113);
        this.nominalCurrency = Currency.getCurrency(bytes, offset + 121);
        this.mic = BendecUtils.stringFromByteArray(bytes, offset + 123, 4);
        this.publicationTimestamp = BendecUtils.uInt64FromByteArray(bytes, offset + 127);
        this.tradeId = BendecUtils.uInt32FromByteArray(bytes, offset + 135);
        this.tradeToBeCleared = BendecUtils.booleanFromByteArray(bytes, offset + 139);
        this.mmtMarketMechanism = MmtMarketMechanism.getMmtMarketMechanism(bytes, offset + 140);
        this.mmtTradingMode = MmtTradingMode.getMmtTradingMode(bytes, offset + 141);
        this.mmtTransationCategory = MmtTransationCategory.getMmtTransationCategory(bytes, offset + 142);
        this.mmtNegotitationIndicator = MmtNegotitationIndicator.getMmtNegotitationIndicator(bytes, offset + 143);
        this.mmtAgencyCrossTradeIndicator = MmtAgencyCrossTradeIndicator.getMmtAgencyCrossTradeIndicator(bytes, offset + 144);
        this.mmtModificationIndicator = MmtModificationIndicator.getMmtModificationIndicator(bytes, offset + 145);
        this.mmtBenchmarkReferencePriceIndicator = MmtBenchmarkReferencePriceIndicator.getMmtBenchmarkReferencePriceIndicator(bytes, offset + 146);
        this.mmtSpecialDividendIndicator = MmtSpecialDividendIndicator.getMmtSpecialDividendIndicator(bytes, offset + 147);
        this.mmtOffBookAutomatedIndicator = MmtOffBookAutomatedIndicator.getMmtOffBookAutomatedIndicator(bytes, offset + 148);
        this.mmtOrdinaryTradeIndicator = MmtOrdinaryTradeIndicator.getMmtOrdinaryTradeIndicator(bytes, offset + 149);
        this.mmtAlgorithmicIndicator = MmtAlgorithmicIndicator.getMmtAlgorithmicIndicator(bytes, offset + 150);
        this.mmtPostTradeDeferralReason = MmtPostTradeDeferralReason.getMmtPostTradeDeferralReason(bytes, offset + 151);
        this.mmtPostTradeDeferralType = MmtPostTradeDeferralType.getMmtPostTradeDeferralType(bytes, offset + 152);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.TRADE);
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
    };
    /**
     * @return ID of financial instrument.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    };
    /**
     * @return Settlement value.
     */
    public long getSettlementValue() {
        return this.settlementValue;
    };
    /**
     * @return Settlement date.
     */
    public long getSettlementDate() {
        return this.settlementDate;
    };
    /**
     * @return Date and time when the transaction was executed.
     */
    public BigInteger getTradingTimestamp() {
        return this.tradingTimestamp;
    };
    /**
     * @return Product identification type and code.
     */
    public PublicProductIdentification getPublicProductIdentification() {
        return this.publicProductIdentification;
    };
    /**
     * @return Trade price.
     */
    public long getPrice() {
        return this.price;
    };
    /**
     * @return Currency in which the price is expressed (applicable if the price is expressed as monetary value).
     */
    public Currency getPriceCurrency() {
        return this.priceCurrency;
    };
    /**
     * @return Indication as to whether the price is expressed in monetary value, in percentage or in yield.
     */
    public PriceExpressionType getPriceNotation() {
        return this.priceNotation;
    };
    /**
     * @return Trade quantity.
     */
    public BigInteger getQuantity() {
        return this.quantity;
    };
    /**
     * @return Nominal value.
     */
    public long getNominalValue() {
        return this.nominalValue;
    };
    /**
     * @return Currency in which the notional is denominated.
     */
    public Currency getNominalCurrency() {
        return this.nominalCurrency;
    };
    /**
     * @return Identification of the venue where the transaction was executed.
     */
    public String getMic() {
        return this.mic;
    };
    /**
     * @return Date and time when the transaction was published by a trading venue.
     */
    public BigInteger getPublicationTimestamp() {
        return this.publicationTimestamp;
    };
    /**
     * @return ID of the trade.
     */
    public long getTradeId() {
        return this.tradeId;
    };
    /**
     * @return Code to identify whether the transaction will be cleared.
     */
    public boolean getTradeToBeCleared() {
        return this.tradeToBeCleared;
    };
    /**
     * @return MMT Market Mechanism
     */
    public MmtMarketMechanism getMmtMarketMechanism() {
        return this.mmtMarketMechanism;
    };
    /**
     * @return MMT Trading Mode
     */
    public MmtTradingMode getMmtTradingMode() {
        return this.mmtTradingMode;
    };
    /**
     * @return MMT Transaction Category
     */
    public MmtTransationCategory getMmtTransationCategory() {
        return this.mmtTransationCategory;
    };
    /**
     * @return MMT Negotitation Indicator
     */
    public MmtNegotitationIndicator getMmtNegotitationIndicator() {
        return this.mmtNegotitationIndicator;
    };
    /**
     * @return MMT Agency Cross Trade Indicator
     */
    public MmtAgencyCrossTradeIndicator getMmtAgencyCrossTradeIndicator() {
        return this.mmtAgencyCrossTradeIndicator;
    };
    /**
     * @return MMT Modification Indicator
     */
    public MmtModificationIndicator getMmtModificationIndicator() {
        return this.mmtModificationIndicator;
    };
    /**
     * @return MMT Benchmark / Reference Price Indicator
     */
    public MmtBenchmarkReferencePriceIndicator getMmtBenchmarkReferencePriceIndicator() {
        return this.mmtBenchmarkReferencePriceIndicator;
    };
    /**
     * @return MMT Special Dividend Indicator
     */
    public MmtSpecialDividendIndicator getMmtSpecialDividendIndicator() {
        return this.mmtSpecialDividendIndicator;
    };
    /**
     * @return MMT Off Book Automated Indicator
     */
    public MmtOffBookAutomatedIndicator getMmtOffBookAutomatedIndicator() {
        return this.mmtOffBookAutomatedIndicator;
    };
    /**
     * @return MMT Ordinary Trade Indicator
     */
    public MmtOrdinaryTradeIndicator getMmtOrdinaryTradeIndicator() {
        return this.mmtOrdinaryTradeIndicator;
    };
    /**
     * @return MMT Algorithmic Indicator
     */
    public MmtAlgorithmicIndicator getMmtAlgorithmicIndicator() {
        return this.mmtAlgorithmicIndicator;
    };
    /**
     * @return MMT Post-Trade Deferral Reason
     */
    public MmtPostTradeDeferralReason getMmtPostTradeDeferralReason() {
        return this.mmtPostTradeDeferralReason;
    };
    /**
     * @return MMT Post-Trade Deferral Type
     */
    public MmtPostTradeDeferralType getMmtPostTradeDeferralType() {
        return this.mmtPostTradeDeferralType;
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
     * @param settlementValue Settlement value.
     */
    public void setSettlementValue(long settlementValue) {
        this.settlementValue = settlementValue;
    };
    /**
     * @param settlementDate Settlement date.
     */
    public void setSettlementDate(long settlementDate) {
        this.settlementDate = settlementDate;
    };
    /**
     * @param tradingTimestamp Date and time when the transaction was executed.
     */
    public void setTradingTimestamp(BigInteger tradingTimestamp) {
        this.tradingTimestamp = tradingTimestamp;
    };
    /**
     * @param publicProductIdentification Product identification type and code.
     */
    public void setPublicProductIdentification(PublicProductIdentification publicProductIdentification) {
        this.publicProductIdentification = publicProductIdentification;
    };
    /**
     * @param price Trade price.
     */
    public void setPrice(long price) {
        this.price = price;
    };
    /**
     * @param priceCurrency Currency in which the price is expressed (applicable if the price is expressed as monetary value).
     */
    public void setPriceCurrency(Currency priceCurrency) {
        this.priceCurrency = priceCurrency;
    };
    /**
     * @param priceNotation Indication as to whether the price is expressed in monetary value, in percentage or in yield.
     */
    public void setPriceNotation(PriceExpressionType priceNotation) {
        this.priceNotation = priceNotation;
    };
    /**
     * @param quantity Trade quantity.
     */
    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    };
    /**
     * @param nominalValue Nominal value.
     */
    public void setNominalValue(long nominalValue) {
        this.nominalValue = nominalValue;
    };
    /**
     * @param nominalCurrency Currency in which the notional is denominated.
     */
    public void setNominalCurrency(Currency nominalCurrency) {
        this.nominalCurrency = nominalCurrency;
    };
    /**
     * @param mic Identification of the venue where the transaction was executed.
     */
    public void setMic(String mic) {
        this.mic = mic;
    };
    /**
     * @param publicationTimestamp Date and time when the transaction was published by a trading venue.
     */
    public void setPublicationTimestamp(BigInteger publicationTimestamp) {
        this.publicationTimestamp = publicationTimestamp;
    };
    /**
     * @param tradeId ID of the trade.
     */
    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    };
    /**
     * @param tradeToBeCleared Code to identify whether the transaction will be cleared.
     */
    public void setTradeToBeCleared(boolean tradeToBeCleared) {
        this.tradeToBeCleared = tradeToBeCleared;
    };
    /**
     * @param mmtMarketMechanism MMT Market Mechanism
     */
    public void setMmtMarketMechanism(MmtMarketMechanism mmtMarketMechanism) {
        this.mmtMarketMechanism = mmtMarketMechanism;
    };
    /**
     * @param mmtTradingMode MMT Trading Mode
     */
    public void setMmtTradingMode(MmtTradingMode mmtTradingMode) {
        this.mmtTradingMode = mmtTradingMode;
    };
    /**
     * @param mmtTransationCategory MMT Transaction Category
     */
    public void setMmtTransationCategory(MmtTransationCategory mmtTransationCategory) {
        this.mmtTransationCategory = mmtTransationCategory;
    };
    /**
     * @param mmtNegotitationIndicator MMT Negotitation Indicator
     */
    public void setMmtNegotitationIndicator(MmtNegotitationIndicator mmtNegotitationIndicator) {
        this.mmtNegotitationIndicator = mmtNegotitationIndicator;
    };
    /**
     * @param mmtAgencyCrossTradeIndicator MMT Agency Cross Trade Indicator
     */
    public void setMmtAgencyCrossTradeIndicator(MmtAgencyCrossTradeIndicator mmtAgencyCrossTradeIndicator) {
        this.mmtAgencyCrossTradeIndicator = mmtAgencyCrossTradeIndicator;
    };
    /**
     * @param mmtModificationIndicator MMT Modification Indicator
     */
    public void setMmtModificationIndicator(MmtModificationIndicator mmtModificationIndicator) {
        this.mmtModificationIndicator = mmtModificationIndicator;
    };
    /**
     * @param mmtBenchmarkReferencePriceIndicator MMT Benchmark / Reference Price Indicator
     */
    public void setMmtBenchmarkReferencePriceIndicator(MmtBenchmarkReferencePriceIndicator mmtBenchmarkReferencePriceIndicator) {
        this.mmtBenchmarkReferencePriceIndicator = mmtBenchmarkReferencePriceIndicator;
    };
    /**
     * @param mmtSpecialDividendIndicator MMT Special Dividend Indicator
     */
    public void setMmtSpecialDividendIndicator(MmtSpecialDividendIndicator mmtSpecialDividendIndicator) {
        this.mmtSpecialDividendIndicator = mmtSpecialDividendIndicator;
    };
    /**
     * @param mmtOffBookAutomatedIndicator MMT Off Book Automated Indicator
     */
    public void setMmtOffBookAutomatedIndicator(MmtOffBookAutomatedIndicator mmtOffBookAutomatedIndicator) {
        this.mmtOffBookAutomatedIndicator = mmtOffBookAutomatedIndicator;
    };
    /**
     * @param mmtOrdinaryTradeIndicator MMT Ordinary Trade Indicator
     */
    public void setMmtOrdinaryTradeIndicator(MmtOrdinaryTradeIndicator mmtOrdinaryTradeIndicator) {
        this.mmtOrdinaryTradeIndicator = mmtOrdinaryTradeIndicator;
    };
    /**
     * @param mmtAlgorithmicIndicator MMT Algorithmic Indicator
     */
    public void setMmtAlgorithmicIndicator(MmtAlgorithmicIndicator mmtAlgorithmicIndicator) {
        this.mmtAlgorithmicIndicator = mmtAlgorithmicIndicator;
    };
    /**
     * @param mmtPostTradeDeferralReason MMT Post-Trade Deferral Reason
     */
    public void setMmtPostTradeDeferralReason(MmtPostTradeDeferralReason mmtPostTradeDeferralReason) {
        this.mmtPostTradeDeferralReason = mmtPostTradeDeferralReason;
    };
    /**
     * @param mmtPostTradeDeferralType MMT Post-Trade Deferral Type
     */
    public void setMmtPostTradeDeferralType(MmtPostTradeDeferralType mmtPostTradeDeferralType) {
        this.mmtPostTradeDeferralType = mmtPostTradeDeferralType;
    };


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
        mmtMarketMechanism.toBytes(buffer);
        mmtTradingMode.toBytes(buffer);
        mmtTransationCategory.toBytes(buffer);
        mmtNegotitationIndicator.toBytes(buffer);
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
        mmtMarketMechanism.toBytes(buffer);
        mmtTradingMode.toBytes(buffer);
        mmtTransationCategory.toBytes(buffer);
        mmtNegotitationIndicator.toBytes(buffer);
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
        return Objects.hash(header, instrumentId, settlementValue, settlementDate, tradingTimestamp, publicProductIdentification, price, priceCurrency, priceNotation, quantity, nominalValue, nominalCurrency, mic, publicationTimestamp, tradeId, tradeToBeCleared, mmtMarketMechanism, mmtTradingMode, mmtTransationCategory, mmtNegotitationIndicator, mmtAgencyCrossTradeIndicator, mmtModificationIndicator, mmtBenchmarkReferencePriceIndicator, mmtSpecialDividendIndicator, mmtOffBookAutomatedIndicator, mmtOrdinaryTradeIndicator, mmtAlgorithmicIndicator, mmtPostTradeDeferralReason, mmtPostTradeDeferralType);
    }

    @Override
    public String toString() {
        return "Trade{" +
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
            ", mmtMarketMechanism=" + mmtMarketMechanism +
            ", mmtTradingMode=" + mmtTradingMode +
            ", mmtTransationCategory=" + mmtTransationCategory +
            ", mmtNegotitationIndicator=" + mmtNegotitationIndicator +
            ", mmtAgencyCrossTradeIndicator=" + mmtAgencyCrossTradeIndicator +
            ", mmtModificationIndicator=" + mmtModificationIndicator +
            ", mmtBenchmarkReferencePriceIndicator=" + mmtBenchmarkReferencePriceIndicator +
            ", mmtSpecialDividendIndicator=" + mmtSpecialDividendIndicator +
            ", mmtOffBookAutomatedIndicator=" + mmtOffBookAutomatedIndicator +
            ", mmtOrdinaryTradeIndicator=" + mmtOrdinaryTradeIndicator +
            ", mmtAlgorithmicIndicator=" + mmtAlgorithmicIndicator +
            ", mmtPostTradeDeferralReason=" + mmtPostTradeDeferralReason +
            ", mmtPostTradeDeferralType=" + mmtPostTradeDeferralType +
            '}';
        }
}
