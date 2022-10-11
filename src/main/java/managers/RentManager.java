package managers;

import model.Client;
import model.EQ.Address;
import model.EQ.Equipment;
import model.Rent;
import org.joda.time.LocalDateTime;
import repository.impl.RentRepository;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;


public class RentManager {
    private RentRepository rentRepository;

    public RentManager(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    public Rent makeReservation(Client client, Equipment equipment, Address address, LocalDateTime beginTime,
                                LocalDateTime endTime) {

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
        List<Rent> rentEquipmentList = getEquipmentRents(equipment);


        for (int i = 0; i < rentEquipmentList.size(); i++) {
            if (beginTime.isAfter(rentEquipmentList.get(i).getBeginTime()) &&
                    beginTime.isBefore(rentEquipmentList.get(i).getEndTime())) {
                good = false;
            }
            if (endTime.isAfter(rentEquipmentList.get(i).getBeginTime()) &&
                    endTime.isBefore(rentEquipmentList.get(i).getEndTime())) {
                good = false;
            }
            if (beginTime.isBefore(rentEquipmentList.get(i).getBeginTime()) &&
                    endTime.isAfter(rentEquipmentList.get(i).getEndTime())) {
                good = false;
            }

        }

        if (good) {
            Rent rent = new Rent(rentRepository.size(), beginTime, endTime, equipment, client, address);
            rentRepository.add(rent);
            return rent;
        } else {
            return null;
        }
    }

    public RentManager() {

    }

    public List<Rent> getClientRents(Client client) {
        Predicate<Rent> rentPredicate = (
                x -> x.getClient() == client && !x.getClient().isArchive()
                );

        return rentRepository.findBy(rentPredicate);
    }

    public void shipEquipment(Rent rent) {
        rent.setShipped(true);
    }

    public String checkForShipments() {
        LocalDateTime nowTime = LocalDateTime.now();
        StringBuilder retInfo = new StringBuilder();
        Predicate<Rent> rentPredicate = (
                x -> x.getBeginTime().isAfter(nowTime) || !x.isShipped()
        );
        List<Rent> allEqRents = findRents(rentPredicate);

        for (Rent rent:
                allEqRents) {
            retInfo.append(rent.getRentInfo()).append("\n");
        }
        return retInfo.toString();

    }

    public boolean isAvailable(Equipment equipment) {
        return Objects.equals(whenAvailable(equipment), LocalDateTime.now());
    }


    public LocalDateTime whenAvailable(Equipment equipment) {
        if (equipment.isArchive() || equipment.isMissing()) {
            return null;
        }
        LocalDateTime when = LocalDateTime.now();
        List<Rent> equipmentRents = getEquipmentRents(equipment);

        for (Rent rent :
                equipmentRents) {
            if (when.isAfter(rent.getBeginTime()) && when.isBefore(rent.getEndTime())) {
                when = rent.getEndTime();
            }
        }
        return when;
    }

    public LocalDateTime untilAvailable(Equipment equipment) {
        LocalDateTime until = null;

        if (equipment.isArchive() || equipment.isMissing()) {
            return null;
        }

        LocalDateTime when = whenAvailable(equipment);
        List<Rent> equipmentRents = getEquipmentRents(equipment);

        for (Rent rent :
                equipmentRents) {
            if (when.isBefore(rent.getBeginTime())) {
                until = rent.getEndTime();
            }
        } //FIXME Is there a mistake?
        return until;
    }

    public void cancelReservation(Rent rent) {
        rentRepository.remove(rent);
    }

    public List<Rent> getEquipmentRents(Equipment equipment) {

        Predicate<Rent> rentPredicate = (
                x -> x.getEquipment() == equipment && !x.getEquipment().isMissing() && !x.getEquipment().isArchive()
        );
        return findRents(rentPredicate);
    }

    public String getEquipmentNextRents(Equipment equipment) {

        StringBuilder retInfo = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        Predicate<Rent>  rentPredicate = (
                x -> x.getEquipment() == equipment && x.getBeginTime().isAfter(now)
        );

        List<Rent> allEqRents = findRents(rentPredicate);

        for (Rent rent:
             allEqRents) {
            retInfo.append(rent.getRentInfo()).append("\n");
        }
        return retInfo.toString();
    }

    public String getEquipmentAllRents(Equipment equipment) {

        StringBuilder retInfo = new StringBuilder();
        Predicate<Rent>  rentPredicate = (
                x -> x.getEquipment() == equipment
        );

        List<Rent> allEqRents = findRents(rentPredicate);

        for (Rent rent:
                allEqRents) {
            retInfo.append(rent.getRentInfo()).append("\n");
        }
        return retInfo.toString();
    }

    public void returnEquipment(Rent rent, boolean missing) {
        rent.setEqReturned(true);
        rent.getEquipment().setMissing(missing);
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

    public List<Rent> findRents(Predicate<Rent> predicate) {
        return rentRepository.findBy(predicate);
    }

    public List<Rent> findAllRents() {
        return rentRepository.findAll();
    }

    public Rent getRent(int id) {
        for (int i = 0; i < rentRepository.size(); i++) {
            if (rentRepository.get(i).getRent_id() == id) {
                return rentRepository.get(i);
            }
        }
        return null;
    }
}
