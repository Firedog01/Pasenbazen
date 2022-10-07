package managers;

import model.Address;
import model.Client;
import model.EQ.Equipment;
import model.Rent;
import org.joda.time.LocalDateTime;
import predicates.Predicate;
import repository.impl.RentRepository;

import java.util.List;

public class RentManager {
    private RentRepository rentRepository;
    //TODO


    public RentManager(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    public Rent makeReservation(Client client, Equipment equipment, Address address, org.joda.time.LocalDateTime beginTime,
                                org.joda.time.LocalDateTime endTime) {
        Rent rent = new Rent(rentRepository.size(), beginTime, endTime, equipment, client, address);
        rentRepository.add(rent);
        return rent;
    }


    public List<Rent> getClientRents(Client client) {
        return rentRepository.findBy(x -> x.getClient() == client && !x.getClient().isArchive());
    }

    public String checkForShipments() {
        org.joda.time.LocalDateTime nowTime = LocalDateTime.now();
//        List<Rent> rentList = findRents(x -> x)
        return null; //FIXME
    }

    public boolean isAvailable(Equipment equipment) {
        if (whenAvailable(equipment) == LocalDateTime.now()) {
            return true;
        } else {
            return false;
        }
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

    public double checkClientBalance(Client client) {
        List<Rent> rentList = getClientRents(client);
        double balance = 0.0;
        for (Rent rent:
             rentList) {
            balance += rent.getRentCost();
        }
        return balance;
    }

    public List<Rent> findRents(Predicate<Rent> predicate) {
//        return rentRepository.findBy(predicate);  //FIXME tutaj jest ten sam problem z predykatem
        return null;
    }

    public List<Rent> findAllRents() {
        return rentRepository.findAll();
    }

    public Rent getRent(int id) {
        if (id < rentRepository.size()) {
            return rentRepository.get(id);
        }
        return null; //todo?
    }

}
