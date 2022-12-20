package pl.lodz.p.edu.cassandra.repository.Schemas;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class ClientSchema {
    public static final CqlIdentifier clients = CqlIdentifier.fromCql("clients");
    public static final CqlIdentifier clientUuid = CqlIdentifier.fromCql("clientUuid");
    public static final CqlIdentifier clientId = CqlIdentifier.fromCql("clientId");
    public static final CqlIdentifier idType = CqlIdentifier.fromCql("idType");
    public static final CqlIdentifier firstName = CqlIdentifier.fromCql("firstName");
    public static final CqlIdentifier lastName = CqlIdentifier.fromCql("lastName");
    public static final CqlIdentifier archive = CqlIdentifier.fromCql("archive");
    public static final CqlIdentifier city = CqlIdentifier.fromCql("city");
    public static final CqlIdentifier street = CqlIdentifier.fromCql("street");
    public static final CqlIdentifier streetNr = CqlIdentifier.fromCql("streetNr");
}
