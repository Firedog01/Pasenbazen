package pl.lodz.p.edu.rest.repository.impl;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import pl.lodz.p.edu.rest.model.*;
import pl.lodz.p.edu.rest.repository.Repository;

import java.util.List;
import java.util.UUID;


public class ClientRepository implements Repository<Client> {
    private EntityManager em;

    public ClientRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Client get(UniqueId id) {
        return null;  //FIXME TEMP
    }

    public Client getClientByUuid(UniqueId uniqueId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Client> cq = cb.createQuery(Client.class);
        Root<Client> client = cq.from(Client.class);

        cq.select(client);
        cq.where(cb.equal(client.get(Client_.ENTITY_ID), uniqueId));

        EntityTransaction et = em.getTransaction();
        et.begin();
        List<Client> clients = em.createQuery(cq).setLockMode(LockModeType.OPTIMISTIC).getResultList();
        et.commit();

        if(clients.isEmpty()) {
            throw new EntityNotFoundException("Client not found for uniqueId: " + uniqueId);
        }
        return clients.get(0);
    }

    public Client getClientByIdName(String clientId, idType clientIdType) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Client> cq = cb.createQuery(Client.class);
        Root<Client> clientRoot = cq.from(Client.class);

        cq.select(clientRoot);
        cq.where(cb.and(
                cb.equal(clientRoot.get(Client_.CLIENT_ID), clientId),
                cb.equal(clientRoot.get(Client_.ID_TYPE), clientIdType)
        ));

        EntityTransaction et = em.getTransaction();
        et.begin();
        TypedQuery<Client> q = em.createQuery(cq).setLockMode(LockModeType.OPTIMISTIC);

        try {
            Client client = q.getSingleResult(); //Transaction is not ending so commit is needed.
            et.commit();
            return client;
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Client not found for clientId and clientIdType:" +
                    " " + clientId + " " + clientIdType.toString());
        }
    }

    @Override
    public List<Client> getAll() {

        EntityTransaction et = em.getTransaction();
        et.begin();
        List<Client> clientList = em.createQuery("Select Client from Client client", Client.class)
                .setLockMode(LockModeType.OPTIMISTIC).getResultList();
        et.commit();
        return clientList;
    }

    @Override
    public boolean add(Client elem) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {
            this.em.persist(elem);
            em.lock(elem, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            et.commit();
        } finally {
            if(et.isActive()) {
                et.rollback();
                return false;
            }
        }
        return true;
    }


    public boolean remove(UUID uuid) { //Fixme archive not remove for clients
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {
            Client elem = getClientByUuid(new UniqueId(uuid)); //Fixme co za syf
            em.lock(elem, LockModeType.OPTIMISTIC);
            this.em.remove(elem);
            et.commit();
        } finally {
            if(et.isActive()) {
                et.rollback();
                return false;
            }
        }
        return true;
    }

    @Override
    public void update(Client elem) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {
            em.lock(elem, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            em.merge(elem);
            et.commit();
        } finally {
            if(et.isActive()) {
                et.rollback();
            }
        }
    }

    @Override
    public Long count() {
        EntityTransaction et = em.getTransaction();
        et.begin();
        Long count =  em.createQuery("Select count(Client) from Client client", Long.class)
                .setLockMode(LockModeType.OPTIMISTIC).getSingleResult();
        et.commit();
        return count;
    }
}