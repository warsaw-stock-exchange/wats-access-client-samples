package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>RiskLimitDefinitionResponse</h2>
 * <p>Risk limit definition response message.</p>
 * <p>Byte length: 26</p>
 * <p>Header header - Header. | size 16</p>
 * <p>RiskDefinitionId > BigInteger (u64) id - Risk limit definition message Id. | size 8</p>
 * <p>RiskDefinitionStatus status - Risk limit definition status. | size 1</p>
 * <p>RiskLimitDefinitionRejectionReason reason - Risk limit definition rejection reason. | size 1</p>
 * */

public class RiskLimitDefinitionResponse implements ByteSerializable, Message {

    private Header header;
    private BigInteger id;
    private RiskDefinitionStatus status;
    private RiskLimitDefinitionRejectionReason reason;
    public static final int byteLength = 26;

    public RiskLimitDefinitionResponse(Header header, BigInteger id, RiskDefinitionStatus status, RiskLimitDefinitionRejectionReason reason) {
        this.header = header;
        this.id = id;
        this.status = status;
        this.reason = reason;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.RISKLIMITDEFINITIONRESPONSE);
    }

    public RiskLimitDefinitionResponse(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.id = BendecUtils.uInt64FromByteArray(bytes, offset + 16);
        this.status = RiskDefinitionStatus.getRiskDefinitionStatus(bytes, offset + 24);
        this.reason = RiskLimitDefinitionRejectionReason.getRiskLimitDefinitionRejectionReason(bytes, offset + 25);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.RISKLIMITDEFINITIONRESPONSE);
    }

    public RiskLimitDefinitionResponse(byte[] bytes) {
        this(bytes, 0);
    }

    public RiskLimitDefinitionResponse() {
    }



    /**
     * @return Header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return Risk limit definition message Id.
     */
    public BigInteger getId() {
        return this.id;
    };
    /**
     * @return Risk limit definition status.
     */
    public RiskDefinitionStatus getStatus() {
        return this.status;
    };
    /**
     * @return Risk limit definition rejection reason.
     */
    public RiskLimitDefinitionRejectionReason getReason() {
        return this.reason;
    };

    /**
     * @param header Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param id Risk limit definition message Id.
     */
    public void setId(BigInteger id) {
        this.id = id;
    };
    /**
     * @param status Risk limit definition status.
     */
    public void setStatus(RiskDefinitionStatus status) {
        this.status = status;
    };
    /**
     * @param reason Risk limit definition rejection reason.
     */
    public void setReason(RiskLimitDefinitionRejectionReason reason) {
        this.reason = reason;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.id));
        status.toBytes(buffer);
        reason.toBytes(buffer);
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.id));
        status.toBytes(buffer);
        reason.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, id, status, reason);
    }

    @Override
    public String toString() {
        return "RiskLimitDefinitionResponse{" +
            "header=" + header +
            ", id=" + id +
            ", status=" + status +
            ", reason=" + reason +
            '}';
        }
}
