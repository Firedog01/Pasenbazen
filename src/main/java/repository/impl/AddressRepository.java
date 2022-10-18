package repository.impl;

import jakarta.persistence.*;
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


        EntityTransaction et = em.getTransaction();
        et.begin();

        List<Address> addressList = em.createQuery(cq).getResultList();
        et.commit();

        if(addressList.isEmpty()) {
            throw new EntityNotFoundException("Address not found for uniqueId: " + id);
        }
        return addressList.get(0);
    }

    @Override
    public List<Address> getAll() {
        EntityTransaction et = em.getTransaction();
        et.begin();

        TypedQuery<Address> addressList = em.createQuery("Select address from Address address", Address.class)
                .setLockMode(LockModeType.OPTIMISTIC);
        List<Address> ret = addressList.getResultList();
        et.commit();
        return ret;
    }

    @Override
    public void add(Address elem) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {
            em.persist(elem);
            em.lock(elem, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
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
            em.lock(elem, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
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
            em.lock(elem, LockModeType.OPTIMISTIC);
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
        EntityTransaction et = em.getTransaction();
        et.begin();
        Long count = em.createQuery("Select count(address) from Address address", Long.class).getSingleResult();
        et.commit();
        return count;
    }
}
