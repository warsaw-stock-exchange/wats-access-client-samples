package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>TradingPhaseScheduleEntry</h2>
 * <p>Trading phase definition.</p>
 * <p>Byte length: 82</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) tradingScheduleId - Trading schedule ID. | size 4</p>
 * <p>ElementId > long (u32) tradingPhaseId - Trading phase ID. | size 4</p>
 * <p>u8 > int tradingPhaseSettlementCycle - Settlement cycle for transactions made during a continuous phase. | size 1</p>
 * <p>u8 > int tradingPhaseMaxBlockSettlementCycle - Max settlement cycle for block transactions. | size 1</p>
 * <p>ElementId > long (u32) staticCollarVolatilityAuctionId - Static collar volatility auction ID. | size 4</p>
 * <p>ElementId > long (u32) dynamicCollarVolatilityAuctionId - Dynamic collar volatility auction ID. | size 4</p>
 * <p>Timestamp > BigInteger (u64) tradingPhaseStartTime - Trading phase start time. | size 8</p>
 * <p>TradingPhaseType tradingPhaseType - Type of matching algorithm. | size 1</p>
 * <p>bool > boolean uncrossing - True if the phase includes an uncrossing. | size 1</p>
 * <p>ElementId > long (u32) extStaticCollarVolatilityAuctionId - ID of Static Collar Volatility Auction. | size 4</p>
 * <p>ElementId > long (u32) extDynamicCollarVolatilityAuctionId - ID of Dynamic Collar Volatility Auction. | size 4</p>
 * <p>ElementId > long (u32) lastAuctionPhaseId - ID of last auction phase. | size 4</p>
 */
public class TradingPhaseScheduleEntry implements ByteSerializable, Message {
    private Header header;
    private long tradingScheduleId;
    private long tradingPhaseId;
    private int tradingPhaseSettlementCycle;
    private int tradingPhaseMaxBlockSettlementCycle;
    private long staticCollarVolatilityAuctionId;
    private long dynamicCollarVolatilityAuctionId;
    private BigInteger tradingPhaseStartTime;
    private TradingPhaseType tradingPhaseType;
    private boolean uncrossing;
    private long extStaticCollarVolatilityAuctionId;
    private long extDynamicCollarVolatilityAuctionId;
    private long lastAuctionPhaseId;
    public static final int byteLength = 82;
    
    public TradingPhaseScheduleEntry(Header header, long tradingScheduleId, long tradingPhaseId, int tradingPhaseSettlementCycle, int tradingPhaseMaxBlockSettlementCycle, long staticCollarVolatilityAuctionId, long dynamicCollarVolatilityAuctionId, BigInteger tradingPhaseStartTime, TradingPhaseType tradingPhaseType, boolean uncrossing, long extStaticCollarVolatilityAuctionId, long extDynamicCollarVolatilityAuctionId, long lastAuctionPhaseId) {
        this.header = header;
        this.tradingScheduleId = tradingScheduleId;
        this.tradingPhaseId = tradingPhaseId;
        this.tradingPhaseSettlementCycle = tradingPhaseSettlementCycle;
        this.tradingPhaseMaxBlockSettlementCycle = tradingPhaseMaxBlockSettlementCycle;
        this.staticCollarVolatilityAuctionId = staticCollarVolatilityAuctionId;
        this.dynamicCollarVolatilityAuctionId = dynamicCollarVolatilityAuctionId;
        this.tradingPhaseStartTime = tradingPhaseStartTime;
        this.tradingPhaseType = tradingPhaseType;
        this.uncrossing = uncrossing;
        this.extStaticCollarVolatilityAuctionId = extStaticCollarVolatilityAuctionId;
        this.extDynamicCollarVolatilityAuctionId = extDynamicCollarVolatilityAuctionId;
        this.lastAuctionPhaseId = lastAuctionPhaseId;
    }
    
    public TradingPhaseScheduleEntry(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.tradingScheduleId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.tradingPhaseId = BendecUtils.uInt32FromByteArray(bytes, offset + 46);
        this.tradingPhaseSettlementCycle = BendecUtils.uInt8FromByteArray(bytes, offset + 50);
        this.tradingPhaseMaxBlockSettlementCycle = BendecUtils.uInt8FromByteArray(bytes, offset + 51);
        this.staticCollarVolatilityAuctionId = BendecUtils.uInt32FromByteArray(bytes, offset + 52);
        this.dynamicCollarVolatilityAuctionId = BendecUtils.uInt32FromByteArray(bytes, offset + 56);
        this.tradingPhaseStartTime = BendecUtils.uInt64FromByteArray(bytes, offset + 60);
        this.tradingPhaseType = TradingPhaseType.getTradingPhaseType(bytes, offset + 68);
        this.uncrossing = BendecUtils.booleanFromByteArray(bytes, offset + 69);
        this.extStaticCollarVolatilityAuctionId = BendecUtils.uInt32FromByteArray(bytes, offset + 70);
        this.extDynamicCollarVolatilityAuctionId = BendecUtils.uInt32FromByteArray(bytes, offset + 74);
        this.lastAuctionPhaseId = BendecUtils.uInt32FromByteArray(bytes, offset + 78);
    }
    
    public TradingPhaseScheduleEntry(byte[] bytes) {
        this(bytes, 0);
    }
    
    public TradingPhaseScheduleEntry() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Trading schedule ID.
     */
    public long getTradingScheduleId() {
        return this.tradingScheduleId;
    }
    
    /**
     * @return Trading phase ID.
     */
    public long getTradingPhaseId() {
        return this.tradingPhaseId;
    }
    
    /**
     * @return Settlement cycle for transactions made during a continuous phase.
     */
    public int getTradingPhaseSettlementCycle() {
        return this.tradingPhaseSettlementCycle;
    }
    
    /**
     * @return Max settlement cycle for block transactions.
     */
    public int getTradingPhaseMaxBlockSettlementCycle() {
        return this.tradingPhaseMaxBlockSettlementCycle;
    }
    
    /**
     * @return Static collar volatility auction ID.
     */
    public long getStaticCollarVolatilityAuctionId() {
        return this.staticCollarVolatilityAuctionId;
    }
    
    /**
     * @return Dynamic collar volatility auction ID.
     */
    public long getDynamicCollarVolatilityAuctionId() {
        return this.dynamicCollarVolatilityAuctionId;
    }
    
    /**
     * @return Trading phase start time.
     */
    public BigInteger getTradingPhaseStartTime() {
        return this.tradingPhaseStartTime;
    }
    
    /**
     * @return Type of matching algorithm.
     */
    public TradingPhaseType getTradingPhaseType() {
        return this.tradingPhaseType;
    }
    
    /**
     * @return True if the phase includes an uncrossing.
     */
    public boolean getUncrossing() {
        return this.uncrossing;
    }
    
    /**
     * @return ID of Static Collar Volatility Auction.
     */
    public long getExtStaticCollarVolatilityAuctionId() {
        return this.extStaticCollarVolatilityAuctionId;
    }
    
    /**
     * @return ID of Dynamic Collar Volatility Auction.
     */
    public long getExtDynamicCollarVolatilityAuctionId() {
        return this.extDynamicCollarVolatilityAuctionId;
    }
    
    /**
     * @return ID of last auction phase.
     */
    public long getLastAuctionPhaseId() {
        return this.lastAuctionPhaseId;
    }
    
    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param tradingScheduleId Trading schedule ID.
     */
    public void setTradingScheduleId(long tradingScheduleId) {
        this.tradingScheduleId = tradingScheduleId;
    }
    
    /**
     * @param tradingPhaseId Trading phase ID.
     */
    public void setTradingPhaseId(long tradingPhaseId) {
        this.tradingPhaseId = tradingPhaseId;
    }
    
    /**
     * @param tradingPhaseSettlementCycle Settlement cycle for transactions made during a continuous phase.
     */
    public void setTradingPhaseSettlementCycle(int tradingPhaseSettlementCycle) {
        this.tradingPhaseSettlementCycle = tradingPhaseSettlementCycle;
    }
    
    /**
     * @param tradingPhaseMaxBlockSettlementCycle Max settlement cycle for block transactions.
     */
    public void setTradingPhaseMaxBlockSettlementCycle(int tradingPhaseMaxBlockSettlementCycle) {
        this.tradingPhaseMaxBlockSettlementCycle = tradingPhaseMaxBlockSettlementCycle;
    }
    
    /**
     * @param staticCollarVolatilityAuctionId Static collar volatility auction ID.
     */
    public void setStaticCollarVolatilityAuctionId(long staticCollarVolatilityAuctionId) {
        this.staticCollarVolatilityAuctionId = staticCollarVolatilityAuctionId;
    }
    
    /**
     * @param dynamicCollarVolatilityAuctionId Dynamic collar volatility auction ID.
     */
    public void setDynamicCollarVolatilityAuctionId(long dynamicCollarVolatilityAuctionId) {
        this.dynamicCollarVolatilityAuctionId = dynamicCollarVolatilityAuctionId;
    }
    
    /**
     * @param tradingPhaseStartTime Trading phase start time.
     */
    public void setTradingPhaseStartTime(BigInteger tradingPhaseStartTime) {
        this.tradingPhaseStartTime = tradingPhaseStartTime;
    }
    
    /**
     * @param tradingPhaseType Type of matching algorithm.
     */
    public void setTradingPhaseType(TradingPhaseType tradingPhaseType) {
        this.tradingPhaseType = tradingPhaseType;
    }
    
    /**
     * @param uncrossing True if the phase includes an uncrossing.
     */
    public void setUncrossing(boolean uncrossing) {
        this.uncrossing = uncrossing;
    }
    
    /**
     * @param extStaticCollarVolatilityAuctionId ID of Static Collar Volatility Auction.
     */
    public void setExtStaticCollarVolatilityAuctionId(long extStaticCollarVolatilityAuctionId) {
        this.extStaticCollarVolatilityAuctionId = extStaticCollarVolatilityAuctionId;
    }
    
    /**
     * @param extDynamicCollarVolatilityAuctionId ID of Dynamic Collar Volatility Auction.
     */
    public void setExtDynamicCollarVolatilityAuctionId(long extDynamicCollarVolatilityAuctionId) {
        this.extDynamicCollarVolatilityAuctionId = extDynamicCollarVolatilityAuctionId;
    }
    
    /**
     * @param lastAuctionPhaseId ID of last auction phase.
     */
    public void setLastAuctionPhaseId(long lastAuctionPhaseId) {
        this.lastAuctionPhaseId = lastAuctionPhaseId;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradingScheduleId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradingPhaseId));
        buffer.put(BendecUtils.uInt8ToByteArray(this.tradingPhaseSettlementCycle));
        buffer.put(BendecUtils.uInt8ToByteArray(this.tradingPhaseMaxBlockSettlementCycle));
        buffer.put(BendecUtils.uInt32ToByteArray(this.staticCollarVolatilityAuctionId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.dynamicCollarVolatilityAuctionId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.tradingPhaseStartTime));
        tradingPhaseType.toBytes(buffer);
        buffer.put(BendecUtils.booleanToByteArray(this.uncrossing));
        buffer.put(BendecUtils.uInt32ToByteArray(this.extStaticCollarVolatilityAuctionId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.extDynamicCollarVolatilityAuctionId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.lastAuctionPhaseId));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradingScheduleId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.tradingPhaseId));
        buffer.put(BendecUtils.uInt8ToByteArray(this.tradingPhaseSettlementCycle));
        buffer.put(BendecUtils.uInt8ToByteArray(this.tradingPhaseMaxBlockSettlementCycle));
        buffer.put(BendecUtils.uInt32ToByteArray(this.staticCollarVolatilityAuctionId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.dynamicCollarVolatilityAuctionId));
        buffer.put(BendecUtils.uInt64ToByteArray(this.tradingPhaseStartTime));
        tradingPhaseType.toBytes(buffer);
        buffer.put(BendecUtils.booleanToByteArray(this.uncrossing));
        buffer.put(BendecUtils.uInt32ToByteArray(this.extStaticCollarVolatilityAuctionId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.extDynamicCollarVolatilityAuctionId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.lastAuctionPhaseId));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        tradingScheduleId,
        tradingPhaseId,
        tradingPhaseSettlementCycle,
        tradingPhaseMaxBlockSettlementCycle,
        staticCollarVolatilityAuctionId,
        dynamicCollarVolatilityAuctionId,
        tradingPhaseStartTime,
        tradingPhaseType,
        uncrossing,
        extStaticCollarVolatilityAuctionId,
        extDynamicCollarVolatilityAuctionId,
        lastAuctionPhaseId);
    }
    
    @Override
    public String toString() {
        return "TradingPhaseScheduleEntry {" +
            "header=" + header +
            ", tradingScheduleId=" + tradingScheduleId +
            ", tradingPhaseId=" + tradingPhaseId +
            ", tradingPhaseSettlementCycle=" + tradingPhaseSettlementCycle +
            ", tradingPhaseMaxBlockSettlementCycle=" + tradingPhaseMaxBlockSettlementCycle +
            ", staticCollarVolatilityAuctionId=" + staticCollarVolatilityAuctionId +
            ", dynamicCollarVolatilityAuctionId=" + dynamicCollarVolatilityAuctionId +
            ", tradingPhaseStartTime=" + tradingPhaseStartTime +
            ", tradingPhaseType=" + tradingPhaseType +
            ", uncrossing=" + uncrossing +
            ", extStaticCollarVolatilityAuctionId=" + extStaticCollarVolatilityAuctionId +
            ", extDynamicCollarVolatilityAuctionId=" + extDynamicCollarVolatilityAuctionId +
            ", lastAuctionPhaseId=" + lastAuctionPhaseId +
            "}";
    }
}