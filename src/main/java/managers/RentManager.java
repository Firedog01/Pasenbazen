package managers;

import jakarta.persistence.EntityNotFoundException;
import mgd.AddressMgd;
import mgd.ClientMgd;
import mgd.EQ.EquipmentMgd;
import mgd.RentMgd;
import mgd.UniqueIdMgd;
import org.joda.time.LocalDateTime;
import repository.impl.RentRepository;

import java.util.ArrayList;
import java.util.List;


public class RentManager {
    private final RentRepository rentRepository;

    public RentManager(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    public RentMgd makeReservation(ClientMgd client, EquipmentMgd equipment, AddressMgd address,
                                   LocalDateTime beginTime, LocalDateTime endTime) {
        if (equipment.isMissing() || equipment.isArchive()) {
            return null;
        }
        if (client.isArchive()) {
            return null;
        }
        LocalDateTime now = LocalDateTime.now();
        if (beginTime.isEqual(now) || beginTime.isBefore(now)) {
            return null;
        }
        if (beginTime.isAfter(endTime)) {
            return null;
        }

        boolean good = true;
//        List<Rent> rentEquipmentList = equipment.getEquipmentRents();
        List<RentMgd> rentEquipmentList = equipmentRepisitory.getEquipmentRents(equipment);

        System.out.println(rentEquipmentList);
        for (RentMgd r : rentEquipmentList) {
            System.out.println(r);
        }

        for (int i = 0; i < rentEquipmentList.size(); i++) {
            RentMgd curRent = rentEquipmentList.get(i);

            // +----- old rent -----+
            //         +----- new rent -----+
            if (beginTime.isBefore(curRent.getEndTime()) && beginTime.isAfter(curRent.getBeginTime())) {
                good = false;
            }
            //         +----- old rent -----+
            // +----- new rent -----+
            if (endTime.isAfter(curRent.getBeginTime()) && endTime.isBefore(curRent.getEndTime())) {
                good = false;
            }
            // +----- old rent -----+
            //    +-- new rent --+
            if (beginTime.isAfter(curRent.getBeginTime()) && beginTime.isBefore(curRent.getEndTime())) {
                good = false;
            }
            //    +-- old rent --+
            // +----- new rent -----+
            if (beginTime.isBefore(curRent.getBeginTime()) && endTime.isAfter(curRent.getEndTime())) {
                good = false;
            }
        }

        if (good) {
            RentMgd rent = new RentMgd(new UniqueIdMgd(), beginTime, endTime, equipment, client, address);
            rentRepository.add(rent);
            return rent;
        } else {
            return null;
        }
    }

//    public List<Rent> getClientRents(Client client) {
//        return rentRepository.getRentByClient(client);
//    }

//FIXME
//    public void shipEquipment(RentMgd rent) {
//        rent.setShipped(true);
//        rentRepository.update(rent.getEntityId());
//    }
//    public boolean isAvailable(EquipmentMgd equipmentMgd) {
//        return Objects.equals(whenAvailable(equipment), LocalDateTime.now());
//    }

`

    public LocalDateTime whenAvailable(EquipmentMgd equipment) {
        // todo
        if (equipment.isArchive() || equipment.isMissing()) {
            return null;
        }
        LocalDateTime when = LocalDateTime.now();

        List<RentMgd> temp = new ArrayList<>();
//      FIXME
//        List<RentMgd> equipmentRents = rentRepository.getRentByEq(equipment);
//        List<Rent> equipmentRents = equipment.getEquipmentRents();

        for (RentMgd rent :
                temp) {
            if (when.isAfter(rent.getBeginTime()) && when.isBefore(rent.getEndTime())) {
                when = rent.getEndTime();
            }
        }
        return when;
    }

    public LocalDateTime untilAvailable(EquipmentMgd equipment) {
        // todo
        LocalDateTime until = null;

        if (equipment.isArchive() || equipment.isMissing()) {
            return null;
        }

        LocalDateTime when = whenAvailable(equipment);
//      FIXME: 02.11.2022
//        List<Rent> equipmentRents = rentRepository.getRentByEq(equipment);
//        List<Rent> equipmentRents = equipment.getEquipmentRents();
        List<RentMgd> temp = new ArrayList<>();
        for (RentMgd rent :
                temp) {
            if (when.isBefore(rent.getBeginTime())) {
                until = rent.getEndTime();
            }
        }
        return until;
    }

    public void cancelReservation(RentMgd rent) {
        rentRepository.deleteOne(rent);
    }

//   FIXME: 02.11.2022
//    public void returnEquipment(RentMgd rent, boolean missing) {
//        rent.setEqReturned(true);
//        rent.getEquipment().setMissing(missing);
//        rentRepository.update(rent.getEntityId());
//    }

// FIXME: 02.11.2022
//    public double checkClientBalance(ClientMgd client) {
//        List<RentMgd> rentList = getClientRents(client);
//        double balance = 0.0;
//        for (RentMgd rent :
//                rentList) {
//            balance += rent.getRentCost();
//        }
//        return balance;
//    }

    public List<RentMgd> getAllRents() {
        return rentRepository.getAllRents();
    }

    public RentMgd getRent(UniqueIdMgd id) {
        try {
            return rentRepository.getById(id);
        } catch (EntityNotFoundException ex) {
            return null;
        }
    }
}
