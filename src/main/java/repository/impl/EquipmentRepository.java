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
import model.EQ.Equipment;
import model.UniqueId;
import repository.Repository;

import java.util.List;
import java.util.UUID;


public class EquipmentRepository implements Repository<Equipment> {

    private EntityManager em;

    public EquipmentRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Equipment get(UniqueId uniqueId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Equipment> cq = cb.createQuery(Equipment.class);
        Root<Equipment> equipment = cq.from(Equipment.class);

        cq.select(equipment);
        cq.where(cb.equal(equipment.get(Client_.ENTITY_ID), uniqueId));

        TypedQuery<Equipment> q = em.createQuery(cq);
        List<Equipment> equipmentList = q.getResultList();

        if(equipmentList.isEmpty()) {
            throw new EntityNotFoundException("Equipment not found for uniqueId: " + uniqueId);
        }
        return equipmentList.get(0);
    }

    @Override
    public List<Equipment> getAll() {
        List<Equipment> equipmentList = em.createQuery("Select eq from Equipment eq", Equipment.class).getResultList();
        return equipmentList;
    }

    @Override
    public void add(Equipment elem) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        this.em.persist(elem);
        et.commit();
    }

    @Override
    public void remove(Equipment elem) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        this.em.remove(elem);
        et.commit();
    }

    @Override
    public void update(Equipment elem) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        this.em.merge(elem);
        et.commit();
    }

    @Override
    public Long count() {
        return em.createQuery("Select count(eq) from Equipment eq", Long.class).getSingleResult();
    }
}