package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>IndexUnderlying</h2>
 * <p>Underlying instruments for index.</p>
 * <p>Byte length: 32</p>
 * <p>PublicProductIdentification productIdentification - Product Identification. | size 31</p>
 * <p>IndexUnderlyingType indexUnderlyingType - Underlying type of index. | size 1</p>
 */
public class IndexUnderlying implements ByteSerializable {
    private PublicProductIdentification productIdentification;
    private IndexUnderlyingType indexUnderlyingType;
    public static final int byteLength = 32;

    public IndexUnderlying(PublicProductIdentification productIdentification, IndexUnderlyingType indexUnderlyingType) {
        this.productIdentification = productIdentification;
        this.indexUnderlyingType = indexUnderlyingType;
    }
    
    public IndexUnderlying(byte[] bytes, int offset) {
        this.productIdentification = new PublicProductIdentification(bytes, offset);
        this.indexUnderlyingType = IndexUnderlyingType.getIndexUnderlyingType(bytes, offset + 31);
    }
    
    public IndexUnderlying(byte[] bytes) {
        this(bytes, 0);
    }
    
    public IndexUnderlying() {
    }
    
    /**
     * @return Product Identification.
     */
    public PublicProductIdentification getProductIdentification() {
        return this.productIdentification;
    }
    
    /**
     * @return Underlying type of index.
     */
    public IndexUnderlyingType getIndexUnderlyingType() {
        return this.indexUnderlyingType;
    }

    /**
     * @param productIdentification Product Identification.
     */
    public void setProductIdentification(PublicProductIdentification productIdentification) {
        this.productIdentification = productIdentification;
    }
    
    /**
     * @param indexUnderlyingType Underlying type of index.
     */
    public void setIndexUnderlyingType(IndexUnderlyingType indexUnderlyingType) {
        this.indexUnderlyingType = indexUnderlyingType;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        productIdentification.toBytes(buffer);
        indexUnderlyingType.toBytes(buffer);
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        productIdentification.toBytes(buffer);
        indexUnderlyingType.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productIdentification,
        indexUnderlyingType);
    }
    
    @Override
    public String toString() {
        return "IndexUnderlying {" +
            "productIdentification=" + productIdentification +
            ", indexUnderlyingType=" + indexUnderlyingType +
            "}";
    }
}