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
import pl.lodz.p.edu.cassandra.repository.impl.RentDao;
import pl.lodz.p.edu.cassandra.repository.impl.RentMapper;
import pl.lodz.p.edu.cassandra.repository.impl.RentMapperBuilder;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.dropTable;
import static org.junit.jupiter.api.Assertions.*;

public class RentRepositoryTest {
    static CqlSession session;
    static RentMapper rentMapper;
    static RentManager rentManager;

    static LocalDateTime t0;
    static LocalDateTime t1;
    static LocalDateTime t2;
    static LocalDateTime t3;
    static LocalDateTime t4;
    static LocalDateTime t5;
    // t0 = now
    // t0 < t1 < t2 < t3 < t4 < t5


    @BeforeAll
    static void init() {
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


        t0 = LocalDateTime.now().plusHours(1);
        t1 = t0.plusDays(1);
        t2 = t0.plusDays(2);
        t3 = t0.plusDays(3);
        t4 = t0.plusDays(4);
        t5 = t0.plusDays(5);
    }


    @Test
    void rentTest() {
        Equipment equipment1 = DataFaker.getTrivet();
        Equipment equipment2 = DataFaker.getCamera();
        Client client1 = DataFaker.getClient();
        Client client2 = DataFaker.getClient();

        Rent createdRent1 = rentManager.makeReservation(t0, t1, equipment1, client1);
        Rent createdRent2 = rentManager.makeReservation(t1, t2, equipment2, client1);
        Rent createdRent3 = rentManager.makeReservation(t3, t4, equipment2, client2);

        assertNotNull(createdRent1);
        assertNotNull(createdRent2);
        assertNotNull(createdRent3);

        // get by client rents
        List<RentByClient> rentListC = rentManager.getAllClientRents(client1.getUuid());
        assertEquals(rentListC.size(), 2);

        // get by equipment rents
        List<RentByEquipment> rentListE = rentManager.getAllEquipmentRents(equipment2.getUuid());
        assertEquals(rentListE.size(), 2);

        // add new
        Rent createdRent4 = rentManager.makeReservation(t4, t5, equipment2, client1);
        assertNotNull(createdRent4);

        // new rent is included
        List<RentByEquipment> rentListENew = rentManager.getAllEquipmentRents(equipment2.getUuid());
        assertEquals(rentListENew.size(), 3);

        // delete that new one
        rentManager.delete(createdRent4);

        rentListENew = rentManager.getAllEquipmentRents(equipment2.getUuid());
        assertEquals(rentListENew.size(), 2);

        // update equipment
        Rent updatedRent1 = rentManager.updateRentEquipment(createdRent1, equipment2);
        assertNotNull(updatedRent1);

        rentListENew = rentManager.getAllEquipmentRents(equipment2.getUuid());
        assertEquals(rentListENew.size(), 3);
    }

}
