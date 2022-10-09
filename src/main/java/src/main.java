package src;

import exception.ClientException;
import managers.LogicContainer;
import model.Client;
import model.EQ.Equipment;
import model.Rent;

import java.util.List;

public class main {
    public static void main(String[] args) throws ClientException {
        LogicContainer logicContainer = new LogicContainer();

        System.out.println("---------------SPRZĘT W SKLEPIE--------------\\n");
        List<Equipment> equipmentList = logicContainer.getEquipmentManager().findAllEquipment();

        for (Equipment equipment:
             equipmentList) {
            System.out.println(equipment.toString());
        }

        System.out.println("---------------ZAREJESTROWANE KLIENCI--------------\\n");
        List<Client> clientList = logicContainer.getClientManager().getAllClients();

        for (Client client:
             clientList) {
            System.out.println(client.toString());
        }

        System.out.println("---------------WYPOŻYCZENIA--------------\\n");
        List<Rent> rentList = logicContainer.getRentManager().findAllRents();

        for (Rent rent :
                rentList) {
            System.out.println(rent.getRentInfo());
        }
    }
}
