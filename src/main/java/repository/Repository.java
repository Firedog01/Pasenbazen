package repository;

import java.util.List;
import java.util.function.Predicate;

public interface Repository<T> {

    public T get(int pos);

    public void add(T elem);

    public void remove(T elem);

    public List<T> findBy(Predicate<T> predicate);
    public String report();

    public int size();

    public List<T> findAll();
}
