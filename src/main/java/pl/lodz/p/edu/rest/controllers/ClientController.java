package pl.lodz.p.edu.rest.controllers;

import jakarta.inject.Inject;

import jakarta.persistence.RollbackException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import pl.lodz.p.edu.rest.managers.ClientManager;
import pl.lodz.p.edu.rest.model.Client;
import pl.lodz.p.edu.rest.repository.DataFaker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;

@Path("/clients")
//@RequestScoped
public class ClientController {

    @Inject
    private ClientManager clientManager;

    protected ClientController() {}

    // create
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addClient(Client client) {

        if (client == null) {
            return Response.status(BAD_REQUEST).build();
        }

        if (client.getFirstName() == null
                || client.getLastName() == null
                || !client.getAddress().verify()
        ) {
            return Response.status(NOT_ACCEPTABLE).build();
        }

        try {
            clientManager.registerClient(client);
            return Response.status(CREATED).entity(client).build();
        } catch(RollbackException e) {
            return Response.status(CONFLICT).build();
        }
    }

    // read
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> getAllClients() {
        System.out.println(clientManager);
        List<Client> clients = clientManager.getAllClients();
        return clients;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/available")
    public Response getAllAvailableClients() {
        List<Client> all = clientManager.getAllClients();
        List<Client> available = new ArrayList<>();
        for (Client c : all) {
            if(!(c.isArchive())) {
                available.add(c);
            }
        }
        return Response.status(OK).entity(available).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    public Response getClientByUuid(@PathParam("uuid") UUID uuid) {
        Client client = clientManager.getClientByUuid(uuid);
        if(client != null) {
            return Response.status(OK).entity(client).build();
        } else {
            return Response.status(NOT_FOUND).build();
        }
    }



    // update
    @PUT
    @Path("/")
    public Response updateClient(Client client) {
        clientManager.updateClient(client);
        return Response.status(OK).entity(client).build();
    }



    // ========= other

    @POST
    @Path("/addFakeClient")
    @Produces(MediaType.APPLICATION_JSON)
    public Client addFakeClient() {
        Client c = DataFaker.getClient();
        clientManager.registerClient(c);
        return c;
    }



//    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
//    @POST
//    @Path(("/addClient"))
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response registerClient(ClientDTO clientDTO) {
//
//        String clientId = clientDTO.getClientId();
//        String firstName = clientDTO.getFirstName();
//        String lastName = clientDTO.getLastName();
//        Address address = clientDTO.getAddress();
//
//        if (address == null) {
//            return Response.status(NOT_ACCEPTABLE).build();
//        }
//
//        Client client = null;
//
//        try {
//            client = new Client(clientId, firstName, lastName, address);
//        } catch (ClientException e) {
//            System.out.println(e.getMessage());
//        }
//
////        Client mock = null;
////        try {
////            mock = new Client("test", "123", "324",
////                    new Address("twoja", "stara", "awd"));
////        } catch (ClientException e) {
////            throw new RuntimeException(e);
////        }
//
//        if (clientManager.registerClient(client)) {
//            return Response.status(CREATED).entity(client).build();
//            //Może tu jest błąd?
//        }
//        return Response.status(FORBIDDEN).build();
//    }



    @DELETE
    @Path("/{id}")
    public Response unregisterClient(@PathParam("id") UUID uuid) {
        if (clientManager.unregisterClient(uuid)) {
            return Response.status(NO_CONTENT).build();
        } else {
            return Response.status(NOT_FOUND).build();
        }
    }

//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path(("/{id}"))
//    public Response updateClient(@PathParam("id") UUID uuid) {
//        return null;
//
//    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/{type}")
    public Response getClientByClientId(@PathParam("id") String clientId) {
        Client client = clientManager.getByClientId(clientId);
        if(client != null) {
            return Response.status(OK).entity(client).build();
        } else {
            return Response.status(NOT_FOUND).build();
        }
    }
}
