package pl.lodz.p.edu.cassandra.repository.impl;

import pl.lodz.p.edu.cassandra.model.Client;
import pl.lodz.p.edu.cassandra.model.EQ.Equipment;
import pl.lodz.p.edu.cassandra.model.Rent;
import pl.lodz.p.edu.cassandra.repository.AbstractRepository;

import java.util.List;
import java.util.UUID;

public class RentRepository extends AbstractRepository<Rent> {
    @Override
    public void close() throws Exception {

    }

    @Override
    public Rent get(UUID uuid) {
        return null;
    }

    @Override
    public List<Rent> getAll() {
        return null;
    }

    @Override
    public void add(Rent elem) {

    }

    @Override
    public void remove(Rent elem) {

    }

    @Override
    public void update(Rent elem) {

    }

    public List<Rent> getEquipmentRents(Equipment equipment) {
        return null;
    }

    public List<Rent> getRentByEq(Equipment equipment) {
        return null;
    }

    public List<Rent> getRentByClient(Client client) {
        return null;
    }
}
