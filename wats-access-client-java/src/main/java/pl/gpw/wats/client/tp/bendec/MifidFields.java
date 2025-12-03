package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>MifidFields</h2>
 * <p>Fields related to the MiFID directive.</p>
 * <p>Byte length: 15</p>
 * <p>MifidField client - MifidField of Client. | size 5</p>
 * <p>MifidField executingTrader - MifidField of Executing Trader. | size 5</p>
 * <p>MifidField investmentDecisionMaker - MifidField of Investment decision maker. | size 5</p>
 */
public class MifidFields implements ByteSerializable {
    private MifidField client;
    private MifidField executingTrader;
    private MifidField investmentDecisionMaker;
    public static final int byteLength = 15;
    
    public MifidFields(MifidField client, MifidField executingTrader, MifidField investmentDecisionMaker) {
        this.client = client;
        this.executingTrader = executingTrader;
        this.investmentDecisionMaker = investmentDecisionMaker;
    }
    
    public MifidFields(byte[] bytes, int offset) {
        this.client = new MifidField(bytes, offset);
        this.executingTrader = new MifidField(bytes, offset + 5);
        this.investmentDecisionMaker = new MifidField(bytes, offset + 10);
    }
    
    public MifidFields(byte[] bytes) {
        this(bytes, 0);
    }
    
    public MifidFields() {
    }
    
    /**
     * @return MifidField of Client.
     */
    public MifidField getClient() {
        return this.client;
    }
    
    /**
     * @return MifidField of Executing Trader.
     */
    public MifidField getExecutingTrader() {
        return this.executingTrader;
    }
    
    /**
     * @return MifidField of Investment decision maker.
     */
    public MifidField getInvestmentDecisionMaker() {
        return this.investmentDecisionMaker;
    }
    
    /**
     * @param client MifidField of Client.
     */
    public void setClient(MifidField client) {
        this.client = client;
    }
    
    /**
     * @param executingTrader MifidField of Executing Trader.
     */
    public void setExecutingTrader(MifidField executingTrader) {
        this.executingTrader = executingTrader;
    }
    
    /**
     * @param investmentDecisionMaker MifidField of Investment decision maker.
     */
    public void setInvestmentDecisionMaker(MifidField investmentDecisionMaker) {
        this.investmentDecisionMaker = investmentDecisionMaker;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        client.toBytes(buffer);
        executingTrader.toBytes(buffer);
        investmentDecisionMaker.toBytes(buffer);
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        client.toBytes(buffer);
        executingTrader.toBytes(buffer);
        investmentDecisionMaker.toBytes(buffer);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(client,
        executingTrader,
        investmentDecisionMaker);
    }
    
    @Override
    public String toString() {
        return "MifidFields {" +
            "client=" + client +
            ", executingTrader=" + executingTrader +
            ", investmentDecisionMaker=" + investmentDecisionMaker +
            "}";
    }
}