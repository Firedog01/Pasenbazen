package repository.cache;

import mgd.UniqueIdMgd;
import model.UniqueId;
import repository.AbstractRepository;
import repository.RepositoryDecorator;

import java.util.List;

public class RedisCacheRepositoryDecorator extends RepositoryDecorator {

    private RedisCache cache;





    public RedisCacheRepositoryDecorator(AbstractRepository repository) {
        super(repository);
        cache = new RedisCache();

    }

    @Override
    public Object get(UniqueIdMgd id) {
        return super.get(id);
    }

    @Override
    public List getAll() {
        return super.getAll();
    }

    @Override
    public void add(Object elem) {
        super.add(elem);
    }

    @Override
    public void remove(Object elem) {
        super.remove(elem);
    }

    @Override
    public void update(Object elem) {
        super.update(elem);
    }
}
