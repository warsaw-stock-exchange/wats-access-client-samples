package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>IndexUnderlyings</h2>
 * <p>Array of IndexUnderlying with their count.</p>
 * <p>Byte length: 97</p>
 * <p>u8 > int count - How many elements does IndexUnderlyings contain. | size 1</p>
 * <p>IndexUnderlyingArray > IndexUnderlying[] (IndexUnderlying[]) items - IndexUnderlying Array. | size 96</p>
 */
public class IndexUnderlyings implements ByteSerializable {
    private int count;
    private IndexUnderlying[] items;
    public static final int byteLength = 97;
    
    public IndexUnderlyings(int count, IndexUnderlying[] items) {
        this.count = count;
        this.items = items;
    }
    
    public IndexUnderlyings(byte[] bytes, int offset) {
        this.count = BendecUtils.uInt8FromByteArray(bytes, offset);
        this.items = new IndexUnderlying[3];
        for(int i = 0; i < items.length; i++) {
            this.items[i] = new IndexUnderlying(bytes, offset + 1 + i * 32);
        }
    }
    
    public IndexUnderlyings(byte[] bytes) {
        this(bytes, 0);
    }
    
    public IndexUnderlyings() {
    }
    
    /**
     * @return How many elements does IndexUnderlyings contain.
     */
    public int getCount() {
        return this.count;
    }
    
    /**
     * @return IndexUnderlying Array.
     */
    public IndexUnderlying[] getItems() {
        return this.items;
    }
    
    /**
     * @param count How many elements does IndexUnderlyings contain.
     */
    public void setCount(int count) {
        this.count = count;
    }
    
    /**
     * @param items IndexUnderlying Array.
     */
    public void setItems(IndexUnderlying[] items) {
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
        return "IndexUnderlyings {" +
            "count=" + count +
            ", items=" + items +
            "}";
    }
}