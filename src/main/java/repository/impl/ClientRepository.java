package repository.impl;

import exception.ClientException;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import model.Client;
import model.Client_;
import model.UniqueId;
import model.idType;
import repository.Repository;

import java.util.List;


public class ClientRepository implements Repository<Client> {
    private EntityManager em;

    public ClientRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Client get(UniqueId uniqueId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Client> cq = cb.createQuery(Client.class);
        Root<Client> client = cq.from(Client.class);

        cq.select(client);
        cq.where(cb.equal(client.get(Client_.ENTITY_ID), uniqueId));

        TypedQuery<Client> q = em.createQuery(cq);
        List<Client> clients = q.getResultList();

        if(clients.isEmpty()) {
            throw new EntityNotFoundException("Client not found for uniqueId: " + uniqueId);
        }
        return clients.get(0);
    }

    public Client getByClientId(String clientId, idType clientIdType) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Client> cq = cb.createQuery(Client.class);
        Root<Client> clientRoot = cq.from(Client.class);

        cq.select(clientRoot);
        cq.where(cb.and(
                cb.equal(clientRoot.get(Client_.CLIENT_ID), clientId),
                cb.equal(clientRoot.get(Client_.ID_TYPE), clientIdType)
        ));

        TypedQuery<Client> q = em.createQuery(cq);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Client not found for clientId and clientIdType:" +
                    " " + clientId + " " + clientIdType.toString());
        }
    }

    @Override
    public List<Client> getAll() {
        List<Client> clientList = em.createQuery("Select client from Client client", Client.class).getResultList();
        return clientList;
    }

    @Override
    public void add(Client elem) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {
            this.em.persist(elem);
            et.commit();
        } finally {
            if(et.isActive()) {
                et.rollback();
            }
        }
    }

    @Override
    public void remove(Client elem) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {
            this.em.remove(elem);
            et.commit();
        } finally {
            if(et.isActive()) {
                et.rollback();
            }
        }
    }

    @Override
    public void update(Client elem) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {
            this.em.merge(elem);
            et.commit();
        } finally {
            if(et.isActive()) {
                et.rollback();
            }
        }
    }

    @Override
    public Long count() {
        return em.createQuery("Select count(client) from Client client", Long.class).getSingleResult();
    }
}
