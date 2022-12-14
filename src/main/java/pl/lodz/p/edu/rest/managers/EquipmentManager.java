package pl.lodz.p.edu.rest.managers;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.rest.exception.ConflictException;
import pl.lodz.p.edu.rest.exception.IllegalModificationException;
import pl.lodz.p.edu.rest.exception.ObjectNotValidException;
import pl.lodz.p.edu.rest.model.DTO.EquipmentDTO;
import pl.lodz.p.edu.rest.model.Equipment;
import pl.lodz.p.edu.rest.repository.impl.EquipmentRepository;

import java.util.List;
import java.util.UUID;

@Transactional
public class EquipmentManager {

    @Inject
    private EquipmentRepository equipmentRepository;

    protected EquipmentManager() {
    }

    public void add(Equipment equipment) throws ObjectNotValidException {
//        try {
//            if (!equipment.verify()) {
//                throw new ObjectNotValidException("Equipment fields have illegal values");
//            }
//        } catch(NullPointerException e) {
//            throw new ObjectNotValidException("Equipment fields have illegal values");
//        }

        equipmentRepository.add(equipment);
    }

    public Equipment get(UUID uuid) {
        return equipmentRepository.get(uuid);
    }

    public List<Equipment> getAll() {
        return equipmentRepository.getAll();
    }

    public void update(UUID entityId, EquipmentDTO equipmentDTO) throws IllegalModificationException, ObjectNotValidException {
//        Equipment equipmentVerify = new Equipment(equipmentDTO);
//        try {
//            if (!equipmentVerify.verify()) {
//                throw new ObjectNotValidException("Equipment fields have illegal values");
//            }
//        } catch(NullPointerException e) {
//            throw new ObjectNotValidException("Equipment fields have illegal values");
//        }
        synchronized (equipmentRepository) {
            Equipment equipment = equipmentRepository.get(entityId);
            equipment.merge(equipmentDTO);
            equipmentRepository.update(equipment);
        }
    }

    public void remove(UUID uuid) throws ConflictException {
        try {
            if (!equipmentRepository.isEquipmentRented(uuid)) {
                equipmentRepository.remove(uuid);
            } else {
                throw new ConflictException("There exists unfinished rent for this equipment");
            }
        } catch (EntityNotFoundException ignored) {
        }
    }
}
