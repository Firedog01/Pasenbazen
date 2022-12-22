package pl.lodz.p.edu.cassandra.repository.impl;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.QueryProvider;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import pl.lodz.p.edu.cassandra.model.Client;
import pl.lodz.p.edu.cassandra.model.RentByClient;
import pl.lodz.p.edu.cassandra.model.RentByEquipment;
import pl.lodz.p.edu.cassandra.repository.providers.ClientRepositoryProvider;
import pl.lodz.p.edu.cassandra.repository.providers.RentRepositoryProvider;

import java.util.UUID;

@Dao
public interface RentDao {

    @QueryProvider(providerClass = RentRepositoryProvider.class,
            entityHelpers = {RentByClient.class, RentByEquipment.class})
    void add(RentByClient rentByClient, RentByEquipment rentByEquipment);

    @Select
    PagingIterable<RentByClient> getByClient(UUID clientUuid);

    @Select
    PagingIterable<RentByEquipment> getByEquipment(UUID equipmentUuid);

//    @Delete
//    boolean delete(UUID uuid);

    //TODO UPDATE
}
