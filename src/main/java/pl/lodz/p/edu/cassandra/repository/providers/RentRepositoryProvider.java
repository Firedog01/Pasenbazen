package pl.lodz.p.edu.cassandra.repository.providers;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import pl.lodz.p.edu.cassandra.model.Client;
import pl.lodz.p.edu.cassandra.model.RentByClient;
import pl.lodz.p.edu.cassandra.model.RentByEquipment;
import pl.lodz.p.edu.cassandra.repository.Schemas.RentsSchema;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

public class RentRepositoryProvider {
    private final CqlSession session;

    private EntityHelper<RentByClient> rentByClientEntityHelper;
    private EntityHelper<RentByEquipment> rentByEquipmentEntityHelper;

    public RentRepositoryProvider(MapperContext context,
                                    EntityHelper<RentByClient> rentByClientEntityHelper, EntityHelper<RentByEquipment> rentByEquipmentEntityHelper) {
        this.session = context.getSession();
        this.rentByClientEntityHelper = rentByClientEntityHelper;
        this.rentByEquipmentEntityHelper = rentByEquipmentEntityHelper;
    }
    public void add(RentByClient rentByClient, RentByEquipment rentByEquipment) {
        Insert insertRentClient = QueryBuilder.insertInto(RentsSchema.rentsByClient)
                .value(RentsSchema.clientUuid, literal(rentByClient.getClientUuid()))
                .value(RentsSchema.rentUuid, literal(rentByClient.getRentUuid()))
                .value(RentsSchema.beginTime, literal(rentByClient.getBeginTime()))
                .value(RentsSchema.equipmentUuid, literal(rentByClient.getEquipmentUuid()))
                .value(RentsSchema.endTime, literal(rentByClient.getEndTime()))
                .value(RentsSchema.shipped, literal(rentByClient.isShipped()))
                .value(RentsSchema.eqReturned, literal(rentByClient.isEqReturned()))
                .ifNotExists();

        Insert insertRentEquipment = QueryBuilder.insertInto(RentsSchema.rentsByEquipment)
                .value(RentsSchema.equipmentUuid, literal(rentByEquipment.getEquipmentUuid()))
                .value(RentsSchema.rentUuid, literal(rentByEquipment.getRentUuid()))
                .value(RentsSchema.endTime, literal(rentByEquipment.getEndTime()))
                .value(RentsSchema.clientUuid, literal(rentByEquipment.getClientUuid()))
                .value(RentsSchema.beginTime, literal(rentByEquipment.getBeginTime()))
                .value(RentsSchema.shipped, literal(rentByEquipment.isShipped()))
                .value(RentsSchema.eqReturned, literal(rentByEquipment.isEqReturned()))
                .ifNotExists();

        SimpleStatement clientRent = insertRentClient.build();
        SimpleStatement equipmentRent = insertRentEquipment.build();

        session.execute(clientRent);
        session.execute(equipmentRent);
    }

}
