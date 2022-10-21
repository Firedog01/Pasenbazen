package managers;

import exception.ClientException;
import model.Client;

import model.Address;
import model.idType;
import repository.impl.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientManager {
    ClientRepository clientRepository;

    public ClientManager(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client registerClient(String clientId, idType idtype, String name,
                                 String surname, Address address
    ) throws ClientException {
        Client client = new Client(clientId, idtype, name, surname, address);
        clientRepository.add(client);
        return client;
    }

    public void unregisterClient(Client client) {
        Client c = clientRepository.get(client.getClientUniqueID());
        clientRepository.remove(c);
    }

// FIXME
//    public Client getClient(String clientId, idType idType) {
//        try {
//            return clientRepository.getByClientId(clientId, idType);
//        } catch (EntityNotFoundException ex) {
//            return null;
//        }
//  }

      public Client getClient(UUID uuid) {
        return clientRepository.get(uuid);
    }

    public List<Client> getAllClients() {
        return clientRepository.getAll();
    }

    public List<Client> getAllAvailableClients() {
        List<Client> all = getAllClients();
        List<Client> available = new ArrayList<>();
        for (Client c : all) {
            if(!(c.isArchive())) {
                available.add(c);
            }
        }
        return available;
    }
}
