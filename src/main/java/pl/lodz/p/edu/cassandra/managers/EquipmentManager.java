package pl.lodz.p.edu.cassandra.managers;

import jakarta.persistence.EntityNotFoundException;
import pl.lodz.p.edu.cassandra.exception.EquipmentException;
import pl.lodz.p.edu.cassandra.model.EQ.Equipment;
import pl.lodz.p.edu.cassandra.repository.impl.EquipmentDao;

import java.util.UUID;

public class EquipmentManager {
    private final EquipmentDao equipmentDao;

    public EquipmentManager(EquipmentDao equipmentDao) {
        this.equipmentDao = equipmentDao;
    }

    public boolean unregisterEquipment(UUID uuid) {
        Equipment equipment = new Equipment();
        equipment.setArchive(true);
        return equipmentDao.archive(equipment, uuid);
    }

    public void registerEquipment(Equipment equipment) throws EquipmentException {
        equipmentDao.add(equipment);
    }

    public Equipment getEquipment(UUID equipmentUuid) {
        try {
            return equipmentDao.get(equipmentUuid);
        } catch (EntityNotFoundException | EquipmentException ex) {
            return null;
        }
    }

    public boolean updateEquipment(Equipment equipment) {
        return equipmentDao.update(equipment);
    }

    public boolean deleteEquipment(UUID equipmentUuid) {
        return equipmentDao.delete(equipmentUuid);
    }

//    public List<Equipment> getAllEquipment() {
//        return equipmentRepository.getAll();
//    }

//    public List<Equipment> getAllAvailableEquipment() {
//        List<Equipment> all = getAllEquipment();
//        List<Equipment> available = new ArrayList<>();
//        for (Equipment e : all) {
//            if(!(e.isArchive() || e.isMissing())) {
//                available.add(e);
//            }
//        }
//        return available;
//    }

//    public Equipment registerCamera(double fDayCost, double nDayCost, double bail, String name, String resolution) throws EquipmentException {
//        Camera camera = new Camera(fDayCost, nDayCost, bail, name, resolution);
//        equipmentRepository.add(camera);
//        return camera;
//    }
//
//    public Equipment registerTrivet(double fDayCost, double nDayCost, double bail, String name, double weight) throws EquipmentException {
//        Trivet trivet = new Trivet(fDayCost, nDayCost, bail, name, weight);
//        equipmentRepository.add(trivet);
//        return trivet;
//    }
//
//    public Equipment registerLens(double fDayCost, double nDayCost, double bail, String name, String length) throws EquipmentException {
//        Lens lens = new Lens(fDayCost, nDayCost, bail, name, length);
//        equipmentRepository.add(lens);
//        return lens;
//    }
}