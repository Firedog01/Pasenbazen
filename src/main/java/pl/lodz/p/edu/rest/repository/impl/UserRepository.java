package pl.lodz.p.edu.rest.repository.impl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.rest.model.*;
import pl.lodz.p.edu.rest.model.users.Client;
import pl.lodz.p.edu.rest.model.users.Client_;
import pl.lodz.p.edu.rest.model.users.User;
import pl.lodz.p.edu.rest.model.users.User_;
import pl.lodz.p.edu.rest.repository.Repository;

import java.util.List;

@RequestScoped
public class UserRepository implements Repository<User> {

    @PersistenceContext(name = "request")
    protected EntityManager em;

    public UserRepository() {}

    public UserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public User get(UniqueId entityId) {
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

    public User getUserByLogin(String login) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> clientRoot = cq.from(User.class);

        cq.select(clientRoot);
        cq.where(
                cb.equal(clientRoot.get(User_.LOGIN), login)
        );

        EntityTransaction et = em.getTransaction();
        et.begin();
        TypedQuery<User> q = em.createQuery(cq).setLockMode(LockModeType.OPTIMISTIC);

        try {
            User client = q.getSingleResult(); //Transaction is not ending so commit is needed.
            et.commit();
            return client;
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Client not found for clientId:" +
                    " " + login);
        }
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
    public void remove(UniqueId entityId) {
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

    @Override
    @Transactional
    public Long count() {
        return em.createQuery("Select count(Client) from Client client", Long.class)
                .setLockMode(LockModeType.OPTIMISTIC).getSingleResult();
    }
}
