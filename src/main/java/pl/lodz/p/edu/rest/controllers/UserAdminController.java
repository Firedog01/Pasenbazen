package pl.lodz.p.edu.rest.controllers;

import jakarta.inject.Inject;
import jakarta.persistence.RollbackException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.managers.UserManager;
import pl.lodz.p.edu.rest.model.users.*;
import pl.lodz.p.edu.rest.repository.DataFaker;

import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;
import static jakarta.ws.rs.core.Response.Status.CONFLICT;
import static pl.lodz.p.edu.rest.model.Rent_.client;

@Path("/admins")
public class UserAdminController {

    @Inject
    private UserManager userManager;

    @Inject
    private UserControllerMethods userControllerMethods;

    protected UserAdminController() {}

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUserAdmin(UserAdmin uadmin) {

        if (uadmin == null) {
            return Response.status(BAD_REQUEST).build();
        }

        try {
            userManager.registerUserAdmin(uadmin);
            return Response.status(CREATED).entity(uadmin).build();
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
    public Response updateEmployee(@PathParam("entityId") UUID entityId, ResourceAdmin resourceAdmin) {
        userManager.updateResourceAdmin(entityId, resourceAdmin);
        return Response.status(OK).entity(resourceAdmin).build();
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

    @POST
    @Path("/addFake")
    @Produces(MediaType.APPLICATION_JSON)
    public UserAdmin addFakeUserAdmin() {
        UserAdmin c = DataFaker.getUserAdmin();
        userManager.registerUserAdmin(c);
        return c;
    }
}
