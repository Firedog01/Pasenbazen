package RepositoryTests;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import org.junit.jupiter.api.BeforeAll;
import pl.lodz.p.edu.cassandra.managers.EquipmentManager;
import pl.lodz.p.edu.cassandra.managers.RentManager;
import pl.lodz.p.edu.cassandra.repository.Schemas.EquipmentSchema;
import pl.lodz.p.edu.cassandra.repository.Schemas.RentsSchema;
import pl.lodz.p.edu.cassandra.repository.Schemas.SchemaConst;
import pl.lodz.p.edu.cassandra.repository.impl.*;

import java.net.InetSocketAddress;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.dropTable;

public class RentRepositoryTest {
    static CqlSession session;
    static RentByClientMapper rentByClientMapper;
    static RentByEquipmentMapper rentByEquipmentMapper;
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
                        .withClusteringColumn(RentsSchema.beginTime, DataTypes.TIMESTAMP)
                        .withColumn(RentsSchema.endTime, DataTypes.TIMESTAMP)
                        .withColumn(RentsSchema.shipped, DataTypes.BOOLEAN)
                        .withColumn(RentsSchema.eqReturned, DataTypes.BOOLEAN)
                        .build();

        SimpleStatement createRentsByEquipment =
                SchemaBuilder.createTable(RentsSchema.rentsByEquipment)
                        .ifNotExists()
                        .withPartitionKey(RentsSchema.equipmentUuid, DataTypes.UUID)
                        .withClusteringColumn(RentsSchema.rentUuid, DataTypes.UUID)
                        .withClusteringColumn(RentsSchema.endTime, DataTypes.TIMESTAMP)
                        .withColumn(RentsSchema.beginTime, DataTypes.TIMESTAMP)
                        .withColumn(RentsSchema.shipped, DataTypes.BOOLEAN)
                        .withColumn(RentsSchema.eqReturned, DataTypes.BOOLEAN)
                        .build();


        session.execute(dropTable(RentsSchema.rentsByClient).ifExists().build());

        SimpleStatement createKeySpace = keyspace.build();
        session.execute(createKeySpace);
        session.execute(createRentsByClient);
        session.execute(createRentsByEquipment);

        rentByClientMapper = new RentByClientMapperBuilder(session).build();
        rentByEquipmentMapper = new RentByEquipmentMapperBuilder(session).build();
        rentManager = new RentManager(rentByClientMapper.rentByClientDao(), rentByEquipmentMapper.rentByEquipmentDao());
    }

}
