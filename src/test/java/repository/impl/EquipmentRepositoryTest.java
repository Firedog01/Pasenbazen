package repository.impl;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import mgd.ClientMgd;
import mgd.DataFakerMgd;
import mgd.EQ.CameraMgd;
import mgd.EQ.EquipmentMgd;
import mgd.EQ.LensMgd;
import mgd.EQ.TrivetMgd;
import model.EQ.Equipment;
import model.UniqueId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import model.DataFaker;
import repository.RepositoryFactory;
import repository.RepositoryType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EquipmentRepositoryTest {

    static EquipmentRepository equipmentRepository = new EquipmentRepository();

    static CameraMgd camera  = DataFakerMgd.getCameraMgd();
    static LensMgd   lens    = DataFakerMgd.getLensMgd();
    static TrivetMgd trivet  = DataFakerMgd.getTrivetMgd();

    @BeforeAll
    static void beforeAll() {
        equipmentRepository.addCamera(camera);
        equipmentRepository.addLens(lens);
        equipmentRepository.addTrivet(trivet);
    }

    @Test
    void checkEquipmentType() {

        EquipmentMgd should_be_camera = equipmentRepository.getById(camera.getEntityId());
        assertDoesNotThrow(() -> {
            CameraMgd camera = (CameraMgd) should_be_camera;
        });
    }

    @Test
    void test() {

    }
}