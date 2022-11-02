package repository.impl;

import mgd.AddressMgd;
import mgd.ClientMgd;
import mgd.DataFakerMgd;
import mgd.UniqueIdMgd;

import model.Client;
import org.junit.After;
import org.junit.Before;
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
    void updateTest() {
        client1.setFirstName("__1__");
        client1.setArchive(true);
        clientRepository.updateClient(client1);
        ClientMgd client1_updated = clientRepository.getByUniqueId(client1.getEntityId());

    }

}
