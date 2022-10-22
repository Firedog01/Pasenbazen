package repository;

import jakarta.persistence.EntityManagerFactory;
import model.Address;
import model.Client;
import model.EQ.Equipment;
import model.Rent;
import repository.impl.AddressRepository;
import repository.impl.ClientRepository;
import repository.impl.EquipmentRepository;
import repository.impl.RentRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RepositoryFactory {
//    EntityManagerFactory emf;

    public RepositoryFactory() {

    }

    public Repository getRepository(RepositoryType type) {
        switch(type) {
            case RentRepository -> {
                return new RentRepository(new HashMap<UUID, Rent>());
            }
            case EquipmentRepository -> {
                return new EquipmentRepository(new HashMap<UUID, Equipment>());
            }
            case ClientRepository -> {
                return new ClientRepository(new HashMap<UUID, Client>());
            }
            case AddressRepository -> {
                return new AddressRepository(new HashMap<UUID, Address>());
            }
        }
        return null;
    }

}
