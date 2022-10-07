package managers;

import model.Address;
import model.Client;
import model.idType;
import predicates.impl.ClientPredicate;
import repository.impl.ClientRepository;

import java.util.List;
import java.util.Objects;

public class ClientManager {
    ClientRepository clientRepository;
    //TODO ALL TODO :weary:

    public ClientManager(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client registerClient(String name, String surname, Address address, String id, idType idType) {

        Client client = new Client(name, surname, address, id, idType, true);  //FIXME nie wiem po co są te archive
        clientRepository.add(client);
        return client;
    }

    public void unregisterClient(Client client) {
        clientRepository.remove(client);
    }

    public Client getClient(String id, idType idType) {
        return (Client) clientRepository.findBy(x -> Objects.equals(x.getID(), id));
        //FIXME w zasadzie to jest bardzo niebezpieczne ale idę zgodnie z diagramem, a tam findBy zwraca listę,
        // a nie wiem jak znaleźć po ID w inny sposób, chyba tylko inna funkcja xD
    }

    public List<Client> findClients(ClientPredicate predicate) {
//        return clientRepository.findBy(predicate);  //TODO Problem z predykatami
        return null;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
}
