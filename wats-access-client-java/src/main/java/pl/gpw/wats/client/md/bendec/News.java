package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>News</h2>
 * <p>News message.</p>
 * <p>Byte length: 932</p>
 * <p>Header header - Message header | size 42</p>
 * <p>ElementId > long (u32) marketStructureId - ID of the instrument's market structure. | size 4</p>
 * <p>ElementId > long (u32) newsId - News id - The news id serves as a unique reference for tracking  purposes within the WATS system. | size 4</p>
 * <p>NewsTitle > String (u8[]) title - News title, unique per message. | size 80</p>
 * <p>u8 > int entryNumber - News entry number. | size 1</p>
 * <p>u8 > int total - Total number of news entries. | size 1</p>
 * <p>NewsText > String (u8[]) text - News text. | size 800</p>
 */
public class News implements ByteSerializable, Message {
    private Header header;
    private long marketStructureId;
    private long newsId;
    private String title;
    private int entryNumber;
    private int total;
    private String text;
    public static final int byteLength = 932;
    
    public News(Header header, long marketStructureId, long newsId, String title, int entryNumber, int total, String text) {
        this.header = header;
        this.marketStructureId = marketStructureId;
        this.newsId = newsId;
        this.title = title;
        this.entryNumber = entryNumber;
        this.total = total;
        this.text = text;
    }
    
    public News(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.marketStructureId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.newsId = BendecUtils.uInt32FromByteArray(bytes, offset + 46);
        this.title = BendecUtils.stringFromByteArray(bytes, offset + 50, 80);
        this.entryNumber = BendecUtils.uInt8FromByteArray(bytes, offset + 130);
        this.total = BendecUtils.uInt8FromByteArray(bytes, offset + 131);
        this.text = BendecUtils.stringFromByteArray(bytes, offset + 132, 800);
    }
    
    public News(byte[] bytes) {
        this(bytes, 0);
    }
    
    public News() {
    }
    
    /**
     * @return Message header
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return ID of the instrument's market structure.
     */
    public long getMarketStructureId() {
        return this.marketStructureId;
    }
    
    /**
     * @return News id - The news id serves as a unique reference for tracking  purposes within the WATS system.
     */
    public long getNewsId() {
        return this.newsId;
    }
    
    /**
     * @return News title, unique per message.
     */
    public String getTitle() {
        return this.title;
    }
    
    /**
     * @return News entry number.
     */
    public int getEntryNumber() {
        return this.entryNumber;
    }
    
    /**
     * @return Total number of news entries.
     */
    public int getTotal() {
        return this.total;
    }
    
    /**
     * @return News text.
     */
    public String getText() {
        return this.text;
    }
    
    /**
     * @param header Message header
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param marketStructureId ID of the instrument's market structure.
     */
    public void setMarketStructureId(long marketStructureId) {
        this.marketStructureId = marketStructureId;
    }
    
    /**
     * @param newsId News id - The news id serves as a unique reference for tracking  purposes within the WATS system.
     */
    public void setNewsId(long newsId) {
        this.newsId = newsId;
    }
    
    /**
     * @param title News title, unique per message.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * @param entryNumber News entry number.
     */
    public void setEntryNumber(int entryNumber) {
        this.entryNumber = entryNumber;
    }
    
    /**
     * @param total Total number of news entries.
     */
    public void setTotal(int total) {
        this.total = total;
    }
    
    /**
     * @param text News text.
     */
    public void setText(String text) {
        this.text = text;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.marketStructureId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.newsId));
        buffer.put(BendecUtils.stringToByteArray(this.title, 80));
        buffer.put(BendecUtils.uInt8ToByteArray(this.entryNumber));
        buffer.put(BendecUtils.uInt8ToByteArray(this.total));
        buffer.put(BendecUtils.stringToByteArray(this.text, 800));
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.marketStructureId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.newsId));
        buffer.put(BendecUtils.stringToByteArray(this.title, 80));
        buffer.put(BendecUtils.uInt8ToByteArray(this.entryNumber));
        buffer.put(BendecUtils.uInt8ToByteArray(this.total));
        buffer.put(BendecUtils.stringToByteArray(this.text, 800));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        marketStructureId,
        newsId,
        title,
        entryNumber,
        total,
        text);
    }
    
    @Override
    public String toString() {
        return "News {" +
            "header=" + header +
            ", marketStructureId=" + marketStructureId +
            ", newsId=" + newsId +
            ", title=" + title +
            ", entryNumber=" + entryNumber +
            ", total=" + total +
            ", text=" + text +
            "}";
    }
}