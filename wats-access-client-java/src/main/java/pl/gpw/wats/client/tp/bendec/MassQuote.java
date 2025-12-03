package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>MassQuote</h2>
 * <p>Mass Quote</p>
 * <p>Byte length: 1208</p>
 * <p>Header header - Message header. | size 24</p>
 * <p>STPId > int (u8) stpId - An ID assigned by the client used in Self Match Prevention mechanism. | size 1</p>
 * <p>Capacity capacity - Capacity of the party making the order (either principal or agency). | size 1</p>
 * <p>Account > String (u8[]) account - Account mnemonic as agreed between buy and sell sides. | size 16</p>
 * <p>AccountType accountType - Type of account associated with the order. | size 1</p>
 * <p>OrderFlags flags - Flags raised on an order. | size 1</p>
 * <p>MifidFields mifidFields | size 15</p>
 * <p>Memo > String (u8[]) memo | size 18</p>
 * <p>ClearingCode > String (u8[]) clearingMemberCode - Clearing member code. | size 20</p>
 * <p>ClearingIdentifier clearingMemberClearingIdentifier - Clearing member's clearing identifier. | size 1</p>
 * <p>Quotes quotes - The array of quotes. | size 1081</p>
 * <p>u8 > int feeStructureId - Optional identifier of a fee scheme for billing purposes. | size 1</p>
 * <p>InterestedParty > String (u8[]) interestedParty - 3rd party interested in this order or trade. | size 8</p>
 * <p>ClientOrderId > String (u8[]) quoteId - Arbitrary user provided value associated with the mass quote. | size 20</p>
 */
public class MassQuote implements ByteSerializable, Message {
    private Header header;
    private int stpId;
    private Capacity capacity;
    private String account;
    private AccountType accountType;
    private OrderFlags flags;
    private MifidFields mifidFields;
    private String memo;
    private String clearingMemberCode;
    private ClearingIdentifier clearingMemberClearingIdentifier;
    private Quotes quotes;
    private int feeStructureId;
    private String interestedParty;
    private String quoteId;
    public static final int byteLength = 1208;
    
    public MassQuote(Header header, int stpId, Capacity capacity, String account, AccountType accountType, OrderFlags flags, MifidFields mifidFields, String memo, String clearingMemberCode, ClearingIdentifier clearingMemberClearingIdentifier, Quotes quotes, int feeStructureId, String interestedParty, String quoteId) {
        this.header = header;
        this.stpId = stpId;
        this.capacity = capacity;
        this.account = account;
        this.accountType = accountType;
        this.flags = flags;
        this.mifidFields = mifidFields;
        this.memo = memo;
        this.clearingMemberCode = clearingMemberCode;
        this.clearingMemberClearingIdentifier = clearingMemberClearingIdentifier;
        this.quotes = quotes;
        this.feeStructureId = feeStructureId;
        this.interestedParty = interestedParty;
        this.quoteId = quoteId;
    }
    
    public MassQuote(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.stpId = BendecUtils.uInt8FromByteArray(bytes, offset + 24);
        this.capacity = Capacity.getCapacity(bytes, offset + 25);
        this.account = BendecUtils.stringFromByteArray(bytes, offset + 26, 16);
        this.accountType = AccountType.getAccountType(bytes, offset + 42);
        this.flags = new OrderFlags(bytes, offset + 43);
        this.mifidFields = new MifidFields(bytes, offset + 44);
        this.memo = BendecUtils.stringFromByteArray(bytes, offset + 59, 18);
        this.clearingMemberCode = BendecUtils.stringFromByteArray(bytes, offset + 77, 20);
        this.clearingMemberClearingIdentifier = ClearingIdentifier.getClearingIdentifier(bytes, offset + 97);
        this.quotes = new Quotes(bytes, offset + 98);
        this.feeStructureId = BendecUtils.uInt8FromByteArray(bytes, offset + 1179);
        this.interestedParty = BendecUtils.stringFromByteArray(bytes, offset + 1180, 8);
        this.quoteId = BendecUtils.stringFromByteArray(bytes, offset + 1188, 20);
    }
    
    public MassQuote(byte[] bytes) {
        this(bytes, 0);
    }
    
    public MassQuote() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return An ID assigned by the client used in Self Match Prevention mechanism.
     */
    public int getStpId() {
        return this.stpId;
    }
    
    /**
     * @return Capacity of the party making the order (either principal or agency).
     */
    public Capacity getCapacity() {
        return this.capacity;
    }
    
    /**
     * @return Account mnemonic as agreed between buy and sell sides.
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
    
    public MifidFields getMifidFields() {
        return this.mifidFields;
    }
    
    public String getMemo() {
        return this.memo;
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
     * @return The array of quotes.
     */
    public Quotes getQuotes() {
        return this.quotes;
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
     * @return Arbitrary user provided value associated with the mass quote.
     */
    public String getQuoteId() {
        return this.quoteId;
    }
    
    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param stpId An ID assigned by the client used in Self Match Prevention mechanism.
     */
    public void setStpId(int stpId) {
        this.stpId = stpId;
    }
    
    /**
     * @param capacity Capacity of the party making the order (either principal or agency).
     */
    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }
    
    /**
     * @param account Account mnemonic as agreed between buy and sell sides.
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
    
    public void setMifidFields(MifidFields mifidFields) {
        this.mifidFields = mifidFields;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
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
     * @param quotes The array of quotes.
     */
    public void setQuotes(Quotes quotes) {
        this.quotes = quotes;
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
    
    /**
     * @param quoteId Arbitrary user provided value associated with the mass quote.
     */
    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt8ToByteArray(this.stpId));
        capacity.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.account, 16));
        accountType.toBytes(buffer);
        flags.toBytes(buffer);
        mifidFields.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.memo, 18));
        buffer.put(BendecUtils.stringToByteArray(this.clearingMemberCode, 20));
        clearingMemberClearingIdentifier.toBytes(buffer);
        quotes.toBytes(buffer);
        buffer.put(BendecUtils.uInt8ToByteArray(this.feeStructureId));
        buffer.put(BendecUtils.stringToByteArray(this.interestedParty, 8));
        buffer.put(BendecUtils.stringToByteArray(this.quoteId, 20));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt8ToByteArray(this.stpId));
        capacity.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.account, 16));
        accountType.toBytes(buffer);
        flags.toBytes(buffer);
        mifidFields.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.memo, 18));
        buffer.put(BendecUtils.stringToByteArray(this.clearingMemberCode, 20));
        clearingMemberClearingIdentifier.toBytes(buffer);
        quotes.toBytes(buffer);
        buffer.put(BendecUtils.uInt8ToByteArray(this.feeStructureId));
        buffer.put(BendecUtils.stringToByteArray(this.interestedParty, 8));
        buffer.put(BendecUtils.stringToByteArray(this.quoteId, 20));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        stpId,
        capacity,
        account,
        accountType,
        flags,
        mifidFields,
        memo,
        clearingMemberCode,
        clearingMemberClearingIdentifier,
        quotes,
        feeStructureId,
        interestedParty,
        quoteId);
    }
    
    @Override
    public String toString() {
        return "MassQuote {" +
            "header=" + header +
            ", stpId=" + stpId +
            ", capacity=" + capacity +
            ", account=" + account +
            ", accountType=" + accountType +
            ", flags=" + flags +
            ", mifidFields=" + mifidFields +
            ", memo=" + memo +
            ", clearingMemberCode=" + clearingMemberCode +
            ", clearingMemberClearingIdentifier=" + clearingMemberClearingIdentifier +
            ", quotes=" + quotes +
            ", feeStructureId=" + feeStructureId +
            ", interestedParty=" + interestedParty +
            ", quoteId=" + quoteId +
            "}";
    }
}