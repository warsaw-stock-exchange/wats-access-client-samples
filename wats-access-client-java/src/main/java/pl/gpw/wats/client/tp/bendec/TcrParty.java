package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>TcrParty</h2>
 * <p>Information about Trade Capture Report party.</p>
 * <p>Byte length: 82</p>
 * <p>MifidFields mifidFields - Fields related to the MiFID directive. | size 16</p>
 * <p>ClearingCode > String (u8[]) clearingMemberCode - Clearing member code. | size 20</p>
 * <p>ClearingIdentifier clearingMemberClearingIdentifier - Clearing member's clearing identifier. | size 1</p>
 * <p>Account > String (u8[]) account - Account number. | size 16</p>
 * <p>AccountType accountType - Type of account associated with the order. | size 1</p>
 * <p>Capacity orderCapacity - Designates the capacity of the firm placing the order. | size 1</p>
 * <p>ElementId > long (u32) orderRestrictions - Restrictions associated with an order. | size 4</p>
 * <p>ElementId > long (u32) orderOrigination - Identifies the origin of the order. | size 4</p>
 * <p>u8 > int feeStructureId - Optional identifier of a fee scheme for billing purposes. | size 1</p>
 * <p>Memo > String (u8[]) memo - Free text. | size 18</p>
 */
public class TcrParty implements ByteSerializable {
    private MifidFields mifidFields;
    private String clearingMemberCode;
    private ClearingIdentifier clearingMemberClearingIdentifier;
    private String account;
    private AccountType accountType;
    private Capacity orderCapacity;
    private long orderRestrictions;
    private long orderOrigination;
    private int feeStructureId;
    private String memo;
    public static final int byteLength = 82;
    
    public TcrParty(MifidFields mifidFields, String clearingMemberCode, ClearingIdentifier clearingMemberClearingIdentifier, String account, AccountType accountType, Capacity orderCapacity, long orderRestrictions, long orderOrigination, int feeStructureId, String memo) {
        this.mifidFields = mifidFields;
        this.clearingMemberCode = clearingMemberCode;
        this.clearingMemberClearingIdentifier = clearingMemberClearingIdentifier;
        this.account = account;
        this.accountType = accountType;
        this.orderCapacity = orderCapacity;
        this.orderRestrictions = orderRestrictions;
        this.orderOrigination = orderOrigination;
        this.feeStructureId = feeStructureId;
        this.memo = memo;
    }
    
    public TcrParty(byte[] bytes, int offset) {
        this.mifidFields = new MifidFields(bytes, offset);
        this.clearingMemberCode = BendecUtils.stringFromByteArray(bytes, offset + 16, 20);
        this.clearingMemberClearingIdentifier = ClearingIdentifier.getClearingIdentifier(bytes, offset + 36);
        this.account = BendecUtils.stringFromByteArray(bytes, offset + 37, 16);
        this.accountType = AccountType.getAccountType(bytes, offset + 53);
        this.orderCapacity = Capacity.getCapacity(bytes, offset + 54);
        this.orderRestrictions = BendecUtils.uInt32FromByteArray(bytes, offset + 55);
        this.orderOrigination = BendecUtils.uInt32FromByteArray(bytes, offset + 59);
        this.feeStructureId = BendecUtils.uInt8FromByteArray(bytes, offset + 63);
        this.memo = BendecUtils.stringFromByteArray(bytes, offset + 64, 18);
    }
    
    public TcrParty(byte[] bytes) {
        this(bytes, 0);
    }
    
    public TcrParty() {
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
     * @return Restrictions associated with an order.
     */
    public long getOrderRestrictions() {
        return this.orderRestrictions;
    }
    
    /**
     * @return Identifies the origin of the order.
     */
    public long getOrderOrigination() {
        return this.orderOrigination;
    }
    
    /**
     * @return Optional identifier of a fee scheme for billing purposes.
     */
    public int getFeeStructureId() {
        return this.feeStructureId;
    }
    
    /**
     * @return Free text.
     */
    public String getMemo() {
        return this.memo;
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
     * @param orderRestrictions Restrictions associated with an order.
     */
    public void setOrderRestrictions(long orderRestrictions) {
        this.orderRestrictions = orderRestrictions;
    }
    
    /**
     * @param orderOrigination Identifies the origin of the order.
     */
    public void setOrderOrigination(long orderOrigination) {
        this.orderOrigination = orderOrigination;
    }
    
    /**
     * @param feeStructureId Optional identifier of a fee scheme for billing purposes.
     */
    public void setFeeStructureId(int feeStructureId) {
        this.feeStructureId = feeStructureId;
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
        mifidFields.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.clearingMemberCode, 20));
        clearingMemberClearingIdentifier.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.account, 16));
        accountType.toBytes(buffer);
        orderCapacity.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.orderRestrictions));
        buffer.put(BendecUtils.uInt32ToByteArray(this.orderOrigination));
        buffer.put(BendecUtils.uInt8ToByteArray(this.feeStructureId));
        buffer.put(BendecUtils.stringToByteArray(this.memo, 18));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        mifidFields.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.clearingMemberCode, 20));
        clearingMemberClearingIdentifier.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.account, 16));
        accountType.toBytes(buffer);
        orderCapacity.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.orderRestrictions));
        buffer.put(BendecUtils.uInt32ToByteArray(this.orderOrigination));
        buffer.put(BendecUtils.uInt8ToByteArray(this.feeStructureId));
        buffer.put(BendecUtils.stringToByteArray(this.memo, 18));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(mifidFields,
        clearingMemberCode,
        clearingMemberClearingIdentifier,
        account,
        accountType,
        orderCapacity,
        orderRestrictions,
        orderOrigination,
        feeStructureId,
        memo);
    }
    
    @Override
    public String toString() {
        return "TcrParty {" +
            "mifidFields=" + mifidFields +
            ", clearingMemberCode=" + clearingMemberCode +
            ", clearingMemberClearingIdentifier=" + clearingMemberClearingIdentifier +
            ", account=" + account +
            ", accountType=" + accountType +
            ", orderCapacity=" + orderCapacity +
            ", orderRestrictions=" + orderRestrictions +
            ", orderOrigination=" + orderOrigination +
            ", feeStructureId=" + feeStructureId +
            ", memo=" + memo +
            "}";
    }
}