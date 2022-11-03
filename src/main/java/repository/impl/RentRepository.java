package repository.impl;

import com.mongodb.MongoCommandException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import mgd.ClientMgd;
import mgd.EQ.EquipmentMgd;
import mgd.RentMgd;
import mgd.UniqueIdMgd;
import model.Rent;
import org.bson.conversions.Bson;
import repository.AbstractRepository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class RentRepository extends AbstractRepository {

    // create

    public void add(RentMgd rentMgd) {
        ClientSession session = getNewSession();

        MongoCollection<RentMgd> equipmentCollection = getDb().getCollection("rents", RentMgd.class);
//        MongoCollection<EquipmentRentMgd> equipmentRentsCollection =
//                getDb().getCollection("equipment_rent", EquipmentRentMgd.class);
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

    // read

    public List<RentMgd> getAllRents() {
        MongoCollection<RentMgd> rentCollection = getDb().getCollection("rents", RentMgd.class);
        return rentCollection.find(Filters.empty()).into(new ArrayList<>());
    }

    public RentMgd getById(UniqueIdMgd uniqueIdMgd) {
        MongoCollection<RentMgd> rentCollection = getDb().getCollection("rents", RentMgd.class);
        Bson filter = eq("_id", uniqueIdMgd);
        return rentCollection.find(filter).first();
    }

    public List<RentMgd> getEquipmentRents(EquipmentMgd equipment) {
        MongoCollection<RentMgd> rentCollection = getDb().getCollection("rents", RentMgd.class);
        Bson filter = eq("equipment._id", equipment.getEntityId());
        return rentCollection.find(filter).into(new ArrayList<RentMgd>());
    }

    public List<RentMgd> getClientRents(ClientMgd client) {
        MongoCollection<RentMgd> rentCollection = getDb().getCollection("rents", RentMgd.class);
        Bson filter = eq("clients._id", client.getEntityId());
        return rentCollection.find(filter).into(new ArrayList<RentMgd>());
    }

    // update

    public void updateByKey(UniqueIdMgd uniqueIdMgd, String key, String value) {
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

    public RentMgd updateShipped(RentMgd rent, boolean shipped) {
        MongoCollection<RentMgd> eqCollection = getDb().getCollection("rents", RentMgd.class);
        Bson filter = eq("_id", rent.getEntityId());
        Bson updateOp = Updates.set("shipped", shipped);
        eqCollection.updateOne(filter, updateOp);
        rent.setShipped(shipped);
        return rent;
    }

    public RentMgd updateMissingReturned(RentMgd rent, boolean missing, boolean eqReturned) {
        MongoCollection<RentMgd> eqCollection = getDb().getCollection("rents", RentMgd.class);
        Bson filter = eq("_id", rent.getEntityId());
        Bson updateOp = Updates.combine(
                Updates.set("eqReturned", eqReturned),
                Updates.set("eq.missing", missing)
        );
        eqCollection.updateOne(filter, updateOp);
        rent.setEqReturned(eqReturned);
        rent.getEquipment().setMissing(eqReturned);
        return rent;
    }

    // delete

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
            System.out.println("#####  MongoCommandException  #####");
            System.out.println(e.getMessage());
        } finally {
            session.close();
            System.out.println("####################################\n");
        }
    }

}