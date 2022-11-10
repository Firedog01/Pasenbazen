package pl.lodz.p.edu.rest.repository.impl;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import pl.lodz.p.edu.rest.model.Client;
import pl.lodz.p.edu.rest.model.EQ.Equipment;
import pl.lodz.p.edu.rest.model.Rent;
import pl.lodz.p.edu.rest.model.UniqueId;
import pl.lodz.p.edu.rest.model.Rent_;
import pl.lodz.p.edu.rest.repository.Repository;

import java.util.ArrayList;
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


        EntityTransaction et = em.getTransaction();
        et.begin();

        List<Rent> rents = em.createQuery(cq).setLockMode(LockModeType.OPTIMISTIC).getResultList();
        et.commit();

        if (rents.isEmpty()) {
            throw new EntityNotFoundException("Rent not found for uniqueId: " + uniqueId);
        }
        return rents.get(0);
    }

    @Override
    public List<Rent> getAll() {
        EntityTransaction et = em.getTransaction();
        et.begin();

        TypedQuery<Rent> rentQuery = em.createQuery("Select r from Rent r", Rent.class)
                .setLockMode(LockModeType.OPTIMISTIC);
        List<Rent> rents = rentQuery.getResultList();
        et.commit();
        return rents;
    }

    public List<Rent> getEquipmentRents(Equipment e) {
        if(e.getId() == null) {
            return new ArrayList<Rent>();
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rent> cq = cb.createQuery(Rent.class);
        Root<Rent> rent = cq.from(Rent.class);

        cq.select(rent);
        cq.where(cb.equal(rent.get(Rent_.EQUIPMENT), e));
        // jakiś błąd z cascade type

        EntityTransaction et = em.getTransaction();
        et.begin();
        List<Rent> rents = em.createQuery(cq).
                setLockMode(LockModeType.OPTIMISTIC).
                getResultList();
        et.commit();
        return rents;
    }

    @Override
    public boolean add(Rent elem) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {
            if(elem.getEquipment().getId() != null) {
                Equipment e = em.find(Equipment.class, elem.getEquipment().getId()); // tutaj lock!
                em.lock(e, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
                elem.setEquipment(e);
            }
            em.persist(elem);
            et.commit();
//            System.out.println("equipment found: " + e.toString());
        } catch (RollbackException e) {
            System.out.println("rollback");
        } finally {
            if (et.isActive()) {
                et.rollback();
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean remove(UUID uuid) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        Rent elem = get(new UniqueId(uuid)); //Fixme syf
        try {
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
    public void update(Rent elem) {
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
        Long count = em.createQuery("Select count(rent) from Rent rent", Long.class).getSingleResult();
        et.commit();
        return count;
    }

    public List<Rent> getRentByClient(Client clientP) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rent> cq = cb.createQuery(Rent.class);
        Root<Rent> rent = cq.from(Rent.class);

        cq.select(rent);
        cq.where(cb.equal(rent.get(Rent_.CLIENT), clientP));

        TypedQuery<Rent> q = em.createQuery(cq).setLockMode(LockModeType.OPTIMISTIC);
        List<Rent> rents = q.getResultList();

        if(rents.isEmpty()) {
            throw new EntityNotFoundException("Rent not found for client: " + clientP);
        }
        return rents;
    }

    public List<Rent> getRentByEq(Equipment equipment) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rent> cq = cb.createQuery(Rent.class);
        Root<Rent> rent = cq.from(Rent.class);

        cq.select(rent);
        cq.where(cb.equal(rent.get(Rent_.EQUIPMENT), equipment));

        TypedQuery<Rent> q = em.createQuery(cq).setLockMode(LockModeType.OPTIMISTIC);
        List<Rent> rents = q.getResultList();

        if(rents.isEmpty()) {
            throw new EntityNotFoundException("Rent not found for equipment: " + equipment);
        }
        return rents;
    }


}
