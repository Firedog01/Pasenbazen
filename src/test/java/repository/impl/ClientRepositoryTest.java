package repository.impl;

import mgd.AddressMgd;
import mgd.ClientMgd;
import mgd.DataFakerMgd;


import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ClientRepositoryTest {

    ClientRepository clientRepository = new ClientRepository();
    AddressMgd address1 = DataFakerMgd.getAddressMgd();
    AddressMgd address2 = DataFakerMgd.getAddressMgd();
    AddressMgd address3 = DataFakerMgd.getAddressMgd();
    ClientMgd client1 = DataFakerMgd.getClientMgd(address1);
    ClientMgd client2 = DataFakerMgd.getClientMgd(address2);
    ClientMgd client3 = DataFakerMgd.getClientMgd(address3);

    @BeforeAll
    void beforeEach() {
        clientRepository.add(client1);
        clientRepository.add(client2);
        clientRepository.add(client3);
    }

    @Test
    void GetRemoveTest() {
        Assertions.assertEquals(3, (long) clientRepository.getAllClients().size());
        Assertions.assertEquals(clientRepository.getByUniqueId(client1.getEntityId()), client1);
        clientRepository.deleteOne(client1);
        Assertions.assertEquals(2, (long) clientRepository.getAllClients().size());
        //Assert throws?
    }

    @Test
    void updateTest() {
        client1.setFirstName("__1__");
        client1.setFirstName("__2__");
        client1.setArchive(true);
        AddressMgd tempAddr = DataFakerMgd.getAddressMgd();
        client1.setAddress(tempAddr);
        clientRepository.updateClient(client1);
        ClientMgd client1_updated = clientRepository.getByUniqueId(client1.getEntityId());
        Assertions.assertEquals(client1_updated.getFirstName(), "__1__");
        Assertions.assertEquals(client1_updated.getLastName(), "__2__");
        Assertions.assertEquals(client1_updated.isArchive(), true);
        Assertions.assertEquals(client1_updated.getAddress(), tempAddr);
    }

}
