package managers;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Address;
import model.Client;
import model.EQ.Camera;
import model.Rent;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import model.DataFaker;
import repository.RepositoryFactory;
import repository.RepositoryType;
import repository.impl.EquipmentRepository;
import repository.impl.RentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class RentManagerTest {

    static RentRepository rr;
    static RepositoryFactory rf;
    static EntityManagerFactory emf;
    static RentManager rm;


    LocalDateTime t0;
    LocalDateTime t1;
    LocalDateTime t2;
    LocalDateTime t3;
    LocalDateTime t4;
    LocalDateTime t5;
    // t0 = now
    // t0 < t1 < t2 < t3 < t4 < t5

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_DB");
        rf = new RepositoryFactory(emf);
        rr = (RentRepository) rf.getRepository(RepositoryType.RentRepository);
        rm = new RentManager(rr);
    }


    RentManagerTest() {
        t0 = LocalDateTime.now();
        t1 = t0.plusDays(1);
        t2 = t0.plusDays(2);
        t3 = t0.plusDays(3);
        t4 = t0.plusDays(4);
        t5 = t0.plusDays(5);
    }
}