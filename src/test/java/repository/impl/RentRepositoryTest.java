package repository.impl;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import model.Address;
import model.Client;
import model.EQ.Equipment;
import model.Rent;
import model.UniqueId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.DataFaker;
import repository.RepositoryFactory;
import repository.RepositoryType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class RentRepositoryTest {

    static RentRepository rr;
    static RepositoryFactory rf;
    static EntityManagerFactory emf;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_DB");
        rf = new RepositoryFactory(emf);
        rr = (RentRepository) rf.getRepository(RepositoryType.RentRepository);
    }

    @Test
    void add_get_remove() {
        Address a = DataFaker.getAddress();
        Client c = DataFaker.getClient(a);
        Equipment e = DataFaker.getTrivet();

        Rent r = DataFaker.getRent(e, c, a);
        System.out.println(r);
        rr.add(r);
        UniqueId uid = r.getEntityId();
        
        Rent r1 = rr.get(uid);

        assertEquals(r, r1);
        rr.remove(r1);
        assertThrows(EntityNotFoundException.class, () -> {
            rr.get(uid);
        });
    }




    @Test
    void update_count() {
        EquipmentRepository er = (EquipmentRepository) rf.getRepository(RepositoryType.EquipmentRepository);
        Equipment e1 = DataFaker.getLens();
        Equipment e2 = DataFaker.getLighting();
        Equipment e3 = DataFaker.getMicrophone();
        Equipment e4 = DataFaker.getTrivet();

        er.add(e1);
        er.add(e2);
        er.add(e3);
        er.add(e4);

        boolean e1_ar = true;
        double e2_bail = 9999.;
        String e3_name = "_____";
        double e4_fdc = 99999.;

        e1.setArchive(e1_ar);
        e2.setBail(e2_bail);
        e3.setName(e3_name);
        e4.setFirstDayCost(e4_fdc);

        er.update(e1);
        er.update(e2);
        er.update(e3);
        er.update(e4);

        Equipment e1_ = er.get(e1.getEntityId());
        Equipment e2_ = er.get(e2.getEntityId());
        Equipment e3_ = er.get(e3.getEntityId());
        Equipment e4_ = er.get(e4.getEntityId());

        assertEquals(e1_.isArchive(), e1_ar);
        assertEquals(e1, e1_);
        assertEquals(e2_.getBail(), e2_bail);
        assertEquals(e2, e2_);
        assertEquals(e3_.getName(), e3_name);
        assertEquals(e3, e3_);
        assertEquals(e4_.getFirstDayCost(), e4_fdc);
        assertEquals(e4, e4_);

        long count = er.count();
        assertEquals(count, 4);
        er.remove(e1_);
        er.remove(e2_);
        er.remove(e3_);
        er.remove(e4_);
        count = er.count();
        assertEquals(count, 0);
    }

    @Test
    void count() {
        Long startingCount = rr.count();

        Address address1 = DataFaker.getAddress();
        Address address2 = DataFaker.getAddress();
        Address address3 = DataFaker.getAddress();
        Client client1 = DataFaker.getClient(address1);
        Client client2 = DataFaker.getClient(address2);
        Client client3 = DataFaker.getClient(address3);
        Equipment eq1 = DataFaker.getCamera();
        Equipment eq2 = DataFaker.getTrivet();
        Equipment eq3 = DataFaker.getMicrophone();

        Rent r1 = DataFaker.getRent(eq1, client1, address1);
        Rent r2 = DataFaker.getRent(eq2, client2, address2);
        Rent r3 = DataFaker.getRent(eq3, client3, address3);

        rr.add(r1);
        rr.add(r2);
        rr.add(r3);

        assertEquals(startingCount + 3, rr.count());

        rr.remove(r1);

        assertEquals(startingCount + 2, rr.count());
    }

    @Test
    void makeReservationTest() {

    }

    @Test
    void concurrentReservationTest() throws BrokenBarrierException, InterruptedException {
        Address address1 = DataFaker.getAddress();
        Client client1 = DataFaker.getClient(address1);
        Equipment eq1 = DataFaker.getCamera();
        Rent r1 = DataFaker.getRent(eq1, client1, address1);

        int amount = 5;
        List<RentRepository> rentRepositoryList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            rentRepositoryList.add((RentRepository) rf.getRepository(RepositoryType.RentRepository));
        }

        List<Rent> rents;

        AtomicInteger atomicInteger = new AtomicInteger();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(amount);
        List<Thread> threadList = new ArrayList<Thread>();

        for (int i = 0; i < amount; i++) {
            int finalI = i;
            threadList.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (BrokenBarrierException e) {
                        throw new RuntimeException(e);
                    }
                    rentRepositoryList.get(finalI).add(r1);
                    atomicInteger.getAndIncrement();
                }}));
        }

        threadList.forEach(Thread::start);
        cyclicBarrier.await();

        for (int i = 0; i < 5; i++) {
            System.out.println(rentRepositoryList.get(0).getAll());
        }



    }
}