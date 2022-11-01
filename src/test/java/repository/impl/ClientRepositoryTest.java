package repository.impl;

import mgd.AddressMgd;
import mgd.ClientMgd;
import mgd.UniqueIdMgd;

import org.junit.jupiter.api.Test;

class ClientRepositoryTest {

    @Test
    void addOneTest() {
        ClientRepository repo = new ClientRepository();
        AddressMgd addressMgd = new AddressMgd(new UniqueIdMgd(),
                "city", "street", "streetNo");
        ClientMgd clientMgd = new ClientMgd(new UniqueIdMgd(),
                "123",
                ClientMgd.idType.DowodOsobisty,
                "fname",
                "lname",
                addressMgd,
                false);
        repo.add(clientMgd);
    }

}
