package repository.impl;

import mgd.*;
import mgd.EQ.CameraMgd;
import mgd.EQ.EquipmentMgd;
import mgd.EQ.LensMgd;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
    static RentMgd rent1 = DataFakerMgd.getRentMgd(camera, client1, address1);
    static RentMgd rent2 = DataFakerMgd.getRentMgd(lens, client2, address2);
    static RentMgd rent3 = DataFakerMgd.getRentMgd(trivet, client3, address3);


    @BeforeAll
    static void beforeEach() {
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
        RentMgd otherRent = DataFakerMgd.getRent();
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

        AddressMgd address1c = DataFakerMgd.getAddressMgd();
        AddressMgd address2c = DataFakerMgd.getAddressMgd();
        AddressMgd address3c = DataFakerMgd.getAddressMgd();
        ClientMgd client1 = DataFakerMgd.getClientMgd(address1c);
        ClientMgd client2 = DataFakerMgd.getClientMgd(address2c);
        ClientMgd client3 = DataFakerMgd.getClientMgd(address3c);
        EquipmentMgd eq1 = DataFakerMgd.getCameraMgd();
        EquipmentMgd eq2 = DataFakerMgd.getTrivetMgd();
        EquipmentMgd eq3 = DataFakerMgd.getLensMgd();

        RentMgd r1 = DataFakerMgd.getRentMgd(eq1, client1, address1c);
        RentMgd r2 = DataFakerMgd.getRentMgd(eq2, client2, address2c);
        RentMgd r3 = DataFakerMgd.getRentMgd(eq3, client3, address3c);

        rentRepository.add(r1);
        rentRepository.add(r2);
        rentRepository.add(r3);

        assertEquals(startingCount + 3, rentRepository.getAll().size());

        rentRepository.remove(r1);

        assertEquals(startingCount + 2, rentRepository.getAll().size());
    }
}