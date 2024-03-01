package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>MifidFields</h2>
 * <p>Fields related to the MiFID directive.</p>
 * <p>Byte length: 16</p>
 * <p>MifidFlags flags - Flags raised on an order in compliance with the MiFID directive. | size 1</p>
 * <p>MifidField client - MifidField of Client. | size 5</p>
 * <p>MifidField executingTrader - MifidField of Executing Trader. | size 5</p>
 * <p>MifidField investmentDecisionMaker - MifidField of Investment decision maker. | size 5</p>
 * */

public class MifidFields implements ByteSerializable {

    private MifidFlags flags;
    private MifidField client;
    private MifidField executingTrader;
    private MifidField investmentDecisionMaker;
    public static final int byteLength = 16;

    public MifidFields(MifidFlags flags, MifidField client, MifidField executingTrader, MifidField investmentDecisionMaker) {
        this.flags = flags;
        this.client = client;
        this.executingTrader = executingTrader;
        this.investmentDecisionMaker = investmentDecisionMaker;
    }

    public MifidFields(byte[] bytes, int offset) {
        this.flags = MifidFlags.getMifidFlags(bytes, offset);
        this.client = new MifidField(bytes, offset + 1);
        this.executingTrader = new MifidField(bytes, offset + 6);
        this.investmentDecisionMaker = new MifidField(bytes, offset + 11);
    }

    public MifidFields(byte[] bytes) {
        this(bytes, 0);
    }

    public MifidFields() {
    }



    /**
     * @return Flags raised on an order in compliance with the MiFID directive.
     */
    public MifidFlags getFlags() {
        return this.flags;
    };
    /**
     * @return MifidField of Client.
     */
    public MifidField getClient() {
        return this.client;
    };
    /**
     * @return MifidField of Executing Trader.
     */
    public MifidField getExecutingTrader() {
        return this.executingTrader;
    };
    /**
     * @return MifidField of Investment decision maker.
     */
    public MifidField getInvestmentDecisionMaker() {
        return this.investmentDecisionMaker;
    };

    /**
     * @param flags Flags raised on an order in compliance with the MiFID directive.
     */
    public void setFlags(MifidFlags flags) {
        this.flags = flags;
    };
    /**
     * @param client MifidField of Client.
     */
    public void setClient(MifidField client) {
        this.client = client;
    };
    /**
     * @param executingTrader MifidField of Executing Trader.
     */
    public void setExecutingTrader(MifidField executingTrader) {
        this.executingTrader = executingTrader;
    };
    /**
     * @param investmentDecisionMaker MifidField of Investment decision maker.
     */
    public void setInvestmentDecisionMaker(MifidField investmentDecisionMaker) {
        this.investmentDecisionMaker = investmentDecisionMaker;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        flags.toBytes(buffer);
        client.toBytes(buffer);
        executingTrader.toBytes(buffer);
        investmentDecisionMaker.toBytes(buffer);
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        flags.toBytes(buffer);
        client.toBytes(buffer);
        executingTrader.toBytes(buffer);
        investmentDecisionMaker.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flags, client, executingTrader, investmentDecisionMaker);
    }

    @Override
    public String toString() {
        return "MifidFields{" +
            "flags=" + flags +
            ", client=" + client +
            ", executingTrader=" + executingTrader +
            ", investmentDecisionMaker=" + investmentDecisionMaker +
            '}';
        }
}
