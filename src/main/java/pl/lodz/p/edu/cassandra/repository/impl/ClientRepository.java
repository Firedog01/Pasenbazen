package pl.lodz.p.edu.cassandra.repository.impl;

import pl.lodz.p.edu.cassandra.model.Client;
import pl.lodz.p.edu.cassandra.model.IdType;
import pl.lodz.p.edu.cassandra.repository.AbstractRepository;

import java.util.List;
import java.util.UUID;

public class ClientRepository extends AbstractRepository<Client> {
    @Override
    public void close() throws Exception {

    }

    @Override
    public Client get(UUID uuid) {
        return null;
    }

    @Override
    public List<Client> getAll() {
        return null;
    }

    @Override
    public void add(Client elem) {

    }

    @Override
    public void remove(Client elem) {

    }

    @Override
    public void update(Client elem) {

    }

    public Client getByClientId(String clientId, IdType idType) {
        return null;
    }
}
