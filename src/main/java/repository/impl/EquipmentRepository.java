package repository.impl;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.Client;
import model.Client_;
import model.EQ.Equipment;
import model.UniqueId;
import repository.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public class EquipmentRepository implements Repository<Equipment> {

    private Map<UUID, Equipment> equipmentMap;

    private EntityManager em;

    public EquipmentRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Equipment get(UUID uuid) {
        if (equipmentMap.containsKey(uuid)) {
            return equipmentMap.get(uuid);
        }
        return null; //FIXME not sure
    }

    @Override
    public List<Equipment> getAll() {
        return equipmentMap.values().stream().toList(); //Somehow idk
    }

    @Override
    public boolean add(UUID uuid, Equipment elem) {
        if (!equipmentMap.containsKey(uuid)) {
            equipmentMap.put(uuid, elem);
            return true;
        }
        return false; // FIXME Same eq cannot be added twice?
    }

    @Override
    public boolean remove(UUID key) {
        if (equipmentMap.containsKey(key)) {
            equipmentMap.remove(key);  //FIXME returns boolean or Eq?
            return true;
        }
        return false;
    }

    @Override
    public boolean update(UUID uuid, Equipment elem) {
        if (equipmentMap.containsKey(uuid)) {
            equipmentMap.put(uuid, elem); //FIXME bool or EQ from here?
            return true;
        }
        return false;
    }

    @Override
    public int count() {
        return equipmentMap.size();
    }
}