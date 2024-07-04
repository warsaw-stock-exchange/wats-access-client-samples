package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>Quotes</h2>
 * <p>Array of quotes grouped with their amount.</p>
 * <p>Byte length: 1081</p>
 * <p>u8 > int count - How many quotes this message contains. | size 1</p>
 * <p>QuotesArray > Quote[] (Quote[]) items - The array of quotes. | size 1080</p>
 */
public class Quotes implements ByteSerializable {
    private int count;
    private Quote[] items;
    public static final int byteLength = 1081;
    
    public Quotes(int count, Quote[] items) {
        this.count = count;
        this.items = items;
    }
    
    public Quotes(byte[] bytes, int offset) {
        this.count = BendecUtils.uInt8FromByteArray(bytes, offset);
        this.items = new Quote[30];
        for(int i = 0; i < items.length; i++) {
            this.items[i] = new Quote(bytes, offset + 1 + i * 36);
        }
    }
    
    public Quotes(byte[] bytes) {
        this(bytes, 0);
    }
    
    public Quotes() {
    }
    
    /**
     * @return How many quotes this message contains.
     */
    public int getCount() {
        return this.count;
    }
    
    /**
     * @return The array of quotes.
     */
    public Quote[] getItems() {
        return this.items;
    }
    
    /**
     * @param count How many quotes this message contains.
     */
    public void setCount(int count) {
        this.count = count;
    }
    
    /**
     * @param items The array of quotes.
     */
    public void setItems(Quote[] items) {
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
        return "Quotes {" +
            "count=" + count +
            ", items=" + items +
            "}";
    }
}