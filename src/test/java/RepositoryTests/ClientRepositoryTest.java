package RepositoryTests;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.cassandra.model.Client;
import pl.lodz.p.edu.cassandra.repository.DataFaker;
import pl.lodz.p.edu.cassandra.repository.Schemas.ClientSchema;
import pl.lodz.p.edu.cassandra.repository.Schemas.SchemaConst;
import pl.lodz.p.edu.cassandra.repository.impl.ClientDao;
import pl.lodz.p.edu.cassandra.repository.impl.ClientMapper;
import pl.lodz.p.edu.cassandra.repository.impl.ClientMapperBuilder;

import java.net.InetSocketAddress;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.dropTable;
import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientRepositoryTest {

    static CqlSession session;
    static ClientMapper clientMapper;
    static ClientDao clientDao;

    @BeforeAll
    public static void init() { //Throw into static method in interface and then run after initialization?
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

        SimpleStatement createKeySpace = keyspace.build();
        session.execute(createKeySpace);


        SimpleStatement createClients =
                SchemaBuilder.createTable(ClientSchema.clients)
                        .ifNotExists()
//                        .withPartitionKey(ClientSchema.clientId, DataTypes.TEXT)
                        .withPartitionKey("clientId", DataTypes.TEXT)
//                        .withClusteringColumn(ClientSchema.idType, DataTypes.TEXT)
                        .withColumn(ClientSchema.idType, DataTypes.TEXT)
                        .withColumn(ClientSchema.archive, DataTypes.BOOLEAN)
                        .withColumn(ClientSchema.city, DataTypes.TEXT)
                        .withColumn(ClientSchema.clientUuid, DataTypes.UUID)
                        .withColumn(ClientSchema.firstName, DataTypes.TEXT)
                        .withColumn(ClientSchema.lastName, DataTypes.TEXT)
                        .withColumn(ClientSchema.street, DataTypes.TEXT)
                        .withColumn(ClientSchema.streetNr, DataTypes.TEXT)
//                        .withClusteringOrder(ClientSchema.idType, ClusteringOrder.ASC)
                        .build();
        session.execute(dropTable(ClientSchema.clients).ifExists().build());
        session.execute(createClients);

        clientMapper = new ClientMapperBuilder(session).build();
        clientDao = clientMapper.clientDao();


    }

    @Test
    void checkConnection() {
        assertFalse(session.isClosed());
    }

    @Test
    void createClient() {
        Client client1 = DataFaker.getClient();
        assert client1 != null;
        assertNotNull(client1.getClientId());
        clientDao.add(client1);
//
//        assertNotNull(client1);
//        Client clientGet = clientDao.get(client1.getClientUuid());
//        assertEquals(client1, clientGet);

    }
}
