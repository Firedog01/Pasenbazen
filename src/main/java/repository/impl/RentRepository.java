package repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import model.Rent;
import repository.Repository;

import java.util.List;
import java.util.UUID;


public class RentRepository implements Repository<Rent> {

    private EntityManager em;

    public RentRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Rent get(UUID id) {
        Rent rent = em.find(Rent.class, id);
        if (rent == null) {
            throw new EntityNotFoundException("There is no client with ID " + id);
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

    @Override
    public long count() {
        long lenRent = em.createQuery("SELECT COUNT(r) from Rent r", Rent.class).getFirstResult();
        return lenRent; //FIXME to trzeba sprawdzic
    }
}
