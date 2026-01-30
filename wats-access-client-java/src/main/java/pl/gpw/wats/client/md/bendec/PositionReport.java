package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>PositionReport</h2>
 * <p>Report of open positions.</p>
 * <p>Byte length: 82</p>
 * <p>Header header - Message Header. | size 42</p>
 * <p>PublicProductIdentification publicProductIdentification - Product identification, for example its ISIN number. | size 31</p>
 * <p>PosType posType - Type of quantity returned. | size 1</p>
 * <p>u32 > long openPositions - Number of open positions. | size 4</p>
 * <p>TradeId > long (u32) tradeId - Trade ID. | size 4</p>
 */
public class PositionReport implements ByteSerializable, Message {
    private Header header;
    private PublicProductIdentification publicProductIdentification;
    private PosType posType;
    private long openPositions;
    private long tradeId;
    public static final int byteLength = 82;

    public PositionReport(Header header, PublicProductIdentification publicProductIdentification, PosType posType, long openPositions, long tradeId) {
        this.header = header;
        this.publicProductIdentification = publicProductIdentification;
        this.posType = posType;
        this.openPositions = openPositions;
        this.tradeId = tradeId;
    }
    
    public PositionReport(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.publicProductIdentification = new PublicProductIdentification(bytes, offset + 42);
        this.posType = PosType.getPosType(bytes, offset + 73);
        this.openPositions = BendecUtils.uInt32FromByteArray(bytes, offset + 74);
        this.tradeId = BendecUtils.uInt32FromByteArray(bytes, offset + 78);
    }
    
    public PositionReport(byte[] bytes) {
        this(bytes, 0);
    }
    
    public PositionReport() {
    }
    
    /**
     * @return Message Header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Product identification, for example its ISIN number.
     */
    public PublicProductIdentification getPublicProductIdentification() {
        return this.publicProductIdentification;
    }
    
    /**
     * @return Type of quantity returned.
     */
    public PosType getPosType() {
        return this.posType;
    }
    
    /**
     * @return Number of open positions.
     */
    public long getOpenPositions() {
        return this.openPositions;
    }
    
    /**
     * @return Trade ID.
     */
    public long getTradeId() {
        return this.tradeId;
    }

    /**
     * @param header Message Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param publicProductIdentification Product identification, for example its ISIN number.
     */
    public void setPublicProductIdentification(PublicProductIdentification publicProductIdentification) {
        this.publicProductIdentification = publicProductIdentification;
    }
    
    /**
     * @param posType Type of quantity returned.
     */
    public void setPosType(PosType posType) {
        this.posType = posType;
    }
    
    /**
     * @param openPositions Number of open positions.
     */
    public void setOpenPositions(long openPositions) {
        this.openPositions = openPositions;
    }
    
    /**
     * @param tradeId Trade ID.
     */
    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        publicProductIdentification.toBytes(buffer);
        posType.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.openPositions));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradeId));
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        publicProductIdentification.toBytes(buffer);
        posType.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.openPositions));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradeId));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header,
        publicProductIdentification,
        posType,
        openPositions,
        tradeId);
    }
    
    @Override
    public String toString() {
        return "PositionReport {" +
            "header=" + header +
            ", publicProductIdentification=" + publicProductIdentification +
            ", posType=" + posType +
            ", openPositions=" + openPositions +
            ", tradeId=" + tradeId +
            "}";
    }
}