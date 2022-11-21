package repository;


import mgd.UniqueIdMgd;

import java.util.List;

public interface Repository<T> {

    T get(UniqueIdMgd id);

    List<T> getAll();

    void add(T elem);

    void remove(T elem);

    void update(T elem);
}
