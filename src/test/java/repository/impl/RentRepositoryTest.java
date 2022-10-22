package repository.impl;

import model.Address;
import model.Client;
import model.EQ.Equipment;
import model.Rent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.DataFaker;
import repository.RepositoryFactory;
import repository.RepositoryType;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RentRepositoryTest {

    static RentRepository rr;
    static RepositoryFactory rf;

    @BeforeAll
    static void beforeAll() {
        rf = new RepositoryFactory();
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
        UUID uuid = r.getUuid();

        Rent r1 = rr.get(uuid);

        assertEquals(r, r1);
        rr.remove(r1.getUuid());
        assertEquals(0, rr.count());
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

        er.update(e1.getUuid(), e1);
        er.update(e2.getUuid(), e2);
        er.update(e3.getUuid(), e3);
        er.update(e4.getUuid(), e4);

        Equipment e1_ = er.get(e1.getUuid());
        Equipment e2_ = er.get(e2.getUuid());
        Equipment e3_ = er.get(e3.getUuid());
        Equipment e4_ = er.get(e4.getUuid());

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
        er.remove(e1_.getUuid());
        er.remove(e2_.getUuid());
        er.remove(e3_.getUuid());
        er.remove(e4_.getUuid());
        count = er.count();
        assertEquals(count, 0);
    }

    @Test
    void count() {
        int startingCount = rr.count();

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

        rr.remove(r1.getUuid());

        assertEquals(startingCount + 2, rr.count());
    }
}