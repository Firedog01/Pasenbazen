package repository.impl;

import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import mgd.ClientMgd;
import mgd.EQ.EquipmentMgd;
import mgd.EquipmentRents;
import mgd.RentMgd;
import mgd.UniqueIdMgd;
import org.bson.conversions.Bson;
import repository.AbstractRepository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class EquipmentRepository extends AbstractRepository {

    public void add(EquipmentMgd equipmentMgd) {
//        ClientSession session = getNewSession();
        MongoCollection<EquipmentMgd> equipmentCollection = getDb().getCollection("equipment", EquipmentMgd.class);
        try {
//            session.startTransaction(getTransactionOptions());
            equipmentCollection.insertOne(equipmentMgd);
//            session.commitTransaction();
        } catch (MongoCommandException e) {
//            session.abortTransaction();
            System.out.println("####### ROLLBACK TRANSACTION #######");
        } finally {
//            session.close();
            System.out.println("####################################\n");
        }
    }

    public List<EquipmentMgd> getAllEq() {
        MongoCollection<EquipmentMgd> eqCollection = getDb().getCollection("equipment", EquipmentMgd.class);
        ArrayList<EquipmentMgd> equipmentMgds = eqCollection.find().into(new ArrayList<>());
        return equipmentMgds;
    }

    public EquipmentMgd getById(UniqueIdMgd uniqueIdMgd) {
        MongoCollection<EquipmentMgd> eqCollection = getDb().getCollection("equipment", EquipmentMgd.class);
        Bson filter = eq("_id", uniqueIdMgd);
        return eqCollection.find(filter).first();
    }

    public List<EquipmentMgd> getByName(String name) {
        MongoCollection<EquipmentMgd> eqCollection = getDb().getCollection("equipment", EquipmentMgd.class);
        Bson filter = eq("name", name);
        return eqCollection.find(filter).into(new ArrayList<EquipmentMgd>());
    }

    public List<EquipmentRents> getEquipmentRents(EquipmentMgd equipment) {
        MongoCollection<EquipmentRents> eqRentsCollection = getDb().getCollection("equipment_rents", EquipmentRents.class);
        Bson filter = eq("equipment._id", equipment.getEntityId());
        return eqRentsCollection.find(filter).into(new ArrayList<EquipmentRents>());
    }

    /*
     * to update children fields use this
     */
    public void update(UniqueIdMgd uniqueIdMgd, String key, String value) {
//        ClientSession session = getNewSession();
        MongoCollection<EquipmentMgd> eqCollection = getDb().getCollection("equipment", EquipmentMgd.class);
        Bson filter = eq("_id", uniqueIdMgd);
        Bson updateOp = Updates.set(key, value);
        try {
//            session.startTransaction(getTransactionOptions());
            eqCollection.updateOne(filter, updateOp);
//            session.commitTransaction();
        } catch (MongoCommandException e) {
//            session.abortTransaction();
            System.out.println("####### ROLLBACK TRANSACTION #######");
        } finally {
//            session.close();
            System.out.println("####################################\n");
        }
    }

    /*
     * only updates base class, no children
     */
    public void updateEquipment(EquipmentMgd equipment) {
        MongoCollection<ClientMgd> clientsCollection =
                getDb().getCollection("equipment", ClientMgd.class);
        Bson filter = eq("_id", equipment.getEntityId().getUuid());
        Bson update = Updates.combine(
                Updates.set("first_day_cost", equipment.getFirstDayCost()),
                Updates.set("next_day_cost", equipment.getNextDaysCost()),
                Updates.set("bail", equipment.getBail()),
                Updates.set("name", equipment.getName()),
                Updates.set("archive", equipment.isArchive()),
                Updates.set("description", equipment.getDescription()),
                Updates.set("missing", equipment.isMissing())
        );
        clientsCollection.updateOne(filter, update);
    }

    public void deleteOne(EquipmentMgd clientMgd) {
//        ClientSession session = getNewSession();
        MongoCollection<EquipmentMgd> eqCollection = getDb().getCollection("equipment", EquipmentMgd.class);
        Bson filter = eq("_id", clientMgd.getEntityId().getUuid());

        try {
//            session.startTransaction(getTransactionOptions());
            eqCollection.deleteOne(filter);
//            session.commitTransaction();
        } catch (MongoCommandException e) {
//            session.abortTransaction();
            System.out.println("#####   MongoCommandException  #####");
            System.out.println(e.getMessage());
        } finally {
//            session.close();
            System.out.println("####################################\n");
        }
    }
}