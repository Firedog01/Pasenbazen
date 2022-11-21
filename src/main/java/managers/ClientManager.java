package managers;

import exception.ClientException;
import jakarta.persistence.EntityNotFoundException;
import mgd.AddressMgd;
import mgd.ClientMgd;
import repository.impl.ClientRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientManager {
    ClientRepository clientRepository;

    public ClientManager(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientMgd registerClient(String clientId, String name, String surname,
                                    AddressMgd addressMgd) {
        ClientMgd clientMgd = new ClientMgd(clientId, name, surname, addressMgd);
        clientRepository.add(clientMgd);
        return clientMgd;
    }

    public void unregisterClient(ClientMgd clientMgd) {
        clientRepository.remove(clientMgd);
    }

    public ClientMgd getClient(String clientId) {
        try {
            return clientRepository.getByClientId(clientId);
        } catch (EntityNotFoundException ex) {
            return null;
        }
    }

    public List<ClientMgd> getAllClients() {
        return clientRepository.getAll();
    }

    public List<ClientMgd> getAllAvailableClients() {
        List<ClientMgd> all = getAllClients();
        List<ClientMgd> available = new ArrayList<>();
        for (ClientMgd c : all) {
            if (!(c.isArchive())) {
                available.add(c);
            }
        }
        return available;
    }
}
