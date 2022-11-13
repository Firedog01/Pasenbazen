package pl.lodz.p.edu.rest.controllers;

import jakarta.inject.Inject;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.DTO.ClientDTO;
import pl.lodz.p.edu.rest.exception.ClientException;
import pl.lodz.p.edu.rest.managers.ClientManager;
import pl.lodz.p.edu.rest.model.Address;
import pl.lodz.p.edu.rest.model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/clients")
public class ClientController {

    @Inject
    private ClientManager clientManager;

    protected ClientController() {
    }

    @POST
    @Path(("/addClient"))
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerClient(@Valid ClientDTO clientDTO) {

        String clientId = clientDTO.getClientId();
        String firstName = clientDTO.getFirstName();
        String lastName = clientDTO.getLastName();
        Address address = clientDTO.getAddress();

        if (address == null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        Client client = null;

        try {
            client = new Client(clientId, firstName, lastName, address);
        } catch (ClientException e) {
            System.out.println(e.getMessage());
        }

        Client mock = null;
        try {
            mock = new Client("test", "123", "324",
                    new Address("twoja", "stara", "awd"));
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }

        if (clientManager.registerClient(mock)) {
            return Response.status(Response.Status.CREATED).entity(mock).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }


    @DELETE
    @Path("/{id}")
    public Response unregisterClient(@PathParam("id") UUID uuid) {
        if (clientManager.unregisterClient(uuid)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
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
            return Response.status(Response.Status.OK).entity(client).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    public Response getClientByUuid(@PathParam("uuid") UUID uuid) {
        Client client = clientManager.getClientByUuid(uuid);
        if(client != null) {
            return Response.status(Response.Status.OK).entity(client).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response getAllClients() {
        List<Client> clients = clientManager.getAllClients();
        return Response.status(Response.Status.OK).entity(clients).build();
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
        return Response.status(Response.Status.OK).entity(available).build();
    }
}
