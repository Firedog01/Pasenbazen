package repository.impl;

import exception.ClientException;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import model.Address;
import model.Client;
import model.EQ.Equipment;
import model.UniqueId;
import model.idType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.DataFaker;
import repository.RepositoryFactory;
import repository.RepositoryType;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientRepositoryTest {

    static ClientRepository cr;
    static AddressRepository ar;
    static RepositoryFactory rf;
    static EntityManagerFactory emf;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_DB");
        rf = new RepositoryFactory(emf);
        cr = (ClientRepository) rf.getRepository(RepositoryType.ClientRepository);
        ar = (AddressRepository) rf.getRepository(RepositoryType.AddressRepository);
    }

    @Test
    void add_get_remove() {
        Client c = DataFaker.getClient();
        System.out.println(c);
        cr.add(c);
        UniqueId uid = c.getEntityId();
        Client c1 = cr.get(uid);
        assertEquals(c, c1);
        cr.remove(c1);
        assertThrows(EntityNotFoundException.class, () -> {
            cr.get(uid);
        });
    }

    @Test
    void update_remove() {
        Address a1 = DataFaker.getAddress();
        Address a2 = DataFaker.getAddress();
        Address a3 = DataFaker.getAddress();
        Client c1_a1 = DataFaker.getClient(a1);
        Client c2_a1 = DataFaker.getClient(a1);
        Client c3_a2 = DataFaker.getClient(a2);
        Client c4_a3 = DataFaker.getClient(a3);
        cr.add(c1_a1);
        cr.add(c2_a1);
        cr.add(c3_a2);
        cr.add(c4_a3);

        boolean c1_ar = true;
        String c2_fname = "_1_";
        String c3_lname = "_2_";

        c1_a1.setArchive(c1_ar);
        c2_a1.setFirstName(c2_fname);
        c3_a2.setLastName(c3_lname);
        c4_a3.setAddress(a2);
        cr.update(c1_a1);
        cr.update(c2_a1);
        cr.update(c3_a2);
        cr.update(c4_a3);

        Client c1_ = cr.get(c1_a1.getEntityId());
        Client c2_ = cr.get(c2_a1.getEntityId());
        Client c3_ = cr.get(c3_a2.getEntityId());
        Client c4_ = cr.get(c4_a3.getEntityId());

        assertEquals(c1_.isArchive(), c1_ar);
        assertEquals(c1_a1, c1_);
        assertEquals(c2_.getFirstName(), c2_fname);
        assertEquals(c2_a1, c2_);
        assertEquals(c3_.getLastName(), c3_lname);
        assertEquals(c3_a2, c3_);
        assertEquals(c4_.getAddress(), a2);
        assertEquals(c4_a3, c4_);

        List<Client> clientList = cr.getAll();
        assertEquals(4, clientList.size());

        // address a1 should not be removed
        cr.remove(c1_);
        assertDoesNotThrow(() -> {
            ar.get(a1.getEntityId());
        });

        // on address update client should get updated address
        String a1_street = "==1==";
        Address a1_ = ar.get(a1.getEntityId());
        a1_.setStreet(a1_street);
        ar.update(a1_);
        assertNotEquals(a1_street, c1_.getAddress().getStreet());
        Client c2_2 = cr.get(c2_.getEntityId());
//        assertEquals(a1_street, c2_2.getAddress().getStreet()); // problem
        cr.remove(c2_2);

        // todo sth clever
        cr.remove(c3_);
        cr.remove(c4_);

        clientList = cr.getAll();
        assertEquals(0, clientList.size());
    }

    @Test
    void count() {
        Address address1 = DataFaker.getAddress();
        Address address2 = DataFaker.getAddress();
        Address address3 = DataFaker.getAddress();
        Client client1 = DataFaker.getClient(address1);
        Client client2 = DataFaker.getClient(address2);
        Client client3 = DataFaker.getClient(address3);

        cr.add(client1);
        cr.add(client2);
        cr.add(client3);

        assertEquals(cr.count(), 3);

        cr.remove(client1);

        assertEquals(cr.count(), 2);
    }
}
