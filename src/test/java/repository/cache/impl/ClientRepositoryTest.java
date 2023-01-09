package repository.cache.impl;

import mgd.AddressMgd;
import mgd.ClientMgd;
import mgd.DataFakerMgd;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import repository.impl.ClientRepository;

@Disabled
class ClientRepositoryTest {

    static ClientRepository clientRepository = new ClientRepository();
    static AddressMgd address1 = DataFakerMgd.getAddressMgd();
    static AddressMgd address2 = DataFakerMgd.getAddressMgd();
    static AddressMgd address3 = DataFakerMgd.getAddressMgd();
    static ClientMgd client1 = DataFakerMgd.getClientMgd(address1);
    static ClientMgd client2 = DataFakerMgd.getClientMgd(address2);
    static ClientMgd client3 = DataFakerMgd.getClientMgd(address3);

    @BeforeAll
    static void beforeAll() {
        clientRepository.add(client1);
        clientRepository.add(client2);
        clientRepository.add(client3);
    }

    @Test
    void GetRemoveTest() {
        ClientMgd client4 = DataFakerMgd.getClientMgd();
        int startSize = clientRepository.getAll().size();
        clientRepository.add(client4);
        Assertions.assertEquals(startSize + 1, (long) clientRepository.getAll().size());
        Assertions.assertEquals(clientRepository.get(client4.getEntityId()), client4);
        clientRepository.remove(client4);
        Assertions.assertEquals(startSize, (long) clientRepository.getAll().size());
    }

    @Test
    void updateClientTest() {
        client1.setFirstName("__1__");
        client1.setLastName("__2__");
        client1.setArchive(true);
        AddressMgd tempAddr = DataFakerMgd.getAddressMgd();
        client1.setAddress(tempAddr);
        clientRepository.update(client1);

        ClientMgd client1_updated = clientRepository.get(client1.getEntityId());
        Assertions.assertEquals("__1__", client1_updated.getFirstName());
        Assertions.assertEquals("__2__", client1_updated.getLastName());
        Assertions.assertEquals(true, client1_updated.isArchive());
        Assertions.assertEquals(tempAddr, client1_updated.getAddress());
    }

    @Test
    void updateByKeyTest() {
        client2.setLastName("_1_");
        clientRepository.updateByKey(client1.getEntityId(), "last_name", client2.getLastName());

        ClientMgd client1_updated = clientRepository.get(client1.getEntityId());
        Assertions.assertEquals("_1_", client1_updated.getLastName());
    }
}
