package repository;

import java.util.List;
import java.util.function.Predicate;

public interface Repository<T> {

    T get(int pos);

    void add(T elem);

    void remove(T elem);

    List<T> findBy(Predicate<T> predicate);
    String report();

    int size();

    List<T> findAll();
}
