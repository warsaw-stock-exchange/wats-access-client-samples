package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>RequestForExecution</h2>
 * <p>The information for the MM that one of the quotes has been crossed.</p>
 * <p>Byte length: 29</p>
 * <p>Header header - Message header. | size 24</p>
 * <p>ElementId > long (u32) instrumentId - Instrument ID | size 4</p>
 * <p>RequestForExecutionReason reason - The reason for this RFE. | size 1</p>
 */
public class RequestForExecution implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private RequestForExecutionReason reason;
    public static final int byteLength = 29;
    
    public RequestForExecution(Header header, long instrumentId, RequestForExecutionReason reason) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.reason = reason;
    }
    
    public RequestForExecution(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 24);
        this.reason = RequestForExecutionReason.getRequestForExecutionReason(bytes, offset + 28);
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
    }
    
    /**
     * @return Instrument ID
     */
    public long getInstrumentId() {
        return this.instrumentId;
    }
    
    /**
     * @return The reason for this RFE.
     */
    public RequestForExecutionReason getReason() {
        return this.reason;
    }
    
    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param instrumentId Instrument ID
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }
    
    /**
     * @param reason The reason for this RFE.
     */
    public void setReason(RequestForExecutionReason reason) {
        this.reason = reason;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        reason.toBytes(buffer);
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        reason.toBytes(buffer);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        reason);
    }
    
    @Override
    public String toString() {
        return "RequestForExecution {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", reason=" + reason +
            "}";
    }
}