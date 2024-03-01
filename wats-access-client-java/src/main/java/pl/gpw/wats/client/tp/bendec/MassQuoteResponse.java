package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>MassQuoteResponse</h2>
 * <p>The response to a MassQuote message.</p>
 * <p>Byte length: 718</p>
 * <p>Header header - Message header. | size 16</p>
 * <p>OrderId > BigInteger (u64) massQuoteId - Quote id | size 8</p>
 * <p>u8 > int count - How many responses this message contains. | size 1</p>
 * <p>QuoteOrderResponses > QuoteOrderResponse[] (QuoteOrderResponse[]) responses - The array of responses. | size 690</p>
 * <p>MassQuoteStatus status - Status of the given mass quote order. | size 1</p>
 * <p>MassQuoteRejectionReason reason - Reason for rejecting the given mass quote order. | size 2</p>
 * */

public class MassQuoteResponse implements ByteSerializable, Message {

    private Header header;
    private BigInteger massQuoteId;
    private int count;
    private QuoteOrderResponse[] responses;
    private MassQuoteStatus status;
    private MassQuoteRejectionReason reason;
    public static final int byteLength = 718;

    public MassQuoteResponse(Header header, BigInteger massQuoteId, int count, QuoteOrderResponse[] responses, MassQuoteStatus status, MassQuoteRejectionReason reason) {
        this.header = header;
        this.massQuoteId = massQuoteId;
        this.count = count;
        this.responses = responses;
        this.status = status;
        this.reason = reason;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.MASSQUOTERESPONSE);
    }

    public MassQuoteResponse(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.massQuoteId = BendecUtils.uInt64FromByteArray(bytes, offset + 16);
        this.count = BendecUtils.uInt8FromByteArray(bytes, offset + 24);
        this.responses = new QuoteOrderResponse[30];
        for(int i = 0; i < responses.length; i++) {
            this.responses[i] = new QuoteOrderResponse(bytes, offset + 25 + i * 23);
        }
        this.status = MassQuoteStatus.getMassQuoteStatus(bytes, offset + 715);
        this.reason = MassQuoteRejectionReason.getMassQuoteRejectionReason(bytes, offset + 716);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.MASSQUOTERESPONSE);
    }

    public MassQuoteResponse(byte[] bytes) {
        this(bytes, 0);
    }

    public MassQuoteResponse() {
    }



    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return Quote id
     */
    public BigInteger getMassQuoteId() {
        return this.massQuoteId;
    };
    /**
     * @return How many responses this message contains.
     */
    public int getCount() {
        return this.count;
    };
    /**
     * @return The array of responses.
     */
    public QuoteOrderResponse[] getResponses() {
        return this.responses;
    };
    /**
     * @return Status of the given mass quote order.
     */
    public MassQuoteStatus getStatus() {
        return this.status;
    };
    /**
     * @return Reason for rejecting the given mass quote order.
     */
    public MassQuoteRejectionReason getReason() {
        return this.reason;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param massQuoteId Quote id
     */
    public void setMassQuoteId(BigInteger massQuoteId) {
        this.massQuoteId = massQuoteId;
    };
    /**
     * @param count How many responses this message contains.
     */
    public void setCount(int count) {
        this.count = count;
    };
    /**
     * @param responses The array of responses.
     */
    public void setResponses(QuoteOrderResponse[] responses) {
        this.responses = responses;
    };
    /**
     * @param status Status of the given mass quote order.
     */
    public void setStatus(MassQuoteStatus status) {
        this.status = status;
    };
    /**
     * @param reason Reason for rejecting the given mass quote order.
     */
    public void setReason(MassQuoteRejectionReason reason) {
        this.reason = reason;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.massQuoteId));
        buffer.put(BendecUtils.uInt8ToByteArray(this.count));
        for(int i = 0; i < responses.length; i++) {
            responses[i].toBytes(buffer);
        }
        status.toBytes(buffer);
        reason.toBytes(buffer);
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.massQuoteId));
        buffer.put(BendecUtils.uInt8ToByteArray(this.count));
        for(int i = 0; i < responses.length; i++) {
            responses[i].toBytes(buffer);
        }
        status.toBytes(buffer);
        reason.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, massQuoteId, count, responses, status, reason);
    }

    @Override
    public String toString() {
        return "MassQuoteResponse{" +
            "header=" + header +
            ", massQuoteId=" + massQuoteId +
            ", count=" + count +
            ", responses=" + responses +
            ", status=" + status +
            ", reason=" + reason +
            '}';
        }
}
