package pl.lodz.p.edu.rest.managers;

import jakarta.inject.Inject;

import jakarta.transaction.Transactional;
import pl.lodz.p.edu.rest.model.Equipment;
import pl.lodz.p.edu.rest.model.Rent;

import pl.lodz.p.edu.rest.model.users.Client;
import pl.lodz.p.edu.rest.repository.impl.RentRepository;

import java.util.List;
import java.util.UUID;


@Transactional
public class RentManager {

    @Inject
    private RentRepository rentRepository;

    protected RentManager() {}

    public List<Rent> getRentByEq(Equipment equipment) {
        return rentRepository.getRentByEq(equipment);
    }

    public List<Rent> getRentsByClient(Client client) {
        return rentRepository.getRentByClient(client);
    }

    public Rent get(UUID uuid) {
        return rentRepository.get(uuid);
    }

    public List<Rent> getAll() {
        return rentRepository.getAll();
    }

    public void add(Rent rent) {
        rentRepository.add(rent);
    }

    public void update(Rent rent) {
        rentRepository.update(rent);
    }

    public void remove(UUID uuid) {
        rentRepository.remove(uuid);
    }


}