package managers;

import model.EQ.*;

import repository.impl.EquipmentRepository;

import java.util.List;
import java.util.function.Predicate;

public class EquipmentManager {
//    EquipmentRepository equipmentRepository;
//
//    public EquipmentManager(EquipmentRepository equipmentRepository) {
//        this.equipmentRepository = equipmentRepository;
//    }
//
//    public void UnregisterEquipment(Equipment equipment) {
//        Predicate<Equipment> equipmentPredicate = (
//                x -> x == equipment
//        );
//
//        if (!equipmentRepository.findBy(equipmentPredicate).isEmpty()) {
//            equipmentRepository.findBy(equipmentPredicate).get(0).setArchive(true);
//        }
//
//    }
//
//    public List<Equipment> findEquipment(Predicate<Equipment> predicate) {
//        Predicate<Equipment> equipmentPredicate = (
//                x -> !x.isArchive() && !x.isMissing() && predicate.test(x)
//        );
//        return equipmentRepository.findBy(equipmentPredicate);
//    }
//
//    public List<Equipment> findAllEquipment() {
//        return equipmentRepository.findAll();
//    }
//
//    public Equipment registerCamera(double fDayCost, double nDayCost, double bail, String name, String resolution) {
//        Camera camera = new Camera(fDayCost, nDayCost, bail, name, equipmentRepository.size(), resolution);
//        equipmentRepository.add(camera);
//        return camera;
//    }
//
//    public Equipment registerTrivet(double fDayCost, double nDayCost, double bail, String name, double weight) {
//        Trivet trivet = new Trivet(fDayCost, nDayCost, bail, name, equipmentRepository.size(), weight);
//        equipmentRepository.add(trivet);
//        return trivet;
//    }
//
//    public Equipment registerLens(double fDayCost, double nDayCost, double bail, String name, String length) {
//        Lens lens = new Lens(fDayCost, nDayCost, bail, name, equipmentRepository.size(), length);
//        equipmentRepository.add(lens);
//        return lens;
//    }
//
//    public Equipment registerMicrophone(double fDayCost, double nDayCost, double bail, String name, String sensitivity) {
//        Microphone microphone = new Microphone(fDayCost, nDayCost, bail, name, equipmentRepository.size(), sensitivity);
//        equipmentRepository.add(microphone);
//        return microphone;
//    }
//
//    public Equipment registerLighting(double fDayCost, double nDayCost, double bail, String name, String brightness) {
//        Lighting lighting = new Lighting(fDayCost, nDayCost, bail, name, equipmentRepository.size(), brightness);
//        equipmentRepository.add(lighting);
//        return lighting;
//    }
//
//    public Equipment getEquipment(int id) {
//        Predicate<Equipment> equipmentPredicate = (
//                x -> x.getId() == id
//        );
//        if (equipmentRepository.findBy(equipmentPredicate).isEmpty()) {
//            return null;
//        } else {
//            return equipmentRepository.findBy(equipmentPredicate).get(0);
//        }
//    }
//
//    public List<Equipment> getAllEquipment() {
//        Predicate<Equipment> equipmentPredicate = (
//                x -> !x.isArchive() && !x.isMissing()
//                );
//        return equipmentRepository.findBy(equipmentPredicate);
//    }

}
