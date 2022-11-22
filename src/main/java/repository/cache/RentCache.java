package repository.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mgd.RentMgd;
import mgd.UniqueIdMgd;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class RentCache extends AbstractCache {

    ObjectMapper obj;
    String prefix;

    public RentCache() {
        super();
        obj = new ObjectMapper();
        prefix = "rent:";
    }


    public void save(RentMgd rent) {
        String rentString;
        try {
            rentString = obj.writeValueAsString(rent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String key = prefix + rent.getEntityId().toString();
        pool.jsonSet(key, rentString);
    }

    public RentMgd get(UniqueIdMgd entityId) {
        String key = prefix + entityId.toString();
        String rentStr = pool.get(key);
        return obj.convertValue(rentStr, RentMgd.class);
    }

    public void delete(RentMgd rent) {
        String key = prefix + rent.getEntityId().toString();
        pool.jsonDel(key);
    }

    public void deleteAll() {
        JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();
        try (Jedis jedis = new Jedis(getHostAndPort(), clientConfig)) {
            jedis.flushAll(); // flushDb
        }
    }
}
