package repository.impl;

import jakarta.persistence.EntityManager;
import model.Client;
import repository.Repository;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ClientRepository implements Repository<Client> {
    private EntityManager em;

    public ClientRepository(EntityManager em) {
        this.em = em;
    }

    //TODO
    @Override
    public Client get(int pos) {
        return repository.get(pos);
    }

    @Override
    public void add(Client elem) {
        repository.add(elem);
    }

    @Override
    public void remove(Client elem) {
        repository.remove(elem);
    }

    @Override
    public List<Client> findBy(Predicate<Client> predicate) {
        return repository.stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public String report() {
        StringBuilder ret = new StringBuilder(new String("Informacje o repozytorium Client {\n"));

        for (Client client : repository) {
            ret.append(client.toString()).append('\n');
        }
        ret.append("}");
        return ret.toString();
    }

    @Override
    public int size() {
        return repository.size();
    }

    @Override
    public List<Client> findAll() {
        return repository;
    }
}
