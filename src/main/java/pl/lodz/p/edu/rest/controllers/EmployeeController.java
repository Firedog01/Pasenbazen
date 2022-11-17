package pl.lodz.p.edu.rest.controllers;

import jakarta.inject.Inject;
import jakarta.persistence.RollbackException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.DTO.EmployeeDTO;
import pl.lodz.p.edu.rest.exception.NoObjectException;
import pl.lodz.p.edu.rest.exception.user.IllegalModificationException;
import pl.lodz.p.edu.rest.exception.user.MalformedUserException;
import pl.lodz.p.edu.rest.exception.user.UserConflictException;
import pl.lodz.p.edu.rest.managers.UserManager;
import pl.lodz.p.edu.rest.model.users.Client;
import pl.lodz.p.edu.rest.model.users.Employee;
import pl.lodz.p.edu.rest.repository.DataFaker;

import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;
import static jakarta.ws.rs.core.Response.Status.OK;

@Path("/employees")
public class EmployeeController {
    @Inject
    private UserManager userManager;

    @Inject
    private UserControllerMethods userControllerMethods;

    protected EmployeeController() {}

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addResourceAdmin(EmployeeDTO employeeDTO) {
        try {
            Employee employee = new Employee(employeeDTO);
            userManager.registerEmployee(employee);
            return Response.status(CREATED).entity(employeeDTO).build();
        } catch(UserConflictException e) {
            return Response.status(CONFLICT).build();
        } catch(NullPointerException e) {
            return Response.status(BAD_REQUEST).build();
        } catch(MalformedUserException e) {
            return Response.status(NOT_ACCEPTABLE).build();
        }
    }

    // read

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAdmin(@QueryParam("login") String login) {
        return userControllerMethods.searchUser("Employee", login);
    }

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByUuid(@PathParam("uuid") UUID entityId) {
        return userControllerMethods.getSingleUser("Employee", entityId);
    }

    // update
    @PUT
    @Path("/{entityId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("entityId") UUID entityId, EmployeeDTO employeeDTO) {
        try {
            userManager.updateEmployee(entityId, employeeDTO);
            return Response.status(OK).entity(employeeDTO).build();
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
        return userControllerMethods.activateUser("Employee", entityId);
    }

    @PUT
    @Path("/{entityId}/deactivate")
    public Response deactivateUser(@PathParam("entityId") UUID entityId) {
        return userControllerMethods.deactivateUser("Employee", entityId);
    }

    // other

    @POST
    @Path("/addFake")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee addFakeUserAdmin() {
        Employee c = DataFaker.getResourceAdmin();
        try {
            userManager.registerEmployee(c);
        } catch (Exception e) {
            return null;
        }
        return c;
    }
}
