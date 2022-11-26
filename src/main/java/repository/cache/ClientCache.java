package repository.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mgd.ClientMgd;
import mgd.RentMgd;
import mgd.UniqueIdMgd;
import redis.clients.jedis.*;

public class ClientCache extends AbstractCache {

    ObjectMapper obj;
    String prefix;

    public ClientCache() {
        super();
        obj = new ObjectMapper();
        obj.registerModule(new JavaTimeModule());
//        obj.registerModule(new JodaModule());
        prefix = "client:";
    }

    public void save(ClientMgd client) {
        String clientString;
        try {
            clientString = obj.writeValueAsString(client);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String key = prefix + client.getEntityId().toString();
        pool.set(key, clientString);
    }

    public ClientMgd get(UniqueIdMgd entityId) throws JsonProcessingException {
        String key = prefix + entityId.toString();
        System.out.println(key);
        var ret = pool.get(key);
        return obj.readValue(ret, ClientMgd.class);
    }

    public void delete(ClientMgd rent) {
        String key = prefix + rent.getEntityId().toString();
        pool.del(key);
    }

    public void deleteAll() {
        JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();
        try (Jedis jedis = new Jedis(getHostAndPort(), clientConfig)) {
            jedis.flushAll();
        }
    }
}
