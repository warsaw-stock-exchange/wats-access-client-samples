package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>MarketMakerCommandResponse</h2>
 * <p>The response to the Market Maker command.</p>
 * <p>Byte length: 31</p>
 * <p>Header header - Message header. | size 24</p>
 * <p>u32 > long refId - A reference id to the Market Maker command request message. | size 4</p>
 * <p>CommandResult result - Confirmation of achieving the intended action. If the action (e.g., Buy Only state) has already been achieved before the command, the result will be positive. | size 1</p>
 * <p>CommandRejectionCode rejectionCode - Reason for rejecting the Market Maker command. | size 2</p>
 */
public class MarketMakerCommandResponse implements ByteSerializable, Message {
    private Header header;
    private long refId;
    private CommandResult result;
    private CommandRejectionCode rejectionCode;
    public static final int byteLength = 31;

    public MarketMakerCommandResponse(Header header, long refId, CommandResult result, CommandRejectionCode rejectionCode) {
        this.header = header;
        this.refId = refId;
        this.result = result;
        this.rejectionCode = rejectionCode;
    }
    
    public MarketMakerCommandResponse(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.refId = BendecUtils.uInt32FromByteArray(bytes, offset + 24);
        this.result = CommandResult.getCommandResult(bytes, offset + 28);
        this.rejectionCode = CommandRejectionCode.getCommandRejectionCode(bytes, offset + 29);
    }
    
    public MarketMakerCommandResponse(byte[] bytes) {
        this(bytes, 0);
    }
    
    public MarketMakerCommandResponse() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return A reference id to the Market Maker command request message.
     */
    public long getRefId() {
        return this.refId;
    }
    
    /**
     * @return Confirmation of achieving the intended action. If the action (e.g., Buy Only state) has already been achieved before the command, the result will be positive.
     */
    public CommandResult getResult() {
        return this.result;
    }
    
    /**
     * @return Reason for rejecting the Market Maker command.
     */
    public CommandRejectionCode getRejectionCode() {
        return this.rejectionCode;
    }

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param refId A reference id to the Market Maker command request message.
     */
    public void setRefId(long refId) {
        this.refId = refId;
    }
    
    /**
     * @param result Confirmation of achieving the intended action. If the action (e.g., Buy Only state) has already been achieved before the command, the result will be positive.
     */
    public void setResult(CommandResult result) {
        this.result = result;
    }
    
    /**
     * @param rejectionCode Reason for rejecting the Market Maker command.
     */
    public void setRejectionCode(CommandRejectionCode rejectionCode) {
        this.rejectionCode = rejectionCode;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.refId));
        result.toBytes(buffer);
        rejectionCode.toBytes(buffer);
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.refId));
        result.toBytes(buffer);
        rejectionCode.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header,
        refId,
        result,
        rejectionCode);
    }
    
    @Override
    public String toString() {
        return "MarketMakerCommandResponse {" +
            "header=" + header +
            ", refId=" + refId +
            ", result=" + result +
            ", rejectionCode=" + rejectionCode +
            "}";
    }
}