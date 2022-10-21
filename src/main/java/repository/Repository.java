package repository;

import java.util.List;
import java.util.UUID;

public interface Repository<T> {

    T get(UUID uuid);

    List<T> getAll();

    boolean add(UUID uuid, T elem);

    boolean remove(UUID uuid);

    boolean update(UUID uuid, T elem);

    int count();
}
