package pl.lodz.p.edu.rest.managers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.exception.EquipmentException;
import jakarta.persistence.EntityNotFoundException;
import pl.lodz.p.edu.rest.model.EQ.*;

import pl.lodz.p.edu.rest.model.UniqueId;
import pl.lodz.p.edu.rest.repository.RepositoryFactory;
import pl.lodz.p.edu.rest.repository.RepositoryType;
import pl.lodz.p.edu.rest.repository.impl.EquipmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//public class EquipmentManager {
//    EquipmentRepository equipmentRepository;
//
//    public EquipmentManager(EquipmentRepository equipmentRepository) {
//        this.equipmentRepository = equipmentRepository;
//    }
//
//    public void unregisterEquipment(Equipment equipment) {
//        Equipment e = equipmentRepository.get(equipment.getEntityId());
//        equipmentRepository.remove(e);
//    }
//
//    public List<Equipment> getAllEquipment() {
//        return equipmentRepository.getAll();
//    }
//
//    public List<Equipment> getAllAvailableEquipment() {
//        List<Equipment> all = getAllEquipment();
//        List<Equipment> available = new ArrayList<>();
//        for (Equipment e : all) {
//            if(!(e.isArchive() || e.isMissing())) {
//                available.add(e);
//            }
//        }
//        return available;
//    }
//
//    public Equipment registerCamera(double fDayCost, double nDayCost, double bail, String name, String resolution) throws EquipmentException {
//        Camera camera = new Camera(fDayCost, nDayCost, bail, name, resolution);
//        equipmentRepository.add(camera);
//        return camera;
//    }
//
//    public Equipment registerTrivet(double fDayCost, double nDayCost, double bail, String name, double weight) throws EquipmentException {
//        Trivet trivet = new Trivet(fDayCost, nDayCost, bail, name, weight);
//        equipmentRepository.add(trivet);
//        return trivet;
//    }
//
//    public Equipment registerLens(double fDayCost, double nDayCost, double bail, String name, String length) throws EquipmentException {
//        Lens lens = new Lens(fDayCost, nDayCost, bail, name, length);
//        equipmentRepository.add(lens);
//        return lens;
//    }
//
//    public Equipment registerMicrophone(double fDayCost, double nDayCost, double bail, String name, String sensitivity) throws EquipmentException {
//        Microphone microphone = new Microphone(fDayCost, nDayCost, bail, name, sensitivity);
//        equipmentRepository.add(microphone);
//        return microphone;
//    }
//
//    public Equipment registerLighting(double fDayCost, double nDayCost, double bail, String name, String brightness) throws EquipmentException {
//        Lighting lighting = new Lighting(fDayCost, nDayCost, bail, name, brightness);
//        equipmentRepository.add(lighting);
//        return lighting;
//    }
//
//    public Equipment getEquipment(UniqueId id) {
//        try {
//            return equipmentRepository.get(id);
//        } catch(EntityNotFoundException ex) {
//            return null;
//        }
//    }

@Path("/equipment")
public class EquipmentManager {
    private EquipmentRepository equipmentRepository = (EquipmentRepository) RepositoryFactory.getRepository(RepositoryType.EquipmentRepository);

    @DELETE
    @Path("/{id}")
    public Response unregisterEquipment(@PathParam("id") UUID uuid) {
        if(equipmentRepository.remove(uuid)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
            //not sure what response status
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEquipment() {
        List<Equipment> equipment = equipmentRepository.getAll();
        return Response.status(Response.Status.OK).entity(equipment).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/available")
    public Response getAllAvailableEquipment() {
        List<Equipment> all = equipmentRepository.getAll();
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
        if(equipmentRepository.add(camera)) {
            return Response.status(Response.Status.CREATED).entity(camera).build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/trivet")
    public Response registerTrivet(Trivet trivet) {
        if(equipmentRepository.add(trivet)) {
            return Response.status(Response.Status.CREATED).entity(trivet).build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/lens")
    public Response registerLens(Lens lens) {
        if(equipmentRepository.add(lens)) {
            return Response.status(Response.Status.CREATED).entity(lens).build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/microphone")
    public Response registerMicrophone(Microphone microphone) {
        if(equipmentRepository.add(microphone)) {
            return Response.status(Response.Status.CREATED).entity(microphone).build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/lighting")
    public Response registerLighting(Lighting lighting) {
        if(equipmentRepository.add(lighting)) {
            return Response.status(Response.Status.CREATED).entity(lighting).build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    public Response getEquipment(@PathParam("uuid") UUID uuid) {
        Equipment equipment = equipmentRepository.get(uuid);
        if(equipment != null) {
            return Response.status(Response.Status.OK).entity(equipment).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
