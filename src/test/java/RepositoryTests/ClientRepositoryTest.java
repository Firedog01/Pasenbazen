package RepositoryTests;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.cassandra.exception.ClientException;
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

public class ClientRepositoryTest {

    static CqlSession session;
    static ClientMapper clientMapper;
    static ClientDao clientDao;

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


        SimpleStatement createClients =
                SchemaBuilder.createTable(ClientSchema.clients)
                        .ifNotExists()
                        .withPartitionKey(ClientSchema.clientUuid, DataTypes.UUID)
//                        .withClusteringColumn(ClientSchema.idType, DataTypes.TEXT)
                        .withColumn(ClientSchema.clientId, DataTypes.TEXT)
                        .withColumn(ClientSchema.idType, DataTypes.TEXT)
                        .withColumn(ClientSchema.archive, DataTypes.BOOLEAN)
                        .withColumn(ClientSchema.city, DataTypes.TEXT)
                        .withColumn(ClientSchema.firstName, DataTypes.TEXT)
                        .withColumn(ClientSchema.lastName, DataTypes.TEXT)
                        .withColumn(ClientSchema.street, DataTypes.TEXT)
                        .withColumn(ClientSchema.streetNr, DataTypes.TEXT)
//                        .withClusteringOrder(ClientSchema.idType, ClusteringOrder.ASC)
                        .build();

        session.execute(dropTable(ClientSchema.clients).ifExists().build());

        SimpleStatement createKeySpace = keyspace.build();
        session.execute(createKeySpace);
        session.execute(createClients);

        clientMapper = new ClientMapperBuilder(session).build();
        clientDao = clientMapper.clientDao();
    }

    @Test
    void clientTest() throws ClientException {
        Client client1 = DataFaker.getClient();

        assertNotNull(client1);

        System.out.println(client1);

        clientDao.add(client1);

        Client rClient = clientDao.get(client1.getUuid());

        assertEquals(client1, rClient);

        client1.setCity("idk");
        client1.setArchive(true);

        clientDao.update(client1);

        client1 = clientDao.get(client1.getUuid());

        assertNotEquals(client1, rClient);

        System.out.println(client1);

        assertTrue(clientDao.deleteIfExistsByUUID(client1.getUuid()));

        assertNull(clientDao.get(client1.getUuid()));
    }
}
