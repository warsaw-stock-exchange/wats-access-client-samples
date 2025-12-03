package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>OrderAdd</h2>
 * <p>Message used to add new orders to the system.</p>
 * <p>Byte length: 175</p>
 * <p>Header header - Header. | size 24</p>
 * <p>STPId > int (u8) stpId - ID assigned by the client used in the Self Trade Prevention mechanism. | size 1</p>
 * <p>ElementId > long (u32) instrumentId - ID of the instrument being traded. | size 4</p>
 * <p>OrderType orderType - Indicates the order type. | size 1</p>
 * <p>TimeInForce timeInForce - Indicates the order's time in force (e.g. GTC). | size 1</p>
 * <p>OrderSide side - Indicates the order's side (buy or sell). | size 1</p>
 * <p>Price > long (i64) price - Indicates the price of the order. | size 8</p>
 * <p>Price > long (i64) triggerPrice - Indicates the trigger price (Last Trade Price - LTP) after which the order should be added to the order book. | size 8</p>
 * <p>Quantity > BigInteger (u64) quantity - Indicates the quantity of the instrument included in the order. | size 8</p>
 * <p>Quantity > BigInteger (u64) displayQty - Used only for iceberg order. The quantity to be displayed. | size 8</p>
 * <p>Capacity capacity - Capacity of the party making the order (either principal or agency). | size 1</p>
 * <p>Account > String (u8[]) account - Account number. | size 16</p>
 * <p>AccountType accountType - Type of account associated with the order. | size 1</p>
 * <p>OrderFlags flags - Flags raised on an order. | size 1</p>
 * <p>MifidFields mifidFields - Fields related to the MiFID directive. | size 15</p>
 * <p>Timestamp > BigInteger (u64) expire - Expiration time indicating the validity of the order - relevant only when TimeInForce is set to GTD (Good Till Date) or GTT (Good Till Time). | size 8</p>
 * <p>Memo > String (u8[]) memo - Free text. | size 18</p>
 * <p>ClientOrderId > String (u8[]) clientOrderId - Arbitrary user provided value associated with the order. | size 20</p>
 * <p>ClearingCode > String (u8[]) clearingMemberCode - Clearing member code. | size 20</p>
 * <p>ClearingIdentifier clearingMemberClearingIdentifier - Clearing member's clearing identifier. | size 1</p>
 * <p>ExecInst execInst - Instructions for order handling on exchange trading floor. | size 1</p>
 * <p>u8 > int feeStructureId - Optional identifier of a fee scheme for billing purposes. | size 1</p>
 * <p>InterestedParty > String (u8[]) interestedParty - 3rd party interested in this order or trade. | size 8</p>
 */
public class OrderAdd implements ByteSerializable, Message {
    private Header header;
    private int stpId;
    private long instrumentId;
    private OrderType orderType;
    private TimeInForce timeInForce;
    private OrderSide side;
    private long price;
    private long triggerPrice;
    private BigInteger quantity;
    private BigInteger displayQty;
    private Capacity capacity;
    private String account;
    private AccountType accountType;
    private OrderFlags flags;
    private MifidFields mifidFields;
    private BigInteger expire;
    private String memo;
    private String clientOrderId;
    private String clearingMemberCode;
    private ClearingIdentifier clearingMemberClearingIdentifier;
    private ExecInst execInst;
    private int feeStructureId;
    private String interestedParty;
    public static final int byteLength = 175;
    
    public OrderAdd(Header header, int stpId, long instrumentId, OrderType orderType, TimeInForce timeInForce, OrderSide side, long price, long triggerPrice, BigInteger quantity, BigInteger displayQty, Capacity capacity, String account, AccountType accountType, OrderFlags flags, MifidFields mifidFields, BigInteger expire, String memo, String clientOrderId, String clearingMemberCode, ClearingIdentifier clearingMemberClearingIdentifier, ExecInst execInst, int feeStructureId, String interestedParty) {
        this.header = header;
        this.stpId = stpId;
        this.instrumentId = instrumentId;
        this.orderType = orderType;
        this.timeInForce = timeInForce;
        this.side = side;
        this.price = price;
        this.triggerPrice = triggerPrice;
        this.quantity = quantity;
        this.displayQty = displayQty;
        this.capacity = capacity;
        this.account = account;
        this.accountType = accountType;
        this.flags = flags;
        this.mifidFields = mifidFields;
        this.expire = expire;
        this.memo = memo;
        this.clientOrderId = clientOrderId;
        this.clearingMemberCode = clearingMemberCode;
        this.clearingMemberClearingIdentifier = clearingMemberClearingIdentifier;
        this.execInst = execInst;
        this.feeStructureId = feeStructureId;
        this.interestedParty = interestedParty;
    }
    
    public OrderAdd(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.stpId = BendecUtils.uInt8FromByteArray(bytes, offset + 24);
        this.instrumentId = BendecUtils.uInt32FromByteArray(bytes, offset + 25);
        this.orderType = OrderType.getOrderType(bytes, offset + 29);
        this.timeInForce = TimeInForce.getTimeInForce(bytes, offset + 30);
        this.side = OrderSide.getOrderSide(bytes, offset + 31);
        this.price = BendecUtils.int64FromByteArray(bytes, offset + 32);
        this.triggerPrice = BendecUtils.int64FromByteArray(bytes, offset + 40);
        this.quantity = BendecUtils.uInt64FromByteArray(bytes, offset + 48);
        this.displayQty = BendecUtils.uInt64FromByteArray(bytes, offset + 56);
        this.capacity = Capacity.getCapacity(bytes, offset + 64);
        this.account = BendecUtils.stringFromByteArray(bytes, offset + 65, 16);
        this.accountType = AccountType.getAccountType(bytes, offset + 81);
        this.flags = new OrderFlags(bytes, offset + 82);
        this.mifidFields = new MifidFields(bytes, offset + 83);
        this.expire = BendecUtils.uInt64FromByteArray(bytes, offset + 98);
        this.memo = BendecUtils.stringFromByteArray(bytes, offset + 106, 18);
        this.clientOrderId = BendecUtils.stringFromByteArray(bytes, offset + 124, 20);
        this.clearingMemberCode = BendecUtils.stringFromByteArray(bytes, offset + 144, 20);
        this.clearingMemberClearingIdentifier = ClearingIdentifier.getClearingIdentifier(bytes, offset + 164);
        this.execInst = new ExecInst(bytes, offset + 165);
        this.feeStructureId = BendecUtils.uInt8FromByteArray(bytes, offset + 166);
        this.interestedParty = BendecUtils.stringFromByteArray(bytes, offset + 167, 8);
    }
    
    public OrderAdd(byte[] bytes) {
        this(bytes, 0);
    }
    
    public OrderAdd() {
    }
    
    /**
     * @return Header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return ID assigned by the client used in the Self Trade Prevention mechanism.
     */
    public int getStpId() {
        return this.stpId;
    }
    
    /**
     * @return ID of the instrument being traded.
     */
    public long getInstrumentId() {
        return this.instrumentId;
    }
    
    /**
     * @return Indicates the order type.
     */
    public OrderType getOrderType() {
        return this.orderType;
    }
    
    /**
     * @return Indicates the order's time in force (e.g. GTC).
     */
    public TimeInForce getTimeInForce() {
        return this.timeInForce;
    }
    
    /**
     * @return Indicates the order's side (buy or sell).
     */
    public OrderSide getSide() {
        return this.side;
    }
    
    /**
     * @return Indicates the price of the order.
     */
    public long getPrice() {
        return this.price;
    }
    
    /**
     * @return Indicates the trigger price (Last Trade Price - LTP) after which the order should be added to the order book.
     */
    public long getTriggerPrice() {
        return this.triggerPrice;
    }
    
    /**
     * @return Indicates the quantity of the instrument included in the order.
     */
    public BigInteger getQuantity() {
        return this.quantity;
    }
    
    /**
     * @return Used only for iceberg order. The quantity to be displayed.
     */
    public BigInteger getDisplayQty() {
        return this.displayQty;
    }
    
    /**
     * @return Capacity of the party making the order (either principal or agency).
     */
    public Capacity getCapacity() {
        return this.capacity;
    }
    
    /**
     * @return Account number.
     */
    public String getAccount() {
        return this.account;
    }
    
    /**
     * @return Type of account associated with the order.
     */
    public AccountType getAccountType() {
        return this.accountType;
    }
    
    /**
     * @return Flags raised on an order.
     */
    public OrderFlags getFlags() {
        return this.flags;
    }
    
    /**
     * @return Fields related to the MiFID directive.
     */
    public MifidFields getMifidFields() {
        return this.mifidFields;
    }
    
    /**
     * @return Expiration time indicating the validity of the order - relevant only when TimeInForce is set to GTD (Good Till Date) or GTT (Good Till Time).
     */
    public BigInteger getExpire() {
        return this.expire;
    }
    
    /**
     * @return Free text.
     */
    public String getMemo() {
        return this.memo;
    }
    
    /**
     * @return Arbitrary user provided value associated with the order.
     */
    public String getClientOrderId() {
        return this.clientOrderId;
    }
    
    /**
     * @return Clearing member code.
     */
    public String getClearingMemberCode() {
        return this.clearingMemberCode;
    }
    
    /**
     * @return Clearing member's clearing identifier.
     */
    public ClearingIdentifier getClearingMemberClearingIdentifier() {
        return this.clearingMemberClearingIdentifier;
    }
    
    /**
     * @return Instructions for order handling on exchange trading floor.
     */
    public ExecInst getExecInst() {
        return this.execInst;
    }
    
    /**
     * @return Optional identifier of a fee scheme for billing purposes.
     */
    public int getFeeStructureId() {
        return this.feeStructureId;
    }
    
    /**
     * @return 3rd party interested in this order or trade.
     */
    public String getInterestedParty() {
        return this.interestedParty;
    }
    
    /**
     * @param header Header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param stpId ID assigned by the client used in the Self Trade Prevention mechanism.
     */
    public void setStpId(int stpId) {
        this.stpId = stpId;
    }
    
    /**
     * @param instrumentId ID of the instrument being traded.
     */
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }
    
    /**
     * @param orderType Indicates the order type.
     */
    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
    
    /**
     * @param timeInForce Indicates the order's time in force (e.g. GTC).
     */
    public void setTimeInForce(TimeInForce timeInForce) {
        this.timeInForce = timeInForce;
    }
    
    /**
     * @param side Indicates the order's side (buy or sell).
     */
    public void setSide(OrderSide side) {
        this.side = side;
    }
    
    /**
     * @param price Indicates the price of the order.
     */
    public void setPrice(long price) {
        this.price = price;
    }
    
    /**
     * @param triggerPrice Indicates the trigger price (Last Trade Price - LTP) after which the order should be added to the order book.
     */
    public void setTriggerPrice(long triggerPrice) {
        this.triggerPrice = triggerPrice;
    }
    
    /**
     * @param quantity Indicates the quantity of the instrument included in the order.
     */
    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }
    
    /**
     * @param displayQty Used only for iceberg order. The quantity to be displayed.
     */
    public void setDisplayQty(BigInteger displayQty) {
        this.displayQty = displayQty;
    }
    
    /**
     * @param capacity Capacity of the party making the order (either principal or agency).
     */
    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }
    
    /**
     * @param account Account number.
     */
    public void setAccount(String account) {
        this.account = account;
    }
    
    /**
     * @param accountType Type of account associated with the order.
     */
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    
    /**
     * @param flags Flags raised on an order.
     */
    public void setFlags(OrderFlags flags) {
        this.flags = flags;
    }
    
    /**
     * @param mifidFields Fields related to the MiFID directive.
     */
    public void setMifidFields(MifidFields mifidFields) {
        this.mifidFields = mifidFields;
    }
    
    /**
     * @param expire Expiration time indicating the validity of the order - relevant only when TimeInForce is set to GTD (Good Till Date) or GTT (Good Till Time).
     */
    public void setExpire(BigInteger expire) {
        this.expire = expire;
    }
    
    /**
     * @param memo Free text.
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }
    
    /**
     * @param clientOrderId Arbitrary user provided value associated with the order.
     */
    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }
    
    /**
     * @param clearingMemberCode Clearing member code.
     */
    public void setClearingMemberCode(String clearingMemberCode) {
        this.clearingMemberCode = clearingMemberCode;
    }
    
    /**
     * @param clearingMemberClearingIdentifier Clearing member's clearing identifier.
     */
    public void setClearingMemberClearingIdentifier(ClearingIdentifier clearingMemberClearingIdentifier) {
        this.clearingMemberClearingIdentifier = clearingMemberClearingIdentifier;
    }
    
    /**
     * @param execInst Instructions for order handling on exchange trading floor.
     */
    public void setExecInst(ExecInst execInst) {
        this.execInst = execInst;
    }
    
    /**
     * @param feeStructureId Optional identifier of a fee scheme for billing purposes.
     */
    public void setFeeStructureId(int feeStructureId) {
        this.feeStructureId = feeStructureId;
    }
    
    /**
     * @param interestedParty 3rd party interested in this order or trade.
     */
    public void setInterestedParty(String interestedParty) {
        this.interestedParty = interestedParty;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt8ToByteArray(this.stpId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        orderType.toBytes(buffer);
        timeInForce.toBytes(buffer);
        side.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.int64ToByteArray(this.triggerPrice));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        buffer.put(BendecUtils.uInt64ToByteArray(this.displayQty));
        capacity.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.account, 16));
        accountType.toBytes(buffer);
        flags.toBytes(buffer);
        mifidFields.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.expire));
        buffer.put(BendecUtils.stringToByteArray(this.memo, 18));
        buffer.put(BendecUtils.stringToByteArray(this.clientOrderId, 20));
        buffer.put(BendecUtils.stringToByteArray(this.clearingMemberCode, 20));
        clearingMemberClearingIdentifier.toBytes(buffer);
        execInst.toBytes(buffer);
        buffer.put(BendecUtils.uInt8ToByteArray(this.feeStructureId));
        buffer.put(BendecUtils.stringToByteArray(this.interestedParty, 8));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt8ToByteArray(this.stpId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.instrumentId));
        orderType.toBytes(buffer);
        timeInForce.toBytes(buffer);
        side.toBytes(buffer);
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.int64ToByteArray(this.triggerPrice));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        buffer.put(BendecUtils.uInt64ToByteArray(this.displayQty));
        capacity.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.account, 16));
        accountType.toBytes(buffer);
        flags.toBytes(buffer);
        mifidFields.toBytes(buffer);
        buffer.put(BendecUtils.uInt64ToByteArray(this.expire));
        buffer.put(BendecUtils.stringToByteArray(this.memo, 18));
        buffer.put(BendecUtils.stringToByteArray(this.clientOrderId, 20));
        buffer.put(BendecUtils.stringToByteArray(this.clearingMemberCode, 20));
        clearingMemberClearingIdentifier.toBytes(buffer);
        execInst.toBytes(buffer);
        buffer.put(BendecUtils.uInt8ToByteArray(this.feeStructureId));
        buffer.put(BendecUtils.stringToByteArray(this.interestedParty, 8));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        stpId,
        instrumentId,
        orderType,
        timeInForce,
        side,
        price,
        triggerPrice,
        quantity,
        displayQty,
        capacity,
        account,
        accountType,
        flags,
        mifidFields,
        expire,
        memo,
        clientOrderId,
        clearingMemberCode,
        clearingMemberClearingIdentifier,
        execInst,
        feeStructureId,
        interestedParty);
    }
    
    @Override
    public String toString() {
        return "OrderAdd {" +
            "header=" + header +
            ", stpId=" + stpId +
            ", instrumentId=" + instrumentId +
            ", orderType=" + orderType +
            ", timeInForce=" + timeInForce +
            ", side=" + side +
            ", price=" + price +
            ", triggerPrice=" + triggerPrice +
            ", quantity=" + quantity +
            ", displayQty=" + displayQty +
            ", capacity=" + capacity +
            ", account=" + account +
            ", accountType=" + accountType +
            ", flags=" + flags +
            ", mifidFields=" + mifidFields +
            ", expire=" + expire +
            ", memo=" + memo +
            ", clientOrderId=" + clientOrderId +
            ", clearingMemberCode=" + clearingMemberCode +
            ", clearingMemberClearingIdentifier=" + clearingMemberClearingIdentifier +
            ", execInst=" + execInst +
            ", feeStructureId=" + feeStructureId +
            ", interestedParty=" + interestedParty +
            "}";
    }
}