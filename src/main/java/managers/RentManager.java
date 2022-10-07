package managers;

import model.Client;
import model.EQ.Equipment;
import model.Rent;
import predicates.Predicate;

import java.util.List;

public class RentManager {
    //TODO

    public Rent makeReservation(Client client, Equipment equipment, org.joda.time.LocalDateTime beginTime,
                                org.joda.time.LocalDateTime endTime) {
        return null;
    }

    public List<Rent> getClientRents(Client client) {
        return null;
    }

    public String checkForShipments() {
        return null;
    }

    public boolean isAvailable(Equipment equipment) {
        return false;
    }

    public org.joda.time.LocalDateTime whenAvailable(Equipment equipment) {
        return null;
    }

    public org.joda.time.LocalDateTime untilAvailable(Equipment equipment) {
        return null;
    }

    public String getEquipmentNextRents(Equipment equipment) {
        return null;
    }

    public void cancelReservation(Equipment equipment) {

    }

    public List<Rent> getEquipmentAllRents(Equipment equipment) {
        return null;
    }

    public void returnEquipment(boolean missing) { //Czy ta funkcja jest... kompletna?

    }

    public double checkClientBalance() { //Bez client?
        return 0.0;
    }

    public List<Rent> findRents(Predicate<Rent> predicate) {
        return null;
    }

    public List<Rent> findAllRents() {
        return null;
    }

    public Rent getRent(int id) {
        return null;
    }

}
