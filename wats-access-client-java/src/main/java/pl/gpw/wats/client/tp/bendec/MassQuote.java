package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>MassQuote</h2>
 * <p>Mass Quote</p>
 * <p>Byte length: 1174</p>
 * <p>Header header - Message header. | size 16</p>
 * <p>OnBehalfOf > int (u16) onBehalfOf | size 2</p>
 * <p>STPId > int (u16) stpId - An ID assigned by the client used in Self Match Prevention mechanism. | size 2</p>
 * <p>Capacity capacity - Capacity of the party making the order (either principal or agency). | size 1</p>
 * <p>Account > String (u8[]) account - Account mnemonic as agreed between buy and sell sides. | size 16</p>
 * <p>AccountType accountType - Type of account associated with the order. | size 1</p>
 * <p>MifidFields mifidFields | size 16</p>
 * <p>Memo > String (u8[]) memo | size 18</p>
 * <p>ClearingCode > String (u8[]) clearingMemberCode - Clearing member code. | size 20</p>
 * <p>ClearingIdentifier clearingMemberClearingIdentifier - Clearing member's clearing identifier. | size 1</p>
 * <p>u8 > int count - How many quotes this message contains. | size 1</p>
 * <p>Quotes > Quote[] (Quote[]) quotes - The array of quotes. | size 1080</p>
 * */

public class MassQuote implements ByteSerializable, Message {

    private Header header;
    private int onBehalfOf;
    private int stpId;
    private Capacity capacity;
    private String account;
    private AccountType accountType;
    private MifidFields mifidFields;
    private String memo;
    private String clearingMemberCode;
    private ClearingIdentifier clearingMemberClearingIdentifier;
    private int count;
    private Quote[] quotes;
    public static final int byteLength = 1174;

    public MassQuote(Header header, int onBehalfOf, int stpId, Capacity capacity, String account, AccountType accountType, MifidFields mifidFields, String memo, String clearingMemberCode, ClearingIdentifier clearingMemberClearingIdentifier, int count, Quote[] quotes) {
        this.header = header;
        this.onBehalfOf = onBehalfOf;
        this.stpId = stpId;
        this.capacity = capacity;
        this.account = account;
        this.accountType = accountType;
        this.mifidFields = mifidFields;
        this.memo = memo;
        this.clearingMemberCode = clearingMemberCode;
        this.clearingMemberClearingIdentifier = clearingMemberClearingIdentifier;
        this.count = count;
        this.quotes = quotes;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.MASSQUOTE);
    }

    public MassQuote(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.onBehalfOf = BendecUtils.uInt16FromByteArray(bytes, offset + 16);
        this.stpId = BendecUtils.uInt16FromByteArray(bytes, offset + 18);
        this.capacity = Capacity.getCapacity(bytes, offset + 20);
        this.account = BendecUtils.stringFromByteArray(bytes, offset + 21, 16);
        this.accountType = AccountType.getAccountType(bytes, offset + 37);
        this.mifidFields = new MifidFields(bytes, offset + 38);
        this.memo = BendecUtils.stringFromByteArray(bytes, offset + 54, 18);
        this.clearingMemberCode = BendecUtils.stringFromByteArray(bytes, offset + 72, 20);
        this.clearingMemberClearingIdentifier = ClearingIdentifier.getClearingIdentifier(bytes, offset + 92);
        this.count = BendecUtils.uInt8FromByteArray(bytes, offset + 93);
        this.quotes = new Quote[30];
        for(int i = 0; i < quotes.length; i++) {
            this.quotes[i] = new Quote(bytes, offset + 94 + i * 36);
        }
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.MASSQUOTE);
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
    };
    public int getOnBehalfOf() {
        return this.onBehalfOf;
    };
    /**
     * @return An ID assigned by the client used in Self Match Prevention mechanism.
     */
    public int getStpId() {
        return this.stpId;
    };
    /**
     * @return Capacity of the party making the order (either principal or agency).
     */
    public Capacity getCapacity() {
        return this.capacity;
    };
    /**
     * @return Account mnemonic as agreed between buy and sell sides.
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
    public MifidFields getMifidFields() {
        return this.mifidFields;
    };
    public String getMemo() {
        return this.memo;
    };
    /**
     * @return Clearing member code.
     */
    public String getClearingMemberCode() {
        return this.clearingMemberCode;
    };
    /**
     * @return Clearing member's clearing identifier.
     */
    public ClearingIdentifier getClearingMemberClearingIdentifier() {
        return this.clearingMemberClearingIdentifier;
    };
    /**
     * @return How many quotes this message contains.
     */
    public int getCount() {
        return this.count;
    };
    /**
     * @return The array of quotes.
     */
    public Quote[] getQuotes() {
        return this.quotes;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    public void setOnBehalfOf(int onBehalfOf) {
        this.onBehalfOf = onBehalfOf;
    };
    /**
     * @param stpId An ID assigned by the client used in Self Match Prevention mechanism.
     */
    public void setStpId(int stpId) {
        this.stpId = stpId;
    };
    /**
     * @param capacity Capacity of the party making the order (either principal or agency).
     */
    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    };
    /**
     * @param account Account mnemonic as agreed between buy and sell sides.
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
    public void setMifidFields(MifidFields mifidFields) {
        this.mifidFields = mifidFields;
    };
    public void setMemo(String memo) {
        this.memo = memo;
    };
    /**
     * @param clearingMemberCode Clearing member code.
     */
    public void setClearingMemberCode(String clearingMemberCode) {
        this.clearingMemberCode = clearingMemberCode;
    };
    /**
     * @param clearingMemberClearingIdentifier Clearing member's clearing identifier.
     */
    public void setClearingMemberClearingIdentifier(ClearingIdentifier clearingMemberClearingIdentifier) {
        this.clearingMemberClearingIdentifier = clearingMemberClearingIdentifier;
    };
    /**
     * @param count How many quotes this message contains.
     */
    public void setCount(int count) {
        this.count = count;
    };
    /**
     * @param quotes The array of quotes.
     */
    public void setQuotes(Quote[] quotes) {
        this.quotes = quotes;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt16ToByteArray(this.onBehalfOf));
        buffer.put(BendecUtils.uInt16ToByteArray(this.stpId));
        capacity.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.account, 16));
        accountType.toBytes(buffer);
        mifidFields.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.memo, 18));
        buffer.put(BendecUtils.stringToByteArray(this.clearingMemberCode, 20));
        clearingMemberClearingIdentifier.toBytes(buffer);
        buffer.put(BendecUtils.uInt8ToByteArray(this.count));
        for(int i = 0; i < quotes.length; i++) {
            quotes[i].toBytes(buffer);
        }
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt16ToByteArray(this.onBehalfOf));
        buffer.put(BendecUtils.uInt16ToByteArray(this.stpId));
        capacity.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.account, 16));
        accountType.toBytes(buffer);
        mifidFields.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.memo, 18));
        buffer.put(BendecUtils.stringToByteArray(this.clearingMemberCode, 20));
        clearingMemberClearingIdentifier.toBytes(buffer);
        buffer.put(BendecUtils.uInt8ToByteArray(this.count));
        for(int i = 0; i < quotes.length; i++) {
            quotes[i].toBytes(buffer);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, onBehalfOf, stpId, capacity, account, accountType, mifidFields, memo, clearingMemberCode, clearingMemberClearingIdentifier, count, quotes);
    }

    @Override
    public String toString() {
        return "MassQuote{" +
            "header=" + header +
            ", onBehalfOf=" + onBehalfOf +
            ", stpId=" + stpId +
            ", capacity=" + capacity +
            ", account=" + account +
            ", accountType=" + accountType +
            ", mifidFields=" + mifidFields +
            ", memo=" + memo +
            ", clearingMemberCode=" + clearingMemberCode +
            ", clearingMemberClearingIdentifier=" + clearingMemberClearingIdentifier +
            ", count=" + count +
            ", quotes=" + quotes +
            '}';
        }
}
