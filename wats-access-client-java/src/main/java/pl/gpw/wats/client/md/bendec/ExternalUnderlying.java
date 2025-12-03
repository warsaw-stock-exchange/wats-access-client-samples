package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>ExternalUnderlying</h2>
 * <p>The message contains a list of underlyings not traded in WATS. It concerns underlyings for derivatives and structured products.</p>
 * <p>Byte length: 155</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) externalUnderlyingId - External underlying ID. | size 4</p>
 * <p>ProductIdentification > String (u8[]) productIdentification - Product identification. | size 30</p>
 * <p>ProductIdentificationType productIdentificationType - Product identification type. | size 1</p>
 * <p>Code > String (u8[]) productCode - Product code. | size 20</p>
 * <p>Name > String (u8[]) productName - Product name. | size 50</p>
 * <p>MicCode > String (u8[]) mic - Market Identifier Code. | size 4</p>
 * <p>Currency nominalCurrency - Nominal currency. | size 2</p>
 * <p>Currency tradingCurrency - Trading currency. | size 2</p>
 */
public class ExternalUnderlying implements ByteSerializable, Message {
    private Header header;
    private long externalUnderlyingId;
    private String productIdentification;
    private ProductIdentificationType productIdentificationType;
    private String productCode;
    private String productName;
    private String mic;
    private Currency nominalCurrency;
    private Currency tradingCurrency;
    public static final int byteLength = 155;
    
    public ExternalUnderlying(Header header, long externalUnderlyingId, String productIdentification, ProductIdentificationType productIdentificationType, String productCode, String productName, String mic, Currency nominalCurrency, Currency tradingCurrency) {
        this.header = header;
        this.externalUnderlyingId = externalUnderlyingId;
        this.productIdentification = productIdentification;
        this.productIdentificationType = productIdentificationType;
        this.productCode = productCode;
        this.productName = productName;
        this.mic = mic;
        this.nominalCurrency = nominalCurrency;
        this.tradingCurrency = tradingCurrency;
    }
    
    public ExternalUnderlying(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.externalUnderlyingId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.productIdentification = BendecUtils.stringFromByteArray(bytes, offset + 46, 30);
        this.productIdentificationType = ProductIdentificationType.getProductIdentificationType(bytes, offset + 76);
        this.productCode = BendecUtils.stringFromByteArray(bytes, offset + 77, 20);
        this.productName = BendecUtils.stringFromByteArray(bytes, offset + 97, 50);
        this.mic = BendecUtils.stringFromByteArray(bytes, offset + 147, 4);
        this.nominalCurrency = Currency.getCurrency(bytes, offset + 151);
        this.tradingCurrency = Currency.getCurrency(bytes, offset + 153);
    }
    
    public ExternalUnderlying(byte[] bytes) {
        this(bytes, 0);
    }
    
    public ExternalUnderlying() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return External underlying ID.
     */
    public long getExternalUnderlyingId() {
        return this.externalUnderlyingId;
    }
    
    /**
     * @return Product identification.
     */
    public String getProductIdentification() {
        return this.productIdentification;
    }
    
    /**
     * @return Product identification type.
     */
    public ProductIdentificationType getProductIdentificationType() {
        return this.productIdentificationType;
    }
    
    /**
     * @return Product code.
     */
    public String getProductCode() {
        return this.productCode;
    }
    
    /**
     * @return Product name.
     */
    public String getProductName() {
        return this.productName;
    }
    
    /**
     * @return Market Identifier Code.
     */
    public String getMic() {
        return this.mic;
    }
    
    /**
     * @return Nominal currency.
     */
    public Currency getNominalCurrency() {
        return this.nominalCurrency;
    }
    
    /**
     * @return Trading currency.
     */
    public Currency getTradingCurrency() {
        return this.tradingCurrency;
    }
    
    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param externalUnderlyingId External underlying ID.
     */
    public void setExternalUnderlyingId(long externalUnderlyingId) {
        this.externalUnderlyingId = externalUnderlyingId;
    }
    
    /**
     * @param productIdentification Product identification.
     */
    public void setProductIdentification(String productIdentification) {
        this.productIdentification = productIdentification;
    }
    
    /**
     * @param productIdentificationType Product identification type.
     */
    public void setProductIdentificationType(ProductIdentificationType productIdentificationType) {
        this.productIdentificationType = productIdentificationType;
    }
    
    /**
     * @param productCode Product code.
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    
    /**
     * @param productName Product name.
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    /**
     * @param mic Market Identifier Code.
     */
    public void setMic(String mic) {
        this.mic = mic;
    }
    
    /**
     * @param nominalCurrency Nominal currency.
     */
    public void setNominalCurrency(Currency nominalCurrency) {
        this.nominalCurrency = nominalCurrency;
    }
    
    /**
     * @param tradingCurrency Trading currency.
     */
    public void setTradingCurrency(Currency tradingCurrency) {
        this.tradingCurrency = tradingCurrency;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.externalUnderlyingId));
        buffer.put(BendecUtils.stringToByteArray(this.productIdentification, 30));
        productIdentificationType.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.productCode, 20));
        buffer.put(BendecUtils.stringToByteArray(this.productName, 50));
        buffer.put(BendecUtils.stringToByteArray(this.mic, 4));
        nominalCurrency.toBytes(buffer);
        tradingCurrency.toBytes(buffer);
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.externalUnderlyingId));
        buffer.put(BendecUtils.stringToByteArray(this.productIdentification, 30));
        productIdentificationType.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.productCode, 20));
        buffer.put(BendecUtils.stringToByteArray(this.productName, 50));
        buffer.put(BendecUtils.stringToByteArray(this.mic, 4));
        nominalCurrency.toBytes(buffer);
        tradingCurrency.toBytes(buffer);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        externalUnderlyingId,
        productIdentification,
        productIdentificationType,
        productCode,
        productName,
        mic,
        nominalCurrency,
        tradingCurrency);
    }
    
    @Override
    public String toString() {
        return "ExternalUnderlying {" +
            "header=" + header +
            ", externalUnderlyingId=" + externalUnderlyingId +
            ", productIdentification=" + productIdentification +
            ", productIdentificationType=" + productIdentificationType +
            ", productCode=" + productCode +
            ", productName=" + productName +
            ", mic=" + mic +
            ", nominalCurrency=" + nominalCurrency +
            ", tradingCurrency=" + tradingCurrency +
            "}";
    }
}