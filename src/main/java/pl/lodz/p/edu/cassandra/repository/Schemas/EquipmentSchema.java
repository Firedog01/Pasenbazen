package pl.lodz.p.edu.cassandra.repository.Schemas;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;

public class EquipmentSchema {
    public static final CqlIdentifier equipments = CqlIdentifier.fromCql("equipments");
    public static final CqlIdentifier equipmentUuid = CqlIdentifier.fromCql("equipmentUuid");
    public static final CqlIdentifier name = CqlIdentifier.fromCql("name");
    public static final CqlIdentifier bail = CqlIdentifier.fromCql("bail");
    public static final CqlIdentifier firstDayCost = CqlIdentifier.fromCql("firstDayCost");
    public static final CqlIdentifier nextDaysCost = CqlIdentifier.fromCql("nextDayCost");
    public static final CqlIdentifier isArchived = CqlIdentifier.fromCql("isArchived");
    public static final CqlIdentifier description = CqlIdentifier.fromCql("description");
    public static final CqlIdentifier isMissing = CqlIdentifier.fromCql("isMissing");
    public static final CqlIdentifier discriminator = CqlIdentifier.fromCql("discriminator");
    public static final CqlIdentifier resolution = CqlIdentifier.fromCql("resolution");
    public static final CqlIdentifier focalLength = CqlIdentifier.fromCql("focalLength");
    public static final CqlIdentifier weight = CqlIdentifier.fromCql("weight");
}
