package pl.lodz.p.edu.rest.controllers;

import jakarta.inject.Inject;

import jakarta.persistence.RollbackException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import pl.lodz.p.edu.rest.managers.UserManager;
import pl.lodz.p.edu.rest.model.UniqueId;
import pl.lodz.p.edu.rest.model.users.Client;
import pl.lodz.p.edu.rest.model.users.User;
import pl.lodz.p.edu.rest.model.users.UserAdmin;
import pl.lodz.p.edu.rest.repository.DataFaker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;

@Path("/clients")
//@RequestScoped
public class ClientController {

    @Inject
    private UserManager userManager;

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
            userManager.registerClient(client);
            return Response.status(CREATED).entity(client).build();
        } catch(RollbackException e) {
            return Response.status(CONFLICT).build();
        }
    }

    // read
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchClients(@QueryParam("login") String login) {
        if(login != null) {
            List<User> searchResult = userManager.search(login);
            if(searchResult.size() == 1) {
                return Response.status(OK).entity(searchResult.get(0)).build();
            } else {
                return Response.status(OK).entity(searchResult).build();
            }
        }
        List<User> users = userManager.getAllUsers();
        return Response.status(OK).entity(users).build();
    }

    @GET
    @Path("/available")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllActiveClients() {
        List<User> all = userManager.getAllUsers();
        List<User> available = new ArrayList<>();
        for (User c : all) {
            if(!(c.isActive())) {
                available.add(c);
            }
        }
        return Response.status(OK).entity(available).build();
    }

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByUuid(@PathParam("uuid") UUID entityId) {
        User user = userManager.getUserByUuid(entityId);
        if(user != null) {
            return Response.status(OK).entity(user).build();
        } else {
            return Response.status(NOT_FOUND).build();
        }
    }

    // update
    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateClient(Client client) {
        userManager.updateClient(client);
        return Response.status(OK).entity(client).build();
    }

    @PUT
    @Path("/{entityId}/activate")
    public Response activateUser(@PathParam("entityId") UUID entityId) {
        User user = userManager.getUserByUuid(entityId);
        user.setActive(true);
        userManager.updateUser(user);
        return Response.status(NO_CONTENT).build();
    }

    @PUT
    @Path("/{entityId}/deactivate")
    public Response deactivateUser(@PathParam("entityId") UUID entityId) {
        User user = userManager.getUserByUuid(entityId);
        user.setActive(false);
        userManager.updateUser(user);
        return Response.status(NO_CONTENT).build();
    }



    // ========= other

    @POST
    @Path("/addFakeClient")
    @Produces(MediaType.APPLICATION_JSON)
    public Client addFakeClient() {
        Client c = DataFaker.getClient();
        userManager.registerClient(c);
        return c;
    }

    @POST
    @Path("/addFakeAdmin")
    @Produces(MediaType.APPLICATION_JSON)
    public UserAdmin addFakeUserAdmin() {
        UserAdmin c = DataFaker.getUserAdmin();
        userManager.registerUserAdmin(c);
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
