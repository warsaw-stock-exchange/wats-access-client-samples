package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>TcrParty</h2>
 * <p>Information about Trade Capture Report party.</p>
 * <p>Byte length: 60</p>
 * <p>MifidFields mifidFields - Fields related to the MiFID directive. | size 16</p>
 * <p>Account > String (u8[]) account - Account number. | size 16</p>
 * <p>AccountType accountType - Type of account associated with the order. | size 1</p>
 * <p>Capacity orderCapacity - Designates the capacity of the firm placing the order. | size 1</p>
 * <p>ElementId > long (u32) orderRestrictions - Restrictions associated with an order. | size 4</p>
 * <p>ElementId > long (u32) orderOrigination - Identifies the origin of the order. | size 4</p>
 * <p>Memo > String (u8[]) memo - Free text. | size 18</p>
 * */

public class TcrParty implements ByteSerializable {

    private MifidFields mifidFields;
    private String account;
    private AccountType accountType;
    private Capacity orderCapacity;
    private long orderRestrictions;
    private long orderOrigination;
    private String memo;
    public static final int byteLength = 60;

    public TcrParty(MifidFields mifidFields, String account, AccountType accountType, Capacity orderCapacity, long orderRestrictions, long orderOrigination, String memo) {
        this.mifidFields = mifidFields;
        this.account = account;
        this.accountType = accountType;
        this.orderCapacity = orderCapacity;
        this.orderRestrictions = orderRestrictions;
        this.orderOrigination = orderOrigination;
        this.memo = memo;
    }

    public TcrParty(byte[] bytes, int offset) {
        this.mifidFields = new MifidFields(bytes, offset);
        this.account = BendecUtils.stringFromByteArray(bytes, offset + 16, 16);
        this.accountType = AccountType.getAccountType(bytes, offset + 32);
        this.orderCapacity = Capacity.getCapacity(bytes, offset + 33);
        this.orderRestrictions = BendecUtils.uInt32FromByteArray(bytes, offset + 34);
        this.orderOrigination = BendecUtils.uInt32FromByteArray(bytes, offset + 38);
        this.memo = BendecUtils.stringFromByteArray(bytes, offset + 42, 18);
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
    };
    /**
     * @return Account number.
     */
    public String getAccount() {
        return this.account;
    };
    /**
     * @return Type of account associated with the order.
     */
    public AccountType getAccountType() {
        return this.accountType;
    };
    /**
     * @return Designates the capacity of the firm placing the order.
     */
    public Capacity getOrderCapacity() {
        return this.orderCapacity;
    };
    /**
     * @return Restrictions associated with an order.
     */
    public long getOrderRestrictions() {
        return this.orderRestrictions;
    };
    /**
     * @return Identifies the origin of the order.
     */
    public long getOrderOrigination() {
        return this.orderOrigination;
    };
    /**
     * @return Free text.
     */
    public String getMemo() {
        return this.memo;
    };

    /**
     * @param mifidFields Fields related to the MiFID directive.
     */
    public void setMifidFields(MifidFields mifidFields) {
        this.mifidFields = mifidFields;
    };
    /**
     * @param account Account number.
     */
    public void setAccount(String account) {
        this.account = account;
    };
    /**
     * @param accountType Type of account associated with the order.
     */
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    };
    /**
     * @param orderCapacity Designates the capacity of the firm placing the order.
     */
    public void setOrderCapacity(Capacity orderCapacity) {
        this.orderCapacity = orderCapacity;
    };
    /**
     * @param orderRestrictions Restrictions associated with an order.
     */
    public void setOrderRestrictions(long orderRestrictions) {
        this.orderRestrictions = orderRestrictions;
    };
    /**
     * @param orderOrigination Identifies the origin of the order.
     */
    public void setOrderOrigination(long orderOrigination) {
        this.orderOrigination = orderOrigination;
    };
    /**
     * @param memo Free text.
     */
    public void setMemo(String memo) {
        this.memo = memo;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        mifidFields.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.account, 16));
        accountType.toBytes(buffer);
        orderCapacity.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.orderRestrictions));
        buffer.put(BendecUtils.uInt32ToByteArray(this.orderOrigination));
        buffer.put(BendecUtils.stringToByteArray(this.memo, 18));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        mifidFields.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.account, 16));
        accountType.toBytes(buffer);
        orderCapacity.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.orderRestrictions));
        buffer.put(BendecUtils.uInt32ToByteArray(this.orderOrigination));
        buffer.put(BendecUtils.stringToByteArray(this.memo, 18));
    }

    @Override
    public int hashCode() {
        return Objects.hash(mifidFields, account, accountType, orderCapacity, orderRestrictions, orderOrigination, memo);
    }

    @Override
    public String toString() {
        return "TcrParty{" +
            "mifidFields=" + mifidFields +
            ", account=" + account +
            ", accountType=" + accountType +
            ", orderCapacity=" + orderCapacity +
            ", orderRestrictions=" + orderRestrictions +
            ", orderOrigination=" + orderOrigination +
            ", memo=" + memo +
            '}';
        }
}
