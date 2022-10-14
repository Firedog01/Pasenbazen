package repository.impl;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import model.Address;
import model.EQ.Equipment;
import model.UniqueId;
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
    static EntityManagerFactory emf;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_DB");
        rf = new RepositoryFactory(emf);
        ar = (AddressRepository) rf.getRepository(RepositoryType.AddressRepository);
    }

    @Test
    void add_get_remove() {
        Address a = DataFaker.getAddress();
        System.out.println(a);
        ar.add(a);
        UniqueId uid = a.getEntityId();
        Address a1 = ar.get(uid);
        assertEquals(a, a1);
        ar.remove(a1);
        assertThrows(EntityNotFoundException.class, () -> {
            ar.get(uid);
        });
    }

    @Test
    void update_remove() {
        Address a1 = DataFaker.getAddress();

        ar.add(a1);

        String a1_city = "__1__";

        a1.setCity(a1_city);

        ar.update(a1);

        Address a1_ = ar.get(a1.getEntityId());

        assertEquals(a1_.getCity(), a1_city);
        assertEquals(a1, a1_);

        List<Address> addressList = ar.getAll();
        assertEquals(1, addressList.size());
        assertEquals(1, ar.count());
        ar.remove(a1_);
        assertEquals(0, ar.count());
    }
}