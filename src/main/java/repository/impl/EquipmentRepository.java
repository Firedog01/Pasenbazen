package repository.impl;

import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import mgd.ClientMgd;
import mgd.EQ.CameraMgd;
import mgd.EQ.EquipmentMgd;
import mgd.EQ.LensMgd;
import mgd.EQ.TrivetMgd;
import mgd.UniqueIdMgd;
import model.EQ.Camera;
import model.EQ.Lens;
import org.bson.conversions.Bson;
import repository.AbstractRepository;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class EquipmentRepository extends AbstractRepository {

    // create

    public void add(EquipmentMgd equipment) {
        MongoCollection<EquipmentMgd> equipmentCollection =
                getDb().getCollection("equipment", EquipmentMgd.class);
        equipmentCollection.insertOne(equipment);
    }

    // read
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

    // update

    public void updateByKey(UniqueIdMgd uniqueIdMgd, String key, String value) {
        MongoCollection<EquipmentMgd> eqCollection = getDb().getCollection("equipment", EquipmentMgd.class);
        Bson filter = eq("_id", uniqueIdMgd);
        Bson updateOp = Updates.set(key, value);
        eqCollection.updateOne(filter, updateOp);
    }

    public LensMgd updateLensFocalLength(LensMgd lens, String focalLength) {
        MongoCollection<LensMgd> eqCollection = getDb().getCollection("equipment", LensMgd.class);
        Bson filter = eq("_id", lens.getEntityId());
        Bson updateOp = Updates.set("focal_length", focalLength);
        eqCollection.updateOne(filter, updateOp);
        lens.setFocalLength(focalLength);
        return lens;
    }

    public CameraMgd updateCameraResolution(CameraMgd camera, String resolution){
        MongoCollection<CameraMgd> eqCollection = getDb().getCollection("equipment", CameraMgd.class);
        Bson filter = eq("_id", camera.getEntityId());
        Bson updateOp = Updates.set("resolution", resolution);
        eqCollection.updateOne(filter, updateOp);
        camera.setResolution(resolution);
        return camera;
    }

    public TrivetMgd updateTrivetWeight(TrivetMgd trivet, double weight) {
        MongoCollection<TrivetMgd> eqCollection = getDb().getCollection("equipment", TrivetMgd.class);
        Bson filter = eq("_id", trivet.getEntityId());
        Bson updateOp = Updates.set("resolution", weight);
        eqCollection.updateOne(filter, updateOp);
        trivet.setWeight(weight);
        return trivet;
    }

    /*
     * only updates base class, no children
     */
    public void updateWholeEquipment(EquipmentMgd equipment) {
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

    // delete

    public void deleteOne(EquipmentMgd clientMgd) {
        MongoCollection<EquipmentMgd> eqCollection = getDb().getCollection("equipment", EquipmentMgd.class);
        Bson filter = eq("_id", clientMgd.getEntityId().getUuid());
        eqCollection.deleteOne(filter);
    }
}