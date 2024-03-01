package pl.gpw.wats.client.md;

import java.util.Objects;

public final class MdConnectionConfig {
    private final String interfaceName;
    private final String multicastGroupIp;
    private final Integer port;

    public MdConnectionConfig(String interfaceName, String multicastGroupIp, Integer port) {
        this.interfaceName = interfaceName;
        this.multicastGroupIp = multicastGroupIp;
        this.port = port;
    }

    public String interfaceName() {
        return interfaceName;
    }

    public String multicastGroupIp() {
        return multicastGroupIp;
    }

    public Integer port() {
        return port;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        MdConnectionConfig that = (MdConnectionConfig) obj;
        return Objects.equals(this.interfaceName, that.interfaceName) &&
                Objects.equals(this.multicastGroupIp, that.multicastGroupIp) &&
                Objects.equals(this.port, that.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interfaceName, multicastGroupIp, port);
    }

    @Override
    public String toString() {
        return "MdConnectionConfig[" +
                "interfaceName=" + interfaceName + ", " +
                "multicastGroupIp=" + multicastGroupIp + ", " +
                "port=" + port + ']';
    }

}