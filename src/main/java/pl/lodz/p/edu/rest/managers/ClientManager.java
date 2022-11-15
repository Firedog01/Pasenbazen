package pl.lodz.p.edu.rest.managers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.rest.model.Client;
import pl.lodz.p.edu.rest.repository.RepositoryType;
import pl.lodz.p.edu.rest.repository.impl.ClientRepository;

import java.util.List;
import java.util.UUID;

@Transactional
@RequestScoped
public class ClientManager {

    @Inject
    private ClientRepository clientRepository;

    protected ClientManager() {}

    public boolean registerClient(Client client) {
        return clientRepository.add(client);
    }

    public boolean unregisterClient(UUID uuid) {
        return clientRepository.remove(uuid);
    }

    public Client getByClientId(String id) {
        return clientRepository.getClientByIdName(id);
    }

    public Client getClientByUuid(UUID uuid) {
        return clientRepository.get(uuid);
    }

    public List<Client> getAllClients() {
        return clientRepository.getAll();
    }

    public boolean updateClient(Client client) {
        return clientRepository.update(client);
    }
}
