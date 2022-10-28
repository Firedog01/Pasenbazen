package repository;

import jakarta.persistence.EntityManagerFactory;
import repository.impl.ClientRepository;
import repository.impl.EquipmentRepository;
import repository.impl.RentRepository;

public class RepositoryFactory {
    EntityManagerFactory emf;

    public RepositoryFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Repository getRepository(RepositoryType type) {
        switch(type) {
            case RentRepository -> {
                return new RentRepository(emf.createEntityManager());
            }
            case EquipmentRepository -> {
                return new EquipmentRepository(emf.createEntityManager());
            }
            case ClientRepository -> {
                return new ClientRepository(emf.createEntityManager());
            }
            case AddressRepository -> {
                return new AddressRepository(emf.createEntityManager());
            }
        }
        return null;
    }

}
