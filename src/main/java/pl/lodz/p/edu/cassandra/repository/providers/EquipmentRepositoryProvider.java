package pl.lodz.p.edu.cassandra.repository.providers;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import pl.lodz.p.edu.cassandra.exception.EquipmentException;
import pl.lodz.p.edu.cassandra.model.EQ.Camera;
import pl.lodz.p.edu.cassandra.model.EQ.Equipment;
import pl.lodz.p.edu.cassandra.model.EQ.Lens;
import pl.lodz.p.edu.cassandra.model.EQ.Trivet;
import pl.lodz.p.edu.cassandra.repository.Schemas.EquipmentSchema;

import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

public class EquipmentRepositoryProvider {

    private final CqlSession session;

    private final EntityHelper<Lens> lensEntityHelper;
    private final EntityHelper<Trivet> trivetEntityHelper;
    private final EntityHelper<Camera> cameraEntityHelper;

    public EquipmentRepositoryProvider(MapperContext context, EntityHelper<Lens> lensEntityHelper,
                                       EntityHelper<Trivet> trivetEntityHelper, EntityHelper<Camera> cameraEntityHelper) {
        this.session = context.getSession();
        this.lensEntityHelper = lensEntityHelper;
        this.trivetEntityHelper = trivetEntityHelper;
        this.cameraEntityHelper = cameraEntityHelper;
    }

    public void add(Equipment equipment) {
        session.execute(
                switch (equipment.getDiscriminator()) {
                    case "camera" -> {
                        Camera camera = (Camera) equipment;
                        yield session.prepare(cameraEntityHelper.insert().build())
                                .bind()
                                .setUuid(EquipmentSchema.equipmentUuid, camera.getUuid())
                                .setString(EquipmentSchema.name, camera.getName())
                                .setDouble(EquipmentSchema.bail, camera.getBail())
                                .setDouble(EquipmentSchema.firstDayCost, camera.getFirstDayCost())
                                .setDouble(EquipmentSchema.nextDaysCost, camera.getNextDaysCost())
                                .setBoolean(EquipmentSchema.isArchived, camera.isArchive())
                                .setString(EquipmentSchema.description, camera.getDescription())
                                .setBoolean(EquipmentSchema.isMissing, camera.isMissing())
                                .setString(EquipmentSchema.resolution, camera.getResolution())
                                .setString(EquipmentSchema.discriminator, camera.getDiscriminator());

                    }

                    case "lens" -> {
                        Lens lens = (Lens) equipment;
                        yield session.prepare(lensEntityHelper.insert().build())
                                .bind()
                                .setUuid(EquipmentSchema.equipmentUuid, lens.getUuid())
                                .setString(EquipmentSchema.name, lens.getName())
                                .setDouble(EquipmentSchema.bail, lens.getBail())
                                .setDouble(EquipmentSchema.firstDayCost, lens.getFirstDayCost())
                                .setDouble(EquipmentSchema.nextDaysCost, lens.getNextDaysCost())
                                .setBoolean(EquipmentSchema.isArchived, lens.isArchive())
                                .setString(EquipmentSchema.description, lens.getDescription())
                                .setBoolean(EquipmentSchema.isMissing, lens.isMissing())
                                .setString(EquipmentSchema.focalLength, lens.getFocalLength())
                                .setString(EquipmentSchema.discriminator, lens.getDiscriminator());

                    }

                    case "trivet" -> {
                        Trivet trivet = (Trivet) equipment;
                        yield session.prepare(trivetEntityHelper.insert().build())
                                .bind()
                                .setUuid(EquipmentSchema.equipmentUuid, trivet.getUuid())
                                .setString(EquipmentSchema.name, trivet.getName())
                                .setDouble(EquipmentSchema.bail, trivet.getBail())
                                .setDouble(EquipmentSchema.firstDayCost, trivet.getFirstDayCost())
                                .setDouble(EquipmentSchema.nextDaysCost, trivet.getNextDaysCost())
                                .setBoolean(EquipmentSchema.isArchived, trivet.isArchive())
                                .setString(EquipmentSchema.description, trivet.getDescription())
                                .setBoolean(EquipmentSchema.isMissing, trivet.isMissing())
                                .setDouble(EquipmentSchema.weight, trivet.getWeight())
                                .setString(EquipmentSchema.discriminator, trivet.getDiscriminator());

                    }
                    default ->
                            throw new IllegalStateException("Unexpected value: " + equipment.getDiscriminator().toLowerCase());
                }
        );
    }

    public Equipment get(UUID key) throws EquipmentException {
        Select selectEquipment = QueryBuilder.selectFrom(EquipmentSchema.equipments)
                .all()
                .where(Relation.column(EquipmentSchema.equipmentUuid).isEqualTo(literal(key)));
        Row row = session.execute(selectEquipment.build()).one();
        if (row == null) {
            throw new NullPointerException("row not found idk"); //fixme
        }
        String discriminator = row.getString(EquipmentSchema.discriminator);
        if (discriminator == null) {
            throw new NullPointerException("discriminator not found idk"); //fixme
        }
        return switch (discriminator) {
            case "lens" -> new Lens(
                    row.getUuid(EquipmentSchema.equipmentUuid),
                    row.getString(EquipmentSchema.name),
                    row.getDouble(EquipmentSchema.bail),
                    row.getDouble(EquipmentSchema.firstDayCost),
                    row.getDouble(EquipmentSchema.nextDaysCost),
                    row.getBoolean(EquipmentSchema.isArchived),
                    row.getString(EquipmentSchema.description),
                    row.getString(EquipmentSchema.discriminator),
                    row.getBoolean(EquipmentSchema.isMissing),
                    row.getString(EquipmentSchema.focalLength));

            case "trivet" -> new Trivet(
                    row.getUuid(EquipmentSchema.equipmentUuid),
                    row.getString(EquipmentSchema.name),
                    row.getDouble(EquipmentSchema.bail),
                    row.getDouble(EquipmentSchema.firstDayCost),
                    row.getDouble(EquipmentSchema.nextDaysCost),
                    row.getBoolean(EquipmentSchema.isArchived),
                    row.getString(EquipmentSchema.description),
                    row.getString(EquipmentSchema.discriminator),
                    row.getBoolean(EquipmentSchema.isMissing),
                    row.getDouble(EquipmentSchema.weight));

            case "camera" -> new Camera(
                    row.getUuid(EquipmentSchema.equipmentUuid),
                    row.getString(EquipmentSchema.name),
                    row.getDouble(EquipmentSchema.bail),
                    row.getDouble(EquipmentSchema.firstDayCost),
                    row.getDouble(EquipmentSchema.nextDaysCost),
                    row.getBoolean(EquipmentSchema.isArchived),
                    row.getString(EquipmentSchema.description),
                    row.getString(EquipmentSchema.discriminator),
                    row.getBoolean(EquipmentSchema.isMissing),
                    row.getString(EquipmentSchema.resolution));

            default -> throw new IllegalStateException("Unexpected value: " + discriminator);
        };
    }
}
