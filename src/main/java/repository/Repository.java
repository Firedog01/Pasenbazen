package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.engine.spi.SessionFactoryDelegatingImpl;

import java.util.List;
import java.util.function.Predicate;

public interface Repository<T> {

    static EntityManagerFactory efm = Persistence.createEntityManagerFactory("POSTGRES_DB");

    T get(int pos);

    void add(T elem);

    void remove(T elem);

    List<T> findBy(Predicate<T> predicate);
    String report();

    int size();

    List<T> findAll();
}
