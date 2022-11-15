package pl.lodz.p.edu.rest.managers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.rest.model.users.Client;
import pl.lodz.p.edu.rest.model.users.User;
import pl.lodz.p.edu.rest.model.users.UserAdmin;
import pl.lodz.p.edu.rest.repository.impl.UserRepository;

import java.util.List;
import java.util.UUID;

@Transactional
@RequestScoped
public class UserManager {

    @Inject
    private UserRepository userRepository;

    protected UserManager() {}

    public void registerClient(Client client) {
        userRepository.add(client);
    }

    public void registerUserAdmin(UserAdmin admin) {
        userRepository.add(admin);
    }

    public void unregisterClient(UUID entityId) {
        userRepository.remove(entityId);
    }

    public User getUserByUuid(UUID entityId) {
        return userRepository.get(entityId);
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public void updateClient(Client client) {
        userRepository.update(client);
    }
}
