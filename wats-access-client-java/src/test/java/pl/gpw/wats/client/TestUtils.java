package pl.gpw.wats.client;

import pl.gpw.wats.client.md.MdSnapshotConnectionConfig;
import pl.gpw.wats.client.md.OnlineMarketDataSnapshotClient;
import pl.gpw.wats.client.tp.bendec.*;

import java.io.IOException;
import java.math.BigInteger;

public class TestUtils {

    public static OrderAdd orderAdd(Long instrumentId, OrderSide side, Long price, BigInteger quantity) {
        return new OrderAdd(
                new Header(OrderAdd.byteLength, MsgType.ORDERADD, 0, BigInteger.ZERO),
                0,
                0,
                instrumentId,
                OrderType.LIMIT,
                TimeInForce.DAY,
                side,
                price,
                0,
                quantity,
                BigInteger.ZERO,
                Capacity.AGENCY,
                "TEST",
                AccountType.MISSING,
                new MifidFields(MifidFlags.NONE,
                    new MifidField(1234567, PartyRoleQualifier.FIRMORLEGALENTITY),
                    new MifidField(1234567, PartyRoleQualifier.ALGORITHM),
                    new MifidField(1234567, PartyRoleQualifier.NATURALPERSON)),
                BigInteger.TEN,
                "",
                "",
                ClearingIdentifier.NOTAPPLICABLE);
    }

    public static OrderModify orderModify(BigInteger orderId, Long price, BigInteger quantity) {
        return new OrderModify(
                new Header(OrderModify.byteLength, MsgType.ORDERMODIFY, 0, BigInteger.ZERO),
                orderId,
                price,
                0,
                quantity,
                BigInteger.ZERO);
    }

    public static OrderCancel orderCancel(BigInteger orderId) {
        return new OrderCancel(
                new Header(OrderCancel.byteLength, MsgType.ORDERCANCEL, 0, BigInteger.ZERO),
                orderId);
    }

    public static EncryptionUtils getEncryptionUtils() throws IOException, NoSuchFieldException {
        MdSnapshotConnectionConfig config = Config.createMdSnapshotConfig(
                "services.market_data.snapshot",
                "connections.market_data_connection_0");
        OnlineMarketDataSnapshotClient omdsc = new OnlineMarketDataSnapshotClient(config, 1000, true);
        omdsc.login();
        while (true) {
            try {
                omdsc.read();
            } catch (Exception e) {
                System.out.println("Reached Snapshot EOF");
                break;
            }
        }
        return omdsc.getEncryptionUtils();
    }

}
