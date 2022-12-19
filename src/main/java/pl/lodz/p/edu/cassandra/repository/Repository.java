package pl.lodz.p.edu.cassandra.repository;

import java.util.List;
import java.util.UUID;

public interface Repository<T> {

    T get(UUID uuid);

    List<T> getAll();

    void add(T elem);

    void remove(T elem);

    void update(T elem);
}