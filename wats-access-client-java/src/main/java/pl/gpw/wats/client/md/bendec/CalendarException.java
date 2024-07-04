package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import java.nio.ByteBuffer;

/**
 * <h2>CalendarException</h2>
 * <p>Upcoming calendar day exception.</p>
 * <p>Byte length: 51</p>
 * <p>Header header - Message header. | size 42</p>
 * <p>ElementId > long (u32) calendarId - Calendar ID. | size 4</p>
 * <p>Date > long (u32) calendarExceptionDate - Calendar exception date. | size 4</p>
 * <p>CalendarExceptionType calendarExceptionType - Calendar exception type. | size 1</p>
 */
public class CalendarException implements ByteSerializable, Message {
    private Header header;
    private long calendarId;
    private long calendarExceptionDate;
    private CalendarExceptionType calendarExceptionType;
    public static final int byteLength = 51;
    
    public CalendarException(Header header, long calendarId, long calendarExceptionDate, CalendarExceptionType calendarExceptionType) {
        this.header = header;
        this.calendarId = calendarId;
        this.calendarExceptionDate = calendarExceptionDate;
        this.calendarExceptionType = calendarExceptionType;
    }
    
    public CalendarException(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.calendarId = BendecUtils.uInt32FromByteArray(bytes, offset + 42);
        this.calendarExceptionDate = BendecUtils.uInt32FromByteArray(bytes, offset + 46);
        this.calendarExceptionType = CalendarExceptionType.getCalendarExceptionType(bytes, offset + 50);
    }
    
    public CalendarException(byte[] bytes) {
        this(bytes, 0);
    }
    
    public CalendarException() {
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
     * @return Calendar exception date.
     */
    public long getCalendarExceptionDate() {
        return this.calendarExceptionDate;
    }
    
    /**
     * @return Calendar exception type.
     */
    public CalendarExceptionType getCalendarExceptionType() {
        return this.calendarExceptionType;
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
     * @param calendarExceptionDate Calendar exception date.
     */
    public void setCalendarExceptionDate(long calendarExceptionDate) {
        this.calendarExceptionDate = calendarExceptionDate;
    }
    
    /**
     * @param calendarExceptionType Calendar exception type.
     */
    public void setCalendarExceptionType(CalendarExceptionType calendarExceptionType) {
        this.calendarExceptionType = calendarExceptionType;
    }
    
    @Override
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.calendarId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.calendarExceptionDate));
        calendarExceptionType.toBytes(buffer);
        return buffer.array();
    }
    
    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.calendarId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.calendarExceptionDate));
        calendarExceptionType.toBytes(buffer);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(header,
        calendarId,
        calendarExceptionDate,
        calendarExceptionType);
    }
    
    @Override
    public String toString() {
        return "CalendarException {" +
            "header=" + header +
            ", calendarId=" + calendarId +
            ", calendarExceptionDate=" + calendarExceptionDate +
            ", calendarExceptionType=" + calendarExceptionType +
            "}";
    }
}