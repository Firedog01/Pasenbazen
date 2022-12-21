package pl.lodz.p.edu.cassandra.repository.providers;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import pl.lodz.p.edu.cassandra.exception.ClientException;
import pl.lodz.p.edu.cassandra.repository.Schemas.ClientSchema;
import pl.lodz.p.edu.cassandra.repository.Schemas.SchemaConst;
import pl.lodz.p.edu.cassandra.model.Client;

import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

public class ClientRepositoryProvider {
    private final CqlSession session;

    private EntityHelper<Client> clientEntityHelper;

    public ClientRepositoryProvider(MapperContext context, EntityHelper<Client> clientEntityHelper) {
        this.session = context.getSession();
        this.clientEntityHelper = clientEntityHelper;
    }

//    public void add(Client client) {
//        session.execute(
//                session.prepare(clientEntityHelper.insert().build())
//                        .bind()
//                        .setString(ClientSchema.clientId, client.getClientId())
//                        .setString(ClientSchema.idType, client.getIdType())
//                        .setBoolean(ClientSchema.archive, client.isArchive())
//                        .setString(ClientSchema.city, client.getCity())
//                        .setUuid(ClientSchema.clientUuid, client.getClientUuid())
//                        .setString(ClientSchema.firstName, client.getFirstName())
//                        .setString(ClientSchema.lastName, client.getLastName())
//                        .setString(ClientSchema.street, client.getStreet())
//                        .setString(ClientSchema.streetNr, client.getStreetNr())
//        );
//    }

    public Client get(UUID uuid) {
        Select selectClient = QueryBuilder
                .selectFrom(CqlIdentifier.fromCql("clients"))
                .all()
                .where(Relation.column(SchemaConst.UUID).isEqualTo(literal(uuid)));
        Row row = session.execute(selectClient.build()).one();
        try {
            return getClient(row);
        } catch (ClientException e) {
            throw new RuntimeException(e); //FIXME IDK temp for now
        }
    }

    private Client getClient(Row client) throws ClientException {
        if (client != null) {
        Client rClient = new Client(
                client.getUuid(ClientSchema.clientUuid),
                client.getString(ClientSchema.clientId),
                client.getString(ClientSchema.idType),
                client.getBoolean(ClientSchema.archive),
                client.getString(ClientSchema.firstName),
                client.getString(ClientSchema.lastName),
                client.getString(ClientSchema.city),
                client.getString(ClientSchema.street),
                client.getString(ClientSchema.streetNr)
        );
        return rClient;
    }
        return null;
    }
}
