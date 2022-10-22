package repository.impl;

import exception.ClientException;
import jakarta.persistence.*;
import model.*;
import model.EQ.Equipment;
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

    @BeforeAll
    static void beforeAll() {
        rf = new RepositoryFactory();
        cr = (ClientRepository) rf.getRepository(RepositoryType.ClientRepository);
        ar = (AddressRepository) rf.getRepository(RepositoryType.AddressRepository);
    }

    @Test
    void add_get_remove() {
        Client c = DataFaker.getClient();
        System.out.println(c);
        cr.add(c);
        UUID uuid = c.getUuid();
        Client c1 = cr.get(c.getUuid());
        assertEquals(c, c1);
        cr.remove(c1.getUuid());
        assertEquals(0, cr.count());
    };

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
        cr.update(c1_a1.getUuid(), c1_a1);
        cr.update(c2_a1.getUuid(), c2_a1);
        cr.update(c3_a2.getUuid(), c3_a2);
        cr.update(c4_a3.getUuid(), c4_a3);

        Client c1_ = cr.get(c1_a1.getUuid());
        Client c2_ = cr.get(c2_a1.getUuid());
        Client c3_ = cr.get(c3_a2.getUuid());
        Client c4_ = cr.get(c4_a3.getUuid());

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
        cr.remove(c1_.getUuid());
        assertDoesNotThrow(() -> {
            ar.get(a1.getUuid());
        });

        cr.remove(c2_.getUuid());
        cr.remove(c3_.getUuid());
        cr.remove(c4_.getUuid());

        clientList = cr.getAll();
        assertEquals(0, clientList.size());
    }

    @Test
    void count() {
        int startingCount = cr.count();

        Address address1 = DataFaker.getAddress();
        Address address2 = DataFaker.getAddress();
        Address address3 = DataFaker.getAddress();
        Client client1 = DataFaker.getClient(address1);
        Client client2 = DataFaker.getClient(address2);
        Client client3 = DataFaker.getClient(address3);

        cr.add(client1);
        cr.add(client2);
        cr.add(client3);

        assertEquals(startingCount + 3, cr.count());

        cr.remove(client1.getUuid());

        assertEquals( startingCount + 2, cr.count());

        cr.remove(client2.getUuid());
        cr.remove(client3.getUuid());
    }


    @Test
    void getByClientIdTest() throws ClientException {
        Address a1 = DataFaker.getAddress();

        String clientId1 = "__1__";
        String clientId2 = "__2__";

        Client c1 = new Client(clientId1, idType.DowodOsobisty, "fname", "lname", a1);
        Client c2 = new Client(clientId1, idType.Passport, "fname", "lname", a1);
        Client c3 = new Client(clientId2, idType.DowodOsobisty, "fname", "lname", a1);

        Client c4 = new Client(clientId1, idType.DowodOsobisty, "fname", "lname", a1);

        cr.add(c1);
        cr.add(c2);
        cr.add(c3);


        Client c1_ = cr.getByClientId(clientId1, idType.DowodOsobisty);
        assertEquals(c1, c1_);
        Client c2_ = cr.getByClientId(clientId1, idType.Passport);
        assertEquals(c2, c2_);
        Client c3_ = cr.getByClientId(clientId2, idType.DowodOsobisty);
        assertEquals(c3, c3_);

        cr.remove(c1_.getUuid());
        cr.remove(c2_.getUuid());
        cr.remove(c3_.getUuid());
    }


}
