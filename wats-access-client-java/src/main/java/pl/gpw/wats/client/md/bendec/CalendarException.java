package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>CalendarException</h2>
 * <p>Upcoming calendar day exception.</p>
 * <p>Byte length: 49</p>
 * <p>Header header - Message header. | size 39</p>
 * <p>ElementId > long (u32) calendarId - Calendar ID. | size 4</p>
 * <p>Date > long (u32) calendarExceptionDate - Calendar exception date. | size 4</p>
 * <p>bool > boolean calendarExceptionRecurrent - True if the calendar exception is recurring. | size 1</p>
 * <p>CalendarExceptionType calendarExceptionType - Calendar exception type. | size 1</p>
 * */

public class CalendarException implements ByteSerializable, Message {

    private Header header;
    private long calendarId;
    private long calendarExceptionDate;
    private boolean calendarExceptionRecurrent;
    private CalendarExceptionType calendarExceptionType;
    public static final int byteLength = 49;

    public CalendarException(Header header, long calendarId, long calendarExceptionDate, boolean calendarExceptionRecurrent, CalendarExceptionType calendarExceptionType) {
        this.header = header;
        this.calendarId = calendarId;
        this.calendarExceptionDate = calendarExceptionDate;
        this.calendarExceptionRecurrent = calendarExceptionRecurrent;
        this.calendarExceptionType = calendarExceptionType;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.CALENDAREXCEPTION);
    }

    public CalendarException(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.calendarId = BendecUtils.uInt32FromByteArray(bytes, offset + 39);
        this.calendarExceptionDate = BendecUtils.uInt32FromByteArray(bytes, offset + 43);
        this.calendarExceptionRecurrent = BendecUtils.booleanFromByteArray(bytes, offset + 47);
        this.calendarExceptionType = CalendarExceptionType.getCalendarExceptionType(bytes, offset + 48);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.CALENDAREXCEPTION);
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
    };
    /**
     * @return Calendar ID.
     */
    public long getCalendarId() {
        return this.calendarId;
    };
    /**
     * @return Calendar exception date.
     */
    public long getCalendarExceptionDate() {
        return this.calendarExceptionDate;
    };
    /**
     * @return True if the calendar exception is recurring.
     */
    public boolean getCalendarExceptionRecurrent() {
        return this.calendarExceptionRecurrent;
    };
    /**
     * @return Calendar exception type.
     */
    public CalendarExceptionType getCalendarExceptionType() {
        return this.calendarExceptionType;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param calendarId Calendar ID.
     */
    public void setCalendarId(long calendarId) {
        this.calendarId = calendarId;
    };
    /**
     * @param calendarExceptionDate Calendar exception date.
     */
    public void setCalendarExceptionDate(long calendarExceptionDate) {
        this.calendarExceptionDate = calendarExceptionDate;
    };
    /**
     * @param calendarExceptionRecurrent True if the calendar exception is recurring.
     */
    public void setCalendarExceptionRecurrent(boolean calendarExceptionRecurrent) {
        this.calendarExceptionRecurrent = calendarExceptionRecurrent;
    };
    /**
     * @param calendarExceptionType Calendar exception type.
     */
    public void setCalendarExceptionType(CalendarExceptionType calendarExceptionType) {
        this.calendarExceptionType = calendarExceptionType;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.calendarId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.calendarExceptionDate));
        buffer.put(BendecUtils.booleanToByteArray(this.calendarExceptionRecurrent));
        calendarExceptionType.toBytes(buffer);
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.uInt32ToByteArray(this.calendarId));
        buffer.put(BendecUtils.uInt32ToByteArray(this.calendarExceptionDate));
        buffer.put(BendecUtils.booleanToByteArray(this.calendarExceptionRecurrent));
        calendarExceptionType.toBytes(buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, calendarId, calendarExceptionDate, calendarExceptionRecurrent, calendarExceptionType);
    }

    @Override
    public String toString() {
        return "CalendarException{" +
            "header=" + header +
            ", calendarId=" + calendarId +
            ", calendarExceptionDate=" + calendarExceptionDate +
            ", calendarExceptionRecurrent=" + calendarExceptionRecurrent +
            ", calendarExceptionType=" + calendarExceptionType +
            '}';
        }
}
