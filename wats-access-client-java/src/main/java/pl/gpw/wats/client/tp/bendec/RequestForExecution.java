package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>RequestForExecution</h2>
 * <p>The information for the MM that one of the quotes has been crossed.</p>
 * <p>Byte length: 20</p>
 * <p>Header header - Message header. | size 16</p>
 * <p>ElementId > long (u32) instrumentId - Instrument ID | size 4</p>
 * */

public class RequestForExecution implements ByteSerializable, Message {

    private Header header;
    private long instrumentId;
    public static final int byteLength = 20;

    public RequestForExecution(Header header, long instrumentId) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.REQUESTFOREXECUTION);
    }

    public RequestForExecution(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 16);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.REQUESTFOREXECUTION);
    }

    public RequestForExecution(byte[] bytes) {
        this(bytes, 0);
    }

    public RequestForExecution() {
    }



    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return Instrument ID
     */
    public long getInstrumentId() {
        return this.instrumentId;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param instrumentId Instrument ID
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, instrumentId);
    }

    @Override
    public String toString() {
        return "RequestForExecution{" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            '}';
        }
}
