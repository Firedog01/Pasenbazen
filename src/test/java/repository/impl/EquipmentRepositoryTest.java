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
import model.EQ.Lens;
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

    static EquipmentMgd camera  = DataFakerMgd.getCameraMgd();
    static EquipmentMgd lens    = DataFakerMgd.getLensMgd();
    static EquipmentMgd trivet  = DataFakerMgd.getTrivetMgd();

    @BeforeAll
    static void beforeAll() {
        equipmentRepository.add(camera);
        equipmentRepository.add(lens);
        equipmentRepository.add(trivet);
    }

    @Test
    void checkEquipmentType() {
        EquipmentMgd should_be_camera = equipmentRepository.getById(camera.getEntityId());
        assertDoesNotThrow(() -> {
            CameraMgd camera = (CameraMgd) should_be_camera;
        });
    }

    @Test
    void updateCameraField() {
        String uniqueVal = "__1__";
        equipmentRepository.updateByKey(camera.getEntityId(), "resolution", uniqueVal);
        camera = equipmentRepository.getById(camera.getEntityId());
    }

    @Test
    void updateTrivetField() {
        String uniqueVal = "21.37";
        equipmentRepository.updateByKey(camera.getEntityId(), "weight", uniqueVal);
        trivet = equipmentRepository.getById(trivet.getEntityId());
    }

    @Test
    void updateLensField() {
        String uniqueVal = "1337_idk";
        equipmentRepository.updateByKey(camera.getEntityId(), "focal_length", uniqueVal);
        lens = equipmentRepository.getById(lens.getEntityId());
    }

    @Test
    void updateWholeTest() {
        lens.setName("__--1--__");
        lens.setFirstDayCost(9999999999.);
        lens.setDescription("__--3--__");
        equipmentRepository.updateWholeEquipment(lens);
        LensMgd updatedLens = (LensMgd) equipmentRepository.getById(lens.getEntityId());
        assertEquals(lens, updatedLens);
    }
}