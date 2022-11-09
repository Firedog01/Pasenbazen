package managers;

import mgd.AddressMgd;
import mgd.ClientMgd;
import mgd.DataFakerMgd;
import mgd.EQ.EquipmentMgd;
import mgd.RentMgd;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.impl.ClientRepository;
import repository.impl.EquipmentRepository;
import repository.impl.RentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RentManagerTest {

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


    LocalDateTime t0;
    LocalDateTime t1;
    LocalDateTime t2;
    LocalDateTime t3;
    LocalDateTime t4;
    LocalDateTime t5;
    // t0 = now
    // t0 < t1 < t2 < t3 < t4 < t5

    RentManager rentManager;
    RentMgd rent;

    @BeforeAll
    static void beforeAll() {
        client1.setFirstName("111111");
        client2.setFirstName("222222");
        client3.setFirstName("333333");
        clientRepository.add(client1);
        clientRepository.add(client2);
        clientRepository.add(client3);
        equipmentRepository.add(camera);
        equipmentRepository.add(lens);
        equipmentRepository.add(trivet);
    }


    RentManagerTest() {
        t0 = LocalDateTime.now();
        t1 = t0.plusDays(1);
        t2 = t0.plusDays(2);
        t3 = t0.plusDays(3);
        t4 = t0.plusDays(4);
        t5 = t0.plusDays(5);
        rentManager = new RentManager(rentRepository);
    }

    @BeforeEach
    void beforeEach() {
        List<RentMgd> allRents = rentRepository.getAllRents();
        for(RentMgd rent : allRents) {
            rentRepository.deleteOne(rent);
        }
        rent = rentManager.makeReservation(client1, lens, address1, t1, t3);
    }

    @AfterEach
    void afterEach() {
        rentRepository.deleteOne(rent);
    }

    // +----- old rent -----+
    //                          +----- new rent -----+
    @Test
    void makeReservationTestCorrect() {
        RentMgd correctRent = rentManager.makeReservation(client2, lens, address2, t4, t5);

        assertNotNull(correctRent);
        assertEquals(2, rentRepository.getAllRents().size());
        RentMgd addedRent = rentRepository.getById(correctRent.getEntityId());

        assertEquals(correctRent, addedRent);
        rentRepository.deleteOne(correctRent);
    }

    //         +----- old rent -----+
    // +----- new rent -----+
    @Test
    void makeReservationTestIncorrectTimeBefore() {
        RentMgd incorrectRent = rentManager.makeReservation(client2, lens, address1, t0, t2);

        assertNull(incorrectRent);
        assertEquals(1, rentRepository.getAllRents().size());
    }

    // +----- old rent -----+
    //         +----- new rent -----+
    @Test
    void makeReservationTestIncorrectTimeAfter() {
        RentMgd incorrectRent = rentManager.makeReservation(client2, lens, address1, t2, t4);

        assertNull(incorrectRent);
        assertEquals(1, rentRepository.getAllRents().size());
    }

    // +----- old rent -----+
    //    +-- new rent --+
    @Test
    void makeReservationTestIncorrectTimeInside() {
        RentMgd incorrectRent = rentManager.makeReservation(client2, lens, address1, t1, t2);

        assertNull(incorrectRent);
        assertEquals(1, rentRepository.getAllRents().size());
    }

    //    +-- old rent --+
    // +----- new rent -----+
    @Test
    void makeReservationTestIncorrectTimeOutside() {
        RentMgd incorrectRent = rentManager.makeReservation(client2, lens, address1, t0, t4);

        assertNull(incorrectRent);
        assertEquals(1, rentRepository.getAllRents().size());
    }

    @Test
    void makeReservationTestIncorrectTimeMalformedRange() {
        RentMgd incorrectRent = rentManager.makeReservation(client2, lens, address1, t5, t4);

        assertNull(incorrectRent);
        assertEquals(1, rentRepository.getAllRents().size());
    }

    @Test
    void makeReservationArchivedClientBadEq() {

        ClientMgd clientArchive = new ClientMgd("1321", "12321", "12321", address1);
        clientArchive.setArchive(true);
        RentMgd badRent = rentManager.makeReservation(clientArchive, trivet, address1, t1, t3);
        assertNull(badRent);
        assertEquals(1, rentRepository.getAllRents().size());

        EquipmentMgd badTrivet = DataFakerMgd.getTrivetMgd();
        badTrivet.setMissing(true);
        badRent = rentManager.makeReservation(client2, badTrivet, address1, t1, t3);
        assertNull(badRent);
        assertEquals(1, rentRepository.getAllRents().size());

        badTrivet.setMissing(false);
        badTrivet.setArchive(true);

        badRent = rentManager.makeReservation(client2, badTrivet, address1, t1, t3);
        assertNull(badRent);

        assertEquals(1, rentRepository.getAllRents().size());

    }

    @Test
    void getEquipmentRentsTest() {
        RentMgd rent1 = rentManager.makeReservation(client2, camera, address2, t4, t5);
        RentMgd rent2 = rentManager.makeReservation(client2, camera, address2, t4, t5);
        List<RentMgd> equipmentRents = rentRepository.getEquipmentRents(camera);

        assertEquals(2, equipmentRents.size());

        rentRepository.deleteOne(rent1);
        rentRepository.deleteOne(rent2);
    }
}