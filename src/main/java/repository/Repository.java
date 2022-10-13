package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.engine.spi.SessionFactoryDelegatingImpl;

import java.util.List;
import java.util.function.Predicate;

public interface Repository<T> {

    T get(long id);

    List<T> getAll();

    void add(T elem);

    void remove(T elem);

    void update(T elem);

    long count();
}
