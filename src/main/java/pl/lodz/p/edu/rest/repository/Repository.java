package pl.lodz.p.edu.rest.repository;

import pl.lodz.p.edu.rest.model.Client;
import pl.lodz.p.edu.rest.model.UniqueId;
import java.util.List;

public interface Repository<T> {

    T get(UniqueId id);

    List<T> getAll();

    void add(T elem);

    void remove(T elem);

    void update(T elem);

    Long count();
}
