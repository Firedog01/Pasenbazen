package pl.lodz.p.edu.rest.controllers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.managers.UserManager;
import pl.lodz.p.edu.rest.model.users.User;

import java.awt.image.RescaleOp;
import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;

@RequestScoped
public class UserControllerMethods {

    @Inject
    private UserManager userManager;

    // todo podział na rodzaje userów
    public Response searchUser(String login) {
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

    // todo podział na rodzaje userów

    public Response getSingleUser(UUID entityId) {
        User user = userManager.getUserByUuid(entityId);
        if(user != null) {
            return Response.status(OK).entity(user).build();
        } else {
            return Response.status(NOT_FOUND).build();
        }
    }

    public Response activateUser(UUID entityId) {
        User user = userManager.getUserByUuid(entityId);
        user.setActive(true);
        userManager.updateUser(user);
        return Response.status(OK).entity(user).build();
    }

    public Response deactivateUser(UUID entityId) {
        User user = userManager.getUserByUuid(entityId);
        user.setActive(false);
        userManager.updateUser(user);
        return Response.status(OK).entity(user).build();
    }
}
