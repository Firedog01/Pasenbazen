package repository.cache;

import org.junit.jupiter.api.Test;
import repository.AbstractRepository;
import repository.Repository;
import repository.impl.RentRepository;

class RentCacheRepositoryDecoratorTest {
    @Test
    void basicCheck() {
        AbstractRepository rentRepo = new RentRepository();
        Repository decoratedRentRepo = new RentCacheRepositoryDecorator(rentRepo);
    }

}