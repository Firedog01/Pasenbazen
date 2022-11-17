package pl.lodz.p.edu.rest.controllers;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.DTO.RentDTO;
import pl.lodz.p.edu.rest.managers.EquipmentManager;
import pl.lodz.p.edu.rest.managers.RentManager;
import pl.lodz.p.edu.rest.managers.UserManager;
import pl.lodz.p.edu.rest.model.Equipment;
import pl.lodz.p.edu.rest.model.Rent;
import pl.lodz.p.edu.rest.model.users.Client;
import pl.lodz.p.edu.rest.model.users.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Path("/rents")
public class RentController {

    @Inject
    private RentManager rentManager;

    @Inject
    private UserManager userManager;

    @Inject
    private EquipmentManager equipmentManager;
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response makeReservation(RentDTO rentDTO) {

        User user = userManager.getUserByUuid(rentDTO.getClientUUIDFromString());
        Client client = (Client) user; // may throw

        Equipment equipment = equipmentManager.get(rentDTO.getEquipmentFromString());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime beginTime = LocalDateTime.parse(rentDTO.getBeginTime());
        LocalDateTime endTime = LocalDateTime.parse(rentDTO.getEndTime());


        if (client == null || equipment == null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else if (equipment.isArchive() || equipment.isMissing() || !client.isActive()
                || beginTime.isEqual(now) || beginTime.isBefore(now) || beginTime.isAfter(endTime)) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        try {
            List<Rent> rentEquipmentList = rentManager.getRentByEq(equipment);
            if(!rentEquipmentList.isEmpty() && validateTime(rentEquipmentList, beginTime, endTime)) {
                Rent rent = new Rent(LocalDateTime.parse(rentDTO.getBeginTime()),
                        LocalDateTime.parse(rentDTO.getEndTime()),
                        equipment, client);

                rentManager.add(rent);
                return Response.status(Response.Status.CREATED).entity(rent).build();
            } else {
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }

        } catch (EntityNotFoundException e) {
            Rent rent = new Rent(LocalDateTime.parse(rentDTO.getBeginTime()),
                    LocalDateTime.parse(rentDTO.getEndTime()),
                    equipment, client);

            rentManager.add(rent);
            return Response.status(Response.Status.CREATED).entity(rent).build();
        }
    }

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
        rentManager.remove(rentUuid);
        return Response.status(Response.Status.NO_CONTENT).build();
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

    public boolean validateTime(List<Rent> rentEquipmentList, LocalDateTime beginTime, LocalDateTime endTime) {
        for (int i = 0; i < rentEquipmentList.size(); i++) {
            Rent curRent = rentEquipmentList.get(i);

            // +----- old rent -----+
            //         +----- new rent -----+
            if (beginTime.isBefore(curRent.getEndTime()) && beginTime.isAfter(curRent.getBeginTime())) {
                return false;
            }
            //         +----- old rent -----+
            // +----- new rent -----+
            if (endTime.isAfter(curRent.getBeginTime()) && endTime.isBefore(curRent.getEndTime())) {
                return false;
            }
            // +----- old rent -----+
            //    +-- new rent --+
            if (beginTime.isAfter(curRent.getBeginTime()) && beginTime.isBefore(curRent.getEndTime())) {
                return false;
            }
            //    +-- old rent --+
            // +----- new rent -----+
            if (beginTime.isBefore(curRent.getBeginTime()) && endTime.isAfter(curRent.getEndTime())) {
                return false;
            }
        }
        return true;
    }
}
