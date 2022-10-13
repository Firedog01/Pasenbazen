package managers;

import exception.ClientException;
import model.Client;

import model.Address;
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

    public Client registerClient(String name,
                                 String surname,
                                 String id,
                                 idType idType,
                                 Address address
    ) throws ClientException {
        Client gotClient = getClient(id, idType);
        if (gotClient == null) {

            Client client = new Client(name, surname, id, idType, address);
            clientRepository.add(client);
            return client;
        } else
            return gotClient;
    }

    public void unregisterClient(Client client) {
        Predicate<Client> clientPredicate = (x -> x == client);

        if (!clientRepository.findBy(clientPredicate).isEmpty()) {
            clientRepository.findBy(clientPredicate).get(0).setArchive(true);
        }
    }

    public Client getClient(String id, idType idType) {
        Predicate<Client> clientPredicate = (
                c -> (c.getIdType() == idType && Objects.equals(c.getClientId(), id)) //FIXME
                );

        if (clientRepository.findBy(clientPredicate).isEmpty()) {
            return null;
        } else {
            return clientRepository.findBy(clientPredicate).get(0);
        }
    }

    public List<Client> findClients(Predicate<Client> predicate) {
        Predicate<Client> clientPredicate = (
                client -> !client.isArchive());

        return clientRepository.findBy(clientPredicate);
    }

    public List<Client> getAllClients() {
        Predicate<Client> clientPredicate = (
                x -> !x.isArchive());

        return clientRepository.findBy(clientPredicate);
    }
}
