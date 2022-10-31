package repository.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import mgd.ClientMgd;
import org.bson.conversions.Bson;
import repository.AbstractRepository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;


public class ClientRepository extends AbstractRepository {

    //TODO transakcje???
    // ClientAddressMgd???
    public void add(ClientMgd clientMgd) {
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        clientsCollection.insertOne(clientMgd);
    }

    public List<ClientMgd> getAll() {
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        ArrayList<ClientMgd> clientMgds = clientsCollection.find().into(new ArrayList<>());
        return clientMgds;
    }

    public ClientMgd getById(String clientId, ClientMgd.idType clientIdType) {
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        Bson filter = and(eq("client_id", clientId), eq("client_id_type", clientIdType));
        return clientsCollection.find(filter).first();
    }

                                                                        //Bardzo mi się nie podoba ten object,
                                                                        //Dlaczego tak? Idk są pola różnego rodzaju
                                                                        //Update dla każdego pola? xD
    public void update(String clientId, ClientMgd.idType clientIdType, String key, Object value) {

        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        Bson filter = and(eq("client_id", clientId), eq("client_id_type", clientIdType));
        Bson updateOp = Updates.set(key, value);

        UpdateResult updateResult = clientsCollection.updateOne(filter, updateOp);
    }
//    public void update(ClientMgd clientMgd, String key, Object value) {
//
//        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
//        Bson filter = eq("_id", clientMgd.getEntityId().getUuid());
//        Bson updateOp = Updates.set(key, value);
//
//        UpdateResult updateResult = clientsCollection.updateOne(filter, updateOp);
//    }

    public ClientMgd findAndDelete(ClientMgd clientMgd) throws Exception {
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        Bson filter = eq("_id", clientMgd.getEntityId().getUuid());
        try (ClientMgd deletedClient = clientsCollection.findOneAndDelete(filter)) {
         return deletedClient;
        }
        //        ClientMgd deletedClient = clientsCollection.findOneAndDelete(filter);
    }

}
