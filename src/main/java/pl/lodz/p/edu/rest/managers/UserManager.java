package pl.lodz.p.edu.rest.managers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.rest.model.users.Client;
import pl.lodz.p.edu.rest.model.users.ResourceAdmin;
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

    // create

    public void registerClient(Client client) {
        userRepository.add(client);
    }

    public void registerUserAdmin(UserAdmin admin) {
        userRepository.add(admin);
    }

    public void registerResourceAdmin(ResourceAdmin resourceAdmin) {
        userRepository.add(resourceAdmin);
    }

//    public void unregisterClient(UUID entityId) {
//        userRepository.remove(entityId);
//    }

    // read


    public User getUserByUuid(UUID entityId) {
        return userRepository.get(entityId);
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public List<User> search(String login) {
        return userRepository.getUsersByLogin(login);
    }

    // update

    public void updateClient(UUID entityId, Client client) {
        // todo
//        userRepository.update(client);
    }
    public void updateResourceAdmin(UUID entityId, ResourceAdmin resourceAdmin) {
    }

    public void updateUser(User user) {
        userRepository.update(user);
    }



}
