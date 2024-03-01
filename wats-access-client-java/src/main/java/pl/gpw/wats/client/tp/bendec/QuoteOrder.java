package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.nio.ByteBuffer;


/**
 * <h2>QuoteOrder</h2>
 * <p>Quote order with price and quantity.</p>
 * <p>Byte length: 16</p>
 * <p>Price > long (i64) price - Indicates the price of the given order. | size 8</p>
 * <p>Quantity > BigInteger (u64) quantity - Indicates the quantity of the instrument included in the order. | size 8</p>
 * */

public class QuoteOrder implements ByteSerializable {

    private long price;
    private BigInteger quantity;
    public static final int byteLength = 16;

    public QuoteOrder(long price, BigInteger quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public QuoteOrder(byte[] bytes, int offset) {
        this.price = BendecUtils.int64FromByteArray(bytes, offset);
        this.quantity = BendecUtils.uInt64FromByteArray(bytes, offset + 8);
    }

    public QuoteOrder(byte[] bytes) {
        this(bytes, 0);
    }

    public QuoteOrder() {
    }



    /**
     * @return Indicates the price of the given order.
     */
    public long getPrice() {
        return this.price;
    };
    /**
     * @return Indicates the quantity of the instrument included in the order.
     */
    public BigInteger getQuantity() {
        return this.quantity;
    };

    /**
     * @param price Indicates the price of the given order.
     */
    public void setPrice(long price) {
        this.price = price;
    };
    /**
     * @param quantity Indicates the quantity of the instrument included in the order.
     */
    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    };


    @Override  
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(this.byteLength);
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
        return buffer.array();
    }

    @Override  
    public void toBytes(ByteBuffer buffer) {
        buffer.put(BendecUtils.int64ToByteArray(this.price));
        buffer.put(BendecUtils.uInt64ToByteArray(this.quantity));
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, quantity);
    }

    @Override
    public String toString() {
        return "QuoteOrder{" +
            "price=" + price +
            ", quantity=" + quantity +
            '}';
        }
}
