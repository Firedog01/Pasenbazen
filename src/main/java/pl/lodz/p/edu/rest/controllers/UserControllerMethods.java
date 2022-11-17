package pl.lodz.p.edu.rest.controllers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.managers.UserManager;
import pl.lodz.p.edu.rest.model.users.User;

import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;

@RequestScoped
public class UserControllerMethods {

    @Inject
    private UserManager userManager;

    public Response searchUser(String type, String login) {
        if(login != null) {
            List<User> searchResult = userManager.searchOfType(type, login);
            return Response.status(OK).entity(searchResult).build();
        }
        List<User> users = userManager.getAllUsersOfType(type);
        return Response.status(OK).entity(users).build();
    }

    public Response getSingleUser(String type, UUID entityId) {
        User user = userManager.getUserByUuidOfType(type, entityId);
        if(user != null) {
            return Response.status(OK).entity(user).build();
        } else {
            return Response.status(NOT_FOUND).build();
        }
    }

    public Response getSingleUser(String type, String login) {
        User user = userManager.getUserByLoginOfType(type, login);
        if(user != null) {
            return Response.status(OK).entity(user).build();
        } else {
            return Response.status(NOT_FOUND).build();
        }
    }

    public Response activateUser(String type, UUID entityId) {
        User user = userManager.getUserByUuidOfType(type, entityId);
        user.setActive(true);
        userManager.updateUser(user);
        return Response.status(OK).entity(user).build();
    }

    public Response deactivateUser(String type, UUID entityId) {
        User user = userManager.getUserByUuidOfType(type, entityId);
        user.setActive(false);
        userManager.updateUser(user);
        return Response.status(OK).entity(user).build();
    }
}
