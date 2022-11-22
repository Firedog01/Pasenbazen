package repository;

import mgd.UniqueIdMgd;
import model.UniqueId;

import java.util.List;

public abstract class RepositoryDecorator<T> implements Repository<T> {
    protected AbstractRepository<T> decoratedRepository;

    public RepositoryDecorator(AbstractRepository<T> repository) {
        this.decoratedRepository = repository;
    }
}
