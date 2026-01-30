package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>TcrParty</h2>
 * <p>Information about Trade Capture Report party.</p>
 * <p>Byte length: 82</p>
 * <p>OrderFlags flags - Flags raised on an order. | size 1</p>
 * <p>MifidFields mifidFields - Fields related to the MiFID directive. | size 15</p>
 * <p>ClearingCode > String (u8[]) clearingMemberCode - Clearing member code. | size 20</p>
 * <p>ClearingIdentifier clearingMemberClearingIdentifier - Clearing member's clearing identifier. | size 1</p>
 * <p>Account > String (u8[]) account - Account number. | size 16</p>
 * <p>AccountType accountType - Type of account associated with the order. | size 1</p>
 * <p>Capacity orderCapacity - Designates the capacity of the firm placing the order. | size 1</p>
 * <p>u8 > int feeStructureId - Optional identifier of a fee scheme for billing purposes. | size 1</p>
 * <p>InterestedParty > String (u8[]) interestedParty - 3rd party interested in this order or trade. | size 8</p>
 * <p>Memo > String (u8[]) memo - Free text. | size 18</p>
 */
public class TcrParty implements ByteSerializable {
    private OrderFlags flags;
    private MifidFields mifidFields;
    private String clearingMemberCode;
    private ClearingIdentifier clearingMemberClearingIdentifier;
    private String account;
    private AccountType accountType;
    private Capacity orderCapacity;
    private int feeStructureId;
    private String interestedParty;
    private String memo;
    public static final int byteLength = 82;

    public TcrParty(OrderFlags flags, MifidFields mifidFields, String clearingMemberCode, ClearingIdentifier clearingMemberClearingIdentifier, String account, AccountType accountType, Capacity orderCapacity, int feeStructureId, String interestedParty, String memo) {
        this.flags = flags;
        this.mifidFields = mifidFields;
        this.clearingMemberCode = clearingMemberCode;
        this.clearingMemberClearingIdentifier = clearingMemberClearingIdentifier;
        this.account = account;
        this.accountType = accountType;
        this.orderCapacity = orderCapacity;
        this.feeStructureId = feeStructureId;
        this.interestedParty = interestedParty;
        this.memo = memo;
    }
    
    public TcrParty(byte[] bytes, int offset) {
        this.flags = new OrderFlags(bytes, offset);
        this.mifidFields = new MifidFields(bytes, offset + 1);
        this.clearingMemberCode = BendecUtils.stringFromByteArray(bytes, offset + 16, 20);
        this.clearingMemberClearingIdentifier = ClearingIdentifier.getClearingIdentifier(bytes, offset + 36);
        this.account = BendecUtils.stringFromByteArray(bytes, offset + 37, 16);
        this.accountType = AccountType.getAccountType(bytes, offset + 53);
        this.orderCapacity = Capacity.getCapacity(bytes, offset + 54);
        this.feeStructureId = BendecUtils.uInt8FromByteArray(bytes, offset + 55);
        this.interestedParty = BendecUtils.stringFromByteArray(bytes, offset + 56, 8);
        this.memo = BendecUtils.stringFromByteArray(bytes, offset + 64, 18);
    }
    
    public TcrParty(byte[] bytes) {
        this(bytes, 0);
    }
    
    public TcrParty() {
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
     * @return Designates the capacity of the firm placing the order.
     */
    public Capacity getOrderCapacity() {
        return this.orderCapacity;
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
     * @return Free text.
     */
    public String getMemo() {
        return this.memo;
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
     * @param orderCapacity Designates the capacity of the firm placing the order.
     */
    public void setOrderCapacity(Capacity orderCapacity) {
        this.orderCapacity = orderCapacity;
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
     * @param memo Free text.
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        flags.toBytes(buffer);
        mifidFields.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.clearingMemberCode, 20));
        clearingMemberClearingIdentifier.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.account, 16));
        accountType.toBytes(buffer);
        orderCapacity.toBytes(buffer);
        buffer.put(BendecUtils.uInt8ToByteArray(this.feeStructureId));
        buffer.put(BendecUtils.stringToByteArray(this.interestedParty, 8));
        buffer.put(BendecUtils.stringToByteArray(this.memo, 18));
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        flags.toBytes(buffer);
        mifidFields.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.clearingMemberCode, 20));
        clearingMemberClearingIdentifier.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.account, 16));
        accountType.toBytes(buffer);
        orderCapacity.toBytes(buffer);
        buffer.put(BendecUtils.uInt8ToByteArray(this.feeStructureId));
        buffer.put(BendecUtils.stringToByteArray(this.interestedParty, 8));
        buffer.put(BendecUtils.stringToByteArray(this.memo, 18));
    }

    @Override
    public int hashCode() {
        return Objects.hash(flags,
        mifidFields,
        clearingMemberCode,
        clearingMemberClearingIdentifier,
        account,
        accountType,
        orderCapacity,
        feeStructureId,
        interestedParty,
        memo);
    }
    
    @Override
    public String toString() {
        return "TcrParty {" +
            "flags=" + flags +
            ", mifidFields=" + mifidFields +
            ", clearingMemberCode=" + clearingMemberCode +
            ", clearingMemberClearingIdentifier=" + clearingMemberClearingIdentifier +
            ", account=" + account +
            ", accountType=" + accountType +
            ", orderCapacity=" + orderCapacity +
            ", feeStructureId=" + feeStructureId +
            ", interestedParty=" + interestedParty +
            ", memo=" + memo +
            "}";
    }
}