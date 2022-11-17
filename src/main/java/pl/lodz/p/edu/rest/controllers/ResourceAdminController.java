package pl.lodz.p.edu.rest.controllers;

import jakarta.inject.Inject;
import jakarta.persistence.RollbackException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.managers.UserManager;
import pl.lodz.p.edu.rest.model.users.Client;
import pl.lodz.p.edu.rest.model.users.ResourceAdmin;
//import pl.lodz.p.edu.rest.repository.DataFaker;

import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;
import static jakarta.ws.rs.core.Response.Status.OK;

@Path("/employees")
public class ResourceAdminController {
    @Inject
    private UserManager userManager;

    @Inject
    private UserControllerMethods userControllerMethods;

    protected ResourceAdminController() {}

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addResourceAdmin(ResourceAdmin resourceAdmin) {

        if (resourceAdmin == null) {
            return Response.status(BAD_REQUEST).build();
        }

        try {
            userManager.registerResourceAdmin(resourceAdmin);
            return Response.status(CREATED).entity(resourceAdmin).build();
        } catch(RollbackException e) {
            return Response.status(CONFLICT).build();
        }
    }

    // read

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAdmin(@QueryParam("login") String login) {
        return userControllerMethods.searchUser(login);
    }

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByUuid(@PathParam("uuid") UUID entityId) {
        return userControllerMethods.getSingleUser(entityId);
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

    // other

//    @POST
//    @Path("/addFake")
//    @Produces(MediaType.APPLICATION_JSON)
//    public ResourceAdmin addFakeUserAdmin() {
//        ResourceAdmin c = DataFaker.getResourceAdmin();
//        userManager.registerResourceAdmin(c);
//        return c;
//    }
}
