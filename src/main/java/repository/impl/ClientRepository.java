package repository.impl;

import com.mongodb.client.MongoCollection;
import mgd.ClientMgd;
import model.Client;
import repository.AbstractRepository;


public class ClientRepository extends AbstractRepository {

    public void add(ClientMgd clientMgd) {
        MongoCollection<ClientMgd> clientsCollection = getDb().getCollection("clients", ClientMgd.class);
        clientsCollection.insertOne(clientMgd);
    }
}
