package repository.impl;
import model.Address;
import model.Client;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.DataFaker;
import repository.RepositoryFactory;
import repository.RepositoryType;

import java.util.List;
import java.util.UUID;

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
    void add_get_remove() {
        Address a = DataFaker.getAddress();
        System.out.println(a);
        ar.add(a);
        Address a1 = ar.get(a.getUuid());
        assertEquals(a, a1);
        ar.remove(a1.getUuid());
        assertEquals(ar.count(), 0);
    };

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