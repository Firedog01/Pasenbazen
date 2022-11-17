package pl.lodz.p.edu.rest.managers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.RollbackException;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.rest.exception.NoObjectException;
import pl.lodz.p.edu.rest.exception.user.MalformedUserException;
import pl.lodz.p.edu.rest.exception.user.UserConflictException;
import pl.lodz.p.edu.rest.model.users.Admin;
import pl.lodz.p.edu.rest.model.users.Client;
import pl.lodz.p.edu.rest.model.users.ResourceAdmin;
import pl.lodz.p.edu.rest.model.users.User;
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

    public void registerClient(Client client) throws MalformedUserException, UserConflictException, NoObjectException {
        if (client == null) {
            throw new NoObjectException("Client cannot be null");
        }

        if (client.getFirstName() == null
                || client.getLastName() == null
                || !client.getAddress().verify()
        ) {
            throw new MalformedUserException("Clients fields have illegal values");
        }
        try {
            userRepository.add(client);
        } catch(RollbackException e) {
            throw new UserConflictException("Already exists user with given login");
        }
    }

    public void registerUserAdmin(Admin admin) throws UserConflictException, NoObjectException, MalformedUserException {
        if (admin == null) {
            throw new NoObjectException("Client cannot be null");
        }
        if (!admin.verify()) {
            throw new MalformedUserException("Admin fields have illegal values");
        }
        try {
            userRepository.add(admin);
        } catch(RollbackException e) {
            throw new UserConflictException("Already exists user with given login");
        }
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
        return userRepository.getAllWithLogin(login);
    }

    public User getUserByLogin(String login) {
        return userRepository.getByLogin(login);
    }

    // update

    public void updateClient(UUID entityId, Client client) {
        userRepository.update(client);
    }
    public void updateResourceAdmin(UUID entityId, ResourceAdmin resourceAdmin) {
    }

    public void updateUser(User user) {
        userRepository.update(user);
    }



}
