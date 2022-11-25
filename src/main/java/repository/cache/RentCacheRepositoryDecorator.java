package repository.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import mgd.RentMgd;
import mgd.UniqueIdMgd;
import repository.AbstractRepository;
import repository.Repository;
import repository.RepositoryDecorator;
import repository.impl.RentRepository;

import java.util.List;

public class RentCacheRepositoryDecorator extends RepositoryDecorator<RentMgd> {

    private RentCache cache;
    private RentRepository repository;

    public RentCacheRepositoryDecorator(RentRepository repository) {
        super(repository);
        cache = new RentCache();
        this.repository = repository;
    }

    public void clearCache() {
        cache.deleteAll();
    }

    public RentMgd updateMissingReturned(RentMgd rent, boolean missing, boolean eqReturned) {
        return repository.updateMissingReturned(rent, missing, eqReturned);
    }

    public RentMgd updateShipped(RentMgd rent, boolean shipped) {
        return repository.updateShipped(rent, shipped);
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
            try {
                return cache.get(id);
            } catch (JsonProcessingException e) {
                System.out.println(e.getMessage());
            }
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
