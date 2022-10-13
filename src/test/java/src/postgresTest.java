package src;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class postgresTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeAll
    static void beforeAll() {
//        emf = Persistence.createEntityManagerFactory("POSTGRES_DB");
    }

    @AfterAll
    static void afterAll() {
        if(emf != null) {
            emf.close();
        }
    }

    @Test
    void create() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_DB");
    }
}