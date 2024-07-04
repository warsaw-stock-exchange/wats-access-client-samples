package pl.gpw.wats.client.tp;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import pl.gpw.wats.client.Config;
import pl.gpw.wats.client.md.MdSnapshotConnectionConfig;
import pl.gpw.wats.client.md.OnlineMarketDataSnapshotClient;
import pl.gpw.wats.client.md.bendec.TickTableEntry;
import pl.gpw.wats.client.md.bendec.Instrument;
import pl.gpw.wats.client.tp.bendec.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static pl.gpw.wats.client.TestUtils.orderAdd;
import static pl.gpw.wats.client.TestUtils.orderModify;
import static pl.gpw.wats.client.TestUtils.orderCancel;
import static pl.gpw.wats.client.TestUtils.tradeCaptureReportDual;
import static pl.gpw.wats.client.TestUtils.toDate;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class TradingPortClientTest {

    /**
     * Test basic client login to [BIN] Trading Port and logout capabilities.
     */
    @Test
    public void loginLogout() throws IOException, NoSuchFieldException {
        AtomicBoolean waitForLogoutResponse = new AtomicBoolean(true);
        AtomicBoolean testSuccess = new AtomicBoolean(false);

        // Configure connector
        ConnectionConfig config = Config.createConnectionConfig(
                "login_logout.services.trading_port",
                "login_logout.connections.trading_port");
        TradingPortClient tpc = new TradingPortClient(config, 1000, true);

        // Catch login response and send logout request
        tpc.addHandler(MsgType.LOGINRESPONSE, x -> {
            try {
                LoginResponse response = new LoginResponse((byte[]) x);
                assertEquals(LoginResult.OK, response.getResult());
                System.out.println("Received: " + response);
                tpc.logout();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Catch logout reponse
        tpc.addHandler(MsgType.LOGOUTRESPONSE, x -> {
            System.out.println("Received: " + new LogoutResponse((byte[]) x));
            // We've got logoutResponse message, tell test result is a success!
            waitForLogoutResponse.set(false);
            testSuccess.set(true);
        });

        // Login message is sent to Trading Port here
        tpc.login();

        // Wait for LogoutResponse
        while (waitForLogoutResponse.get()) {
            tpc.read();
        }

        tpc.close();
        assertTrue(testSuccess.get());
    }

    /**
     * Put a limit buy order and wait for an ACK response from WATS and then put a
     * matching sell order.
     * Assert that a Trade response has expected value.
     */
    @Test
    public void limitOrder() throws IOException, NoSuchFieldException {
        Long instrumentId = Long.valueOf((Integer) Config.get("limit_order.product"));
        AtomicBoolean waitForTradeA = new AtomicBoolean(true);
        AtomicBoolean waitForTradeB = new AtomicBoolean(true);
        AtomicBoolean waitForLogoutResponseA = new AtomicBoolean(true);
        AtomicBoolean waitForLogoutResponseB = new AtomicBoolean(true);

        // Configure connector A and B
        ConnectionConfig configA = Config.createConnectionConfig(
                "limit_order.connection_0.services.trading_port",
                "limit_order.connection_0.connections.trading_port");
        TradingPortClient tpcA = new TradingPortClient(configA, 10000, true);
        tpcA.addGeneralHandler(x -> {
            System.out.println(
                    "Connector A received: " + Message.createObject((byte[]) x).get());
        });

        ConnectionConfig configB = Config.createConnectionConfig(
                "limit_order.connection_1.services.trading_port",
                "limit_order.connection_1.connections.trading_port");
        TradingPortClient tpcB = new TradingPortClient(configB, 10000, true);
        tpcB.addGeneralHandler(x -> {
            System.out.println(
                    "Connector B received: " + Message.createObject((byte[]) x).get());
        });

        // Catch Trade message
        tpcA.addHandler(MsgType.TRADE, x -> waitForTradeA.set(false));
        tpcB.addHandler(MsgType.TRADE, x -> waitForTradeB.set(false));

        // Catch LogoutResponse
        tpcA.addHandler(MsgType.LOGOUTRESPONSE, x -> waitForLogoutResponseA.set(false));
        tpcB.addHandler(MsgType.LOGOUTRESPONSE, x -> waitForLogoutResponseB.set(false));

        tpcA.login();
        tpcB.login();
        while (!tpcA.getConnectionStatus().isLogged() && !tpcB.getConnectionStatus().isLogged()) {
            tpcA.read();
            tpcB.read();
        }
        ignoreMessagesFromPreviousTests(tpcA);
        ignoreMessagesFromPreviousTests(tpcB);

        // Send Buy order to WATS - A
        OrderAdd order = orderAdd(instrumentId, OrderSide.BUY, 100L * 100_000_000L, BigInteger.valueOf(1000L));
        System.out.println("Sending order: " + order);
        tpcA.send(order);

        // Send Buy order to WATS - B
        order = orderAdd(instrumentId, OrderSide.SELL, 100L * 100_000_000L, BigInteger.valueOf(1000L));
        System.out.println("Sending order: " + order);
        tpcB.send(order);

        // Wait for trade
        while (waitForTradeA.get() || waitForTradeB.get()) {
            tpcA.read();
            tpcB.read();
        }
        tpcA.logout();
        tpcB.logout();

        // Wait for LogoutResponse
        while (waitForLogoutResponseA.get() || waitForLogoutResponseB.get()) {
            tpcA.read();
            tpcB.read();
        }

        tpcA.close();
        tpcB.close();
        assertFalse(waitForTradeA.get());
        assertFalse(waitForTradeB.get());
    }

    /**
     * Put a limit buy order for TradableProduct. Then immediately send OrderModify,
     * without waiting for an ACK response from WATS. Assert that OrderModify was
     * sent for the right OrderAdd.
     *
     * The important bit here is prior knowledge of order_ref_id that must be
     * calculated on the client's side without waiting for a WATS response.
     * The order_ref_id is derived from: `connection_id`, `session_id` and `seq_num`
     * concatenated together bitwise, that is:
     *
     * <connection_id> <session_id> <seq_num>
     * <63-48> <47-32> <31-0>
     *
     * `Client` keeps track of `connection_id`, `session_id` and `seq_num`, thus
     * it's possible to calculate
     * order_ref_id on the client's side. With the knowledge of `seq_num` of
     * OrderAdd, it's possible to send OrderModify
     * for any matching message.
     */
    @Test
    public void modifyInflight() throws IOException, NoSuchFieldException {
        Long instrumentId = Long.valueOf((Integer) Config.get("modify_inflight.product"));
        AtomicBoolean waitForAck = new AtomicBoolean(true);
        AtomicBoolean waitForModifyAck = new AtomicBoolean(true);
        AtomicInteger stage = new AtomicInteger(0);
        AtomicBoolean waitForLogoutResponse = new AtomicBoolean(true);
        AtomicReference<OrderAddResponse> orderAddResponse = new AtomicReference<>();
        AtomicReference<OrderModifyResponse> orderModifyResponse = new AtomicReference<>();

        ConnectionConfig config = Config.createConnectionConfig(
                "modify_inflight.services.trading_port",
                "modify_inflight.connections.trading_port");
        TradingPortClient tpc = new TradingPortClient(config, 10000, true);
        tpc.login();
        while (!tpc.getConnectionStatus().isLogged()) {
            tpc.read();
        }
        ignoreMessagesFromPreviousTests(tpc);

        tpc.addGeneralHandler(x -> {
            System.out.println(
                    "Connector received: " + Message.createObject((byte[]) x).get());
        });
        tpc.addHandler(MsgType.ORDERADDRESPONSE, x -> {
            orderAddResponse.set(new OrderAddResponse((byte[]) x));
            waitForAck.set(false);
        });
        tpc.addHandler(MsgType.ORDERMODIFYRESPONSE, x -> {
            orderModifyResponse.set(new OrderModifyResponse((byte[]) x));
            waitForModifyAck.set(false);
            switch(stage.incrementAndGet()) {
                case 1:
                    assertEquals(OrderStatus.NEW, orderModifyResponse.get().getStatus());
                    break;
                case 2:
                    assertEquals(OrderStatus.NEW, orderModifyResponse.get().getStatus());
                    break;
                case 3:
                    assertEquals(OrderStatus.REJECTED, orderModifyResponse.get().getStatus());
                    break;
                case 4:
                    assertEquals(OrderStatus.NEW, orderModifyResponse.get().getStatus());
                    break;
                case 5:
                    assertEquals(OrderStatus.REJECTED, orderModifyResponse.get().getStatus());
                    break;
                case 6:
                    assertEquals(OrderStatus.NEW, orderModifyResponse.get().getStatus());
                    break;
                default: break;
            };
        });

        // Catch LogoutResponse
        tpc.addHandler(MsgType.LOGOUTRESPONSE, x -> waitForLogoutResponse.set(false));

        // Send Buy order to WATS
        OrderAdd order_buy = orderAdd(
            instrumentId,
            OrderSide.BUY,
            100L * 100_000_000L,
            BigInteger.valueOf(1000L)
        );
        System.out.println("Sending order: " + order_buy);
        waitForAck.set(true);
        tpc.send(order_buy);

        // Calculate oderRefId
        BigInteger orderRefId = calcuateRefId(config.connectionId(),
                tpc.getConnectionStatus().getSessionId(),
                tpc.getConnectionStatus().getNextExpectedSeqNum() - 1);

        while (waitForAck.get()) {
            tpc.read();
        }

        // ModifyOrder #1
        OrderModify modify1 = orderModify(
            orderRefId,
            100L * 100_000_000L,
            BigInteger.valueOf(2000L)
        );
        System.out.println("Sending order-modify #1: " + modify1);
        waitForModifyAck.set(true);
        tpc.send(modify1);

        while (waitForModifyAck.get()) {
            tpc.read();
        }

        // ModifyOrder #2
        OrderModify modify2 = orderModify(
            orderRefId,
            101L * 100_000_000L,
            BigInteger.valueOf(2000L)
        );
        System.out.println("Sending order-modify #2: " + modify2);
        waitForModifyAck.set(true);
        tpc.send(modify2);

        while (waitForModifyAck.get()) {
            tpc.read();
        }

        // ModifyOrder #3
        OrderModify modify3 = orderModify(
            orderRefId,
            10001L * 100_000_000L,
            BigInteger.valueOf(2000000000L)
        );
        System.out.println("Sending order-modify #3: " + modify3);
        waitForModifyAck.set(true);
        tpc.send(modify3);

        while (waitForModifyAck.get()) {
            tpc.read();
        }

        // ModifyOrder #4
        OrderModify modify4 = orderModify(
            orderRefId,
            100L * 100_000_000L,
            BigInteger.valueOf(1500L)
        );
        System.out.println("Sending order-modify #4: " + modify4);
        waitForModifyAck.set(true);
        tpc.send(modify4);

        while (waitForModifyAck.get()) {
            tpc.read();
        }

        // ModifyOrder #5
        OrderModify modify5 = orderModify(
            orderRefId,
            100L * 100_000_000L,
            BigInteger.valueOf(1500L)
        );
        modify5.setExpire(BigInteger.valueOf(toDate(LocalDate.now().plusDays(-1))));
        System.out.println("Sending order-modify #5: " + modify5);
        waitForModifyAck.set(true);
        tpc.send(modify5);

        while (waitForModifyAck.get()) {
            tpc.read();
        }

        // ModifyOrder #6
        OrderModify modify6 = orderModify(
            orderRefId,
            100L * 100_000_000L,
            BigInteger.valueOf(3000L)
        );
        System.out.println("Sending order-modify #6: " + modify6);
        waitForModifyAck.set(true);
        tpc.send(modify6);

        while (waitForModifyAck.get()) {
            tpc.read();
        }

        // Send Sell order to clear stock
        OrderAdd order_sell = orderAdd(
            instrumentId,
            OrderSide.SELL,
            100L * 100_000_000L,
            BigInteger.valueOf(3000L)
        );
        System.out.println("Sending order: " + order_sell);
        waitForAck.set(true);
        tpc.send(order_sell);

        while (waitForAck.get()) {
            tpc.read();
        }

        tpc.logout();

        // Wait for LogoutResponse
        while (waitForLogoutResponse.get()) {
            tpc.read();
        }

        tpc.close();
    }

    /**
     * Put a limit buy order with a bad instrumentId (it does not exist). Assert
     * that WATS response
     * status is rejected and rejection reason is UnknowninstrumentId.
     */
    @Test
    public void badInstrumentId() throws IOException, NoSuchFieldException {
        Long instrumentId = Long.valueOf((Integer) Config.get("bad_tradable_product_id.product"));
        AtomicReference<OrderAddResponse> orderAddResponse = new AtomicReference<>();
        AtomicBoolean waitForLogoutResponse = new AtomicBoolean(true);

        ConnectionConfig config = Config.createConnectionConfig(
                "bad_tradable_product_id.services.trading_port",
                "bad_tradable_product_id.connections.trading_port");
        TradingPortClient tpc = new TradingPortClient(config, 10000, true);
        tpc.addGeneralHandler(x -> {
            System.out.println(
                    "Connector received: " + Message.createObject((byte[]) x).get());
        });

        tpc.login();
        while (!tpc.getConnectionStatus().isLogged()) {
            tpc.read();
        }
        ignoreMessagesFromPreviousTests(tpc);

        tpc.addHandler(MsgType.ORDERADDRESPONSE, x -> {
            orderAddResponse.set(new OrderAddResponse((byte[]) x));
        });

        // Catch LogoutResponse
        tpc.addHandler(MsgType.LOGOUTRESPONSE, x -> waitForLogoutResponse.set(false));

        // Send order to WATS
        OrderAdd order = orderAdd(instrumentId, OrderSide.BUY, 100L * 100_000_000L, BigInteger.valueOf(1000L));
        System.out.println("Sending order: " + order);
        tpc.send(order);

        while (orderAddResponse.get() == null) {
            tpc.read();
        }
        tpc.logout();

        // Wait for LogoutResponse
        while (waitForLogoutResponse.get()) {
            tpc.read();
        }

        tpc.close();

        assertEquals(OrderStatus.REJECTED, orderAddResponse.get().getStatus());
        assertEquals(OrderRejectionReason.UNKNOWNINSTRUMENT, orderAddResponse.get().getReason());
    }

    /**
     * Put a limit buy order with a bad price=100.00000001 and quantity=1000.
     * Assert that WATS response status is rejected and rejection reason is
     * InvalidPriceIncrement.
     */
    @Test
    public void badPrice() throws IOException, NoSuchFieldException {
        Long instrumentId = Long.valueOf((Integer) Config.get("bad_price.product"));
        AtomicReference<OrderAddResponse> orderAddResponse = new AtomicReference<>();
        AtomicBoolean waitForLogoutResponse = new AtomicBoolean(true);

        ConnectionConfig config = Config.createConnectionConfig(
                "bad_price.services.trading_port",
                "bad_price.connections.trading_port");
        TradingPortClient tpc = new TradingPortClient(config, 500000, true);
        tpc.addGeneralHandler(x -> {
            System.out.println(
                    "Connector received: " + Message.createObject((byte[]) x).get());
        });

        // Catch LogoutResponse
        tpc.addHandler(MsgType.LOGOUTRESPONSE, x -> waitForLogoutResponse.set(false));

        tpc.login();
        while (!tpc.getConnectionStatus().isLogged()) {
            tpc.read();
        }
        ignoreMessagesFromPreviousTests(tpc);

        tpc.addHandler(MsgType.ORDERADDRESPONSE, x -> {
            orderAddResponse.set(new OrderAddResponse((byte[]) x));
        });

        // Send order to WATS
        OrderAdd order = orderAdd(instrumentId, OrderSide.BUY, 100L * 100_000_001L, BigInteger.valueOf(1000L));
        System.out.println("Sending order: " + order);
        tpc.send(order);

        while (orderAddResponse.get() == null) {
            tpc.read();
        }

        tpc.logout();

        // Wait for LogoutResponse
        while (waitForLogoutResponse.get()) {
            tpc.read();
        }

        tpc.close();

        assertEquals(OrderStatus.REJECTED, orderAddResponse.get().getStatus());
        assertEquals(OrderRejectionReason.INVALIDPRICEINCREMENT, orderAddResponse.get().getReason());
    }

    /**
     * Put a limit buy order with a price=100.00000000 and quantity=1000.
     * Wait for an ACK for OrderAdd and then send a CancelOrder for it. Assert that
     * rejection reason in
     * OrderCancelResponse is NA and order_ref_id matches OrderAddResponse.
     */
    @Test
    public void orderCancelTest() throws IOException, NoSuchFieldException {
        Long instrumentId = Long.valueOf((Integer) Config.get("order_cancel.product"));
        AtomicBoolean waitForAck = new AtomicBoolean(true);
        AtomicBoolean waitForModifyAck = new AtomicBoolean(true);
        AtomicBoolean waitForLogoutResponse = new AtomicBoolean(true);
        AtomicReference<OrderAddResponse> orderAddResponse = new AtomicReference<>();
        AtomicReference<OrderCancelResponse> orderCancelResponse = new AtomicReference<>();

        ConnectionConfig config = Config.createConnectionConfig(
                "order_cancel.services.trading_port",
                "order_cancel.connections.trading_port");
        TradingPortClient tpc = new TradingPortClient(config, 10000, true);
        tpc.login();
        while (!tpc.getConnectionStatus().isLogged()) {
            tpc.read();
        }
        ignoreMessagesFromPreviousTests(tpc);

        tpc.addGeneralHandler(x -> {
            System.out.println(
                    "Connector received: " + Message.createObject((byte[]) x).get());
        });
        tpc.addHandler(MsgType.ORDERADDRESPONSE, x -> {
            orderAddResponse.set(new OrderAddResponse((byte[]) x));
            waitForAck.set(false);
        });
        tpc.addHandler(MsgType.ORDERCANCELRESPONSE, x -> {
            orderCancelResponse.set(new OrderCancelResponse((byte[]) x));
            waitForModifyAck.set(false);
        });

        // Catch LogoutResponse
        tpc.addHandler(MsgType.LOGOUTRESPONSE, x -> waitForLogoutResponse.set(false));

        // Send Buy order to WATS
        OrderAdd order = orderAdd(instrumentId, OrderSide.BUY, 100L * 100_000_000L, BigInteger.valueOf(1000L));
        System.out.println("Sending order: " + order);
        tpc.send(order);

        while (waitForAck.get()) {
            tpc.read();
        }

        // Send Cancel previous order to WATS
        OrderCancel cancel = orderCancel(orderAddResponse.get().getOrderId());
        System.out.println("Sending cancel: " + cancel);
        tpc.send(cancel);

        while (waitForModifyAck.get()) {
            tpc.read();
        }
        tpc.logout();

        // Wait for LogoutResponse
        while (waitForLogoutResponse.get()) {
            tpc.read();
        }

        tpc.close();

        assertEquals(OrderRejectionReason.NA, orderCancelResponse.get().getReason());
    }

    /**
     * Put a limit buy order with a price=100.00000000 and quantity=1000.
     * Wait for an ACK for OrderAdd and then send a CancelOrder with bad
     * order_ref_id. Assert that
     * rejection reason in OrderCancelResponse is UnknownOrderRefId.
     */
    @Test
    public void orderCancelBadOrderRefId() throws IOException, NoSuchFieldException {
        AtomicBoolean waitForAck = new AtomicBoolean(true);
        AtomicBoolean waitForModifyAck = new AtomicBoolean(true);
        AtomicBoolean waitForLogoutResponse = new AtomicBoolean(true);
        AtomicReference<OrderAddResponse> orderAddResponse = new AtomicReference<>();
        AtomicReference<OrderCancelResponse> orderCancelResponse = new AtomicReference<>();

        ConnectionConfig config = Config.createConnectionConfig(
                "order_cancel_bad_order_ref_id.services.trading_port",
                "order_cancel_bad_order_ref_id.connections.trading_port");
        TradingPortClient tpc = new TradingPortClient(config, 10000, true);
        tpc.login();
        while (!tpc.getConnectionStatus().isLogged()) {
            tpc.read();
        }
        ignoreMessagesFromPreviousTests(tpc);

        tpc.addGeneralHandler(x -> {
            System.out.println(
                    "Connector received: " + Message.createObject((byte[]) x).get());
        });
        tpc.addHandler(MsgType.ORDERADDRESPONSE, x -> {
            orderAddResponse.set(new OrderAddResponse((byte[]) x));
            waitForAck.set(false);
        });
        tpc.addHandler(MsgType.ORDERCANCELRESPONSE, x -> {
            orderCancelResponse.set(new OrderCancelResponse((byte[]) x));
            waitForModifyAck.set(false);
        });

        // Catch LogoutResponse
        tpc.addHandler(MsgType.LOGOUTRESPONSE, x -> waitForLogoutResponse.set(false));

        Long instrumentId = Long.valueOf((Integer) Config.get("order_cancel_bad_order_ref_id.product"));

        // Send Buy order to WATS
        OrderAdd order = orderAdd(instrumentId, OrderSide.BUY, 100L * 100_000_000L, BigInteger.valueOf(1000L));
        System.out.println("Sending order: " + order);
        tpc.send(order);

        while (waitForAck.get()) {
            tpc.read();
        }

        // Send Cancel with wrong refId to WATS
        OrderCancel cancel = orderCancel(BigInteger.ONE);
        System.out.println("Sending cancel: " + cancel);
        tpc.send(cancel);

        while (waitForModifyAck.get()) {
            tpc.read();
        }
        tpc.logout();

        // Wait for LogoutResponse
        while (waitForLogoutResponse.get()) {
            tpc.read();
        }

        tpc.close();

        assertEquals(OrderRejectionReason.UNKNOWNORDER, orderCancelResponse.get().getReason());
    }

    /**
     * Submit OrderAdds that:
     * 1. Fits PTC
     * 2. Violates PTC (volume above max volume)
     * 3. Violates PTC (value above max value)
     */
    @Test
    public void preTradeCheckVerification() throws IOException, NoSuchFieldException {
        Long instrumentId = Long.valueOf((Integer) Config.get("ptc_verification.product"));
        Map<Long, ArrayList<Long[]>> tickTables = new HashMap<>();
        Map<Long, Instrument> instruments = new HashMap<>();
        AtomicBoolean waitForLogoutResponse = new AtomicBoolean(true);

        // receive reference data (tradable product and tick tables)
        AtomicBoolean readData = new AtomicBoolean(true);
        MdSnapshotConnectionConfig configMds = Config.createMdSnapshotConfig(
                "ptc_verification.services.market_data.snapshot",
                "ptc_verification.connections.market_data");
        OnlineMarketDataSnapshotClient omdsc = new OnlineMarketDataSnapshotClient(configMds, 1000, true);
        omdsc.addHandler(pl.gpw.wats.client.md.bendec.MsgType.INSTRUMENT, x -> {
            Instrument product = new Instrument((byte[]) x);
            instruments.put(product.getInstrumentId(), product);
        });
        omdsc.addHandler(pl.gpw.wats.client.md.bendec.MsgType.TICKTABLEENTRY, x -> {
            TickTableEntry entry = new TickTableEntry((byte[]) x);
            tickTables.putIfAbsent(entry.getTickTableId(), new ArrayList<>());
            tickTables.get(entry.getTickTableId()).add(new Long[] { entry.getTickSize(), entry.getLowerBound() });
        });
        omdsc.login();
        while (readData.get()) {
            try {
                omdsc.read();
            } catch (Exception e) {
                System.out.println("Reached EOF");
                readData.set(false);
            }
        }

        // connect to trading port
        ConnectionConfig config = Config.createConnectionConfig(
                "ptc_verification.services.trading_port",
                "ptc_verification.connections.trading_port");
        TradingPortClient tpc = new TradingPortClient(config, 10000, true);
        tpc.addGeneralHandler(x -> {
            System.out.println(
                    "Connector received: " + Message.createObject((byte[]) x).get());
        });
        LinkedList<OrderAddResponse> tradeCaptureReportResponses = new LinkedList<>();
        tpc.login();
        while (!tpc.getConnectionStatus().isLogged()) {
            tpc.read();
        }
        ignoreMessagesFromPreviousTests(tpc);
        tpc.addHandler(MsgType.ORDERADDRESPONSE, x -> {
            tradeCaptureReportResponses.add(new OrderAddResponse((byte[]) x));
        });

        // Catch LogoutResponse
        tpc.addHandler(MsgType.LOGOUTRESPONSE, x -> waitForLogoutResponse.set(false));

        // send orders
        Instrument product = instruments.get(instrumentId);
        long price = 100 * 100_000_000l;
        BigInteger maxPrice = BigInteger.valueOf(product.getPreTradeCheckMaxValue())
                .divide(product.getPreTradeCheckMaxQuantity())
                .multiply(BigInteger.valueOf(100_000_000l));
        BigInteger quantity = (product.getPreTradeCheckMaxQuantity()
                .subtract(product.getPreTradeCheckMinQuantity()))
                .divide(BigInteger.TWO);

        BigInteger orderRefId_ok = sendOrderAndCalcuateRefId(config, tpc,
                orderAdd(instrumentId, OrderSide.BUY, price, BigInteger.valueOf(1000l)));
        BigInteger orderRefId_tooHighValue = sendOrderAndCalcuateRefId(config, tpc,
                orderAdd(instrumentId, OrderSide.BUY, price, quantity));
        BigInteger orderRefId_tooHighQuantity = sendOrderAndCalcuateRefId(config, tpc,
                orderAdd(instrumentId, OrderSide.BUY, price,
                        product.getPreTradeCheckMaxQuantity().add(BigInteger.ONE)));
        BigInteger orderRefId_tooHighPrice = sendOrderAndCalcuateRefId(config, tpc,
                orderAdd(instrumentId, OrderSide.BUY, maxPrice.longValue(),
                        product.getPreTradeCheckMaxQuantity()));

        // check responses
        while (tradeCaptureReportResponses.size() != 4) {
            tpc.read();
        }
        tradeCaptureReportResponses.forEach(response -> {
            if (response.getOrderId().equals(orderRefId_ok)) {
                assertEquals(OrderStatus.NEW, response.getStatus());
            } else if (response.getOrderId().equals(orderRefId_tooHighValue)) {
                assertEquals(OrderStatus.REJECTED, response.getStatus());
                assertEquals(OrderRejectionReason.ORDERVALUEMUSTBELOWERTHANMAXIMUMVALUE, response.getReason());
            } else if (response.getOrderId().equals(orderRefId_tooHighQuantity)) {
                assertEquals(OrderStatus.REJECTED, response.getStatus());
                assertEquals(OrderRejectionReason.ORDERQUANTITYMUSTBELOWERTHANMAXIMUMQUANTITY, response.getReason());
            } else if (response.getOrderId().equals(orderRefId_tooHighPrice)) {
                assertEquals(OrderStatus.REJECTED, response.getStatus());
                assertEquals(OrderRejectionReason.PRICEABOVEHIGHCOLLAR, response.getReason());
            }
        });

        tpc.logout();

        // Wait for LogoutResponse
        while (waitForLogoutResponse.get()) {
            tpc.read();
        }

        tpc.close();
    }

    /**
     * Put a series of orders
     */
    @Test
    public void ordersInPhase() throws IOException, NoSuchFieldException {
        CountDownLatch counter = new CountDownLatch(6);
        ConnectionConfig config = Config.createConnectionConfig(
                "orders_in_phase.services.trading_port",
                "orders_in_phase.connections.trading_port");
        AtomicBoolean waitForLogoutResponse = new AtomicBoolean(true);

        TradingPortClient tpc = new TradingPortClient(config, 10000, true);
        tpc.login();
        while (!tpc.getConnectionStatus().isLogged()) {
            tpc.read();
        }
        ignoreMessagesFromPreviousTests(tpc);
        tpc.addHandler(MsgType.ORDERADDRESPONSE, byteResponse -> {
            OrderAddResponse orderAddResponse = new OrderAddResponse((byte[]) byteResponse);
            assertTrue(orderAddResponse.getStatus().equals(OrderStatus.NEW) ||
                    orderAddResponse.getStatus().equals(OrderStatus.FILLED),
                    "Expected Ack or Filled but received " + orderAddResponse.getStatus() + " - "
                            + orderAddResponse.getReason());
            counter.countDown();
        });

        // Catch LogoutResponse
        tpc.addHandler(MsgType.LOGOUTRESPONSE, x -> waitForLogoutResponse.set(false));

        ArrayList<LinkedHashMap> orders = (ArrayList) Config.get("orders_in_phase.orders");
        orders.forEach(order -> {
            Long product = Long.valueOf((Integer) order.get("product"));
            OrderSide side = order.get("side").equals("Buy") ? OrderSide.BUY : OrderSide.SELL;
            Long price = Long.valueOf((Integer) order.get("price")) * 100_000_000L;
            BigInteger quantity = BigInteger.valueOf((Integer) order.get("quantity"));
            try {
                tpc.send(orderAdd(product, side, price, quantity));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        while (counter.getCount() != 0) {
            tpc.read();
        }

        tpc.logout();

        // Wait for LogoutResponse
        while (waitForLogoutResponse.get()) {
            tpc.read();
        }

        tpc.close();
    }

    @Test
    public void blockDual() throws IOException, NoSuchFieldException {
        Long instrumentId = Long.valueOf((Integer) Config.get("block_dual.product"));
        AtomicBoolean waitForLogoutResponse = new AtomicBoolean(true);
        AtomicLong responsesCount = new AtomicLong(0l);

        // connect to trading port
        ConnectionConfig config = Config.createConnectionConfig(
                "block_dual.services.trading_port",
                "block_dual.connections.trading_port");
        TradingPortClient tpc = new TradingPortClient(config, 10000, true);
        tpc.addGeneralHandler(x -> {
            System.out.println(
                    "Connector received: " + Message.createObject((byte[]) x).get());
        });
        tpc.login();
        while (!tpc.getConnectionStatus().isLogged()) {
            tpc.read();
        }

        ignoreMessagesFromPreviousTests(tpc);

        tpc.addHandler(MsgType.TRADECAPTUREREPORTRESPONSE, x -> {
            TradeCaptureReportResponse response = new TradeCaptureReportResponse((byte[]) x);
            responsesCount.set(responsesCount.get() + 1);

            // Examine responses
            if (response.getTradeReportId().equals("ID0000")) {
                assertEquals(TcrStatus.ACCEPTED, response.getStatus());
            } else if (response.getTradeReportId().equals("ID0001")) {
                assertEquals(TcrStatus.REJECTED, response.getStatus());
            } else if (response.getTradeReportId().equals("ID0002")) {
                if (responsesCount.get() == 3) {
                    assertEquals(TcrStatus.ACCEPTED, response.getStatus());
                } else if (responsesCount.get() == 4) {
                    assertEquals(TcrStatus.REJECTED, response.getStatus());
                }
            } else if (response.getTradeReportId().equals("ID0003")) {
                assertEquals(TcrStatus.ACCEPTED, response.getStatus());
            } else if (response.getTradeReportId().equals("ID0004")) {
                assertEquals(TcrStatus.REJECTED, response.getStatus());
            }
        });

        // Catch LogoutResponse
        tpc.addHandler(MsgType.LOGOUTRESPONSE, x -> waitForLogoutResponse.set(false));

        // send TradeCaptureReportDual
        tpc.send(tradeCaptureReportDual(
                instrumentId,
                "ID0000",
                ExecType.NA,
                100 * 100_000_000l,
                BigInteger.valueOf(10),
                toDate(LocalDate.now().plusDays(1)))
        );

        tpc.read();

        tpc.send(tradeCaptureReportDual(
                instrumentId,
                "ID0001",
                ExecType.TRADE,
                100 * 100_000_000l,
                BigInteger.valueOf(10),
                toDate(LocalDate.now().plusDays(1)))
        );

        tpc.read();

        tpc.send(tradeCaptureReportDual(
                instrumentId,
                "ID0002",
                ExecType.NA,
                100 * 100_000_000l,
                BigInteger.valueOf(10),
                toDate(LocalDate.now().plusDays(1)))
        );

        tpc.read();

        tpc.send(tradeCaptureReportDual(
                instrumentId,
                "ID0002",
                ExecType.NA,
                100 * 100_000_000l,
                BigInteger.valueOf(10),
                toDate(LocalDate.now().plusDays(1)))
        );

        tpc.read();

        tpc.send(tradeCaptureReportDual(
                instrumentId,
                "ID0003",
                ExecType.NA,
                100 * 100_000_000l,
                BigInteger.valueOf(1),
                toDate(LocalDate.now().plusDays(1))));

        tpc.read();

        tpc.send(tradeCaptureReportDual(
                instrumentId,
                "ID0004",
                ExecType.NA,
                100 * 100_000_000l,
                BigInteger.valueOf(10),
                toDate(LocalDate.now().plusDays(-1))));

        tpc.logout();

        // Wait for LogoutResponse
        while (waitForLogoutResponse.get()) {
            tpc.read();
        }

        tpc.close();
    }

    private BigInteger sendOrderAndCalcuateRefId(ConnectionConfig config, TradingPortClient tpc, OrderAdd order)
            throws IOException {
        System.out.println("Sending order: " + order);
        tpc.send(order);

        return calcuateRefId(config.connectionId(),
                tpc.getConnectionStatus().getSessionId(),
                tpc.getConnectionStatus().getNextExpectedSeqNum() - 1);
    }

    private BigInteger calcuateRefId(Integer connectionId, Integer sessionId, Long seqNum) throws IOException {

        BigInteger con = BigInteger.valueOf(connectionId);
        BigInteger ses = BigInteger.valueOf(sessionId);
        BigInteger seq = BigInteger.valueOf(seqNum);

        return con.shiftLeft(50).or(ses.shiftLeft(36)).or(seq.shiftLeft(0));
    }

    private void ignoreMessagesFromPreviousTests(TradingPortClient tpc) throws IOException {
        AtomicBoolean wait = new AtomicBoolean(true);
        Consumer<byte[]> countHandler = (bytes) -> {
            Header h = new Header(bytes);
            if (tpc.getConnectionStatus().getLastReplaySeqNum() <= h.getSeqNum() || h.getSeqNum() == 0)
                wait.set(false);
        };
        tpc.addGeneralHandler(countHandler);
        while (wait.get()) {
            tpc.read();
        }
        tpc.removeGeneralHandler(countHandler);
    }

}
