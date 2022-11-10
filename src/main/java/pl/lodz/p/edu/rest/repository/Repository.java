package pl.lodz.p.edu.rest.repository;

import pl.lodz.p.edu.rest.model.UniqueId;
import java.util.List;
import java.util.UUID;

public interface Repository<T> {

    T get(UniqueId id);

    List<T> getAll();

    boolean add(T elem);

    boolean remove(UUID uuid);

    void update(T elem);

    Long count();
}
