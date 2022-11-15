package pl.lodz.p.edu.rest.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.managers.EquipmentManager;
import pl.lodz.p.edu.rest.model.EQ.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Path("/equipment")
public class EquipmentController {

    @Inject
    private EquipmentManager equipmentManager;

    @DELETE
    @Path("/{id}")
    public Response unregisterEquipment(@PathParam("id") UUID uuid) {
        if(equipmentManager.remove(uuid)) { //TODO
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response getAllEquipment() {
        List<Equipment> equipment = equipmentManager.getAll();
        return Response.status(Response.Status.OK).entity(equipment).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/available")
    public Response getAllAvailableEquipment() {
        List<Equipment> all = equipmentManager.getAll();
        List<Equipment> available = new ArrayList<>();
        for (Equipment e : all) {
            if (!(e.isArchive() || e.isMissing())) {
                available.add(e);
            }
        }
        return Response.status(Response.Status.OK).entity(available).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    public Response getEquipment(@PathParam("uuid") UUID uuid) {
        Equipment equipment = equipmentManager.get(uuid);
        if (equipment != null) {
            return Response.status(Response.Status.OK).entity(equipment).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
