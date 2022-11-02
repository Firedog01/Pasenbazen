package repository.impl;

import com.mongodb.MongoCommandException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import mgd.EQ.EquipmentMgd;
import mgd.EquipmentRentMgd;
import mgd.RentMgd;
import mgd.UniqueIdMgd;
import org.bson.conversions.Bson;
import repository.AbstractRepository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class RentRepository extends AbstractRepository {

    public void add(RentMgd rentMgd) {
        ClientSession session = getNewSession();

        MongoCollection<RentMgd> equipmentCollection = getDb().getCollection("rents", RentMgd.class);
        MongoCollection<EquipmentRentMgd> equipmentRentsCollection =
                getDb().getCollection("equipment_rent", EquipmentRentMgd.class);
        try {
            session.startTransaction(getTransactionOptions());
            equipmentCollection.insertOne(session, rentMgd);
            session.commitTransaction();
        } catch (MongoCommandException e) {
            session.abortTransaction();
            System.out.println("####### ROLLBACK TRANSACTION #######");
        } finally {
            session.close();
            System.out.println("####################################\n");
        }
    }

    public List<RentMgd> getAllRents() {
        MongoCollection<RentMgd> eqCollection = getDb().getCollection("rents", RentMgd.class);
        ArrayList<RentMgd> rentMgds = eqCollection.find().into(new ArrayList<>());
        return rentMgds;
    }

    public RentMgd getById(UniqueIdMgd uniqueIdMgd) {
        MongoCollection<RentMgd> eqCollection = getDb().getCollection("rents", RentMgd.class);
        Bson filter = eq("_id", uniqueIdMgd);
        return eqCollection.find(filter).first();
    }

    public List<RentMgd> getByName(String name) {
        MongoCollection<RentMgd> eqCollection = getDb().getCollection("rents", RentMgd.class);
        Bson filter = eq("name", name);
        return eqCollection.find(filter).into(new ArrayList<RentMgd>());
    }

    public void update(UniqueIdMgd uniqueIdMgd, String key, String value) {
        ClientSession session = getNewSession();
        MongoCollection<EquipmentMgd> eqCollection = getDb().getCollection("equipment", EquipmentMgd.class);
        Bson filter = eq("_id", uniqueIdMgd);
        Bson updateOp = Updates.set(key, value);
        try {
            session.startTransaction(getTransactionOptions());
            eqCollection.updateOne(session, filter, updateOp);
            session.commitTransaction();
        } catch (MongoCommandException e) {
            session.abortTransaction();
            System.out.println("####### ROLLBACK TRANSACTION #######");
        } finally {
            session.close();
            System.out.println("####################################\n");
        }
    }

    public void deleteOne(RentMgd rentMgd) {
        ClientSession session = getNewSession();
        MongoCollection<RentMgd> eqCollection = getDb().getCollection("rents", RentMgd.class);
        Bson filter = eq("_id", rentMgd.getEntityId().getUuid());

        try {
            session.startTransaction(getTransactionOptions());
            eqCollection.deleteOne(session, filter);
            session.commitTransaction();
        } catch (MongoCommandException e) {
            session.abortTransaction();
            System.out.println("#####   MongoCommandException  #####");
            System.out.println(e.getMessage());
        } finally {
            session.close();
            System.out.println("####################################\n");
        }
    }
}