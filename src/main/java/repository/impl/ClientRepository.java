package repository.impl;

import model.Client;
import repository.Repository;

import java.util.List;
import java.util.function.Predicate;

public class ClientRepository implements Repository<Client> {
    private List<Client> repository;
    //TODO
    @Override
    public Client get(int pos) {
        return null;
    }

    @Override
    public void add(Client elem) {

    }

    @Override
    public void remove(Client elem) {

    }

    @Override
    public List<Client> findBy(Predicate<Client> predicate) {
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
    public List<Client> findAll() {
        return null;
    }
}
