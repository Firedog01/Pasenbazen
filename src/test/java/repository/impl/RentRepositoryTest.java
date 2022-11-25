package repository.impl;

import mgd.*;
import mgd.EQ.EquipmentMgd;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class RentRepositoryTest {

    static RentRepository rentRepository = new RentRepository();
    static EquipmentRepository equipmentRepository = new EquipmentRepository();
    static ClientRepository clientRepository = new ClientRepository();

    static AddressMgd address1 = DataFakerMgd.getAddressMgd();
    static AddressMgd address2 = DataFakerMgd.getAddressMgd();
    static AddressMgd address3 = DataFakerMgd.getAddressMgd();
    static ClientMgd client1 = DataFakerMgd.getClientMgd(address1);
    static ClientMgd client2 = DataFakerMgd.getClientMgd(address2);
    static ClientMgd client3 = DataFakerMgd.getClientMgd(address3);
    static EquipmentMgd camera  = DataFakerMgd.getCameraMgd();
    static EquipmentMgd lens    = DataFakerMgd.getLensMgd();
    static EquipmentMgd trivet  = DataFakerMgd.getTrivetMgd();

    static LocalDateTime t0 = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    static  LocalDateTime t1 = t0.plusDays('1');
    static RentMgd rent1;
    static RentMgd rent2;
    static RentMgd rent3;


    @BeforeAll
    static void beforeAll() {
        rent1 = new RentMgd(new UniqueIdMgd(), t0, t1, camera, client1, address1, false, false);
        rent2 = new RentMgd(new UniqueIdMgd(), t0, t1, lens, client2, address2, false, false);
        rent3 = new RentMgd(new UniqueIdMgd(), t0, t1, camera, client3, address3, false, false);

        clientRepository.add(client1);
        clientRepository.add(client2);
        clientRepository.add(client3);
        equipmentRepository.add(camera);
        equipmentRepository.add(lens);
        equipmentRepository.add(trivet);
        rentRepository.add(rent1);
        rentRepository.add(rent2);
        rentRepository.add(rent3);
    }

    @Test
    void deleteTest() {
        RentMgd otherRent = DataFakerMgd.getRentMgd(t0, t1);
        rentRepository.add(otherRent);
        assertEquals(otherRent, rentRepository.get(otherRent.getEntityId()));
        rentRepository.remove(otherRent);
        assertNull(rentRepository.get(otherRent.getEntityId()));
    }

    @Test
    void updateShippedTest() {
        boolean shipped = true;
        RentMgd updatedRent = rentRepository.updateShipped(rent1, shipped);
        assertEquals(updatedRent, rent1);
        assertEquals(updatedRent, rentRepository.get(updatedRent.getEntityId()));
    }

    @Test
    void updateMissingReturnedTest() {
        boolean missing = true;
        boolean eqReturned = true;
        RentMgd updatedRent = rentRepository.updateMissingReturned(rent2, missing, eqReturned);
        assertEquals(updatedRent, rent2);
        assertEquals(updatedRent, rentRepository.get(updatedRent.getEntityId()));
        // equipment was also updated

        assertEquals(missing, equipmentRepository.get(updatedRent.getEquipment().getEntityId())
                .isMissing());

    }


    @Test
    void count() {
        int startingCount = rentRepository.getAll().size();

        RentMgd r1 = DataFakerMgd.getRentMgd(t0, t1);
        RentMgd r2 = DataFakerMgd.getRentMgd(t0, t1);
        RentMgd r3 = DataFakerMgd.getRentMgd(t0, t1);

        rentRepository.add(r1);
        rentRepository.add(r2);
        rentRepository.add(r3);

        assertEquals(startingCount + 3, rentRepository.getAll().size());

        rentRepository.remove(r1);

        assertEquals(startingCount + 2, rentRepository.getAll().size());
    }
}