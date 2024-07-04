package pl.gpw.wats.client;

import pl.gpw.wats.client.md.MdSnapshotConnectionConfig;
import pl.gpw.wats.client.md.OnlineMarketDataSnapshotClient;
import pl.gpw.wats.client.tp.bendec.*;
import pl.gpw.wats.client.tp.bendec.ExecInst.ExecInstOptions;
import pl.gpw.wats.client.tp.bendec.MifidFlags.MifidFlagsOptions;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestUtils {

    public static OrderAdd orderAdd(Long instrumentId, OrderSide side, Long price, BigInteger quantity) {
        return new OrderAdd(
                new Header(
                    OrderAdd.byteLength,
                    MsgType.ORDERADD,
                    0,
                    BigInteger.ZERO
                ),
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
                new MifidFields(
                    new MifidFlags(MifidFlagsOptions.NONE.getOptionValue()),
                    new MifidField(1, PartyRoleQualifier.NA),
                    new MifidField(4, PartyRoleQualifier.ALGORITHM),
                    new MifidField(17, PartyRoleQualifier.NATURALPERSON)
                ),
                BigInteger.TEN,
                "",
                "",
                ClearingIdentifier.NOTAPPLICABLE,
                new ExecInst(ExecInstOptions.CANCELONCONNECTIONLOSS.getOptionValue()),
                0
            );
    }

    public static OrderModify orderModify(BigInteger orderId, Long price, BigInteger quantity) {
        return new OrderModify(
                new Header(
                    OrderModify.byteLength,
                    MsgType.ORDERMODIFY,
                    0,
                    BigInteger.ZERO
                ),
                orderId,
                price,
                0,
                quantity,
                BigInteger.ZERO,
                BigInteger.ZERO,
                new MifidFields(
                    new MifidFlags(MifidFlagsOptions.NONE.getOptionValue()),
                    new MifidField(1, PartyRoleQualifier.NA),
                    new MifidField(4, PartyRoleQualifier.ALGORITHM),
                    new MifidField(17, PartyRoleQualifier.NATURALPERSON)
                )
            );
    }

    public static OrderCancel orderCancel(BigInteger orderId) {
        return new OrderCancel(
                new Header(
                    OrderCancel.byteLength,
                    MsgType.ORDERCANCEL,
                    0,
                    BigInteger.ZERO
                ),
                orderId,
                new MifidFields(
                    new MifidFlags(MifidFlagsOptions.NONE.getOptionValue()),
                    new MifidField(1, PartyRoleQualifier.NA),
                    new MifidField(4, PartyRoleQualifier.ALGORITHM),
                    new MifidField(17, PartyRoleQualifier.NATURALPERSON)
                ));
    }

    public static TradeCaptureReportDual tradeCaptureReportDual(
            Long instrumentId,
            String tradeReportId,
            ExecType execType,
            Long lastPx,
            BigInteger lastQty,
            Long settlement_date) {
        return new TradeCaptureReportDual(
                new Header(
                   TradeCaptureReportDual.byteLength,
                   MsgType.TRADECAPTUREREPORTDUAL,
            0,
                   BigInteger.ZERO
                ),
                instrumentId,
                tradeReportId,
                0,
                TradeReportTransType.NEW,
                TradeReportType.SUBMIT,
                TradeType.BLOCKTRADE,
                AlgorithmicTradeIndicator.NONALGORITHMICTRADE,
                execType,
                "",
                lastQty,
                lastPx,
                settlement_date,
                new TcrParty(
                    new MifidFields(
                        new MifidFlags(MifidFlagsOptions.NONE.getOptionValue()),
                        new MifidField(1, PartyRoleQualifier.NA),
                        new MifidField(4, PartyRoleQualifier.ALGORITHM),
                        new MifidField(17, PartyRoleQualifier.NATURALPERSON)
                    ),
                    "",
                    ClearingIdentifier.NOTAPPLICABLE,
                    "",
                    AccountType.CUSTOMER,
                    Capacity.AGENCY,
                    0,
                    0,
                    0,
                    ""
                ),
                new TcrParty(
                    new MifidFields(
                        new MifidFlags(MifidFlagsOptions.NONE.getOptionValue()),
                        new MifidField(1, PartyRoleQualifier.NA),
                        new MifidField(4, PartyRoleQualifier.ALGORITHM),
                        new MifidField(17, PartyRoleQualifier.NATURALPERSON)
                    ),
                    "",
                    ClearingIdentifier.NOTAPPLICABLE,
                    "",
                    AccountType.CUSTOMER,
                    Capacity.AGENCY,
                    0,
                    0,
                    0,
                    ""
                )
            );
    }

    public static Long toDate(LocalDate d) {
        // Returns date (YYYYMMDD) as long integer value.
        return Long.valueOf(d.format(DateTimeFormatter.BASIC_ISO_DATE));
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
