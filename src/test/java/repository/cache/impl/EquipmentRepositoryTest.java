package repository.cache.impl;

import mgd.DataFakerMgd;
import mgd.EQ.CameraMgd;
import mgd.EQ.EquipmentMgd;
import mgd.EQ.LensMgd;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import repository.impl.EquipmentRepository;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
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
        EquipmentMgd should_be_camera = equipmentRepository.get(camera.getEntityId());
        assertDoesNotThrow(() -> {
            CameraMgd camera = (CameraMgd) should_be_camera;
        });
    }

    @Test
    void updateCameraField() {
        String uniqueVal = "__1__";
        equipmentRepository.updateByKey(camera.getEntityId(), "resolution", uniqueVal);
        camera = equipmentRepository.get(camera.getEntityId());
    }

    @Test
    void updateLensFocalLength() {
        String uniqueVal = "21.37";
        LensMgd updatedLens = equipmentRepository.updateLensFocalLength((LensMgd) lens, uniqueVal);
        assertEquals(updatedLens, lens);
        assertEquals(updatedLens, equipmentRepository.get(updatedLens.getEntityId()));
    }


    @Test
    void updateWholeTest() {
        lens.setName("__--1--__");
        lens.setFirstDayCost(9999999999.);
        lens.setDescription("__--3--__");
        equipmentRepository.update(lens);
        LensMgd updatedLens = (LensMgd) equipmentRepository.get(lens.getEntityId());
        assertEquals(lens, updatedLens);
    }

    @Test
    void deleteTest() {
        CameraMgd otherCamera = DataFakerMgd.getCameraMgd();
        equipmentRepository.add(otherCamera);
        assertEquals(otherCamera, equipmentRepository.get(otherCamera.getEntityId()));
        equipmentRepository.remove(otherCamera);
        assertNull(equipmentRepository.get(otherCamera.getEntityId()));
    }
}