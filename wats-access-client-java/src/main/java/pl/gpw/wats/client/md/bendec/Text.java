package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>Text</h2>
 * <p>A text message.</p>
 * <p>Byte length: 89</p>
 * <p>Header header - Message header. | size 39</p>
 * <p>TextMessage > String (u8[]) text - Arbitrary text. | size 50</p>
 * */

public class Text implements ByteSerializable, Message {

    private Header header;
    private String text;
    public static final int byteLength = 89;

    public Text(Header header, String text) {
        this.header = header;
        this.text = text;
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.TEXT);
    }

    public Text(byte[] bytes, int offset) {
        this.header = new Header(bytes, offset);
        this.text = BendecUtils.stringFromByteArray(bytes, offset + 39, 50);
        this.header.setLength(this.byteLength);
        this.header.setMsgType(MsgType.TEXT);
    }

    public Text(byte[] bytes) {
        this(bytes, 0);
    }

    public Text() {
    }



    /**
     * @return Message header.
     */
    public Header getHeader() {
        return this.header;
    };
    /**
     * @return Arbitrary text.
     */
    public String getText() {
        return this.text;
    };

    /**
     * @param header Message header.
     */
    public void setHeader(Header header) {
        this.header = header;
    };
    /**
     * @param text Arbitrary text.
     */
    public void setText(String text) {
        this.text = text;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        header.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.text, 50));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        header.toBytes(buffer);
        buffer.put(BendecUtils.stringToByteArray(this.text, 50));
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, text);
    }

    @Override
    public String toString() {
        return "Text{" +
            "header=" + header +
            ", text=" + text +
            '}';
        }
}
