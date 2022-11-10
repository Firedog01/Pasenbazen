package pl.lodz.p.edu.rest.managers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.DTO.RentDTO;
import pl.lodz.p.edu.rest.model.Client;
import pl.lodz.p.edu.rest.model.EQ.Equipment;
import pl.lodz.p.edu.rest.model.Rent;
import org.joda.time.LocalDateTime;
import pl.lodz.p.edu.rest.repository.RepositoryFactory;
import pl.lodz.p.edu.rest.repository.RepositoryType;
import pl.lodz.p.edu.rest.repository.impl.ClientRepository;
import pl.lodz.p.edu.rest.repository.impl.EquipmentRepository;
import pl.lodz.p.edu.rest.repository.impl.RentRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Path("/rents")
public class RentManager {
    private final static RentRepository rentRepository =
            (RentRepository) RepositoryFactory.getRepository(RepositoryType.RentRepository);
    private final static ClientRepository clientRepository =
            (ClientRepository) RepositoryFactory.getRepository(RepositoryType.ClientRepository);
    private final static EquipmentRepository equipmentRepository =
            (EquipmentRepository) RepositoryFactory.getRepository(RepositoryType.EquipmentRepository);


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeReservation(RentDTO rentDTO) {
//        ClientRepository clientRepository =
//                (ClientRepository) RepositoryFactory.getRepository(RepositoryType.ClientRepository);
//        EquipmentRepository equipmentRepository =
//                (EquipmentRepository) RepositoryFactory.getRepository(RepositoryType.EquipmentRepository);

        Client client = clientRepository.get(rentDTO.getClientUUID());
        Equipment equipment = equipmentRepository.get(rentDTO.getEquipmentUUID());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime beginTime = LocalDateTime.parse(rentDTO.getBeginTime());
        LocalDateTime endTime = LocalDateTime.parse(rentDTO.getEndTime());


        if (client == null || equipment == null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else if (equipment.isArchive() || equipment.isMissing() || client.isArchive()
                || beginTime.isEqual(now) || beginTime.isBefore(now) || beginTime.isAfter(endTime)) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        boolean good = true;
        List<Rent> rentEquipmentList = rentRepository.getRentByEq(equipment);

        for (int i = 0; i < rentEquipmentList.size(); i++) {
            Rent curRent = rentEquipmentList.get(i);

            // +----- old rent -----+
            //         +----- new rent -----+
            if (beginTime.isBefore(curRent.getEndTime()) && beginTime.isAfter(curRent.getBeginTime())) {
                good = false;
            }
            //         +----- old rent -----+
            // +----- new rent -----+
            if (endTime.isAfter(curRent.getBeginTime()) && endTime.isBefore(curRent.getEndTime())) {
                good = false;
            }
            // +----- old rent -----+
            //    +-- new rent --+
            if (beginTime.isAfter(curRent.getBeginTime()) && beginTime.isBefore(curRent.getEndTime())) {
                good = false;
            }
            //    +-- old rent --+
            // +----- new rent -----+
            if (beginTime.isBefore(curRent.getBeginTime()) && endTime.isAfter(curRent.getEndTime())) {
                good = false;
            }
        }

        if (good) {
            Rent rent = new Rent(LocalDateTime.parse(rentDTO.getBeginTime()),
                    LocalDateTime.parse(rentDTO.getEndTime()),
                    equipment, client, rentDTO.getShippingAddress());
            rentRepository.add(rent);
            return Response.status(Response.Status.CREATED).entity(rent).build();
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/client/{clientuuid}")
    public Response getClientRents(@PathParam("clientuuid") UUID clientUuid) {
        List<Rent> rents = rentRepository.getRentsByClientId(clientUuid);
        return Response.status(Response.Status.OK).entity(rents).build();
    }

    public void shipEquipment(Rent rent) {
        rent.setShipped(true);
        rentRepository.update(rent.getUuid(), rent);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRents() {
        List<Rent> rents = rentRepository.getAll();
        return Response.status(Response.Status.OK).entity(rents).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    public Response getRent(@PathParam("uuid") UUID uuid) {
        Rent rent = rentRepository.get(uuid);
        if (rent != null) {
            return Response.status(Response.Status.OK).entity(rent).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{uuid}")
    public Response cancelReservation(@PathParam("uuid") UUID rentUuid) {
        if (rentRepository.remove(rentUuid)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    } //FIXME send UUID or get UUID from rent obj?

    public void returnEquipment(Rent rent, boolean missing) {
        rent.getEquipment().setMissing(missing);
        rentRepository.update(rent.getUuid(), rent);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/available/{equipmentUuid}")
    public boolean isAvailable( UUID equipmentUuid) {
        Equipment equipment =
        return Objects.equals(whenAvailable(equipment), LocalDateTime.now());
    }


    public LocalDateTime whenAvailable(Equipment equipment) {
        // todo
        if (equipment.isArchive() || equipment.isMissing()) {
            return null;
        }
        LocalDateTime when = LocalDateTime.now();
        List<Rent> equipmentRents = rentRepository.getRentByEq(equipment);
//        List<Rent> equipmentRents = equipment.getEquipmentRents();

        for (Rent rent :
                equipmentRents) {
            if (when.isAfter(rent.getBeginTime()) && when.isBefore(rent.getEndTime())) {
                when = rent.getEndTime();
            }
        }
        return when;
    }

    public LocalDateTime untilAvailable(Equipment equipment) {
        // todo
        LocalDateTime until = null;

        if (equipment.isArchive() || equipment.isMissing()) {
            return null;
        }

        LocalDateTime when = whenAvailable(equipment);
        List<Rent> equipmentRents = rentRepository.getRentByEq(equipment);
        List<Rent> equipmentRents = equipment.getEquipmentRents();

        for (Rent rent :
                equipmentRents) {
            if (when.isBefore(rent.getBeginTime())) {
                until = rent.getEndTime();
            }
        }
        return until;
    }
}


//    public double checkClientBalance(Client client) {
//        List<Rent> rentList = getClientRents(client.getUuid());
//        double balance = 0.0;
//        for (Rent rent :
//                rentList) {
//            balance += rent.getRentCost();
//        }
//        return balance;
//    }

}

/*
public class RentManager {
    private RentRepository rentRepository;

    public RentManager(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    public Rent makeReservation(Client client, Equipment equipment, Address address,
                                LocalDateTime beginTime, LocalDateTime endTime) {
        if (equipment.isMissing() || equipment.isArchive()) {
            return null;
        }
        if (client.isArchive()) {
            return null;
        }
        LocalDateTime now = LocalDateTime.now();
        if (beginTime.isEqual(now) || beginTime.isBefore(now)) {
            return null;
        }
        if (beginTime.isAfter(endTime)) {
            return null;
        }

        boolean good = true;
//        List<Rent> rentEquipmentList = equipment.getEquipmentRents();
        List<Rent> rentEquipmentList = rentRepository.getEquipmentRents(equipment);

        System.out.println(rentEquipmentList);
        for(Rent r : rentEquipmentList) {
            System.out.println(r);
        }

        for (int i = 0; i < rentEquipmentList.size(); i++) {
            Rent curRent = rentEquipmentList.get(i);

            // +----- old rent -----+
            //         +----- new rent -----+
            if (beginTime.isBefore(curRent.getEndTime()) && beginTime.isAfter(curRent.getBeginTime())) {
                good = false;
            }
            //         +----- old rent -----+
            // +----- new rent -----+
            if (endTime.isAfter(curRent.getBeginTime()) && endTime.isBefore(curRent.getEndTime())) {
                good = false;
            }
            // +----- old rent -----+
            //    +-- new rent --+
            if (beginTime.isAfter(curRent.getBeginTime()) && beginTime.isBefore(curRent.getEndTime())) {
                good = false;
            }
            //    +-- old rent --+
            // +----- new rent -----+
            if (beginTime.isBefore(curRent.getBeginTime()) && endTime.isAfter(curRent.getEndTime())) {
                good = false;
            }
        }

        if (good) {
            Rent rent = new Rent(beginTime, endTime, equipment, client, address);
            rentRepository.add(rent);
            return rent;
        } else {
            return null;
        }
    }

    public List<Rent> getClientRents(Client client) {
        return rentRepository.getRentByClient(client);
    }

    public void shipEquipment(Rent rent) {
        rent.setShipped(true);
        rentRepository.update(rent);
    }

    public boolean isAvailable(Equipment equipment) {
        return Objects.equals(whenAvailable(equipment), LocalDateTime.now());
    }


    public LocalDateTime whenAvailable(Equipment equipment) {
        // todo
        if (equipment.isArchive() || equipment.isMissing()) {
            return null;
        }
        LocalDateTime when = LocalDateTime.now();
        List<Rent> equipmentRents = rentRepository.getRentByEq(equipment);
//        List<Rent> equipmentRents = equipment.getEquipmentRents();

        for (Rent rent :
                equipmentRents) {
            if (when.isAfter(rent.getBeginTime()) && when.isBefore(rent.getEndTime())) {
                when = rent.getEndTime();
            }
        }
        return when;
    }

    public LocalDateTime untilAvailable(Equipment equipment) {
        // todo
        LocalDateTime until = null;

        if (equipment.isArchive() || equipment.isMissing()) {
            return null;
        }

        LocalDateTime when = whenAvailable(equipment);
        List<Rent> equipmentRents = rentRepository.getRentByEq(equipment);
//        List<Rent> equipmentRents = equipment.getEquipmentRents();

        for (Rent rent :
                equipmentRents) {
            if (when.isBefore(rent.getBeginTime())) {
                until = rent.getEndTime();
            }
        }
        return until;
    }

    public void cancelReservation(Rent rent) {
        rentRepository.remove(rent);
    }

    public void returnEquipment(Rent rent, boolean missing) {
        rent.setEqReturned(true);
        rent.getEquipment().setMissing(missing);
        rentRepository.update(rent);
    }

    public double checkClientBalance(Client client) {
        // todo
        List<Rent> rentList = getClientRents(client);
        double balance = 0.0;
        for (Rent rent :
                rentList) {
            balance += rent.getRentCost();
        }
        return balance;
    }

    public List<Rent> getAllRents() {
        return rentRepository.getAll();
    }

    public Rent getRent(UniqueId id) {
        try {
            return rentRepository.get(id);
        } catch(EntityNotFoundException ex) {
            return null;
        }
    }
}
 */
