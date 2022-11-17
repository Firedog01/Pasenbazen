package pl.lodz.p.edu.rest.repository.impl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.rest.model.users.*;
import pl.lodz.p.edu.rest.repository.Repository;

import java.util.List;
import java.util.UUID;

@RequestScoped
public class UserRepository implements Repository<User> {

    @PersistenceContext(name = "request")
    protected EntityManager em;

    public UserRepository() {}

    // get user of any type
    @Override
    @Transactional
    public User get(UUID entityId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> user = cq.from(User.class);

        cq.select(user);
        cq.where(cb.equal(user.get(User_.ENTITY_ID), entityId));

        List<User> users = em.createQuery(cq).getResultList();

        if(users.isEmpty()) {
            throw new EntityNotFoundException("Client not found for uniqueId: " + entityId);
        }
        return users.get(0);
    }

    public User getOfType(String type, UUID entityId) {
        Query q = em.createQuery("SELECT user FROM User user WHERE user.entityId = :login and type(user) = " + type, User.class);
        q.setParameter("entityId", entityId);
        return (User) q.getSingleResult();
    }

    public Client getClient(UUID entityId) {
        Query q = em.createQuery("SELECT user FROM User user WHERE user.entityId = :login and type(user) = Client", User.class);
        q.setParameter("entityId", entityId);
        return (Client) q.getSingleResult();
    }

    public Admin getAdmin(UUID entityId) {
        Query q = em.createQuery("SELECT user FROM User user WHERE user.entityId = :login and type(user) = Admin", User.class);
        q.setParameter("entityId", entityId);
        return (Admin) q.getSingleResult();
    }

    public Employee getEmployee(UUID entityId) {
        Query q = em.createQuery("SELECT user FROM User user WHERE user.entityId = :login and type(user) = Employee", User.class);
        q.setParameter("entityId", entityId);
        return (Employee) q.getSingleResult();
    }

    @Transactional
    public User getByLogin(String login) {
        Query q = em.createQuery("SELECT user FROM User user WHERE user.login = :login", User.class);
        q.setParameter("login", login);
        return (User) q.getSingleResult();
    }

    @Transactional
    public List<User> getAllWithLogin(String login) {
        Query q = em.createQuery("SELECT user FROM User user WHERE user.login like :login", User.class);
        q.setParameter("login", login + "%");
        return q.getResultList();
    }

    @Override
    @Transactional
    public List<User> getAll()  {
        return em.createQuery("SELECT user FROM User user", User.class)
                .setLockMode(LockModeType.OPTIMISTIC).getResultList();
    }

    @Override
    @Transactional
    public void add(User elem) {
        em.persist(elem);
//        em.lock(elem, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
    }

    @Override
    @Transactional
    public void remove(UUID entityId) {
        User elem = get(entityId);
        em.lock(elem, LockModeType.OPTIMISTIC);
        em.remove(elem);
    }

    @Override
    @Transactional
    public void update(User elem) {
        em.lock(elem, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        em.merge(elem);
    }

    @Transactional
    public void updateUuid(UUID entityId, User elem) {
        Query q = em.createQuery("SELECT user FROM User user WHERE user.entityId = :entityId", User.class);
        q.setParameter("entityId", entityId);
        User existing = (User) q.getSingleResult();
        existing.merge(elem);

    }

    @Override
    @Transactional
    public Long count() {
        return em.createQuery("Select count(Client) from Client client", Long.class)
                .setLockMode(LockModeType.OPTIMISTIC).getSingleResult();
    }
}
