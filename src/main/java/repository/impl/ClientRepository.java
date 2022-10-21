package repository.impl;

import jakarta.persistence.EntityManager;
import model.Client;
import repository.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public class ClientRepository implements Repository<Client> {

    private Map<UUID, Client> clientMap;

    private EntityManager em;

    public ClientRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Client get(UUID uuid) {
        if (clientMap.containsKey(uuid)) {
            return clientMap.get(uuid);
        }
        return null; //FIXME not sure
    }


    @Override
    public List<Client> getAll() {
        return clientMap.values().stream().toList(); //Somehow idk
    }

    @Override
    public boolean add(UUID uuid, Client elem) {
    if (!clientMap.containsKey(uuid)) {
        clientMap.put(uuid, elem);
        return true;
    }
    return false; // FIXME Same client cannot be added twice?
    }

    @Override
    public boolean remove(UUID key) {
        if (clientMap.containsKey(key)) {
            clientMap.remove(key);  //FIXME returns boolean or Client?
            return true;
        }
        return false;
    }

    @Override
    public boolean update(UUID uuid, Client elem) {
        if (clientMap.containsKey(uuid)) {
            clientMap.put(uuid, elem); //FIXME bool or Client from here?
            return true;
        }
        return false;
    }

    @Override
    public int count() {
        return clientMap.size();
    }
}
