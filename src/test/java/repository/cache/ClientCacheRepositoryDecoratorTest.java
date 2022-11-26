package repository.cache;

import mgd.*;
import mgd.EQ.EquipmentMgd;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.Repository;
import repository.impl.ClientRepository;
import repository.impl.EquipmentRepository;
import repository.impl.RentRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class ClientCacheRepositoryDecoratorTest {

    static ClientCacheRepositoryDecorator clientRepository = new ClientCacheRepositoryDecorator(new ClientRepository());

    static AddressMgd address1 = DataFakerMgd.getAddressMgd();
    static AddressMgd address2 = DataFakerMgd.getAddressMgd();
    static AddressMgd address3 = DataFakerMgd.getAddressMgd();
    static ClientMgd client1 = DataFakerMgd.getClientMgd(address1);
    static ClientMgd client2 = DataFakerMgd.getClientMgd(address2);
    static ClientMgd client3 = DataFakerMgd.getClientMgd(address3);

    static LocalDateTime t0 = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    static  LocalDateTime t1 = t0.plusDays('1');


    @BeforeAll
    static void beforeAll() {
        clientRepository.clearCache();

        clientRepository.add(client1);
        clientRepository.add(client2);
        clientRepository.add(client3);

    }

    @Test
    void basicCheck() {
        Repository decoratedRentRepo = new ClientCacheRepositoryDecorator(new ClientRepository());
    }

    @Test
    void count() {
        int startingCount = clientRepository.getAll().size();

        ClientMgd r1 = DataFakerMgd.getClientMgd();
        ClientMgd r2 = DataFakerMgd.getClientMgd();
        ClientMgd r3 = DataFakerMgd.getClientMgd();

        clientRepository.add(r1);
        clientRepository.add(r2);
        clientRepository.add(r3);

        clientRepository.get(client1.getEntityId());

        assertEquals(startingCount + 3, clientRepository.getAll().size());

        clientRepository.remove(r1);

        assertEquals(startingCount + 2, clientRepository.getAll().size());
    }
}