package repository.impl;

import jakarta.persistence.EntityManager;
import model.EQ.Equipment;
import repository.Repository;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EquipmentRepository implements Repository<Equipment> {

    private EntityManager em;

    public EquipmentRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Equipment get(long id) {
        return null;
    }

    @Override
    public List<Equipment> getAll() {
        return null;
    }

    @Override
    public void add(Equipment elem) {

    }

    @Override
    public void remove(Equipment elem) {

    }

    @Override
    public void update(Equipment elem) {

    }

    @Override
    public long count() {
        return 0;
    }
}