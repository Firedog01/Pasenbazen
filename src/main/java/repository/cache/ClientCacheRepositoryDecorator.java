package repository.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import mgd.ClientMgd;
import mgd.RentMgd;
import mgd.UniqueIdMgd;
import repository.RepositoryDecorator;
import repository.impl.ClientRepository;

import java.util.List;

public class ClientCacheRepositoryDecorator extends RepositoryDecorator<ClientMgd> {

    private ClientCache cache;
    private ClientRepository repository;

    public ClientCacheRepositoryDecorator(ClientRepository repository) {
        super(repository);
        cache = new ClientCache();
        this.repository = repository;
    }

    public void clearCache() {
        cache.deleteAll();
    }

    @Override
    public void add(ClientMgd elem) {
        if(ClientCache.isHealthy()) {
            cache.save(elem);
        }
        decoratedRepository.add(elem);
    }

    @Override
    public ClientMgd get(UniqueIdMgd id) {
        if(ClientCache.isHealthy()) {
            try {
                return cache.get(id);
            } catch (JsonProcessingException e) {
                System.out.println(e.getMessage());
            }
        }
        return decoratedRepository.get(id);
    }

    @Override
    public List<ClientMgd> getAll() {
        return decoratedRepository.getAll();
    }

    @Override
    public void update(ClientMgd elem) {
        if(ClientCache.isHealthy()) {
            cache.save(elem);
        }
        decoratedRepository.update(elem);
    }

    @Override
    public void remove(ClientMgd elem) {
        if(ClientCache.isHealthy()) {
            cache.delete(elem);
        }
        decoratedRepository.remove(elem);
    }
}
