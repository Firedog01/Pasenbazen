package pl.lodz.p.edu.rest.managers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.rest.model.Client;
import pl.lodz.p.edu.rest.model.UniqueId;
import pl.lodz.p.edu.rest.model.idType;

import pl.lodz.p.edu.rest.repository.impl.ClientRepository;

import java.util.List;
import java.util.UUID;

//TODO Managers are like services?

@Transactional
//@RequestScoped
//@WebService FIXME ?
public class ClientManager {

    @Inject
    ClientRepository clientRepository;

//    private ClientRepository clientRepository = (ClientRepository) RepositoryFactory.getRepository(RepositoryType.ClientRepository);

//    public ClientManager(ClientRepository clientRepository) {
//        this.clientRepository = clientRepository;
//    }

    public void registerClient(Client client) {
        clientRepository.add(client);
    }


    public boolean unregisterClient(UUID uuid) {
        return clientRepository.remove(uuid);
    }

    public Client getByClientId(String id, idType idType) {
        return clientRepository.getClientByIdName(id, idType);
    }

    public Client getClientByUuid(UniqueId uniqueId) {
        return clientRepository.getClientByUuid(uniqueId);
    }

    public List<Client> getAllClients() {
        return clientRepository.getAll();
    }

    public boolean updateClient(Client client) {
        return clientRepository.update(client);
    }
}
