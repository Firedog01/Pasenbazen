package repository.impl;

import com.mongodb.MongoCommandException;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import mgd.ClientMgd;
import org.bson.conversions.Bson;
import repository.AbstractRepository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
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
        ClientSession session = getMongoClient().startSession();
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        try {
            session.startTransaction(TransactionOptions.builder().writeConcern(WriteConcern.MAJORITY).build());
            clientsCollection.insertOne(clientMgd);
            session.commitTransaction();
        } catch (MongoCommandException e) {
            session.abortTransaction();
            System.out.println("####### ROLLBACK TRANSACTION #######");
        } finally {
            session.close();
            System.out.println("####################################\n");
        }
    }




    public List<ClientMgd> getAllClients() {
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
//    public void update(String clientId, ClientMgd.idType clientIdType, String key, Object value) {
    public void update(String clientId, ClientMgd.idType clientIdType, String key, String value) {
        ClientSession session = getMongoClient().startSession();
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        Bson filter = and(eq("client_id", clientId), eq("client_id_type", clientIdType));
        Bson updateOp = Updates.set(key, value);

        try {                                                               //FIXME coś innego
            session.startTransaction(TransactionOptions.builder().writeConcern(WriteConcern.MAJORITY).build());
            clientsCollection.updateOne(filter, updateOp);
            session.commitTransaction();
        } catch (MongoCommandException e) {
            session.abortTransaction();
            System.out.println("####### ROLLBACK TRANSACTION #######");
        } finally {
            session.close();
            System.out.println("####################################\n");
        }

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

    public void findAndDelete(ClientMgd clientMgd, Bson removeBson) {
        ClientSession session = getMongoClient().startSession();
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        Bson filter = eq("_id", clientMgd.getEntityId().getUuid());

        try {
            //SESJA JEST TUTAJ
            clientsCollection.updateOne(session, filter, removeBson);
        } catch (MongoCommandException e) {
            System.out.println("#####   MongoCommandException  #####");
            System.out.println(e.getMessage());
        }

    }

}
