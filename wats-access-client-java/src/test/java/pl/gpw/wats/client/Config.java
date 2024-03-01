package pl.gpw.wats.client;

import org.yaml.snakeyaml.Yaml;
import pl.gpw.wats.client.md.MdConnectionConfig;
import pl.gpw.wats.client.md.MdReplayConnectionConfig;
import pl.gpw.wats.client.md.MdSnapshotConnectionConfig;
import pl.gpw.wats.client.tp.ConnectionConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class Config {
    private static Map<String, Object> configFile = null;

    public static Map<String, Object> getConfig() throws FileNotFoundException {
        if (configFile == null) {
            InputStream inputStream = new FileInputStream(new File("wats.yml"));
            Yaml yaml = new Yaml();
            configFile = yaml.load(inputStream);
        }
        return configFile;
    }

    private static Object explore(String path, Map<String, Object> map) throws NoSuchFieldException {
        String[] splittedPath = path.split("\\.");
        for(int i = 0; i < splittedPath.length; i++) {
            if (i == splittedPath.length-1)
                return map.get(splittedPath[i]);
            map = (Map<String, Object>) map.get(splittedPath[i]);
            if (map == null)
                throw new NoSuchFieldException(splittedPath[i] + " not found in path " + path);
        }
        return null;
    }

    public static Object get(String path) throws FileNotFoundException, NoSuchFieldException {
        return explore(path, getConfig());
    }

    public static ConnectionConfig createConnectionConfig(String serviceSection, String connectionSection) throws FileNotFoundException, NoSuchFieldException {
        String hostname = (String) Config.get(serviceSection+".host");
        Integer port = (Integer) Config.get(serviceSection+".port");
        Integer connectionId = (Integer) Config.get(connectionSection+".connection_id");
        String token = (String) Config.get(connectionSection+".token");
        return new ConnectionConfig(hostname, port, connectionId, token, 0);
    }

    public static MdSnapshotConnectionConfig createMdSnapshotConfig(String serviceSection, String connectionSection) throws FileNotFoundException, NoSuchFieldException {
        String hostname = (String) Config.get(serviceSection+".host");
        Integer port = (Integer) Config.get(serviceSection+".port");
        Integer connectionId = (Integer) Config.get(connectionSection+".connection_id");
        String token = (String) Config.get(connectionSection+".token");
        return new MdSnapshotConnectionConfig(hostname, port, connectionId, token, 0, new byte[12]);
    }

    public static MdReplayConnectionConfig createMDReplayConfig(String serviceSection) throws FileNotFoundException, NoSuchFieldException {
        String hostname = (String) Config.get(serviceSection+".host");
        Integer port = (Integer) Config.get(serviceSection+".port");
        return new MdReplayConnectionConfig(hostname, port);
    }

    public static MdConnectionConfig createMDConnectionConfig(String serviceSection) throws FileNotFoundException, NoSuchFieldException {
        String hostname = (String) Config.get(serviceSection+".host");
        Integer port = (Integer) Config.get(serviceSection+".port");
        String iterfce = (String) Config.get(serviceSection+".interface");
        return new MdConnectionConfig(iterfce, hostname, port);
    }
}
