package pl.gpw.wats.client.replay.bendec;

import java.math.BigInteger;
import java.util.*;
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
    
    static Optional<ReplayMessage> createObject(byte[] bytes) {
        return createObject(getReplayMsgType(bytes), bytes);
    }
    
    static Optional<ReplayMessage> createObject(ReplayMsgType type, byte[] bytes) {
        switch (type) {
            case REPLAYREQUEST:
            return Optional.of(new ReplayRequest(bytes));
            default:
                return Optional.empty();
        }
    }
    
    static Class findClassByDiscriminator(ReplayMsgType type) {
        return  typeToClassMap.get(type);
    }
    
    static ReplayMsgType findDiscriminatorByClass(Class clazz) {
        return  classToTypeMap.get(clazz);
    }
    
    HashMap<Class, ReplayMsgType> classToTypeMap = new HashMap<>(){{
        put(ReplayRequest.class, ReplayMsgType.REPLAYREQUEST);
    }};
    
    HashMap<ReplayMsgType, Class> typeToClassMap = new HashMap<>() {{
        put(ReplayMsgType.REPLAYREQUEST, ReplayRequest.class);
    }};
}