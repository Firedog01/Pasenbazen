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
import java.util.concurrent.atomic.AtomicReference;

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
    void get() {

    }

    @Test
    void getAll() {
    }

    @Test
    void add() {
        Address a = new Address("City", "street", "streetNr");
        Client c = null;
        try {
            c = new Client("clientId", idType.DowodOsobisty, "firstName", "lastName", a);
        } catch (ClientException e) {
            fail();
        }
        cr.add(c);
        List<Client> cl = cr.getAll();
        assertEquals(cl.size(), 1);
        System.out.println(cl.get(0));
    }

    @Test
    void remove() {
//        Address a = new Address("City", "street", "streetNr");
//        Client c = null;
//        try {
//            c = new Client("clientId", idType.DowodOsobisty, "firstName", "lastName", a);
//        } catch (ClientException e) {
//            fail();
//        }
//        cr.add(c);


    }

    @Test
    void update() {
    }

    @Test
    void count() {
    }
}