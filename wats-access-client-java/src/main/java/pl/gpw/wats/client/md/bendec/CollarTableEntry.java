package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>CollarTableEntry</h2>
 * <p>Collar table definition.</p>
 * <p>Byte length: 93</p>
 * <p>Header header - Message header. | size 39</p>
 * <p>ElementId > long (u32) collarTableId - Collar table ID. | size 4</p>
 * <p>CollarMode collarMode - Collar mode (trade price collar or order price collar). | size 1</p>
 * <p>ExpressionType collarExpression - Collar expression (percentage or absolute value in reference price). | size 1</p>
 * <p>Bound > long (i64) collarLowerBound - Collar lower bound. | size 8</p>
 * <p>CollarValue > long (i64) collarValue - Collar value. | size 8</p>
 * <p>Bound > long (i64) collarLowerBid - Lower bid collar. | size 8</p>
 * <p>Bound > long (i64) collarUpperBid - Upper bid collar. | size 8</p>
 * <p>Bound > long (i64) collarUpperAsk - Upper ask collar. | size 8</p>
 * <p>Bound > long (i64) collarLowerAsk - Lower ask collar. | size 8</p>
 * */

public class CollarTableEntry implements ByteSerializable, Message {

    private Header header;
    private long collarTableId;
    private CollarMode collarMode;
    private ExpressionType collarExpression;
    private long collarLowerBound;
    private long collarValue;
    private long collarLowerBid;
    private long collarUpperBid;
    private long collarUpperAsk;
    private long collarLowerAsk;
    public static final int byteLength = 93;

    public CollarTableEntry(Header header, long collarTableId, CollarMode collarMode, ExpressionType collarExpression, long collarLowerBound, long collarValue, long collarLowerBid, long collarUpperBid, long collarUpperAsk, long collarLowerAsk) {
        this.header = header;
        this.collarTableId = collarTableId;
        this.collarMode = collarMode;
        this.collarExpression = collarExpression;
        this.collarLowerBound = collarLowerBound;
        this.collarValue = collarValue;
        this.collarLowerBid = collarLowerBid;
        this.collarUpperBid = collarUpperBid;
        this.collarUpperAsk = collarUpperAsk;
        this.collarLowerAsk = collarLowerAsk;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.COLLARTABLEENTRY);
    }

    public CollarTableEntry(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.collarTableId = BendecUtils.uInt32FromByteArray(bytes, offset + 39);
        this.collarMode = CollarMode.getCollarMode(bytes, offset + 43);
        this.collarExpression = ExpressionType.getExpressionType(bytes, offset + 44);
        this.collarLowerBound = BendecUtils.int64FromByteArray(bytes, offset + 45);
        this.collarValue = BendecUtils.int64FromByteArray(bytes, offset + 53);
        this.collarLowerBid = BendecUtils.int64FromByteArray(bytes, offset + 61);
        this.collarUpperBid = BendecUtils.int64FromByteArray(bytes, offset + 69);
        this.collarUpperAsk = BendecUtils.int64FromByteArray(bytes, offset + 77);
        this.collarLowerAsk = BendecUtils.int64FromByteArray(bytes, offset + 85);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.COLLARTABLEENTRY);
    }

    public CollarTableEntry(byte[] bytes) {
        this(bytes, 0);
    }

    public CollarTableEntry() {
    }



    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return Collar table ID.
     */
    public long getCollarTableId() {
        return this.collarTableId;
    };
    /**
     * @return Collar mode (trade price collar or order price collar).
     */
    public CollarMode getCollarMode() {
        return this.collarMode;
    };
    /**
     * @return Collar expression (percentage or absolute value in reference price).
     */
    public ExpressionType getCollarExpression() {
        return this.collarExpression;
    };
    /**
     * @return Collar lower bound.
     */
    public long getCollarLowerBound() {
        return this.collarLowerBound;
    };
    /**
     * @return Collar value.
     */
    public long getCollarValue() {
        return this.collarValue;
    };
    /**
     * @return Lower bid collar.
     */
    public long getCollarLowerBid() {
        return this.collarLowerBid;
    };
    /**
     * @return Upper bid collar.
     */
    public long getCollarUpperBid() {
        return this.collarUpperBid;
    };
    /**
     * @return Upper ask collar.
     */
    public long getCollarUpperAsk() {
        return this.collarUpperAsk;
    };
    /**
     * @return Lower ask collar.
     */
    public long getCollarLowerAsk() {
        return this.collarLowerAsk;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param collarTableId Collar table ID.
     */
    public void setCollarTableId(long collarTableId) {
        this.collarTableId = collarTableId;
    };
    /**
     * @param collarMode Collar mode (trade price collar or order price collar).
     */
    public void setCollarMode(CollarMode collarMode) {
        this.collarMode = collarMode;
    };
    /**
     * @param collarExpression Collar expression (percentage or absolute value in reference price).
     */
    public void setCollarExpression(ExpressionType collarExpression) {
        this.collarExpression = collarExpression;
    };
    /**
     * @param collarLowerBound Collar lower bound.
     */
    public void setCollarLowerBound(long collarLowerBound) {
        this.collarLowerBound = collarLowerBound;
    };
    /**
     * @param collarValue Collar value.
     */
    public void setCollarValue(long collarValue) {
        this.collarValue = collarValue;
    };
    /**
     * @param collarLowerBid Lower bid collar.
     */
    public void setCollarLowerBid(long collarLowerBid) {
        this.collarLowerBid = collarLowerBid;
    };
    /**
     * @param collarUpperBid Upper bid collar.
     */
    public void setCollarUpperBid(long collarUpperBid) {
        this.collarUpperBid = collarUpperBid;
    };
    /**
     * @param collarUpperAsk Upper ask collar.
     */
    public void setCollarUpperAsk(long collarUpperAsk) {
        this.collarUpperAsk = collarUpperAsk;
    };
    /**
     * @param collarLowerAsk Lower ask collar.
     */
    public void setCollarLowerAsk(long collarLowerAsk) {
        this.collarLowerAsk = collarLowerAsk;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.collarTableId));
        collarMode.toBytes(buffer);
        collarExpression.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.collarLowerBound));
        buffer.put(BendecUtils.int64ToByteArray(this.collarValue));
        buffer.put(BendecUtils.int64ToByteArray(this.collarLowerBid));
        buffer.put(BendecUtils.int64ToByteArray(this.collarUpperBid));
        buffer.put(BendecUtils.int64ToByteArray(this.collarUpperAsk));
        buffer.put(BendecUtils.int64ToByteArray(this.collarLowerAsk));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.collarTableId));
        collarMode.toBytes(buffer);
        collarExpression.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.collarLowerBound));
        buffer.put(BendecUtils.int64ToByteArray(this.collarValue));
        buffer.put(BendecUtils.int64ToByteArray(this.collarLowerBid));
        buffer.put(BendecUtils.int64ToByteArray(this.collarUpperBid));
        buffer.put(BendecUtils.int64ToByteArray(this.collarUpperAsk));
        buffer.put(BendecUtils.int64ToByteArray(this.collarLowerAsk));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, collarTableId, collarMode, collarExpression, collarLowerBound, collarValue, collarLowerBid, collarUpperBid, collarUpperAsk, collarLowerAsk);
    }

    @Override
    public String toString() {
        return "CollarTableEntry{" +
            "header=" + header +
            ", collarTableId=" + collarTableId +
            ", collarMode=" + collarMode +
            ", collarExpression=" + collarExpression +
            ", collarLowerBound=" + collarLowerBound +
            ", collarValue=" + collarValue +
            ", collarLowerBid=" + collarLowerBid +
            ", collarUpperBid=" + collarUpperBid +
            ", collarUpperAsk=" + collarUpperAsk +
            ", collarLowerAsk=" + collarLowerAsk +
            '}';
        }
}
