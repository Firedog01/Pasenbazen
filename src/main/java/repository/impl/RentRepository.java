package repository.impl;

import model.Client;
import model.Rent;
import repository.Repository;

import java.util.List;
import java.util.function.Predicate;

public class RentRepository implements Repository<Rent> {
    private List<Rent> repository;
    //TODO
    @Override
    public Rent get(int pos) {
        return null;
    }

    @Override
    public void add(Rent elem) {

    }

    @Override
    public void remove(Rent elem) {

    }

    @Override
    public List<Rent> findBy(Predicate<Rent> predicate) {
        return null;
    }

    @Override
    public String report() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public List<Rent> findAll() {
        return null;
    }
}
