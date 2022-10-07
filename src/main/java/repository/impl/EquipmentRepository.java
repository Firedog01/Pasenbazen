package repository.impl;

import model.Client;
import model.EQ.Equipment;
import repository.Repository;

import java.util.List;
import java.util.function.Predicate;

public class EquipmentRepository implements Repository<Equipment> {
    private List<Equipment> repository;
    //TODO
    @Override
    public Equipment get(int pos) {
        return null;
    }

    @Override
    public void add(Equipment elem) {

    }

    @Override
    public void remove(Equipment elem) {

    }

    @Override
    public List<Equipment> findBy(Predicate<Equipment> predicate) {
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
    public List<Equipment> findAll() {
        return null;
    }
}
