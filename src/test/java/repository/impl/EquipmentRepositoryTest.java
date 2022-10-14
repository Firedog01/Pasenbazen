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

class EquipmentRepositoryTest {

    static EquipmentRepository er;
    static RepositoryFactory rf;
    static EntityManagerFactory emf;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_DB");
        rf = new RepositoryFactory(emf);
        er = (EquipmentRepository) rf.getRepository(RepositoryType.ClientRepository);
    }

    @Test
    void add_get_remove() {
        Equipment e = DataFaker.getCamera();
        System.out.println(e);
        er.add(e);
        UniqueId uid = e.getEntityId();
        Equipment e1 = er.get(uid);
        assertEquals(e, e1);
        er.remove(e);
        assertThrows(EntityNotFoundException.class, () -> {
            er.get(uid);
        });
    }

    @Test
    void update_count() {
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
}