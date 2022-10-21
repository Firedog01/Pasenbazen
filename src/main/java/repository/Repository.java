package repository;


import model.UniqueId;

import java.util.List;

public interface Repository<T> {

    T get(UniqueId id);

    List<T> getAll();

    boolean add(T elem);

    boolean remove(T elem);

    boolean update(T elem);

    int count();
}
