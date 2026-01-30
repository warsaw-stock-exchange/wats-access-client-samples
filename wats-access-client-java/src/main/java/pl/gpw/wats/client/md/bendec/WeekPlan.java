package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>WeekPlan</h2>
 * <p>Trading week plan.</p>
 * <p>Byte length: 53</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) calendarId - Calendar ID. | size 4</p>
 * <p>Weekdays > boolean[] (bool[]) weekdays - Boolean array of weekdays (Monday to Sunday) open or closed for trading; true – weekday open for trading, false – weekday closed for trading. | size 7</p>
 */
public class WeekPlan implements ByteSerializable, Message {
    private Header header;
    private long calendarId;
    private boolean[] weekdays;
    public static final int byteLength = 53;

    public WeekPlan(Header header, long calendarId, boolean[] weekdays) {
        this.header = header;
        this.calendarId = calendarId;
        this.weekdays = weekdays;
    }
    
    public WeekPlan(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.calendarId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.weekdays = new boolean[7];
        for(int i = 0; i < weekdays.length; i++) {
            this.weekdays[i] = BendecUtils.booleanFromByteArray(bytes, offset + 46 + i * 1);
        }
    }
    
    public WeekPlan(byte[] bytes) {
        this(bytes, 0);
    }
    
    public WeekPlan() {
    }
    
    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    }
    
    /**
     * @return Calendar ID.
     */
    public long getCalendarId() {
        return this.calendarId;
    }
    
    /**
     * @return Boolean array of weekdays (Monday to Sunday) open or closed for trading; true – weekday open for trading, false – weekday closed for trading.
     */
    public boolean[] getWeekdays() {
        return this.weekdays;
    }

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    
    /**
     * @param calendarId Calendar ID.
     */
    public void setCalendarId(long calendarId) {
        this.calendarId = calendarId;
    }
    
    /**
     * @param weekdays Boolean array of weekdays (Monday to Sunday) open or closed for trading; true – weekday open for trading, false – weekday closed for trading.
     */
    public void setWeekdays(boolean[] weekdays) {
        this.weekdays = weekdays;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.calendarId));
        for(int i = 0; i < weekdays.length; i++) {
            buffer.put(BendecUtils.booleanToByteArray(this.weekdays[i]));
        }
        return buffer.array();
    }
    
    @Override
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.calendarId));
        for(int i = 0; i < weekdays.length; i++) {
            buffer.put(BendecUtils.booleanToByteArray(this.weekdays[i]));
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(header,
        calendarId,
        weekdays);
    }
    
    @Override
    public String toString() {
        return "WeekPlan {" +
            "header=" + header +
            ", calendarId=" + calendarId +
            ", weekdays=" + weekdays +
            "}";
    }
}