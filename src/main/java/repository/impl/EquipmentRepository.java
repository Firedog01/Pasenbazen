package repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
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
        Equipment equipment = em.find(Equipment.class, uniqueId);  //FIXME ??
        if (equipment == null) {
            throw new EntityNotFoundException("There is no client with ID " + uniqueId.getUniqueID());
        }
        return equipment;
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
    public long count() {
        long lenEq = em.createQuery("SELECT COUNT(e) from Equipment e", Equipment.class).getFirstResult();
        return lenEq; //FIXME to trzeba sprawdzic
    }
}