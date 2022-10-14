package repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import model.Client;
import repository.Repository;

import java.util.List;
import java.util.UUID;


public class ClientRepository implements Repository<Client> {
    private EntityManager em;

    public ClientRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Client get(UUID clientID) {
        Client client = em.find(Client.class, clientID);
        if (client == null) {
            throw new EntityNotFoundException("There is no client with ID " + clientID);
        }
        return client;
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

    @Override
    public long count() {
        long lenClient = em.createQuery("SELECT COUNT(client) from Client client", Client.class).getFirstResult();
        return lenClient;
    }
}
