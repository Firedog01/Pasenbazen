package pl.lodz.p.edu.rest.managers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.RollbackException;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.rest.DTO.AdminDTO;
import pl.lodz.p.edu.rest.DTO.ClientDTO;
import pl.lodz.p.edu.rest.DTO.EmployeeDTO;
import pl.lodz.p.edu.rest.controllers.ClientController;
import pl.lodz.p.edu.rest.exception.NoObjectException;
import pl.lodz.p.edu.rest.exception.user.MalformedUserException;
import pl.lodz.p.edu.rest.exception.user.UserConflictException;
import pl.lodz.p.edu.rest.model.users.Admin;
import pl.lodz.p.edu.rest.model.users.Client;
import pl.lodz.p.edu.rest.model.users.Employee;
import pl.lodz.p.edu.rest.model.users.User;
import pl.lodz.p.edu.rest.repository.impl.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Transactional
@RequestScoped
public class UserManager {

    Logger logger = Logger.getLogger(UserManager.class.getName());

    @Inject
    private UserRepository userRepository;

    protected UserManager() {}

    // ========================================= create

    public void registerClient(Client client) throws MalformedUserException, UserConflictException {
        if (!client.verify()) {
            throw new MalformedUserException("Clients fields have illegal values");
        }
        try {
            userRepository.add(client);
        } catch(RollbackException e) {
            throw new UserConflictException("Already exists user with given login");
        }
    }

    public void registerAdmin(Admin admin) throws UserConflictException, MalformedUserException {
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

    public void registerEmployee(Employee employee) throws MalformedUserException, UserConflictException {
        if (!employee.verify()) {
            throw new MalformedUserException("Admin fields have illegal values");
        }
        try {
            userRepository.add(employee);
        } catch(RollbackException e) {
            throw new UserConflictException("Already exists user with given login");
        }
        userRepository.add(employee);
    }

//    public void unregisterClient(UUID entityId) {
//        userRepository.remove(entityId);
//    }

    // ========================================= read

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

    // ========================================= update

    public void updateClient(UUID entityId, ClientDTO clientDTO) throws MalformedUserException {
        Client clientVerify = new Client(clientDTO);
        logger.info(clientVerify.toString());
        if (clientVerify.verify()) {
            throw new MalformedUserException("Clients fields have illegal values");
        }
        Client client = (Client) userRepository.getOfType("Client", entityId);
        System.out.println(client);
        client.merge(clientDTO);
        userRepository.update(client);
    }

    public void updateAdmin(UUID entityId, AdminDTO adminDTO) {

    }
    public void updateEmployee(UUID entityId, EmployeeDTO employeeDTO) {
    }

    public void updateUser(User user) {
        userRepository.update(user);
    }
}
