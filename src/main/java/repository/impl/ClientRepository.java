package repository.impl;

import com.mongodb.client.MongoCollection;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import mgd.ClientMgd;
import model.*;
import mgd.AbstractEntityMgd;
import model.EQ.Equipment;
import repository.Repository;

import java.util.List;


public class ClientRepository implements Repository<ClientMgd> {
    private EntityManager em;

    public ClientRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public ClientMgd get(UniqueId id) {
        return null;
    }

    @Override
    public List<ClientMgd> getAll() {
        return null;
    }

    @Override
    public void add(ClientMgd elem) {
        MongoCollection<ClientMgd> clientsCollection = getDatabase().getCollection()
    }

    @Override
    public void remove(ClientMgd elem) {

    }

    @Override
    public void update(ClientMgd elem) {

    }

    @Override
    public Long count() {
        return null;
    }
}
