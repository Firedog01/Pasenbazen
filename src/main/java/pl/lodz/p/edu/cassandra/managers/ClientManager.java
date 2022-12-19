package pl.lodz.p.edu.cassandra.managers;

import jakarta.persistence.EntityNotFoundException;
import pl.lodz.p.edu.cassandra.exception.ClientException;
import pl.lodz.p.edu.cassandra.model.Address;
import pl.lodz.p.edu.cassandra.model.Client;
import pl.lodz.p.edu.cassandra.model.IdType;
import pl.lodz.p.edu.cassandra.repository.impl.ClientRepository;


import java.util.ArrayList;
import java.util.List;

public class ClientManager {
    ClientRepository clientRepository;

    public ClientManager(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client registerClient(String clientId, IdType idtype, String name,
                                 String surname, Address address
    ) throws ClientException {
        Client client = new Client(clientId, idtype, name, surname, address);
        clientRepository.add(client);
        return client;
    }

    public void unregisterClient(Client client) {
        Client c = clientRepository.get(client.getUuid());
        clientRepository.remove(c);
    }

    public Client getClient(String clientId, IdType idType) {
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