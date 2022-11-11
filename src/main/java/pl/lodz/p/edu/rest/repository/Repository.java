package pl.lodz.p.edu.rest.repository;

import java.util.List;
import java.util.UUID;

public interface Repository<T> {

    T get(UUID id);
    List<T> getAll();
    boolean add(T elem);
    boolean remove(UUID uuid);
    boolean update(T elem);
    Long count();
}
