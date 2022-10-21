package repository.impl;

import jakarta.persistence.*;
import model.*;
import repository.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AddressRepository implements Repository<Address> {

    private Map<UUID, Address> addressMap;

    private EntityManager em;

    public AddressRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Address get(UUID uuid) {
        if (addressMap.containsKey(uuid)) {
            return addressMap.get(uuid);
        }
        return null; //FIXME not sure
    }

    @Override
    public List<Address> getAll() {
        return addressMap.values().stream().toList();
    }

    @Override
    public boolean add(UUID uuid, Address elem) {
        if (!addressMap.containsKey(uuid)) {
            addressMap.put(uuid, elem);
            return true;
        }
        return false; //FIXME Same address cannot be added twice?
    }

    @Override
    public boolean remove(UUID key) {
        if (addressMap.containsKey(key)) {
            addressMap.remove(key);  //FIXME returns boolean or Client?
            return true;
        }
        return false;
    }

    @Override
    public boolean update(UUID uuid, Address elem) {
        if (addressMap.containsKey(uuid)) {
            addressMap.put(uuid, elem); //FIXME bool or Address from here?
            return true;
        }
        return false;
    }

    @Override
    public int count() {

        return addressMap.size();

    }
}
