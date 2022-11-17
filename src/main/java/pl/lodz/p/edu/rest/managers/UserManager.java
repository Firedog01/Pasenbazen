package pl.lodz.p.edu.rest.managers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.RollbackException;
import jakarta.transaction.Transactional;
import jakarta.transaction.TransactionalException;
import pl.lodz.p.edu.rest.model.users.DTO.AdminDTO;
import pl.lodz.p.edu.rest.model.users.DTO.ClientDTO;
import pl.lodz.p.edu.rest.model.users.DTO.EmployeeDTO;
import pl.lodz.p.edu.rest.exception.user.IllegalModificationException;
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
        } catch(TransactionalException e) {
            throw new UserConflictException("Already exists user with given login");
        } catch(RollbackException e) {
            throw new UserConflictException("Already exists user with given login");
        } catch(Throwable e) {
            logger.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            throw new UserConflictException("Already exists user with given login");
        }
    }

    public void registerAdmin(Admin admin) throws UserConflictException, MalformedUserException {
        if (!admin.verify()) {
            throw new MalformedUserException("Admin fields have illegal values");
        }
        try {
            userRepository.add(admin);
        } catch(PersistenceException e) {
            throw new UserConflictException("Already exists user with given login");
        } catch(Exception e) {
            System.out.println(e.getClass());
        }
    }

    public void registerEmployee(Employee employee) throws MalformedUserException, UserConflictException {
        if (!employee.verify()) {
            throw new MalformedUserException("Admin fields have illegal values");
        }
        try {
            userRepository.add(employee);
        } catch(PersistenceException e) {
            throw new UserConflictException("Already exists user with given login");
        }
    }

//    public void unregisterClient(UUID entityId) {
//        userRepository.remove(entityId);
//    }

    // ========================================= read

    public User getUserByUuidOfType(String type, UUID entityId) {
        return userRepository.getOfType(type, entityId);
    }

    public List<User> getAllUsersOfType(String type) {
        return userRepository.getAllOfType(type);
    }

    public List<User> searchOfType(String type, String login) {
        return userRepository.getAllWithLogin(type, login);
    }

    public User getUserByLoginOfType(String type, String login) {
        return userRepository.getByLogin(type, login);
    }

    // ========================================= update

    public void updateClient(UUID entityId, ClientDTO clientDTO) throws MalformedUserException, IllegalModificationException {
        try {
            Client clientVerify = new Client(clientDTO);
            if (!clientVerify.verify()) {
                throw new MalformedUserException("Clients fields have illegal values");
            }
        } catch(NullPointerException e) {
            throw new MalformedUserException("Client address cannot be null");
        }

        Client client = (Client) userRepository.getOfType("Client", entityId);
        client.merge(clientDTO);

        try {
            userRepository.update(client);
        } catch(PersistenceException e) {
            throw new IllegalModificationException("Cannot modify clients login");
        }
    }

    public void updateAdmin(UUID entityId, AdminDTO adminDTO) throws MalformedUserException, IllegalModificationException {
        Admin adminVerify = new Admin(adminDTO);
        if (!adminVerify.verify()) {
            throw new MalformedUserException("Clients fields have illegal values");
        }
        Admin admin = (Admin) userRepository.getOfType("Admin", entityId);
        admin.merge(adminDTO);

        try {
            userRepository.update(admin);
        } catch(PersistenceException e) {
            throw new IllegalModificationException("Cannot modify clients login");
        }
    }
    public void updateEmployee(UUID entityId, EmployeeDTO employeeDTO) throws IllegalModificationException, MalformedUserException {
        Employee employeeVerify = new Employee(employeeDTO);
        if (!employeeVerify.verify()) {
            throw new MalformedUserException("Clients fields have illegal values");
        }
        Employee employee = (Employee) userRepository.getOfType("Employee", entityId);
        employee.merge(employeeDTO);

        try {
            userRepository.update(employee);
        } catch(PersistenceException e) {
            throw new IllegalModificationException("Cannot modify clients login");
        }
    }

    public void updateUser(User user) {
        userRepository.update(user);
    }
}
