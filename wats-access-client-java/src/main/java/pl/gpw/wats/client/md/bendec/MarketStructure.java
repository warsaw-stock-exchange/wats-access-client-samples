package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>MarketStructure</h2>
 * <p>Messages defining the Trading Venue’s market structure.</p>
 * <p>Byte length: 102</p>
 * <p>Header header - Message header. | size 39</p>
 * <p>ElementId > long (u32) id - ID of the market structure. | size 4</p>
 * <p>ElementId > long (u32) parentId - ID of the market structure's parent market structure if the market structure is a sub-structure. Set to '0' (zero) if no parent market structure. | size 4</p>
 * <p>MicCode > String (u8[]) mic - Market structure's Market Identifier Code (MIC) as specified in ISO 10383. | size 4</p>
 * <p>Name > String (u8[]) name - Market structure name assigned by market operator. | size 50</p>
 * <p>MarketModelType marketModelType - Market model type. | size 1</p>
 * */

public class MarketStructure implements ByteSerializable, Message {

    private Header header;
    private long id;
    private long parentId;
    private String mic;
    private String name;
    private MarketModelType marketModelType;
    public static final int byteLength = 102;

    public MarketStructure(Header header, long id, long parentId, String mic, String name, MarketModelType marketModelType) {
        this.header = header;
        this.id = id;
        this.parentId = parentId;
        this.mic = mic;
        this.name = name;
        this.marketModelType = marketModelType;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.MARKETSTRUCTURE);
    }

    public MarketStructure(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.id = BendecUtils.uInt32FromByteArray(bytes, offset + 39);
        this.parentId = BendecUtils.uInt32FromByteArray(bytes, offset + 43);
        this.mic = BendecUtils.stringFromByteArray(bytes, offset + 47, 4);
        this.name = BendecUtils.stringFromByteArray(bytes, offset + 51, 50);
        this.marketModelType = MarketModelType.getMarketModelType(bytes, offset + 101);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.MARKETSTRUCTURE);
    }

    public MarketStructure(byte[] bytes) {
        this(bytes, 0);
    }

    public MarketStructure() {
    }



    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return ID of the market structure.
     */
    public long getId() {
        return this.id;
    };
    /**
     * @return ID of the market structure's parent market structure if the market structure is a sub-structure. Set to '0' (zero) if no parent market structure.
     */
    public long getParentId() {
        return this.parentId;
    };
    /**
     * @return Market structure's Market Identifier Code (MIC) as specified in ISO 10383.
     */
    public String getMic() {
        return this.mic;
    };
    /**
     * @return Market structure name assigned by market operator.
     */
    public String getName() {
        return this.name;
    };
    /**
     * @return Market model type.
     */
    public MarketModelType getMarketModelType() {
        return this.marketModelType;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param id ID of the market structure.
     */
    public void setId(long id) {
        this.id = id;
    };
    /**
     * @param parentId ID of the market structure's parent market structure if the market structure is a sub-structure. Set to '0' (zero) if no parent market structure.
     */
    public void setParentId(long parentId) {
        this.parentId = parentId;
    };
    /**
     * @param mic Market structure's Market Identifier Code (MIC) as specified in ISO 10383.
     */
    public void setMic(String mic) {
        this.mic = mic;
    };
    /**
     * @param name Market structure name assigned by market operator.
     */
    public void setName(String name) {
        this.name = name;
    };
    /**
     * @param marketModelType Market model type.
     */
    public void setMarketModelType(MarketModelType marketModelType) {
        this.marketModelType = marketModelType;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.id));
        buffer.put(BendecUtils.uInt32ToByteArray(this.parentId));
        buffer.put(BendecUtils.stringToByteArray(this.mic, 4));
        buffer.put(BendecUtils.stringToByteArray(this.name, 50));
        marketModelType.toBytes(buffer);
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.id));
        buffer.put(BendecUtils.uInt32ToByteArray(this.parentId));
        buffer.put(BendecUtils.stringToByteArray(this.mic, 4));
        buffer.put(BendecUtils.stringToByteArray(this.name, 50));
        marketModelType.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, id, parentId, mic, name, marketModelType);
    }

    @Override
    public String toString() {
        return "MarketStructure{" +
            "header=" + header +
            ", id=" + id +
            ", parentId=" + parentId +
            ", mic=" + mic +
            ", name=" + name +
            ", marketModelType=" + marketModelType +
            '}';
        }
}
