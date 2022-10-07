package repository.impl;

import model.EQ.Equipment;
import repository.Repository;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EquipmentRepository implements Repository<Equipment> {
    private List<Equipment> repository;

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
        return repository.stream().filter(predicate).collect(Collectors.toList()); //TODO To wystarczy?
    }

    @Override
    public String report() { //TODO??
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
    public List<Equipment> findAll() {
        return repository; //To wszystko?
    }
}