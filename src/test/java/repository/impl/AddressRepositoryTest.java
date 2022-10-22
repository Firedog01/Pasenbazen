package repository.impl;
import model.Address;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.DataFaker;
import repository.RepositoryFactory;
import repository.RepositoryType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddressRepositoryTest {
    static AddressRepository ar;
    static RepositoryFactory rf;

    @BeforeAll
    static void beforeAll() {
        rf = new RepositoryFactory();
        ar = (AddressRepository) rf.getRepository(RepositoryType.AddressRepository);
    }

    @Test
    void update_remove() {
        Address a1 = DataFaker.getAddress();

        ar.add(a1);

        String a1_city = "__1__";

        a1.setCity(a1_city);

        ar.update(a1.getUuid(), a1);

        Address a1_ = ar.get(a1.getUuid());

        assertEquals(a1_.getCity(), a1_city);
        assertEquals(a1, a1_);

        List<Address> addressList = ar.getAll();
        assertEquals(1, addressList.size());
        assertEquals(1, ar.count());
        ar.remove(a1_.getUuid());
        assertEquals(0, ar.count());
    }
}