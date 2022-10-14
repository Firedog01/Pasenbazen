package repository.impl;

import exception.ClientException;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Address;
import model.Client;
import model.idType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.RepositoryFactory;
import repository.RepositoryType;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientRepositoryTest {

    static ClientRepository cr;
    static RepositoryFactory rf;
    static EntityManagerFactory emf;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_DB");
        rf = new RepositoryFactory(emf);
        cr = (ClientRepository) rf.getRepository(RepositoryType.ClientRepository);
    }

    @Test
    void add() {
        List<Client> cl1 = cr.getAll();
        System.out.println("client list size " + cl1.size());
        for(Client c : cl1) {
            System.out.println(c);
        }

        Address a = new Address("City", "street", "streetNr");
        Client c = null;
        try {
            c = new Client("clientId", idType.DowodOsobisty, "firstName", "lastName", a);
        } catch (ClientException e) {
            fail();
        }
        System.out.println(c);
        cr.add(c);

        Address a1 = new Address("cityRem", "streetRem", "streetNrRem");
        Client c1 = null;
        try {
            c1 = new Client("clientIdRem", idType.Passport, "firstNameRem", "lastNameRem", a1);
        } catch (ClientException e) {
            fail();
        }
        System.out.println(c1);
        cr.add(c1);

        List<Client> cl = cr.getAll();
        assertEquals(cl.size(), 3);
        System.out.println(cl.get(0));
    }

    @Test
    void remove() {
        List<Client> cl1 = cr.getAll();
        System.out.println("client list size " + cl1.size());
        for(Client c : cl1) {
            System.out.println(c);
        }

        Address a = new Address("cityRem", "streetRem", "streetNrRem");
        Client c = null;
        try {
            c = new Client("clientIdRem", idType.DowodOsobisty, "firstNameRem", "lastNameRem", a);
        } catch (ClientException e) {
            fail();
        }
        cr.add(c);

        List<Client> cl = cr.getAll();
        assertEquals(cl.size(), 1);

        cr.remove(c);

        cl = cr.getAll();
        assertEquals(cl.size(), 0);
    }

    @Test
    void update() {
        List<Client> cl1 = cr.getAll();
        System.out.println("client list size " + cl1.size());
        for(Client c : cl1) {
            System.out.println(c);
        }

        Address address1 = new Address("cityUpd", "streetUpd", "streetNrUpd");
        Client client1 = null;
        try {
            client1 = new Client("clientIdUpd", idType.DowodOsobisty, "firstNameUpd",
                    "lastNameUpd", address1);
        } catch (ClientException e) {
            fail();
        }
        cr.add(client1);
        Address address2 = new Address("city1", "street1", "streetNr1");
        Client client2 = null;
        try {
            client2 = new Client("clientIdUpd", idType.DowodOsobisty, "firstNameUpd",
                    "test", address2);
        } catch (ClientException e) {
            fail();
        }

        cr.update(client2);

        List<Client> cl = cr.getAll();
        assertEquals(cl.size(), 1);
    }

    @Test
    void getGetAll() {

        Address address1 = new Address("cityGet1", "streetGet1", "streetNrGet1");
        Client client1 = null;
        try {
            client1 = new Client("clientIdGet1", idType.DowodOsobisty, "firstNameGet1",
                    "lastNameGet1", address1);
        } catch (ClientException e) {
            fail();
        }

        Address address2 = new Address("cityGet2", "streetGet2", "streetNrGet2");
        Client client2 = null;
        try {
            client2 = new Client("clientIdGet2", idType.DowodOsobisty, "firstNameGet2",
                    "lastNameGet2", address2);
        } catch (ClientException e) {
            fail();
        }

        cr.add(client1);
        cr.add(client2);

        UUID uuid1 = client1.getEntityId().getUniqueID();
        UUID uuid2 = client2.getEntityId().getUniqueID();

        Client getClient1 = cr.get(uuid1);
        Client getClient2 = cr.get(uuid2);

        assertSame(client1, getClient1);
        assertSame(client2, getClient2);

        assertNotSame(getClient1, getClient2);
        assertNotSame(getClient1, client2);
        assertNotSame(client1, getClient2);
    }
}