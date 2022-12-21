package pl.lodz.p.edu.cassandra.repository.impl;

import com.datastax.oss.driver.api.mapper.annotations.*;
import pl.lodz.p.edu.cassandra.model.Client;
import pl.lodz.p.edu.cassandra.repository.providers.ClientRepositoryProvider;

import java.io.Closeable;
import java.util.UUID;

@Dao
public interface ClientDao {

    @Insert
    void add(Client client);
//
//
//    @QueryProvider(providerClass = ClientRepositoryProvider.class,
//            entityHelpers = {Client.class})
//    Client get(UUID clientId);

    @Select
    Client get(String clientId);

    @Update
    void update(Client client);
//
    @Delete(ifExists = true)
    boolean deleteIfExists(Client client);
}
