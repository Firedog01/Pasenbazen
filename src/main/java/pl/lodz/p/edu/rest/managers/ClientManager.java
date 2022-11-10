package pl.lodz.p.edu.rest.managers;

import jakarta.inject.Inject;
import jakarta.jws.WebService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.model.Client;
import pl.lodz.p.edu.rest.model.idType;

import pl.lodz.p.edu.rest.repository.impl.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//TODO Managers are like services?

@Transactional
//@RequestScoped
//@WebService FIXME ?
@Path("/clients")
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


    public Response unregisterClient(UUID uuid) {
        if (clientRepository.remove(uuid)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    public Client getByClientId(UUID uuid) {
        clientRepository.get(uuid)
    }

    //FIXME HERE ENDED


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    public Response getClient(@PathParam("uuid") UUID uuid) {
        Client client = clientRepository.get(uuid);
        if(client != null) {
            return Response.status(Response.Status.OK).entity(client).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

//    public Client getClient(String clientId, idType idType) {
//        try {
//            return clientRepository.getByClientId(clientId, idType);
//        } catch (EntityNotFoundException ex) {
//            return null;
//        }
//    }
//
//    public List<Client> getAllClients() {
//        return clientRepository.getAll();
//    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClients() {
        List<Client> clients = clientRepository.getAll();
        return Response.status(Response.Status.OK).entity(clients).build();
    }

//    public List<Client> getAllAvailableClients() {
//        List<Client> all = getAllClients();
//        List<Client> available = new ArrayList<>();
//        for (Client c : all) {
//            if (!(c.isArchive())) {
//                available.add(c);
//            }
//        }
//        return available;
//    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/available")
    public Response getAllAvailableClients() {
        List<Client> all = clientRepository.getAll();
        List<Client> available = new ArrayList<>();
        for (Client c : all) {
            if(!(c.isArchive())) {
                available.add(c);
            }
        }
        return Response.status(Response.Status.OK).entity(available).build();
    }
}
