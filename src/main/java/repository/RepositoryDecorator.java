package repository;

import model.UniqueId;

import java.util.List;

public abstract class RepositoryDecorator implements Repository {
    protected Repository decoratedRepository;

    public RepositoryDecorator(Repository repository) {
        this.decoratedRepository = repository;
    }

    @Override
    public Object get(UniqueId id) {
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

    @Override
    public Long count() {
        return decoratedRepository.count();
    }
}
