package repository.impl;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import mgd.ClientMgd;
import mgd.UniqueIdMgd;
import org.bson.conversions.Bson;
import repository.AbstractRepository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ClientRepository extends AbstractRepository {

    // create

    public void add(ClientMgd clientMgd) {
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        clientsCollection.insertOne(clientMgd);
    }

    // read

    public List<ClientMgd> getAllClients() {
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        ArrayList<ClientMgd> clientMgds = clientsCollection.find().into(new ArrayList<>());
        return clientMgds;
    }

    public ClientMgd getByClientId(String clientId) {
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        Bson filter = eq("client_id", clientId);
        return clientsCollection.find(filter).first();
    }

    public ClientMgd getByUniqueId(UniqueIdMgd uniqueIdMgd) {
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        Bson filter = eq("_id", uniqueIdMgd);
        return clientsCollection.find(filter).first();
    }

    // update

    public void updateByKey(UniqueIdMgd uniqueIdMgd, String key, String value) {
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        Bson filter = eq("_id", uniqueIdMgd);
        Bson updateOp = Updates.set(key, value);
        clientsCollection.updateOne(filter, updateOp);
    }

    public void updateClient(ClientMgd client) {
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

    // delete

    public void deleteOne(ClientMgd clientMgd) {
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        Bson filter = eq("_id", clientMgd.getEntityId().getUuid());
        clientsCollection.deleteOne(filter);
    }

}
