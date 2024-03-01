package pl.gpw.wats.client.replay.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import pl.gpw.wats.client.replay.bendec.ReplayMsgType;

import java.util.Optional;

public interface ReplayMessage {
    default ReplayMsgType getReplayMsgType() {
        return this.getHeader().getReplayMsgType();
    }
    ReplayHeader getHeader();

    static ReplayMsgType getReplayMsgType(byte[] bytes) {
        return ReplayMsgType.getReplayMsgType(bytes, 2);
    }

    static Optional<Object> createObject(ReplayMsgType type, byte[] bytes){
        switch (type) {

            case REPLAYREQUEST:
                return Optional.of(new ReplayRequest(bytes));

            default:
                return Optional.empty();
        }
    }

}