package pl.gpw.wats.client.md;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import pl.gpw.wats.client.Config;
import pl.gpw.wats.client.md.bendec.Message;
import pl.gpw.wats.client.replay.bendec.ReplayHeader;
import pl.gpw.wats.client.replay.bendec.ReplayMsgType;
import pl.gpw.wats.client.replay.bendec.ReplayRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.gpw.wats.client.TestUtils.getEncryptionUtils;

public class OnlineMarketDataReplayClientTest {

    /**
     * Request a replay of few OMD messages
     */
    @Test
    public void requestOmdReplay() throws Exception {
        ///request_omd_replay
        AtomicInteger waitForMessages = new AtomicInteger(0);
        MdReplayConnectionConfig conf = Config.createMDReplayConfig("request_omd_replay.services.market_data.replay");
        OnlineMarketDataReplayClient omdrc = new OnlineMarketDataReplayClient(conf, getEncryptionUtils(), 1000, true);
        omdrc.addGeneralHandler(x -> {
            waitForMessages.incrementAndGet();
            System.out.println("Replay connector received: " + Message.getMsgType((byte[]) x)
                    + " " + Message.createObject(Message.getMsgType((byte[]) x), (byte[]) x).get());
        });

        ReplayRequest request = new ReplayRequest();
        request.setHeader(new ReplayHeader(ReplayRequest.byteLength, ReplayMsgType.REPLAYREQUEST));
        request.setSeqNum(2);
        request.setEndSeqNum(8);
        omdrc.send(request);
        while (waitForMessages.get() < 8) {
            if (!omdrc.read()) {
                System.out.println("EOF");
                break;
            }
        }
        assertEquals(7, waitForMessages.get());
    }

}
