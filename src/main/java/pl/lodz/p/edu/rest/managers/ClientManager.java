package pl.lodz.p.edu.rest.managers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.rest.model.Client;
import pl.lodz.p.edu.rest.repository.impl.UserRepository;

import java.util.List;
import java.util.UUID;

@Transactional
@RequestScoped
public class ClientManager {

    @Inject
    private UserRepository userRepository;

    protected ClientManager() {}

    public boolean registerClient(Client client) {
        return userRepository.add(client);
    }

    public boolean unregisterClient(UUID uuid) {
        return userRepository.remove(uuid);
    }

    public Client getByClientId(String id) {
        return userRepository.getClientByIdName(id);
    }

    public Client getClientByUuid(UUID uuid) {
        return userRepository.get(uuid);
    }

    public List<Client> getAllClients() {
        return userRepository.getAll();
    }

    public boolean updateClient(Client client) {
        return userRepository.update(client);
    }
}
