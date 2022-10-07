package repository.impl;

import model.Rent;
import repository.Repository;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RentRepository implements Repository<Rent> {
    private List<Rent> repository;
    //TODO
    @Override
    public Rent get(int pos) {
        return repository.get(pos);
    }

    @Override
    public void add(Rent elem) {
        repository.add(elem);
    }

    @Override
    public void remove(Rent elem) {
        repository.remove(elem);
    }

    @Override
    public List<Rent> findBy(Predicate<Rent> predicate) {
        return repository.stream().filter(predicate).collect(Collectors.toList()); //TODO?
    }

    @Override
    public String report() {
        final StringBuilder sb = new StringBuilder("EquipmentRepository{");
        sb.append("repository=").append(repository);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int size() {
        return repository.size();
    }

    @Override
    public List<Rent> findAll() {
        return repository;
    }
}
