package pl.lodz.p.edu.rest.controllers;

import jakarta.inject.Inject;

import jakarta.persistence.RollbackException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import pl.lodz.p.edu.rest.DTO.ClientDTO;
import pl.lodz.p.edu.rest.exception.NoObjectException;
import pl.lodz.p.edu.rest.exception.UserException;
import pl.lodz.p.edu.rest.exception.user.MalformedUserException;
import pl.lodz.p.edu.rest.exception.user.UserConflictException;
import pl.lodz.p.edu.rest.managers.UserManager;
import pl.lodz.p.edu.rest.model.users.Client;
//import pl.lodz.p.edu.rest.repository.DataFaker;

import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;

@Path("/clients")
//@RequestScoped
public class ClientController {

    @Inject
    private UserManager userManager;

    @Inject
    private UserControllerMethods userControllerMethods;

    protected ClientController() {}

    // create
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addClient(ClientDTO client) {
        try {
            Client c = new Client(client.getLogin(), client.getFirstName(),
                    client.getLastName(), client.getAddress());
            userManager.registerClient(c);
            return Response.status(CREATED).entity(client).build();
        } catch(UserConflictException e) {
            return Response.status(CONFLICT).build();
        } catch(NoObjectException e) {
            return Response.status(BAD_REQUEST).build();
        } catch(MalformedUserException e) {
            return Response.status(NOT_ACCEPTABLE).build();
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
    }

    // read
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchClients(@QueryParam("login") String login) {
        return userControllerMethods.searchUser(login);
    }

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByUuid(@PathParam("uuid") UUID entityId) {
        return userControllerMethods.getSingleUser(entityId);
    }

    @GET
    @Path("/login/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByLogin(@PathParam("login") String login) {
        return userControllerMethods.getSingleUser(login);
    }

    // update
    @PUT
    @Path("/{entityId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateClient(@PathParam("entityId") UUID entityId, ClientDTO clientDTO) {
        Client client = (Client) userManager.getUserByUuid(entityId);
        client.updateClientData(clientDTO.getFirstName(), clientDTO.getLastName(), clientDTO.getAddress());
        userManager.updateClient(entityId, client);
        return Response.status(OK).entity(client).build();
    }

    @PUT
    @Path("/{entityId}/activate")
    public Response activateUser(@PathParam("entityId") UUID entityId) {
        return userControllerMethods.activateUser(entityId);
    }

    @PUT
    @Path("/{entityId}/deactivate")
    public Response deactivateUser(@PathParam("entityId") UUID entityId) {
        return userControllerMethods.deactivateUser(entityId);
    }



    // ========= other
//
//    @POST
//    @Path("/addFake")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Client addFakeClient() {
//        Client c = DataFaker.getClient();
//        try {
//            userManager.registerClient(c);
//        } catch(Exception e) {
//            return null;
//        }
//        return c;
//    }
}
