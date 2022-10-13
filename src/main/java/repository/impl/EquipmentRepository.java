package repository.impl;

import jakarta.persistence.EntityManager;
import model.EQ.Equipment;
import repository.Repository;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EquipmentRepository implements Repository<Equipment> {
    private EntityManager em;

    public EquipmentRepository(List<Equipment> repository) {
            this.repository = repository;
        }

    //TODO
    @Override
    public Equipment get(int pos) {
        return repository.get(pos);
    }

    @Override
    public void add(Equipment elem) {
        repository.add(elem);
    }

    @Override
    public void remove(Equipment elem) {
        repository.remove(elem);
    }

    @Override
    public List<Equipment> findBy(Predicate<Equipment> predicate) {
        return repository.stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public String report() {
        StringBuilder ret = new StringBuilder(new String("Informacje o repozytorium Equipment{\n"));

        for (Equipment eq :
                repository) {
            ret.append(eq.toString()).append('\n');
        }
        ret.append("}");
        return ret.toString();
    }

    @Override
    public int size() {
        return repository.size();
    }

    @Override
    public List<Equipment> findAll() {
        return repository;
    }
}