package pl.lodz.p.edu.rest.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.DTO.AdminDTO;
import pl.lodz.p.edu.rest.exception.NoObjectException;
import pl.lodz.p.edu.rest.exception.user.IllegalModificationException;
import pl.lodz.p.edu.rest.exception.user.MalformedUserException;
import pl.lodz.p.edu.rest.exception.user.UserConflictException;
import pl.lodz.p.edu.rest.managers.UserManager;
import pl.lodz.p.edu.rest.model.users.*;
import pl.lodz.p.edu.rest.repository.DataFaker;

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
            userManager.registerAdmin(admin);
            return Response.status(CREATED).entity(admin).build();
        } catch(NullPointerException e) {
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
        return userControllerMethods.searchUser("Admin", login);
    }

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByUuid(@PathParam("uuid") UUID entityId) {
        return userControllerMethods.getSingleUser("Admin", entityId);
    }

    // update
    @PUT
    @Path("/{entityId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAdmin(@PathParam("entityId") UUID entityId, AdminDTO adminDTO) {
        try {
            userManager.updateAdmin(entityId, adminDTO);
            return Response.status(OK).entity(adminDTO).build();
        } catch (MalformedUserException e) {
            return Response.status(NOT_ACCEPTABLE).build();
        } catch(IllegalModificationException e) {
            return Response.status(NOT_ACCEPTABLE).build();
        } catch(NullPointerException e) {
            return Response.status(BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/{entityId}/activate")
    public Response activateUser(@PathParam("entityId") UUID entityId) {
        return userControllerMethods.activateUser("Admin", entityId);
    }

    @PUT
    @Path("/{entityId}/deactivate")
    public Response deactivateUser(@PathParam("entityId") UUID entityId) {
        return userControllerMethods.deactivateUser("Admin", entityId);
    }

    // other

    @POST
    @Path("/addFake")
    @Produces(MediaType.APPLICATION_JSON)
    public Admin addFakeUserAdmin() {
        Admin c = DataFaker.getUserAdmin();
        try {
            userManager.registerAdmin(c);
        } catch(Exception e) {
            return null;
        }
        return c;
    }
}
