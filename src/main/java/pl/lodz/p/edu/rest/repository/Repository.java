package pl.lodz.p.edu.rest.repository;

import pl.lodz.p.edu.rest.model.UniqueId;

import java.util.List;
import java.util.UUID;

public interface Repository<T> {

    T get(UniqueId entityId);
    List<T> getAll();
    void add(T elem);
    void remove(UniqueId entityId);
    void update(T elem);
    Long count();
}
