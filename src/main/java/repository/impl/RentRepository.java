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
import org.bson.conversions.Bson;
import repository.AbstractRepository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class RentRepository extends AbstractRepository {

    // create

    public void add(RentMgd rentMgd) {
        MongoCollection<RentMgd> rentCollection = getDb().getCollection("rents", RentMgd.class);
        rentCollection.insertOne(rentMgd);
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
//        ClientSession session = startNewSession();
        MongoCollection<EquipmentMgd> eqCollection = getDb().getCollection("equipment", EquipmentMgd.class);
        Bson filter = eq("_id", uniqueIdMgd);
        Bson updateOp = Updates.set(key, value);
        try {
//            session.startTransaction(getTransactionOptions());
            eqCollection.updateOne(filter, updateOp);
//            session.commitTransaction();
        } catch (MongoCommandException e) {
//            session.abortTransaction();
        }
    }

    public RentMgd updateShipped(RentMgd rent, boolean shipped) {
        MongoCollection<RentMgd> rentCollection = getDb().getCollection("rents", RentMgd.class);
        Bson filter = eq("_id", rent.getEntityId());
        Bson updateOp = Updates.set("shipped", shipped);
        try {
            rentCollection.updateOne(filter, updateOp);
        } catch (MongoCommandException e) {
            System.out.println("#####  MongoCommandException  #####");
            System.out.println(e.getMessage());
        }
        rent.setShipped(shipped);
        return rent;
    }

    public RentMgd updateMissingReturned(RentMgd rent, boolean missing, boolean eqReturned) {
        ClientSession session = startNewSession();

        // prepare data
        MongoCollection<RentMgd> rentCollection = getDb().getCollection("rents", RentMgd.class);
        Bson filterRent = eq("_id", rent.getEntityId());
        Bson updateRent = Updates.combine(
                Updates.set("eqReturned", eqReturned),
                Updates.set("equipment.missing", missing)
        );
        MongoCollection<EquipmentMgd> equipmentCollection = getDb().getCollection("equipment", EquipmentMgd.class);
        Bson filterEq = eq("_id", rent.getEquipment().getEntityId());
        Bson updateEq = Updates.set("missing", missing);

        try {
            session.startTransaction(getTransactionOptions());
            rentCollection.updateOne(filterRent, updateRent);
            equipmentCollection.updateOne(filterEq, updateEq);
            session.commitTransaction();
        } catch (MongoCommandException e) {
            session.abortTransaction();
            System.out.println("#####  MongoCommandException  #####");
            System.out.println(e.getMessage());
        }

        // update model
        rent.setEqReturned(eqReturned);
        rent.getEquipment().setMissing(eqReturned);
        return rent;
    }
    // delete

    public void deleteOne(RentMgd rentMgd) {
        MongoCollection<RentMgd> rentCollection = getDb().getCollection("rents", RentMgd.class);
        Bson filter = eq("_id", rentMgd.getEntityId().getUuid());
        try {
            rentCollection.deleteOne(filter);
        } catch (MongoCommandException e) {
            System.out.println("#####  MongoCommandException  #####");
            System.out.println(e.getMessage());
        }
    }

}