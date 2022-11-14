package pl.lodz.p.edu.rest.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pl.lodz.p.edu.rest.repository.impl.*;

public final class RepositoryFactory {
    private static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("app");

//    private static final RentRepository rentRepository = new RentRepository(emf.createEntityManager());
//    private static final ClientRepository clientRepository = new ClientRepository(emf.createEntityManager());
//    private static final EquipmentRepository equipmentRepository = new EquipmentRepository(emf.createEntityManager());
   private static final RentRepository rentRepository = new RentRepository();
    private static final ClientRepository clientRepository = new ClientRepository();
    private static final EquipmentRepository equipmentRepository = new EquipmentRepository();


//    public RepositoryFactory() {
//
//    }

    public static Repository getRepository(RepositoryType type) {
        switch(type) {
            case RentRepository -> {
                return rentRepository;
            }
            case EquipmentRepository -> {
                return equipmentRepository;
            }
            case ClientRepository -> {
                return clientRepository;
            }
        }
        return null;
    }
}
