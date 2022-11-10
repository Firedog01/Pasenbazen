package pl.lodz.p.edu.rest.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.managers.EquipmentManager;
import pl.lodz.p.edu.rest.model.EQ.*;
import pl.lodz.p.edu.rest.model.UniqueId;

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
            //not sure what response status
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
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
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/camera")
    public Response registerCamera(Camera camera) {
        if(equipmentManager.add(camera)) {
            return Response.status(Response.Status.CREATED).entity(camera).build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/trivet")
    public Response registerTrivet(Trivet trivet) {
        if(equipmentManager.add(trivet)) {
            return Response.status(Response.Status.CREATED).entity(trivet).build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/lens")
    public Response registerLens(Lens lens) {
        if(equipmentManager.add(lens)) {
            return Response.status(Response.Status.CREATED).entity(lens).build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/microphone")
    public Response registerMicrophone(Microphone microphone) {
        if(equipmentManager.add(microphone)) {
            return Response.status(Response.Status.CREATED).entity(microphone).build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/lighting")
    public Response registerLighting(Lighting lighting) {
        if(equipmentManager.add(lighting)) {
            return Response.status(Response.Status.CREATED).entity(lighting).build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    public Response getEquipment(@PathParam("uuid") UUID uuid) {
        Equipment equipment = equipmentManager.get(new UniqueId(uuid));
        if (equipment != null) {
            return Response.status(Response.Status.OK).entity(equipment).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
