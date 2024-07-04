package pl.gpw.wats.client.md;

import org.junit.jupiter.api.Test;

import pl.gpw.wats.client.Config;
import pl.gpw.wats.client.md.bendec.*;
import pl.gpw.wats.client.md.bendec.MsgType;
import pl.gpw.wats.client.md.bendec.OrderAdd;
import pl.gpw.wats.client.tp.ConnectionConfig;
import pl.gpw.wats.client.tp.TradingPortClient;
import pl.gpw.wats.client.tp.bendec.*;
import pl.gpw.wats.client.tp.bendec.OrderSide;
import pl.gpw.wats.client.tp.bendec.Trade;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.gpw.wats.client.TestUtils.*;

public class OnlineMarketDataClientTest {

    /**
     * Build order book based on Online Market Data
     */
    @Test
    public void orderBookBasedOnOmd() throws Exception {
        Long tradableProductId = Long.valueOf((Integer) Config.get("order_book_based_on_omd.product"));
        AtomicReference<pl.gpw.wats.client.tp.bendec.OrderAddResponse> tpA_buyOrderResponse = new AtomicReference<>();
        AtomicReference<pl.gpw.wats.client.tp.bendec.OrderAddResponse> tpB_sellOrderResponse = new AtomicReference<>();
        AtomicReference<pl.gpw.wats.client.tp.bendec.OrderModifyResponse> tpA_modifyBuyOrderResponse = new AtomicReference<>();
        AtomicReference<pl.gpw.wats.client.tp.bendec.OrderCancelResponse> tpA_cancelBuyOrderResponse = new AtomicReference<>();
        AtomicReference<pl.gpw.wats.client.tp.bendec.Trade> tpA_tradeResponse = new AtomicReference<>();
        AtomicReference<pl.gpw.wats.client.tp.bendec.Trade> tpB_tradeResponse = new AtomicReference<>();
        AtomicReference<pl.gpw.wats.client.md.bendec.OrderAdd> md_buyOrder = new AtomicReference<>();
        AtomicReference<pl.gpw.wats.client.md.bendec.OrderModify> md_modifyBuyOrder = new AtomicReference<>();
        AtomicReference<pl.gpw.wats.client.md.bendec.OrderDelete> md_orderDelete = new AtomicReference<>();
        AtomicReference<pl.gpw.wats.client.md.bendec.OrderExecute> md_buyOrderExecute = new AtomicReference<>();
        AtomicReference<pl.gpw.wats.client.md.bendec.OrderExecute> md_sellOrderExecute = new AtomicReference<>();
        AtomicBoolean waitForHeartbeatA = new AtomicBoolean(true);
        AtomicBoolean waitForHeartbeatB = new AtomicBoolean(true);
        AtomicBoolean waitForLogoutResponseA = new AtomicBoolean(true);
        AtomicBoolean waitForLogoutResponseB = new AtomicBoolean(true);

        // Prepare Market Data Connector
        MdConnectionConfig connectionConfig = Config.createMDConnectionConfig("order_book_based_on_omd.services.market_data.stream");
        OnlineMarketDataClient omdc = new OnlineMarketDataClient(connectionConfig, getEncryptionUtils(), 16777216, 8500);
        omdc.addHandler(MsgType.ORDERADD, x -> md_buyOrder.set(new OrderAdd((byte[]) x)));
        omdc.addHandler(MsgType.ORDERMODIFY, x -> md_modifyBuyOrder.set(new pl.gpw.wats.client.md.bendec.OrderModify((byte[]) x)));
        omdc.addHandler(MsgType.ORDERDELETE, x -> md_orderDelete.set(new OrderDelete((byte[]) x)));
        omdc.addHandler(MsgType.ORDEREXECUTE, x -> {
            OrderExecute oe = new OrderExecute((byte[]) x);
            if (md_buyOrderExecute.get() == null) {
                md_buyOrderExecute.set(oe);
            } else {
                md_sellOrderExecute.set(oe);
            }
        });

        // Prepare first Trading Port connection
        ConnectionConfig configA = Config.createConnectionConfig(
                "order_book_based_on_omd.services.trading_port",
                "order_book_based_on_omd.connection_0.connections.trading_port");
        TradingPortClient tpcA = new TradingPortClient(configA, 10000, true);
        tpcA.addHandler(pl.gpw.wats.client.tp.bendec.MsgType.HEARTBEAT,
                x -> waitForHeartbeatA.set(false));
        tpcA.addHandler(pl.gpw.wats.client.tp.bendec.MsgType.ORDERADDRESPONSE,
                x -> tpA_buyOrderResponse.set(new OrderAddResponse((byte[]) x)));
        tpcA.addHandler(pl.gpw.wats.client.tp.bendec.MsgType.ORDERMODIFYRESPONSE,
                x -> tpA_modifyBuyOrderResponse.set(new OrderModifyResponse((byte[]) x)));
        tpcA.addHandler(pl.gpw.wats.client.tp.bendec.MsgType.ORDERCANCELRESPONSE,
                x -> tpA_cancelBuyOrderResponse.set(new OrderCancelResponse((byte[]) x)));
        tpcA.addHandler(pl.gpw.wats.client.tp.bendec.MsgType.TRADE,
                x -> tpA_tradeResponse.set(new Trade((byte[]) x)));
        tpcA.addHandler(pl.gpw.wats.client.tp.bendec.MsgType.LOGOUTRESPONSE,
                x -> waitForLogoutResponseA.set(false));
        tpcA.login();

        // Prepare second Trading Port connection
        ConnectionConfig configB = Config.createConnectionConfig(
                "order_book_based_on_omd.services.trading_port",
                "order_book_based_on_omd.connection_1.connections.trading_port");
        TradingPortClient tpcB = new TradingPortClient(configB, 10000, true);
        tpcB.addHandler(pl.gpw.wats.client.tp.bendec.MsgType.HEARTBEAT,
                x -> waitForHeartbeatB.set(false));
        tpcB.addHandler(pl.gpw.wats.client.tp.bendec.MsgType.ORDERADDRESPONSE,
                x -> tpB_sellOrderResponse.set(new OrderAddResponse((byte[]) x)));
        tpcB.addHandler(pl.gpw.wats.client.tp.bendec.MsgType.TRADE,
                x -> tpB_tradeResponse.set(new Trade((byte[]) x)));
        tpcB.addHandler(pl.gpw.wats.client.tp.bendec.MsgType.LOGOUTRESPONSE,
                x -> waitForLogoutResponseB.set(false));
        tpcB.login();

        while (!tpcA.getConnectionStatus().isLogged() && !tpcB.getConnectionStatus().isLogged()) {
            tpcA.read();
            tpcB.read();
        }

        // Skip other messages that contaminate test
        while (waitForHeartbeatA.get()) {
            tpcA.read();
        }

        while (waitForHeartbeatB.get()) {
            tpcB.read();
        }

        // Clear so that previous OrderResponse(s) do not interfere
        tpA_buyOrderResponse.set(null);

        // Send Buy order to WATS with price=100
        tpcA.send(orderAdd(tradableProductId, OrderSide.BUY, 100 * 100_000_000L, BigInteger.valueOf(5000)));
        while (tpA_buyOrderResponse.get() == null) {
            tpcA.read();
        }
        assertEquals(OrderStatus.NEW, tpA_buyOrderResponse.get().getStatus(),
                "Test failed: " + tpA_buyOrderResponse.get().getReason());

        md_buyOrder.set(null);

        // Get last OrderAdd message from Online Market Data
        while (md_buyOrder.get() == null) {
            omdc.receive();
            omdc.read();
        }

        // Assert that OrderAdd pulled from OMD matches the one sent to binary trading port
        assertEquals(tpA_buyOrderResponse.get().getPublicOrderId(), md_buyOrder.get().getPublicOrderId());
        assertEquals(md_buyOrder.get().getInstrumentId(), tradableProductId);
        assertEquals(pl.gpw.wats.client.md.bendec.OrderSide.BUY, md_buyOrder.get().getSide());
        assertEquals(100 * 100_000_000L, md_buyOrder.get().getPrice());
        assertEquals(BigInteger.valueOf(5000), md_buyOrder.get().getQuantity());

        tpA_modifyBuyOrderResponse.set(null);

        // Submit OrderModify for previous OrderAdd
        tpcA.send(orderModify(tpA_buyOrderResponse.get().getOrderId(), 100 * 100_000_000L, BigInteger.valueOf(13000)));
        while (tpA_modifyBuyOrderResponse.get() == null) {
            tpcA.read();
        }

        // Get last OrderModify message from Online Market Data
        while (md_modifyBuyOrder.get() == null) {
            omdc.receive();
            omdc.read();
        }

        // Assert that OrderModify pulled from OMD matches OrderAdd and OrderModify sent to binary trading port
        assertEquals(tpA_buyOrderResponse.get().getOrderId(), tpA_modifyBuyOrderResponse.get().getOrderId());
        assertEquals(100 * 100_000_000L, md_buyOrder.get().getPrice());
        assertEquals(BigInteger.valueOf(13000), md_modifyBuyOrder.get().getQuantity());

        tpB_sellOrderResponse.set(null);

        // Send Sell order that partially matches previous OrderAdd (Buy)
        tpcB.send(orderAdd(tradableProductId, OrderSide.SELL, 100 * 100_000_000L, BigInteger.valueOf(5000)));
        while (tpB_sellOrderResponse.get() == null) {
            tpcB.read();
        }
        assertEquals(tpB_sellOrderResponse.get().getStatus(), OrderStatus.FILLED);

        tpA_tradeResponse.set(null);

        // Get Trade for Client A
        while (tpA_tradeResponse.get() == null) {
            tpcA.read();
        }
        assertEquals(tpA_buyOrderResponse.get().getOrderId(), tpA_tradeResponse.get().getOrderId());
        assertEquals(100 * 100_000_000L, tpA_tradeResponse.get().getPrice());
        assertEquals(BigInteger.valueOf(5000), tpA_tradeResponse.get().getQuantity());
        assertEquals(BigInteger.valueOf(8000), tpA_tradeResponse.get().getLeavesQty());

        tpB_tradeResponse.set(null);

        // Get Trade for Client B
        while (tpB_tradeResponse.get() == null) {
            tpcB.read();
        }
        assertEquals(tpB_sellOrderResponse.get().getOrderId(), tpB_tradeResponse.get().getOrderId());
        assertEquals(100 * 100_000_000L, tpB_tradeResponse.get().getPrice());
        assertEquals(BigInteger.valueOf(5000), tpB_tradeResponse.get().getQuantity());
        assertEquals(BigInteger.ZERO, tpB_tradeResponse.get().getLeavesQty());

        // Pull last OrderExecute messages from Online Market Data
        while (md_buyOrderExecute.get() == null || md_sellOrderExecute.get() == null) {
            omdc.receive();
            omdc.read();
        }
        assertEquals(md_buyOrderExecute.get().getPublicOrderId(), tpA_buyOrderResponse.get().getPublicOrderId());
        assertEquals(md_sellOrderExecute.get().getPublicOrderId(), tpB_sellOrderResponse.get().getPublicOrderId());

        // Cancel remaining OrderAdd
        tpcA.send(orderCancel(tpA_buyOrderResponse.get().getOrderId()));

        tpA_cancelBuyOrderResponse.set(null);

        while (tpA_cancelBuyOrderResponse.get() == null) {
            tpcA.read();
        }
        assertEquals(OrderRejectionReason.NA, tpA_cancelBuyOrderResponse.get().getReason());
        assertEquals(tpA_buyOrderResponse.get().getOrderId(), tpA_cancelBuyOrderResponse.get().getOrderId());

        // Get OrderDelete from OMD after cancelling order
        while (md_orderDelete.get() == null) {
            omdc.receive();
            omdc.read();
        }
        assertEquals(tpA_buyOrderResponse.get().getPublicOrderId(), md_orderDelete.get().getPublicOrderId());

        tpcA.logout();
        tpcB.logout();

        // Wait for LogoutResponse
        while (waitForLogoutResponseA.get() || waitForLogoutResponseB.get()) {
            tpcA.read();
            tpcB.read();
        }

        tpcA.close();
        tpcB.close();
    }

}
