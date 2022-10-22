package repository.impl;

import jakarta.persistence.*;
import model.*;
import model.EQ.Equipment;
import repository.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public class RentRepository implements Repository<Rent> {

    private Map<UUID, Rent> rentMap;

//    private EntityManager em;
//
//    public RentRepository(EntityManager em) {
//        this.em = em;
//    }


    public RentRepository(Map<UUID, Rent> rentMap) {
        this.rentMap = rentMap;
    }

    public RentRepository() {
    }

    @Override
    public Rent get(UUID uuid) {
        if (rentMap.containsKey(uuid)) {
            return rentMap.get(uuid);
        }
        return null; //FIXME
    }

    @Override
    public List<Rent> getAll() {
        return rentMap.values().stream().toList();
    }

//FIXME ???
//    public List<Rent> getEquipmentRents(Equipment e) {
//        if(e.getId() == null) {
//            return new ArrayList<Rent>();
//        }
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Rent> cq = cb.createQuery(Rent.class);
//        Root<Rent> rent = cq.from(Rent.class);
//        cq.select(rent);
//        cq.where(cb.equal(rent.get(Rent_.EQUIPMENT), e));
//        EntityTransaction et = em.getTransaction();
//        et.begin();
//        List<Rent> rents = em.createQuery(cq).
//                setLockMode(LockModeType.OPTIMISTIC).
//                getResultList();
//        et.commit();
//        return rents;
//    }

    @Override
    public boolean add(Rent elem) {
        if (!rentMap.containsKey(elem.getUuid())) {
            rentMap.put(elem.getUuid(), elem);
            return true;
        }
        return false; //FIXME Same client cannot be added twice?
    }

    @Override
    public boolean remove(UUID key) {
        if (rentMap.containsKey(key)) {
            rentMap.remove(key);  //FIXME returns boolean or Client?
            return true;
        }
        return false;
    }

    @Override
    public boolean update(UUID uuid, Rent elem) {
        if (rentMap.containsKey(uuid)) {
            rentMap.put(uuid, elem); //FIXME bool or Rent from here?
            return true;
        }
        return false;
    }

    @Override
    public int count() {
        return rentMap.size();
    }

    public List<Rent> getRentByClient(Client clientP) {
        List<Rent> result = rentMap.values().stream()
                .filter(rent -> clientP.getUuid().equals(rent.getClient().getUuid())).toList();
        //Bruh
        return result;
    }


    public List<Rent> getRentByEq(Equipment eq) {
        List<Rent> result = rentMap.values().stream()
                .filter(rent -> eq.getUuid().equals(rent.getEquipment().getUuid())).toList();
        return result;
    }

//    public List<Rent> getEquipmentRents(Equipment eq) {
//
//    }


}
