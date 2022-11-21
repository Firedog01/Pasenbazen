package repository.cache;

import org.junit.jupiter.api.Test;
import repository.AbstractRepository;
import repository.Repository;
import repository.RepositoryFactory;
import repository.impl.RentRepository;

import static org.junit.jupiter.api.Assertions.*;

class RedisCacheRepositoryDecoratorTest {
    @Test
    void basicCheck() {
        AbstractRepository rentRepo = new RentRepository();
        Repository decoratedRentRepo = new RedisCacheRepositoryDecorator(rentRepo);
    }

}