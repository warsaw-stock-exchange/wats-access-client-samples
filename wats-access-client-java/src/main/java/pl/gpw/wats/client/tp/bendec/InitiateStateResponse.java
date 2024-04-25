package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>InitiateStateResponse</h2>
 * <p>The response to the attempt at state change.</p>
 * <p>Byte length: 17</p>
 * <p>Header header - Message header. | size 16</p>
 * <p>InitiateStateResult result - The result of an attempt at changing phase. | size 1</p>
 * */

public class InitiateStateResponse implements ByteSerializable, Message {

    private Header header;
    private InitiateStateResult result;
    public static final int byteLength = 17;

    public InitiateStateResponse(Header header, InitiateStateResult result) {
        this.header = header;
        this.result = result;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.INITIATESTATERESPONSE);
    }

    public InitiateStateResponse(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.result = InitiateStateResult.getInitiateStateResult(bytes, offset + 16);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.INITIATESTATERESPONSE);
    }

    public InitiateStateResponse(byte[] bytes) {
        this(bytes, 0);
    }

    public InitiateStateResponse() {
    }



    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return The result of an attempt at changing phase.
     */
    public InitiateStateResult getResult() {
        return this.result;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param result The result of an attempt at changing phase.
     */
    public void setResult(InitiateStateResult result) {
        this.result = result;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        result.toBytes(buffer);
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        result.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, result);
    }

    @Override
    public String toString() {
        return "InitiateStateResponse{" +
            "header=" + header +
            ", result=" + result +
            '}';
        }
}
