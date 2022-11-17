package pl.lodz.p.edu.rest.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.managers.EquipmentManager;
import pl.lodz.p.edu.rest.model.Equipment;
//import pl.lodz.p.edu.rest.repository.DataFaker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Path("/equipment")
public class EquipmentController {

    @Inject
    private EquipmentManager equipmentManager;

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEquipment(Equipment equipment) {
        equipmentManager.add(equipment);
        return Response.status(Response.Status.OK).entity(equipment).build();
    }

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

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/available")
//    public Response getAllAvailableEquipment() {
//        List<Equipment> all = equipmentManager.getAll();
//        List<Equipment> available = new ArrayList<>();
//        for (Equipment e : all) {
//            if (!(e.isArchive() || e.isMissing())) {
//                available.add(e);
//            }
//        }
//        return Response.status(Response.Status.OK).entity(available).build();
//    }

    @PUT
    @Path("/{uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEquipment(@PathParam("uuid") UUID uuid, Equipment equipment) {
        Equipment current = equipmentManager.get(uuid);
        if(current == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        current.merge(equipment);
        equipmentManager.update(current);

        return Response.status(Response.Status.OK).entity(equipment).build();
    }

    @DELETE
    @Path("/{uuid}")
    public Response unregisterEquipment(@PathParam("uuid") UUID uuid) {
        equipmentManager.remove(uuid);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    //===============================================

//    @POST
//    @Path("/addFakeEq")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Equipment addFakeEquipment() {
//        Equipment e = DataFaker.getEquipment();
//        System.out.println(e);
//        equipmentManager.add(e);
//        return e;
//    }
}
