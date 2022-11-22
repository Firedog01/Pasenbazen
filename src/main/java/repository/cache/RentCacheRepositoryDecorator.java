package repository.cache;

import mgd.RentMgd;
import mgd.UniqueIdMgd;
import repository.AbstractRepository;
import repository.RepositoryDecorator;

import java.util.List;

public class RentCacheRepositoryDecorator extends RepositoryDecorator<RentMgd> {

    private RentCache cache;

    public RentCacheRepositoryDecorator(AbstractRepository<RentMgd> repository) {
        super(repository);
        cache = new RentCache();
    }

    @Override
    public void add(RentMgd elem) {
        if(RentCache.isHealthy()) {
            cache.save(elem);
        }
        decoratedRepository.add(elem);
    }

    @Override
    public RentMgd get(UniqueIdMgd id) {
        if(RentCache.isHealthy()) {
            return cache.get(id);
        }
        return decoratedRepository.get(id);
    }

    @Override
    public List<RentMgd> getAll() {
        return decoratedRepository.getAll();
    }

    @Override
    public void update(RentMgd elem) {
        if(RentCache.isHealthy()) {
            cache.save(elem);
        }
        decoratedRepository.update(elem);
    }

    @Override
    public void remove(RentMgd elem) {
        if(RentCache.isHealthy()) {
            cache.delete(elem);
        }
        decoratedRepository.remove(elem);
    }
}
