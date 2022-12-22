package pl.lodz.p.edu.cassandra.repository.impl;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.QueryProvider;
import pl.lodz.p.edu.cassandra.exception.EquipmentException;
import pl.lodz.p.edu.cassandra.model.EQ.Camera;
import pl.lodz.p.edu.cassandra.model.EQ.Equipment;
import pl.lodz.p.edu.cassandra.model.EQ.Lens;
import pl.lodz.p.edu.cassandra.model.EQ.Trivet;
import pl.lodz.p.edu.cassandra.repository.providers.EquipmentRepositoryProvider;

import java.util.UUID;

@Dao
public interface EquipmentDao {

    @QueryProvider(providerClass = EquipmentRepositoryProvider.class,
    entityHelpers = {Lens.class, Trivet.class, Camera.class})
    void add(Equipment equipment) throws EquipmentException;

    @QueryProvider(providerClass = EquipmentRepositoryProvider.class,
            entityHelpers = {Lens.class, Trivet.class, Camera.class})
    Equipment get(UUID uuid) throws EquipmentException;
}
