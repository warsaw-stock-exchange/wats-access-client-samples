package pl.gpw.wats.client.md;

import java.util.Objects;

public final class MdReplayConnectionConfig {
    private final String hostname;
    private final Integer port;

    public MdReplayConnectionConfig(String hostname, Integer port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String hostname() {
        return hostname;
    }

    public Integer port() {
        return port;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        MdReplayConnectionConfig that = (MdReplayConnectionConfig) obj;
        return Objects.equals(this.hostname, that.hostname) &&
                Objects.equals(this.port, that.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostname, port);
    }

    @Override
    public String toString() {
        return "MdReplayConnectionConfig[" +
                "hostname=" + hostname + ", " +
                "port=" + port + ']';
    }


}
