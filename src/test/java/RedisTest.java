import org.junit.jupiter.api.Test;
import repository.cache.RedisCache;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RedisTest {
    @Test
    void redisTestHealthy() throws InterruptedException {
        RedisCache cache = new RedisCache();
        for(int i = 0; i < 10; i++) {
            assertTrue(cache.isHealthy());
            Thread.sleep(2000);
            System.out.println(i);
        }
    }
}
