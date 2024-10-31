package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>TradeCaptureReportResponse</h2>
 * <p>The message is a response to an Trade Capture Report message, containing the state of TCR execution.</p>
 * <p>Byte length: 56</p>
 * <p>Header header - Header. | size 16</p>
 * <p>ElementId > long (u32) instrumentId - ID of the instrument included in the order. | size 4</p>
 * <p>TradeId > long (u32) tradeId - The unique ID assigned to the trade entity once it is received or matched by the exchange or central counterparty. | size 4</p>
 * <p>TradeReportId > String (u8[]) tradeReportId - Unique identifier of trade capture report. | size 21</p>
 * <p>OrderId > BigInteger (u64) secondaryTradeReportId - ID of the trade capture report. | size 8</p>
 * <p>TcrStatus status - Status of the given order. | size 1</p>
 * <p>TcrRejectionReason reason - Reason for rejecting the given TCR. | size 2</p>
 */
public class TradeCaptureReportResponse implements ByteSerializable, Message {
    private Header header;
    private long instrumentId;
    private long tradeId;
    private String tradeReportId;
    private BigInteger secondaryTradeReportId;
    private TcrStatus status;
    private TcrRejectionReason reason;
    public static final int byteLength = 56;
    
    public TradeCaptureReportResponse(Header header, long instrumentId, long tradeId, String tradeReportId, BigInteger secondaryTradeReportId, TcrStatus status, TcrRejectionReason reason) {
        this.header = header;
        this.instrumentId = instrumentId;
        this.tradeId = tradeId;
        this.tradeReportId = tradeReportId;
        this.secondaryTradeReportId = secondaryTradeReportId;
        this.status = status;
        this.reason = reason;
    }
    
    public TradeCaptureReportResponse(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 16);
        this.tradeId = BendecUtils.uInt32FromByteArray(bytes, offset + 20);
        this.tradeReportId = BendecUtils.stringFromByteArray(bytes, offset + 24, 21);
        this.secondaryTradeReportId = BendecUtils.uInt64FromByteArray(bytes, offset + 45);
        this.status = TcrStatus.getTcrStatus(bytes, offset + 53);
        this.reason = TcrRejectionReason.getTcrRejectionReason(bytes, offset + 54);
    }
    
    public TradeCaptureReportResponse(byte[] bytes) {
        this(bytes, 0);
    }
    
    public TradeCaptureReportResponse() {
    }
    
    /**
     * @return Header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return ID of the instrument included in the order.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    }
    
    /**
     * @return The unique ID assigned to the trade entity once it is received or matched by the exchange or central counterparty.
     */
    public long getTradeId() {
        return this.tradeId;
    }
    
    /**
     * @return Unique identifier of trade capture report.
     */
    public String getTradeReportId() {
        return this.tradeReportId;
    }
    
    /**
     * @return ID of the trade capture report.
     */
    public BigInteger getSecondaryTradeReportId() {
        return this.secondaryTradeReportId;
    }
    
    /**
     * @return Status of the given order.
     */
    public TcrStatus getStatus() {
        return this.status;
    }
    
    /**
     * @return Reason for rejecting the given TCR.
     */
    public TcrRejectionReason getReason() {
        return this.reason;
    }
    
    /**
     * @param header Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param instrumentId ID of the instrument included in the order.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }
    
    /**
     * @param tradeId The unique ID assigned to the trade entity once it is received or matched by the exchange or central counterparty.
     */
    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }
    
    /**
     * @param tradeReportId Unique identifier of trade capture report.
     */
    public void setTradeReportId(String tradeReportId) {
        this.tradeReportId = tradeReportId;
    }
    
    /**
     * @param secondaryTradeReportId ID of the trade capture report.
     */
    public void setSecondaryTradeReportId(BigInteger secondaryTradeReportId) {
        this.secondaryTradeReportId = secondaryTradeReportId;
    }
    
    /**
     * @param status Status of the given order.
     */
    public void setStatus(TcrStatus status) {
        this.status = status;
    }
    
    /**
     * @param reason Reason for rejecting the given TCR.
     */
    public void setReason(TcrRejectionReason reason) {
        this.reason = reason;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradeId));
        buffer.put(BendecUtils.stringToByteArray(this.tradeReportId, 21));
        buffer.put(BendecUtils.uInt64ToByteArray(this.secondaryTradeReportId));
        status.toBytes(buffer);
        reason.toBytes(buffer);
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradeId));
        buffer.put(BendecUtils.stringToByteArray(this.tradeReportId, 21));
        buffer.put(BendecUtils.uInt64ToByteArray(this.secondaryTradeReportId));
        status.toBytes(buffer);
        reason.toBytes(buffer);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        instrumentId,
        tradeId,
        tradeReportId,
        secondaryTradeReportId,
        status,
        reason);
    }
    
    @Override
    public String toString() {
        return "TradeCaptureReportResponse {" +
            "header=" + header +
            ", instrumentId=" + instrumentId +
            ", tradeId=" + tradeId +
            ", tradeReportId=" + tradeReportId +
            ", secondaryTradeReportId=" + secondaryTradeReportId +
            ", status=" + status +
            ", reason=" + reason +
            "}";
    }
}