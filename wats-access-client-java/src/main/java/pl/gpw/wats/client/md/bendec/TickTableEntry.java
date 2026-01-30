package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>TickTableEntry</h2>
 * <p>Tick size definition.</p>
 * <p>Byte length: 62</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>TickSize > long (i64) tickSize - Size of the tick. | size 8</p>
 * <p>Bound > long (i64) lowerBound - Tick size lower bound. | size 8</p>
 * <p>ElementId > long (u32) tickTableId - Tick table ID. | size 4</p>
 */
public class TickTableEntry implements ByteSerializable, Message {
    private Header header;
    private long tickSize;
    private long lowerBound;
    private long tickTableId;
    public static final int byteLength = 62;

    public TickTableEntry(Header header, long tickSize, long lowerBound, long tickTableId) {
        this.header = header;
        this.tickSize = tickSize;
        this.lowerBound = lowerBound;
        this.tickTableId = tickTableId;
    }
    
    public TickTableEntry(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.tickSize = BendecUtils.int64FromByteArray(bytes, offset + 42);
        this.lowerBound = BendecUtils.int64FromByteArray(bytes, offset + 50);
        this.tickTableId = BendecUtils.uInt32FromByteArray(bytes, offset + 58);
    }
    
    public TickTableEntry(byte[] bytes) {
        this(bytes, 0);
    }
    
    public TickTableEntry() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Size of the tick.
     */
    public long getTickSize() {
        return this.tickSize;
    }
    
    /**
     * @return Tick size lower bound.
     */
    public long getLowerBound() {
        return this.lowerBound;
    }
    
    /**
     * @return Tick table ID.
     */
    public long getTickTableId() {
        return this.tickTableId;
    }

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param tickSize Size of the tick.
     */
    public void setTickSize(long tickSize) {
        this.tickSize = tickSize;
    }
    
    /**
     * @param lowerBound Tick size lower bound.
     */
    public void setLowerBound(long lowerBound) {
        this.lowerBound = lowerBound;
    }
    
    /**
     * @param tickTableId Tick table ID.
     */
    public void setTickTableId(long tickTableId) {
        this.tickTableId = tickTableId;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.tickSize));
        buffer.put(BendecUtils.int64ToByteArray(this.lowerBound));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tickTableId));
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.tickSize));
        buffer.put(BendecUtils.int64ToByteArray(this.lowerBound));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tickTableId));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header,
        tickSize,
        lowerBound,
        tickTableId);
    }
    
    @Override
    public String toString() {
        return "TickTableEntry {" +
            "header=" + header +
            ", tickSize=" + tickSize +
            ", lowerBound=" + lowerBound +
            ", tickTableId=" + tickTableId +
            "}";
    }
}