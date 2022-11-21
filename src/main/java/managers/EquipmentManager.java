package managers;

import jakarta.persistence.EntityNotFoundException;
import mgd.EQ.CameraMgd;
import mgd.EQ.EquipmentMgd;
import mgd.EQ.LensMgd;
import mgd.EQ.TrivetMgd;
import mgd.UniqueIdMgd;

import repository.impl.EquipmentRepository;

import java.util.ArrayList;
import java.util.List;

public class EquipmentManager {
    EquipmentRepository equipmentRepository;

    public EquipmentManager(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public void unregisterEquipment(EquipmentMgd equipmentMgd) {
        equipmentRepository.remove(equipmentMgd);
    }

    public List<EquipmentMgd> getAllEquipment() {
        return equipmentRepository.getAll();
    }

    public List<EquipmentMgd> getAllAvailableEquipment() {
        List<EquipmentMgd> all = getAllEquipment();
        List<EquipmentMgd> available = new ArrayList<>();
        for (EquipmentMgd e : all) {
            if(!(e.isArchive() || e.isMissing())) {
                available.add(e);
            }
        }
        return available;
    }

    public EquipmentMgd registerCamera(UniqueIdMgd idMgd, String name, double bail, double fDayCost, double nDayCost,
                                        String resolution, boolean archive, String desc, boolean missing) {

        CameraMgd camera = new CameraMgd(idMgd, name, bail, fDayCost, nDayCost, archive, desc, missing, resolution);
        equipmentRepository.add(camera);
        return camera;
    }

    public EquipmentMgd registerTrivet(UniqueIdMgd idMgd, String name, double bail, double fDayCost, double nDayCost,
                                       double weight, boolean archive, String desc, boolean missing) {
        TrivetMgd trivet = new TrivetMgd(idMgd, name, bail, fDayCost, nDayCost, archive, desc, missing, weight);
        equipmentRepository.add(trivet);
        return trivet;
    }

    public EquipmentMgd registerLens(UniqueIdMgd idMgd, String name, double bail, double fDayCost, double nDayCost,
                                     String focalLength, boolean archive, String desc, boolean missing) {
        LensMgd lens = new LensMgd(idMgd, name, bail, fDayCost, nDayCost, archive, desc, missing, focalLength);
        equipmentRepository.add(lens);
        return lens;
    }


    public EquipmentMgd getEquipment(UniqueIdMgd id) {
        try {
            return equipmentRepository.get(id);
        } catch(EntityNotFoundException ex) {
            return null;
        }
    }
}
