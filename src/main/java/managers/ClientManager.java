package managers;

import model.Address;
import model.Client;
import model.idType;
import predicates.impl.ClientPredicate;

import java.util.List;

public class ClientManager {
    //TODO ALL TODO :weary:

    public ClientManager() {
    }

    public Client registerClient(String name, String surname, Address address, String id, idType idType) {

        return null;
    }

    public void unregisterClient(Client client) {

    }

    public Client getClient(String id, idType idType) {
        return null;
    }

    public List<Client> findClients(ClientPredicate predicate) {

        return null;
    }

    public List<Client> getAllClients() {
        return null;
    }
}
