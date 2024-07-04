package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>QuoteOrderResponses</h2>
 * <p>Array of quote order responses grouped with their amount.</p>
 * <p>Byte length: 691</p>
 * <p>u8 > int count - How many quote order responses this message contains. | size 1</p>
 * <p>QuoteOrderResponseArray > QuoteOrderResponse[] (QuoteOrderResponse[]) items - The array of quote order responses. | size 690</p>
 */
public class QuoteOrderResponses implements ByteSerializable {
    private int count;
    private QuoteOrderResponse[] items;
    public static final int byteLength = 691;
    
    public QuoteOrderResponses(int count, QuoteOrderResponse[] items) {
        this.count = count;
        this.items = items;
    }
    
    public QuoteOrderResponses(byte[] bytes, int offset) {
        this.count = BendecUtils.uInt8FromByteArray(bytes, offset);
        this.items = new QuoteOrderResponse[30];
        for(int i = 0; i < items.length; i++) {
            this.items[i] = new QuoteOrderResponse(bytes, offset + 1 + i * 23);
        }
    }
    
    public QuoteOrderResponses(byte[] bytes) {
        this(bytes, 0);
    }
    
    public QuoteOrderResponses() {
    }
    
    /**
     * @return How many quote order responses this message contains.
     */
    public int getCount() {
        return this.count;
    }
    
    /**
     * @return The array of quote order responses.
     */
    public QuoteOrderResponse[] getItems() {
        return this.items;
    }
    
    /**
     * @param count How many quote order responses this message contains.
     */
    public void setCount(int count) {
        this.count = count;
    }
    
    /**
     * @param items The array of quote order responses.
     */
    public void setItems(QuoteOrderResponse[] items) {
        this.items = items;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        buffer.put(BendecUtils.uInt8ToByteArray(this.count));
        for(int i = 0; i < items.length; i++) {
            items[i].toBytes(buffer);
        }
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        buffer.put(BendecUtils.uInt8ToByteArray(this.count));
        for(int i = 0; i < items.length; i++) {
            items[i].toBytes(buffer);
        }
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(count,
        items);
    }
    
    @Override
    public String toString() {
        return "QuoteOrderResponses {" +
            "count=" + count +
            ", items=" + items +
            "}";
    }
}