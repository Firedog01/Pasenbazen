package repository;

import mgd.UniqueIdMgd;
import model.UniqueId;

import java.util.List;

public abstract class RepositoryDecorator implements Repository {
    protected AbstractRepository decoratedRepository;

    public RepositoryDecorator(AbstractRepository repository) {
        this.decoratedRepository = repository;
    }

    @Override
    public Object get(UniqueIdMgd id) {
        return decoratedRepository.get(id);
    }

    @Override
    public List getAll() {
        return decoratedRepository.getAll();
    }

    @Override
    public void add(Object elem) {
        decoratedRepository.add(elem);
    }

    @Override
    public void remove(Object elem) {
        decoratedRepository.remove(elem);
    }

    @Override
    public void update(Object elem) {
        decoratedRepository.update(elem);
    }
}
