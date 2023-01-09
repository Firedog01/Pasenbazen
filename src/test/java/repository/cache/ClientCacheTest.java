package repository.cache;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientCacheTest {
    @Test
    @Disabled
    void redisTestHealthy() throws InterruptedException {
        ClientCache cache = new ClientCache();
        for(int i = 0; i < 10; i++) {
            assertTrue(cache.isHealthy());
            Thread.sleep(2000);
            System.out.println(i);
        }
    }
}
