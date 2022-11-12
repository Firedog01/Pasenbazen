package pl.lodz.p.edu.rest.managers;

import jakarta.inject.Inject;

import pl.lodz.p.edu.rest.model.EQ.Equipment;
import pl.lodz.p.edu.rest.model.Rent;
import pl.lodz.p.edu.rest.repository.RepositoryFactory;
import pl.lodz.p.edu.rest.repository.RepositoryType;
import pl.lodz.p.edu.rest.repository.impl.RentRepository;

import java.util.List;
import java.util.UUID;


public class RentManager {

    private final RentRepository rentRepository;

    protected RentManager() {
        rentRepository = (RentRepository) RepositoryFactory
                .getRepository(RepositoryType.RentRepository);
    }

    public List<Rent> getRentByEq(Equipment equipment) {
        return rentRepository.getRentByEq(equipment);
    }
    public List<Rent> getRentsByClient(UUID uuid) {

        return rentRepository.getRentByClient(get(uuid).getClient());
    }

    public boolean add(Rent rent) {
        return rentRepository.add(rent);
    }

    public void update(Rent rent) {
        rentRepository.update(rent);
    }
    public boolean remove(UUID uuid) {
        return rentRepository.remove(uuid);
    }

    public Rent get(UUID uuid) {
        return rentRepository.get(uuid);
    }

    public List<Rent> getAll() {
        return rentRepository.getAll();
    }
}

//    private final static RentRepository rentRepository =
//            (RentRepository) RepositoryFactory.getRepository(RepositoryType.RentRepository);
//    private final static ClientRepository clientRepository =
//            (ClientRepository) RepositoryFactory.getRepository(RepositoryType.ClientRepository);
//    private final static EquipmentRepository equipmentRepository =
//            (EquipmentRepository) RepositoryFactory.getRepository(RepositoryType.EquipmentRepository);

//    public double checkClientBalance(ClientUser ClientUser) {
//        List<Rent> rentList = getClientRents(ClientUser.getUuid());
//        double balance = 0.0;
//        for (Rent rent :
//                rentList) {
//            balance += rent.getRentCost();
//        }
//        return balance;
//    }



/*
public class RentManager {
    private RentRepository rentRepository;

    public RentManager(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    public Rent makeReservation(ClientUser ClientUser, Equipment equipment, Address address,
                                LocalDateTime beginTime, LocalDateTime endTime) {
        if (equipment.isMissing() || equipment.isArchive()) {
            return null;
        }
        if (ClientUser.isArchive()) {
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
        List<Rent> rentEquipmentList = rentRepository.getEquipmentRents(equipment);

        System.out.println(rentEquipmentList);
        for(Rent r : rentEquipmentList) {
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
            Rent rent = new Rent(beginTime, endTime, equipment, ClientUser, address);
            rentRepository.add(rent);
            return rent;
        } else {
            return null;
        }
    }

    public List<Rent> getClientRents(ClientUser ClientUser) {
        return rentRepository.getRentByClient(ClientUser);
    }

    public void shipEquipment(Rent rent) {
        rent.setShipped(true);
        rentRepository.update(rent);
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
        rentRepository.remove(rent);
    }

    public void returnEquipment(Rent rent, boolean missing) {
        rent.setEqReturned(true);
        rent.getEquipment().setMissing(missing);
        rentRepository.update(rent);
    }

    public double checkClientBalance(ClientUser ClientUser) {
        // todo
        List<Rent> rentList = getClientRents(ClientUser);
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

    public Rent getRent(UniqueId id) {
        try {
            return rentRepository.get(id);
        } catch(EntityNotFoundException ex) {
            return null;
        }
    }
}
 */
