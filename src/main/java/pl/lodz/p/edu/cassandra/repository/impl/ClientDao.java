package pl.lodz.p.edu.cassandra.repository.impl;

import com.datastax.oss.driver.api.mapper.annotations.*;
import pl.lodz.p.edu.cassandra.model.Client;
import pl.lodz.p.edu.cassandra.repository.providers.ClientRepositoryProvider;

import java.util.UUID;

@Dao
public interface ClientDao {

    @QueryProvider(providerClass = ClientRepositoryProvider.class,
        entityHelpers = {Client.class})
    void add(Client client);
//
//
//    @QueryProvider(providerClass = ClientRepositoryProvider.class,
//            entityHelpers = {Client.class})
//    Client get(UUID clientId);
//
//
//    @Delete
//    void remove(Client client);

}
