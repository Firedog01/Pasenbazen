package pl.lodz.p.edu.cassandra.repository.impl;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.saving.NullSavingStrategy;
import pl.lodz.p.edu.cassandra.model.Client;
import pl.lodz.p.edu.cassandra.model.EQ.Equipment;
import pl.lodz.p.edu.cassandra.repository.providers.ClientRepositoryProvider;

import java.io.Closeable;
import java.util.UUID;

@Dao
public interface ClientDao {

    @Insert
    @StatementAttributes(consistencyLevel = "QUORUM")
    void add(Client client);

    @QueryProvider(providerClass = ClientRepositoryProvider.class,
            entityHelpers = {Client.class})
    @StatementAttributes(consistencyLevel = "QUORUM")
    Client get(UUID clientId);


    @Update
    @StatementAttributes(consistencyLevel = "QUORUM")
    boolean update(Client client);


    @Update(customWhereClause = "clientUuid in (:clientUuid)", nullSavingStrategy = NullSavingStrategy.DO_NOT_SET)
    @StatementAttributes(consistencyLevel = "QUORUM")
    boolean archive(Client client, UUID clientUuid);

    @Delete(entityClass = Client.class)
    @StatementAttributes(consistencyLevel = "QUORUM")
    boolean delete(UUID uuid);
}
