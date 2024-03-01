package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>Heartbeat</h2>
 * <p>Heartbeat message</p>
 * <p>Byte length: 16</p>
 * <p>Header header - Header. | size 16</p>
 * */

public class Heartbeat implements ByteSerializable, Message {

    private Header header;
    public static final int byteLength = 16;

    public Heartbeat(Header header) {
        this.header = header;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.HEARTBEAT);
    }

    public Heartbeat(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.HEARTBEAT);
    }

    public Heartbeat(byte[] bytes) {
        this(bytes, 0);
    }

    public Heartbeat() {
    }



    /**
     * @return Header.
     */
    public Header getHeader() {
        return this.header;
    };

    /**
     * @param header Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header);
    }

    @Override
    public String toString() {
        return "Heartbeat{" +
            "header=" + header +
            '}';
        }
}
