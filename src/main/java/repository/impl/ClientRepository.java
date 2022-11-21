package repository.impl;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import mgd.ClientMgd;
import mgd.UniqueIdMgd;
import model.UniqueId;
import org.bson.conversions.Bson;
import repository.AbstractRepository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ClientRepository extends AbstractRepository<ClientMgd> {

    // create

    @Override
    public void add(ClientMgd clientMgd) {
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        clientsCollection.insertOne(clientMgd);
    }

    // read
    @Override
    public ClientMgd get(UniqueIdMgd uniqueIdMgd) {
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        Bson filter = eq("_id", uniqueIdMgd);
        return clientsCollection.find(filter).first();
    }

    @Override
    public List<ClientMgd> getAll() {
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        return clientsCollection.find().into(new ArrayList<>());
    }

    public ClientMgd getByClientId(String clientId) {
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        Bson filter = eq("client_id", clientId);
        return clientsCollection.find(filter).first();
    }

    // update

    @Override
    public void update(ClientMgd client) {
        MongoCollection<ClientMgd> clientsCollection =
                getDb().getCollection("clients", ClientMgd.class);
        Bson filter = eq("_id", client.getEntityId().getUuid());
        Bson update = Updates.combine(
                Updates.set("first_name", client.getFirstName()),
                Updates.set("last_name", client.getLastName()),
                Updates.set("address", client.getAddress()),
                Updates.set("archive", client.isArchive())
        );
        clientsCollection.updateOne(filter, update);
    }

    public void updateByKey(mgd.UniqueIdMgd uniqueIdMgd, String key, String value) {
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        Bson filter = eq("_id", uniqueIdMgd);
        Bson updateOp = Updates.set(key, value);
        clientsCollection.updateOne(filter, updateOp);
    }

    // delete

    @Override
    public void remove(ClientMgd clientMgd) {
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        Bson filter = eq("_id", clientMgd.getEntityId().getUuid());
        clientsCollection.deleteOne(filter);
    }
}
