package pl.lodz.p.edu.rest.managers;

import pl.lodz.p.edu.rest.exception.ClientException;
import jakarta.persistence.EntityNotFoundException;
import pl.lodz.p.edu.rest.model.Client;

import pl.lodz.p.edu.rest.model.Address;
import pl.lodz.p.edu.rest.model.idType;
import pl.lodz.p.edu.rest.repository.impl.ClientRepository;

import java.util.ArrayList;
import java.util.List;

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
        Client c = clientRepository.get(client.getEntityId());
        clientRepository.remove(c);
    }

    public Client getClient(String clientId, idType idType) {
        try {
            return clientRepository.getByClientId(clientId, idType);
        } catch (EntityNotFoundException ex) {
            return null;
        }
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
