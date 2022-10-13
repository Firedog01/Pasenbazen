package repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Root;
import model.Client;
import model.Client_;
import org.hibernate.hql.internal.classic.WhereParser;
import repository.Repository;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ClientRepository implements Repository<Client> {
    private EntityManager em;

    public ClientRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Client get(long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Client> cq = cb.createQuery(Client.class);
        Root<Client> client = cq.from(Client.class);
        cq.select(client);
        cq.where(cb.equal(client.get(Client_.CLIENT_ID), id));

        TypedQuery<Client> q = em.createQuery(cq);
        List<Client> clients = q.getResultList();

        if(!clients.isEmpty()) {
            return clients.get(0);
        }
        return null;
    }

    @Override
    public List<Client> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Client> cq = cb.createQuery(Client.class);
        Root<Client> client = cq.from(Client.class);
        cq.select(client);

        TypedQuery<Client> q = em.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public void add(Client elem) {
        EntityTransaction t = em.getTransaction();
        t.begin();
        em.persist(elem);
        t.commit();
    }

    @Override
    public void remove(Client elem) {
        EntityTransaction t = em.getTransaction();
        t.begin();
        em.remove(elem);
        t.commit();
    }

    @Override
    public void update(Client elem) {
        EntityTransaction t = em.getTransaction();
        t.begin();
        em.merge(elem);
        t.commit();
    }

    @Override
    public long count() {
        return 0;
    }
}
