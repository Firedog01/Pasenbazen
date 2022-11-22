package managers;

import mgd.AddressMgd;
import mgd.ClientMgd;
import mgd.EQ.EquipmentMgd;
import mgd.RentMgd;
import mgd.UniqueIdMgd;
import java.time.LocalDateTime;
import repository.impl.RentRepository;

import java.util.List;
import java.util.Objects;


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

        List<RentMgd> rentEquipmentList = rentRepository.getEquipmentRents(equipment);


        for (int i = 0; i < rentEquipmentList.size(); i++) {
            RentMgd curRent = rentEquipmentList.get(i);

            // +----- old rent -----+
            //         +----- new rent -----+
            if (beginTime.isBefore(curRent.getEndTime()) &&
                    beginTime.isAfter(curRent.getEndTime())) {
                return null;
            }
            //         +----- old rent -----+
            // +----- new rent -----+
            if (endTime.isAfter(curRent.getBeginTime()) && endTime.isBefore(curRent.getEndTime())) {
                return null;
            }
            // +----- old rent -----+
            //    +-- new rent --+
            if (beginTime.isAfter(curRent.getBeginTime()) && beginTime.isBefore(curRent.getEndTime())) {
                return null;
            }
            //    +-- old rent --+
            // +----- new rent -----+
            if (beginTime.isBefore(curRent.getBeginTime()) && endTime.isAfter(curRent.getEndTime())) {
                return null;
            }
        }

        RentMgd rent = new RentMgd(new UniqueIdMgd(), beginTime, endTime, equipment, client, address);
        rentRepository.add(rent);
        return rent;
    }

    public void shipEquipment(RentMgd rent) {
        rentRepository.updateShipped(rent, true);
    }

    public boolean isAvailable(EquipmentMgd equipment) {
        return Objects.equals(whenAvailable(equipment), LocalDateTime.now());
    }

    public LocalDateTime whenAvailable(EquipmentMgd equipment) {
        if (equipment.isArchive() || equipment.isMissing()) {
            return null;
        }
        LocalDateTime when = LocalDateTime.now();

        List<RentMgd> equipmentRents = rentRepository.getEquipmentRents(equipment);

        for (RentMgd rent : equipmentRents) {
            if (when.isAfter(rent.getBeginTime()) && when.isBefore(rent.getEndTime())) {
                when = rent.getEndTime();
            }
        }
        return when;
    }

    public LocalDateTime untilAvailable(EquipmentMgd equipment) {
        LocalDateTime until = null;

        if (equipment.isArchive() || equipment.isMissing()) {
            return null;
        }

        LocalDateTime whenAvailable = whenAvailable(equipment);
        List<RentMgd> equipmentRents = rentRepository.getEquipmentRents(equipment);
        for (RentMgd rent : equipmentRents) {
            if (whenAvailable.isBefore(rent.getBeginTime())) {
                until = rent.getEndTime();
            }
        }
        return until;
    }

    public void cancelReservation(RentMgd rent) {
        rentRepository.remove(rent);
    }

    public void returnEquipment(RentMgd rent, boolean missing) {
        rentRepository.updateMissingReturned(rent, missing, true);
    }

    public List<RentMgd> getAllRents() {
        return rentRepository.getAll();
    }

    public RentMgd getRent(UniqueIdMgd id) {
        return rentRepository.get(id);
    }
}
