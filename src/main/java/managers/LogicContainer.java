package managers;

import exception.ClientException;
import model.Address;
import static model.idType.DowodOsobisty;

import model.Client;
import model.EQ.Equipment;
import model.Rent;
import org.joda.time.LocalDateTime;
import repository.impl.ClientRepository;
import repository.impl.EquipmentRepository;
import repository.impl.RentRepository;

import java.util.ArrayList;
import java.util.List;


public class LogicContainer {

    private ClientManager clientManager;
    private RentManager rentManager;
    private EquipmentManager equipmentManager;


    public ClientManager getClientManager() {
        return clientManager;
    }

    public LogicContainer(ClientManager clientManager, RentManager rentManager, EquipmentManager equipmentManager) {
        this.clientManager = clientManager;
        this.rentManager = rentManager;
        this.equipmentManager = equipmentManager;
    }

    public RentManager getRentManager() {
        return rentManager;
    }

    public EquipmentManager getEquipmentManager() {
        return equipmentManager;
    }

    public LogicContainer() throws ClientException {
        List<Equipment> equipmentList = new ArrayList<Equipment>();
        List<Client> clientList = new ArrayList<Client>();
        List<Rent> rentList = new ArrayList<Rent>();

        ClientRepository clientRepository = new ClientRepository(clientList);
        RentRepository rentRepository = new RentRepository(rentList);
        EquipmentRepository equipmentRepository = new EquipmentRepository(equipmentList);

        this.clientManager = new ClientManager(clientRepository);
        this.rentManager = new RentManager(rentRepository);
        this.equipmentManager = new EquipmentManager(equipmentRepository);

        Address address1 = new Address("Uc", "Czeresniowa", "23A");
        Address address2 = new Address("Warszawa", "Wisniowa", "48");
        Address address3 = new Address("Krakow", "Ananasowa", "3C");
        Address address4 = new Address("Zakopane", "Brzoskwiniowa", "75");

        String id1 = "4178632";
        String id2 = "SDK43987";
        String id3 = "LFKSJDL";

        getClientManager().registerClient("Zbigniew", "Nowak", address1, id1, DowodOsobisty);
        getClientManager().registerClient("Andrzej", "Wisinewski", address2, id2, DowodOsobisty);
        getClientManager().registerClient("Stefan", "Kowalski", address3, id3, DowodOsobisty);


        getEquipmentManager().registerCamera(100.0, 50.0, 2000.0, "Panasonic GX80", "4K");
        getEquipmentManager().registerLens(30.0, 25.0, 1500.0, "jdksahg", "100 - 350 mm");
        getEquipmentManager().registerLighting(40.0, 30.0, 1200.0, "kjfh", "300 W");

        getEquipmentManager().getEquipment(0).setArchive(false);
        getEquipmentManager().getEquipment(1).setArchive(false);
        getEquipmentManager().getEquipment(2).setArchive(false);

        LocalDateTime timeNow = LocalDateTime.now();

        LocalDateTime time2d = timeNow.plusHours(24 * 2);
        LocalDateTime time4d = timeNow.plusHours(24 * 4);
        LocalDateTime time6d = timeNow.plusHours(24 * 6);
        LocalDateTime time7d = timeNow.plusHours(24 * 7);
        LocalDateTime time9d = timeNow.plusHours(24 * 9);
        LocalDateTime time11d = timeNow.plusHours(24 * 11);

        getRentManager().makeReservation(getClientManager().getClient(id3, DowodOsobisty),
                getEquipmentManager().getEquipment(2), address3, timeNow.plusSeconds(1), time2d);

        getRentManager().makeReservation(getClientManager().getClient(id1, DowodOsobisty),
                getEquipmentManager().getEquipment(0), address1, time2d, time4d);

        getRentManager().makeReservation(getClientManager().getClient(id1, DowodOsobisty),
                getEquipmentManager().getEquipment(1), address1, time4d, time6d);

        getRentManager().makeReservation(getClientManager().getClient(id2, DowodOsobisty),
                getEquipmentManager().getEquipment(2), address2, time4d, time6d);

        getRentManager().makeReservation(getClientManager().getClient(id2, DowodOsobisty),
                getEquipmentManager().getEquipment(0), address4, time6d, time7d);

        getRentManager().makeReservation(getClientManager().getClient(id2, DowodOsobisty),
                getEquipmentManager().getEquipment(0), address2, time9d, time11d);

        getRentManager().returnEquipment(getRentManager().getRent(0), false);
        getRentManager().returnEquipment(getRentManager().getRent(1), false);
        getRentManager().returnEquipment(getRentManager().getRent(2), true);
        getRentManager().returnEquipment(getRentManager().getRent(3), false);
        getRentManager().returnEquipment(getRentManager().getRent(4), false);
        getRentManager().returnEquipment(getRentManager().getRent(5), false);
//
        /* spis wszystkich wypożyczeń w kontenerze:
         *
         * 3 klienci: 1, 2, 3
         *                  |<- teraz
         * Kamera(id: 0)    |   |-1-|   |-2-|   |-2-|
         * Obiektyw(id: 1)  |       |-1-x
         * Lampa(id: 2)     |-3-|   |-2-|
         *       czas:      +0  +2  +4  +6  +7  +9  +11
         *
         * x - sprzęt zgubiony
         */

       }
}
