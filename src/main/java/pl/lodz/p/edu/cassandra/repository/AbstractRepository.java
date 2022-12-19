package pl.lodz.p.edu.cassandra.repository;

import java.util.List;

public abstract class AbstractRepository<T> implements AutoCloseable, Repository<T> {

    public AbstractRepository() {
    }
}