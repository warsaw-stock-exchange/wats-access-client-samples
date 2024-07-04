package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>TestEvent</h2>
 * <p>Test event</p>
 * <p>Byte length: 217</p>
 * <p>Header header - Message header. | size 16</p>
 * <p>ScenarioName > String (u8[]) scenarioName - Test scenario name | size 200</p>
 * <p>EventType eventType - Test event type | size 1</p>
 */
public class TestEvent implements ByteSerializable, Message {
    private Header header;
    private String scenarioName;
    private EventType eventType;
    public static final int byteLength = 217;
    
    public TestEvent(Header header, String scenarioName, EventType eventType) {
        this.header = header;
        this.scenarioName = scenarioName;
        this.eventType = eventType;
    }
    
    public TestEvent(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.scenarioName = BendecUtils.stringFromByteArray(bytes, offset + 16, 200);
        this.eventType = EventType.getEventType(bytes, offset + 216);
    }
    
    public TestEvent(byte[] bytes) {
        this(bytes, 0);
    }
    
    public TestEvent() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Test scenario name
     */
    public String getScenarioName() {
        return this.scenarioName;
    }
    
    /**
     * @return Test event type
     */
    public EventType getEventType() {
        return this.eventType;
    }
    
    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param scenarioName Test scenario name
     */
    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }
    
    /**
     * @param eventType Test event type
     */
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.scenarioName, 200));
        eventType.toBytes(buffer);
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.scenarioName, 200));
        eventType.toBytes(buffer);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        scenarioName,
        eventType);
    }
    
    @Override
    public String toString() {
        return "TestEvent {" +
            "header=" + header +
            ", scenarioName=" + scenarioName +
            ", eventType=" + eventType +
            "}";
    }
}