package repository.cache;

import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

public class RedisCache {

    public RedisCache() {
        HostAndPort hnp = getHostAndPort();
        JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();
        if(pool == null) {
            pool = new JedisPooled(hnp, clientConfig);
            task = new Healthcheck();
            timer = new Timer(true);
            healthy = RedisCache.checkHealthy();
            timer.scheduleAtFixedRate(task, 0, 2);
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

    private static HostAndPort getHostAndPort() {
        String connectionString = RedisCache.getConnectionString();
        return HostAndPort.from(connectionString);
    }

    // healthcheck

    private static TimerTask task = null;
    private static Timer timer = null;
    static class Healthcheck extends TimerTask {
        @Override
        public void run() {
            RedisCache.healthy = RedisCache.checkHealthy();
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

    public boolean isHealthy() {
        return healthy;
    }

    // connection

    private static JedisPooled pool;






    public void save(Object obj) {

    }

    public Object get(UUID uuid) {
        return new Object();
    }

    public void delete(UUID uuid) {

    }



    public void close() throws Exception {

    }
}
