package repository;


import model.UniqueId;

import java.util.List;
import java.util.UUID;

public interface Repository<T> {

    T get(UniqueId id);

    List<T> getAll();

    void add(T elem);

    void remove(T elem);

    void update(T elem);
}
