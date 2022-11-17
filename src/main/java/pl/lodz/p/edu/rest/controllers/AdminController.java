package pl.lodz.p.edu.rest.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.exception.NoObjectException;
import pl.lodz.p.edu.rest.exception.user.MalformedUserException;
import pl.lodz.p.edu.rest.exception.user.UserConflictException;
import pl.lodz.p.edu.rest.managers.UserManager;
import pl.lodz.p.edu.rest.model.users.*;

import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;
import static jakarta.ws.rs.core.Response.Status.CONFLICT;

@Path("/admins")
public class AdminController {

    @Inject
    private UserManager userManager;

    @Inject
    private UserControllerMethods userControllerMethods;

    protected AdminController() {}

    // create

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAdmin(Admin admin) {
        try {
            userManager.registerUserAdmin(admin);
            return Response.status(CREATED).entity(admin).build();
        } catch(NoObjectException e) {
            return Response.status(BAD_REQUEST).build();
        } catch(UserConflictException e) {
            return Response.status(CONFLICT).build();
        } catch(MalformedUserException e) {
            return Response.status(NOT_ACCEPTABLE).build();
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

//    @POST
//    @Path("/addFake")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Admin addFakeUserAdmin() {
//        Admin c = DataFaker.getUserAdmin();
//        try {
//            userManager.registerUserAdmin(c);
//        } catch(Exception e) {
//            return null;
//        }
//        return c;
//    }
}
