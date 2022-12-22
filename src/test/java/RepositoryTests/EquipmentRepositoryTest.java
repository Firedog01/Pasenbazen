package RepositoryTests;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.cassandra.exception.EquipmentException;
import pl.lodz.p.edu.cassandra.managers.EquipmentManager;
import pl.lodz.p.edu.cassandra.model.EQ.Camera;
import pl.lodz.p.edu.cassandra.model.EQ.Equipment;
import pl.lodz.p.edu.cassandra.model.EQ.Lens;
import pl.lodz.p.edu.cassandra.model.EQ.Trivet;
import pl.lodz.p.edu.cassandra.repository.DataFaker;
import pl.lodz.p.edu.cassandra.repository.Schemas.EquipmentSchema;
import pl.lodz.p.edu.cassandra.repository.Schemas.SchemaConst;
import pl.lodz.p.edu.cassandra.repository.impl.EquipmentDao;
import pl.lodz.p.edu.cassandra.repository.impl.EquipmentMapper;
import pl.lodz.p.edu.cassandra.repository.impl.EquipmentMapperBuilder;

import java.net.InetSocketAddress;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.dropTable;
import static org.junit.jupiter.api.Assertions.*;


public class EquipmentRepositoryTest {
    static CqlSession session;
    static EquipmentMapper equipmentMapper;
    static EquipmentDao equipmentDao;
    static EquipmentManager equipmentManager;

    @BeforeAll
    public static void init() {
        session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("localhost", 9042))
                .addContactPoint(new InetSocketAddress("localhost", 9043))
                .withLocalDatacenter("dc1")
                .withKeyspace(CqlIdentifier.fromCql("just_rent"))
                .withAuthCredentials("cassandra", "cassandra")
                .build();

        CreateKeyspace keyspace = SchemaBuilder.createKeyspace(SchemaConst.JUST_RENT_NAMESPACE)
                .ifNotExists()
                .withSimpleStrategy(2)
                .withDurableWrites(true);


        SimpleStatement createEquipment =
                SchemaBuilder.createTable(EquipmentSchema.equipments)
                        .ifNotExists()
                        .withPartitionKey(EquipmentSchema.equipmentUuid, DataTypes.UUID)
                        .withColumn(EquipmentSchema.name, DataTypes.TEXT)
                        .withColumn(EquipmentSchema.bail, DataTypes.DOUBLE)
                        .withColumn(EquipmentSchema.firstDayCost, DataTypes.DOUBLE)
                        .withColumn(EquipmentSchema.nextDaysCost, DataTypes.DOUBLE)
                        .withColumn(EquipmentSchema.isArchived, DataTypes.BOOLEAN)
                        .withColumn(EquipmentSchema.description, DataTypes.TEXT)
                        .withColumn(EquipmentSchema.isMissing, DataTypes.BOOLEAN)
                        .withColumn(EquipmentSchema.focalLength, DataTypes.TEXT)
                        .withColumn(EquipmentSchema.resolution, DataTypes.TEXT)
                        .withColumn(EquipmentSchema.weight, DataTypes.DOUBLE)
                        .withColumn(EquipmentSchema.discriminator, DataTypes.TEXT)
                        .build();

        session.execute(dropTable(EquipmentSchema.equipments).ifExists().build());

        SimpleStatement createKeySpace = keyspace.build();
        session.execute(createKeySpace);
        session.execute(createEquipment);

        equipmentMapper = new EquipmentMapperBuilder(session).build();
        equipmentManager = new EquipmentManager(equipmentMapper.equipmentDao());
    }

    @Test
    void equipmentTest() throws EquipmentException {
        Equipment lens1 = DataFaker.getLens();
        Equipment trivet1 = DataFaker.getTrivet();
        Equipment camera1 = DataFaker.getCamera();

        equipmentManager.registerEquipment(lens1);
        equipmentManager.registerEquipment(trivet1);
        equipmentManager.registerEquipment(camera1);

        Lens daoLens = (Lens) equipmentManager.getEquipment(lens1.getUuid());
        Trivet daoTrivet = (Trivet) equipmentManager.getEquipment(trivet1.getUuid());
        Camera daoCamera = (Camera) equipmentManager.getEquipment(camera1.getUuid());

        assertEquals(daoLens, lens1);
        assertEquals(daoTrivet, trivet1);
        assertEquals(daoCamera, camera1);

        lens1.setDescription("askdbjaskbhjdbjkas");

        equipmentManager.updateEquipment(lens1);

        lens1 = equipmentManager.getEquipment(lens1.getUuid());

        assertNotEquals(lens1, daoLens);

        assertTrue(equipmentManager.unregisterEquipment(lens1.getUuid()));

        assertNull(equipmentManager.getEquipment(lens1.getUuid()));

    }

}
