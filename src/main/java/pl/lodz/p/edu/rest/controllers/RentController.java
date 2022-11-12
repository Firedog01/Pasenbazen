package pl.lodz.p.edu.rest.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.joda.time.LocalDateTime;
import pl.lodz.p.edu.rest.DTO.RentDTO;
import pl.lodz.p.edu.rest.managers.ClientManager;
import pl.lodz.p.edu.rest.managers.EquipmentManager;
import pl.lodz.p.edu.rest.managers.RentManager;
import pl.lodz.p.edu.rest.model.Client;
import pl.lodz.p.edu.rest.model.EQ.Equipment;
import pl.lodz.p.edu.rest.model.Rent;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Path("/rents")
public class RentController {

    @Inject
    private RentManager rentManager;

    @Inject
    private ClientManager clientManager;

    @Inject
    private EquipmentManager equipmentManager;
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response makeReservation(RentDTO rentDTO) {

        Client client = clientManager.getClientByUuid(rentDTO.getClientUUID());
        Equipment equipment = equipmentManager.get(rentDTO.getEquipmentUUID());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime beginTime = LocalDateTime.parse(rentDTO.getBeginTime());
        LocalDateTime endTime = LocalDateTime.parse(rentDTO.getEndTime());


        if (client == null || equipment == null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else if (equipment.isArchive() || equipment.isMissing() || client.isArchive()
                || beginTime.isEqual(now) || beginTime.isBefore(now) || beginTime.isAfter(endTime)) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        List<Rent> rentEquipmentList = rentManager.getRentByEq(equipment);

        for (int i = 0; i < rentEquipmentList.size(); i++) {
            Rent curRent = rentEquipmentList.get(i);

            // +----- old rent -----+
            //         +----- new rent -----+
            if (beginTime.isBefore(curRent.getEndTime()) && beginTime.isAfter(curRent.getBeginTime())) {
                return null;
            }
            //         +----- old rent -----+
            // +----- new rent -----+
            if (endTime.isAfter(curRent.getBeginTime()) && endTime.isBefore(curRent.getEndTime())) {
                return null;
            }
            // +----- old rent -----+
            //    +-- new rent --+
            if (beginTime.isAfter(curRent.getBeginTime()) && beginTime.isBefore(curRent.getEndTime())) {
                return null;
            }
            //    +-- old rent --+
            // +----- new rent -----+
            if (beginTime.isBefore(curRent.getBeginTime()) && endTime.isAfter(curRent.getEndTime())) {
                return null;
            }
        }

        Rent rent = new Rent(LocalDateTime.parse(rentDTO.getBeginTime()),
                LocalDateTime.parse(rentDTO.getEndTime()),
                equipment, client, rentDTO.getShippingAddress());

        if(rentManager.add(rent)) {
            return Response.status(Response.Status.CREATED).entity(rent).build();
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    //TODO Client UUID?
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/client/{uuid}")
    public Response getClientRents(@PathParam("uuid") UUID clientUuid) {
        List<Rent> rents = rentManager.getRentsByClient(clientUuid);
        return Response.status(Response.Status.OK).entity(rents).build();
    }

    public void shipEquipment(Rent rent) {
        rent.setShipped(true);
        rentManager.update(rent);
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
        Rent rent = rentManager.get(uuid);
        if (rent != null) {
            return Response.status(Response.Status.OK).entity(rent).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{uuid}")
    public Response cancelReservation(@PathParam("uuid") UUID rentUuid) {
        if (rentManager.remove(rentUuid)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    } //FIXME send UUID or get UUID from rent obj?

    public void returnEquipment(Rent rent, boolean missing) {
        rent.getEquipment().setMissing(missing);
        rentManager.update(rent); //FIXME UPDATE BY POST? Kinda weird here tho
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/available/{equipmentUuid}")
    public boolean isAvailable(@PathParam("equipmentUuid") UUID equipmentUuid) {
        Equipment equipment = equipmentManager.get(equipmentUuid);
        return Objects.equals(whenAvailable(equipment), LocalDateTime.now());
    }

    //TODO PLANTEXT?
    public LocalDateTime whenAvailable(Equipment equipment) {
        // todo
        if (equipment.isArchive() || equipment.isMissing()) {
            return null;
        }
        LocalDateTime when = LocalDateTime.now();
        List<Rent> equipmentRents = rentManager.getRentByEq(equipment);
//        List<Rent> equipmentRents = equipment.getEquipmentRents();

        for (Rent rent :
                equipmentRents) {
            if (when.isAfter(rent.getBeginTime()) && when.isBefore(rent.getEndTime())) {
                when = rent.getEndTime();
            }
        }
        return when;
    }

    //TODO PLANTEXT?
    public LocalDateTime untilAvailable(Equipment equipment) {
        // todo
        LocalDateTime until = null;

        if (equipment.isArchive() || equipment.isMissing()) {
            return null;
        }

        LocalDateTime when = whenAvailable(equipment);
        List<Rent> equipmentRents = rentManager.getRentByEq(equipment);
//        List<Rent> equipmentRents = equipment.getEquipmentRents();

        for (Rent rent :
                equipmentRents) {
            if (when.isBefore(rent.getBeginTime())) {
                until = rent.getEndTime();
            }
        }
        return until;
    }
}
