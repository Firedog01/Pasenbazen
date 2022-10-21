package repository.impl;

import jakarta.persistence.*;
import model.*;
import model.EQ.Equipment;
import repository.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public class RentRepository implements Repository<Rent> {

    private Map<UUID, Rent> rentList;

    private EntityManager em;

    public RentRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Rent get(UUID uuid) {
        if (rentList.containsKey(uuid)) {
            return rentList.get(uuid);
        }
        return null; //FIXME
    }

    @Override
    public List<Rent> getAll() {
        return rentList.values().stream().toList();
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
    public boolean add(UUID uuid, Rent elem) {
        if (!rentList.containsKey(uuid)) {
            rentList.put(uuid, elem);
            return true;
        }
        return false; //FIXME Same client cannot be added twice?
    }

    @Override
    public boolean remove(UUID key) {
        if (rentList.containsKey(key)) {
            rentList.remove(key);  //FIXME returns boolean or Client?
            return true;
        }
        return false;
    }

    @Override
    public boolean update(UUID uuid, Rent elem) {
        if (rentList.containsKey(uuid)) {
            rentList.put(uuid, elem); //FIXME bool or Rent from here?
            return true;
        }
        return false;
    }

    @Override
    public int count() {
        return rentList.size();
    }
/*
    public List<Rent> getRentByClient(Client clientP) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rent> cq = cb.createQuery(Rent.class);
        Root<Rent> rent = cq.from(Rent.class);

        cq.select(rent);
        cq.where(cb.equal(rent.get(Rent_.CLIENT), clientP));

        TypedQuery<Rent> q = em.createQuery(cq).setLockMode(LockModeType.OPTIMISTIC);
        List<Rent> rents = q.getResultList();

        if(rents.isEmpty()) {
            throw new EntityNotFoundException("Rent not found for client: " + clientP);
        }
        return rents;
    }
*/
    /*
    public List<Rent> getRentByEq(Equipment equipment) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rent> cq = cb.createQuery(Rent.class);
        Root<Rent> rent = cq.from(Rent.class);

        cq.select(rent);
        cq.where(cb.equal(rent.get(Rent_.EQUIPMENT), equipment));

        TypedQuery<Rent> q = em.createQuery(cq).setLockMode(LockModeType.OPTIMISTIC);
        List<Rent> rents = q.getResultList();

        if(rents.isEmpty()) {
            throw new EntityNotFoundException("Rent not found for equipment: " + equipment);
        }
        return rents;
    }
*/

}
