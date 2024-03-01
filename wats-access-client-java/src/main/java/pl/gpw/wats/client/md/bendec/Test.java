package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>Test</h2>
 * <p>A message used to test system operation.</p>
 * <p>Byte length: 71</p>
 * <p>Header header - Message header. | size 39</p>
 * <p>Timestamp > BigInteger (u64) timestampA - First Core Bus timestamp. | size 8</p>
 * <p>Timestamp > BigInteger (u64) timestampB - Sequencer timestamp. | size 8</p>
 * <p>Timestamp > BigInteger (u64) timestampC - Market Data timestamp. | size 8</p>
 * <p>Timestamp > BigInteger (u64) timestampD - Consumer timestamp. | size 8</p>
 * */

public class Test implements ByteSerializable, Message {

    private Header header;
    private BigInteger timestampA;
    private BigInteger timestampB;
    private BigInteger timestampC;
    private BigInteger timestampD;
    public static final int byteLength = 71;

    public Test(Header header, BigInteger timestampA, BigInteger timestampB, BigInteger timestampC, BigInteger timestampD) {
        this.header = header;
        this.timestampA = timestampA;
        this.timestampB = timestampB;
        this.timestampC = timestampC;
        this.timestampD = timestampD;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.TEST);
    }

    public Test(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.timestampA = BendecUtils.uInt64FromByteArray(bytes, offset + 39);
        this.timestampB = BendecUtils.uInt64FromByteArray(bytes, offset + 47);
        this.timestampC = BendecUtils.uInt64FromByteArray(bytes, offset + 55);
        this.timestampD = BendecUtils.uInt64FromByteArray(bytes, offset + 63);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.TEST);
    }

    public Test(byte[] bytes) {
        this(bytes, 0);
    }

    public Test() {
    }



    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return First Core Bus timestamp.
     */
    public BigInteger getTimestampA() {
        return this.timestampA;
    };
    /**
     * @return Sequencer timestamp.
     */
    public BigInteger getTimestampB() {
        return this.timestampB;
    };
    /**
     * @return Market Data timestamp.
     */
    public BigInteger getTimestampC() {
        return this.timestampC;
    };
    /**
     * @return Consumer timestamp.
     */
    public BigInteger getTimestampD() {
        return this.timestampD;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param timestampA First Core Bus timestamp.
     */
    public void setTimestampA(BigInteger timestampA) {
        this.timestampA = timestampA;
    };
    /**
     * @param timestampB Sequencer timestamp.
     */
    public void setTimestampB(BigInteger timestampB) {
        this.timestampB = timestampB;
    };
    /**
     * @param timestampC Market Data timestamp.
     */
    public void setTimestampC(BigInteger timestampC) {
        this.timestampC = timestampC;
    };
    /**
     * @param timestampD Consumer timestamp.
     */
    public void setTimestampD(BigInteger timestampD) {
        this.timestampD = timestampD;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.timestampA));
        buffer.put(BendecUtils.uInt64ToByteArray(this.timestampB));
        buffer.put(BendecUtils.uInt64ToByteArray(this.timestampC));
        buffer.put(BendecUtils.uInt64ToByteArray(this.timestampD));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.timestampA));
        buffer.put(BendecUtils.uInt64ToByteArray(this.timestampB));
        buffer.put(BendecUtils.uInt64ToByteArray(this.timestampC));
        buffer.put(BendecUtils.uInt64ToByteArray(this.timestampD));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, timestampA, timestampB, timestampC, timestampD);
    }

    @Override
    public String toString() {
        return "Test{" +
            "header=" + header +
            ", timestampA=" + timestampA +
            ", timestampB=" + timestampB +
            ", timestampC=" + timestampC +
            ", timestampD=" + timestampD +
            '}';
        }
}
