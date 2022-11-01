package repository.impl;

import com.mongodb.MongoCommandException;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import jakarta.persistence.*;

import mgd.ClientMgd;
import mgd.EQ.EquipmentMgd;
import mgd.UniqueIdMgd;
import model.EQ.Equipment;
import model.UniqueId;
import org.bson.conversions.Bson;
import repository.AbstractRepository;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class EquipmentRepository extends AbstractRepository {

    private EntityManager em;

    //Niby ok ale related problems idk
    public void add(EquipmentMgd equipmentMgd) {
        ClientSession session = getMongoClient().startSession();
        MongoCollection<EquipmentMgd> eqCollection = getDb().getCollection("eq", EquipmentMgd.class);
        try {
            session.startTransaction(TransactionOptions.builder().writeConcern(WriteConcern.MAJORITY).build());
            eqCollection.insertOne(equipmentMgd);
            session.commitTransaction();
        } catch (MongoCommandException e) {
            session.abortTransaction();
            System.out.println("####### ROLLBACK TRANSACTION #######");
        } finally {
            session.close();
            System.out.println("####################################\n");
        }
    }

    public List<EquipmentMgd> getAllEq() {
        MongoCollection<EquipmentMgd> eqCollection = getDb().getCollection("eq", EquipmentMgd.class);
        ArrayList<EquipmentMgd> equipmentMgds = eqCollection.find().into(new ArrayList<>());
        return equipmentMgds;
    }

    public EquipmentMgd getById(UniqueIdMgd uniqueIdMgd) {
        MongoCollection<EquipmentMgd> eqCollection = getDb().getCollection("eq", EquipmentMgd.class);
        Bson filter = eq("_id", uniqueIdMgd);
        return eqCollection.find(filter).first();
    }

    public void update(UniqueIdMgd uniqueIdMgd, String key, String value) {
        ClientSession session = getMongoClient().startSession();
        MongoCollection<EquipmentMgd> eqCollection = getDb().getCollection("eq", EquipmentMgd.class);
        Bson filter = eq("_id", uniqueIdMgd);
        Bson updateOp = Updates.set(key, value);
        try {                                                               //FIXME coś innego
            session.startTransaction(TransactionOptions.builder().writeConcern(WriteConcern.MAJORITY).build());
            eqCollection.updateOne(filter, updateOp);
            session.commitTransaction();
        } catch (MongoCommandException e) {
            session.abortTransaction();
            System.out.println("####### ROLLBACK TRANSACTION #######");
        } finally {
            session.close();
            System.out.println("####################################\n");
        }
    }

    public void findAndDelete(EquipmentMgd clientMgd, Bson removeBson) {
        ClientSession session = getMongoClient().startSession();
        MongoCollection<EquipmentMgd> eqCollection = getDb().getCollection("eq", EquipmentMgd.class);
        Bson filter = eq("_id", clientMgd.getEntityId().getUuid());

        try {
            //SESJA JEST TUTAJ
            eqCollection.updateOne(session, filter, removeBson);
        } catch (MongoCommandException e) {
            System.out.println("#####   MongoCommandException  #####");
            System.out.println(e.getMessage());
        }
    }
}