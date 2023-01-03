package pl.lodz.p.edu.cassandra.repository.impl;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import pl.lodz.p.edu.cassandra.model.RentByClient;
import pl.lodz.p.edu.cassandra.model.RentByEquipment;
import pl.lodz.p.edu.cassandra.repository.providers.RentRepositoryProvider;

import java.util.UUID;

@Dao
public interface RentDao {

    @QueryProvider(providerClass = RentRepositoryProvider.class,
            entityHelpers = {RentByClient.class, RentByEquipment.class})
    @StatementAttributes(consistencyLevel = "QUORUM")
    void add(RentByClient rentByClient, RentByEquipment rentByEquipment);

    @Select
    @StatementAttributes(consistencyLevel = "QUORUM")
    PagingIterable<RentByClient> getByClient(UUID clientUuid);

    @Select
    @StatementAttributes(consistencyLevel = "QUORUM")
    PagingIterable<RentByEquipment> getByEquipment(UUID equipmentUuid);

    @Delete(entityClass = {RentByEquipment.class, RentByClient.class})
    @StatementAttributes(consistencyLevel = "QUORUM")
    boolean delete(UUID uuid);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Update
    void updateRentEquipment(RentByEquipment rent);

    @Update
    @StatementAttributes(consistencyLevel = "QUORUM")
    void updateRentClient(RentByClient rent);

}
