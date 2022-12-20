package pl.lodz.p.edu.cassandra.repository.Schemas;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class SchemaConst {
    public static final CqlIdentifier JUST_RENT_NAMESPACE = CqlIdentifier.fromCql("just_rent");

    public static final CqlIdentifier UUID = CqlIdentifier.fromCql("clientUuid");
    public static final CqlIdentifier clientId = CqlIdentifier.fromCql("clientId");
}
