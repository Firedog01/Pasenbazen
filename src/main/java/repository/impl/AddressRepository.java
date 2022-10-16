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

import java.util.List;

public class AddressRepository implements Repository<Address> {

    private EntityManager em;

    public AddressRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Address get(UniqueId id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Address> cq = cb.createQuery(Address.class);
        Root<Address> address = cq.from(Address.class);

        cq.select(address);
        cq.where(cb.equal(address.get(Address_.ENTITY_ID), id));

        TypedQuery<Address> q = em.createQuery(cq);
        List<Address> addressList = q.getResultList();

        if(addressList.isEmpty()) {
            throw new EntityNotFoundException("Address not found for uniqueId: " + id);
        }
        return addressList.get(0);
    }

    @Override
    public List<Address> getAll() {
        List<Address> addressList = em.createQuery("Select address from Address address", Address.class).getResultList();
        return addressList;
    }

    @Override
    public void add(Address elem) {
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
    public void remove(Address elem) {
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
    public void update(Address elem) {
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
        return em.createQuery("Select count(address) from Address address", Long.class).getSingleResult();
    }
}
