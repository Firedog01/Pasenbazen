package repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.Client;
import model.Client_;
import model.UniqueId;
import repository.Repository;

import java.util.List;
import java.util.UUID;


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

    @Override
    public List<Client> getAll() {
        List<Client> clientList = em.createQuery("Select client from Client client", Client.class).getResultList();
        return clientList;
    }

    @Override
    public void add(Client elem) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        this.em.persist(elem);
        et.commit();
    }

    @Override
    public void remove(Client elem) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        this.em.remove(elem);
        et.commit();
    }

    @Override
    public void update(Client elem) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        this.em.merge(elem);
        et.commit();
    }

    public Long count() {
        Long clientSize = em.createQuery("Select count(client) from Client client", Long.class).getSingleResult();
        return clientSize;
    }
}
