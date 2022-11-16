package pl.lodz.p.edu.rest.controllers;

import jakarta.inject.Inject;

import jakarta.persistence.RollbackException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import pl.lodz.p.edu.rest.exception.NoObjectException;
import pl.lodz.p.edu.rest.exception.user.MalformedUserException;
import pl.lodz.p.edu.rest.exception.user.UserConflictException;
import pl.lodz.p.edu.rest.managers.UserManager;
import pl.lodz.p.edu.rest.model.users.Client;
import pl.lodz.p.edu.rest.repository.DataFaker;

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
    public Response addClient(Client client) {
        try {
            userManager.registerClient(client);
            return Response.status(CREATED).entity(client).build();
        } catch(UserConflictException e) {
            return Response.status(CONFLICT).build();
        } catch(NoObjectException e) {
            return Response.status(BAD_REQUEST).build();
        } catch(MalformedUserException e) {
            return Response.status(NOT_ACCEPTABLE).build();
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
    public Response updateClient(@PathParam("entityId") UUID entityId, Client client) {
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

    @POST
    @Path("/addFake")
    @Produces(MediaType.APPLICATION_JSON)
    public Client addFakeClient() {
        Client c = DataFaker.getClient();
        try {
            userManager.registerClient(c);
        } catch(Exception e) {
            return null;
        }
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




}
