import com.lambdaworks.redis.*;
import org.junit.jupiter.api.Test;
import repository.cache.RedisCache;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class RedisTest {
    @Test
    void redisTest() {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "app.properties";
        Properties appProps = new Properties();
        String connectionString;
        try {
            appProps.load(new FileInputStream(appConfigPath));
            connectionString = appProps.getProperty("connectionString");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Property file not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        RedisClient redisClient = new RedisClient(RedisURI.create(connectionString));
        RedisConnection<String, String> connection = redisClient.connect();

        System.out.println("Connected to Redis");

        connection.close();
        redisClient.shutdown();
    }
}
