package pl.lodz.p.edu.cassandra.repository.impl;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.saving.NullSavingStrategy;
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
    @StatementAttributes(consistencyLevel = "QUORUM")
    void add(Equipment equipment) throws EquipmentException;

    @QueryProvider(providerClass = EquipmentRepositoryProvider.class,
            entityHelpers = {Lens.class, Trivet.class, Camera.class})
    @StatementAttributes(consistencyLevel = "QUORUM")
    Equipment get(UUID uuid) throws EquipmentException;

    @Update
    @StatementAttributes(consistencyLevel = "QUORUM")
    boolean update(Equipment equipment);

    @Update(customWhereClause = "equipmentUuid in (:equipmentUuid)", nullSavingStrategy = NullSavingStrategy.DO_NOT_SET)
    @StatementAttributes(consistencyLevel = "QUORUM")
    boolean archive(Equipment equipment, UUID equipmentUuid);

    @Delete(entityClass = Equipment.class)
    @StatementAttributes(consistencyLevel = "QUORUM")
    boolean delete(UUID equipmentUuid);
}
