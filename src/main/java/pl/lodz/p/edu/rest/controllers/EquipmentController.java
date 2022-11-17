package pl.lodz.p.edu.rest.controllers;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.exception.IllegalModificationException;
import pl.lodz.p.edu.rest.exception.ObjectNotValidException;
import pl.lodz.p.edu.rest.exception.ConflictException;
import pl.lodz.p.edu.rest.managers.EquipmentManager;
import pl.lodz.p.edu.rest.model.DTO.EquipmentDTO;
import pl.lodz.p.edu.rest.model.Equipment;
import pl.lodz.p.edu.rest.repository.DataFaker;

import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;
import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;


@Path("/equipment")
public class EquipmentController {

    @Inject
    private EquipmentManager equipmentManager;

    // create

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEquipment(EquipmentDTO equipmentDTO) {
        try {
            Equipment equipment = new Equipment(equipmentDTO);
            equipmentManager.add(equipment);
            return Response.status(CREATED).entity(equipment).build();
        } catch(ConflictException e) {
            return Response.status(CONFLICT).build();
        } catch(TransactionalException e) {
            return Response.status(CONFLICT).build();
        } catch(NullPointerException e) {
            return Response.status(BAD_REQUEST).build();
        } catch(ObjectNotValidException e) {
            return Response.status(BAD_REQUEST).build();
        }
        
    }

    // read

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEquipment() {
        List<Equipment> equipment = equipmentManager.getAll();
        return Response.status(Response.Status.OK).entity(equipment).build();
    }

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEquipment(@PathParam("uuid") UUID uuid) {
        Equipment equipment = equipmentManager.get(uuid);
        if (equipment != null) {
            return Response.status(Response.Status.OK).entity(equipment).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // update

    @PUT
    @Path("/{entityId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEquipment(@PathParam("entityId") UUID entityId, EquipmentDTO equipmentDTO) {
        try {
            equipmentManager.update(entityId, equipmentDTO);
            return Response.status(OK).entity(equipmentDTO).build();
        } catch (ObjectNotValidException | IllegalModificationException e) {
            return Response.status(BAD_REQUEST).build();
        } catch(TransactionalException e) { // login modification
            return Response.status(BAD_REQUEST).build();
        } catch(NullPointerException e) {
            return Response.status(NOT_FOUND).build();
        } catch(EntityNotFoundException e) {
            return Response.status(NOT_FOUND).build();
        }
    }

    // delete

    @DELETE
    @Path("/{uuid}")
    public Response unregisterEquipment(@PathParam("uuid") UUID uuid) {
        equipmentManager.remove(uuid);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    //===============================================

    @POST
    @Path("/addFake")
    @Produces(MediaType.APPLICATION_JSON)
    public Equipment addFakeEquipment() {
        Equipment e = DataFaker.getEquipment();
        try {
            equipmentManager.add(e);
        } catch(Exception ex) {
            return null;
        }
        return e;
    }
}
