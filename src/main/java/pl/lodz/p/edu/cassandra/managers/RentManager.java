package pl.lodz.p.edu.cassandra.managers;

import pl.lodz.p.edu.cassandra.model.Client;
import pl.lodz.p.edu.cassandra.model.EQ.Equipment;
import pl.lodz.p.edu.cassandra.model.Rent;
import pl.lodz.p.edu.cassandra.model.RentByClient;
import pl.lodz.p.edu.cassandra.model.RentByEquipment;
import pl.lodz.p.edu.cassandra.repository.impl.RentDao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public class RentManager {
    private final RentDao rentDao;

    public RentManager(RentDao rentDao) {
        this.rentDao = rentDao;
    }

    public Rent makeReservation(LocalDateTime beginTime, LocalDateTime endTime, Equipment equipment, Client client) {
        if (equipment.isMissing() || equipment.isArchive()) {
            return null;
        }
        if (client.isArchive()) {
            return null;
        }
        LocalDateTime now = LocalDateTime.now();
        if (beginTime.isBefore(now)) {
            return null;
        }
        if (beginTime.isAfter(endTime)) {
            return null;
        }

        boolean good = true;
        List<RentByEquipment> rentEquipmentList = rentDao.getByEquipment(equipment.getUuid()).all();

        System.out.println(rentEquipmentList);
        for(RentByEquipment r : rentEquipmentList) {
            System.out.println(r);
        }

        for (int i = 0; i < rentEquipmentList.size(); i++) {
            RentByEquipment curRent = rentEquipmentList.get(i);

            LocalDateTime beginTimeDB = LocalDateTime.parse(curRent.getBeginTime());
            LocalDateTime endTimeDB = LocalDateTime.parse(curRent.getEndTime());
            // +----- old rent -----+
            //         +----- new rent -----+
            if (beginTime.isBefore(endTimeDB) && beginTime.isAfter(beginTimeDB)) {
                good = false;
            }
            //         +----- old rent -----+
            // +----- new rent -----+
            if (endTime.isAfter(beginTimeDB) && endTime.isBefore(endTimeDB)) {
                good = false;
            }
            // +----- old rent -----+
            //    +-- new rent --+
            if (beginTime.isAfter(beginTimeDB) && beginTime.isBefore(endTimeDB)) {
                good = false;
            }
            //    +-- old rent --+
            // +----- new rent -----+
            if (beginTime.isBefore(beginTimeDB) && endTime.isAfter(endTimeDB)) {
                good = false;
            }
        }

        if (good) {
            RentByClient rentByClient = new RentByClient(beginTime, endTime, equipment.getUuid(), client.getUuid());
            RentByEquipment rentByEquipment = new RentByEquipment(beginTime, endTime, equipment.getUuid(), client.getUuid());
            UUID uuid = UUID.randomUUID();
            rentByClient.setRentUuid(uuid);
            rentByEquipment.setRentUuid(uuid);
            rentDao.add(rentByClient, rentByEquipment);

            Rent rent = new Rent(beginTime, endTime, equipment, client);
            rent.setRentByClient(rentByClient);
            rent.setRentByEquipment(rentByEquipment);


            return rent;
        } else {
            return null;
        }
    }

    public List<RentByClient> getAllClientRents(UUID uuid) {
        return rentDao.getByClient(uuid).all();
    }

    public List<RentByEquipment> getAllEquipmentRents(UUID uuid) {
        return rentDao.getByEquipment(uuid).all();
    }

    public void updateClientRent(RentByClient rent) {
        rentDao.updateRentClient(rent);
    }
    public void updateEquipmentRent(RentByEquipment rent) {
        rentDao.updateRentEquipment(rent);
    }

    public Rent updateRentClient(Rent rent, Client client) {
        Rent updatedRent = new Rent(rent);
        updatedRent.setClient(client);
        RentByClient byClient = updatedRent.toRentByClient();
        RentByEquipment byEquipment = updatedRent.toRentByEquipment();
        RentByClient byClientOld = rent.toRentByClient();
        rentDao.updateRentEquipment(updatedRent.toRentByEquipment());
        rentDao.addRentByClient(updatedRent.toRentByClient());
        rentDao.deleteClientRents(rent.toRentByClient());
        return updatedRent;
    }

    public Rent updateRentEquipment(Rent rent, Equipment equipment) {
        Rent updatedRent = new Rent(rent);
        updatedRent.setEquipment(equipment);
        RentByClient byClient = updatedRent.toRentByClient();
        RentByEquipment byEquipment = updatedRent.toRentByEquipment();
        RentByEquipment byClientOld = rent.toRentByEquipment();
        rentDao.updateRentClient(updatedRent.toRentByClient());
        rentDao.addRentByEquipment(updatedRent.toRentByEquipment());
        rentDao.deleteEquipmentRents(rent.toRentByEquipment());
        return updatedRent;
    }

    public boolean delete(Rent rent) {
        return rentDao.deleteEquipmentRents(rent.getRentByEquipment())
                && rentDao.deleteClientRents(rent.getRentByClient());
    }

//    public void shipEquipment(Rent rent) {
//        rent.setShipped(true);
//        rentRepository.update(rent);
//    }
//
//    public boolean isAvailable(Equipment equipment) {
//        return Objects.equals(whenAvailable(equipment), LocalDateTime.now());
//    }
//
//
//    public LocalDateTime whenAvailable(Equipment equipment) {
//        // todo
//        if (equipment.isArchive() || equipment.isMissing()) {
//            return null;
//        }
//        LocalDateTime when = LocalDateTime.now();
//        List<Rent> equipmentRents = rentRepository.getRentByEq(equipment);
////        List<Rent> equipmentRents = equipment.getEquipmentRents();
//
//        for (Rent rent :
//                equipmentRents) {
//            if (when.isAfter(rent.getBeginTime()) && when.isBefore(rent.getEndTime())) {
//                when = rent.getEndTime();
//            }
//        }
//        return when;
//    }
//
//    public LocalDateTime untilAvailable(Equipment equipment) {
//        // todo
//        LocalDateTime until = null;
//
//        if (equipment.isArchive() || equipment.isMissing()) {
//            return null;
//        }
//
//        LocalDateTime when = whenAvailable(equipment);
//        List<Rent> equipmentRents = rentRepository.getRentByEq(equipment);
////        List<Rent> equipmentRents = equipment.getEquipmentRents();
//
//        for (Rent rent :
//                equipmentRents) {
//            if (when.isBefore(rent.getBeginTime())) {
//                until = rent.getEndTime();
//            }
//        }
//        return until;
//    }
//
//    public void cancelReservation(Rent rent) {
//        rentRepository.remove(rent);
//    }
//
//    public void returnEquipment(Rent rent, boolean missing) {
//        rent.setEqReturned(true);
//        rent.getEquipment().setMissing(missing);
//        rentRepository.update(rent);
//    }
//
//    public double checkClientBalance(Client client) {
//        // todo
//        List<Rent> rentList = getClientRents(client);
//        double balance = 0.0;
//        for (Rent rent :
//                rentList) {
//            balance += rent.getRentCost();
//        }
//        return balance;
//    }
//
//    public List<Rent> getAllRents() {
//        return rentRepository.getAll();
//    }
//
//    public Rent getRent(UUID id) {
//        try {
//            return rentRepository.get(id);
//        } catch(EntityNotFoundException ex) {
//            return null;
//        }
//    }
}