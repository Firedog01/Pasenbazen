package pl.lodz.p.edu.cassandra.managers;

import jakarta.persistence.EntityNotFoundException;
import pl.lodz.p.edu.cassandra.exception.EquipmentException;
import pl.lodz.p.edu.cassandra.model.EQ.Camera;
import pl.lodz.p.edu.cassandra.model.EQ.Equipment;
import pl.lodz.p.edu.cassandra.model.EQ.Lens;
import pl.lodz.p.edu.cassandra.model.EQ.Trivet;
import pl.lodz.p.edu.cassandra.repository.impl.EquipmentRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EquipmentManager {
    EquipmentRepository equipmentRepository;

    public EquipmentManager(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public void unregisterEquipment(Equipment equipment) {
        Equipment e = equipmentRepository.get(equipment.getUuid());
        equipmentRepository.remove(e);
    }

    public List<Equipment> getAllEquipment() {
        return equipmentRepository.getAll();
    }

    public List<Equipment> getAllAvailableEquipment() {
        List<Equipment> all = getAllEquipment();
        List<Equipment> available = new ArrayList<>();
        for (Equipment e : all) {
            if(!(e.isArchive() || e.isMissing())) {
                available.add(e);
            }
        }
        return available;
    }

    public Equipment registerCamera(double fDayCost, double nDayCost, double bail, String name, String resolution) throws EquipmentException {
        Camera camera = new Camera(fDayCost, nDayCost, bail, name, resolution);
        equipmentRepository.add(camera);
        return camera;
    }

    public Equipment registerTrivet(double fDayCost, double nDayCost, double bail, String name, double weight) throws EquipmentException {
        Trivet trivet = new Trivet(fDayCost, nDayCost, bail, name, weight);
        equipmentRepository.add(trivet);
        return trivet;
    }

    public Equipment registerLens(double fDayCost, double nDayCost, double bail, String name, String length) throws EquipmentException {
        Lens lens = new Lens(fDayCost, nDayCost, bail, name, length);
        equipmentRepository.add(lens);
        return lens;
    }

    public Equipment getEquipment(UUID id) {
        try {
            return equipmentRepository.get(id);
        } catch(EntityNotFoundException ex) {
            return null;
        }
    }
}