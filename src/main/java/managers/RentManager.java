package managers;

import model.Client;
import model.Address;
import model.EQ.Equipment;
import model.Rent;
import org.joda.time.LocalDateTime;
import repository.impl.RentRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class RentManager {
    private RentRepository rentRepository;

    public RentManager(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    public Rent makeReservation(Client client, Equipment equipment, Address address,
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
//        List<Rent> rentEquipmentList = rentRepository.getEquipmentRents(equipment);
        List<Rent> rentEquipmentList = rentRepository.getRentByEq(equipment); //Isn't it the same? ^

        System.out.println(rentEquipmentList);
        for (Rent r : rentEquipmentList) {
            System.out.println(r);
        }

        for (int i = 0; i < rentEquipmentList.size(); i++) {
            Rent curRent = rentEquipmentList.get(i);

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
            Rent rent = new Rent(beginTime, endTime, equipment, client, address);
            rentRepository.add(rent);
            return rent;
        } else {
            return null;
        }
    }

    public List<Rent> getClientRents(Client client) {
        return rentRepository.getRentByClient(client);
    }

    public void shipEquipment(Rent rent) {
        rent.setShipped(true);
        rentRepository.update(rent.getUuid(), rent);
    }

    public boolean isAvailable(Equipment equipment) {
        return Objects.equals(whenAvailable(equipment), LocalDateTime.now());
    }


    public LocalDateTime whenAvailable(Equipment equipment) {
        // todo
        if (equipment.isArchive() || equipment.isMissing()) {
            return null;
        }
        LocalDateTime when = LocalDateTime.now();
        List<Rent> equipmentRents = rentRepository.getRentByEq(equipment);
//        List<Rent> equipmentRents = equipment.getEquipmentRents();

        for (Rent rent :
                equipmentRents) {
            if (when.isAfter(rent.getBeginTime()) && when.isBefore(rent.getEndTime())) {
                when = rent.getEndTime();
            }
        }
        return when;
    }

    public LocalDateTime untilAvailable(Equipment equipment) {
        // todo
        LocalDateTime until = null;

        if (equipment.isArchive() || equipment.isMissing()) {
            return null;
        }

        LocalDateTime when = whenAvailable(equipment);
        List<Rent> equipmentRents = rentRepository.getRentByEq(equipment);
//        List<Rent> equipmentRents = equipment.getEquipmentRents();

        for (Rent rent :
                equipmentRents) {
            if (when.isBefore(rent.getBeginTime())) {
                until = rent.getEndTime();
            }
        }
        return until;
    }

    public void cancelReservation(Rent rent) {
        rentRepository.remove(rent.getUuid());
    } //FIXME send UUID or get UUID from rent obj?

    public void returnEquipment(Rent rent, boolean missing) {
        rent.setEqReturned(true);
        rent.getEquipment().setMissing(missing);
        rentRepository.update(rent.getUuid(), rent);
    }

    public double checkClientBalance(Client client) {
        List<Rent> rentList = getClientRents(client);
        double balance = 0.0;
        for (Rent rent :
                rentList) {
            balance += rent.getRentCost();
        }
        return balance;
    }

    public List<Rent> getAllRents() {
        return rentRepository.getAll();
    }

    public Rent getRent(UUID uuid) { //FIXME UUID or rent obj
        return rentRepository.get(uuid);
    }
}
