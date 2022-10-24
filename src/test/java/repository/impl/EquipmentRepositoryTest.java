package repository.impl;

import model.EQ.Equipment;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.DataFaker;
import repository.RepositoryFactory;
import repository.RepositoryType;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EquipmentRepositoryTest {

    static EquipmentRepository er;
    static RepositoryFactory rf;

    @BeforeAll
    static void beforeAll() {
        rf = new RepositoryFactory();
        er = (EquipmentRepository) rf.getRepository(RepositoryType.EquipmentRepository);
    }

    @Test
    void add_get_remove() {
        Equipment e = DataFaker.getCamera();
//        System.out.println(e);
        er.add(e);
        UUID uuid = e.getUuid();
        Equipment e1 = er.get(e.getUuid());
        assertEquals(e, e1);
        er.remove(e1.getUuid());
        assertEquals(0, er.count());
    };

    @Test
    void update_remove() {
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

        List<Equipment> equipmentList = er.getAll();
        assertEquals(4, equipmentList.size());
        assertEquals(4, er.count());
        er.remove(e1_.getUuid());
        er.remove(e2_.getUuid());
        er.remove(e3_.getUuid());
        er.remove(e4_.getUuid());
        assertEquals(0, er.count());
    }

    @Test
    void count() {
        Equipment eq1 = DataFaker.getCamera();
        Equipment eq2 = DataFaker.getTrivet();
        Equipment eq3 = DataFaker.getMicrophone();

        er.add(eq1);
        er.add(eq2);
        er.add(eq3);

        assertEquals(er.count(), 3);

        er.remove(eq1.getUuid());

        assertEquals(er.count(), 2);

        er.remove(eq2.getUuid());
        er.remove(eq3.getUuid());

    }
}