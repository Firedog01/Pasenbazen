package repository.impl;

import com.mongodb.MongoCommandException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import mgd.ClientMgd;
import mgd.UniqueIdMgd;
import org.bson.conversions.Bson;
import repository.AbstractRepository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ClientRepository extends AbstractRepository {

//FIXME CLIENTMGD OR CLIENTADDRESSMGD???
// ClientAddressMgd???
//    public void add(ClientMgd clientMgd) {
//        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
//        clientsCollection.insertOne(clientMgd);
//   }
//    public void add(ClientAddressMgd clientMgd) {
//        MongoCollection<ClientAddressMgd> clientsCollection = getDb().getCollection("clients", ClientAddressMgd.class);
//        clientsCollection.insertOne(clientMgd);
//    }

    public void add(ClientMgd clientMgd) {
//        ClientSession session = getNewSession();
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        try {
//            session.startTransaction(getTransactionOptions());
            clientsCollection.insertOne(clientMgd);
//            session.commitTransaction();
        } catch (MongoCommandException e) {
//            session.abortTransaction();
            System.out.println("####### ROLLBACK TRANSACTION #######");
        } finally {
//            session.close();
            System.out.println("####################################\n");
        }
    }

    public List<ClientMgd> getAllClients() {
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        ArrayList<ClientMgd> clientMgds = clientsCollection.find().into(new ArrayList<>());
        return clientMgds;
    }

    public List<ClientMgd> getByClientId(String clientId) {
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        Bson filter = eq("client_id", clientId);
        return clientsCollection.find(filter).into(new ArrayList<>());
    }

    public ClientMgd getByUniqueId(UniqueIdMgd uniqueIdMgd) {
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        Bson filter = eq("_id", uniqueIdMgd);
        return clientsCollection.find(filter).first();
    }

    //    public void update(String clientId, ClientMgd.idType clientIdType, String key, Object value) {
    //FIXME ten String clientId mi nie pasuje
    public void update(UniqueIdMgd uniqueIdMgd, String key, String value) {
//        ClientSession session = getNewSession();
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        Bson filter = eq("_id", uniqueIdMgd);
        Bson updateOp = Updates.set(key, value);
        try {
//            session.startTransaction(getTransactionOptions());
            clientsCollection.updateOne(filter, updateOp);
//            session.commitTransaction();
        } catch (MongoCommandException e) {
//            session.abortTransaction();
            System.out.println("####### ROLLBACK TRANSACTION #######");
        } finally {
//            session.close();
            System.out.println("####################################\n");
        }
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

//TODO to jest wcześniejszy pomysł
//      h
//    public void update(ClientMgd clientMgd, String key, Object value) {
//        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
//        Bson filter = eq("_id", clientMgd.getEntityId().getUuid());
//        Bson updateOp = Updates.set(key, value);
//        UpdateResult updateResult = clientsCollection.updateOne(filter, updateOp);
//    }

//TODO idk to jest wcześniejszy pomysł
//      h
//    public ClientMgd findAndDelete(ClientMgd clientMgd) throws Exception {
//        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
//        Bson filter = eq("_id", clientMgd.getEntityId().getUuid());
//        try (ClientMgd deletedClient = clientsCollection.findOneAndDelete(filter)) {
//         return deletedClient;
//        }
//    }

    public void deleteOne(ClientMgd clientMgd) {
//        ClientSession session = getNewSession();
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        Bson filter = eq("_id", clientMgd.getEntityId().getUuid());

        try {
//            session.startTransaction(getTransactionOptions());
            clientsCollection.deleteOne(filter);
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
