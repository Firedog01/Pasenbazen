package managers;

import model.Address;
import model.Client;
import model.idType;

import repository.impl.ClientRepository;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ClientManager {
    ClientRepository clientRepository;

    public ClientManager(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client registerClient(String name, String surname, Address address, String id, idType idType) {

        Client gotClient = getClient(id, idType);
        if (gotClient == null) {

            Client client = new Client(name, surname, address, id, idType);  //FIXME nie wiem po co są te archive
            clientRepository.add(client);
            return client;
        } else
            return gotClient;
    }

    public void unregisterClient(Client client) {
        if (!clientRepository.findBy(x -> x == client).isEmpty()) {
            clientRepository.findBy(x -> x == client).get(0).setArchive(true);
        }
    }

    public Client getClient(String id, idType idType) {

        //TODO CHECK PREDICATES, also i don't know how to put that in variable.
        if (clientRepository.findBy(c -> (c.getIdType() == idType && Objects.equals(c.getID(), id))).isEmpty()) {
            return null;
        } else {
            return clientRepository.findBy(c -> (c.getIdType() == idType && Objects.equals(c.getID(), id))).get(0);
        }
    }

    public List<Client> findClients(Predicate<Client> predicate) {
        return clientRepository.findBy(client -> !client.isArchive());
        //TODO CHECK
    }

    public List<Client> getAllClients() {
        return clientRepository.findBy(x -> !x.isArchive());
    }
}
