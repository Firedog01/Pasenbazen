package repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import model.Rent;
import model.UniqueId;
import repository.Repository;

import java.util.List;
import java.util.UUID;


public class RentRepository implements Repository<Rent> {

    private EntityManager em;

    public RentRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Rent get(UniqueId uniqueId) {
        Rent rent = em.find(Rent.class, uniqueId);
        if (rent == null) {
            throw new EntityNotFoundException("There is no client with ID " + uniqueId.getUniqueID());
        }
        return rent;
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
        this.em.persist(elem);
        et.commit();
    }

    @Override
    public void remove(Rent elem) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        this.em.remove(elem);
        et.commit();
    }

    @Override
    public void update(Rent elem) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        this.em.merge(elem);
        et.commit();
    }
}
