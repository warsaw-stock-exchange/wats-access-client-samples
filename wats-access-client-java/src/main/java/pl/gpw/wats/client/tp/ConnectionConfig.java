package pl.gpw.wats.client.tp;

import java.util.Objects;

public final class ConnectionConfig {
    private final String hostname;
    private final Integer port;
    private final Integer connectionId;
    private final String token;
    private final Integer version;

    /**
     * Basic connection configuration
     *
     * @param hostname     Trading Port hostname
     * @param port         Trading Port port
     * @param connectionId ID of the connection
     * @param token        The security data required for authentication. It's a key received by the exchange member in a registration process (aka securityData).
     * @param version      Indicates the version of the BIN protocol in which the message is defined.
     */
    public ConnectionConfig(String hostname, Integer port, Integer connectionId, String token, Integer version) {
        this.hostname = hostname;
        this.port = port;
        this.connectionId = connectionId;
        this.token = token;
        this.version = version;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        ConnectionConfig that = (ConnectionConfig) obj;
        return Objects.equals(this.hostname, that.hostname) &&
                Objects.equals(this.port, that.port) &&
                Objects.equals(this.connectionId, that.connectionId) &&
                Objects.equals(this.token, that.token) &&
                Objects.equals(this.version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostname, port, connectionId, token, version);
    }

    @Override
    public String toString() {
        return "ConnectionConfig[" +
                "hostname=" + hostname + ", " +
                "port=" + port + ", " +
                "connectionId=" + connectionId + ", " +
                "token=" + token + ", " +
                "version=" + version + ']';
    }

}
