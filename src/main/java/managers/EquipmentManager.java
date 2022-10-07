package managers;

import model.EQ.Equipment;
import predicates.Predicate;
import repository.impl.EquipmentRepository;

import java.util.List;

public class EquipmentManager {
    EquipmentRepository equipmentRepository;

    public EquipmentManager(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public void UnregisterEquipment(Equipment equipment) {

    }

    public Equipment findEquipment(Predicate<Equipment> predicate) {
        return null;
    }

    public List<Equipment> findAllEquipment() {
        return null;
    }

    public Equipment registerCamera(double fDayCost, double nDayCost, double bail, String name, String resolution) {
        return null;
    }

    public Equipment registerTrivet(double fDayCost, double nDayCost, double bail, String name, double weight) {
        return null;
    }

    public Equipment registerLens(double fDayCost, double nDayCost, double bail, String name, String length) {
        return null;
    }

    public Equipment registerMicrophone(double fDayCost, double nDayCost, double bail, String name, String sensitivity) {
        return null;
    }

    public Equipment registerLighting(double fDayCost, double nDayCost, double bail, String name, String brightness) {
        return null;
    }

    public Equipment getEquipment(Equipment equipment) {
        return null;
    }

}
