package repository.impl;

import jakarta.persistence.EntityManager;
import model.Rent;
import repository.Repository;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RentRepository implements Repository<Rent> {

    private EntityManager em;

    public RentRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Rent get(long id) {
        return null;
    }

    @Override
    public List<Rent> getAll() {
        return null;
    }

    @Override
    public void add(Rent elem) {

    }

    @Override
    public void remove(Rent elem) {

    }

    @Override
    public void update(Rent elem) {

    }

    @Override
    public long count() {
        return 0;
    }
}
