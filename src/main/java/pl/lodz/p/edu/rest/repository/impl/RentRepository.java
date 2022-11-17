package pl.lodz.p.edu.rest.repository.impl;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.rest.model.Equipment;
import pl.lodz.p.edu.rest.model.Rent;
import pl.lodz.p.edu.rest.model.Rent_;
import pl.lodz.p.edu.rest.model.users.Client;
import pl.lodz.p.edu.rest.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RentRepository implements Repository<Rent> {

    @PersistenceContext(unitName = "app")
    private EntityManager em;

    public RentRepository() {}

    @Override
    public Rent get(UUID entityId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rent> cq = cb.createQuery(Rent.class);
        Root<Rent> rent = cq.from(Rent.class);

        cq.select(rent);
        cq.where(cb.equal(rent.get(Rent_.ENTITY_ID), entityId));


        EntityTransaction et = em.getTransaction();
        et.begin();

        List<Rent> rents = em.createQuery(cq).setLockMode(LockModeType.OPTIMISTIC).getResultList();
        et.commit();

        if (rents.isEmpty()) {
            throw new EntityNotFoundException("Rent not found for uniqueId: " + entityId);
        }
        return rents.get(0);
    }

    @Override
    @Transactional
    public List<Rent> getAll() {
        TypedQuery<Rent> rentQuery = em.createQuery("Select r from Rent r", Rent.class)
                .setLockMode(LockModeType.OPTIMISTIC);
        return rentQuery.getResultList();
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
    @Transactional
    public void add(Rent elem) {
        if(elem.getEquipment().getId() != null) {
//            Equipment e = em.find(Equipment.class, elem.getEquipment().getId(),
//                    LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            Equipment e = em.find(Equipment.class, elem.getEquipment().getId());
            elem.setEquipment(e);
        }
        em.persist(elem);
    }

    @Override
    @Transactional
    public void remove(UUID entityId) {
        Rent elem = get(entityId);
        em.lock(elem, LockModeType.OPTIMISTIC);
        em.remove(elem);
    }

    @Override
    @Transactional
    public void update(Rent elem) {
        em.lock(elem, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        em.merge(elem);
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
            throw new EntityNotFoundException("Rent not found for Client: " + clientP);
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
