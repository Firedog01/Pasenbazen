package pl.lodz.p.edu.rest.controllers;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.exception.BusinessLogicInterruptException;
import pl.lodz.p.edu.rest.exception.ObjectNotValidException;
import pl.lodz.p.edu.rest.managers.EquipmentManager;
import pl.lodz.p.edu.rest.managers.RentManager;
import pl.lodz.p.edu.rest.managers.UserManager;
import pl.lodz.p.edu.rest.model.DTO.RentDTO;
import pl.lodz.p.edu.rest.model.Rent;
import pl.lodz.p.edu.rest.model.users.Client;

import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;

@Path("/rents")
public class RentController {

    @Inject
    private RentManager rentManager;

    @Inject
    private UserManager userManager;

    @Inject
    private EquipmentManager equipmentManager;

    // create

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response makeReservation(RentDTO rentDTO) {
        try {
            Rent rent = rentManager.add(rentDTO);
            return Response.status(CREATED).entity(rent).build();
        } catch (ObjectNotValidException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        } catch (BusinessLogicInterruptException e) {
            return Response.status(CONFLICT).build();
        }
    }

    // read

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/client/{uuid}")
    public Response getClientRents(@PathParam("uuid") UUID clientUuid) {
        try {
            Client client = (Client) userManager.getUserByUuidOfType("Client", clientUuid);
            List<Rent> rents = rentManager.getRentsByClient(client);
            return Response.status(Response.Status.OK).entity(rents).build();
        } catch (NoResultException e) {
            return Response.status(NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response getAllRents() {
        List<Rent> rents = rentManager.getAll();
        return Response.status(Response.Status.OK).entity(rents).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    public Response getRent(@PathParam("uuid") UUID uuid) {
        try {
            Rent rent = rentManager.get(uuid);
            return Response.status(Response.Status.OK).entity(rent).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    public Response modifyRent(@PathParam("uuid") UUID entityId, RentDTO rentDTO) {
        try {
            rentManager.update(entityId, rentDTO);
            return Response.status(OK).entity(rentDTO).build();
        } catch (ObjectNotValidException e) {
            return Response.status(BAD_REQUEST).build();
        } catch (TransactionalException e) {
            return Response.status(BAD_REQUEST).build();
        } catch (NoResultException e) {
            return Response.status(NOT_FOUND).build();
        } catch (BusinessLogicInterruptException e) {
            return Response.status(CONFLICT).build();
        }
    }

    @DELETE
    @Path("/{uuid}")
    public Response cancelReservation(@PathParam("uuid") UUID rentUuid) {
        try {
            rentManager.remove(rentUuid);
            return Response.status(NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return Response.status(NO_CONTENT).build();
        } catch (BusinessLogicInterruptException e) {
            return Response.status(CONFLICT).build();
        }
    }


//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    @Path("/available/{equipmentUuid}")
//    public boolean isAvailable(@PathParam("equipmentUuid") UUID equipmentUuid) {
//        Equipment equipment = equipmentManager.get(equipmentUuid);
//        return Objects.equals(rentManager.whenAvailable(equipment), LocalDateTime.now());
//    }

    //TODO PLANTEXT?

}
