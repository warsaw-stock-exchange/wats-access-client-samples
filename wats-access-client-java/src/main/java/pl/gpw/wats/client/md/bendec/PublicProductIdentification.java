package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>PublicProductIdentification</h2>
 * <p>Product identification based on well known identification types, such as ISIN.</p>
 * <p>Byte length: 31</p>
 * <p>ProductIdentification > String (u8[]) productIdentification - Product identification, for example its ISIN code. | size 30</p>
 * <p>ProductIdentificationType productIdentificationType - Type of product identification. | size 1</p>
 */
public class PublicProductIdentification implements ByteSerializable {
    private String productIdentification;
    private ProductIdentificationType productIdentificationType;
    public static final int byteLength = 31;

    public PublicProductIdentification(String productIdentification, ProductIdentificationType productIdentificationType) {
        this.productIdentification = productIdentification;
        this.productIdentificationType = productIdentificationType;
    }
    
    public PublicProductIdentification(byte[] bytes, int offset) {
        this.productIdentification = BendecUtils.stringFromByteArray(bytes, offset, 30);
        this.productIdentificationType = ProductIdentificationType.getProductIdentificationType(bytes, offset + 30);
    }
    
    public PublicProductIdentification(byte[] bytes) {
        this(bytes, 0);
    }
    
    public PublicProductIdentification() {
    }
    
    /**
     * @return Product identification, for example its ISIN code.
     */
    public String getProductIdentification() {
        return this.productIdentification;
    }
    
    /**
     * @return Type of product identification.
     */
    public ProductIdentificationType getProductIdentificationType() {
        return this.productIdentificationType;
    }

    /**
     * @param productIdentification Product identification, for example its ISIN code.
     */
    public void setProductIdentification(String productIdentification) {
        this.productIdentification = productIdentification;
    }
    
    /**
     * @param productIdentificationType Type of product identification.
     */
    public void setProductIdentificationType(ProductIdentificationType productIdentificationType) {
        this.productIdentificationType = productIdentificationType;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        buffer.put(BendecUtils.stringToByteArray(this.productIdentification, 30));
        productIdentificationType.toBytes(buffer);
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        buffer.put(BendecUtils.stringToByteArray(this.productIdentification, 30));
        productIdentificationType.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productIdentification,
        productIdentificationType);
    }
    
    @Override
    public String toString() {
        return "PublicProductIdentification {" +
            "productIdentification=" + productIdentification +
            ", productIdentificationType=" + productIdentificationType +
            "}";
    }
}