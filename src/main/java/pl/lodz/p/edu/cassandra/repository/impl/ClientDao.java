package pl.lodz.p.edu.cassandra.repository.impl;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.saving.NullSavingStrategy;
import pl.lodz.p.edu.cassandra.model.Client;
import pl.lodz.p.edu.cassandra.repository.providers.ClientRepositoryProvider;

import java.io.Closeable;
import java.util.UUID;

@Dao
public interface ClientDao {

    @Insert
    void add(Client client);

    @QueryProvider(providerClass = ClientRepositoryProvider.class,
            entityHelpers = {Client.class})
    Client get(UUID clientId);

//    @Select
//    Client get(String clientId);

    @Update
    boolean update(Client client);


    @Update(customWhereClause = "clientUuid in (:clientUuid)", nullSavingStrategy = NullSavingStrategy.DO_NOT_SET)
    boolean archive(Client client, UUID clientUuid);
}