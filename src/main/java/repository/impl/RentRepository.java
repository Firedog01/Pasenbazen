package repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.*;
import repository.Repository;
import repository.RepositoryType;

import java.util.List;
import java.util.UUID;


public class RentRepository implements Repository<Rent> {

    private EntityManager em;

    public RentRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Rent get(UniqueId uniqueId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rent> cq = cb.createQuery(Rent.class);
        Root<Rent> rent = cq.from(Rent.class);

        cq.select(rent);
        cq.where(cb.equal(rent.get(Rent_.ENTITY_ID), uniqueId));

        TypedQuery<Rent> q = em.createQuery(cq);
        List<Rent> rents = q.getResultList();

        if(rents.isEmpty()) {
            throw new EntityNotFoundException("Rent not found for uniqueId: " + uniqueId);
        }
        return rents.get(0);
    }

    @Override
    public List<Rent> getAll() {
        List<Rent> rentList = em.createQuery("Select r from Rent r", Rent.class).getResultList();
        return rentList;
    }

    @Override
    public void add(Rent elem) {
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
    public void remove(Rent elem) {
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
    public void update(Rent elem) {
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
        return em.createQuery("Select count(rent) from Rent rent", Long.class).getSingleResult();
    }
}
