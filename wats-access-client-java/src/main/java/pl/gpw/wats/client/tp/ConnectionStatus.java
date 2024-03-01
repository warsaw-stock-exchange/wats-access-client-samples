package pl.gpw.wats.client.tp;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ConnectionStatus {
    private final AtomicInteger sessionId = new AtomicInteger();
    private final AtomicBoolean logged = new AtomicBoolean(false);
    private final AtomicLong lastReplaySeqNum = new AtomicLong(0);
    private final AtomicLong nextExpectedSeqNum = new AtomicLong(1);

    public int getSessionId() {
        return sessionId.get();
    }
    public void setSessionId(int sessionId) {
        this.sessionId.set(sessionId);
    }

    public boolean isLogged() {
        return logged.get();
    }
    public void setLogged(boolean logged) {
        this.logged.set(logged);
    }

    public long getLastReplaySeqNum() {
        return lastReplaySeqNum.get();
    }
    public void setLastReplaySeqNum(long num) {
        lastReplaySeqNum.set(num);
    }

    public long getNextExpectedSeqNum() {
        return nextExpectedSeqNum.get();
    }
    public long getNextExpectedSeqNumAndIncrement() {
        return nextExpectedSeqNum.getAndIncrement();
    }
    public void setNextExpectedSeqNum(long num) {
        this.nextExpectedSeqNum.set(num);
    }

    public boolean updateNextExpectedSentSeqNumIfNeeded(long newValue) {
        return nextExpectedSeqNum.updateAndGet(x -> x < newValue ? newValue : x) == newValue;
    }
    public boolean updateLastReplaySeqNum(long newValue) {
        return nextExpectedSeqNum.updateAndGet(x -> x < newValue ? newValue : x) == newValue;
    }

    @Override
    public String toString() {
        return "ConnectionStatus{" +
                "sessionId=" + sessionId +
                ", logged=" + logged +
                ", lastReplaySeqNum=" + lastReplaySeqNum +
                ", nextExpectedSeqNum=" + nextExpectedSeqNum +
                '}';
    }
}
