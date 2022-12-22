package pl.lodz.p.edu.cassandra.repository.Schemas;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class RentsSchema {
    public static final CqlIdentifier rentsByClient = CqlIdentifier.fromCql("rentsByClient");
    public static final CqlIdentifier rentsByEquipment = CqlIdentifier.fromCql("rentsByEquipment");
    public static final CqlIdentifier rentUuid = CqlIdentifier.fromCql("rentUuid");
    public static final CqlIdentifier equipmentUuid = CqlIdentifier.fromCql("equipmentUuid");
    public static final CqlIdentifier clientUuid = CqlIdentifier.fromCql("clientUuid");
    public static final CqlIdentifier beginTime = CqlIdentifier.fromCql("beginTime");
    public static final CqlIdentifier endTime = CqlIdentifier.fromCql("endTime");
    public static final CqlIdentifier shipped = CqlIdentifier.fromCql("shipped");
    public static final CqlIdentifier eqReturned = CqlIdentifier.fromCql("eqReturned");
}