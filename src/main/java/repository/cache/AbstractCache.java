package repository.cache;

import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractCache {

    public AbstractCache() {
        HostAndPort hnp = getHostAndPort();
        JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();

        if(pool == null) {
            pool = new JedisPooled(hnp, clientConfig);
            task = new Healthcheck();
            timer = new Timer(true);
            healthy = ClientCache.checkHealthy();
            timer.scheduleAtFixedRate(task, 2, 2);
        }
    }

    // config
    public static String getConnectionString() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("app.properties");
        Properties appProps = new Properties();
        try {
            appProps.load(stream);
            return appProps.getProperty("connectionString");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Property file not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected static HostAndPort getHostAndPort() {
        String connectionString = ClientCache.getConnectionString();
        return HostAndPort.from(connectionString);
    }

    // healthcheck

    private static TimerTask task = null;
    private static Timer timer = null;
    static class Healthcheck extends TimerTask {
        @Override
        public void run() {
            AbstractCache.healthy = ClientCache.checkHealthy();
        }
    }

    private static boolean healthy;

    public static boolean checkHealthy() {
        HostAndPort hnp = getHostAndPort();
        JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();
        try(Jedis jedis = new Jedis(hnp, clientConfig)) {
            try {
                return Objects.equals(jedis.ping(), "PONG");
            } catch(JedisConnectionException e) {
                return false;
            }
        }
    }

    public static boolean isHealthy() {
        return healthy;
    }

    // connection

    protected static JedisPooled pool;
}
