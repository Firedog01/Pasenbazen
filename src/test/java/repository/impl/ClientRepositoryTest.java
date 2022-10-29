package repository.impl;

import exception.ClientException;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import mgd.AddressMgd;
import mgd.ClientMgd;
import mgd.UniqueIdMgd;
import model.*;
import model.EQ.Equipment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.DataFaker;
import repository.RepositoryFactory;
import repository.RepositoryType;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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
