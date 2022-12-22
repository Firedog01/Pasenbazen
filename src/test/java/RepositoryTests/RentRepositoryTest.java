package RepositoryTests;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.PagingIterable;
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
import pl.lodz.p.edu.cassandra.repository.DataFaker;
import pl.lodz.p.edu.cassandra.repository.Schemas.RentsSchema;
import pl.lodz.p.edu.cassandra.repository.Schemas.SchemaConst;
import pl.lodz.p.edu.cassandra.repository.impl.RentMapper;
import pl.lodz.p.edu.cassandra.repository.impl.RentMapperBuilder;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.dropTable;

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
        Equipment equipment = DataFaker.getTrivet();
        Client client = DataFaker.getClient();
        Rent rent = DataFaker.getRent(equipment, client);

        UUID rentUuid = rentManager.makeReservation(rent.getBeginTime(), rent.getEndTime(), equipment, client);

        List<RentByClient> rent1 = rentManager.getClientRents(client.getUuid());
        System.out.println(rent1);
    }

}
