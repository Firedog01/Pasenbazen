package repository.cache;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class RedisCache {
    public static String getConnectionString() {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "app.properties";
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
            return appProps.getProperty("connectionString");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Property file not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
