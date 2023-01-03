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
import pl.lodz.p.edu.cassandra.model.Client;
import pl.lodz.p.edu.cassandra.repository.Schemas.ClientSchema;

import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

public class ClientRepositoryProvider {
    private final CqlSession session;

    private EntityHelper<Client> clientEntityHelper;

    public ClientRepositoryProvider(MapperContext context, EntityHelper<Client> clientEntityHelper) {
        this.session = context.getSession();
        this.clientEntityHelper = clientEntityHelper;
    }



    public Client get(UUID uuid) {
        Select selectClient = QueryBuilder
                .selectFrom(CqlIdentifier.fromCql("clients"))
                .all()
                .where(Relation.column(ClientSchema.clientUuid).isEqualTo(literal(uuid)));
        Row row = session.execute(selectClient.build()).one();
        try {
            return getClient(row);
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
    }

    private Client getClient(Row row) throws ClientException {
        if (row != null) {
            Client rClient = new Client(
                    row.getString(ClientSchema.clientId),
                    row.getString(ClientSchema.idType),
                    row.getString(ClientSchema.firstName),
                    row.getString(ClientSchema.lastName),
                    row.getString(ClientSchema.city),
                    row.getString(ClientSchema.street),
                    row.getString(ClientSchema.streetNr)
            );
            rClient.setArchive(row.getBoolean(ClientSchema.archive));
            rClient.setUuid(row.getUuid(ClientSchema.clientUuid));
            return rClient;
        }
        return null;
    }
}
