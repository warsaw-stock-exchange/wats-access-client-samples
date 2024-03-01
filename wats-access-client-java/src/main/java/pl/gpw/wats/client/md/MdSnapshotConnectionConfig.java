package pl.gpw.wats.client.md;

import java.util.Objects;

public final class MdSnapshotConnectionConfig {
    private final String hostname;
    private final Integer port;
    private final Integer connectionId;
    private final String token;
    private final Integer version;
    private final byte[] nonce;

    public MdSnapshotConnectionConfig(String hostname, Integer port, Integer connectionId, String token, Integer version, byte[] nonce) {
        this.hostname = hostname;
        this.port = port;
        this.connectionId = connectionId;
        this.token = token;
        this.version = version;
        this.nonce = nonce;
    }

    public String hostname() {
        return hostname;
    }

    public Integer port() {
        return port;
    }

    public Integer connectionId() {
        return connectionId;
    }

    public String token() {
        return token;
    }

    public Integer version() {
        return version;
    }

    public byte[] nonce() {
        return nonce;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        MdSnapshotConnectionConfig that = (MdSnapshotConnectionConfig) obj;
        return Objects.equals(this.hostname, that.hostname) &&
                Objects.equals(this.port, that.port) &&
                Objects.equals(this.connectionId, that.connectionId) &&
                Objects.equals(this.token, that.token) &&
                Objects.equals(this.version, that.version) &&
                Objects.equals(this.nonce, that.nonce);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostname, port, connectionId, token, version, nonce);
    }

    @Override
    public String toString() {
        return "MdSnapshotConnectionConfig[" +
                "hostname=" + hostname + ", " +
                "port=" + port + ", " +
                "connectionId=" + connectionId + ", " +
                "token=" + token + ", " +
                "version=" + version + ", " +
                "nonce=" + nonce + ']';
    }

}