package pl.gpw.wats.client.md;

import org.junit.jupiter.api.Test;
import pl.gpw.wats.client.Config;
import pl.gpw.wats.client.md.bendec.*;
import pl.gpw.wats.client.md.bendec.MsgType;
import pl.gpw.wats.client.tp.bendec.OrderAdd;
import pl.gpw.wats.client.tp.bendec.OrderSide;

import java.math.BigInteger;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static pl.gpw.wats.client.TestUtils.orderAdd;

public class OnlineMarketDataSnapshotClientTest {

    /**
     * Acquire information about TickTables form Market Data Snapshot service.
     * Create a limit buy order for TradableProductId with a price that violates tickTableEntryTickSize.
     */
    @Test
    public void priceViolatesTickTable() throws Exception {
        AtomicBoolean readData = new AtomicBoolean(true);
        Long tradableProductId = Long.valueOf((Integer) Config.get("price_violates_tick_table.product"));
        Map<Long, ArrayList<Long[]>> tickTables = new HashMap<>();
        Map<Long, Long> instruments = new HashMap<>();

        MdSnapshotConnectionConfig config = Config.createMdSnapshotConfig(
                "price_violates_tick_table.services.market_data.snapshot",
                "price_violates_tick_table.connections.market_data");
        OnlineMarketDataSnapshotClient omdsc = new OnlineMarketDataSnapshotClient(config, 1000, true);

        omdsc.addHandler(MsgType.INSTRUMENT, x -> {
            Instrument product = new Instrument((byte[]) x);
            instruments.put(product.getInstrumentId(), product.getTickTableId());
        });

        omdsc.addHandler(MsgType.TICKTABLEENTRY, x -> {
            TickTableEntry entry = new TickTableEntry((byte[]) x);
            tickTables.putIfAbsent(entry.getTickTableId(), new ArrayList<>());
            tickTables.get(entry.getTickTableId()).add(new Long[]{entry.getTickSize(), entry.getLowerBound()});
        });

        omdsc.login();
        while (readData.get()) {
            try {
                omdsc.read();
            } catch (ClosedChannelException e) {
                System.out.println("Reached EOF");
                readData.set(false);
            }
        }

        OrderAdd order = orderAdd(tradableProductId, OrderSide.BUY, 100 * 100_000_001L, BigInteger.valueOf(1000L));
        Long tickTableId = instruments.get(order.getInstrumentId());
        ArrayList<Long[]> tickTable = tickTables.get(tickTableId);

        int idx = -1;
        for (int i = 0; i < tickTable.size(); i++) {
            if (tickTable.get(i)[0] <= order.getPrice())
                idx = i;
        }
        if (idx == -1) {
            fail("Tick Table entry not found.");
        } else {
            Long tickSize = tickTable.get(idx)[0];
            Long mod = order.getPrice() % tickSize;
            assertNotEquals(0, mod, "Test failed - price: " + order.getPrice() + ", tick size: " + tickSize);
        }
    }

}
