package repository.impl;

import model.Client;
import repository.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public class ClientRepository implements Repository<Client> {

    private Map<UUID, Client> clientList;

    /* yyyyyyyy
    public ClientRepository(EntityManager em) {
        this.em = em;
    }
*/
    @Override
    public Client get(UUID uuid) {
        if (clientList.containsKey(uuid)) {
            return clientList.get(uuid);
        }
        return null; //FIXME not sure
    }


    @Override
    public List<Client> getAll() {
        return clientList.values().stream().toList(); //Somehow idk
    }

        @Override
        public boolean add(UUID uuid, Client elem) {
        if (!clientList.containsKey(uuid)) {
            clientList.put(uuid, elem);
            return true;
        }
        return false; //FIXME Same client cannot be added twice?
    }

    @Override
    public boolean remove(UUID key) {
        if (clientList.containsKey(key)) {
            clientList.remove(key);  //FIXME returns boolean or Client?
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Client elem) {
        return false; //TODO
    }

    @Override
    public int count() {
        return clientList.size();
    }
}
