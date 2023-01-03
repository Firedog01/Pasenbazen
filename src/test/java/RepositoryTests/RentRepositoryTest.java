package RepositoryTests;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.cassandra.managers.RentManager;
import pl.lodz.p.edu.cassandra.model.Client;
import pl.lodz.p.edu.cassandra.model.EQ.Equipment;
import pl.lodz.p.edu.cassandra.model.Rent;
import pl.lodz.p.edu.cassandra.model.RentByClient;
import pl.lodz.p.edu.cassandra.model.RentByEquipment;
import pl.lodz.p.edu.cassandra.repository.DataFaker;
import pl.lodz.p.edu.cassandra.repository.Schemas.RentsSchema;
import pl.lodz.p.edu.cassandra.repository.Schemas.SchemaConst;
import pl.lodz.p.edu.cassandra.repository.impl.RentMapper;
import pl.lodz.p.edu.cassandra.repository.impl.RentMapperBuilder;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.dropTable;
import static org.junit.jupiter.api.Assertions.*;

public class RentRepositoryTest {
    static CqlSession session;
    static RentMapper rentMapper;
    static RentManager rentManager;

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


        SimpleStatement createRentsByClient =
                SchemaBuilder.createTable(RentsSchema.rentsByClient)
                        .ifNotExists()
                        .withPartitionKey(RentsSchema.clientUuid, DataTypes.UUID)
                        .withClusteringColumn(RentsSchema.rentUuid, DataTypes.UUID)
                        .withClusteringColumn(RentsSchema.beginTime, DataTypes.TEXT)
                        .withColumn(RentsSchema.equipmentUuid, DataTypes.UUID)
                        .withColumn(RentsSchema.endTime, DataTypes.TEXT)
                        .withColumn(RentsSchema.shipped, DataTypes.BOOLEAN)
                        .withColumn(RentsSchema.eqReturned, DataTypes.BOOLEAN)
                        .build();

        SimpleStatement createRentsByEquipment =
                SchemaBuilder.createTable(RentsSchema.rentsByEquipment)
                        .ifNotExists()
                        .withPartitionKey(RentsSchema.equipmentUuid, DataTypes.UUID)
                        .withClusteringColumn(RentsSchema.rentUuid, DataTypes.UUID)
                        .withClusteringColumn(RentsSchema.endTime, DataTypes.TEXT)
                        .withColumn(RentsSchema.clientUuid, DataTypes.UUID)
                        .withColumn(RentsSchema.beginTime, DataTypes.TEXT)
                        .withColumn(RentsSchema.shipped, DataTypes.BOOLEAN)
                        .withColumn(RentsSchema.eqReturned, DataTypes.BOOLEAN)
                        .build();

        session.execute(dropTable(RentsSchema.rentsByClient).ifExists().build());

        SimpleStatement createKeySpace = keyspace.build();
        session.execute(createKeySpace);
        session.execute(createRentsByClient);
        session.execute(createRentsByEquipment);

        rentMapper = new RentMapperBuilder(session).build();
        rentManager = new RentManager(rentMapper.rentDao());
    }


    @Test
    void rentTest() {
        Equipment equipment1 = DataFaker.getTrivet();
        Equipment equipment2 = DataFaker.getCamera();
        Client client1 = DataFaker.getClient();
        Client client2 = DataFaker.getClient();
        Rent rent1 = DataFaker.getRent(equipment1, client1);
        Rent rent2 = DataFaker.getRent(equipment2, client1);
        Rent rent3 = DataFaker.getRent(equipment2, client2);

        assert equipment1 != null;
        UUID rent1Uuid = rentManager.makeReservation(rent1.getBeginTime(), rent1.getEndTime(), equipment1, client1);
        assert equipment2 != null;
        UUID rent2Uuid = rentManager.makeReservation(rent2.getBeginTime(), rent2.getEndTime(), equipment2, client1);
        UUID rent3Uuid = rentManager.makeReservation(rent3.getBeginTime(), rent3.getEndTime(), equipment2, client2);


        assert client1 != null;
        List<RentByClient> rentListC = rentManager.getAllClientRents(client1.getUuid());
        assertEquals(rentListC.size(), 2);

        List<RentByEquipment> rentListE = rentManager.getAllEquipmentRents(equipment2.getUuid());
        assertEquals(rentListE.size(), 2); //FIXME somehow doesnt work here?

        RentByEquipment rbe = new RentByEquipment(rent1.getBeginTime(), rent1.getEndTime(), equipment2.getUuid(), client1.getUuid());
        rbe.setRentUuid(rent1Uuid);
        rentManager.updateEquipmentRent(rbe);

        List<RentByEquipment> rentListENew = rentManager.getAllEquipmentRents(equipment2.getUuid());
        assertEquals(rentListENew.size(), 3);
//        assertNotEquals(rentListE.get(0).getEquipmentUuid(), rentListENew.get(0).getEquipmentUuid());
    }

}
