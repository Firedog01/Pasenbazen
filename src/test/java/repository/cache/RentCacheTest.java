package repository.cache;

import org.junit.jupiter.api.Test;
import repository.cache.RentCache;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RentCacheTest {
    @Test
    void redisTestHealthy() throws InterruptedException {
        RentCache cache = new RentCache();
        for(int i = 0; i < 10; i++) {
            assertTrue(cache.isHealthy());
            Thread.sleep(2000);
            System.out.println(i);
        }
    }
}
