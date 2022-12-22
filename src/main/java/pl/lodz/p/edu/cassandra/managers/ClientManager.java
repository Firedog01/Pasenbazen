package pl.lodz.p.edu.cassandra.managers;

import jakarta.persistence.EntityNotFoundException;
import pl.lodz.p.edu.cassandra.exception.ClientException;
import pl.lodz.p.edu.cassandra.model.Client;
import pl.lodz.p.edu.cassandra.repository.impl.ClientDao;

import java.util.UUID;

public class ClientManager {
    private final ClientDao clientDao;

    public ClientManager(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public Client registerClient(String clientId, String idType, String name,
                                 String surname, String city, String street, String streetNr
    ) throws ClientException {
        Client client = new Client(clientId, idType, name, surname, city, street, streetNr);
        clientDao.add(client);
        return client;
    }

    public void addClient(Client client) throws ClientException {
        clientDao.add(client);
    }

    public boolean unregisterClient(UUID uuid) {

        Client client = new Client();
        client.setArchive(true);
        return clientDao.archive(client, uuid); //FIXME ARCHIVE THAN DELETE
    }

    public Client getClient(UUID clientUuid) {
        try {
            return clientDao.get(clientUuid);
        } catch (EntityNotFoundException ex) {
            return null;
        }
    }

    public boolean updateClient(Client client) {
        return clientDao.update(client);
    }

//    public List<Client> getAllClients() {
//        return clientDao.getAll();
//    }

//    public List<Client> getAllAvailableClients() {
//        List<Client> all = getAllClients();
//        List<Client> available = new ArrayList<>();
//        for (Client c : all) {
//            if(!(c.isArchive())) {
//                available.add(c);
//            }
//        }
//        return available;
//    }
}